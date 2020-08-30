package com.dq.core.timer;

/**
 * * @Author: RyouA
 * * @Date: 2020/8/30
 **/
public interface Timer extends Runnable {
    void init();

    void scan();

    void destroy();
}
