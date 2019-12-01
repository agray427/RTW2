package com.agray427.rewardsthatwork.repository;

import androidx.annotation.NonNull;

import com.agray427.rewardsthatwork.repository.firebase.GroupRepositoryImpl;
import com.agray427.rewardsthatwork.repository.firebase.GroupUserRepositoryImpl;
import com.agray427.rewardsthatwork.repository.firebase.RewardRepositoryImpl;
import com.agray427.rewardsthatwork.repository.firebase.TaskRepositoryImpl;
import com.agray427.rewardsthatwork.repository.firebase.UserRepositoryImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class Repositories {
    private static final UserRepository USER_REPOSITORY;
    private static final GroupRepository GROUP_REPOSITORY;
    private static final Map<String, GroupUserRepository> GROUP_USER_REPOSITORY_MAP;
    private static final Map<String, RewardRepository> REWARD_REPOSITORY_MAP;
    private static final Map<String, TaskRepository> TASK_REPOSITORY_MAP;

    static {
        USER_REPOSITORY = new UserRepositoryImpl();
        GROUP_REPOSITORY = new GroupRepositoryImpl();
        GROUP_USER_REPOSITORY_MAP = new HashMap<>();
        REWARD_REPOSITORY_MAP = new HashMap<>();
        TASK_REPOSITORY_MAP = new HashMap<>();
    }

    @NonNull
    public static UserRepository getUserRepository() {
        return USER_REPOSITORY;
    }

    @NonNull
    public static GroupRepository getGroupRepository() {
        return GROUP_REPOSITORY;
    }

    @NonNull
    public static GroupUserRepository getGroupUserRepository(@NonNull String groupId) {
        if (!GROUP_USER_REPOSITORY_MAP.containsKey(groupId)) {
            GROUP_USER_REPOSITORY_MAP.put(groupId, new GroupUserRepositoryImpl(groupId));
        }
        return Objects.requireNonNull(GROUP_USER_REPOSITORY_MAP.get(groupId), "Map should never contain null value.");
    }

    @NonNull
    public static RewardRepository getRewardRepository(@NonNull String groupId) {
        if (!REWARD_REPOSITORY_MAP.containsKey(groupId)) {
            REWARD_REPOSITORY_MAP.put(groupId, new RewardRepositoryImpl(groupId));
        }
        return Objects.requireNonNull(REWARD_REPOSITORY_MAP.get(groupId), "Map should never contain null value.");
    }

    @NonNull
    public static TaskRepository getTaskRepository(@NonNull String groupId) {
        if (!TASK_REPOSITORY_MAP.containsKey(groupId)) {
            TASK_REPOSITORY_MAP.put(groupId, new TaskRepositoryImpl(groupId));
        }
        return Objects.requireNonNull(TASK_REPOSITORY_MAP.get(groupId), "Map should never contain null value.");
    }
}
