package pqdong.movie.recommend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pqdong.movie.recommend.data.entity.UserEntity;
import pqdong.movie.recommend.domain.user.UserInfo;
import pqdong.movie.recommend.domain.util.ResponseMessage;
import pqdong.movie.recommend.service.UserService;

import javax.annotation.Resource;
import java.util.List;

/**
 * UserController
 * @description 用户信息相关接口
 * @author pqdong
 * @since 2020/02/27 16:42
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * @method getUserInfo 获取用户信息
     */
    @GetMapping("/userInfo")
    public ResponseMessage getCourseInfo(@RequestParam(required = true) String nameMd) {
        return ResponseMessage.successMessage(userService.getUserInfo(nameMd));
    }

    /**
     * @method login 登录接口
     */
    @PostMapping("/login")
    public ResponseMessage userLogin(@RequestParam(required = true) String userName, @RequestParam(required = true) String password) {
        return ResponseMessage.successMessage(userService.login(userName, password));
    }
}
