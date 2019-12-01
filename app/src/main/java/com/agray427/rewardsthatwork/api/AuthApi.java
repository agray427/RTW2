package com.agray427.rewardsthatwork.api;

import androidx.annotation.NonNull;

import com.agray427.rewardsthatwork.common.AsyncAction;

public interface AuthApi {
    @NonNull
    AsyncAction logIn(@NonNull String email, @NonNull String password);
    @NonNull
    AsyncAction register(@NonNull String email, @NonNull String password);
}
