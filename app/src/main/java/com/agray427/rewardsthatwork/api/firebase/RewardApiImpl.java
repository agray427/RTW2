package com.agray427.rewardsthatwork.api.firebase;

import androidx.annotation.NonNull;

import com.agray427.rewardsthatwork.api.TaskRewardApi;
import com.agray427.rewardsthatwork.common.AsyncAction;
import com.agray427.rewardsthatwork.common.AsyncResult;
import com.agray427.rewardsthatwork.model.common.Entity;
import com.agray427.rewardsthatwork.model.network.NetworkTaskReward;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class RewardApiImpl implements TaskRewardApi {
    private final CollectionReference collection;

    public RewardApiImpl(@NonNull String groupId) {
        this.collection = FirebaseFirestore.getInstance().collection("groups").document(groupId).collection("rewards");
    }

    @NonNull
    @Override
    public AsyncResult<String> create(@NonNull NetworkTaskReward networkReward) {
        final AsyncResult<String> result = new AsyncResult<>();
        final DocumentReference document = collection.document();
        document.set(networkReward).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    result.setResult(document.getId());
                } else {
                    result.setError("Could not create reward network data.");
                }
            }
        });
        return result;
    }

    @NonNull
    @Override
    public AsyncResult<Entity<NetworkTaskReward>> get(@NonNull String id) {
        final AsyncResult<Entity<NetworkTaskReward>> asyncResult = new AsyncResult<>();
        collection.document(id).get().continueWith(new Continuation<DocumentSnapshot, Entity<NetworkTaskReward>>() {
            @Override
            public Entity<NetworkTaskReward> then(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful() && task.getResult() != null && task.getResult().exists()) {
                    NetworkTaskReward networkReward = task.getResult().toObject(NetworkTaskReward.class);
                    if (networkReward != null) {
                        return new Entity<>(task.getResult().getId(), networkReward);
                    }
                }
                return null;
            }
        }).addOnCompleteListener(new OnCompleteListener<Entity<NetworkTaskReward>>() {
            @Override
            public void onComplete(@NonNull Task<Entity<NetworkTaskReward>> task) {
                if (task.isSuccessful() && task.getResult() != null) {
                    asyncResult.setResult(task.getResult());
                } else {
                    asyncResult.setError("Could not get reward network data.");
                }
            }
        });
        return asyncResult;
    }

    @NonNull
    @Override
    public AsyncResult<List<Entity<NetworkTaskReward>>> getAll() {
        final AsyncResult<List<Entity<NetworkTaskReward>>> asyncResult = new AsyncResult<>();
        collection.orderBy("name").get().continueWith(new Continuation<QuerySnapshot, List<Entity<NetworkTaskReward>>>() {
            @Override
            public List<Entity<NetworkTaskReward>> then(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful() && task.getResult() != null) {
                    List<Entity<NetworkTaskReward>> dataList = new ArrayList<>();
                    for (DocumentSnapshot snapshot : task.getResult().getDocuments()) {
                        if (snapshot != null && snapshot.exists()) {
                            NetworkTaskReward networkTaskReward = snapshot.toObject(NetworkTaskReward.class);
                            if (networkTaskReward != null) {
                                dataList.add(new Entity<>(snapshot.getId(), networkTaskReward));
                            }
                        }
                    }
                    return dataList;
                }
                return null;
            }
        }).addOnCompleteListener(new OnCompleteListener<List<Entity<NetworkTaskReward>>>() {
            @Override
            public void onComplete(@NonNull Task<List<Entity<NetworkTaskReward>>> task) {
                if (task.isSuccessful() && task.getResult() != null) {
                    asyncResult.setResult(task.getResult());
                } else {
                    asyncResult.setError("Could not get list of reward network data.");
                }
            }
        });
        return asyncResult;
    }

    @NonNull
    @Override
    public AsyncAction set(@NonNull String id, @NonNull NetworkTaskReward networkTaskReward) {
        final AsyncAction asyncAction = new AsyncAction();
        collection.document(id).set(networkTaskReward).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    asyncAction.trigger();
                } else {
                    asyncAction.setError("Could not set reward network data.");
                }
            }
        });
        return asyncAction;
    }
}