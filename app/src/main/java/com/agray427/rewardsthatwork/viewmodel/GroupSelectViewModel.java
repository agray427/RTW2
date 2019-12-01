package com.agray427.rewardsthatwork.viewmodel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.agray427.rewardsthatwork.RewardsThatWork;
import com.agray427.rewardsthatwork.common.AsyncAction;
import com.agray427.rewardsthatwork.common.GroupRole;
import com.agray427.rewardsthatwork.common.OnAsyncActionListener;
import com.agray427.rewardsthatwork.common.OnAsyncResultListener;
import com.agray427.rewardsthatwork.model.common.Entity;
import com.agray427.rewardsthatwork.model.domain.Group;
import com.agray427.rewardsthatwork.model.domain.GroupUser;
import com.agray427.rewardsthatwork.model.domain.User;
import com.agray427.rewardsthatwork.model.ui.GroupList;
import com.agray427.rewardsthatwork.repository.GroupRepository;
import com.agray427.rewardsthatwork.repository.Repositories;
import com.agray427.rewardsthatwork.repository.UserRepository;
import com.agray427.rewardsthatwork.utils.InviteCodeUtil;

public final class GroupSelectViewModel extends ViewModel {
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final MutableLiveData<Entity<User>> clientUser;
    private final LiveData<GroupList> groupListData;

    public GroupSelectViewModel() {
        this.groupRepository = Repositories.getGroupRepository();
        this.userRepository = Repositories.getUserRepository();
        this.clientUser = new MutableLiveData<>();
        this.groupListData = Transformations.map(clientUser, new Function<Entity<User>, GroupList>() {
            @Override
            public GroupList apply(Entity<User> input) {
                if (input != null) {
                    return new GroupList(input);
                } else {
                    return null;
                }
            }
        });
    }

    public LiveData<GroupList> getGroupList() {
        return groupListData;
    }

    public AsyncAction init() {
        final AsyncAction asyncAction = new AsyncAction();
        userRepository.getClient().addListener(new OnAsyncResultListener<Entity<User>>() {
            @Override
            public void onResult(@NonNull Entity<User> result) {
                clientUser.postValue(result);
                asyncAction.trigger();
            }

            @Override
            public void onError(@Nullable String errorMessage) {
                asyncAction.setError(errorMessage);
            }
        });
        return asyncAction;
    }

    public AsyncAction createGroup(@NonNull String groupName) {
        final AsyncAction asyncAction = new AsyncAction();
        final Group group = new Group(groupName, InviteCodeUtil.generate());
        groupRepository.create(group).addListener(new OnAsyncResultListener<String>() {
            @Override
            public void onResult(@NonNull String id) {
                addGroup(new Entity<>(id, group), GroupRole.OWNER).addListener(new OnAsyncActionListener() {
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

    public AsyncAction joinGroup(@NonNull String inviteCode) {
        final AsyncAction asyncAction = new AsyncAction();
        groupRepository.getFromCode(inviteCode).addListener(new OnAsyncResultListener<Entity<Group>>() {
            @Override
            public void onResult(@NonNull Entity<Group> entity) {
                addGroup(entity, GroupRole.BASIC).addListener(new OnAsyncActionListener() {
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

    private AsyncAction addGroup(@NonNull final Entity<Group> group, @NonNull GroupRole role) {
        final AsyncAction asyncAction = new AsyncAction();
        final Entity<User> user = clientUser.getValue();
        if (user != null) {
            GroupUser groupUser = new GroupUser(group, user, role);
            group.getData().getUsers().add(new Entity<>(user.getId(), groupUser));
            user.getData().getGroups().add(group);
            groupRepository.set(group.getId(), group.getData()).addListener(new OnAsyncActionListener() {
                @Override
                public void onAction() {
                    RewardsThatWork.getInstance().setGroupId(group.getId());
                    userRepository.set(user.getId(), user.getData()).addListener(new OnAsyncActionListener() {
                        @Override
                        public void onAction() {
                            clientUser.postValue(user);
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
        } else {
            asyncAction.setError("Could not get client user.");
        }
        return asyncAction;
    }
}
