package xyz.txcplus.ehcachedemo.sercive.impl;

import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import org.springframework.stereotype.Service;
import xyz.txcplus.ehcachedemo.sercive.CacheService;

/**
 * ClassName: CacheServiceImpl
 * Description:
 * date: 2020/10/22 14:50
 *
 * @author TXC
 */
@Service
public class CacheServiceImpl implements CacheService {

    @Autowired
    private CacheManager cacheManager;

    public void put(String cacheName,String key,Object value){

        try {
            Cache cache = cacheManager.getCache(cacheName);
            Element element = new Element(key, value);
            cache.put(element);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Object get(String cacheName,String key){
        try {
            Cache cache = cacheManager.getCache(cacheName);
            Element element = cache.get(key);
            return element == null ? null : element.getObjectValue();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public void delete(String cacheName,String key) {
        try {
            Cache cache = cacheManager.getCache(cacheName);
            cache.remove(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
