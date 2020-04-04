package pqdong.movie.recommend.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import pqdong.movie.recommend.data.constant.ServerConstant;
import pqdong.movie.recommend.data.dto.MovieSearchDto;
import pqdong.movie.recommend.data.entity.MovieEntity;
import pqdong.movie.recommend.data.repository.MovieRepository;
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
    private RedisApi redisApi;

    // 获取高分电影
    public Map<String, Object> getHighMovie(){
        String movieByRedis = redisApi.getString(RedisKeys.HIGH_MOVIE);
        Map<String, Object> result = new HashMap<>(2, 1);
        if (StringUtils.isEmpty(movieByRedis)){
            List<MovieEntity> allMovies = movieRepository.findAllByHighScore();
            redisApi.setValue(RedisKeys.HIGH_MOVIE, JSONObject.toJSONString(allMovies), 1, TimeUnit.DAYS);
            result.put("total", allMovies.size());
            result.put("movieList", allMovies);
        }else{
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

    // 修改电影信息
    public MovieEntity updateMovie(MovieEntity movie) {
        return movieRepository.save(movie);
    }
}
