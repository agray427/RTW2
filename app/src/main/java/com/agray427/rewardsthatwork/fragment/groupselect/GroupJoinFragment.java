package com.agray427.rewardsthatwork.fragment.groupselect;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.agray427.rewardsthatwork.R;
import com.agray427.rewardsthatwork.common.OnAsyncActionListener;
import com.agray427.rewardsthatwork.viewmodel.GroupSelectViewModel;

public class GroupJoinFragment extends Fragment {
    private GroupSelectViewModel viewModel;
    private NavController navController;
    private EditText editInviteCode, editGroupName;
    private Button buttonJoin, buttonCreate;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_group_join, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity()).get(GroupSelectViewModel.class);
        navController = Navigation.findNavController(view);
        editInviteCode = view.findViewById(R.id.edit_invite_code);
        editGroupName = view.findViewById(R.id.edit_group_name);
        buttonJoin = view.findViewById(R.id.button_join);
        buttonJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inviteCode = editInviteCode.getText().toString();
                if (validateJoin(inviteCode)) {
                    viewModel.joinGroup(inviteCode).addListener(new OnAsyncActionListener() {
                        @Override
                        public void onAction() {
                            navController.navigate(R.id.action_select);
                        }

                        @Override
                        public void onError(@Nullable String errorMessage) {
                            if (errorMessage != null) {
                                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show();
                            }
                            setLoading(false);
                        }
                    });
                    setLoading(true);
                }
            }
        });
        buttonCreate = view.findViewById(R.id.button_create);
        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String groupName = editGroupName.getText().toString();
                if (validateCreate(groupName)) {
                    viewModel.createGroup(groupName).addListener(new OnAsyncActionListener() {
                        @Override
                        public void onAction() {
                            navController.navigate(R.id.action_select);
                        }

                        @Override
                        public void onError(@Nullable String errorMessage) {
                            if (errorMessage != null) {
                                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show();
                            }
                            setLoading(false);
                        }
                    });
                    setLoading(true);
                }
            }
        });
    }

    private boolean validateJoin(String inviteCode) {
        if (inviteCode == null || inviteCode.isEmpty()) {
            editInviteCode.setError("Required.");
            return false;
        }
        return true;
    }

    private boolean validateCreate(String groupName) {
        if (groupName == null || groupName.isEmpty()) {
            editGroupName.setError("Required.");
            return false;
        }
        return true;
    }

    private void setLoading(boolean loading) {
        // TODO: Show loading indicator
        editInviteCode.setEnabled(!loading);
        editGroupName.setEnabled(!loading);
        buttonJoin.setEnabled(!loading);
        buttonCreate.setEnabled(!loading);
    }
}