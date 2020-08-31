package com.dq.core;

import com.dq.model.Job;
import com.dq.utils.RedisUtil;
import com.dq.utils.SnowFlake;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;
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

    private final Queue<Job> retryAddQueue = new ConcurrentLinkedDeque<>();
    private final Queue<String> retryDeleteQueue = new ConcurrentLinkedDeque<>();

    public void addJob(Job job) {
        job.setId(job.getTopic() + "No." + snowFlake.nextId());
        try {
            redisUtil.set(job.getId(), gson.toJson(job));
        } catch (Exception e) {
            this.retryAddQueue.add(job);
            System.out.println("持久化准备重试");
        }
    }

    public void deleteJob(String id) {
        try {
            redisUtil.delete(id);
        } catch (Exception e) {
            this.retryDeleteQueue.add(id);
            System.out.println("持久化准备重试");
        }
    }

    public Job getJob(String id) {
        String job = redisUtil.get(id);
        return gson.fromJson(job, Job.class);
    }

    public String getJobStr(String id) {
        return redisUtil.get(id);
    }

    public void deleteTopic(String topic) {
    }

    public void getTopicJob(String topic) {
    }


}
