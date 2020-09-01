package com.dq.bootstrap;

import com.dq.controller.JobController;
import com.dq.model.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.UUID;

/**
 * * @Author: RyouA
 * * @Date: 2020/8/30
 **/
@Component
public class Test {
    @Autowired
    JobController jobController;

    public void test() throws Exception {
        Long start = System.currentTimeMillis();
        for (int i = 0; i < 200000; i++) {
            Job job = new Job();
            job.setId(UUID.randomUUID().toString());
            job.setTopic("test");
            job.setDelay(0L);
            job.setTtr(10L);
            job.setBody("test");
            jobController.addJob(job);
        }
        Long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
