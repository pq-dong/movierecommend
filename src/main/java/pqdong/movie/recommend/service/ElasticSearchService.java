package pqdong.movie.recommend.service;

import com.alibaba.fastjson.JSONObject;
import com.csvreader.CsvReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pqdong.movie.recommend.data.entity.CommentEs;
import pqdong.movie.recommend.data.entity.MovieEntity;
import pqdong.movie.recommend.data.entity.UserEntity;
import pqdong.movie.recommend.data.repository.CommentEsRepo;
import pqdong.movie.recommend.data.repository.MovieRepository;
import pqdong.movie.recommend.data.repository.UserRepository;
import pqdong.movie.recommend.utils.RecommendUtils;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;

/**
 * ElasticSearchService
 *
 * @author pqdong
 * @since 2020/03/31
 */

@Service
@Slf4j
public class ElasticSearchService {

    @Resource
    private ElasticsearchRestTemplate elasticsearchTemplate;

    @Resource
    private MovieRepository movieRepository;

    @Resource
    private UserRepository userRepository;

    @Resource
    private CommentEsRepo commentEsRepo;

    public List<CommentEs> getAllIndex() {
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder().withQuery(matchAllQuery())
                .withPageable(PageRequest.of(0, 100));
        SearchQuery query = builder.build();
        List<CommentEs> index = elasticsearchTemplate.queryForList(query, CommentEs.class);
        log.info("{}", index);
        return index;
    }

    // 更新所有评论，异步处理
    @Async("taskExecutor")
    public long updateAllComment(UserEntity userEntity) {
        List<CommentEs> commentEs = commentEsRepo.findByUserMd(userEntity.getUserMd());
        List queries = new ArrayList();
        int counter = 0;
        for (CommentEs comment : commentEs) {
            comment.setUserAvatar(userEntity.getUserAvatar());
            comment.setUserName(userEntity.getUsername());
            IndexQuery indexQuery = new IndexQuery();
            indexQuery.setId(Optional.ofNullable(comment.getCommentId())
                    .orElse(Long.parseLong(comment.getUserMd(),16))
                    .toString());
            indexQuery.setSource(JSONObject.toJSONString(comment));
            indexQuery.setIndexName("comment");
            indexQuery.setType("comment");
            queries.add(indexQuery);
            //分批提交修改
            if (counter != 0 && counter % 1000 == 0) {
                elasticsearchTemplate.bulkIndex(queries);
                queries.clear();
            }
            counter++;
        }
        // 提交不足量修改
        if (queries.size() > 0) {
            elasticsearchTemplate.bulkIndex(queries);
        }
        if (counter > 0) {
            elasticsearchTemplate.refresh("comment");
        }
        log.info("commentEs has update" + counter);
        return counter;
    }

    // 用于将csv文件中的数据导入到es表中，在处理用户昵称和电影名称时考虑到速度，不查询数据库，用现有数据代替
    public long importCommentToEs() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            ArrayList<String[]> csvList = new ArrayList<String[]>();
            CsvReader reader = new CsvReader("D:\\graduation\\data\\moviedata\\comments.csv", ',', StandardCharsets.UTF_8);
            reader.readHeaders(); //跳过表头,不跳可以注释掉

            while (reader.readRecord()) {
                csvList.add(reader.getValues()); //按行读取，并把每一行的数据添加到list集合
            }
            reader.close();

            List queries = new ArrayList();
            int counter = 0;
            for (String[] comment : csvList) {
                IndexQuery indexQuery = new IndexQuery();
                indexQuery.setId(comment[0]);
                indexQuery.setSource(JSONObject.toJSONString(CommentEs.builder()
                        .userAvatar(RecommendUtils.getRandomAvatar(comment[1]))
                        .userMd(comment[1])
                        .userName(comment[1])
                        .commentTime(dateFormat.parse(comment[5]))
                        .movieId(Long.valueOf(comment[2]))
                        .content(comment[3])
                        .movieName(comment[2])
                        .votes(Long.valueOf(comment[4]))
                        .build()));
                indexQuery.setIndexName("comment");
                indexQuery.setType("comment");
                queries.add(indexQuery);
                //分批提交修改
                if (counter != 0 && counter % 1000 == 0) {
                    elasticsearchTemplate.bulkIndex(queries);
                    log.info("comment to es has update");
                    queries.clear();
                }
                counter++;
            }
            // 提交不足量修改
            if (queries.size() > 0) {
                elasticsearchTemplate.bulkIndex(queries);
                log.info("comment to es has update");
            }
            if (counter > 0) {
                elasticsearchTemplate.refresh("comment");
                log.info("comment to es has refresh");
            }
            log.info("commentEs has update" + counter);
            return counter;

        } catch (Exception e) {
            log.info("{}", e.getMessage());
        }
        return 0;
    }

    // 对电影下的评论数据进行数据处理
    public long updateCommentToEs(Long movieId) {
        List<MovieEntity> movieEntities = new LinkedList<>();
        if (movieId != null && movieId != 0) {
            movieEntities.add(movieRepository.findOneByMovieID(movieId));
        } else {
            movieEntities = movieRepository.findAllByCountLimit(50);
            movieEntities.addAll(movieRepository.findAllByHighScore());
        }
        List queries = new ArrayList();
        int counter = 0;
        for (MovieEntity movieEntity : movieEntities) {
            List<CommentEs> commentEs = commentEsRepo.findByMovieId(movieEntity.getMovieId());
            for (CommentEs comment : commentEs) {
                UserEntity userEntity = userRepository.findByUserMd(comment.getUserMd());
                if (userEntity == null) {
                    if (comment.getContent().length()>5){
                        comment.setUserName(comment.getContent().substring(0,5));
                    }else{
                        comment.setUserName(comment.getContent());
                    }
                } else{
                    comment.setUserName(userEntity.getUsername());
                }
                comment.setMovieName(movieEntity.getName());
                IndexQuery indexQuery = new IndexQuery();
                indexQuery.setId(Optional.ofNullable(comment.getCommentId())
                        .orElse(Long.parseLong(comment.getUserMd(),16))
                        .toString());
                indexQuery.setSource(JSONObject.toJSONString(comment));
                indexQuery.setIndexName("comment");
                indexQuery.setType("comment");
                queries.add(indexQuery);
                //分批提交修改
                if (counter != 0 && counter % 1000 == 0) {
                    elasticsearchTemplate.bulkIndex(queries);
                    queries.clear();
                }
                counter++;
            }
        }
        // 提交不足量修改
        if (queries.size() > 0) {
            elasticsearchTemplate.bulkIndex(queries);
        }
        if (counter > 0) {
            elasticsearchTemplate.refresh("comment");
        }
        log.info("commentEs has update" + counter);
        return counter;
    }

}
