package com.agray427.rewardsthatwork.common;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface OnAsyncResultListener<T> {
    void onResult(@NonNull T result);
    void onError(@Nullable String errorMessage);
}
