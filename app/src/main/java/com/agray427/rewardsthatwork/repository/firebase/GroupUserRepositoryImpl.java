package com.agray427.rewardsthatwork.repository.firebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.agray427.rewardsthatwork.api.Apis;
import com.agray427.rewardsthatwork.api.GroupUserApi;
import com.agray427.rewardsthatwork.common.AsyncAction;
import com.agray427.rewardsthatwork.common.AsyncResult;
import com.agray427.rewardsthatwork.common.OnAsyncActionListener;
import com.agray427.rewardsthatwork.common.OnAsyncResultListener;
import com.agray427.rewardsthatwork.mapper.common.MapperListImpl;
import com.agray427.rewardsthatwork.mapper.Mappers;
import com.agray427.rewardsthatwork.mapper.common.EntityMapper;
import com.agray427.rewardsthatwork.mapper.firebase.GroupUserMapper;
import com.agray427.rewardsthatwork.model.common.Entity;
import com.agray427.rewardsthatwork.model.domain.GroupUser;
import com.agray427.rewardsthatwork.model.network.NetworkGroupUser;
import com.agray427.rewardsthatwork.repository.GroupUserRepository;
import com.agray427.rewardsthatwork.utils.AsyncListeners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class GroupUserRepositoryImpl implements GroupUserRepository {
    private final GroupUserApi api;
    private final GroupUserMapper mapper;
    private final Map<String, GroupUser> cache;

    public GroupUserRepositoryImpl(@NonNull String groupId) {
        this.api = Apis.getGroupUserApi(groupId);
        this.mapper = Mappers.getGroupUserMapper();
        this.cache = new HashMap<>();
    }

    @NonNull
    @Override
    public AsyncResult<Entity<GroupUser>> get(@NonNull final String id) {
        final AsyncResult<Entity<GroupUser>> asyncResult = new AsyncResult<>();
        if (cache.containsKey(id)) {
            asyncResult.setResult(new Entity<>(id, Objects.requireNonNull(cache.get(id), "Cache should never contain null value.")));
        } else {
            api.get(id).addListener(new OnAsyncResultListener<Entity<NetworkGroupUser>>() {
                @Override
                public void onResult(@NonNull Entity<NetworkGroupUser> networkGroupUser) {
                    mapper.mapTo(networkGroupUser.getData()).addListener(new OnAsyncResultListener<GroupUser>() {
                        @Override
                        public void onResult(@NonNull GroupUser groupUser) {
                            asyncResult.setResult(new Entity<>(id, groupUser));
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
    public AsyncResult<List<Entity<GroupUser>>> getAll() {
        final AsyncResult<List<Entity<GroupUser>>> asyncResult = new AsyncResult<>();
        api.getAll().addListener(new OnAsyncResultListener<List<Entity<NetworkGroupUser>>>() {
            @Override
            public void onResult(@NonNull List<Entity<NetworkGroupUser>> networkGroupUsers) {
                EntityMapper<NetworkGroupUser, GroupUser> entityMapper = new EntityMapper<>(mapper);
                MapperListImpl<Entity<NetworkGroupUser>, Entity<GroupUser>> listMapper = new MapperListImpl<>(entityMapper);
                listMapper.mapTo(networkGroupUsers).addListener(new OnAsyncResultListener<List<Entity<GroupUser>>>() {
                    @Override
                    public void onResult(@NonNull List<Entity<GroupUser>> result) {
                        asyncResult.setResult(result);
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

    public AsyncAction set(@NonNull final String id, @NonNull GroupUser groupUser) {
        final AsyncAction asyncAction = new AsyncAction();
        cache.put(id, groupUser);
        mapper.mapFrom(groupUser).addListener(new OnAsyncResultListener<NetworkGroupUser>() {
            @Override
            public void onResult(@NonNull NetworkGroupUser networkGroupUser) {
                api.set(id, networkGroupUser).addListener(new OnAsyncActionListener() {
                    @Override
                    public void onAction() {
                        asyncAction.trigger();
                    }

                    @Override
                    public void onError(@Nullable String errorMessage) {
                        asyncAction.setError(errorMessage);
                    }
                });
            }

            @Override
            public void onError(@Nullable String errorMessage) {
                asyncAction.setError(errorMessage);
            }
        });
        return asyncAction;
    }

    @Override
    public AsyncAction setAll(List<Entity<GroupUser>> entities) {
        List<AsyncAction> asyncActions = new ArrayList<>();
        for (Entity<GroupUser> entity : entities) {
            asyncActions.add(set(entity.getId(), entity.getData()));
        }
        return AsyncListeners.whenAllActionsComplete(asyncActions);
    }
}
