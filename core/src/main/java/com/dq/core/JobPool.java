package com.dq.core;

import com.dq.lifecycle.LifecycleEvent;
import com.dq.lifecycle.LifecycleListener;
import com.dq.model.Job;
import com.dq.model.JobStatus;
import com.dq.utils.RedisUtil;
import com.dq.utils.SnowFlake;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * * @Author: RyouA
 * * @Date: 2020/8/26
 **/
@Component
public class JobPool {
    @Autowired
    Gson gson;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    SnowFlake snowFlake;

    public void addJob(Job job) {
        // TODO: 验证是否加入成功, 做持久化?
        job.setId(job.getTopic() + "No." + snowFlake.nextId());
        redisUtil.set(job.getId(), gson.toJson(job));
    }

    public void deleteJob(String id) {
        redisUtil.delete(id);
    }

    public Job getJob(String id) {
        String job = redisUtil.get(id);
        return gson.fromJson(job, Job.class);
    }

    public void deleteTopic(String topic) {
    }

    public void getTopicJob(String topic) {
    }
}
