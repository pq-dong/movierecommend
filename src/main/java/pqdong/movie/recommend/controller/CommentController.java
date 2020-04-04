package pqdong.movie.recommend.controller;

import org.springframework.web.bind.annotation.*;
import pqdong.movie.recommend.annotation.LoginRequired;
import pqdong.movie.recommend.data.dto.CommentSearchDto;
import pqdong.movie.recommend.data.entity.CommentEs;
import pqdong.movie.recommend.domain.util.ResponseMessage;
import pqdong.movie.recommend.service.CommentService;

import javax.annotation.Resource;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Resource
    private CommentService commentService;

    /**
     * @method getCommentList 获取电影标签
     */
    @PostMapping("/list")
    public ResponseMessage getCommentList(@RequestBody CommentSearchDto commentSearchDto) {
        return ResponseMessage.successMessage(commentService.getCommentList(commentSearchDto));
    }

    /**
     * @method submitComment 提交评论
     */
    @PostMapping("/submit")
    @LoginRequired
    public ResponseMessage submitComment(@RequestBody CommentEs commentEs) {
        CommentEs comment = commentService.submitComment(commentEs);
        if (comment == null){
            return ResponseMessage.failedMessage("留言过快或失败，请稍后重试！");
        }
        return ResponseMessage.successMessage(comment);
    }
}
