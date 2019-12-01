package com.agray427.rewardsthatwork.fragment.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.agray427.rewardsthatwork.R;
import com.agray427.rewardsthatwork.adapter.CompletedTaskRewardListAdapter;
import com.agray427.rewardsthatwork.common.OnAsyncActionListener;
import com.agray427.rewardsthatwork.model.ui.Home;
import com.agray427.rewardsthatwork.viewmodel.MainViewModel;

public class HomeFragment extends Fragment {
    private MainViewModel viewModel;
    private TextView textName, textPoints;
    private ViewGroup sectionRequests, sectionRewards, sectionTasks;
    private RecyclerView rvRequests, rvRewards, rvTasks;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setupView(view);
        setupRequests();
        setupRewards();
        setupTasks();
        if (viewModel != null) {
            viewModel.getHomeData().observe(getViewLifecycleOwner(), new Observer<Home>() {
                @Override
                public void onChanged(Home home) {
                    if (home != null) {
                        setData(home);
                    }
                }
            });
        }
    }

    private void setupView(View view) {
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        viewModel.init().addListener(new OnAsyncActionListener() {
            @Override
            public void onAction() {}

            @Override
            public void onError(@Nullable String errorMessage) {
                if (errorMessage != null && getContext() != null) {
                    Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
                }
            }
        });
        textName = view.findViewById(R.id.text_name);
        textPoints = view.findViewById(R.id.text_points);
        sectionRequests = view.findViewById(R.id.section_requests);
        sectionRewards = view.findViewById(R.id.section_rewards);
        sectionTasks = view.findViewById(R.id.section_tasks);
        rvRequests = view.findViewById(R.id.rv_requests);
        rvRewards = view.findViewById(R.id.rv_rewards);
        rvTasks = view.findViewById(R.id.rv_tasks);
    }

    private void setupRequests() {
        // TODO: Might not do this...
        sectionRequests.setVisibility(View.GONE);
    }

    private void setupRewards() {
        if (rvRewards != null) {
            rvRewards.setAdapter(new CompletedTaskRewardListAdapter(null));
            rvRewards.setLayoutManager(new LinearLayoutManager(requireContext()));
        }
    }

    private void setupTasks() {
        if (rvTasks != null) {
            rvTasks.setAdapter(new CompletedTaskRewardListAdapter(null));
            rvTasks.setLayoutManager(new LinearLayoutManager(requireContext()));
        }
    }

    private void setData(@NonNull Home home) {
        textName.setText(home.getUserName());
        textPoints.setText(home.getPoints());
        CompletedTaskRewardListAdapter rewardsAdapter = (CompletedTaskRewardListAdapter) rvRewards.getAdapter();
        if (rewardsAdapter != null) {
            rewardsAdapter.submitList(home.getRewards());
            if (home.getRewards().isEmpty()) {
                sectionRewards.setVisibility(View.GONE);
            } else {
                sectionRewards.setVisibility(View.VISIBLE);
            }
        }
        CompletedTaskRewardListAdapter tasksAdapter = (CompletedTaskRewardListAdapter) rvTasks.getAdapter();
        if (tasksAdapter != null) {
            tasksAdapter.submitList(home.getTasks());
            if (home.getTasks().isEmpty()) {
                sectionTasks.setVisibility(View.GONE);
            } else {
                sectionTasks.setVisibility(View.VISIBLE);
            }
        }
    }
}