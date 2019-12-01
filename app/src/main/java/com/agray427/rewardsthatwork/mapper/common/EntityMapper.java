package com.agray427.rewardsthatwork.mapper.common;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.agray427.rewardsthatwork.common.AsyncResult;
import com.agray427.rewardsthatwork.common.OnAsyncResultListener;
import com.agray427.rewardsthatwork.mapper.Mapper;
import com.agray427.rewardsthatwork.model.common.Entity;

public final class EntityMapper<X, Y> implements Mapper<Entity<X>, Entity<Y>> {
    private final Mapper<X, Y> mapper;

    public EntityMapper(@NonNull Mapper<X, Y> mapper) {
        this.mapper = mapper;
    }

    @NonNull
    @Override
    public AsyncResult<Entity<Y>> mapTo(@NonNull final Entity<X> entityX) {
        final AsyncResult<Entity<Y>> asyncResult = new AsyncResult<>();
        mapper.mapTo(entityX.getData()).addListener(new OnAsyncResultListener<Y>() {
            @Override
            public void onResult(@NonNull Y dataY) {
                asyncResult.setResult(new Entity<Y>(entityX.getId(), dataY));
            }

            @Override
            public void onError(@Nullable String errorMessage) {
                asyncResult.setError(errorMessage);
            }
        });
        return asyncResult;
    }

    @NonNull
    @Override
    public AsyncResult<Entity<X>> mapFrom(@NonNull final Entity<Y> entityY) {
        final AsyncResult<Entity<X>> asyncResult = new AsyncResult<>();
        mapper.mapFrom(entityY.getData()).addListener(new OnAsyncResultListener<X>() {
            @Override
            public void onResult(@NonNull X dataX) {
                asyncResult.setResult(new Entity<X>(entityY.getId(), dataX));
            }

            @Override
            public void onError(@Nullable String errorMessage) {
                asyncResult.setError(errorMessage);
            }
        });
        return asyncResult;
    }
}
