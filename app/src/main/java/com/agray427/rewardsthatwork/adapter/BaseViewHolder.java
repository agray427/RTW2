package com.agray427.rewardsthatwork.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {
    private T boundData;

    BaseViewHolder(@NonNull View itemView, @Nullable final BaseListAdapter.OnSelectedListener<T> listener) {
        super(itemView);
        if (listener != null) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onSelected(boundData);
                }
            });
        }
    }

    public void bindData(@NonNull T data) {
        this.boundData = data;
    }
}
