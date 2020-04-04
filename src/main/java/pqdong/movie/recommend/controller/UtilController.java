package pqdong.movie.recommend.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pqdong.movie.recommend.domain.util.ResponseMessage;
import pqdong.movie.recommend.service.ElasticSearchService;

import javax.annotation.Resource;

/**
 * UtilController
 * @description 系统状态请求
 * @author pqdong
 * @since 2020/02/27 16:42
 */
@RestController
@RequestMapping("/util")
public class UtilController {

    @Resource
    private ElasticSearchService elasticSearchService;

    @GetMapping("/ping/system")
    public ResponseMessage pingSystem() {
        return ResponseMessage.successMessage("system health");
    }

    @GetMapping("/ping/es")
    public ResponseMessage pingEs() {
        return ResponseMessage.successMessage(elasticSearchService.getAllIndex());
    }

    @GetMapping("/backend/comment")
    public ResponseMessage backend() {
        return ResponseMessage.successMessage(elasticSearchService.importCommentToEs());
    }

    @GetMapping("/update/comment")
    public ResponseMessage update(@RequestParam(required = false) Long movieId) {
        return ResponseMessage.successMessage(elasticSearchService.updateCommentToEs(movieId));
    }
}
