package xyz.txcplus.redis.lock;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author: TXC
 * @date: 2020-06-30 16:28
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class RedisToolTest {


    @Test
    void tryGetDistributedLock() {
        Jedis jedis = new Jedis();
        RedisTool.tryGetDistributedLock(jedis,"name","name",120);
    }


}