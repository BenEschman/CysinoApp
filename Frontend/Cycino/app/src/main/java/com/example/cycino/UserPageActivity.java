package com.example.cycino;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class UserPageActivity extends AppCompatActivity {

    // Declare UI elements
    private MaterialButton backButton, chatButton, friendUserButton;
    private ImageView profilePicture;
    private TextView username, userBio, userRole, fullName, phoneNumber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userpage); // Link to your XML layout

        // Initialize UI elements
        backButton = findViewById(R.id.backButton);
        chatButton = findViewById(R.id.chatButton);
        friendUserButton = findViewById(R.id.friendUserButton);
        profilePicture = findViewById(R.id.profilePicture);
        username = findViewById(R.id.username);
        userBio = findViewById(R.id.userBio);
        userRole = findViewById(R.id.userRole);
        fullName = findViewById(R.id.fullName);
        phoneNumber = findViewById(R.id.phoneNumber);

        // Set default values for demonstration (can be updated dynamically later)
        username.setText("John Doe");
        userBio.setText("Software Engineer | Tech Enthusiast | Gamer");
        userRole.setText("Role: Admin");
        fullName.setText("Johnathan Doe");
        phoneNumber.setText("Phone: 555-123-4567");

        // Set up button click listeners
        backButton.setOnClickListener(view -> finish()); // Close the activity and go back

        chatButton.setOnClickListener(view -> {
            // Placeholder for starting chat activity
            Toast.makeText(this, "Chat feature coming soon!", Toast.LENGTH_SHORT).show();
        });

        friendUserButton.setOnClickListener(view -> {
            // Placeholder for "Friend User" action
            Toast.makeText(this, "User added as a friend!", Toast.LENGTH_SHORT).show();
        });

        // Placeholder for profile picture click event
        profilePicture.setOnClickListener(view -> {
            // Show preset image selection dialog or perform other action
            Toast.makeText(this, "Change Profile Picture coming soon!", Toast.LENGTH_SHORT).show();
        });
    }
}