package com.agray427.rewardsthatwork.api;

import androidx.annotation.NonNull;

import com.agray427.rewardsthatwork.common.AsyncAction;
import com.agray427.rewardsthatwork.common.AsyncResult;
import com.agray427.rewardsthatwork.model.common.Entity;
import com.agray427.rewardsthatwork.model.network.NetworkGroupUser;

import java.util.List;

public interface GroupUserApi {
    @NonNull
    AsyncResult<Entity<NetworkGroupUser>> get(@NonNull String id);
    @NonNull
    AsyncResult<List<Entity<NetworkGroupUser>>> getAll();
    AsyncAction set(@NonNull String id, @NonNull NetworkGroupUser networkGroupUser);
}
