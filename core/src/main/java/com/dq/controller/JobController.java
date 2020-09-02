package com.dq.controller;

import com.dq.core.DelayBucket;
import com.dq.core.JobPool;
import com.dq.model.Job;
import com.dq.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * * 暴露的接口
 * * @Author: RyouA
 * * @Date: 2020/8/26
 **/
@RestController
@RequestMapping("/dq")
public class JobController {
    @Autowired
    private JobPool jobPool;
    @Autowired
    private DelayBucket delayBucket;

    @PostMapping("/job")
    public Result addJob(@RequestBody Job job) {
        jobPool.addJob(job);
        delayBucket.addJobToBucket(job);
        return Result.SUCCESS();
    }

    @PostMapping("/jobs")
    public Result addJobs(@RequestBody List<Job> jobs) {
        jobPool.addJobs(jobs);
        delayBucket.addJobsToBucket(jobs);
        return Result.SUCCESS();
    }

    @DeleteMapping("/job/{id}")
    public Result deleteJob(@PathVariable("id") String id) {
        jobPool.deleteJob(id);
        return Result.SUCCESS();
    }
}
