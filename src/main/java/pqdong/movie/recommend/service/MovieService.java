package pqdong.movie.recommend.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.mahout.cf.taste.common.TasteException;
import org.springframework.stereotype.Service;
import pqdong.movie.recommend.data.constant.ServerConstant;
import pqdong.movie.recommend.data.dto.MovieSearchDto;
import pqdong.movie.recommend.data.dto.RatingDto;
import pqdong.movie.recommend.data.entity.MovieEntity;
import pqdong.movie.recommend.data.entity.RatingEntity;
import pqdong.movie.recommend.data.entity.UserEntity;
import pqdong.movie.recommend.data.repository.MovieRepository;
import pqdong.movie.recommend.data.repository.RatingRepository;
import pqdong.movie.recommend.data.repository.UserRepository;
import pqdong.movie.recommend.domain.service.MovieRecommender;
import pqdong.movie.recommend.redis.RedisApi;
import pqdong.movie.recommend.redis.RedisKeys;
import pqdong.movie.recommend.utils.RecommendUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * MovieService
 *
 * @author pqdong
 * @since 2020/03/31
 */

@Service
@Slf4j
public class MovieService {

    @Resource
    private ConfigService configService;

    @Resource
    private MovieRepository movieRepository;

    @Resource
    private RatingRepository ratingRepository;

    @Resource
    private UserRepository userRepository;

    @Resource
    private RedisApi redisApi;

    @Resource
    private MovieRecommender movieRecommender;

    private final static int RECOMMENT_SIZE = 4;


    // 获取推荐电影
    public List<MovieEntity> getRecommendMovie(UserEntity user) {
        // 用户已经登录
        List<MovieEntity> recommendMovies = new LinkedList<>();
        String recommend = "";
        if (user != null) {
            // load缓存数据
            recommend = redisApi.getString(RecommendUtils.getKey(RedisKeys.RECOMMEND, user.getUserMd()));
            if (StringUtils.isEmpty(recommend)) {
                // 用户打过分
                if (!ratingRepository.findAllByUserId(user.getId()).isEmpty()){
                    // 基于内容推荐
                    try {
                        List<Long> movieIds = movieRecommender.itemBasedRecommender(user.getId(), RECOMMENT_SIZE);
                        recommendMovies.addAll(movieRepository.findAllById(movieIds));
                    } catch (Exception e) {
                        log.info("{}",e);
                    }
                }
            } else {
                // 从缓存中直接加载
                recommendMovies.addAll(JSONObject.parseArray(recommend, MovieEntity.class));
            }
        } else{
            // 用户未登录，随机返回
            recommendMovies.addAll(movieRepository.findAllByCountLimit(RECOMMENT_SIZE));
        }
        // 上述过程异常，或者用户未登录，直接根据标签查询数据库并推荐
        // 从性能方面考虑先缓存，所以不是实时推荐的。如果有足够好的服务器，完全可以不缓存，搞成实时的
        if (recommendMovies.isEmpty() && user != null){
            recommendMovies.addAll(movieRepository.findAllByTag(Optional.ofNullable(user.getFormatTag())
                    .orElse(Collections.singletonList("科幻")).get(0), RECOMMENT_SIZE));
        }
        if (StringUtils.isEmpty(recommend)){
            redisApi.setValue(RecommendUtils.getKey(RedisKeys.RECOMMEND, user.getUserMd()),JSONObject.toJSONString(recommendMovies),1,TimeUnit.DAYS );
        }
        return recommendMovies;
    }

    // 获取高分电影
    public Map<String, Object> getHighMovie() {
        String movieByRedis = redisApi.getString(RedisKeys.HIGH_MOVIE);
        Map<String, Object> result = new HashMap<>(2, 1);
        if (StringUtils.isEmpty(movieByRedis)) {
            List<MovieEntity> allMovies = movieRepository.findAllByHighScore();
            redisApi.setValue(RedisKeys.HIGH_MOVIE, JSONObject.toJSONString(allMovies), 1, TimeUnit.DAYS);
            result.put("total", allMovies.size());
            result.put("movieList", allMovies);
        } else {
            List<MovieEntity> allMovies = JSONObject.parseArray(movieByRedis, MovieEntity.class);
            result.put("total", allMovies.size());
            result.put("movieList", allMovies);
        }
        return result;
    }

