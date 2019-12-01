package com.agray427.rewardsthatwork.model.domain;

import androidx.annotation.NonNull;

import com.agray427.rewardsthatwork.model.common.Entity;

public final class CompletedTaskReward {
    private final Entity<TaskReward> entity;
    private int quantity;

    public CompletedTaskReward(@NonNull Entity<TaskReward> entity, int quantity) {
        this.entity = entity;
        this.quantity = quantity;
    }

    @NonNull
    public Entity<TaskReward> getEntity() {
        return entity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
