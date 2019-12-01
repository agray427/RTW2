package com.agray427.rewardsthatwork.model.ui;

import androidx.annotation.NonNull;

import com.agray427.rewardsthatwork.model.common.Entity;
import com.agray427.rewardsthatwork.model.domain.TaskReward;

public final class TaskRewardData {
    private final String id;
    private final String name;
    private final String description;
    private final String points;

    public TaskRewardData(@NonNull Entity<TaskReward> entity) {
        this(entity.getId(), entity.getData());
    }

    public TaskRewardData(@NonNull String id, @NonNull TaskReward data) {
        this.id = id;
        this.name = data.getName();
        this.description = data.getDescription();
        this.points = String.valueOf(data.getPoints());
    }

    public TaskRewardData(@NonNull String id, @NonNull String name, @NonNull String description, @NonNull String points) {
        this.id = id;
        this.name = name;
        this.description = description;
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
    public String getDescription() {
        return description;
    }

    @NonNull
    public String getPoints() {
        return points;
    }
}
