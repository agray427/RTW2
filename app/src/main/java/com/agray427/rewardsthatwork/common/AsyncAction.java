package com.agray427.rewardsthatwork.common;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public final class AsyncAction {
    private String error;
    private boolean isComplete;
    private boolean isSuccessful;
    private final List<OnAsyncActionListener> listeners;

    public AsyncAction() {
        this.error = null;
        this.isComplete = false;
        this.isSuccessful = false;
        this.listeners = new ArrayList<>();
    }

    @Nullable
    public String getError() {
        return error;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public void addListener(@NonNull OnAsyncActionListener listener) {
        synchronized(listeners) {
            if (!listeners.contains(listener)) {
                if (isComplete) {
                    if (isSuccessful) {
                        listener.onAction();
                    } else {
                        listener.onError(error);
                    }
                }
                listeners.add(listener);
            }
        }
    }

    public void removeListener(@NonNull OnAsyncActionListener listener) {
        synchronized(listeners) {
            int index = listeners.indexOf(listener);
            if (index >= 0) {
                listeners.remove(index);
            }
        }
    }

    public void removeAllListeners() {
        synchronized(listeners) {
            listeners.clear();
        }
    }

    public void setError(@Nullable String error) {
        this.error = error;
        this.isComplete = true;
        if (listeners.size() > 0) {
            for (OnAsyncActionListener listener : listeners) {
                if (listener != null) {
                    listener.onError(error);
                }
            }
        }
    }

    public void trigger() {
        this.isComplete = true;
        this.isSuccessful = true;
        if (listeners.size() > 0) {
            for (OnAsyncActionListener listener : listeners) {
                if (listener != null) {
                    listener.onAction();
                }
            }
        }
    }
}
