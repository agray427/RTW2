package com.agray427.rewardsthatwork.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class BaseListAdapter<T, VH extends BaseViewHolder<T>> extends RecyclerView.Adapter<VH> {
    protected List<T> dataList;
    protected OnSelectedListener<T> onSelectedListener;

    public BaseListAdapter(@Nullable List<T> dataList) {
        if (dataList != null) {
            submitList(dataList);
        }
    }

    public void submitList(@NonNull List<T> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    public void setOnSelectedListener(@Nullable OnSelectedListener<T> onSelectedListener) {
        this.onSelectedListener = onSelectedListener;
    }

    @Nullable
    public T getAt(int index) {
        if (index >= 0 && index < getItemCount()) {
            return dataList.get(index);
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        T data = getAt(position);
        if (data != null) {
            holder.bindData(data);
        }
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    public interface OnSelectedListener<T> {
        void onSelected(@Nullable T data);
    }
}
