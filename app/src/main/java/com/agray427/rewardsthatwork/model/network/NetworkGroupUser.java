package com.agray427.rewardsthatwork.model.network;

import androidx.annotation.NonNull;

import com.agray427.rewardsthatwork.common.GroupRole;

public final class NetworkGroupUser {
    private final String groupId;
    private final String userId;
    private final GroupRole groupRole;
    private final int points;

    public NetworkGroupUser() { this("", "", GroupRole.BASIC, 0); }
    public NetworkGroupUser(@NonNull String groupId, @NonNull String userId, @NonNull GroupRole groupRole, int points) {
        this.groupId = groupId;
        this.userId = userId;
        this.groupRole = groupRole;
        this.points = points;
    }

    @NonNull
    public String getGroupId() {
        return groupId;
    }

    @NonNull
    public String getUserId() {
        return userId;
    }

    @NonNull
    public GroupRole getGroupRole() {
        return groupRole;
    }

    public int getPoints() {
        return points;
    }
}
