package com.agray427.rewardsthatwork.mapper.firebase;

import androidx.annotation.NonNull;

import com.agray427.rewardsthatwork.common.AsyncResult;
import com.agray427.rewardsthatwork.mapper.Mapper;
import com.agray427.rewardsthatwork.model.domain.Group;
import com.agray427.rewardsthatwork.model.network.NetworkGroup;

public final class GroupMapper implements Mapper<NetworkGroup, Group> {
    @NonNull
    @Override
    public AsyncResult<Group> mapTo(@NonNull NetworkGroup dataX) {
        AsyncResult<Group> asyncResult = new AsyncResult<>();
        Group group = new Group(dataX.getName(), dataX.getInviteCode());
        asyncResult.setResult(group);
        return asyncResult;
    }

    @NonNull
    @Override
    public AsyncResult<NetworkGroup> mapFrom(@NonNull Group dataY) {
        AsyncResult<NetworkGroup> asyncResult = new AsyncResult<>();
        NetworkGroup networkGroup = new NetworkGroup(dataY.getName(), dataY.getInviteCode());
        asyncResult.setResult(networkGroup);
        return asyncResult;
    }
}
