package com.agray427.rewardsthatwork;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public final class RewardsThatWork extends Application {
    private static RewardsThatWork instance;

    public static RewardsThatWork getInstance() {
        return instance;
    }

    private final MutableLiveData<String> clientId;
    private final MutableLiveData<String> groupId;
    private final MutableLiveData<String> rewardId;
    private final MutableLiveData<String> taskId;
    private final MutableLiveData<String> userId;

    public RewardsThatWork() {
        this.clientId = new MutableLiveData<>();
        this.groupId = new MutableLiveData<>();
        this.rewardId = new MutableLiveData<>();
        this.taskId = new MutableLiveData<>();
        this.userId = new MutableLiveData<>();
    }

    @NonNull
    public LiveData<String> getClientId() {
        return clientId;
    }

    public void setClientId(@NonNull String id) {
        clientId.setValue(id);
    }

    @NonNull
    public LiveData<String> getGroupId() {
        return groupId;
    }

    public void setGroupId(@NonNull String id) {
        groupId.setValue(id);
    }

    @NonNull
    public LiveData<String> getRewardId() {
        return rewardId;
    }

    public void setRewardId(@NonNull String id) {
        rewardId.setValue(id);
    }

    @NonNull
    public LiveData<String> getTaskId() {
        return taskId;
    }

    public void setTaskId(@NonNull String id) {
        taskId.setValue(id);
    }

    @NonNull
    public LiveData<String> getUserId() {
        return userId;
    }

    public void setUserId(@NonNull String id) {
        userId.setValue(id);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (instance == null) {
            instance = this;
        }
    }
}
