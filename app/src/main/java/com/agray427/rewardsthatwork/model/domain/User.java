package com.agray427.rewardsthatwork.model.domain;

import androidx.annotation.NonNull;

import com.agray427.rewardsthatwork.model.common.Entity;

import java.util.ArrayList;
import java.util.List;

public final class User {
    private final UserName name;
    private final List<Entity<Group>> groups;

    public User(@NonNull UserName name) {
        this.name = name;
        this.groups = new ArrayList<>();
    }

    @NonNull
    public UserName getName() {
        return name;
    }

    @NonNull
    public List<Entity<Group>> getGroups() {
        return groups;
    }
}
