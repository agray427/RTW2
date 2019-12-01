package com.agray427.rewardsthatwork.model.ui;

import androidx.annotation.NonNull;

import com.agray427.rewardsthatwork.model.common.Entity;
import com.agray427.rewardsthatwork.model.domain.Group;
import com.agray427.rewardsthatwork.model.domain.GroupUser;

import java.util.ArrayList;
import java.util.List;

public final class UserList {
    private final String inviteCode;
    private final List<UserItem> list;

    public UserList(@NonNull Entity<Group> entity) {
        this(entity.getData());
    }

    public UserList(@NonNull Group data) {
        this(data.getInviteCode());
        for (Entity<GroupUser> entity : data.getUsers()) {
            this.list.add(new UserItem(entity));
        }
    }

    public UserList(@NonNull String inviteCode) {
        this.inviteCode = inviteCode;
        this.list = new ArrayList<>();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    @NonNull
    public String getInviteCode() {
        return inviteCode;
    }

    @NonNull
    public List<UserItem> getList() {
        return list;
    }
}
