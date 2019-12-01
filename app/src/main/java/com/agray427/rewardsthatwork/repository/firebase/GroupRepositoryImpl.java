package com.agray427.rewardsthatwork.repository.firebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.agray427.rewardsthatwork.api.Apis;
import com.agray427.rewardsthatwork.api.GroupApi;
import com.agray427.rewardsthatwork.common.AsyncAction;
import com.agray427.rewardsthatwork.common.AsyncResult;
import com.agray427.rewardsthatwork.common.OnAsyncActionListener;
import com.agray427.rewardsthatwork.common.OnAsyncResultListener;
import com.agray427.rewardsthatwork.mapper.Mappers;
import com.agray427.rewardsthatwork.mapper.firebase.GroupMapper;
import com.agray427.rewardsthatwork.model.common.Entity;
import com.agray427.rewardsthatwork.model.domain.Group;
import com.agray427.rewardsthatwork.model.domain.GroupUser;
import com.agray427.rewardsthatwork.model.domain.TaskReward;
import com.agray427.rewardsthatwork.model.network.NetworkGroup;
import com.agray427.rewardsthatwork.repository.GroupRepository;
import com.agray427.rewardsthatwork.repository.Repositories;
import com.agray427.rewardsthatwork.utils.AsyncListeners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class GroupRepositoryImpl implements GroupRepository {
    private final GroupApi groupApi;
    private final Map<String, Group> cache;
    private final GroupMapper mapper;

    public GroupRepositoryImpl() {
        this.groupApi = Apis.getGroupApi();
        this.cache = new HashMap<>();
        this.mapper = Mappers.getGroupMapper();
    }

    @NonNull
    @Override
    public AsyncResult<String> create(@NonNull final Group group) {
        final AsyncResult<String> asyncResult = new AsyncResult<>();
        mapper.mapFrom(group).addListener(new OnAsyncResultListener<NetworkGroup>() {
            @Override
            public void onResult(@NonNull NetworkGroup networkGroup) {
                groupApi.create(networkGroup).addListener(new OnAsyncResultListener<String>() {
                    @Override
                    public void onResult(@NonNull String id) {
                        cache.put(id, group);
                        asyncResult.setResult(id);
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
    public AsyncResult<Entity<Group>> get(@NonNull final String id) {
        final AsyncResult<Entity<Group>> asyncResult = new AsyncResult<>();
        if (cache.containsKey(id))  {
            asyncResult.setResult(new Entity<>(id, Objects.requireNonNull(cache.get(id), "Cached data should exist.")));
        } else {
            groupApi.get(id).addListener(new OnAsyncResultListener<Entity<NetworkGroup>>() {
                @Override
                public void onResult(@NonNull Entity<NetworkGroup> networkGroup) {
                    fromNetworkGroup(networkGroup, asyncResult);
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
    public AsyncResult<Entity<Group>> getFromCode(@NonNull String inviteCode) {
        final AsyncResult<Entity<Group>> asyncResult = new AsyncResult<>();
        groupApi.getFromCode(inviteCode).addListener(new OnAsyncResultListener<Entity<NetworkGroup>>() {
            @Override
            public void onResult(@NonNull final Entity<NetworkGroup> networkGroup) {
                fromNetworkGroup(networkGroup, asyncResult);
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
    public AsyncAction set(@NonNull final String id, @NonNull final Group group) {
        final AsyncAction asyncAction = new AsyncAction();
        cache.put(id, group);
        mapper.mapFrom(group).addListener(new OnAsyncResultListener<NetworkGroup>() {
            @Override
            public void onResult(@NonNull NetworkGroup networkGroup) {
                groupApi.set(id, networkGroup).addListener(new OnAsyncActionListener() {
                    @Override
                    public void onAction() {
                        List<AsyncAction> asyncActions = new ArrayList<>();
                        asyncActions.add(Repositories.getGroupUserRepository(id).setAll(group.getUsers()));
                        asyncActions.add(Repositories.getRewardRepository(id).setAll(group.getRewards()));
                        asyncActions.add(Repositories.getTaskRepository(id).setAll(group.getTasks()));
                        AsyncListeners.whenAllActionsComplete(asyncActions).addListener(new OnAsyncActionListener() {
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
            }

            @Override
            public void onError(@Nullable String errorMessage) {
                asyncAction.setError(errorMessage);
            }
        });
        return asyncAction;
    }

    private void fromNetworkGroup(@NonNull final Entity<NetworkGroup> networkGroup, @NonNull final AsyncResult<Entity<Group>> asyncResult) {
        mapper.mapTo(networkGroup.getData()).addListener(new OnAsyncResultListener<Group>() {
            @Override
            public void onResult(@NonNull final Group group) {
                cache.put(networkGroup.getId(), group);
                Repositories.getGroupUserRepository(networkGroup.getId()).getAll().addListener(new OnAsyncResultListener<List<Entity<GroupUser>>>() {
                    @Override
                    public void onResult(@NonNull List<Entity<GroupUser>> groupUserList) {
                        group.getUsers().addAll(groupUserList);
                        Repositories.getRewardRepository(networkGroup.getId()).getAll().addListener(new OnAsyncResultListener<List<Entity<TaskReward>>>() {
                            @Override
                            public void onResult(@NonNull List<Entity<TaskReward>> rewardList) {
                                group.getRewards().addAll(rewardList);
                                Repositories.getTaskRepository(networkGroup.getId()).getAll().addListener(new OnAsyncResultListener<List<Entity<TaskReward>>>() {
                                    @Override
                                    public void onResult(@NonNull List<Entity<TaskReward>> taskList) {
                                        group.getTasks().addAll(taskList);
                                        asyncResult.setResult(new Entity<>(networkGroup.getId(), group));
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
}