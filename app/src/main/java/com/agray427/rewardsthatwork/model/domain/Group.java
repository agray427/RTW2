package com.agray427.rewardsthatwork.model.domain;

import androidx.annotation.NonNull;

import com.agray427.rewardsthatwork.model.common.Entity;

import java.util.ArrayList;
import java.util.List;

public final class Group {
    private String name;
    private String inviteCode;
    private final List<Entity<TaskReward>> rewards;
    private final List<Entity<TaskReward>> tasks;
    private final List<Entity<GroupUser>> users;

    public Group(@NonNull String name, @NonNull String inviteCode) {
        this.name = name;
        this.inviteCode = inviteCode;
        this.rewards = new ArrayList<>();
        this.tasks = new ArrayList<>();
        this.users = new ArrayList<>();
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(@NonNull String inviteCode) {
        this.inviteCode = inviteCode;
    }

    @NonNull
    public List<Entity<TaskReward>> getRewards() {
        return rewards;
    }

    @NonNull
    public List<Entity<TaskReward>> getTasks() {
        return tasks;
    }

    @NonNull
    public List<Entity<GroupUser>> getUsers() {
        return users;
    }
}
