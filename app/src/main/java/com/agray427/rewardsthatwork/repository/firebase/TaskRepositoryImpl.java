package com.agray427.rewardsthatwork.repository.firebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.agray427.rewardsthatwork.api.Apis;
import com.agray427.rewardsthatwork.api.TaskRewardApi;
import com.agray427.rewardsthatwork.common.AsyncAction;
import com.agray427.rewardsthatwork.common.AsyncResult;
import com.agray427.rewardsthatwork.common.OnAsyncActionListener;
import com.agray427.rewardsthatwork.common.OnAsyncResultListener;
import com.agray427.rewardsthatwork.mapper.Mappers;
import com.agray427.rewardsthatwork.mapper.common.EntityMapper;
import com.agray427.rewardsthatwork.mapper.common.MapperListImpl;
import com.agray427.rewardsthatwork.mapper.firebase.TaskRewardMapper;
import com.agray427.rewardsthatwork.model.common.Entity;
import com.agray427.rewardsthatwork.model.domain.TaskReward;
import com.agray427.rewardsthatwork.model.network.NetworkTaskReward;
import com.agray427.rewardsthatwork.repository.TaskRepository;
import com.agray427.rewardsthatwork.utils.AsyncListeners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class TaskRepositoryImpl implements TaskRepository {
    private final TaskRewardApi api;
    private final TaskRewardMapper mapper;
    private final Map<String, TaskReward> cache;

    public TaskRepositoryImpl(@NonNull String groupId) {
        this.api = Apis.getTaskApi(groupId);
        this.mapper = Mappers.getTaskRewardMapper();
        this.cache = new HashMap<>();
    }

    @NonNull
    @Override
    public AsyncResult<String> create(@NonNull final TaskReward task) {
        final AsyncResult<String> asyncResult = new AsyncResult<>();
        mapper.mapFrom(task).addListener(new OnAsyncResultListener<NetworkTaskReward>() {
            @Override
            public void onResult(@NonNull NetworkTaskReward networkTask) {
                api.create(networkTask).addListener(new OnAsyncResultListener<String>() {
                    @Override
                    public void onResult(@NonNull String id) {
                        cache.put(id, task);
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
    public AsyncResult<Entity<TaskReward>> get(@NonNull final String id) {
        final AsyncResult<Entity<TaskReward>> asyncResult = new AsyncResult<>();
        if (cache.containsKey(id)) {
            asyncResult.setResult(new Entity<>(id, Objects.requireNonNull(cache.get(id), "Cache should never contain null value.")));
        } else {
            api.get(id).addListener(new OnAsyncResultListener<Entity<NetworkTaskReward>>() {
                @Override
                public void onResult(@NonNull Entity<NetworkTaskReward> networkTask) {
                    mapper.mapTo(networkTask.getData()).addListener(new OnAsyncResultListener<TaskReward>() {
                        @Override
                        public void onResult(@NonNull TaskReward task) {
                            asyncResult.setResult(new Entity<>(id, task));
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
    public AsyncResult<List<Entity<TaskReward>>> getAll() {
        final AsyncResult<List<Entity<TaskReward>>> asyncResult = new AsyncResult<>();
        api.getAll().addListener(new OnAsyncResultListener<List<Entity<NetworkTaskReward>>>() {
            @Override
            public void onResult(@NonNull List<Entity<NetworkTaskReward>> networkTasks) {
                EntityMapper<NetworkTaskReward, TaskReward> entityMapper = new EntityMapper<>(mapper);
                MapperListImpl<Entity<NetworkTaskReward>, Entity<TaskReward>> listMapper = new MapperListImpl<>(entityMapper);
                listMapper.mapTo(networkTasks).addListener(new OnAsyncResultListener<List<Entity<TaskReward>>>() {
                    @Override
                    public void onResult(@NonNull List<Entity<TaskReward>> result) {
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

    @NonNull
    public AsyncAction set(@NonNull final String id, @NonNull TaskReward taskReward) {
        final AsyncAction asyncAction = new AsyncAction();
        cache.put(id, taskReward);
        mapper.mapFrom(taskReward).addListener(new OnAsyncResultListener<NetworkTaskReward>() {
            @Override
            public void onResult(@NonNull NetworkTaskReward networkTaskReward) {
                api.set(id, networkTaskReward).addListener(new OnAsyncActionListener() {
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

    @NonNull
    @Override
    public AsyncAction setAll(List<Entity<TaskReward>> entities) {
        List<AsyncAction> asyncActions = new ArrayList<>();
        for (Entity<TaskReward> entity : entities) {
            asyncActions.add(set(entity.getId(), entity.getData()));
        }
        return AsyncListeners.whenAllActionsComplete(asyncActions);
    }
}
