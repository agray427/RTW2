package com.agray427.rewardsthatwork.api.firebase;

import androidx.annotation.NonNull;

import com.agray427.rewardsthatwork.api.UserApi;
import com.agray427.rewardsthatwork.common.AsyncAction;
import com.agray427.rewardsthatwork.common.AsyncResult;
import com.agray427.rewardsthatwork.model.common.Entity;
import com.agray427.rewardsthatwork.model.network.NetworkUser;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserApiImpl implements UserApi {
    private final CollectionReference collection;

    public UserApiImpl() {
        collection = FirebaseFirestore.getInstance().collection("users");
    }

    @NonNull
    @Override
    public AsyncResult<Entity<NetworkUser>> get(@NonNull String id) {
        final AsyncResult<Entity<NetworkUser>> result = new AsyncResult<>();
        collection.document(id).get().continueWith(new Continuation<DocumentSnapshot, Entity<NetworkUser>>() {
            @Override
            public Entity<NetworkUser> then(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful() && task.getResult() != null && task.getResult().exists()) {
                    NetworkUser networkUser = task.getResult().toObject(NetworkUser.class);
                    if (networkUser != null) {
                        return new Entity<>(task.getResult().getId(), networkUser);
                    }
                }
                return null;
            }
        }).addOnCompleteListener(new OnCompleteListener<Entity<NetworkUser>>() {
            @Override
            public void onComplete(@NonNull Task<Entity<NetworkUser>> task) {
                if (task.isSuccessful() && task.getResult() != null) {
                    result.setResult(task.getResult());
                } else {
                    result.setError("Could not get user network data.");
                }
            }
        });
        return result;
    }

    @NonNull
    @Override
    public AsyncAction set(@NonNull String id, @NonNull NetworkUser networkUser) {
        final AsyncAction action = new AsyncAction();
        collection.document(id).set(networkUser).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    action.trigger();
                } else {
                    action.setError("Could not set user network data.");
                }
            }
        });
        return action;
    }
}
