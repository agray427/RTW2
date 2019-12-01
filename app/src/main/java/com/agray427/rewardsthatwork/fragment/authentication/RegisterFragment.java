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

public class RegisterFragment extends Fragment {
    private AuthViewModel viewModel;
    private EditText editFirstName,
            editLastName,
            editEmail,
            editPassword,
            editPasswordConfirm;
    private Button buttonRegister;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);
        final NavController navController = Navigation.findNavController(view);
        editFirstName = view.findViewById(R.id.edit_first_name);
        editLastName = view.findViewById(R.id.edit_last_name);
        editEmail = view.findViewById(R.id.edit_email);
        editPassword = view.findViewById(R.id.edit_password);
        editPasswordConfirm = view.findViewById(R.id.edit_password_confirm);
        buttonRegister = view.findViewById(R.id.button_register);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = editFirstName.getText().toString();
                String lastName = editLastName.getText().toString();
                String email = editEmail.getText().toString();
                String password = editPassword.getText().toString();
                String passwordConfirm = editPasswordConfirm.getText().toString();
                if (validate(firstName, lastName, email, password, passwordConfirm)) {
                    viewModel.register(email, password, firstName, lastName).addListener(new OnAsyncActionListener() {
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
    }

    private boolean validate(@Nullable String firstName, @Nullable String lastName, @Nullable String email, @Nullable String password, @Nullable String passwordConfrim) {
        boolean isValid = true;

        if (firstName == null || firstName.isEmpty()) {
            editFirstName.setError("Required.");
            isValid = false;
        }

        if (lastName == null || lastName.isEmpty()) {
            editLastName.setError("Required.");
            isValid = false;
        }

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

        if (passwordConfrim == null || passwordConfrim.isEmpty()) {
            editPasswordConfirm.setError("Required.");
            isValid = false;
        } else if (!password.equals(passwordConfrim)) {
            editPasswordConfirm.setError("Doesn't Match.");
            isValid = false;
        }

        return isValid;
    }

    private void showLoading(boolean loading) {
        // TODO: Add Loading Indicator
        editFirstName.setEnabled(!loading);
        editLastName.setEnabled(!loading);
        editEmail.setEnabled(!loading);
        editPassword.setEnabled(!loading);
        editPasswordConfirm.setEnabled(!loading);
        buttonRegister.setEnabled(!loading);
    }
}