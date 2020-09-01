package com.dq.core;

import com.dq.model.Job;
import com.dq.utils.RedisUtil;
import com.dq.utils.SnowFlake;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * * @Author: RyouA
 * * @Date: 2020/8/26
 **/
@Component
public class JobPool {
    @Autowired
    private Gson gson;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private SnowFlake snowFlake;

    public void addJob(Job job) {
        job.setId(job.getTopic() + "No." + snowFlake.nextId());
        try {
            redisUtil.set(job.getId(), gson.toJson(job));
        } catch (Exception e) {
            System.out.println("持久化准备重试");
        }
    }

    public boolean deleteJob(String id) {
        try {
            redisUtil.delete(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Job getJob(String id) {
        String job = redisUtil.get(id);
        return gson.fromJson(job, Job.class);
    }

    public String getJobStr(String id) {
        return redisUtil.get(id);
    }

    public void deleteAllJobWithTopic(String topic) {
    }

    public void getTopicJob(String topic) {
    }


}
