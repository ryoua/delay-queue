package com.dq.core;

import com.dq.model.Job;
import com.dq.utils.RedisUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * * @Author: RyouA
 * * @Date: 2020/8/26
 **/
@Component
public class DelayBucket {
    @Autowired
    private Gson gson;

    @Autowired
    private RedisUtil redisUtil;

    private static final String DELAY_QUEUE = "dq:";

    public void addBucket(Job job) {
        long absTime = System.currentTimeMillis() + job.getDelay() * 1000;
        job.setAbsTime(absTime);
        redisUtil.zAdd(DELAY_QUEUE + job.getTopic(), job.getId(), absTime);
    }

    public Job[] getBucket(String topic) {
        long now = System.currentTimeMillis();
        Set<String> jobs = redisUtil.zRange(DELAY_QUEUE + topic, now - 5000, now);
        return (Job[]) jobs.toArray();
    }
}
