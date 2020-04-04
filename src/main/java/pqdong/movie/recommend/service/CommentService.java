package pqdong.movie.recommend.service;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import pqdong.movie.recommend.data.dto.CommentSearchDto;
import pqdong.movie.recommend.data.entity.CommentEs;
import pqdong.movie.recommend.data.repository.CommentEsRepo;
import pqdong.movie.recommend.redis.RedisApi;
import pqdong.movie.recommend.redis.RedisKeys;
import pqdong.movie.recommend.utils.RecommendUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * ConfigService
 *
 * @author pqdong
 * @since 2020/04/02
 */

@Service
@Slf4j
public class CommentService {

    @Resource
    private CommentEsRepo commentEsRepo;

    @Resource
    private RedisApi redisApi;

    // 获取评论列表
    public Map<String, Object> getCommentList(CommentSearchDto commentSearchDto) {
        Pair<Integer, Integer> pair = RecommendUtils.getStartAndEnd(commentSearchDto.getPage(), commentSearchDto.getSize());
        List<CommentEs> allComment = getComments(commentSearchDto);
        List<CommentEs> commentList = allComment.subList(pair.getLeft(), pair.getRight() <= allComment.size() ? pair.getRight() : allComment.size());
        Map<String, Object> result = new HashMap<>(2, 1);
        result.put("total", commentList.size());
        result.put("commentList", commentList.stream()
                .peek(m -> {
                    if (StringUtils.isEmpty(m.getUserAvatar())) {
                        m.setUserAvatar(RecommendUtils.getRandomAvatar(m.getUserAvatar()));
                    }
                })
                .collect(Collectors.toCollection(LinkedList::new)));
        return result;
    }

    // 根据条件搜索
    private List<CommentEs> getComments(CommentSearchDto commentSearchDto) {
        if (commentSearchDto.getMovieId() != null && commentSearchDto.getMovieId() != 0) {
            return commentEsRepo.findByMovieId(commentSearchDto.getMovieId()).stream()
                    .sorted(Comparator.comparing(CommentEs::getCommentTime).reversed())
                    .collect(Collectors.toCollection(LinkedList::new));
        }else if (!StringUtils.isEmpty(commentSearchDto.getUserMd())) {
            return commentEsRepo.findByUserMd(commentSearchDto.getUserMd()).stream()
                    .sorted(Comparator.comparing(CommentEs::getCommentTime).reversed())
                    .collect(Collectors.toCollection(LinkedList::new));
        }
        return new LinkedList<>();
    }

    // 将评论信息写入es index
    public CommentEs submitComment(CommentEs commentEs) {
        // times 用来做防刷爬虫攻击，限制每个用户短时间内的第五次提交
        Integer times = Integer.valueOf(Optional
                .ofNullable(redisApi.getString(RecommendUtils.getKey(RedisKeys.BRUSH, commentEs.getUserMd())))
                .orElse("0"));
        if (times > 5){
            return null;
        } else {
            times = times + 1;
            redisApi.setValue(RecommendUtils.getKey(RedisKeys.BRUSH, commentEs.getUserMd()), times.toString(), 3, TimeUnit.MINUTES);
        }
        return commentEsRepo.save(commentEs);
    }

}
