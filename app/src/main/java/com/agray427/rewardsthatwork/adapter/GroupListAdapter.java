package com.agray427.rewardsthatwork.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.agray427.rewardsthatwork.R;
import com.agray427.rewardsthatwork.model.ui.GroupItem;

import java.util.List;

public final class GroupListAdapter extends BaseListAdapter<GroupItem, GroupListAdapter.ViewHolder> {
    public GroupListAdapter(@Nullable List<GroupItem> dataList) {
        super(dataList);
    }

    public List<GroupItem> debugList() {
        return dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_group, parent, false);
        return new ViewHolder(view, onSelectedListener);
    }

    public final class ViewHolder extends BaseViewHolder<GroupItem> {
        private TextView textName, textUserCount;

        ViewHolder(@NonNull View itemView, @Nullable OnSelectedListener<GroupItem> listener) {
            super(itemView, listener);
            textName = itemView.findViewById(R.id.text_name);
            textUserCount = itemView.findViewById(R.id.text_user_count);
        }

        @Override
        public void bindData(@NonNull GroupItem data) {
            super.bindData(data);
            textName.setText(data.getName());
            textUserCount.setText(data.getUserCount());
        }
    }
}
