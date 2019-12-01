package com.agray427.rewardsthatwork.fragment.reward;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.agray427.rewardsthatwork.R;
import com.agray427.rewardsthatwork.common.OnAsyncActionListener;
import com.agray427.rewardsthatwork.model.domain.TaskReward;
import com.agray427.rewardsthatwork.model.ui.TaskRewardData;
import com.agray427.rewardsthatwork.viewmodel.MainViewModel;

public class RewardDataFragment extends Fragment {
    private MainViewModel viewModel;
    private NavController navController;
    private TextView editName, editDescription, editCost;
    private Button buttonSubmit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_reward_data, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        setupView(view);
        setupData();
        setupSubmit();
    }

    private void setupView(@NonNull View view) {
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        navController = Navigation.findNavController(view);
        editName = view.findViewById(R.id.edit_name);
        editDescription = view.findViewById(R.id.edit_description);
        editCost = view.findViewById(R.id.edit_cost);
        buttonSubmit = view.findViewById(R.id.button_submit);
    }

    private void setupData() {
        if (viewModel != null) {
            viewModel.getRewardData().observe(getViewLifecycleOwner(), new Observer<TaskRewardData>() {
                @Override
                public void onChanged(TaskRewardData rewardData) {
                    if (rewardData != null) {
                        editName.setText(rewardData.getName());
                        editDescription.setText(rewardData.getDescription());
                        editCost.setText(rewardData.getPoints());
                    }
                }
            });
        }
    }

    private void setupSubmit() {
        if (viewModel != null && buttonSubmit != null) {
            buttonSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = editName.getText().toString();
                    String description = editDescription.getText().toString();
                    int cost = Integer.parseInt(editCost.getText().toString());
                    viewModel.submitReward(new TaskReward(name, description, cost)).addListener(new OnAsyncActionListener() {
                        @Override
                        public void onAction() {
                            navController.popBackStack();
                        }

                        @Override
                        public void onError(@Nullable String errorMessage) {
                            if (errorMessage != null) {
                                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            });
        }
    }
}
