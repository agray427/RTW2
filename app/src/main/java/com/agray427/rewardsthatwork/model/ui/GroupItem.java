package com.agray427.rewardsthatwork.model.ui;

import androidx.annotation.NonNull;

import com.agray427.rewardsthatwork.model.common.Entity;
import com.agray427.rewardsthatwork.model.domain.Group;

public final class GroupItem {
    private final String id;
    private final String name;
    private final String userCount;

    public GroupItem(@NonNull Entity<Group> entity) {
        this(entity.getId(), entity.getData());
    }

    public GroupItem(@NonNull String id, @NonNull Group data) {
        this.id = id;
        this.name = data.getName();
        this.userCount = String.valueOf(data.getUsers().size());
    }

    public GroupItem(@NonNull String id, @NonNull String name, @NonNull String userCount) {
        this.id = id;
        this.name = name;
        this.userCount = userCount;
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
    public String getUserCount() {
        return userCount;
    }
}
