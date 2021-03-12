package xyz.txcplus.redis.aop.lock.annotation;



import xyz.txcplus.redis.aop.lock.FailureHandleType;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 锁定资源对象
 *
 * @version 1.0.0
 * @author: wenhai
 * @date:2019-05-29 14:33
 * @since JDK 1.8
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LockResource {

    /**
     * 加锁的资源名称
     * 默认包名称+方法名称
     */
    String resourceName() default "";

    /**
     * 加锁解锁的key 支持SpEL表达式
     * 默认所有参数的md5值
     */
    String key() default "";


    /**
     * 过期时间
     * 小于0不设置过期时间
     */
    long expire() default -1L;

    /**
     * 获取锁等待时间
     * 小于0永远等待，直到获取到锁
     * 等于0不等待立即返回结果
     * 大于0等待指定时间，如果在指定时间内未获取到锁 则配合{@link FailureHandleType}
     *
     * @return
     */
    long waitTime() default -1L;


    /**
     * 获取锁失败处理类型 默认抛出异常
     *
     * @return
     */
    FailureHandleType failureHandleType() default FailureHandleType.ERROR;

    /**
     * 锁定时长单位
     *
     * @return
     */
    TimeUnit unit() default TimeUnit.SECONDS;
}
