package pqdong.movie.recommend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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

    /**
     * @method getUserInfo 获取用户信息
     */
    @GetMapping("/userInfo")
    public String getCourseInfo() {
        return "HelloWorld!!!";
    }
}
