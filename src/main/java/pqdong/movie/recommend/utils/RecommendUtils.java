package pqdong.movie.recommend.utils;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Joiner;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import pqdong.movie.recommend.data.dto.UserInfo;

/**
 * RecommendUtils
 * 工具类，单例模式
 * @author pqdong
 * @since 2020/03/03
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RecommendUtils {

    public static final String SPLIT = ":";

    public static final Joiner JOINER = Joiner.on(SPLIT);

    public static UserInfo getUser(HttpServletRequest request) {
        try {
            String userHeader = request.getHeader("user");
            if (StringUtils.isNotEmpty(userHeader)) {
                // 中文需要解码
                String user = URLDecoder.decode(userHeader, "UTF-8");
                if (StringUtils.isNotEmpty(user)) {
                    return JSONObject.parseObject(user, UserInfo.class);
                }
            }
        } catch (UnsupportedEncodingException e) {
            log.warn("getUser decode error");
        }
        return null;
    }

    public static String getUserMd(HttpServletRequest request) {
        UserInfo user = getUser(request);
        if (user == null) {
            return null;
        }
        return user.getUserMd();
    }

    /**
     * 生成token
     **/
    public static String genToken() {
        return RandomStringUtils.randomAlphanumeric(10);
    }
    /**
     * 获取token
     */
    public static String getToken(HttpServletRequest request) {
        try {
            String tokenHeader = request.getHeader("token");
            if (StringUtils.isNotEmpty(tokenHeader)) {
                String token = URLDecoder.decode(tokenHeader, "UTF-8");
                if (StringUtils.isNotEmpty(token)) {
                    return token;
                }
            }
        } catch (UnsupportedEncodingException e) {
            log.warn("getToken decode error");
        }
        return null;
    }

    public static String getKey(String... keys) {
        return JOINER.join(keys);
    }

    private static Random random = new Random();

    /**
     * 生成随机过期时间，防止缓存雪崩
     */
    public static long genExpireTime(long time, int bound) {
        return time + random.nextInt(bound);
    }

    public static long genExpireTime(long time) {
        return genExpireTime(time, 3);
    }


    /**
     * 对Map按值排序,reverse为true代表从大到小排序，反之从小到大
     * Map的value必须实现Comparable，否则无法实现排序
     **/
    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> sortByValue(Map<K, V> map, final boolean reverse) {
        List<Map.Entry<K, V>> list = new LinkedList<>(map.entrySet());
        list.sort((o1, o2) -> {
            if (reverse) {
                return ((Comparable<V>) o2.getValue())
                        .compareTo(o1.getValue());
            }
            return ((Comparable<V>) o1.getValue())
                    .compareTo(o2.getValue());
        });
        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    private static final String[] AVATARURLARRAY = new String[] {
            "http://oimagec6.ydstatic.com/image?id=-4541055657611236390&product=bisheng",
            "http://oimageb2.ydstatic.com/image?id=8981431901149412470&product=bisheng",
            "http://oimagea2.ydstatic.com/image?id=-6268572656325873060&product=bisheng",
            "http://oimagea2.ydstatic.com/image?id=-38385107928742692&product=bisheng",
            "http://oimageb4.ydstatic.com/image?id=3484504410139022595&product=bisheng"
    };

    /**
     * 获取随机头像
     **/
    public static String getRandomAvatar(String userId) {
        int h = userId.hashCode();
        h = h < 0 ? -h : h;
        return AVATARURLARRAY[h % AVATARURLARRAY.length];
    }

    public static Pair<Integer, Integer> getStartAndEnd(int page, int size) {
        int start = (page - 1) * size;
        int end = start + size;
        return Pair.of(start, end);
    }

}