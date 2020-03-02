package pqdong.movie.recommend.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pqdong.movie.recommend.data.entity.UserEntity;
import pqdong.movie.recommend.data.repository.UserRepository;
import pqdong.movie.recommend.domain.user.UserInfo;
import pqdong.movie.recommend.utils.Md5EncryptionHelper;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

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

    public UserInfo getUserInfo(String userMd) {
        UserEntity userEntity = userRepository.findByUserMd(userMd);
        UserInfo userInfo = new UserInfo();
        userInfo.setId(userEntity.getId());
        userInfo.setUserAvatar(userEntity.getUserAvatar());
        userInfo.setUserMd(userEntity.getUserMd());
        userInfo.setUserNickName(userEntity.getUserNickName());
        userInfo.setUserTags(userEntity.getUserTags());
        return userInfo;
    }

    public boolean login(String userName, String password) {
        UserEntity user = userRepository.findByUserNickName(userName);
        return user.getPassword().equals(Md5EncryptionHelper.getMD5WithSalt(password));
    }
}
