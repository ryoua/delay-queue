package com.dq.core.timer.poll;

import com.dq.core.DelayBucket;
import com.dq.core.JobPool;
import com.dq.model.JobBucket;
import com.dq.model.Job;
import com.dq.model.JobStatus;
import com.dq.holder.ApplicationContextHolder;
import com.dq.utils.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * * @Author: RyouA
 * * @Date: 2020/8/26
 **/
@Component
public class Timer implements Runnable {
    public void polling() {
        DelayBucket bucket = (DelayBucket) ApplicationContextHolder.getBeanByType(DelayBucket.class);
        JobPool jobPool = (JobPool) ApplicationContextHolder.getBeanByType(JobPool.class);

        List<JobBucket> list = bucket.getBucket("");
        for (JobBucket jobBucket : list) {
            long now = System.currentTimeMillis();
            if (jobBucket.getAbsTime() >= now) {
                Job job = jobPool.getJob(jobBucket.getJobId());
                if (StringUtils.isNull(job)) {
                    continue;
                }
                if (job.getStatus().equals(JobStatus.DELETED)) {
                    continue;
                } else {
                    if (job.getAbsTime() >= now) {

                    } else {
                        // TODO: 重新放入
                    }
                }
            }
        }
    }

    @Override
    public void run() {
        polling();
    }
}
