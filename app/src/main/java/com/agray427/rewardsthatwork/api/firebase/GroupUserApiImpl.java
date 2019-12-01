package com.agray427.rewardsthatwork.api.firebase;

import androidx.annotation.NonNull;

import com.agray427.rewardsthatwork.api.GroupUserApi;
import com.agray427.rewardsthatwork.common.AsyncAction;
import com.agray427.rewardsthatwork.common.AsyncResult;
import com.agray427.rewardsthatwork.model.common.Entity;
import com.agray427.rewardsthatwork.model.network.NetworkGroupUser;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class GroupUserApiImpl implements GroupUserApi {
    private final CollectionReference collection;

    public GroupUserApiImpl(@NonNull String groupId) {
        this.collection = FirebaseFirestore.getInstance().collection("groups").document(groupId).collection("users");
    }

    @NonNull
    @Override
    public AsyncResult<Entity<NetworkGroupUser>> get(@NonNull String id) {
        final AsyncResult<Entity<NetworkGroupUser>> asyncResult = new AsyncResult<>();
        collection.document(id).get().continueWith(new Continuation<DocumentSnapshot, Entity<NetworkGroupUser>>() {
            @Override
            public Entity<NetworkGroupUser> then(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful() && task.getResult() != null && task.getResult().exists()) {
                    NetworkGroupUser networkGroupUser = task.getResult().toObject(NetworkGroupUser.class);
                    if (networkGroupUser != null) {
                        return new Entity<>(task.getResult().getId(), networkGroupUser);
                    }
                }
                return null;
            }
        }).addOnCompleteListener(new OnCompleteListener<Entity<NetworkGroupUser>>() {
            @Override
            public void onComplete(@NonNull Task<Entity<NetworkGroupUser>> task) {
                if (task.isSuccessful() && task.getResult() != null) {
                    asyncResult.setResult(task.getResult());
                } else {
                    asyncResult.setError("Could not get group user network data.");
                }
            }
        });
        return asyncResult;
    }

    @NonNull
    @Override
    public AsyncResult<List<Entity<NetworkGroupUser>>> getAll() {
        final AsyncResult<List<Entity<NetworkGroupUser>>> asyncResult = new AsyncResult<>();
        collection.orderBy("groupRole", Query.Direction.DESCENDING).get().continueWith(new Continuation<QuerySnapshot, List<Entity<NetworkGroupUser>>>() {
            @Override
            public List<Entity<NetworkGroupUser>> then(@NonNull Task<QuerySnapshot> task) throws Exception {
                if (task.isSuccessful() && task.getResult() != null) {
                    List<Entity<NetworkGroupUser>> dataList = new ArrayList<>();
                    for (DocumentSnapshot snapshot : task.getResult().getDocuments()) {
                        if (snapshot != null && snapshot.exists()) {
                            NetworkGroupUser networkGroupUser = snapshot.toObject(NetworkGroupUser.class);
                            if (networkGroupUser != null) {
                                dataList.add(new Entity<>(snapshot.getId(), networkGroupUser));
                            }
                        }
                    }
                    return dataList;
                }
                return null;
            }
        }).addOnCompleteListener(new OnCompleteListener<List<Entity<NetworkGroupUser>>>() {
            @Override
            public void onComplete(@NonNull Task<List<Entity<NetworkGroupUser>>> task) {
                if (task.isSuccessful() && task.getResult() != null) {
                    asyncResult.setResult(task.getResult());
                } else {
                    asyncResult.setError("Could not get list of group user network data.");
                }
            }
        });
        return asyncResult;
    }

    @Override
    public AsyncAction set(@NonNull String id, @NonNull NetworkGroupUser networkGroupUser) {
        final AsyncAction asyncAction = new AsyncAction();
        collection.document(id).set(networkGroupUser).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    asyncAction.trigger();
                } else {
                    asyncAction.setError("Could not set group user network data.");
                }
            }
        });
        return asyncAction;
    }
}
