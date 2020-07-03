package xyz.txcplus.redis.aop.lock;

/**
 * 获取锁失败处理类型
 *
 * @version 1.0.0
 * @author: wenhai
 * @date:2019-05-31 17:25
 * @since JDK 1.8
 */

public enum FailureHandleType {

    /**
     * 抛出异常
     */
    ERROR,

    /**
     * 继续后续逻辑,不做任何处理 注：加锁方法不会被执行
     */
    CONTINUE
}
