package com.agray427.rewardsthatwork.repository;

import androidx.annotation.NonNull;

import com.agray427.rewardsthatwork.common.AsyncAction;
import com.agray427.rewardsthatwork.common.AsyncResult;
import com.agray427.rewardsthatwork.model.common.Entity;
import com.agray427.rewardsthatwork.model.domain.User;

public interface UserRepository {
    @NonNull
    AsyncResult<Entity<User>> getClient();
    @NonNull
    AsyncResult<Entity<User>> get(@NonNull String id);
    @NonNull
    AsyncAction set(@NonNull String id, @NonNull User user);
}
