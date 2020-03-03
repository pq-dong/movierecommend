/**
 * @(#)RedisService.java, 2019-03-12.
 *
 * Copyright 2019 Youdao, Inc. All rights reserved.
 * YOUDAO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package pqdong.movie.recommend.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * RedisService
 *
 * @author pqdong
 * @since 2020/03/04
 */
@Service
@Slf4j
public class RedisApi {

    @Resource(name = "stringRedisTemplate")
    private StringRedisTemplate redis;

    public boolean exist(String key) {
        try {
            return Objects.requireNonNull(redis.hasKey(key));
        } catch (Exception e) {
            log.warn("redis exist error key={}", key, e);
            return false;
        }
    }

    public void expire(String key, long time, TimeUnit timeUnit) {
        try {
            redis.expire(key, time, timeUnit);
        } catch (Exception e) {
            log.warn("redis expire error key={}", key, e);
        }
    }

    public void delKey(String key) {
        try {
            redis.delete(key);
        } catch (Exception e) {
            log.warn("redis delKey error key={}", key, e);
        }
    }

    public void delKeys(List<String> keys) {
        try {
            redis.delete(keys);
        } catch (Exception e) {
            log.warn("redis delKeys error keys={}", keys, e);
        }
    }


    public String getString(String key) {
        try {
            return redis.opsForValue().get(key);
        } catch (Exception e) {
            log.warn("redis getString error key={}", key, e);
            return null;
        }
    }

    public void publish(String key, String message) {
        try {
            redis.convertAndSend(key, message);
        } catch (Exception e) {
            log.warn("redis publish error key={}, message={}", key, message, e);
        }
    }


    public void setValue(String key, String value, long time, TimeUnit unit) {
        try {
            redis.opsForValue().set(key, value, time, unit);
        } catch (Exception e) {
            log.warn("redis setValue error key={}, value={}", key, value, e);
        }
    }

    public void setValue(String key, String value, long time) {
        try {
            redis.executePipelined((RedisCallback) connection -> {
                connection.openPipeline();
                redis.opsForValue().set(key, value);
                redis.expireAt(key, new Date(time));
                connection.closePipeline();
                return null;
            });
        } catch (Exception e) {
            log.warn("redis setValue error key={}, value={}", key, value, e);
        }
    }


}