package xyz.txcplus.ehcachedemo.sercive.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;
import xyz.txcplus.ehcachedemo.sercive.CacheService;
import xyz.txcplus.ehcachedemo.sercive.DemoService;

/**
 * ClassName: DemoServiceImpl
 * Description:
 * date: 2020/10/22 13:55
 *
 * @author TXC
 */
@Service
public class DemoServiceImpl implements DemoService {

    @Autowired
    private CacheService cacheService;

    @Override
    public String sendCode(String telephone) {
        String code = "123456";
        System.out.println(code);
        cacheService.put("code",telephone,code);
        return code;
    }

    @Override
    public String getCode(String telephone) {
        String code = "null";
        System.out.println("hhhhhhh");
        code = (String) cacheService.get("code",telephone);
        return code;
    }
}
