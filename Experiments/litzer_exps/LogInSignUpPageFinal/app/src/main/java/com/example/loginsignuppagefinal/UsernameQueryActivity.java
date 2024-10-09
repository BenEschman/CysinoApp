package com.example.loginsignuppagefinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UsernameQueryActivity extends AppCompatActivity {
    private EditText usernameEditText;
    private Button resetPassword;
    private Button backToLogin;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usernamequery);

        /* initialize UI elements */
        resetPassword = findViewById(R.id.reset_button);
        usernameEditText = findViewById(R.id.username_input);
        backToLogin = findViewById(R.id.go_back_to_login_btn);

        //testServerConnection();

        resetPassword.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String username = usernameEditText.getText().toString();

                // RUN METHOD THAT SEARCHES SERVER FOR USERNAME AND SETS A BOOLEAN TO TRUE OR FALSE.


                // IF BOOLEAN == TRUE
                Intent intent = new Intent(UsernameQueryActivity.this, ResetPasswordActivity.class);
                intent.putExtra("USERNAME", username);
                startActivity(intent);


                // IF BOOLEAN == FALSE
                Toast.makeText(getApplicationContext(), "Username is not found.", Toast.LENGTH_LONG).show();

            }
        });



        backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UsernameQueryActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


    }
}
