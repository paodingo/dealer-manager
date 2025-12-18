package com.dealermanager.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfig {

    long accessToken = 100;

    @Bean
    public Cache<String, Object> accessToken() {
        return Caffeine.newBuilder()
                // 设置最后一次写入后经过固定时间过期
                .expireAfterWrite(accessToken, TimeUnit.MINUTES)
                // 初始的缓存空间大小
                .initialCapacity(15000)
                // 缓存的最大条数
                .maximumSize(15000)
                .build();
    }

}
