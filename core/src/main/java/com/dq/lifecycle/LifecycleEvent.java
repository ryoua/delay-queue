package com.dq.lifecycle;

import java.util.EventObject;

/**
 * * @Author: RyouA
 * * @Date: 2020/8/30
 **/
public final class LifecycleEvent
        extends EventObject {

    // ----------------------------------------------------------- Constructors

    public LifecycleEvent(Lifecycle lifecycle, String type) {
        this(lifecycle, type, null);
    }

    public LifecycleEvent(Lifecycle lifecycle, String type, Object data) {
        super(lifecycle);
        this.lifecycle = lifecycle;
        this.type = type;
        this.data = data;
    }

    // ----------------------------------------------------- Instance Variables

    private Object data = null;

    private Lifecycle lifecycle = null;

    private String type = null;

    // ------------------------------------------------------------- Properties

    public Object getData() {
        return (this.data);
    }

    public Lifecycle getLifecycle() {
        return (this.lifecycle);
    }

    public String getType() {
        return (this.type);
    }

}

