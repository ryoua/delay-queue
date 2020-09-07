package com.dq.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * * @Author: RyouA
 * * @Date: 2020/8/30
 **/
public abstract class BaseTimer implements Timer {
    protected static final Logger log = LoggerFactory.getLogger(DelayBucket.class);

    @Override
    public void init() {
        log.info(Thread.currentThread().getName() + "No." + Thread.currentThread().getId() + ": Timer start ");
    }

    @Override
    public void destroy() {
        log.info(Thread.currentThread().getName() + "No." + Thread.currentThread().getId() + ": Timer destroy ");
    }
}
