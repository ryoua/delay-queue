package com.dq.core;

import com.dq.core.DelayBucket;
import com.dq.core.JobPool;
import com.dq.core.ReadyQueue;
import com.dq.core.BaseTimer;
import com.dq.holder.ApplicationContextHolder;
import com.dq.model.BucketJob;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * * @Author: RyouA
 * * @Date: 2020/9/1
 **/
@Component
public class BatchTimer extends BaseTimer implements Runnable {
    @Override
    public void scan() {
        DelayBucket bucket = (DelayBucket) ApplicationContextHolder.getBeanByType(DelayBucket.class);
        JobPool jobPool = (JobPool) ApplicationContextHolder.getBeanByType(JobPool.class);
        ReadyQueue readyQueue = (ReadyQueue) ApplicationContextHolder.getBeanByType(ReadyQueue.class);

        List<String> delayList = new ArrayList<>();
        System.out.println(new Date());
        System.out.println(System.currentTimeMillis());
        while (true) {
            long now = System.currentTimeMillis();
            List<BucketJob> bucketJobList = bucket.getBucketJobs("test");
            for (BucketJob job : bucketJobList) {
                if (job.getAbsTime() <= now) {
                    delayList.add(job.getJobId());
                }
            }
            bucket.deleteBucketJobs("test");
            List<String> jobs = jobPool.getJobs(delayList);
            readyQueue.addJobsToReadyQueue(jobs);
            jobPool.deleteJobs(delayList);
            System.out.println(new Date());
        }
    }

    @Override
    public void run() {
        scan();
    }
}
