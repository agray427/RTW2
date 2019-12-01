package com.agray427.rewardsthatwork.api;

import androidx.annotation.NonNull;

import com.agray427.rewardsthatwork.common.AsyncAction;
import com.agray427.rewardsthatwork.common.AsyncResult;
import com.agray427.rewardsthatwork.model.common.Entity;
import com.agray427.rewardsthatwork.model.network.NetworkUser;

public interface UserApi {
    @NonNull
    AsyncResult<Entity<NetworkUser>> get(@NonNull String id);
    @NonNull
    AsyncAction set(@NonNull String id, @NonNull NetworkUser networkUser);
}
