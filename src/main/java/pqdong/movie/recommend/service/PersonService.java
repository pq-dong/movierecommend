package pqdong.movie.recommend.service;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Service;
import pqdong.movie.recommend.data.entity.PersonEntity;
import pqdong.movie.recommend.data.repository.PersonRepository;
import pqdong.movie.recommend.redis.RedisApi;
import pqdong.movie.recommend.redis.RedisKeys;
import pqdong.movie.recommend.utils.RecommendUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class PersonService {

    @Resource
    private RedisApi redisApi;

    @Resource
    private PersonRepository personRepository;
    /**
     * @param key  查询关键字
     * @param page 起始页码
     * @param size 每页数据量
     * @return 总数量和数据列表
     * @method allPerson 获取
     **/
    public Map<String, Object> getAllPerson(String key, int page, int size) {
        Pair<Integer, Integer> pair = RecommendUtils.getStartAndEnd(page, size);
        List<PersonEntity> allPerson = getPersons(key, page*size);
        List<PersonEntity> personList = allPerson.subList(pair.getLeft(), pair.getRight() <= allPerson.size() ? pair.getRight() : allPerson.size());
        Map<String, Object> result = new HashMap<>(2, 1);
        result.put("total", allPerson.size());
        result.put("personList", personList);
        return result;
    }


    private List<PersonEntity> getPersons(String key, int total) {
        List<PersonEntity> personList;
        if (StringUtils.isBlank(key)) {
            personList = personRepository.findAllByCountLimit(total);
        } else {
            personList = personRepository.findAllByName(key);
        }
        return personList;
    }
}
