package com.dq.core.timer.poll;

import com.dq.core.DelayBucket;
import com.dq.core.JobPool;
import com.dq.core.ReadyQueue;
import com.dq.core.timer.BaseTimer;
import com.dq.model.BucketJob;
import com.dq.holder.ApplicationContextHolder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * * @Author: RyouA
 * * @Date: 2020/8/26
 **/
@Component
public class Timer extends BaseTimer {
    @Override
    public void run() {
        scan();
    }

    @Override
    public void scan() {
        DelayBucket bucket = (DelayBucket) ApplicationContextHolder.getBeanByType(DelayBucket.class);
        JobPool jobPool = (JobPool) ApplicationContextHolder.getBeanByType(JobPool.class);
        ReadyQueue readyQueue = (ReadyQueue) ApplicationContextHolder.getBeanByType(ReadyQueue.class);

        Integer atomicInteger = 0;

        boolean flag = true;
        System.out.println(new Date());
        System.out.println(System.currentTimeMillis());
        while (flag) {
            long now = System.currentTimeMillis();
            List<BucketJob> jobList = bucket.getBucketJobs("test");
            for (BucketJob job : jobList) {
                if (job.getAbsTime() <= now) {
                    if (readyQueue.addJobToReadyQueue(job)) {
                        atomicInteger++;
                        System.out.println("put " + atomicInteger);
                    }
                    jobPool.deleteJob(job.getJobId());
                }
            }
            System.out.println("-----------------------------");
            if (jobList.isEmpty()) {
                flag = false;
            }
            if (atomicInteger == 10000) {
                flag = false;
                System.out.println(new Date());
                break;
            }
        }
    }
}
