package xyz.txcplus.redis.aop.lock.config;

import com.daqsoft.lock.aop.LockAnnotationAdvisor;
import com.daqsoft.lock.aop.LockInterceptor;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 资源锁配置
 *
 * @version 1.0.0
 * @author: wenhai
 * @date:2019-11-01 10:52
 * @since JDK 1.8
 */

@Configuration
public class LockConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public LockAnnotationAdvisor lockAnnotationAdvisor(LockInterceptor lockInterceptor) {
        return new LockAnnotationAdvisor(lockInterceptor);
    }

    @Bean
    @ConditionalOnMissingBean
    public LockInterceptor lockInterceptor(RedissonClient redissonClient) {
        LockInterceptor lockInterceptor = new LockInterceptor();
        lockInterceptor.setRedissonClient(redissonClient);
        return lockInterceptor;
    }
}
