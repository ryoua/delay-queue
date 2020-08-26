package com.dq.core;

import com.dq.model.Job;
import com.dq.model.JobStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * * @Author: RyouA
 * * @Date: 2020/8/26
 **/
@Component
public class Timer {
    @Autowired
    private DelayBucket bucket;

    public void polling() {

    }
}
