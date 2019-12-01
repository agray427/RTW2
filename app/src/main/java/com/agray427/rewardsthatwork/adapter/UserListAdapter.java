package com.agray427.rewardsthatwork.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.agray427.rewardsthatwork.R;
import com.agray427.rewardsthatwork.model.ui.UserItem;

import java.util.List;

public final class UserListAdapter extends BaseListAdapter<UserItem, UserListAdapter.ViewHolder> {
    public UserListAdapter(@Nullable List<UserItem> dataList) {
        super(dataList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_user, parent, false);
        return new ViewHolder(view, onSelectedListener);
    }

    public final class ViewHolder extends BaseViewHolder<UserItem> {
        private TextView textName, textRole;

        ViewHolder(@NonNull View itemView, @Nullable OnSelectedListener<UserItem> listener) {
            super(itemView, listener);
            textName = itemView.findViewById(R.id.text_name);
            textRole = itemView.findViewById(R.id.text_role);
        }

        @Override
        public void bindData(@NonNull UserItem data) {
            super.bindData(data);
            textName.setText(data.getName());
            textRole.setText(data.getRole());
        }
    }
}
