package com.agray427.rewardsthatwork.fragment.reward;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.agray427.rewardsthatwork.R;
import com.agray427.rewardsthatwork.RewardsThatWork;
import com.agray427.rewardsthatwork.adapter.BaseListAdapter;
import com.agray427.rewardsthatwork.adapter.TaskRewardListAdapter;
import com.agray427.rewardsthatwork.model.ui.TaskRewardItem;
import com.agray427.rewardsthatwork.model.ui.TaskRewardList;
import com.agray427.rewardsthatwork.viewmodel.MainViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class RewardListFragment extends Fragment {
    private static final String TAG = "RewardListFragment";

    private MainViewModel viewModel;
    private NavController navController;
    private TextView textEmpty;
    private RecyclerView rvList;
    private FloatingActionButton fab;
    private boolean selected;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_task_reward_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setupView(view);
        setupRecycler();
        setupFab();
        showEmpty(false);
        showLoading(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        selected = false;
    }

    private void setupView(@NonNull View view) {
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        navController = Navigation.findNavController(view);
        textEmpty = view.findViewById(R.id.text_empty);
        rvList = view.findViewById(R.id.rv_list);
        fab = view.findViewById(R.id.fab);
    }

    private void setupRecycler() {
        if (rvList != null) {
            final TaskRewardListAdapter adapter = new TaskRewardListAdapter(null);
            adapter.setOnSelectedListener(new BaseListAdapter.OnSelectedListener<TaskRewardItem>() {
                @Override
                public void onSelected(@Nullable TaskRewardItem data) {
                    if (data != null && !selected) {
                        RewardsThatWork.getInstance().setRewardId(data.getId());
                        navController.navigate(R.id.action_select);
                        selected = true;
                    }
                }
            });
            viewModel.getRewardList().observe(getViewLifecycleOwner(), new Observer<TaskRewardList>() {
                @Override
                public void onChanged(TaskRewardList rewardList) {
                    if (rewardList != null) {
                        Log.d(TAG, "onChanged: reward list -> " + rewardList.getList());
                        adapter.submitList(rewardList.getList());
                        showEmpty(rewardList.isEmpty());
                    } else {
                        showEmpty(true);
                    }
                    showLoading(false);
                }
            });
            rvList.setLayoutManager(new LinearLayoutManager(requireContext()));
            rvList.setAdapter(adapter);
        } else {
            showEmpty(true);
            showLoading(false);
        }
    }

    private void setupFab() {
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navController.navigate(R.id.action_select);
                }
            });
        }
    }

    private void showEmpty(boolean value) {
        textEmpty.setVisibility(value ? View.VISIBLE : View.GONE);
    }

    private void showLoading(boolean loading) {
        // TODO: Show Loading Indicator
    }
}
