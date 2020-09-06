package com.dq.core.timer.poll;

import com.dq.core.DelayBucket;
import com.dq.core.JobPool;
import com.dq.core.ReadyQueue;
import com.dq.core.timer.BaseTimer;
import com.dq.model.BucketJob;
import com.dq.holder.ApplicationContextHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * * 单条命令执行版本, QPS在4000左右
 * * @Author: RyouA
 * * @Date: 2020/8/26
 **/
@Component
public class Timer extends BaseTimer implements Runnable {
    @Override
    public void run() {
        scan();
    }

    @Override
    public void scan() {
        DelayBucket bucket = (DelayBucket) ApplicationContextHolder.getBeanByType(DelayBucket.class);
        JobPool jobPool = (JobPool) ApplicationContextHolder.getBeanByType(JobPool.class);
        ReadyQueue readyQueue = (ReadyQueue) ApplicationContextHolder.getBeanByType(ReadyQueue.class);

        boolean flag = true;
        while (flag) {
            long now = System.currentTimeMillis();
            List<BucketJob> jobList = bucket.getBucketJobs("test");
            for (BucketJob job : jobList) {
                if (job.getAbsTime() <= now) {
                    readyQueue.addJobToReadyQueue(job);
                    jobPool.deleteJob(job.getJobId());
                }
            }
            bucket.deleteBucketJobs("test");
        }
    }
}
