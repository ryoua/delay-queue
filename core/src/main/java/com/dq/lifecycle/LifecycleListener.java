package com.dq.lifecycle;

/**
 * * @Author: RyouA
 * * @Date: 2020/8/30
 **/
public interface LifecycleListener {

    /**
     * 触发事件
     * @param event
     */
    public void lifecycleEvent(LifecycleEvent event);

}
