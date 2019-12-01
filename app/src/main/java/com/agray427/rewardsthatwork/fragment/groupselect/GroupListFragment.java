package com.agray427.rewardsthatwork.fragment.groupselect;

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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.agray427.rewardsthatwork.R;
import com.agray427.rewardsthatwork.RewardsThatWork;
import com.agray427.rewardsthatwork.adapter.BaseListAdapter;
import com.agray427.rewardsthatwork.adapter.GroupListAdapter;
import com.agray427.rewardsthatwork.common.OnAsyncActionListener;
import com.agray427.rewardsthatwork.model.ui.GroupItem;
import com.agray427.rewardsthatwork.model.ui.GroupList;
import com.agray427.rewardsthatwork.viewmodel.GroupSelectViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class GroupListFragment extends Fragment {
    private GroupSelectViewModel viewModel;
    private NavController navController;
    private TextView textEmpty;
    private RecyclerView rvList;
    private FloatingActionButton fab;
    private boolean selected;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_group_list, container, false);
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

    private void setupView(View view) {
        viewModel = new ViewModelProvider(requireActivity()).get(GroupSelectViewModel.class);
        viewModel.init().addListener(new OnAsyncActionListener() {
            @Override
            public void onAction() {}

            @Override
            public void onError(@Nullable String errorMessage) {
                if (errorMessage != null) {
                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show();
                }
            }
        });
        navController = Navigation.findNavController(view);
        textEmpty = view.findViewById(R.id.text_empty);
        rvList = view.findViewById(R.id.rv_list);
        fab = view.findViewById(R.id.fab);
    }

    private void setupRecycler() {
        if (rvList != null) {
            final GroupListAdapter adapter = new GroupListAdapter(null);
            adapter.setOnSelectedListener(new BaseListAdapter.OnSelectedListener<GroupItem>() {
                @Override
                public void onSelected(@Nullable GroupItem data) {
                    if (data != null && !selected) {
                        RewardsThatWork.getInstance().setGroupId(data.getId());
                        navController.navigate(R.id.action_select);
                        selected = true;
                    }
                }
            });
            viewModel.getGroupList().observe(getViewLifecycleOwner(), new Observer<GroupList>() {
                @Override
                public void onChanged(GroupList groupList) {
                    if (groupList != null) {
                        adapter.submitList(groupList.getList());
                        showEmpty(groupList.isEmpty());
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
                    navController.navigate(R.id.action_join);
                }
            });
        }
    }

    private void showEmpty(boolean value) {
        textEmpty.setVisibility(value ? View.VISIBLE : View.GONE);
    }

    private void showLoading(boolean loading) {
        // TODO: Show Loading Indicator
        rvList.setEnabled(!loading);
    }
}
