package com.agray427.rewardsthatwork.mapper;

import androidx.annotation.NonNull;

import com.agray427.rewardsthatwork.mapper.firebase.GroupMapper;
import com.agray427.rewardsthatwork.mapper.firebase.GroupUserMapper;
import com.agray427.rewardsthatwork.mapper.firebase.TaskRewardMapper;
import com.agray427.rewardsthatwork.mapper.firebase.UserMapper;

public final class Mappers {
    private static final GroupMapper GROUP_MAPPER;
    private static final GroupUserMapper GROUP_USER_MAPPER;
    private static final TaskRewardMapper TASK_REWARD_MAPPER;
    private static final UserMapper USER_MAPPER;

    static {
        GROUP_MAPPER = new GroupMapper();
        GROUP_USER_MAPPER = new GroupUserMapper();
        TASK_REWARD_MAPPER = new TaskRewardMapper();
        USER_MAPPER = new UserMapper();
    }

    @NonNull
    public static GroupMapper getGroupMapper() {
        return GROUP_MAPPER;
    }

    @NonNull
    public static GroupUserMapper getGroupUserMapper() {
        return GROUP_USER_MAPPER;
    }

    @NonNull
    public static TaskRewardMapper getTaskRewardMapper() {
        return TASK_REWARD_MAPPER;
    }

    @NonNull
    public static UserMapper getUserMapper() {
        return USER_MAPPER;
    }
}
