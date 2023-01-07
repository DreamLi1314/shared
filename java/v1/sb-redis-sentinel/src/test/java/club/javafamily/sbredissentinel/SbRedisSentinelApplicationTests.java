package club.javafamily.sbredissentinel;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@SpringBootTest
class SbRedisSentinelApplicationTests {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    ValueOperations<String, Object> opsForValue;

    @BeforeEach
    public void init() {
        opsForValue = redisTemplate.opsForValue();
    }

    @Test
    void contextLoads() {
        Assertions.assertNotNull(redisTemplate);
    }

    @Test
    void writeTest() {
        opsForValue.set("tsb2", "springboot2");
    }

    @Test
    void readTest() {
        final String value = (String) opsForValue.get("tsb1");

        System.out.println(value);
    }

}
