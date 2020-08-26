package com.dq.controller;

import com.dq.core.DelayBucket;
import com.dq.core.JobPool;
import com.dq.model.Job;
import com.dq.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * * @Author: RyouA
 * * @Date: 2020/8/26
 **/
@RestController
@Slf4j
@RequestMapping("/dq")
public class DelayQueueController {
    @Autowired
    private JobPool jobPool;

    @Autowired
    private DelayBucket delayBucket;

    @PostMapping("/job")
    public Result addJob(@RequestBody Job job) {
        jobPool.addJob(job);
        delayBucket.addBucket(job);
        return Result.SUCCESS();
    }
}
