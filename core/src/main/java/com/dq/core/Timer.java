package com.dq.core.timer;

/**
 * * @Author: RyouA
 * * @Date: 2020/8/30
 **/
public interface Timer extends Runnable {
    /**
     * 扫描器的初始化方法
     */
    void init();

    /**
     * 扫描器的扫描方法
     * 1.while死循环
     * 2.wait/notify
     */
    void scan();

    /**
     * 扫描器的销毁方法
     */
    void destroy();
}
