package com.agray427.rewardsthatwork.model.ui;

import androidx.annotation.NonNull;

import com.agray427.rewardsthatwork.model.common.Entity;
import com.agray427.rewardsthatwork.model.domain.Group;
import com.agray427.rewardsthatwork.model.domain.TaskReward;

import java.util.ArrayList;
import java.util.List;

public final class TaskRewardList {
    private final List<TaskRewardItem> list;

    public TaskRewardList() {
        this.list = new ArrayList<>();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    @NonNull
    public List<TaskRewardItem> getList() {
        return list;
    }

    public void populateTasks(@NonNull Entity<Group> entity) {
        populateTasks(entity.getData());
    }

    public void populateTasks(@NonNull Group data) {
        for (Entity<TaskReward> entity: data.getTasks()) {
            list.add(new TaskRewardItem(entity));
        }
    }

    public void populateRewards(@NonNull Entity<Group> entity) {
        populateRewards(entity.getData());
    }

    public void populateRewards(@NonNull Group data) {
        for (Entity<TaskReward> entity: data.getRewards()) {
            list.add(new TaskRewardItem(entity));
        }
    }
}
