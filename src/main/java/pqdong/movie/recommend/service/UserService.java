package pqdong.movie.recommend.service;

import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pqdong.movie.recommend.data.constant.UserConstant;
import pqdong.movie.recommend.data.entity.UserEntity;
import pqdong.movie.recommend.data.repository.UserRepository;
import pqdong.movie.recommend.domain.user.UserInfo;
import pqdong.movie.recommend.redis.RedisApi;
import pqdong.movie.recommend.redis.RedisKeys;
import pqdong.movie.recommend.utils.Md5EncryptionHelper;
import pqdong.movie.recommend.utils.RecommendUtils;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * userService
 *
 * @author pqdong
 * @since 2020/03/03
 */
@Slf4j
@Service

public class UserService {
    @Resource
    private UserRepository userRepository;

    @Resource
    private QiNiuService qiNiuService;

    @Resource
    private RedisApi redisApi;

    public UserEntity updateUser(UserEntity user){
        // 如果用户名已经存在，不进行更新
        UserEntity userSearch = userRepository.findByUserNickName(user.getUsername());
        // nickname唯一
        if (null != userSearch && !userSearch.getUserMd().equals(user.getUserMd())){
            return null;
        }
        UserEntity userInfo = userRepository.findByUserMd(user.getUserMd());
        user.setPassword(userInfo.getPassword());
        return userRepository.save(user);
    }

    public String register(UserInfo user){
        String code = redisApi.getString(RecommendUtils.getKey(UserConstant.PHONE_CODE, user.getPhone()));
        if (StringUtils.isEmpty(code)){
            return "验证码错误";
        } else {
          if (!code.equals(user.getCode())){
              return "验证码错误";
          }
          UserEntity userEntity = userRepository.findByUserNickName(user.getUsername());
          if (userEntity == null){
              userEntity = new UserEntity();
              try {
                  BeanUtils.copyProperties(user,userEntity);
              } catch (Exception e){
                  log.error("copy properties error");
                  return "注册失败";
              }
              userEntity.setPassword(Md5EncryptionHelper.getMD5WithSalt(user.getPassword()));
              userEntity.setUserMd(Md5EncryptionHelper.getMD5(Long.toString(System.currentTimeMillis())));
              userEntity.setUserAvatar(RecommendUtils.getRandomAvatar(user.getUsername()));
              userRepository.save(userEntity);
              System.out.println(userEntity.toString());
              return "success";
          }else{
              return "用户已经存在";
          }
        }
    }

    public UserEntity getUserInfo(String token) {
        if (StringUtils.isEmpty(token)){
            return null;
        }
        String userMd = redisApi.getString(RecommendUtils.getKey(RedisKeys.USER_TOKEN, token));
        if (StringUtils.isEmpty(userMd)){
            return null;
        }
        return userRepository.findByUserMd(userMd);
    }

    // 登录后需要前端设置header
    public String login(String userName, String password) {
        UserEntity user = userRepository.findByUserNickName(userName);
        if (user == null){
            return "";
        }
        if (user.getPassword().equals(Md5EncryptionHelper.getMD5WithSalt(password))){
            String token = RecommendUtils.genToken();
            redisApi.setValue(RecommendUtils.getKey(RedisKeys.USER_TOKEN, token) , user.getUserMd(), 7, TimeUnit.DAYS);
            return token;
        } else {
            return "";
        }
    }

    // 上传并设置用户头像
    public String uploadAvatar(String userMd, MultipartFile avatar) {
        String name = RecommendUtils.getKey(UserConstant.USER_AVATAR, userMd);
        String url = qiNiuService.uploadPicture(avatar, name);
        UserEntity entity = userRepository.findByUserMd(userMd);
        if (entity == null) {
            return "用户不存在";
        }
        entity.setUserAvatar(url);
        userRepository.save(entity);
        return url;
    }

    // 退出
    public boolean logout(){
        return true;
    }
}
