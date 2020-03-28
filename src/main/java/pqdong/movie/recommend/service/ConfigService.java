package pqdong.movie.recommend.service;

import org.springframework.stereotype.Service;
import pqdong.movie.recommend.data.repository.ConfigRepository;

import javax.annotation.Resource;

@Service
public class ConfigService {

    @Resource
    private ConfigRepository configRepository;

    // 获取七牛云，阿里云短信 access_token等配置信息
    public String getConfigValue(String key){
        return configRepository.findConfigByKey(key).getValue();
    }
}
