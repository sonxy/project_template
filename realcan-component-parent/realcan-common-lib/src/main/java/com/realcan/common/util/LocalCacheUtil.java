package com.realcan.common.util;

import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.stereotype.Service;
/**
 * @Author: fei.wu
 * @Email:
 * @CreateDate: 2020/3/11
 * @Version: 1.0
 */
@Service
public class LocalCacheUtil {

    Cache<String,Object> commonCache = null;

    @PostConstruct
    public void init(){
        commonCache = CacheBuilder.newBuilder()
                //初始容量为10
                .initialCapacity(10)
                //设置最多存放100个 key
                .maximumSize(100)
                //设置写缓存后多少秒过期
                .expireAfterWrite(60, TimeUnit.SECONDS)
                .build();
    }

    public void setLocalCache(String key,Object value){
        commonCache.put(key,value);
    }

    public Object getLocalCache(String key){
        return commonCache.getIfPresent(key);
    }
}
