package com.dq.core;

import com.dq.model.Job;
import com.dq.utils.RedisUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * * @Author: RyouA
 * * @Date: 2020/8/26
 **/
@Component
public class DelayBucket {
    @Autowired
    Gson gson;

    @Autowired
    RedisUtil redisUtil;

    public void addBucket(Job job) {

    }

    public Job getBucket(String bucketId) {
        return null;
    }
}
