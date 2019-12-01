package com.agray427.rewardsthatwork.repository;

import androidx.annotation.NonNull;

import com.agray427.rewardsthatwork.common.AsyncAction;
import com.agray427.rewardsthatwork.common.AsyncResult;
import com.agray427.rewardsthatwork.model.common.Entity;
import com.agray427.rewardsthatwork.model.domain.TaskReward;

import java.util.List;

public interface RewardRepository {
    @NonNull
    AsyncResult<String> create(@NonNull TaskReward reward);
    @NonNull
    AsyncResult<Entity<TaskReward>> get(@NonNull String id);
    @NonNull
    AsyncResult<List<Entity<TaskReward>>> getAll();
    @NonNull
    AsyncAction set(@NonNull String id, @NonNull TaskReward reward);
    @NonNull
    AsyncAction setAll(List<Entity<TaskReward>> rewards);
}
