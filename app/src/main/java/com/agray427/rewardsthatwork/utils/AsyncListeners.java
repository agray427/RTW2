package com.agray427.rewardsthatwork.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.agray427.rewardsthatwork.common.AsyncAction;
import com.agray427.rewardsthatwork.common.AsyncResult;
import com.agray427.rewardsthatwork.common.OnAsyncActionListener;
import com.agray427.rewardsthatwork.common.OnAsyncResultListener;

import java.util.ArrayList;
import java.util.List;

public final class AsyncListeners {
    public static <T> AsyncResult<List<AsyncResult<T>>> whenAllResultsComplete(@NonNull final List<AsyncResult<T>> asyncResults) {
        final AsyncResult<List<AsyncResult<T>>> asyncResult = new AsyncResult<>();
        if (asyncResults.isEmpty()) {
             asyncResult.setResult(new ArrayList<AsyncResult<T>>());
        } else {
            OnAsyncResultCompleteListener<T> listener = new OnAsyncResultCompleteListener<>(asyncResults.size(), new OnAsyncActionListener() {
                @Override
                public void onAction() {
                    asyncResult.setResult(asyncResults);
                }

                @Override
                public void onError(@Nullable String errorMessage) {
                    asyncResult.setError(errorMessage);
                }
            });

            for (AsyncResult<T> result : asyncResults) {
                result.addListener(listener);
            }
        }
        return asyncResult;
    }

    public static AsyncAction whenAllActionsComplete(@NonNull List<AsyncAction> asyncActions) {
        final AsyncAction asyncAction = new AsyncAction();
        if (asyncActions.isEmpty()) {
            asyncAction.trigger();
        } else {
            OnAsyncActionCompleteListener listener = new OnAsyncActionCompleteListener(asyncActions.size(), new OnAsyncActionListener() {
                @Override
                public void onAction() {
                    asyncAction.trigger();
                }

                @Override
                public void onError(@Nullable String errorMessage) {
                    asyncAction.setError(errorMessage);
                }
            });

            for (AsyncAction action : asyncActions) {
                action.addListener(listener);
            }
        }
        return asyncAction;
    }

    private static class OnAsyncActionCompleteListener implements OnAsyncActionListener {
        private int count;
        private final int size;
        private final OnAsyncActionListener onCompleteListener;

        OnAsyncActionCompleteListener(int size, @NonNull OnAsyncActionListener onCompleteListener) {
            this.count = 0;
            this.size = size;
            this.onCompleteListener = onCompleteListener;
        }

        @Override
        public void onAction() {
            count += 1;
            check();
        }

        @Override
        public void onError(@Nullable String errorMessage) {
            count += 1;
            check();
        }

        private void check() {
            if (count >= size) {
                onCompleteListener.onAction();
            }
        }
    }

    private static class OnAsyncResultCompleteListener<T> implements OnAsyncResultListener<T> {
        private int count;
        private final int size;
        private final OnAsyncActionListener onCompleteListener;

        OnAsyncResultCompleteListener(int size, @NonNull OnAsyncActionListener onCompleteListener) {
            this.count = 0;
            this.size = size;
            this.onCompleteListener = onCompleteListener;
        }

        @Override
        public void onResult(@NonNull T result) {
            count += 1;
            check();
        }

        @Override
        public void onError(@Nullable String errorMessage) {
            count += 1;
            check();
        }

        private void check() {
            if (count >= size) {
                onCompleteListener.onAction();
            }
        }
    }
}
