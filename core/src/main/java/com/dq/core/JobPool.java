package com.dq.core;

import com.dq.model.Job;
import com.dq.utils.RedisUtil;
import com.dq.utils.SnowFlake;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public void addJobs(List<Job> jobs) {
        Map<String, String> map = new HashMap<>();
        for (Job job : jobs) {
            job.setId(job.getTopic() + "No." + snowFlake.nextId());
            map.put(job.getId(), gson.toJson(job));
        }
        redisUtil.multiSet(map);
    }

    public boolean deleteJob(String id) {
        redisUtil.delete(id);
        return true;
    }

    public boolean deleteJobs(List<String> idList) {
        redisUtil.delete(idList);
        return true;
    }

    public String getJob(String id) {
        return redisUtil.get(id);
    }

    public List<String> getJobs(List<String> idList) {
        return redisUtil.multiGet(idList);
    }
}
