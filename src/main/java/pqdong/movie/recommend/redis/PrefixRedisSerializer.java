
package pqdong.movie.recommend.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.Assert;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * PrefixRedisSerializer
 *
 * @author pqdong
 * @since 2020/03/04
 */
@Slf4j
public class PrefixRedisSerializer implements RedisSerializer<String> {

    private static String PREFIX = "movie";

    private final Charset charset;

    public PrefixRedisSerializer() {
        this(StandardCharsets.UTF_8);
    }

    public PrefixRedisSerializer(Charset charset) {
        Assert.notNull(charset, "Charset must not be null!");
        this.charset = charset;
    }

    public static void setPrefix(String prefix) {
        PREFIX = prefix;
    }

    @Override
    public String deserialize(byte[] bytes) {
        String saveKey = new String(bytes, charset);
        int indexOf = saveKey.indexOf(PREFIX);
        if (indexOf > 0) {
            log.warn("key缺少前缀");
        } else {
            saveKey = saveKey.substring(indexOf + PREFIX.length() + 1);
        }
        return saveKey;
    }

    @Override
    public byte[] serialize(String string) {
        String key = PREFIX + ":" + string;
        return key.getBytes(charset);
    }
}
