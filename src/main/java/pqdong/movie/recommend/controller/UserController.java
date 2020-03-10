package pqdong.movie.recommend.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pqdong.movie.recommend.annotation.LoginRequired;
import pqdong.movie.recommend.domain.user.UserInfo;
import pqdong.movie.recommend.domain.util.ResponseMessage;
import pqdong.movie.recommend.service.SmsService;
import pqdong.movie.recommend.service.UserService;

import javax.annotation.Resource;

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
    public ResponseMessage getCourseInfo(@RequestParam(required = true) String token) {
        return ResponseMessage.successMessage(userService.getUserInfo(token));
    }

    /**
     * @method register 注册用户
     */
    @PostMapping("/register")
    public ResponseMessage register(@RequestBody UserInfo user){
        String result = userService.register(user);
        if (result.equals("success")){
            return ResponseMessage.successMessage("success");
        } else{
            return ResponseMessage.failedMessage(result);
        }
    }

    /**
     * @method login 登录接口
     */
    @PostMapping("/login")
    public ResponseMessage userLogin(@RequestBody UserInfo user) {
        String token = userService.login(user.getUsername(), user.getPassword());
        if (StringUtils.isNotBlank(token)){
            return ResponseMessage.successMessage(token);
        } else {
            return ResponseMessage.failedMessage("登录失败，请检查用户名或密码！");
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
    @LoginRequired
    public ResponseMessage upload(@RequestParam("userMd") String userMd, @RequestParam("avatar") MultipartFile avatar) {
        boolean flag = userService.uploadAvatar(userMd, avatar);
        if (flag) {
            return ResponseMessage.successMessage("头像上传成功");
        } else {
            return ResponseMessage.failedMessage("上传头像失败");
        }
    }

    /**
     * @method logout 退出接口
     **/
    @GetMapping("/logout")
    @LoginRequired
    public ResponseMessage logout() {
        return ResponseMessage.successMessage(userService.logout());
    }

}
