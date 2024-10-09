package com.example.loginsignuppagefinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class SignupActivity extends AppCompatActivity {

    private EditText usernameEditText;  // define username edittext variable
    private EditText passwordEditText;  // define password edittext variable
    private EditText confirmEditText;   // define confirm edittext variable
    private Button loginButton;         // define login button variable
    private Button signupButton;        // define signup button variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        /* initialize UI elements */
        usernameEditText = findViewById(R.id.signup_username_edt);  // link to username edtext in the Signup activity XML
        passwordEditText = findViewById(R.id.signup_password_edt);  // link to password edtext in the Signup activity XML
        confirmEditText = findViewById(R.id.signup_confirm_edt);    // link to confirm edtext in the Signup activity XML
        loginButton = findViewById(R.id.signup_login_btn);    // link to login button in the Signup activity XML
        signupButton = findViewById(R.id.signup_signup_btn);  // link to signup button in the Signup activity XML

        //testServerConnection();

        /* click listener on login button pressed */
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* when login button is pressed, use intent to switch to Login Activity */
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);  // go to LoginActivity
            }
        });

        /* click listener on signup button pressed */
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* grab strings from user inputs */
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String confirm = confirmEditText.getText().toString();


                if (password.equals(confirm) && !password.equals("") && !username.equals("")) {
                    Toast.makeText(getApplicationContext(), "Signing up", Toast.LENGTH_LONG).show();
                    // SEND USERNAME AND LOGIN INFO FOR USER TO SERVER?????
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                URL url = new URL(BuildConfig.BASE_API_URL) ;
                                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                                connection.setRequestMethod("POST");
                                connection.setRequestProperty("Content-Type", "application/json; utf-8");
                                connection.setRequestProperty("Accept", "application/json");
                                connection.setDoOutput(true);

                                // Create the JSON object with user data
                                JSONObject json = new JSONObject();
                                json.put("username", username);
                                json.put("password", password);

                                // Write JSON to the request body
                                try (OutputStream os = connection.getOutputStream()) {
                                    byte[] input = json.toString().getBytes("utf-8");
                                    os.write(input, 0, input.length);
                                }

                                // Check the response code
                                int responseCode = connection.getResponseCode();
                                if (responseCode == HttpURLConnection.HTTP_OK) {
                                    // Handle success, e.g., move to the next screen
                                    Intent intent = new Intent(SignupActivity.this, WelcomeActivity.class);
                                    intent.putExtra("USERNAME", username);
                                    startActivity(intent);
                                } else {
                                    // Handle failure
                                    runOnUiThread(() -> {
                                        Toast.makeText(getApplicationContext(), "Signup failed", Toast.LENGTH_LONG).show();
                                    });
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                                runOnUiThread(() -> {
                                    Toast.makeText(getApplicationContext(), "An error occurred", Toast.LENGTH_LONG).show();
                                });
                            }
                        }
                    }).start();
                    Intent intent = new Intent(SignupActivity.this, WelcomeActivity.class) ;
                    intent.putExtra("USERNAME", username);

                } else {
                    Toast.makeText(getApplicationContext(), "Passwords don't match or are invalid.", Toast.LENGTH_LONG).show();
                }
            }

        });


//        private void sendSignupData(String username, String password)
//        {
//            RequestQueue requestQueue = Volley.newRequestQueue(this);
//            JSONObject jsonObject = new JSONObject();
//            try {
//                jsonObject.put("username", username) ;
//                jsonObject.put("password", password) ;
//            } catch (JSONException e) {
//                e.printStackTrace();
//                return;
//            }
//            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
//                    Request.Method.POST,
//                    BuildConfig.BASE_API_URL,
//                    jsonObject,
//                    new Response.Listener<JSONObject>() {
//                        @Override
//                        public void onResponse(JSONObject response) {
//                            // Handle the server response here
//                            Toast.makeText(getApplicationContext(), "Sign-up successful!", Toast.LENGTH_LONG).show();
//                        }
//                    },
//                    new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            // Handle the error here
//                            Toast.makeText(getApplicationContext(), "Sign-up failed: " + error.getMessage(), Toast.LENGTH_LONG).show();
//                        }
//                    }
//            );
//
//            // Add the request to the request queue
//            requestQueue.add(jsonObjectRequest);
        // } }


    }

//    public void testServerConnection() {
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        StringRequest stringRequest = new StringRequest(
//                Request.Method.GET,
//                BuildConfig.BASE_API_URL + "/login_info",
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        // Display the response from the server
//                        Toast.makeText(getApplicationContext(), "Response: " + response, Toast.LENGTH_LONG).show();
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // Handle any errors from the server or connection issues
//                        Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
//                    }
//                }
//        );
//
//        // Add the request to the request queue
//        requestQueue.add(stringRequest);
//
//    }
}