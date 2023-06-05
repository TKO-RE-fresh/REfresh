package tko.refresh.redis;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import tko.refresh.util.jwt.redis.RedisTokenRepository;

@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisTokenRepository redisRepository;

    @Test
    @Transactional
    public void redisTest() {
        // 한글 테스트
        redisRepository.setValues("test","memberName", "김민성");
        String test = (String) redisRepository.getValues("test", "memberName");
        Assertions.assertThat(test).isEqualTo("김민성");

    }
}