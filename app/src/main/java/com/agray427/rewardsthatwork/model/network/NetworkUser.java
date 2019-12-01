package com.agray427.rewardsthatwork.model.network;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public final class NetworkUser {
    private final String firstName;
    private final String lastName;
    private final List<String> groupIds;

    public NetworkUser() { this("", ""); }
    public NetworkUser(@NonNull String firstName, @NonNull String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.groupIds = new ArrayList<>();
    }

    @NonNull
    public String getFirstName() {
        return firstName;
    }

    @NonNull
    public String getLastName() {
        return lastName;
    }

    @NonNull
    public List<String> getGroupIds() {
        return groupIds;
    }
}
