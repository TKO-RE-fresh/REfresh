package tko.refresh.util.jwt.redis;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RedisTokenRepository {
    private final RedisTemplate<String, Object> redisTemplate;

    public RedisTokenRepository(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setValues(String key, String field ,String data) {
        redisTemplate.expire(key, 7776000, TimeUnit.SECONDS);
        HashOperations<String, Object, Object> values = redisTemplate.opsForHash();
        values.put(key, field , data);
    }


    public Object getValues(String key, String field) {
        HashOperations<String, Object, Object> values = redisTemplate.opsForHash();
        return values.get(key, field);
    }

    public void deleteValues(String key) {
        redisTemplate.delete(key);
    }
}
