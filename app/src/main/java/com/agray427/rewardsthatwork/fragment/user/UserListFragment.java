package com.agray427.rewardsthatwork.fragment.user;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.agray427.rewardsthatwork.R;
import com.agray427.rewardsthatwork.RewardsThatWork;
import com.agray427.rewardsthatwork.adapter.BaseListAdapter;
import com.agray427.rewardsthatwork.adapter.UserListAdapter;
import com.agray427.rewardsthatwork.common.OnAsyncActionListener;
import com.agray427.rewardsthatwork.model.ui.UserItem;
import com.agray427.rewardsthatwork.model.ui.UserList;
import com.agray427.rewardsthatwork.utils.InviteCodeUtil;
import com.agray427.rewardsthatwork.viewmodel.MainViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class UserListFragment extends Fragment {
    private MainViewModel viewModel;
    private NavController navController;
    private TextView textInviteCode, textEmpty;
    private Button buttonCopy, buttonChange;
    private RecyclerView rvList;
    private FloatingActionButton fab;
    private UserListAdapter adapter;
    private boolean selected;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setupView(view);
        setupInviteCode();
        setupRecycler();
        setupFab();
        showEmpty(false);
        showLoading(true);
        setupData();
    }

    @Override
    public void onStart() {
        super.onStart();
        selected = false;
    }

    private void setupView(@NonNull View view) {
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        navController = Navigation.findNavController(view);
        textInviteCode = view.findViewById(R.id.text_invite_code);
        textEmpty = view.findViewById(R.id.text_empty);
        buttonCopy = view.findViewById(R.id.button_copy);
        buttonChange = view.findViewById(R.id.button_change);
        rvList = view.findViewById(R.id.rv_list);
        fab = view.findViewById(R.id.fab);
    }

    private void setupInviteCode() {
        buttonCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inviteCode = textInviteCode.getText().toString();
                if (!inviteCode.isEmpty()) {
                    ClipboardManager clipboard = (ClipboardManager) requireActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("invite_code", inviteCode);
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(requireContext(), "Invite code copied map clipboard.", Toast.LENGTH_LONG).show();
                }
            }
        });
        buttonChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String inviteCode = InviteCodeUtil.generate();
                viewModel.updateInviteCode(inviteCode).addListener(new OnAsyncActionListener() {
                    @Override
                    public void onAction() {
                        textInviteCode.setText(inviteCode);
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

    private void setupRecycler() {
        if (rvList != null) {
            adapter = new UserListAdapter(null);
            adapter.setOnSelectedListener(new BaseListAdapter.OnSelectedListener<UserItem>() {
                @Override
                public void onSelected(@Nullable UserItem data) {
                    if (data != null && !selected) {
                        RewardsThatWork.getInstance().setUserId(data.getId());
                        navController.navigate(R.id.action_select);
                        selected = true;
                    }
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

    private void setupData() {
        viewModel.getUserList().observe(getViewLifecycleOwner(), new Observer<UserList>() {
            @Override
            public void onChanged(UserList userList) {
                textInviteCode.setText(userList.getInviteCode());
                adapter.submitList(userList.getList());
                showEmpty(userList.isEmpty());
                showLoading(false);
            }
        });
    }

    private void showEmpty(boolean value) {
        textEmpty.setVisibility(value ? View.VISIBLE : View.GONE);
    }

    private void showLoading(boolean loading) {
        // TODO: Show Loading Indicator
    }
}
