package com.agray427.rewardsthatwork.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.agray427.rewardsthatwork.R;
import com.agray427.rewardsthatwork.model.ui.CompletedTaskRewardItem;

import java.util.List;

public final class CompletedTaskRewardListAdapter extends BaseListAdapter<CompletedTaskRewardItem, CompletedTaskRewardListAdapter.ViewHolder> {
    public CompletedTaskRewardListAdapter(@Nullable List<CompletedTaskRewardItem> dataList) {
        super(dataList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_task_reward, parent, false);
        return new ViewHolder(view, onSelectedListener);
    }

    public final class ViewHolder extends BaseViewHolder<CompletedTaskRewardItem> {
        private TextView textName, textPoints;

        ViewHolder(@NonNull View itemView, @Nullable OnSelectedListener<CompletedTaskRewardItem> listener) {
            super(itemView, listener);
            textName = itemView.findViewById(R.id.text_name);
            textPoints = itemView.findViewById(R.id.text_points);
        }

        @Override
        public void bindData(@NonNull CompletedTaskRewardItem data) {
            super.bindData(data);
            textName.setText(data.getFormattedName());
            textPoints.setText(data.getPoints());
        }
    }
}
