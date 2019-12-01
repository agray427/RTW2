package com.agray427.rewardsthatwork.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.agray427.rewardsthatwork.RewardsThatWork;
import com.agray427.rewardsthatwork.common.AsyncAction;
import com.agray427.rewardsthatwork.common.OnAsyncActionListener;
import com.agray427.rewardsthatwork.common.OnAsyncResultListener;
import com.agray427.rewardsthatwork.model.common.Entity;
import com.agray427.rewardsthatwork.model.domain.Group;
import com.agray427.rewardsthatwork.model.domain.GroupUser;
import com.agray427.rewardsthatwork.model.domain.TaskReward;
import com.agray427.rewardsthatwork.model.domain.User;
import com.agray427.rewardsthatwork.model.ui.Home;
import com.agray427.rewardsthatwork.model.ui.TaskRewardData;
import com.agray427.rewardsthatwork.model.ui.TaskRewardList;
import com.agray427.rewardsthatwork.model.ui.UserData;
import com.agray427.rewardsthatwork.model.ui.UserList;
import com.agray427.rewardsthatwork.model.ui.UserProfile;
import com.agray427.rewardsthatwork.repository.Repositories;
import com.agray427.rewardsthatwork.repository.RewardRepository;
import com.agray427.rewardsthatwork.repository.TaskRepository;

import java.util.List;

public final class MainViewModel extends ViewModel {
    private static final String TAG = "MainViewModel";

    private final MutableLiveData<Entity<GroupUser>> clientGroupUser;
    private final MutableLiveData<Entity<Group>> selectedGroup;
    private final MutableLiveData<Entity<TaskReward>> selectedReward;
    private final MutableLiveData<Entity<TaskReward>> selectedTask;
    private final MutableLiveData<Entity<User>> selectedUser;
    private final LiveData<Home> homeData;
    private final LiveData<TaskRewardList> rewardList;
    private final LiveData<TaskRewardList> taskList;
    private final LiveData<TaskRewardData> rewardData;
    private final LiveData<TaskRewardData> taskData;
    private final LiveData<UserList> userList;
    private final LiveData<UserData> userData;
    private final LiveData<UserProfile> userProfile;

    public MainViewModel() {
        this.clientGroupUser = new MutableLiveData<>();
        this.selectedGroup = new MutableLiveData<>();
        this.selectedReward = new MutableLiveData<>();
        this.selectedTask = new MutableLiveData<>();
        this.selectedUser = new MutableLiveData<>();
        this.homeData = Transformations.map(clientGroupUser, new Function<Entity<GroupUser>, Home>() {
            @Override
            public Home apply(Entity<GroupUser> input) {
                return new Home(input);
            }
        });
        this.rewardList = Transformations.map(selectedGroup, new Function<Entity<Group>, TaskRewardList>() {
            @Override
            public TaskRewardList apply(Entity<Group> input) {
                TaskRewardList rewardList = new TaskRewardList();
                rewardList.populateRewards(input);
                return rewardList;
            }
        });
        this.rewardData = Transformations.map(selectedReward, new Function<Entity<TaskReward>, TaskRewardData>() {
            @Override
            public TaskRewardData apply(Entity<TaskReward> input) {
                return new TaskRewardData(input);
            }
        });
        this.taskList = Transformations.map(selectedGroup, new Function<Entity<Group>, TaskRewardList>() {
            @Override
            public TaskRewardList apply(Entity<Group> input) {
                TaskRewardList taskList = new TaskRewardList();
                taskList.populateTasks(input);
                return taskList;
            }
        });
        this.taskData = Transformations.map(selectedTask, new Function<Entity<TaskReward>, TaskRewardData>() {
            @Override
            public TaskRewardData apply(Entity<TaskReward> input) {
                return new TaskRewardData(input);
            }
        });
        this.userList = Transformations.map(selectedGroup, new Function<Entity<Group>, UserList>() {
            @Override
            public UserList apply(Entity<Group> input) {
                return new UserList(input);
            }
        });
        this.userData = Transformations.map(selectedUser, new Function<Entity<User>, UserData>() {
            @Override
            public UserData apply(Entity<User> input) {
                return new UserData(input);
            }
        });
        this.userProfile = Transformations.map(clientGroupUser, new Function<Entity<GroupUser>, UserProfile>() {
            @Override
            public UserProfile apply(Entity<GroupUser> input) {
                return new UserProfile(input);
            }
        });
    }

