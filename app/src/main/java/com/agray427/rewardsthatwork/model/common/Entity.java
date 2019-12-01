package com.agray427.rewardsthatwork.model.common;

import androidx.annotation.NonNull;

public final class Entity<T> {
    private final String id;
    private final T data;

    public Entity(@NonNull String id, @NonNull T data) {
        this.id = id;
        this.data = data;
    }

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public T getData() {
        return data;
    }
}