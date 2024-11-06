package com.example.settingspage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AccountSettings extends AppCompatActivity {

    private Button backButton;
    private Button btnChangeUsername;
    private Button btnChangePassword;
    private Button btnChangeFirstName;
    private Button btnChangeLastName;
    private Button btnChangePhoneNumber;
    private Button deleteAccountButton;
    private EditText editUsername;
    private EditText editPassword;
    private EditText editFirstName;
    private EditText editLastName;
    private EditText editPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);

        // Initialize UI elements
        backButton = findViewById(R.id.backButton);
        btnChangeUsername = findViewById(R.id.btnChangeUsername);
        btnChangePassword = findViewById(R.id.btnChangePassword);
        btnChangeFirstName = findViewById(R.id.btnChangeFirstName);
        btnChangeLastName = findViewById(R.id.btnChangeLastName);
        btnChangePhoneNumber = findViewById(R.id.btnChangePhoneNumber);
        deleteAccountButton = findViewById(R.id.deleteAccountButton);
        editUsername = findViewById(R.id.editUsername);
        editPassword = findViewById(R.id.editPassword);
        editFirstName = findViewById(R.id.editFirstName);
        editLastName = findViewById(R.id.editLastName);
        editPhoneNumber = findViewById(R.id.editPhoneNumber);

        // Set listeners for buttons
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountSettings.this, MainActivity.class);
                startActivity(intent); // Start MainActivity
            }
        });

        btnChangeUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newUsername = editUsername.getText().toString();
                if (newUsername.matches("^[a-zA-Z0-9_]+$")) {
                    // UPDATE USER TABLE USERNAME


                    Toast.makeText(AccountSettings.this, "Username updated to: " + newUsername, Toast.LENGTH_SHORT).show();



                } else {
                    Toast.makeText(AccountSettings.this, "Invalid username. Only letters, numbers, and underscores are allowed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPassword = editPassword.getText().toString();
                if (!newPassword.contains(" ")) {

                    // UPDATE USER TABLE PASSWORD







                    Toast.makeText(AccountSettings.this, "Password updated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AccountSettings.this, "Invalid password. No spaces are allowed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnChangeFirstName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newFirstName = editFirstName.getText().toString();
                if (newFirstName.matches("^[a-zA-Z]+$")) {


                    // UPDATE FIRST NAME IN USER INFO TABLE



                    Toast.makeText(AccountSettings.this, "First name updated to: " + newFirstName, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AccountSettings.this, "Invalid first name. Only letters are allowed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnChangeLastName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newLastName = editLastName.getText().toString();
                if (newLastName.matches("^[a-zA-Z]+$")) {


                    // UPDATE LAST NAME IN USER INFO



                    Toast.makeText(AccountSettings.this, "Last name updated to: " + newLastName, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AccountSettings.this, "Invalid last name. Only letters are allowed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnChangePhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPhoneNumber = editPhoneNumber.getText().toString();
                if (newPhoneNumber.matches("^[0-9]+$")) {


                    // UPDATE USER PHONE NUMBER IN USER TABLE



                    Toast.makeText(AccountSettings.this, "Phone number updated to: " + newPhoneNumber, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AccountSettings.this, "Invalid phone number. Only numbers are allowed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        deleteAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // REMOVE THE USER ROW IN MULTIPLE MYSQL TABLES



                Toast.makeText(AccountSettings.this, "Account deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
