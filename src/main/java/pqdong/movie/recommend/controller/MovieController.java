package pqdong.movie.recommend.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import pqdong.movie.recommend.data.dto.MovieSearchDto;
import pqdong.movie.recommend.data.entity.MovieEntity;
import pqdong.movie.recommend.domain.util.ResponseMessage;
import pqdong.movie.recommend.service.MovieService;

import javax.annotation.Resource;

@RestController
@RequestMapping("/movie")
public class MovieController {

    @Resource
    private MovieService movieService;

    /**
     * @method getMovieTags 获取电影标签
     */
    @GetMapping("/tag")
    public ResponseMessage get() {
        return ResponseMessage.successMessage(movieService.getMovieTags());
    }

    /**
     * @method allMovie 获取电影列表
     * @param key 关键字
     * @param page 当前页数
     * @param size 每页数据量
     **/
    @GetMapping("/list")
    public ResponseMessage allMovie(
            @RequestParam(required = false, defaultValue = "") String key,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "12") int size) {
        return ResponseMessage.successMessage(movieService.getAllMovie(key, page, size));
    }

    /**
     * @method getMovie 获取电影详情
     * @param movieId 电影id
     **/
    @GetMapping("/info")
    public ResponseMessage getMovie(
            @RequestParam(required = true, defaultValue = "0") Long movieId) {
        return ResponseMessage.successMessage(movieService.getMovie(movieId));
    }

    /**
     * @param personName 演员id
     * @method getPersonMovie 获取演员出演的电影
     **/
    @GetMapping("/person/attend")
    public ResponseMessage getPersonAttendMovie(
            @RequestParam(required = true, defaultValue = "0") String personName) {
        return ResponseMessage.successMessage(movieService.getPersonAttendMovie(personName));
    }

    /**
     * @param info 查找条件
     * @method getMovieListByTag 根据标签获取电影列表
     **/
    @PostMapping("/listByTag")
    public ResponseMessage getMovieListByTag(@RequestBody(required = true) MovieSearchDto info) {
        if (info.getTags().isEmpty() && StringUtils.isEmpty(info.getContent())){
            return ResponseMessage.successMessage(movieService.getAllMovie("", info.getPage(), info.getSize()));
        }else{
            return ResponseMessage.successMessage(movieService.searchMovies(info));
        }
    }

    /**
     * @method getHighMovie 获取高分电影
     **/
    @GetMapping("/high")
    public ResponseMessage getHighMovie() {
        return ResponseMessage.successMessage(movieService.getHighMovie());
    }

    /**
     * @param movie 电影
     * @method updateMovie 修改电影信息
     **/
    @PostMapping("/update")
    public ResponseMessage updateMovie(@RequestBody(required = true) MovieEntity movie) {
        // TODO 需要将打分和标签信息扔到flink中做推荐处理写到es，打分这里涉及安全问题，应该能修改分数等信息
        return ResponseMessage.successMessage("");
    }
}
