package com.srv.sumit.cachingredisdemo.service;

import com.srv.sumit.cachingredisdemo.model.Employee;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RKeys;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ObjectHolderService {
    private static final String DELIMITER = "#";
    @Autowired
    private RedissonClient client;

    public <T> void storeObjectInBucket(String cacheKeyPrefix, String itemKey, T object) {
        log.info("Trying to store object in redis  {}", object);
        RBucket<T> objectBucket = client.getBucket(cacheKeyPrefix + DELIMITER + itemKey, JsonJacksonCodec.INSTANCE);
        objectBucket.set(object);
    }

    public void printAllEmployees() {
        for (String key : getAllEmployeeKeys()) {
            Employee employee = (Employee) client.getBucket(key, JsonJacksonCodec.INSTANCE).get();
            log.info("Employee - {}  {}", key, employee);
        }
    }

    public Iterable<String> getAllEmployeeKeys() {
        RKeys keys = client.getKeys();
        return keys.getKeysByPattern("employee#*");
    }

    public void closeConnection() {
        client.shutdown();
    }
}
