package xyz.txcplus.redis.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author: TXC
 * @date: 2020-06-30 11:17
 */
@Configuration
@EnableAutoConfiguration
public class RedisConfig {

    /*  @Configuration 注解是用于定义配置类，可替换xml配置文件，被注解的类内部包含有一个或多个被@Bean注解的方法，这些方法将会被AnnotationConfigApplicationContext或AnnotationConfigWebApplicationContext类进行扫描，并用于构建bean定义，初始化Spring容器。
        @EnableAutoConfiguration 注解是启用Spring应用程序上下文的自动配置，尝试猜测和配置您可能需要的bean。自动配置类通常基于类路径和定义的bean应用。
        @ConfigurationProperties 注解是用于读取配置文件的信息，在这里是读取配置在yml里的redis的相关配置项。
        @Bean 注解用在方法上，告诉Spring容器，你可以从下面这个方法中拿到一个Bean
    */

    @Bean
    @ConfigurationProperties(prefix = "spring.redis.pool")
    public JedisPoolConfig getRedisConfig(){
        JedisPoolConfig config = new JedisPoolConfig();
        return config;
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.redis")
    public JedisConnectionFactory getConnectionFactory() {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setUsePool(true);
        JedisPoolConfig config = getRedisConfig();
        factory.setPoolConfig(config);
        return factory;
    }

    @Bean
    public RedisTemplate<?, ?> getRedisTemplate() {
        JedisConnectionFactory factory = getConnectionFactory();
        RedisTemplate<?, ?> template = new StringRedisTemplate(factory);
        return template;
    }

}
