package com.agray427.rewardsthatwork.model.ui;

import androidx.annotation.NonNull;

import com.agray427.rewardsthatwork.model.common.Entity;
import com.agray427.rewardsthatwork.model.domain.User;

public final class UserData {
    private final String id;
    private final String firstName;
    private final String lastName;

    public UserData(@NonNull Entity<User> entity) {
        this(entity.getId(), entity.getData());
    }

    public UserData(@NonNull String id, @NonNull User data) {
        this.id = id;
        this.firstName = data.getName().getFirst();
        this.lastName = data.getName().getLast();
    }

    public UserData(@NonNull String id, @NonNull String firstName, @NonNull String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public String getFirstName() {
        return firstName;
    }

    @NonNull
    public String getLastName() {
        return lastName;
    }
}
