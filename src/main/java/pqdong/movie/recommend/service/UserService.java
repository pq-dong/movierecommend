package pqdong.movie.recommend.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pqdong.movie.recommend.data.constant.UserConstant;
import pqdong.movie.recommend.data.entity.UserEntity;
import pqdong.movie.recommend.data.repository.UserRepository;
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

    public UserEntity getUserInfo(String userMd) {
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
    public boolean uploadAvatar(String userMd, MultipartFile avatar) {
        String name = RecommendUtils.getKey(UserConstant.USER_AVATAR, userMd);
        String url = qiNiuService.uploadPicture(avatar, name);
        UserEntity entity = userRepository.findByUserMd(userMd);
        if (entity == null) {
            return false;
        }
        entity.setUserAvatar(url);
        userRepository.save(entity);
        return true;
    }
}