    @NonNull
    public LiveData<Home> getHomeData() {
        return homeData;
    }

    @NonNull
    public LiveData<TaskRewardList> getRewardList() {
        return rewardList;
    }

    @NonNull
    public LiveData<TaskRewardList> getTaskList() {
        return taskList;
    }

    @NonNull
    public LiveData<TaskRewardData> getRewardData() {
        return rewardData;
    }

    @NonNull
    public LiveData<TaskRewardData> getTaskData() {
        return taskData;
    }

    @NonNull
    public LiveData<UserList> getUserList() {
        return userList;
    }

    @NonNull
    public LiveData<UserData> getUserData() {
        return userData;
    }

    @NonNull
    public LiveData<UserProfile> getUserProfile() {
        return userProfile;
    }

    public AsyncAction init() {
        final AsyncAction asyncAction = new AsyncAction();
        RewardsThatWork.getInstance().getClientId().observeForever(new Observer<String>() {
            @Override
            public void onChanged(String id) {
                if (id != null) {
                    String groupId = RewardsThatWork.getInstance().getGroupId().getValue();
                    if (groupId != null) {
                        Repositories.getGroupUserRepository(groupId).get(id).addListener(new OnAsyncResultListener<Entity<GroupUser>>() {
                            @Override
                            public void onResult(@NonNull Entity<GroupUser> result) {
                                clientGroupUser.postValue(result);
                            }

                            @Override
                            public void onError(@Nullable String errorMessage) {
                                asyncAction.setError(errorMessage);
                            }
                        });
                    }
                }
            }
        });
        RewardsThatWork.getInstance().getGroupId().observeForever(new Observer<String>() {
            @Override
            public void onChanged(String id) {
                if (id != null) {
                    Repositories.getGroupRepository().get(id).addListener(new OnAsyncResultListener<Entity<Group>>() {
                        @Override
                        public void onResult(@NonNull Entity<Group> result) {
                            Log.d(TAG, "onResult: Entity<Group> -> (" + result.getId() + ", " + result.getData() + ") + List -> " + result.getData().getRewards().size());
                            selectedGroup.postValue(result);
                        }

                        @Override
                        public void onError(@Nullable String errorMessage) {
                            asyncAction.setError(errorMessage);
                        }
                    });
                }
            }
        });
        RewardsThatWork.getInstance().getRewardId().observeForever(new Observer<String>() {
            @Override
            public void onChanged(String id) {
                if (id != null) {
                    String groupId = RewardsThatWork.getInstance().getGroupId().getValue();
                    if (groupId != null) {
                        Repositories.getRewardRepository(groupId).get(id).addListener(new OnAsyncResultListener<Entity<TaskReward>>() {
                            @Override
                            public void onResult(@NonNull Entity<TaskReward> result) {
                                selectedReward.postValue(result);
                            }

                            @Override
                            public void onError(@Nullable String errorMessage) {
                                asyncAction.setError(errorMessage);
                            }
                        });
                    }
                }
            }
        });
        RewardsThatWork.getInstance().getTaskId().observeForever(new Observer<String>() {
            @Override
            public void onChanged(String id) {
                if (id != null) {
                    String groupId = RewardsThatWork.getInstance().getGroupId().getValue();
                    if (groupId != null) {
                        Repositories.getTaskRepository(groupId).get(id).addListener(new OnAsyncResultListener<Entity<TaskReward>>() {
                            @Override
                            public void onResult(@NonNull Entity<TaskReward> result) {
                                selectedTask.postValue(result);
                            }

                            @Override
                            public void onError(@Nullable String errorMessage) {
                                asyncAction.setError(errorMessage);
                            }
                        });
                    }
                }
            }
        });
        RewardsThatWork.getInstance().getUserId().observeForever(new Observer<String>() {
            @Override
            public void onChanged(String id) {
                if (id != null) {
                    Repositories.getUserRepository().get(id).addListener(new OnAsyncResultListener<Entity<User>>() {
                        @Override
                        public void onResult(@NonNull Entity<User> result) {
                            selectedUser.postValue(result);
                        }

                        @Override
                        public void onError(@Nullable String errorMessage) {
                            asyncAction.setError(errorMessage);
                        }
                    });
                }
            }
        });
        return asyncAction;
    }

