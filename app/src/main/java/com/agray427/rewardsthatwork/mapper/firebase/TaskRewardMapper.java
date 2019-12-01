package com.agray427.rewardsthatwork.mapper.firebase;

import androidx.annotation.NonNull;

import com.agray427.rewardsthatwork.common.AsyncResult;
import com.agray427.rewardsthatwork.mapper.Mapper;
import com.agray427.rewardsthatwork.model.domain.TaskReward;
import com.agray427.rewardsthatwork.model.network.NetworkTaskReward;

public final class TaskRewardMapper implements Mapper<NetworkTaskReward, TaskReward> {
    @NonNull
    @Override
    public AsyncResult<TaskReward> mapTo(@NonNull NetworkTaskReward dataX) {
        AsyncResult<TaskReward> asyncResult = new AsyncResult<>();
        TaskReward taskReward = new TaskReward(dataX.getName(), dataX.getDescription(), dataX.getPoints());
        asyncResult.setResult(taskReward);
        return asyncResult;
    }

    @NonNull
    @Override
    public AsyncResult<NetworkTaskReward> mapFrom(@NonNull TaskReward dataY) {
        AsyncResult<NetworkTaskReward> asyncResult = new AsyncResult<>();
        NetworkTaskReward networkTaskReward = new NetworkTaskReward(dataY.getName(), dataY.getDescription(), dataY.getPoints());
        asyncResult.setResult(networkTaskReward);
        return asyncResult;
    }
}
