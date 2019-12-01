package com.agray427.rewardsthatwork.model.network;

import androidx.annotation.NonNull;

public final class NetworkTaskReward {
    private final String name;
    private final String description;
    private final int points;

    public NetworkTaskReward() { this("", "", 0); }
    public NetworkTaskReward(@NonNull String name, @NonNull String description, int points) {
        this.name = name;
        this.description = description;
        this.points = points;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    public int getPoints() {
        return points;
    }
}
