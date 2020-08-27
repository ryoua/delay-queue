package com.dq.core;

import com.dq.model.Bucket;
import com.dq.model.Job;
import com.dq.utils.RedisUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
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

    public List<Bucket> getBucket(String topic) {
        long now = System.currentTimeMillis();
        Set<ZSetOperations.TypedTuple<String>> typedTuples = redisUtil.zRangeWithScores(DELAY_QUEUE + topic, now - 5000, now);
        List<Bucket> result = new ArrayList<>();
        for (ZSetOperations.TypedTuple typedTuple : typedTuples) {
            Bucket bucket = new Bucket();
            bucket.setAbsTime(typedTuple.getScore());
            bucket.setJobId((String) typedTuple.getValue());
            result.add(bucket);
        }
        return result;
    }
}
