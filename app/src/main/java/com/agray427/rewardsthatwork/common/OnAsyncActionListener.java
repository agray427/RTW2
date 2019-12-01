package com.agray427.rewardsthatwork.common;

import androidx.annotation.Nullable;

public interface OnAsyncActionListener {
    void onAction();
    void onError(@Nullable String errorMessage);
}
