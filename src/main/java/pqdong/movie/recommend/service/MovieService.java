package pqdong.movie.recommend.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class MovieService {

    @Resource
    private ConfigService configService;

    public List<String> getMovieTags(){
        return JSON.parseArray(configService.getConfigValue("MOVIE_TAG"),String.class);
    }
}
