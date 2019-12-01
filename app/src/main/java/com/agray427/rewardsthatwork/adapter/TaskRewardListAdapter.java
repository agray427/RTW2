package com.agray427.rewardsthatwork.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.agray427.rewardsthatwork.R;
import com.agray427.rewardsthatwork.model.ui.TaskRewardItem;

import java.util.List;

public final class TaskRewardListAdapter extends BaseListAdapter<TaskRewardItem, TaskRewardListAdapter.ViewHolder> {
    public TaskRewardListAdapter(@Nullable List<TaskRewardItem> dataList) {
        super(dataList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_task_reward, parent, false);
        return new ViewHolder(view, onSelectedListener);
    }

    public final class ViewHolder extends BaseViewHolder<TaskRewardItem> {
        private TextView textName, textPoints;

        ViewHolder(@NonNull View itemView, @Nullable OnSelectedListener<TaskRewardItem> listener) {
            super(itemView, listener);
            textName = itemView.findViewById(R.id.text_name);
            textPoints = itemView.findViewById(R.id.text_points);
        }

        @Override
        public void bindData(@NonNull TaskRewardItem data) {
            super.bindData(data);
            textName.setText(data.getName());
            textPoints.setText(data.getPoints());
        }
    }
}
