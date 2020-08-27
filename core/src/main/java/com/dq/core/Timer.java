package com.dq.core;

import com.dq.model.Bucket;
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

    public void polling() {
        List<Bucket> list = this.bucket.getBucket("");
        for (Bucket bucket : list) {
            long now = System.currentTimeMillis();
            if (bucket.getAbsTime() >= now) {
                Job job = jobPool.getJob(bucket.getJobId());
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
