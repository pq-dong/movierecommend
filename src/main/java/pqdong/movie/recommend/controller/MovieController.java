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

}
