package com.example.loginsignuppagefinal;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {

    private TextView welcomeMessage ;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        welcomeMessage = findViewById(R.id.welcome_message) ;
        Intent intent = getIntent();
        String username = intent.getStringExtra("USERNAME");

        if (username != null && !username.isEmpty())
        {
            welcomeMessage.setText("Welcome, " + username + "!") ;
        }
        else {
            welcomeMessage.setText("Welcome, User!") ;
        }
    }


}
