package com.dq.lifecycle;

/**
 * * @Author: RyouA
 * * @Date: 2020/8/30
 **/
public final class LifecycleException extends Exception {

    //------------------------------------------------------------ Constructors

    public LifecycleException() {
        this(null, null);
    }

    public LifecycleException(String message) {
        this(message, null);
    }

    public LifecycleException(Throwable throwable) {
        this(null, throwable);
    }

    public LifecycleException(String message, Throwable throwable) {
        super();
        this.message = message;
        this.throwable = throwable;
    }


    //------------------------------------------------------ Instance Variables

    protected String message = null;

    protected Throwable throwable = null;


    //---------------------------------------------------------- Public Methods

    @Override
    public String getMessage() {
        return (message);
    }

    public Throwable getThrowable() {
        return (throwable);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("LifecycleException:  ");
        if (message != null) {
            sb.append(message);
            if (throwable != null) {
                sb.append(":  ");
            }
        }
        if (throwable != null) {
            sb.append(throwable.toString());
        }
        return (sb.toString());
    }

}