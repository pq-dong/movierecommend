package pqdong.movie.recommend.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pqdong.movie.recommend.domain.util.ResponseMessage;
import pqdong.movie.recommend.service.SmsService;
import pqdong.movie.recommend.service.UserService;
import pqdong.movie.recommend.utils.RecommendUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
    private SmsService smsService;

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
        String token = userService.login(userName, password);
        if (StringUtils.isNotBlank(token)){
            return ResponseMessage.successMessage(token);
        } else {
            return ResponseMessage.successMessage("登录失败，请检查用户名或密码！");
        }

    }

    /**
     * @method code 发送短信验证码的接口
     * @param phone 手机号
     **/
    @GetMapping("/code")
    public ResponseMessage code(@RequestParam String phone) {
        String code = smsService.sendCode(phone);
        if (StringUtils.isNotEmpty(code)) {
            return ResponseMessage.successMessage("发送成功");
        } else {
            return ResponseMessage.failedMessage("发送失败");
        }
    }

    /**
     * @method upload 上传用户头像
     * @param avatar 头像
     **/
    @PostMapping("/avatar")
    public ResponseMessage upload(HttpServletRequest request, @RequestParam("avatar") MultipartFile avatar) {
        String userMd = RecommendUtils.getUserMd(request);
        boolean flag = userService.uploadAvatar(userMd, avatar);
        if (flag) {
            return ResponseMessage.successMessage("头像上传成功");
        } else {
            return ResponseMessage.failedMessage("上传头像失败");
        }
    }

}
