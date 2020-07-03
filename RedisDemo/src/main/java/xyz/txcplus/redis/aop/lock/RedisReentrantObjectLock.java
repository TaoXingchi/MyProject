package xyz.txcplus.redis.aop.lock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.TimeoutUtils;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * 基于redis的可重入对象锁
 *
 * @version 1.0.0
 * @author: wenhai
 * @date:2019-05-29 11:38
 * @since JDK 1.8
 */

@Component
@Deprecated
public class RedisReentrantObjectLock<C extends Serializable> {

    @Autowired
    private RedisConnection connection;

    private static final JdkSerializationRedisSerializer SERIALIZER = new JdkSerializationRedisSerializer();

    /**
     * 加锁
     *
     * @param key     加锁的对象
     * @param object  锁的使用者
     * @param timeout 过期时间
     * @param unit    时间单位
     * @return true 加锁成功/false 加锁失败
     */
    public boolean lock(String key, C object, long timeout, TimeUnit unit) {
        byte[] serialize = SERIALIZER.serialize(object);
        Boolean success = connection.setNX(key.getBytes(), serialize);
        if (success) {
            // 给锁增加过期时间
            connection.setEx(key.getBytes(), TimeoutUtils.toSeconds(timeout, unit), serialize);
            return true;
        } else {
            byte[] bytes = connection.get(key.getBytes());
            // 验证锁的使用者是否是同一个
            return Arrays.equals(serialize, bytes);
        }
    }


    /**
     * 加锁
     *
     * @param key    加锁的对象
     * @param object 锁的使用者
     * @return true 加锁成功/false 加锁失败
     */
    public boolean lock(String key, C object) {
        byte[] serialize = SERIALIZER.serialize(object);
        Boolean success = connection.setNX(key.getBytes(), serialize);
        if (success) {
            return true;
        } else {
            byte[] bytes = connection.get(key.getBytes());
            // 验证锁的使用者是否是同一个
            return Arrays.equals(serialize, bytes);
        }
    }

    /**
     * 获取锁的使用者
     *
     * @param key key
     * @return
     */
    public C getLockObject(String key) {
        byte[] bytes = connection.get(key.getBytes());
        return (C) SERIALIZER.deserialize(bytes);
    }


    /**
     * 释放锁
     *
     * @param key    key
     * @param object 锁的使用者
     * @return
     */
    public void unlock(String key, C object) {
        Boolean exists = connection.exists(key.getBytes());
        if (exists) {
            byte[] bytes = connection.get(key.getBytes());
            byte[] serialize = SERIALIZER.serialize(object);
            if (Arrays.equals(serialize, bytes)) {
                // 如果锁的使用者是同一个 才释放它
                connection.del(key.getBytes());
            }
        }
    }
}
