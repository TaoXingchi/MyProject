package xyz.txcplus.redis.aop.lock.aop;


import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import xyz.txcplus.redis.aop.lock.FailureHandleType;
import xyz.txcplus.redis.aop.lock.LockKeyGenerator;
import xyz.txcplus.redis.aop.lock.annotation.LockResource;

/**
 * 分布式锁aop处理器
 *
 * @version 1.0.0
 * @author: wenhai
 * @date:2019-11-01 10:23
 * @since JDK 1.8
 */
@Slf4j
public class LockInterceptor implements MethodInterceptor {
    @Setter
    private RedissonClient redissonClient;

    private LockKeyGenerator lockKeyGenerator = new LockKeyGenerator();

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        LockResource lockResource = invocation.getMethod().getAnnotation(LockResource.class);
        String keyName = lockKeyGenerator.getKeyName(invocation, lockResource);
        RLock lock = redissonClient.getLock(keyName);
        boolean tryLock;
        log.debug("当前线程:" + Thread.currentThread().getName() + "开始获取锁：" + keyName);
        if (lockResource.waitTime() >= 0 && lockResource.expire() > 0) {
            tryLock = lock.tryLock(lockResource.waitTime(), lockResource.expire(), lockResource.unit());
        } else if (lockResource.waitTime() >= 0) {
            tryLock = lock.tryLock(lockResource.waitTime(), lockResource.unit());
        } else {
            if (lockResource.expire() > 0) {
                lock.lock(lockResource.expire(), lockResource.unit());
            } else {
                lock.lock();
            }
            tryLock = true;
        }
        if (tryLock) {
            try {
                log.debug("当前线程:" + Thread.currentThread().getName() + "获取锁成功：" + keyName);
                return invocation.proceed();
            } finally {
                lock.unlock();
                log.debug("当前线程:" + Thread.currentThread().getName() + "释放锁：" + keyName);
            }
        } else {
            log.debug("当前线程:" + Thread.currentThread().getName() + "获取锁失败：" + keyName);
//            if (FailureHandleType.ERROR.equals(lockResource.failureHandleType())) {
//                throw new RrException(MessageConstant.RESOURCE_PREEMPTED_FAILURE);
//            }
        }
        return null;
    }
}
