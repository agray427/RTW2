package com.agray427.rewardsthatwork.model.ui;

import androidx.annotation.NonNull;

import com.agray427.rewardsthatwork.model.common.Entity;
import com.agray427.rewardsthatwork.model.domain.GroupUser;
import com.agray427.rewardsthatwork.model.domain.UserName;

public final class UserItem {
    private final String id;
    private final String name;
    private final String role;

    public UserItem(@NonNull Entity<GroupUser> entity) {
        this(entity.getId(), entity.getData());
    }

    public UserItem(@NonNull String id, @NonNull GroupUser data) {
        UserName userName = data.getUser().getData().getName();
        this.id = id;
        this.name = String.format("%s %s", userName.getFirst(), userName.getLast());
        this.role = data.getRole().getText();
    }

    public UserItem(@NonNull String id, @NonNull String name, @NonNull String role) {
        this.id = id;
        this.name = name;
        this.role = role;
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
    public String getRole() {
        return role;
    }
}
