package com.srv.sumit.cachingredisdemo.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CachingConfig {
    private static final String REDIS_URL = "redis://127.0.0.1:6379";
    @Bean
    public RedissonClient getRedissonClient() {
        return Redisson.create(getSingleServerLocalConfig());
    }

    private static Config getSingleServerLocalConfig() {
        Config config = new Config();
        config.useSingleServer().setAddress(REDIS_URL);
        return config;
    }
}
