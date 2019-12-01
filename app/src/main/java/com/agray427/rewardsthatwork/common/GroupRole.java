package com.agray427.rewardsthatwork.common;

import androidx.annotation.NonNull;

public enum GroupRole {
    BASIC("Basic"),
    ADMIN("Admin"),
    OWNER("Owner");

    private final String text;

    GroupRole(@NonNull String text) {
        this.text = text;
    }

    @NonNull
    public String getText() {
        return text;
    }
}
