package com.dq.lifecycle;


/**
 * * @Author: RyouA
 * * @Date: 2020/8/30
 **/
public interface Lifecycle {

    public static final String READY = "ready";

    public static final String DELAY = "delay";

    public static final String RESERVED = "reserved";

    public static final String FINISHED = "finished";

    public void addLifecycleListener(LifecycleListener listener);

    public LifecycleListener[] findLifecycleListeners();

    public void removeLifecycleListener(LifecycleListener listener);

    public void init() throws LifecycleException;

    public void start() throws LifecycleException;

    public void stop() throws LifecycleException;

    public void destroy() throws LifecycleException;

    public LifecycleState getState();

    public String getStateName();

    public interface SingleUse {
    }
}
