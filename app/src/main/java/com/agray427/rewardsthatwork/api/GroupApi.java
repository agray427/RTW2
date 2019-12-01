package com.agray427.rewardsthatwork.api;

import androidx.annotation.NonNull;

import com.agray427.rewardsthatwork.common.AsyncAction;
import com.agray427.rewardsthatwork.common.AsyncResult;
import com.agray427.rewardsthatwork.model.common.Entity;
import com.agray427.rewardsthatwork.model.network.NetworkGroup;

public interface GroupApi {
    @NonNull
    AsyncResult<String> create(@NonNull NetworkGroup networkGroup);
    @NonNull
    AsyncResult<Entity<NetworkGroup>> get(@NonNull String id );
    @NonNull
    AsyncResult<Entity<NetworkGroup>> getFromCode(@NonNull String inviteCode);
    @NonNull
    AsyncAction set(@NonNull String id, @NonNull NetworkGroup networkGroup);
}
