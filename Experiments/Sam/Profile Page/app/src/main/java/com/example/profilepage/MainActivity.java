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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private TextView profileText;
    private EditText firstName;
    private EditText lastName;
    private EditText phoneNumber;
    private Button submit;
    private Button view;
    private Button leaderboard;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        profileText = findViewById(R.id.profileText);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        phoneNumber = findViewById(R.id.editTextPhone);
        submit = findViewById(R.id.submitButton);
        view = findViewById(R.id.viewProfile);
        leaderboard = findViewById(R.id.button_leaderboard);

        profileText.setPadding(0,130,0,0);
        profileText.setTextSize(30);
        profileText.setText("Profile Page");

        submit.setText("Save Profile");
        submit.setBackgroundColor(0xFFFFFF00);
        submit.setTextColor(0xFF000000);
        view.setBackgroundColor(0xFFFFFF00);
        view.setTextColor(0xFF000000);

        requestQueue = Volley.newRequestQueue(MainActivity.this);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fNameStr = firstName.getText().toString();
                String lNameStr = lastName.getText().toString();
                String phoneNum = phoneNumber.getText().toString();

                    pushData(fNameStr,lNameStr,phoneNum);
                    System.out.println(fNameStr + lNameStr);


            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ViewActivity.class);
                startActivity(intent);
            }
        });

        leaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LeaderboardActivity.class);
                startActivity(intent);
            }
        });



    }

    private void pushData(final String fName, final String lName, final String phoneNum) {
        //String url = "https://10c011fe-3b08-4ae2-96a7-71049edb34ae.mock.pstmn.io/putData";
        //String url = "http://coms-3090-052.class.las.iastate.edu:8080/users/create";
        String url = "10.90.75.20:8080/users/create";

        JSONObject postData = new JSONObject();

        try {
            postData.put("firstName",fName);
            postData.put("lastName",lName);
            postData.put("phoneNumber",phoneNum);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getApplicationContext(), "Response: "+response, Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(getApplicationContext(),"FAIL", Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(jsonObjectRequest);

        };

}