    // 获取电影标签
    public List<String> getMovieTags() {
        return JSON.parseArray(configService.getConfigValue("MOVIE_TAG"), String.class);
    }

    // 获取电影列表
    public Map<String, Object> getAllMovie(String key, int page, int size) {
        Pair<Integer, Integer> pair = RecommendUtils.getStartAndEnd(page, size);
        List<MovieEntity> allMovie = getMovies(key, "name", page * size);
        List<MovieEntity> movieList = allMovie.subList(pair.getLeft(), pair.getRight() <= allMovie.size() ? pair.getRight() : allMovie.size());
        Map<String, Object> result = new HashMap<>(2, 1);
        result.put("total", movieList.size());
        result.put("movieList", movieList.stream().peek(m -> {
            if (StringUtils.isEmpty(m.getCover())) {
                m.setCover(ServerConstant.DefaultImg);
            }
        }).collect(Collectors.toCollection(LinkedList::new)));
        return result;
    }

    // 根据条件搜索电影
    public Map<String, Object> searchMovies(MovieSearchDto info) {
        Pair<Integer, Integer> pair = RecommendUtils.getStartAndEnd(info.getPage(), info.getSize());
        List<MovieEntity> allMovie = info.getTags().stream()
                .map(t -> getMovies(t, "tag", info.getPage() * info.getSize()))
                .flatMap(Collection::stream)
                .collect(Collectors.toCollection(LinkedList::new));
        if (!StringUtils.isEmpty(info.getContent())) {
            allMovie.addAll(getMovies(info.getContent(), "name", info.getPage() * info.getSize()));
        }
        List<MovieEntity> movieList = allMovie.subList(pair.getLeft(), pair.getRight() <= allMovie.size() ? pair.getRight() : allMovie.size());
        Map<String, Object> result = new HashMap<>(2, 1);
        result.put("total", movieList.size());
        result.put("movieList", movieList.stream().peek(m -> {
            if (StringUtils.isEmpty(m.getCover())) {
                m.setCover(ServerConstant.DefaultImg);
            }
        }).collect(Collectors.toCollection(LinkedList::new)));
        return result;
    }

    // 根据电影名称等关键字获取电影列表
    private List<MovieEntity> getMovies(String key, String type, int total) {
        if (StringUtils.isBlank(key)) {
            return movieRepository.findAllByCountLimit(total);
        } else if (type.equals("name")) {
            return movieRepository.findAllByName(key, total);
        } else {
            return movieRepository.findAllByTag(key, total);
        }
    }

    // 获取电影详情信息
    public MovieEntity getMovie(Long movieId) {
        return movieRepository.findOneByMovieID(movieId);
    }

    // 获取演员所出演的所有电影
    public List<MovieEntity> getPersonAttendMovie(String personName) {
        return movieRepository.findAllByPersonName(personName).stream().peek(g -> {
            if (StringUtils.isEmpty(g.getCover())) {
                g.setCover(ServerConstant.DefaultImg);
            }
        }).collect(Collectors.toCollection(LinkedList::new));
    }

    // 对电影打分
    public MovieEntity updateScore(RatingDto ratingDto) {
        UserEntity user = userRepository.findOneByUserID(ratingDto.getUserId());
        MovieEntity movie = movieRepository.findOneByMovieID(ratingDto.getMovieId());
        if (user == null){
            return movie;
        }
        movie.setScore(ratingDto.getRating());
        RatingEntity rating = RatingEntity.builder()
                .movieId(ratingDto.getMovieId())
                .rating(ratingDto.getRating().intValue())
                .userId(ratingDto.getUserId())
                .releaseDate(new Date())
                .build();
        ratingRepository.save(rating);
        return movieRepository.save(movie);
    }
}
