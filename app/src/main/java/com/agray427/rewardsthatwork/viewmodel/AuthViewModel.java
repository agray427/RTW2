package com.agray427.rewardsthatwork.viewmodel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;

import com.agray427.rewardsthatwork.RewardsThatWork;
import com.agray427.rewardsthatwork.api.Apis;
import com.agray427.rewardsthatwork.api.AuthApi;
import com.agray427.rewardsthatwork.common.AsyncAction;
import com.agray427.rewardsthatwork.common.OnAsyncActionListener;
import com.agray427.rewardsthatwork.model.domain.User;
import com.agray427.rewardsthatwork.model.domain.UserName;
import com.agray427.rewardsthatwork.repository.Repositories;
import com.agray427.rewardsthatwork.repository.UserRepository;

public final class AuthViewModel extends ViewModel {
    private final AuthApi authApi;
    private final UserRepository userRepository;

    public AuthViewModel() {
        authApi = Apis.getAuthApi();
        userRepository = Repositories.getUserRepository();
    }

    // Attempt to log in to the app.
    public AsyncAction logIn(@NonNull String email, @NonNull String password) {
        return authApi.logIn(email, password);
    }

    // Attempt to register to the app.
    public AsyncAction register(@NonNull String email, @NonNull String password, @NonNull final String firstName, @NonNull final String lastName) {
        final AsyncAction registerAction = new AsyncAction();
        authApi.register(email, password).addListener(new OnAsyncActionListener() {
            @Override
            public void onAction() {
                String clientId = RewardsThatWork.getInstance().getClientId().getValue();
                if (clientId == null) {
                    registerAction.setError("Could not get the client.");
                } else {
                    User user = new User(new UserName(firstName, lastName));
                    userRepository.set(clientId, user).addListener(new OnAsyncActionListener() {
                        @Override
                        public void onAction() {
                            registerAction.trigger();
                        }

                        @Override
                        public void onError(@Nullable String errorMessage) {
                            registerAction.setError(errorMessage);
                        }
                    });
                }
            }

            @Override
            public void onError(@Nullable String errorMessage) {
                registerAction.setError(errorMessage);
            }
        });
        return registerAction;
    }
}