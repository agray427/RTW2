package com.agray427.rewardsthatwork.api.firebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.agray427.rewardsthatwork.RewardsThatWork;
import com.agray427.rewardsthatwork.api.AuthApi;
import com.agray427.rewardsthatwork.common.AsyncAction;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AuthApiImpl implements AuthApi {
    private FirebaseAuth firebaseAuth;

    public AuthApiImpl() {
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public AsyncAction logIn(@NonNull String email, @NonNull String password) {
        final AsyncAction asyncAction = new AsyncAction();
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful() && task.getResult() != null && task.getResult().getUser() != null) {
                    setClientId(task.getResult().getUser().getUid());
                    asyncAction.trigger();
                } else {
                    asyncAction.setError("Could not log in.");
                }
            }
        });
        return asyncAction;
    }

    @NonNull
    @Override
    public AsyncAction register(@NonNull String email, @NonNull String password) {
        final AsyncAction asyncAction = new AsyncAction();
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful() && task.getResult() != null && task.getResult().getUser() != null) {
                    setClientId(task.getResult().getUser().getUid());
                    asyncAction.trigger();
                } else {
                    asyncAction.setError("Could not register.");
                }
            }
        });
        return asyncAction;
    }

    private void setClientId(@NonNull String id) {
        RewardsThatWork.getInstance().setClientId(id);
    }
}
