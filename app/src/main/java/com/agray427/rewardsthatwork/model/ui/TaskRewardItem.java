package com.agray427.rewardsthatwork.model.ui;

import androidx.annotation.NonNull;

import com.agray427.rewardsthatwork.model.common.Entity;
import com.agray427.rewardsthatwork.model.domain.TaskReward;

public final class TaskRewardItem {
    private final String id;
    private final String name;
    private final String points;

    public TaskRewardItem(@NonNull Entity<TaskReward> entity) {
        this(entity.getId(), entity.getData());
    }

    public TaskRewardItem(@NonNull String id, @NonNull TaskReward data) {
        this.id = id;
        this.name = data.getName();
        this.points = String.valueOf(data.getPoints());
    }

    public TaskRewardItem(@NonNull String id, @NonNull String name, @NonNull String points) {
        this.id = id;
        this.name = name;
        this.points = points;
    }

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getPoints() {
        return points;
    }
}
