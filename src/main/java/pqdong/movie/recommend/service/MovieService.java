package pqdong.movie.recommend.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import pqdong.movie.recommend.data.entity.MovieEntity;
import pqdong.movie.recommend.data.entity.PersonEntity;
import pqdong.movie.recommend.data.repository.MovieRepository;
import pqdong.movie.recommend.utils.RecommendUtils;

import javax.annotation.Resource;
import java.util.*;

@Service
@Slf4j
public class MovieService {

    @Resource
    private ConfigService configService;
    @Resource
    private MovieRepository movieRepository;

    public List<String> getMovieTags(){
        return JSON.parseArray(configService.getConfigValue("MOVIE_TAG"),String.class);
    }

    public Map<String, Object> getAllMovie(String key, int page, int size) {
        Pair<Integer, Integer> pair = RecommendUtils.getStartAndEnd(page, size);
        List<MovieEntity> allMovie = getMovies(key, page*size);
        List<MovieEntity> movieList = allMovie.subList(pair.getLeft(), pair.getRight() <= allMovie.size() ? pair.getRight() : allMovie.size());
        Map<String, Object> result = new HashMap<>(2, 1);
        result.put("total", allMovie.size());
        result.put("movieList", movieList);
        return result;
    }

    private List<MovieEntity> getMovies(String key, int total) {
        List<MovieEntity> movieList;
        if (StringUtils.isBlank(key)) {
            movieList = movieRepository.findAllByCountLimit(total);
        } else {
            movieList = movieRepository.findAllByName(key);
        }
        return movieList;
    }
}
