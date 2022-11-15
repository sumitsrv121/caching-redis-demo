package com.srv.sumit.cachingredisdemo.service;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AtomicLongService {
    @Autowired
    private RedissonClient client;

    private static final String KEY_FOR_LONG_DATA_TYPE = "long#10";

    public void performAtomicOperationOnLongDatatype() {
        RAtomicLong atomicLong = client.getAtomicLong(KEY_FOR_LONG_DATA_TYPE);
        atomicLong.set(100L);
        log.info("The value stored in redis cache {}",atomicLong.get());
        log.info("Atomic operation on increment and getting the value {} ", atomicLong.incrementAndGet());
        log.info("Atomic operation on decrement and getting the value {} ", atomicLong.decrementAndGet());
        log.info("The value stored in redis cache {}",atomicLong.get());
        client.shutdown();
    }
}
