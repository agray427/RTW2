package com.agray427.rewardsthatwork.repository;

import androidx.annotation.NonNull;

import com.agray427.rewardsthatwork.common.AsyncAction;
import com.agray427.rewardsthatwork.common.AsyncResult;
import com.agray427.rewardsthatwork.model.common.Entity;
import com.agray427.rewardsthatwork.model.domain.GroupUser;

import java.util.List;

public interface GroupUserRepository {
    @NonNull
    AsyncResult<Entity<GroupUser>> get(@NonNull String id);
    @NonNull
    AsyncResult<List<Entity<GroupUser>>> getAll();
    AsyncAction setAll(List<Entity<GroupUser>> groupUsers);
}
