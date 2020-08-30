package com.dq.lifecycle;

import java.util.EventObject;

/**
 * * @Author: RyouA
 * * @Date: 2020/8/30
 **/
public final class LifecycleEvent extends EventObject {

    private static final long serialVersionUID = 1L;

    public LifecycleEvent(Lifecycle lifecycle, String type, Object data) {
        super(lifecycle);
        this.type = type;
        this.data = data;
    }

    private final Object data;

    private final String type;

    public Object getData() {
        return data;
    }

    public Lifecycle getLifecycle() {
        return (Lifecycle) getSource();
    }

    public String getType() {
        return this.type;
    }
}
