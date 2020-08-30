package com.dq.core;

import com.dq.model.BucketJob;
import com.dq.model.Job;
import com.dq.utils.RedisUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * * @Author: RyouA
 * * @Date: 2020/8/30
 **/
@Component
public class ReadyQueue {
    private static final String DELAY_QUEUE_READY_QUEUE = "dq:ready_queue:";

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private Gson gson;
    @Autowired
    private JobPool jobPool;

    public boolean addJobToReadyQueue(BucketJob bucketJob) {
        Job job = jobPool.getJob(bucketJob.getJobId());
        if (job == null) {
            return false;
        }
        redisUtil.lLeftPush(DELAY_QUEUE_READY_QUEUE, gson.toJson(job));
        return true;
    }
}
