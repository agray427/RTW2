package com.agray427.rewardsthatwork.api;

import androidx.annotation.NonNull;

import com.agray427.rewardsthatwork.api.firebase.AuthApiImpl;
import com.agray427.rewardsthatwork.api.firebase.GroupApiImpl;
import com.agray427.rewardsthatwork.api.firebase.GroupUserApiImpl;
import com.agray427.rewardsthatwork.api.firebase.RewardApiImpl;
import com.agray427.rewardsthatwork.api.firebase.TaskApiImpl;
import com.agray427.rewardsthatwork.api.firebase.UserApiImpl;

public final class Apis {
    private static final AuthApi AUTH_API;
    private static final UserApi USER_API;
    private static final GroupApi GROUP_API;

    static {
        AUTH_API = new AuthApiImpl();
        USER_API = new UserApiImpl();
        GROUP_API = new GroupApiImpl();
    }

    @NonNull
    public static AuthApi getAuthApi() {
        return AUTH_API;
    }

    @NonNull
    public static UserApi getUserApi() {
        return USER_API;
    }

    @NonNull
    public static GroupApi getGroupApi() {
        return GROUP_API;
    }

    @NonNull
    public static GroupUserApi getGroupUserApi(@NonNull String groupId) {
        return new GroupUserApiImpl(groupId);
    }

    @NonNull
    public static TaskRewardApi getRewardApi(@NonNull String groupId) {
        return new RewardApiImpl(groupId);
    }

    @NonNull
    public static TaskRewardApi getTaskApi(@NonNull String groupId) {
        return new TaskApiImpl(groupId);
    }
}
