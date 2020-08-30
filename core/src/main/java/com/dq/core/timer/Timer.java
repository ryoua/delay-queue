package com.dq.core.timer;

import com.dq.core.DelayBucket;
import com.dq.core.JobPool;
import com.dq.model.JobBucket;
import com.dq.model.Job;
import com.dq.model.JobStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * * @Author: RyouA
 * * @Date: 2020/8/26
 **/
@Component
public class Timer {
    @Autowired
    private DelayBucket bucket;
    @Autowired
    JobPool jobPool;

    // TODO: notify / wait实现?
    public void polling() {
        List<JobBucket> list = this.bucket.getBucket("");
        for (JobBucket jobBucket : list) {
            long now = System.currentTimeMillis();
            if (jobBucket.getAbsTime() >= now) {
                Job job = jobPool.getJob(jobBucket.getJobId());
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
}
