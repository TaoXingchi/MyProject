package xyz.txcplus.ehcachedemo.sercive;

/**
 * ClassName: CacheService
 * Description:
 * date: 2020/10/22 14:50
 *
 * @author TXC
 */
public interface CacheService {

    void put(String cacheName,String key,Object value);

    Object get(String cacheName,String key);

    void delete(String cacheName,String key);
}
