package com.agray427.rewardsthatwork.model.domain;

import androidx.annotation.NonNull;

import com.agray427.rewardsthatwork.common.GroupRole;
import com.agray427.rewardsthatwork.model.common.Entity;

import java.util.HashMap;
import java.util.Map;

public final class GroupUser {
    private final Entity<Group> group;
    private final Entity<User> user;
    private GroupRole role;
    private int points;

    private final Map<String, CompletedTaskReward> rewards;
    private final Map<String, CompletedTaskReward> tasks;

    public GroupUser(@NonNull Entity<Group> group, @NonNull Entity<User> user, @NonNull GroupRole role) {
        this.group = group;
        this.user = user;
        this.role = role;
        this.points = 0;
        this.rewards = new HashMap<>();
        this.tasks = new HashMap<>();
    }

    @NonNull
    public Entity<Group> getGroup() {
        return group;
    }

    @NonNull
    public Entity<User> getUser() {
        return user;
    }

    @NonNull
    public GroupRole getRole() {
        return role;
    }

    public void setRole(@NonNull GroupRole role) {
        this.role = role;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @NonNull
    public Map<String, CompletedTaskReward> getRewards() {
        return rewards;
    }

    @NonNull
    public Map<String, CompletedTaskReward> getTasks() {
        return tasks;
    }
}
