package com.agray427.rewardsthatwork.model.domain;

import androidx.annotation.NonNull;

public final class TaskReward {
    private String name;
    private String description;
    private int points;

    public TaskReward(@NonNull String name, @NonNull String description, int points) {
        this.name = name;
        this.description = description;
        this.points = points;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    public void setDescription(@NonNull String description) {
        this.description = description;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
