package com.agray427.rewardsthatwork.model.common;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public final class Result<T> {
    private static final int CODE_ERROR = -1;
    private static final int CODE_LOADING = 0;
    private static final int CODE_SUCCESSFUL = 1;

    private final int code;
    private final String message;
    private final T value;

    private Result(int code, @Nullable String message, @Nullable T value) {
        this.code = code;
        this.message = message;
        this.value = value;
    }

    public boolean isError() {
        return code == CODE_ERROR;
    }

    public boolean isLoading() {
        return code == CODE_LOADING;
    }

    public boolean isSuccessful() {
        return code == CODE_SUCCESSFUL;
    }

    @Nullable
    public String getMessage() {
        return message;
    }

    @Nullable
    public T getValue() {
        return value;
    }

    @NonNull
    public static <U> Result<U> error(@NonNull String message) {
        return new Result<>(CODE_ERROR, message, null);
    }

    @NonNull
    public static <U> Result<U> loading() {
        return new Result<>(CODE_LOADING, null, null);
    }

    @NonNull
    public static <U> Result<U> successful(@NonNull U data) {
        return new Result<>(CODE_SUCCESSFUL, null, data);
    }

    @NonNull
    public static <U, V> Result<V> from(@NonNull Result<U> result, @NonNull V newValue) {
        return new Result<>(result.code, result.message, newValue);
    }
}