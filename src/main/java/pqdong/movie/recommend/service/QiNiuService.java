package pqdong.movie.recommend.service;

import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pqdong.movie.recommend.data.repository.ConfigRepository;
import pqdong.movie.recommend.exception.MyException;
import pqdong.movie.recommend.exception.ResultEnum;

import javax.annotation.Resource;

/**
 * QiNiuService
 *
 * @author pqdong
 * @since 2020/03/04
 */
@Service
@Slf4j
public class QiNiuService {

    @Autowired
    private ConfigService configService;
    /**
     * 七牛云
     **/
    private Auth auth = Auth.create("", "");
    Configuration cfg = new Configuration(Zone.huadong());
    private UploadManager uploadManager = new UploadManager(cfg);

    private String getUpToken() {
        return auth.uploadToken(configService.getConfigValue("BUCKET_NAME"));
    }

    public String uploadPicture(MultipartFile picture, String name) {
        try {
            Response response = uploadManager.put(picture.getBytes(), name, getUpToken());
            if (response.isOK() && response.isJson()) {
                return configService.getConfigValue("QINIU_IMAGE_DOMAIN") + name;
            }
        } catch (Exception e) {
            log.warn("upload picture error", e);
            throw new MyException(ResultEnum.QINIU_ERROR);
        }
        return configService.getConfigValue("QINIU_IMAGE_DOMAIN") + name;
    }
}