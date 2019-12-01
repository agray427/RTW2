package com.agray427.rewardsthatwork.model.ui;

import androidx.annotation.NonNull;

import com.agray427.rewardsthatwork.model.common.Entity;
import com.agray427.rewardsthatwork.model.domain.CompletedTaskReward;
import com.agray427.rewardsthatwork.model.domain.TaskReward;

public final class CompletedTaskRewardItem {
    private final String id;
    private final String name;
    private final String quantity;
    private final String points;

    public CompletedTaskRewardItem(@NonNull Entity<CompletedTaskReward> entity) {
        this (entity.getId(), entity.getData());
    }

    public CompletedTaskRewardItem(@NonNull String id, @NonNull CompletedTaskReward data) {
        TaskReward taskRewardData = data.getEntity().getData();
        this.id = id;
        this.name = taskRewardData.getName();
        this.quantity = String.valueOf(data.getQuantity());
        this.points = String.valueOf(data.getQuantity() * taskRewardData.getPoints());
    }

    public CompletedTaskRewardItem(@NonNull String id, @NonNull String name, @NonNull String quantity, @NonNull String points) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.points = points;
    }

    @NonNull
    public String getId() {
        return name;
    }

    @NonNull
    public String getFormattedName() {
        return String.format("%s x%s", name, quantity);
    }

    @NonNull
    public String getPoints() {
        return points;
    }
}
