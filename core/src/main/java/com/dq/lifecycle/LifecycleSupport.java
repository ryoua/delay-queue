package com.dq.lifecycle;


/**
 * * @Author: RyouA
 * * @Date: 2020/8/31
 **/
public final class LifecycleSupport {

    // ----------------------------------------------------------- Constructors

    public LifecycleSupport(Lifecycle lifecycle) {
        super();
        this.lifecycle = lifecycle;
    }

    // ----------------------------------------------------- Instance Variables

    private Lifecycle lifecycle = null;

    private LifecycleListener[] listeners = new LifecycleListener[0];

    // --------------------------------------------------------- Public Methods

    public void addLifecycleListener(LifecycleListener listener) {
        synchronized (listeners) {
            LifecycleListener[] results =
                    new LifecycleListener[listeners.length + 1];
            System.arraycopy(listeners, 0, results, 0, listeners.length);
            results[listeners.length] = listener;
            listeners = results;
        }
    }

    public LifecycleListener[] findLifecycleListeners() {
        return listeners;
    }

    public void fireLifecycleEvent(String type, Object data) {
        LifecycleEvent event = new LifecycleEvent(lifecycle, type, data);
        LifecycleListener[] interested = null;
        synchronized (listeners) {
            interested = (LifecycleListener[]) listeners.clone();
        }
        for (int i = 0; i < interested.length; i++) {
            interested[i].lifecycleEvent(event);
        }
    }

    public void removeLifecycleListener(org.apache.catalina.LifecycleListener listener) {
        synchronized (listeners) {
            int n = -1;
            for (int i = 0; i < listeners.length; i++) {
                if (listeners[i] == listener) {
                    n = i;
                    break;
                }
            }
            if (n < 0) {
                return;
            }
            LifecycleListener[] results = new LifecycleListener[listeners.length - 1];
            int j = 0;
            for (int i = 0; i < listeners.length; i++) {
                if (i != n) {
                    results[j++] = listeners[i];
                }
            }
            listeners = results;
        }
    }

}
