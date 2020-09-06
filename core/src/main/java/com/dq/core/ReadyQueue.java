package com.dq.core;

import com.dq.model.BucketJob;
import com.dq.model.Job;
import com.dq.utils.CollectionUtil;
import com.dq.utils.RedisUtil;
import com.dq.utils.StringUtil;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

        if (StringUtil.isNull(jobStr) || StringUtils.isBlank(jobStr)) {
            return false;
        }

        redisUtil.lLeftPush(DELAY_QUEUE_READY_QUEUE, jobStr);
        return true;
    }

    public boolean addJobsToReadyQueue(List<String> jobs) {
        int index = 0;
        int length = jobs.size();
        List<List<String>> lists = new ArrayList<>();
        List<String> data = new ArrayList<>();

        for (int i = 0; i < length; i++) {
            if (index == 10000) {
                lists.add(data);
                data.clear();
                index = 0;
            } else {
                data.add(jobs.get(i));
                index++;
            }
        }
        for (List list : lists) {
            list = CollectionUtil.removeNullElement(jobs);
            redisUtil.lLeftPushAll(DELAY_QUEUE_READY_QUEUE, list);
        }
        return true;
    }
}
