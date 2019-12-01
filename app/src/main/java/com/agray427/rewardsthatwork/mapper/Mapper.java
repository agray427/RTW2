package com.agray427.rewardsthatwork.mapper;

import androidx.annotation.NonNull;

import com.agray427.rewardsthatwork.common.AsyncResult;

public interface Mapper<X, Y> {
    @NonNull
    AsyncResult<Y> mapTo(@NonNull X dataX);
    @NonNull
    AsyncResult<X> mapFrom(@NonNull Y dataY);
}
