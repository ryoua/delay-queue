package com.dq.core;

import com.dq.model.Job;
import com.dq.utils.RedisUtil;
import com.dq.utils.SnowFlake;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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
        redisUtil.set(job.getId(), gson.toJson(job));
    }

    public boolean deleteJob(String id) {
        redisUtil.delete(id);
        return true;
    }

    public boolean deleteJobs(List<String> idList) {
        redisUtil.delete(idList);
        return true;
    }

    public Job getJob(String id) {
        String job = redisUtil.get(id);
        return gson.fromJson(job, Job.class);
    }

    public List<String> getJobs(List<String> idList) {
        return redisUtil.multiGet(idList);
    }

    public String getJobStr(String id) {
        return redisUtil.get(id);
    }

    public void deleteAllJobWithTopic(String topic) {
    }

    public void getTopicJob(String topic) {
    }

}
