package com.example.gestionuser;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private EditText editTextUsernameOrEmail;
    private EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUsernameOrEmail = findViewById(R.id.editTextUsernameOrEmail);
        editTextPassword = findViewById(R.id.editTextPassword);

        // Check if the user is already logged in using SharedPreferences
        SharedPreferences preferences = getSharedPreferences("user_preferences", MODE_PRIVATE);
        String savedUsername = preferences.getString("username", null);
        String savedPassword = preferences.getString("password", null);

        if (savedUsername != null && savedPassword != null) {
            // Auto-fill the credentials and attempt login
            editTextUsernameOrEmail.setText(savedUsername);
            editTextPassword.setText(savedPassword);
            attemptLogin(savedUsername, savedPassword);
        }
    }

    public void onLoginClick(View view) {
        String input = editTextUsernameOrEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        // Check user credentials and get the user's role
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_USER_TYPE};
        String selection = "(" + DatabaseHelper.COLUMN_USERNAME + " = ? OR " + DatabaseHelper.COLUMN_EMAIL + " = ?) AND " + DatabaseHelper.COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {input, input, password};

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_USERS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            String userType = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_TYPE));
            if ("Admin".equals(userType)) {
                // Admin login
                Intent adminIntent = new Intent(this, UserItemListActivity.class); // Change to the appropriate activity
                startActivity(adminIntent);
            } else {
                // Regular user login
                Intent userIntent = new Intent(this, MainActivity.class); // Change to the appropriate activity
                startActivity(userIntent);
            }

            // Save credentials in SharedPreferences if "Remember Me" is checked
            CheckBox rememberMeCheckBox = findViewById(R.id.rememberMeCheckBox);
            if (rememberMeCheckBox.isChecked()) {
                saveCredentialsInSharedPreferences(input, password);
            }
        } else {
            Toast.makeText(this, "Username/Email or password is incorrect", Toast.LENGTH_SHORT).show();
        }

        cursor.close();
        db.close();
    }

    public void onSignUpButtonClick(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    private void attemptLogin(String username, String password) {
        // Your existing login logic
        // ...
    }

    private void saveCredentialsInSharedPreferences(String username, String password) {
        // Save the credentials in SharedPreferences
        SharedPreferences preferences = getSharedPreferences("user_preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("username", username);
        editor.putString("password", password);
        editor.apply();
    }
}
