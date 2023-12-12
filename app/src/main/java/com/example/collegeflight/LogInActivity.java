package com.example.collegeflight;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LogInActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInput()) {
                    attemptLogIn();
                }
            }
        });
    }

    private boolean validateInput() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(LogInActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void attemptLogIn() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                DBHelper.COLUMN_USER_ID,
                DBHelper.COLUMN_USER_EMAIL,
                DBHelper.COLUMN_USER_PASSWORD
        };

        String selection = DBHelper.COLUMN_USER_EMAIL + " = ?";
        String[] selectionArgs = {email};

        Cursor cursor = db.query(
                DBHelper.TABLE_USER,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            String storedPassword = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_USER_PASSWORD));
            if (password.equals(storedPassword)) {
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();

                String userId = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_USER_ID));
                saveUserIdToPreferences(userId);

                Intent intent=new Intent(LogInActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Incorrect password", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "User does not exist", Toast.LENGTH_SHORT).show();
        }

        cursor.close();
        dbHelper.close();
    }
    private void saveUserIdToPreferences(String userId) {
        SharedPreferences preferences = getSharedPreferences("user_preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("userId", userId);
        editor.apply();
    }

}
