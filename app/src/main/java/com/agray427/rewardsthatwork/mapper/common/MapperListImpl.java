package com.agray427.rewardsthatwork.mapper.common;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.agray427.rewardsthatwork.common.AsyncResult;
import com.agray427.rewardsthatwork.common.OnAsyncResultListener;
import com.agray427.rewardsthatwork.mapper.Mapper;
import com.agray427.rewardsthatwork.utils.AsyncListeners;

import java.util.ArrayList;
import java.util.List;

public class MapperListImpl<X, Y> implements Mapper<List<X>, List<Y>> {
    private final Mapper<X, Y> mapper;

    public MapperListImpl(@NonNull Mapper<X, Y> mapper) {
        this.mapper = mapper;
    }

    @NonNull
    @Override
    public AsyncResult<List<Y>> mapTo(@NonNull final List<X> listX) {
        final AsyncResult<List<Y>> asyncResult = new AsyncResult<>();
        List<AsyncResult<Y>> asyncResults = new ArrayList<>();
        for (X dataX : listX) {
            asyncResults.add(mapper.mapTo(dataX));
        }
        AsyncListeners.whenAllResultsComplete(asyncResults).addListener(new OnAsyncResultListener<List<AsyncResult<Y>>>() {
            @Override
            public void onResult(@NonNull List<AsyncResult<Y>> resultList) {
                List<Y> listY = new ArrayList<>();
                for (AsyncResult<Y> result : resultList) {
                    if (result.isSuccessful() && result.getResult() != null) {
                        listY.add(result.getResult());
                    }
                }
                asyncResult.setResult(listY);
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
    public AsyncResult<List<X>> mapFrom(@NonNull List<Y> listY) {
        final AsyncResult<List<X>> asyncResult = new AsyncResult<>();
        List<AsyncResult<X>> asyncResults = new ArrayList<>();
        for (Y dataY : listY) {
            asyncResults.add(mapper.mapFrom(dataY));
        }
        AsyncListeners.whenAllResultsComplete(asyncResults).addListener(new OnAsyncResultListener<List<AsyncResult<X>>>() {
            @Override
            public void onResult(@NonNull List<AsyncResult<X>> resultList) {
                List<X> listX = new ArrayList<>();
                for (AsyncResult<X> result : resultList) {
                    if (result.isSuccessful() && result.getResult() != null) {
                        listX.add(result.getResult());
                    }
                }
                asyncResult.setResult(listX);
            }

            @Override
            public void onError(@Nullable String errorMessage) {
                asyncResult.setError(errorMessage);
            }
        });
        return asyncResult;
    }
}
