package com.agray427.rewardsthatwork.repository;

import androidx.annotation.NonNull;

import com.agray427.rewardsthatwork.common.AsyncAction;
import com.agray427.rewardsthatwork.common.AsyncResult;
import com.agray427.rewardsthatwork.model.common.Entity;
import com.agray427.rewardsthatwork.model.domain.Group;

public interface GroupRepository {
    @NonNull
    AsyncResult<String> create(@NonNull Group group);
    @NonNull
    AsyncResult<Entity<Group>> get(@NonNull String id);
    @NonNull
    AsyncResult<Entity<Group>> getFromCode(@NonNull String inviteCode);
    @NonNull
    AsyncAction set(@NonNull String id, @NonNull Group group);
}
