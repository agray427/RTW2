package com.agray427.rewardsthatwork.model.ui;

import androidx.annotation.NonNull;

import com.agray427.rewardsthatwork.model.common.Entity;
import com.agray427.rewardsthatwork.model.domain.Group;
import com.agray427.rewardsthatwork.model.domain.User;

import java.util.ArrayList;
import java.util.List;

public final class GroupList {
    private final List<GroupItem> list;

    public GroupList(@NonNull Entity<User> entity) {
        this(entity.getData());
    }

    public GroupList(@NonNull User data) {
        this();
        for (Entity<Group> entity : data.getGroups()) {
            this.list.add(new GroupItem(entity));
        }
    }

    public GroupList() {
        this.list = new ArrayList<>();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    @NonNull
    public List<GroupItem> getList() {
        return list;
    }
}
