package pqdong.movie.recommend.service;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.google.gson.internal.$Gson$Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pqdong.movie.recommend.data.constant.UserConstant;
import pqdong.movie.recommend.data.entity.ConfigEntity;
import pqdong.movie.recommend.data.repository.ConfigRepository;
import pqdong.movie.recommend.exception.MyException;
import pqdong.movie.recommend.exception.ResultEnum;
import pqdong.movie.recommend.redis.RedisApi;
import pqdong.movie.recommend.utils.RecommendUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * SmsService
 *
 * @author pqdong
 * @since 2020/03/04
 */
@Service
@Slf4j
public class SmsService {

    @Resource
    private RedisApi redis;

    @Resource
    private ConfigService configService;

    /**
     * 阿里短信
     **/

    private IAcsClient getAcsClient() throws ClientException {
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", configService.getConfigValue("ACCESS_KEY_ID"), configService.getConfigValue("ACCESS_KEY_SECRET"));
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", configService.getConfigValue("PRODUCT"), configService.getConfigValue("DOMAIN"));
        return new DefaultAcsClient(profile);
    }

    public String sendCode(String phone) {
        String randomCode = RandomStringUtils.randomNumeric(6);
        if (sendSms(phone, randomCode)) {
            // 有效期一天
            redis.setValue(RecommendUtils.getKey(UserConstant.PHONE_CODE, phone), randomCode, 1, TimeUnit.DAYS);
            return randomCode;
        }
        return null;
    }

    private boolean sendSms(String phoneNumber, String randomCode) {
        SendSmsRequest request = new SendSmsRequest();
        request.setMethod(MethodType.POST);
        request.setPhoneNumbers(phoneNumber);
        request.setSignName("花瓣电影");
        request.setTemplateCode("SMS_185231476");
        request.setTemplateParam("{\"code\":\"" + randomCode + "\"}");
        try {
            SendSmsResponse sendSmsResponse = getAcsClient().getAcsResponse(request);
            if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals(UserConstant.OK)) {
                log.info("发送短信成功");
                return true;
            }
            log.error(sendSmsResponse.getCode());
        } catch (ClientException e) {
            log.error("ClientException异常：" + e.getMessage());
            throw new MyException(ResultEnum.SEND_NOTE_ERROR);
        }
        log.error("发送短信失败");
        return false;
    }
}