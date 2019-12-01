package com.agray427.rewardsthatwork.mapper.firebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.agray427.rewardsthatwork.common.AsyncResult;
import com.agray427.rewardsthatwork.common.OnAsyncResultListener;
import com.agray427.rewardsthatwork.mapper.Mapper;
import com.agray427.rewardsthatwork.model.common.Entity;
import com.agray427.rewardsthatwork.model.domain.Group;
import com.agray427.rewardsthatwork.model.domain.GroupUser;
import com.agray427.rewardsthatwork.model.domain.User;
import com.agray427.rewardsthatwork.model.network.NetworkGroupUser;
import com.agray427.rewardsthatwork.repository.Repositories;

public final class GroupUserMapper implements Mapper<NetworkGroupUser, GroupUser> {
    @NonNull
    @Override
    public AsyncResult<GroupUser> mapTo(@NonNull final NetworkGroupUser dataX) {
        final AsyncResult<GroupUser> asyncResult = new AsyncResult<>();
        Repositories.getGroupRepository().get(dataX.getGroupId()).addListener(new OnAsyncResultListener<Entity<Group>>() {
            @Override
            public void onResult(@NonNull final Entity<Group> group) {
                Repositories.getUserRepository().get(dataX.getUserId()).addListener(new OnAsyncResultListener<Entity<User>>() {
                    @Override
                    public void onResult(@NonNull Entity<User> user) {
                        GroupUser groupUser = new GroupUser(group, user, dataX.getGroupRole());
                        groupUser.setPoints(dataX.getPoints());
                        asyncResult.setResult(groupUser);
                    }

                    @Override
                    public void onError(@Nullable String errorMessage) {
                        asyncResult.setError(errorMessage);
                    }
                });
            }

            @Override
            public void onError(@Nullable String errorMessage) {
                asyncResult.setError(errorMessage);
            }
        });
        return asyncResult;
    }

    @NonNull
    @Override
    public AsyncResult<NetworkGroupUser> mapFrom(@NonNull GroupUser dataY) {
        AsyncResult<NetworkGroupUser> asyncResult = new AsyncResult<>();
        NetworkGroupUser networkGroupUser = new NetworkGroupUser(dataY.getGroup().getId(), dataY.getUser().getId(), dataY.getRole(), dataY.getPoints());
        asyncResult.setResult(networkGroupUser);
        return asyncResult;
    }
}
