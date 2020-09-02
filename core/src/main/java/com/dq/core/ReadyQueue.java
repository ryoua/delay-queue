package com.dq.core;

import com.dq.model.BucketJob;
import com.dq.model.Job;
import com.dq.utils.CollectionUtil;
import com.dq.utils.RedisUtil;
import com.dq.utils.StringUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * * @Author: RyouA
 * * @Date: 2020/8/30
 **/
@Component
public class ReadyQueue {
    private static final String DELAY_QUEUE_READY_QUEUE = "dq:ready_queue";

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private Gson gson;
    @Autowired
    private JobPool jobPool;

    public boolean addJobToReadyQueue(BucketJob bucketJob) {
        String jobStr = jobPool.getJob(bucketJob.getJobId());
        Job job = gson.fromJson(jobStr, Job.class);

        if (StringUtil.isNull(job)) {
            return false;
        }
        redisUtil.lLeftPush(DELAY_QUEUE_READY_QUEUE, gson.toJson(job));
        return true;
    }

    public boolean addJobsToReadyQueue(List<String> jobs) {
        redisUtil.lLeftPushAll(DELAY_QUEUE_READY_QUEUE, CollectionUtil.removeNullElement(jobs));
        return true;
    }
}
