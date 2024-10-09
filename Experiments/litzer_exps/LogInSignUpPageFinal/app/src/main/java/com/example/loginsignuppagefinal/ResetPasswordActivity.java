package com.example.loginsignuppagefinal;

import android.os.Bundle;
import android.widget.EditText;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

public class ResetPasswordActivity extends AppCompatActivity {
    private EditText input_password ;
    private EditText confirm_password ;
    private Button change_password_button ;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpassword);
        input_password = findViewById(R.id.input_password);
        confirm_password = findViewById(R.id.confirm_password);
        change_password_button = findViewById(R.id.reset_password_btn);
        Intent intent = getIntent() ;
        String username = intent.getStringExtra("USERNAME") ;

        change_password_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = input_password.getText().toString();
                String confirm = confirm_password.getText().toString();

                /* grab strings from user inputs */
                if (input.equals(confirm)) {
                    Toast.makeText(getApplicationContext(), "Password has been changed", Toast.LENGTH_LONG).show();
                    // UPDATE USER INFO THROUGH POST TO SERVER!

                    // ********************************************


                    Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Passwords don't match", Toast.LENGTH_LONG).show();
                }
            }

        });







    }











}
