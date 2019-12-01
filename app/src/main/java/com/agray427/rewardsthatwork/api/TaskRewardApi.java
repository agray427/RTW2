package com.agray427.rewardsthatwork.api;

import androidx.annotation.NonNull;

import com.agray427.rewardsthatwork.common.AsyncAction;
import com.agray427.rewardsthatwork.common.AsyncResult;
import com.agray427.rewardsthatwork.model.common.Entity;
import com.agray427.rewardsthatwork.model.network.NetworkTaskReward;

import java.util.List;

public interface TaskRewardApi {
    @NonNull
    AsyncResult<String> create(@NonNull NetworkTaskReward networkTaskReward);
    @NonNull
    AsyncResult<Entity<NetworkTaskReward>> get(@NonNull String id);
    @NonNull
    AsyncResult<List<Entity<NetworkTaskReward>>> getAll();
    @NonNull
    AsyncAction set(@NonNull String id, @NonNull NetworkTaskReward networkTaskReward);
}
