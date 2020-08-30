package com.dq.lifecycle;

/**
 * * @Author: RyouA
 * * @Date: 2020/8/30
 **/
public final class LifecycleException extends Exception {

    private static final long serialVersionUID = 1L;

    public LifecycleException() {
        super();
    }

    public LifecycleException(String message) {
        super(message);
    }

    public LifecycleException(Throwable throwable) {
        super(throwable);
    }

    public LifecycleException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
