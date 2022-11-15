package com.srv.sumit.cachingredisdemo.service;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBitSet;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BitSetService {
    /*The RBitSet  interface in Redisson represents a bit vector
    that can expand as necessary, with a maximum size of 4,294,967,295 bits.*/

    @Autowired
    private RedissonClient client;

    public void performOperationsOnBits() {
        RBitSet simpleBitSet = getBitSet("simple_bit_set");
        simpleBitSet.set(0, false);
        /*set(long fromIndex, long toIndex, boolean value)
        Set all bits to value from fromIndex (inclusive) to toIndex (exclusive)*/
        simpleBitSet.set(1, 10, true);

        log.info("The value at index 0, 1 and 9 are {}, {}, {}",
                simpleBitSet.get(0), simpleBitSet.get(1), simpleBitSet.get(9));
        client.shutdown();
    }

    public RBitSet getBitSet(String key) {
        return client.getBitSet(key);
    }
}
