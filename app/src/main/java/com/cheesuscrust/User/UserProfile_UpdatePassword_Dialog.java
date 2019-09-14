package com.cheesuscrust.User;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.cheesuscrust.R;

public class UserProfile_UpdatePassword_Dialog extends DialogFragment
{
    private EditText editTextCurrentPassword, editTextNewPassword, editTextConfirmPassword;
    private passPassword data;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.user_update_password_dialog, null);
        builder.setView(view);

        builder.setTitle(R.string.update_your_password);

        builder.setPositiveButton(R.string.change_password, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String currentPassword = editTextCurrentPassword.getText().toString();
                String newPassword = editTextNewPassword.getText().toString();
                String confirmPassword = editTextConfirmPassword.getText().toString();
                data.applyPassword(currentPassword, newPassword, confirmPassword);
            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                getDialog().dismiss();
            }
        });

        editTextCurrentPassword = view.findViewById(R.id.userProfile_update_PasswordCurrentEditText);
        editTextNewPassword = view.findViewById(R.id.userProfile_update_PasswordNewEditText);
        editTextConfirmPassword = view.findViewById(R.id.userProfile_update_PasswordNewConfirmEditText);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            data = (passPassword) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +getString(R.string.must_implement_passPassword));
        }
    }

    public interface passPassword
    {
        void applyPassword(String currentPassword, String newPassword, String confirmPassword);
    }
}
