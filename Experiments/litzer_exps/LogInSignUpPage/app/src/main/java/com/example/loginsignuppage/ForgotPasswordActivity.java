package com.example.loginsignuppage;

import androidx.appcompat.app.AppCompatActivity ;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);

        usernameEditText = findViewById(R.id.login_username_edt);

//        loginButton.setOnClickListener(new View.OnClickListener() {
//
//            public void onClick(View v) {
//                String username = usernameEditText.getText().toString();
//                String password = passwordEditText.getText().toString();
//
//                Intent intent = new Intent(ForgotPasswordActivity.this, MainActivity.class);
//                intent.putExtra("USERNAME", username);
//                intent.putExtra("PASSWORD", password);
//                startActivity(intent);
//            }
//        });

//        signupButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
//                startActivity(intent);
//            }
//
//
//        });
//
//        forgotPasswordButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
//                startActivity(intent);
//            }
//
//
//        });

    }

}
















