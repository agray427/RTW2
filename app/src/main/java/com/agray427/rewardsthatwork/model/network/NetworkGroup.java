package com.agray427.rewardsthatwork.model.network;

import androidx.annotation.NonNull;

public final class NetworkGroup {
    private final String name;
    private final String inviteCode;

    public NetworkGroup() { this("", ""); }
    public NetworkGroup(String name, String inviteCode) {
        this.name = name;
        this.inviteCode = inviteCode;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getInviteCode() {
        return inviteCode;
    }
}
