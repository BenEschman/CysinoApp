package com.example.loginsignuppagefinal;

import androidx.appcompat.app.AppCompatActivity ;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button signupButton;
    private Button forgotPasswordButton;
    private boolean key ;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.login_username_edt);
        passwordEditText = findViewById(R.id.login_password_edt);
        loginButton = findViewById(R.id.login_login_btn);
        signupButton = findViewById(R.id.login_signup_btn);
        forgotPasswordButton = findViewById(R.id.forgot_password_btn);
        key = true ; // SET TO FALSE!!!!

        loginButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                // RUN METHOD THAT RUNS GET? REQUEST TO SERVER THAT CHECKS TO SEE IF PASSWORD IS RIGHT FOR USER!
                // SET KEY TO TRUE IF SO, FALSE IF NOT



                // *********************************************************



                if(key)
                {
                    Intent intent = new Intent(LoginActivity.this, WelcomeActivity.class);
                    intent.putExtra("USERNAME", username);
                    intent.putExtra("PASSWORD", password);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Username or Password is incorrect!", Toast.LENGTH_LONG).show();
                }

            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }




        });

        forgotPasswordButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, UsernameQueryActivity.class);
                startActivity(intent);
            }


        });

    }

}
















