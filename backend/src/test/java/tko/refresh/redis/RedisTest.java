package tko.refresh.redis;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import tko.refresh.repository.redis.RedisRepository;

@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisRepository redisRepository;

    @Test
    @Transactional
    public void redisTest() {
        redisRepository.setValues("test", "test");
        String test = redisRepository.getValues("test");
        Assertions.assertThat(test).isEqualTo("test");

    }
}