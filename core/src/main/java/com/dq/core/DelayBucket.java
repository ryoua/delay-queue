package com.dq.core.delayBucket;

import com.dq.model.BucketJob;
import com.dq.model.Job;
import com.dq.utils.RedisUtil;
import com.dq.utils.StringManager;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.*;


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

    private static final String DELAY_QUEUE_BUCKET = "dq:bucket:";

    private static final List<String> topics = new ArrayList<>();

    private static final Logger log = LoggerFactory.getLogger(DelayBucket.class);

    private static final StringManager sm = StringManager.getManager(DelayBucket.class);

    private static long now = System.currentTimeMillis();

    public void addJobToBucket(Job job) {
        long absTime = System.currentTimeMillis() + job.getDelay() * 1000;
        job.setAbsTime(absTime);
        redisUtil.zAdd(DELAY_QUEUE_BUCKET + job.getTopic(), job.getId(), absTime);
        log.info(sm.getString("bucket.addJob"));
    }

    public void addJobsToBucket(List<Job> jobs) {
        List<String> topicList = new ArrayList<>();
        Map<String, Set<ZSetOperations.TypedTuple<String>>> map = new HashMap<>();
        for (Job job : jobs) {
            long absTime = System.currentTimeMillis() + job.getDelay() * 1000;
            job.setAbsTime(absTime);
            if (map.containsKey(job.getTopic())) {
                Set<ZSetOperations.TypedTuple<String>> typedTuples = map.get(job.getTopic());
                ZSetOperations.TypedTuple<String> typedTuple = new DefaultTypedTuple<>(job.getId(), (double) job.getAbsTime());
                typedTuples.add(typedTuple);
                map.put(job.getTopic(), typedTuples);
            } else {
                Set<ZSetOperations.TypedTuple<String>> typedTuples = new HashSet<>();
                ZSetOperations.TypedTuple<String> typedTuple = new DefaultTypedTuple<>(job.getId(), (double) job.getAbsTime());
                typedTuples.add(typedTuple);
                map.put(job.getTopic(), typedTuples);
                topicList.add(job.getTopic());
            }
        }
        for (String topic : topicList) {
            redisUtil.zAdd(DELAY_QUEUE_BUCKET + topic, map.get(topic));
        }
    }

    public List<BucketJob> getBucketJobs(String topic) {
        now = System.currentTimeMillis();
        Set<ZSetOperations.TypedTuple<String>> typedTuples =
                redisUtil.zRangeWithScores(DELAY_QUEUE_BUCKET + topic, now - 10000, now);
        List<BucketJob> result = new ArrayList<>();
        for (ZSetOperations.TypedTuple typedTuple : typedTuples) {
            BucketJob jobBucket = new BucketJob();
            jobBucket.setAbsTime(typedTuple.getScore());
            jobBucket.setJobId((String) typedTuple.getValue());
            result.add(jobBucket);
        }
        return result;
    }

    public void deleteBucketJobs(String topic) {
        redisUtil.zRemoveRange(DELAY_QUEUE_BUCKET + topics, now - 10000, now);
    }
}
