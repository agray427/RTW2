package com.agray427.rewardsthatwork.fragment.authentication;

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
import com.agray427.rewardsthatwork.viewmodel.AuthViewModel;

public class LoginFragment extends Fragment {
    private AuthViewModel viewModel;
    private NavController navController;
    private EditText editEmail, editPassword;
    private Button buttonLogIn, buttonRegister;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);
        navController = Navigation.findNavController(view);
        editEmail = view.findViewById(R.id.edit_email);
        editPassword = view.findViewById(R.id.edit_password);
        buttonLogIn = view.findViewById(R.id.button_log_in);
        buttonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editEmail.getText().toString();
                String password = editPassword.getText().toString();
                if (validate(email, password)) {
                    viewModel.logIn(email, password).addListener(new OnAsyncActionListener() {
                        @Override
                        public void onAction() {
                            navController.navigate(R.id.action_log_in);
                            requireActivity().finish();
                        }

                        @Override
                        public void onError(@Nullable String errorMessage) {
                            if (errorMessage != null) {
                                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show();
                            }
                            showLoading(false);
                        }
                    });
                    showLoading(true);
                }
            }
        });
        buttonRegister = view.findViewById(R.id.button_register);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (navController != null) {
                    navController.navigate(R.id.action_register);
                }
            }
        });
    }

    private boolean validate(@Nullable String email, @Nullable String password) {
        boolean isValid = true;

        if (email == null || email.isEmpty()) {
            editEmail.setError("Required.");
            isValid = false;
        } else if (!email.contains("@")) {
            editEmail.setError("Invalid Format.");
            isValid = false;
        }

        if (password == null || password.isEmpty()) {
            editPassword.setError("Required.");
            isValid = false;
        } else if (password.length() < 6) {
            editPassword.setError("Invalid Format.");
            isValid = false;
        }

        return isValid;
    }

    private void showLoading(boolean loading) {
        // TODO: Add Loading Indicator
        editEmail.setEnabled(!loading);
        editPassword.setEnabled(!loading);
        buttonLogIn.setEnabled(!loading);
        buttonRegister.setEnabled(!loading);
    }
}