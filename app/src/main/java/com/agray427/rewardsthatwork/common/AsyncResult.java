package com.agray427.rewardsthatwork.common;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public final class AsyncResult<T> {
    private T result;
    private String error;
    private boolean isComplete;
    private boolean isSuccessful;
    private final List<OnAsyncResultListener<T>> listeners;

    public AsyncResult() {
        this.result = null;
        this.error = null;
        this.isComplete = false;
        this.isSuccessful = false;
        this.listeners = new ArrayList<>();
    }

    @Nullable
    public T getResult() {
        return result;
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

    public void addListener(@NonNull OnAsyncResultListener<T> listener) {
        synchronized (listeners) {
            if (!listeners.contains(listener)) {
                if (isComplete) {
                    if (isSuccessful && result != null) {
                        listener.onResult(result);
                    } else {
                        listener.onError(error);
                    }
                }
                listeners.add(listener);
            }
        }
    }

    public void removeListener(@NonNull OnAsyncResultListener<T> listener) {
        synchronized (listeners) {
            int index = listeners.indexOf(listener);
            if (index >= 0) {
                listeners.remove(index);
            }
        }
    }

    public void removeAllListeners() {
        synchronized (listeners) {
            listeners.clear();
        }
    }

    public void setError(@Nullable String error) {
        this.error = error;
        this.isComplete = true;
        if (listeners.size() > 0) {
            for (OnAsyncResultListener<T> listener : listeners) {
                if (listener != null) {
                    listener.onError(error);
                }
            }
        }
    }

    public void setResult(@NonNull T result) {
        this.result = result;
        this.isComplete = true;
        this.isSuccessful = true;
        if (listeners.size() > 0) {
            for (OnAsyncResultListener<T> listener : listeners) {
                if (listener != null) {
                    listener.onResult(result);
                }
            }
        }
    }
}
