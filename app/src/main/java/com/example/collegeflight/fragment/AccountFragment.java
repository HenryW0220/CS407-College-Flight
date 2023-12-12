package com.example.collegeflight.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.collegeflight.R;
import com.example.collegeflight.bean.UserInfo;


public class AccountFragment extends Fragment {

    private TextView firstNameTextView;
    private TextView lastNameTextView;
    private TextView emailTextView;
    private TextView birthDateTextView;
    private TextView phoneNumberTextView;
    private TextView passportCountryTextView;
    private TextView passportNumberTextView;
    private TextView passportExpireDateTextView;

    private View dialogView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_account, container, false);


        firstNameTextView = rootView.findViewById(R.id.firstNameTextView);
        lastNameTextView = rootView.findViewById(R.id.lastNameTextView);
        emailTextView = rootView.findViewById(R.id.emailTextView);
        birthDateTextView = rootView.findViewById(R.id.birthDateTextView);
        phoneNumberTextView = rootView.findViewById(R.id.phoneNumberTextView);
        passportCountryTextView = rootView.findViewById(R.id.passportCountryTextView);
        passportNumberTextView = rootView.findViewById(R.id.passportNumberTextView);
        passportExpireDateTextView = rootView.findViewById(R.id.passportExpireDateTextView);
        displayUserInfo();

        Button editProfileButton = rootView.findViewById(R.id.editProfileButton);
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditProfileDialog();
            }
        });

        return rootView;
    }

    private void displayUserInfo() {


        DBHelper dbHelper = new DBHelper(getActivity());

        UserInfo userInfo = dbHelper.getUserInfo(getCurrentUserId());


        if (userInfo != null) {
            firstNameTextView.setText("First Name: " + userInfo.getFirstName());
            lastNameTextView.setText("Last Name: " + userInfo.getLastName());
            emailTextView.setText("Email: " + userInfo.getEmail());
            birthDateTextView.setText("Date of Birth: " + userInfo.getBirthDate());
            phoneNumberTextView.setText("Phone Number: " + userInfo.getPhoneNumber());
            passportCountryTextView.setText("Passport Country: " + userInfo.getPassportCountry());
            passportNumberTextView.setText("Passport Number: " + userInfo.getPassportNumber());
            passportExpireDateTextView.setText("Passport Expire Date: " + userInfo.getPassportExpireDate());
        }
    }
    private String getCurrentUserId() {
        SharedPreferences preferences = requireContext().getSharedPreferences("user_preferences", Context.MODE_PRIVATE);
        return preferences.getString("userId", "");
    }
    private void showEditProfileDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        dialogView = inflater.inflate(R.layout.edit_profile_dialog, null);

        builder.setView(dialogView)
                .setTitle("Edit Profile")
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        updateUserInfoInDatabase();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void updateUserInfoInDatabase() {
        EditText firstNameEditText = dialogView.findViewById(R.id.editFirstName);
        EditText lastNameEditText = dialogView.findViewById(R.id.editLastName);
        EditText emailEditText = dialogView.findViewById(R.id.editEmail);
        EditText birthDateEditText = dialogView.findViewById(R.id.editBirthDate);
        EditText phoneNumberEditText = dialogView.findViewById(R.id.editPhoneNumber);
        EditText passportCountryEditText = dialogView.findViewById(R.id.editPassportCountry);
        EditText passportNumberEditText = dialogView.findViewById(R.id.editPassportNumber);
        EditText passportExpireDateEditText = dialogView.findViewById(R.id.editPassportExpireDate);

        if (areFieldsNotEmpty(
                firstNameEditText,
                lastNameEditText,
                emailEditText,
                birthDateEditText,
                phoneNumberEditText,
                passportCountryEditText,
                passportNumberEditText,
                passportExpireDateEditText
        )) {
            String editedFirstName = firstNameEditText.getText().toString();
            String editedLastName = lastNameEditText.getText().toString();
            String editedEmail = emailEditText.getText().toString();
            String editedBirthDate = birthDateEditText.getText().toString();
            String editedPhoneNumber = phoneNumberEditText.getText().toString();
            String editedPassportCountry = passportCountryEditText.getText().toString();
            String editedPassportNumber = passportNumberEditText.getText().toString();
            String editedPassportExpireDate = passportExpireDateEditText.getText().toString();

            UserInfo editedUserInfo = new UserInfo(
                    editedFirstName,
                    editedLastName,
                    editedEmail,
                    editedBirthDate,
                    editedPassportCountry,
                    editedPassportNumber,
                    editedPassportExpireDate,
                    editedPhoneNumber
            );

            String userId = getCurrentUserId();

            DBHelper dbHelper = new DBHelper(getActivity());
            dbHelper.updateUserInfo(userId, editedUserInfo);
            displayUserInfo();
        } else {

            Toast.makeText(getActivity(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean areFieldsNotEmpty(EditText... editTexts) {
        for (EditText editText : editTexts) {
            if (editText.getText().toString().trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }



}
