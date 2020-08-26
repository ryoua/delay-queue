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
public class JobPool {
    @Autowired
    Gson gson;

    @Autowired
    RedisUtil redisUtil;

    public void addJob(Job job) {
        redisUtil.set(job.getId(), gson.toJson(job));
    }

    public void deleteJob(String id) {
        redisUtil.delete(id);
    }

    public Job getJob(String id) {
        String job = redisUtil.get(id);
        return gson.fromJson(job, Job.class);
    }
}
