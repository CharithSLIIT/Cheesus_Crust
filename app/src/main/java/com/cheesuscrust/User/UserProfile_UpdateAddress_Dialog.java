package com.cheesuscrust.User;

import android.annotation.SuppressLint;
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

import java.util.Objects;

public class UserProfile_UpdateAddress_Dialog extends DialogFragment
{
    private EditText editTextNewAddress;
    private passAddress data;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        LayoutInflater inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();
        @SuppressLint("InflateParams") final View view = inflater.inflate(R.layout.user_update_address_dialog, null);
        builder.setView(view);

        builder.setTitle(R.string.update_address);

        builder.setPositiveButton(R.string.change_address, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String newAddress = editTextNewAddress.getText().toString();
                data.applyAddress(newAddress);

            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Objects.requireNonNull(getDialog()).dismiss();
            }
        });

        editTextNewAddress = view.findViewById(R.id.userProfile_update_AddressEditText);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            data = (passAddress) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +getString(R.string.must_implement_passAddress));
        }
    }

    public interface passAddress
    {
        void applyAddress(String address);
    }
}
