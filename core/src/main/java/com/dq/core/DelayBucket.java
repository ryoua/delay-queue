package com.dq.core;

import com.dq.model.BucketJob;
import com.dq.model.Job;
import com.dq.utils.RedisUtil;
import com.dq.utils.StringManager;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.*;


/**
 * * @Author: RyouA
 * * @Date: 2020/8/26
 **/
public interface DelayBucket {
    /**
     * 添加单个任务到桶中
     * @param job
     */
    void addJobToBucket(Job job);

    /**
     * 添加多个任务到桶中
     * @param jobs
     */
    void addJobsToBucket(List<Job> jobs);

    /**
     * 获取topic下的所有任务
     * @param topic
     * @return
     */
    List<BucketJob> getBucketJobs(String topic);

    /**
     * 删除topic下的所有任务
     * @param topic
     */
    void deleteBucketJobs(String topic);
}