    public AsyncAction submitReward(@NonNull final TaskReward reward) {
        final AsyncAction asyncAction = new AsyncAction();
        final Entity<Group> group = selectedGroup.getValue();
        if (group != null) {
            RewardRepository rewardRepository = Repositories.getRewardRepository(group.getId());
            final String rewardId = RewardsThatWork.getInstance().getRewardId().getValue();
            if (rewardId == null) {
                rewardRepository.create(reward).addListener(new OnAsyncResultListener<String>() {
                    @Override
                    public void onResult(@NonNull String id) {
                        group.getData().getRewards().add(new Entity<>(id, reward));
                        selectedGroup.setValue(group);
                        asyncAction.trigger();
                    }

                    @Override
                    public void onError(@Nullable String errorMessage) {
                        asyncAction.setError(errorMessage);
                    }
                });
            } else {
                rewardRepository.set(rewardId, reward).addListener(new OnAsyncActionListener() {
                    @Override
                    public void onAction() {
                        List<Entity<TaskReward>> rewards = group.getData().getRewards();
                        for(int i = 0; i < rewards.size(); i++) {
                            if (rewards.get(i).getId().equals(rewardId)) {
                                rewards.set(i, new Entity<>(rewardId, reward));
                            }
                        }
                        selectedGroup.setValue(group);
                        asyncAction.trigger();
                    }

                    @Override
                    public void onError(@Nullable String errorMessage) {
                        asyncAction.setError(errorMessage);
                    }
                });
            }
        } else {
            asyncAction.setError("Could not get group.");
        }
        return asyncAction;
    }

    public AsyncAction submitTask(@NonNull final TaskReward task) {
        final AsyncAction asyncAction = new AsyncAction();
        final Entity<Group> group = selectedGroup.getValue();
        if (group != null) {
            TaskRepository taskRepository = Repositories.getTaskRepository(group.getId());
            final String taskId = RewardsThatWork.getInstance().getRewardId().getValue();
            if (taskId == null) {
                taskRepository.create(task).addListener(new OnAsyncResultListener<String>() {
                    @Override
                    public void onResult(@NonNull String id) {
                        group.getData().getTasks().add(new Entity<>(id, task));
                        selectedGroup.setValue(group);
                        asyncAction.trigger();
                    }

                    @Override
                    public void onError(@Nullable String errorMessage) {
                        asyncAction.setError(errorMessage);
                    }
                });
            } else {
                taskRepository.set(taskId, task).addListener(new OnAsyncActionListener() {
                    @Override
                    public void onAction() {
                        List<Entity<TaskReward>> tasks = group.getData().getTasks();
                        for(int i = 0; i < tasks.size(); i++) {
                            if (tasks.get(i).getId().equals(taskId)) {
                                tasks.set(i, new Entity<>(taskId, task));
                            }
                        }
                        selectedGroup.setValue(group);
                        asyncAction.trigger();
                    }

                    @Override
                    public void onError(@Nullable String errorMessage) {
                        asyncAction.setError(errorMessage);
                    }
                });
            }
        } else {
            asyncAction.setError("Could not get group.");
        }
        return asyncAction;
    }

    public AsyncAction updateInviteCode(@NonNull String inviteCode) {
        final AsyncAction asyncAction = new AsyncAction();
        final Entity<Group> group = selectedGroup.getValue();
        if (group != null) {
            group.getData().setInviteCode(inviteCode);
            Repositories.getGroupRepository().set(group.getId(), group.getData()).addListener(new OnAsyncActionListener() {
                @Override
                public void onAction() {
                    selectedGroup.setValue(group);
                    asyncAction.trigger();
                }

                @Override
                public void onError(@Nullable String errorMessage) {
                    asyncAction.setError(errorMessage);
                }
            });
        } else {
            asyncAction.setError("Could not get group.");
        }
        return asyncAction;
    }
}
