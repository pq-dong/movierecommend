package pqdong.movie.recommend.controller;

import org.springframework.web.bind.annotation.*;
import pqdong.movie.recommend.annotation.LoginRequired;
import pqdong.movie.recommend.domain.util.ResponseMessage;
import pqdong.movie.recommend.service.ConfigService;
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
    @ResponseBody
    public ResponseMessage get() {
        return ResponseMessage.successMessage(movieService.getMovieTags());
    }

    /**
     * @method allPerson 查看所有演员
     * @param key 关键字
     * @param page 当前页数
     * @param size 每页数据量
     **/
    @GetMapping("/list")
    public ResponseMessage allPerson(
            @RequestParam(required = false, defaultValue = "") String key,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "12") int size) {
        return ResponseMessage.successMessage(movieService.getAllMovie(key, page, size));
    }

}
