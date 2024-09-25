package com.example.profilepage;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private TextView profileText;
    private TextView firstNameText;
    private TextView lastNameText;
    private EditText firstName;
    private EditText lastName;
    private Button submit;
    private Button view;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        profileText = findViewById(R.id.profileText);
        firstName = findViewById(R.id.firstName);
        firstNameText = findViewById(R.id.firstNameText);
        lastNameText = findViewById(R.id.lastNameText);
        lastName = findViewById(R.id.lastName);
        submit = findViewById(R.id.submitButton);
        view = findViewById(R.id.viewProfile);

        profileText.setPadding(0,130,0,0);
        profileText.setTextSize(30);
        profileText.setText("Profile Page");

        submit.setText("Save Profile");

        requestQueue = Volley.newRequestQueue(MainActivity.this);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fNameStr = firstName.getText().toString();
                String lNameStr = lastName.getText().toString();

                if (fNameStr.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter a first name", Toast.LENGTH_SHORT).show();
                    firstNameText.setTextColor(0xFFFF0000);
                }
                if (lNameStr.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter a last name", Toast.LENGTH_SHORT).show();
                    lastNameText.setTextColor(0xFFFF0000);
                }

                else {
                    pushData(fNameStr,lNameStr);
                    System.out.println(fNameStr + lNameStr);
                }

            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ViewActivity.class);
                startActivity(intent);
            }
        });



    }

    private void pushData(final String fName, final String lName) {
        String url = "https://10c011fe-3b08-4ae2-96a7-71049edb34ae.mock.pstmn.io/postData";
        StringRequest sReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(MainActivity.this, "Data Sent" + response, Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Data Failed", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("fName",fName);
                params.put("lName",lName);
                System.out.println(params.toString());
                return params;
            }
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }
        };
        requestQueue.add(sReq);
    }
}