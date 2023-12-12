package com.example.collegeflight;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.collegeflight.bean.UserInfo;

public class SignUpActivity extends AppCompatActivity {

    private EditText firstNameEditText, lastNameEditText, emailEditText, passwordEditText, confirmPasswordEditText;
    private CheckBox eduAccountCheckBox;

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        dbHelper = new DBHelper(this);

        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        eduAccountCheckBox = findViewById(R.id.eduAccountCheckBox);

        Button confirmButton = findViewById(R.id.confirmButton);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInput()) {
                    saveUserInfoToDatabase();
                }
            }
        });
    }

    private boolean validateInput() {
        String firstName = firstNameEditText.getText().toString();
        String lastName = lastNameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(SignUpActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(SignUpActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }

    private void saveUserInfoToDatabase() {
        String firstName = firstNameEditText.getText().toString();
        String lastName = lastNameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        boolean eduAccount = eduAccountCheckBox.isChecked();

        if (isEmailAlreadyExists(email)) {
            Toast.makeText(this, "This email has already been registered. Please use another email.", Toast.LENGTH_SHORT).show();
            return;
        }

        UserInfo userInfo = new UserInfo();
        userInfo.setFirstName(firstName);
        userInfo.setLastName(lastName);
        userInfo.setEmail(email);
        userInfo.setPassword(password);

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_USER_FIRST_NAME, userInfo.getFirstName());
        values.put(DBHelper.COLUMN_USER_LAST_NAME, userInfo.getLastName());
        values.put(DBHelper.COLUMN_USER_EMAIL, userInfo.getEmail());
        values.put(DBHelper.COLUMN_USER_PASSWORD, userInfo.getPassword());

        long newRowId = db.insert(DBHelper.TABLE_USER, null, values);

        Toast.makeText(this, "Registration successful. Please log in.", Toast.LENGTH_SHORT).show();

        Intent intent=new Intent(SignUpActivity.this, LogInActivity.class);
        startActivity(intent);
        finish();

    }

    private boolean isEmailAlreadyExists(String email) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {DBHelper.COLUMN_USER_ID};
        String selection = DBHelper.COLUMN_USER_EMAIL + " = ?";
        String[] selectionArgs = {email};
        Cursor cursor = db.query(DBHelper.TABLE_USER, projection, selection, selectionArgs, null, null, null);

        boolean emailExists = cursor.getCount() > 0;

        cursor.close();
        return emailExists;
    }
}
