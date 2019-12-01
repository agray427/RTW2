package com.agray427.rewardsthatwork.mapper.firebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.agray427.rewardsthatwork.common.AsyncResult;
import com.agray427.rewardsthatwork.common.OnAsyncResultListener;
import com.agray427.rewardsthatwork.mapper.Mapper;
import com.agray427.rewardsthatwork.model.common.Entity;
import com.agray427.rewardsthatwork.model.domain.Group;
import com.agray427.rewardsthatwork.model.domain.User;
import com.agray427.rewardsthatwork.model.domain.UserName;
import com.agray427.rewardsthatwork.model.network.NetworkUser;
import com.agray427.rewardsthatwork.repository.GroupRepository;
import com.agray427.rewardsthatwork.repository.Repositories;
import com.agray427.rewardsthatwork.utils.AsyncListeners;

import java.util.ArrayList;
import java.util.List;

public final class UserMapper implements Mapper<NetworkUser, User> {
    @NonNull
    @Override
    public AsyncResult<User> mapTo(@NonNull NetworkUser dataX) {
        final AsyncResult<User> asyncResult = new AsyncResult<>();
        final User user = new User(new UserName(dataX.getFirstName(), dataX.getLastName()));
        GroupRepository groupRepository = Repositories.getGroupRepository();
        List<AsyncResult<Entity<Group>>> asyncResults = new ArrayList<>();
        for (String groupId : dataX.getGroupIds()) {
            asyncResults.add(groupRepository.get(groupId));
        }
        AsyncListeners.whenAllResultsComplete(asyncResults).addListener(new OnAsyncResultListener<List<AsyncResult<Entity<Group>>>>() {
            @Override
            public void onResult(@NonNull List<AsyncResult<Entity<Group>>> resultList) {
                List<Entity<Group>> entities = user.getGroups();
                for (AsyncResult<Entity<Group>> result : resultList) {
                    if (result.isSuccessful() && result.getResult() != null) {
                        entities.add(result.getResult());
                    }
                }
                asyncResult.setResult(user);
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
    public AsyncResult<NetworkUser> mapFrom(@NonNull User dataY) {
        AsyncResult<NetworkUser> asyncResult = new AsyncResult<>();
        NetworkUser networkUser = new NetworkUser(dataY.getName().getFirst(), dataY.getName().getLast());
        List<String> groupIds = networkUser.getGroupIds();
        for (Entity<Group> entity : dataY.getGroups()) {
            groupIds.add(entity.getId());
        }
        asyncResult.setResult(networkUser);
        return asyncResult;
    }
}
