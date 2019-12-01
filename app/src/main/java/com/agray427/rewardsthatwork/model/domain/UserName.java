package com.agray427.rewardsthatwork.model.domain;

import androidx.annotation.NonNull;

public final class UserName {
    private String first;
    private String last;

    public UserName(@NonNull String first, @NonNull String last) {
        this.first = first;
        this.last = last;
    }

    @NonNull
    public String getFirst() {
        return first;
    }

    public void setFirst(@NonNull String first) {
        this.first = first;
    }

    @NonNull
    public String getLast() {
        return last;
    }

    public void setLast(@NonNull String last) {
        this.last = last;
    }
}
