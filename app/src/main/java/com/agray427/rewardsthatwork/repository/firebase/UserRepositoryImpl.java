package com.agray427.rewardsthatwork.repository.firebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.agray427.rewardsthatwork.RewardsThatWork;
import com.agray427.rewardsthatwork.api.Apis;
import com.agray427.rewardsthatwork.api.UserApi;
import com.agray427.rewardsthatwork.common.AsyncAction;
import com.agray427.rewardsthatwork.common.AsyncResult;
import com.agray427.rewardsthatwork.common.OnAsyncActionListener;
import com.agray427.rewardsthatwork.common.OnAsyncResultListener;
import com.agray427.rewardsthatwork.mapper.Mappers;
import com.agray427.rewardsthatwork.mapper.firebase.UserMapper;
import com.agray427.rewardsthatwork.model.common.Entity;
import com.agray427.rewardsthatwork.model.domain.User;
import com.agray427.rewardsthatwork.model.network.NetworkUser;
import com.agray427.rewardsthatwork.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UserRepositoryImpl implements UserRepository {
    private final UserApi userApi;
    private final UserMapper mapper;
    private final Map<String, User> cache;

    public UserRepositoryImpl() {
        this.userApi = Apis.getUserApi();
        this.mapper = Mappers.getUserMapper();
        this.cache = new HashMap<>();
    }

    @NonNull
    @Override
    public AsyncResult<Entity<User>> getClient() {
        AsyncResult<Entity<User>> result = new AsyncResult<>();
        String clientId = RewardsThatWork.getInstance().getClientId().getValue();
        if (clientId == null) {
            result.setError("Could not get client.");
        } else {
            return get(clientId);
        }
        return result;
    }

    @NonNull
    @Override
    public AsyncResult<Entity<User>> get(@NonNull final String id) {
        final AsyncResult<Entity<User>> asyncResult = new AsyncResult<>();
        if (cache.containsKey(id))  {
            asyncResult.setResult(new Entity<>(id, Objects.requireNonNull(cache.get(id), "Cached data should exist.")));
        } else {
            userApi.get(id).addListener(new OnAsyncResultListener<Entity<NetworkUser>>() {
                @Override
                public void onResult(@NonNull Entity<NetworkUser> entity) {
                    mapper.mapTo(entity.getData()).addListener(new OnAsyncResultListener<User>() {
                        @Override
                        public void onResult(@NonNull User user) {
                            asyncResult.setResult(new Entity<>(id, user));
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
        }
        return asyncResult;
    }

    @NonNull
    @Override
    public AsyncAction set(@NonNull final String id, @NonNull User user) {
        final AsyncAction action = new AsyncAction();
        cache.put(id, user);
        mapper.mapFrom(user).addListener(new OnAsyncResultListener<NetworkUser>() {
            @Override
            public void onResult(@NonNull NetworkUser networkUser) {
                userApi.set(id, networkUser).addListener(new OnAsyncActionListener() {
                    @Override
                    public void onAction() {
                        action.trigger();
                    }

                    @Override
                    public void onError(@Nullable String errorMessage) {
                        action.setError(errorMessage);
                    }
                });
            }

            @Override
            public void onError(@Nullable String errorMessage) {
                action.setError(errorMessage);
            }
        });
        return action;
    }
}