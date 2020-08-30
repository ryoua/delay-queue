package com.dq.core;

import com.dq.model.JobBucket;
import com.dq.model.Job;
import com.dq.utils.RedisUtil;
import com.dq.utils.StringManager;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    // ---------------------- static properties ---------------------- //

    protected static final String DELAY_QUEUE_BUCKET = "dq:bucket:";

    protected static final List<String> topics = new ArrayList<>();

    protected static final Logger log = LoggerFactory.getLogger(DelayBucket.class);

    protected static final StringManager sm = StringManager.getManager(DelayBucket.class);

    // ---------------------- public methods ---------------------- //

    public void addJobToBucket(Job job) {
        long absTime = System.currentTimeMillis() + job.getDelay() * 1000;
        job.setAbsTime(absTime);
        topics.add(job.getTopic());
        redisUtil.zAdd(DELAY_QUEUE_BUCKET + job.getTopic(), job.getId(), absTime);
        log.info(sm.getString("bucket.addJob"));
    }

    public List<JobBucket> getBucket(String topic) {
        long now = System.currentTimeMillis();
        Set<ZSetOperations.TypedTuple<String>> typedTuples =
                redisUtil.zRangeWithScores(DELAY_QUEUE_BUCKET + topic, now - 5000, now);
        List<JobBucket> result = new ArrayList<>();
        for (ZSetOperations.TypedTuple typedTuple : typedTuples) {
            JobBucket jobBucket = new JobBucket();
            jobBucket.setAbsTime(typedTuple.getScore());
            jobBucket.setJobId((String) typedTuple.getValue());
            result.add(jobBucket);
        }
        return result;
    }
}
