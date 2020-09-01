package com.dq.controller;

import com.dq.core.DelayBucket;
import com.dq.core.JobPool;
import com.dq.model.Job;
import com.dq.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/job/{id}")
    public Result deleteJob(@PathVariable("id") String id) {
        boolean isSuccess = jobPool.deleteJob(id);
        if (isSuccess) {
            return new Result(200, "删除成功");
        } else {
            return new Result(500, "发生异常, 删除失败, 重试中!");
        }
    }

    @DeleteMapping("/topic/job/{topic}")
    public Result deleteTopicJob(@PathVariable("topic") String topic) {
        jobPool.deleteJob(topic);
        return Result.SUCCESS();
    }

    @GetMapping("/topic/{topic}")
    public Result getTopicJob(@PathVariable("topic") String topic) {
        jobPool.getTopicJob(topic);
        return Result.SUCCESS();
    }
}
