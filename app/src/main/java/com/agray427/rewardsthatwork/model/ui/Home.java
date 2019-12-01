package com.agray427.rewardsthatwork.model.ui;

import androidx.annotation.NonNull;

import com.agray427.rewardsthatwork.model.common.Entity;
import com.agray427.rewardsthatwork.model.domain.CompletedTaskReward;
import com.agray427.rewardsthatwork.model.domain.GroupUser;
import com.agray427.rewardsthatwork.model.domain.UserName;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class Home {
    private final String userId;
    private final String userName;
    private final String points;
//    private final List<PendingRequestItem> requests;
    private final List<CompletedTaskRewardItem> rewards;
    private final List<CompletedTaskRewardItem> tasks;

    public Home(@NonNull Entity<GroupUser> entity) {
        this(entity.getId(), entity.getData());
    }

    public Home(@NonNull String userId, @NonNull GroupUser data) {
        UserName name = data.getUser().getData().getName();
        this.userId = userId;
        this.userName = String.format("%s %s", name.getFirst(), name.getLast());
        this.points = String.valueOf(data.getPoints());
//        this.requests = new ArrayList<>();
        this.rewards = new ArrayList<>();
        for (Map.Entry<String, CompletedTaskReward> entry: data.getRewards().entrySet()) {
            this.rewards.add(new CompletedTaskRewardItem(entry.getKey(), entry.getValue()));
        }
        this.tasks = new ArrayList<>();
        for (Map.Entry<String, CompletedTaskReward> entry: data.getTasks().entrySet()) {
            this.tasks.add(new CompletedTaskRewardItem(entry.getKey(), entry.getValue()));
        }
    }

    public Home(@NonNull String userId, @NonNull String userName, @NonNull String points) {
        this.userId = userId;
        this.userName = userName;
        this.points = points;
        this.rewards = new ArrayList<>();
        this.tasks = new ArrayList<>();
    }

    @NonNull
    public String getUserId() {
        return userId;
    }

    @NonNull
    public String getUserName() {
        return userName;
    }

    @NonNull
    public String getPoints() {
        return points;
    }

    @NonNull
    public List<CompletedTaskRewardItem> getRewards() {
        return rewards;
    }

    @NonNull
    public List<CompletedTaskRewardItem> getTasks() {
        return tasks;
    }
}
