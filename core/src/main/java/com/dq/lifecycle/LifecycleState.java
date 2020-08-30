package com.dq.lifecycle;


/**
 * * @Author: RyouA
 * * @Date: 2020/8/30
 **/
public enum LifecycleState {
    NEW(false, null),
    DEALY(false,Lifecycle.DELAY),
    RESERVED(false, Lifecycle.RESERVED),
    FINISHED(false, Lifecycle.FINISHED),
    DELETED(false, null);

    private final boolean available;
    private final String lifecycleEvent;

    private LifecycleState(boolean available, String lifecycleEvent) {
        this.available = available;
        this.lifecycleEvent = lifecycleEvent;
    }

    public boolean isAvailable() {
        return available;
    }

    public String getLifecycleEvent() {
        return lifecycleEvent;
    }
}
