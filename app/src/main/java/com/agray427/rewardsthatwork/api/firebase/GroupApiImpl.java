package com.agray427.rewardsthatwork.api.firebase;

import androidx.annotation.NonNull;

import com.agray427.rewardsthatwork.api.GroupApi;
import com.agray427.rewardsthatwork.common.AsyncAction;
import com.agray427.rewardsthatwork.common.AsyncResult;
import com.agray427.rewardsthatwork.model.common.Entity;
import com.agray427.rewardsthatwork.model.network.NetworkGroup;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class GroupApiImpl implements GroupApi {
    private final CollectionReference collection;

    public GroupApiImpl() {
        this.collection = FirebaseFirestore.getInstance().collection("groups");
    }

    @NonNull
    @Override
    public AsyncResult<String> create(@NonNull NetworkGroup networkGroup) {
        final AsyncResult<String> result = new AsyncResult<>();
        final DocumentReference document = collection.document();
        document.set(networkGroup).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    result.setResult(document.getId());
                } else {
                    result.setError("Could not create group network data.");
                }
            }
        });
        return result;
    }

    @NonNull
    @Override
    public AsyncResult<Entity<NetworkGroup>> get(@NonNull String id) {
        final AsyncResult<Entity<NetworkGroup>> result = new AsyncResult<>();
        collection.document(id).get().continueWith(new Continuation<DocumentSnapshot, Entity<NetworkGroup>>() {
            @Override
            public Entity<NetworkGroup> then(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful() && task.getResult() != null && task.getResult().exists()) {
                    NetworkGroup networkGroup = task.getResult().toObject(NetworkGroup.class);
                    if (networkGroup != null) {
                        return new Entity<>(task.getResult().getId(), networkGroup);
                    }
                }
                return null;
            }
        }).addOnCompleteListener(new OnCompleteListener<Entity<NetworkGroup>>() {
            @Override
            public void onComplete(@NonNull Task<Entity<NetworkGroup>> task) {
                if (task.isSuccessful() && task.getResult() != null) {
                    result.setResult(task.getResult());
                } else {
                    result.setError("Could not get group network data.");
                }
            }
        });
        return result;
    }

    @NonNull
    @Override
    public AsyncResult<Entity<NetworkGroup>> getFromCode(@NonNull String inviteCode) {
        final AsyncResult<Entity<NetworkGroup>> result = new AsyncResult<>();
        collection.whereEqualTo("inviteCode", inviteCode).limit(1).get().continueWith(new Continuation<QuerySnapshot, Entity<NetworkGroup>>() {
            @Override
            public Entity<NetworkGroup> then(@NonNull Task<QuerySnapshot> task) throws Exception {
                if (task.isSuccessful() && task.getResult() != null && !task.getResult().isEmpty()) {
                    DocumentSnapshot snapshot = task.getResult().getDocuments().get(0);
                    if (snapshot != null && snapshot.exists()) {
                        NetworkGroup networkGroup = snapshot.toObject(NetworkGroup.class);
                        if (networkGroup != null) {
                            return new Entity<>(snapshot.getId(), networkGroup);
                        }
                    }
                }
                return null;
            }
        }).addOnCompleteListener(new OnCompleteListener<Entity<NetworkGroup>>() {
            @Override
            public void onComplete(@NonNull Task<Entity<NetworkGroup>> task) {
                if (task.isSuccessful() && task.getResult() != null) {
                    result.setResult(task.getResult());
                } else {
                    result.setError("Could not get group network data.");
                }
            }
        });
        return result;
    }

    @NonNull
    @Override
    public AsyncAction set(@NonNull String id, @NonNull NetworkGroup networkGroup) {
        final AsyncAction action = new AsyncAction();
        collection.document(id).set(networkGroup).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    action.trigger();
                } else {
                    action.setError("Could not set group network data.");
                }
            }
        });
        return action;
    }
}
