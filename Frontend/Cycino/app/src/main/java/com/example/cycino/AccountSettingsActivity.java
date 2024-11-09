package com.example.cycino;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.cycino.R;

import org.json.JSONException;
import org.json.JSONObject;



public class AccountSettingsActivity extends AppCompatActivity {

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

    //testing
<<<<<<< HEAD
    private String username = "mike";
    private String password = "123" ;
=======
    private String userUsername = "mike";
    private String userPassword = "123" ;
>>>>>>> 37-settingspage-dealer-stand

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
<<<<<<< HEAD
        setContentView(R.layout.activity_account_settings);
=======
        setContentView(R.layout.activity_accountsettings);
>>>>>>> 37-settingspage-dealer-stand

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
                Intent intent = new Intent(AccountSettingsActivity.this, MainSettingsActivity.class);
                startActivity(intent); // Start MainActivity
            }
        });

        btnChangeUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newUsername = editUsername.getText().toString();
                if (newUsername.matches("^[a-zA-Z0-9_]+$")) {
<<<<<<< HEAD

                    changeUsername(newUsername, password) ;
                    username = newUsername ;
=======
                    updateLogin(newUsername, userPassword) ;
>>>>>>> 37-settingspage-dealer-stand
                } else {
                    Toast.makeText(AccountSettingsActivity.this, "Invalid username. Only letters, numbers, and underscores are allowed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPassword = editPassword.getText().toString();
                if (!newPassword.contains(" ")) {
<<<<<<< HEAD
                    updateUserPassword(username, newPassword);
                    password = newPassword ;
=======
                    updateUserPassword(userUsername, newPassword);
>>>>>>> 37-settingspage-dealer-stand
                } else {
                    Toast.makeText(AccountSettingsActivity.this, "Invalid password. No spaces are allowed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnChangeFirstName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newFirstName = editFirstName.getText().toString();
                if (newFirstName.matches("^[a-zA-Z]+$")) {


                    // UPDATE FIRST NAME IN USER INFO TABLE


                    Toast.makeText(AccountSettingsActivity.this, "First name updated to: " + newFirstName, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AccountSettingsActivity.this, "Invalid first name. Only letters are allowed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnChangeLastName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newLastName = editLastName.getText().toString();
                if (newLastName.matches("^[a-zA-Z]+$")) {


                    // UPDATE LAST NAME IN USER INFO


                    Toast.makeText(AccountSettingsActivity.this, "Last name updated to: " + newLastName, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AccountSettingsActivity.this, "Invalid last name. Only letters are allowed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnChangePhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPhoneNumber = editPhoneNumber.getText().toString();
                if (newPhoneNumber.matches("^[0-9]+$")) {


                    // UPDATE USER PHONE NUMBER IN USER TABLE


                    Toast.makeText(AccountSettingsActivity.this, "Phone number updated to: " + newPhoneNumber, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AccountSettingsActivity.this, "Invalid phone number. Only numbers are allowed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        deleteAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
<<<<<<< HEAD

                deleteAccount(username);

=======
               // deleteAccount(username);
>>>>>>> 37-settingspage-dealer-stand
                Toast.makeText(AccountSettingsActivity.this, "Account deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }

<<<<<<< HEAD
    private void deleteAccount(final String username) {
=======
    private void deleteAccount(String username) {
>>>>>>> 37-settingspage-dealer-stand

        String url = "http://coms-3090-052.class.las.iastate.edu:8080/login/delete/" + username;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.DELETE, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("AccountSettings", "Server Response: " + response.toString());
                        try {
                            String status = response.getString("status");
                            if (status.equals("200 ok")) {
                                Toast.makeText(AccountSettingsActivity.this, "Account has been deleted.", Toast.LENGTH_SHORT).show();
<<<<<<< HEAD
                                Intent intent = new Intent(AccountSettingsActivity.this, MainSettingsActivity.class); // CHANGE TO SIGNUP PAGE!!!!!
=======
                                Intent intent = new Intent(AccountSettingsActivity.this, SignupActivity.class); // CHANGE TO SIGNUP PAGE!!!!!
>>>>>>> 37-settingspage-dealer-stand
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Error parsing server response", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error.networkResponse != null && error.networkResponse.data != null) {
                            String errorMsg = new String(error.networkResponse.data);
                            Log.e("VolleyError", errorMsg);
                        }
                        Toast.makeText(getApplicationContext(), "Server error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                        error.printStackTrace();
                    }
                }) {
        };

        // Add the request to the RequestQueue
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);

    }

<<<<<<< HEAD
    private void changeUsername(final String username, final String password) {
        String url = "http://coms-3090-052.class.las.iastate.edu:8080/settings/login/update/" + username ;
        // MIGHT NOT BE RIGHT ENDPONT!!!!
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JSONObject userData = new JSONObject();

=======
    private void updateLogin(String username, String password) {
        String url = "http://coms-3090-052.class.las.iastate.edu:8080/settings/login/update/" + username ;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JSONObject userData = new JSONObject();
>>>>>>> 37-settingspage-dealer-stand
        try {
            userData.put("username", username);
            userData.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error creating JSON data", Toast.LENGTH_LONG).show();
            return;
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.PUT, url, userData, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
<<<<<<< HEAD
                        Toast.makeText(getApplicationContext(), "Username has been changed", Toast.LENGTH_LONG).show();
=======
                        Toast.makeText(getApplicationContext(), "Login information has been updated", Toast.LENGTH_LONG).show();
                        userUsername = username ;
                        userPassword = password ;
>>>>>>> 37-settingspage-dealer-stand
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
<<<<<<< HEAD
                        Toast.makeText(getApplicationContext(), "Failed to update username", Toast.LENGTH_SHORT).show();
=======
                        Toast.makeText(getApplicationContext(), "Failed to update login information", Toast.LENGTH_SHORT).show();
>>>>>>> 37-settingspage-dealer-stand
                        Log.e("UsernamePassword", "Error: " + error.getMessage());
                    }
                });
        requestQueue.add(jsonObjectRequest);
    }

    private void updateUserPassword(final String username, final String newPassword) {
        String url = "http://coms-3090-052.class.las.iastate.edu:8080/login/update/" + username;

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("username", username);
            jsonBody.put("password", newPassword);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error creating request body", Toast.LENGTH_SHORT).show();
            return;
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getApplicationContext(), "Password has been changed", Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Failed to update password", Toast.LENGTH_SHORT).show();
                        Log.e("ResetPassword", "Error: " + error.getMessage());
                    }
                });

        requestQueue.add(jsonObjectRequest);
    }

    private void updateFirstName(final String newFirstName)
    {
        String url = "http://coms-3090-052.class.las.iastate.edu:8080/"; //////////////////////////////////////////////////////////////////////////////////////////////////

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("firstname", newFirstName);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error creating request body", Toast.LENGTH_SHORT).show();
            return;
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.PUT, url, jsonBody, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("AccountSettingsActivity", "Server Response: " + response.toString());
                        try {
                            String status = response.getString("status");
                            if (status.equals("200 ok")) {
                                Toast.makeText(getApplicationContext(), "First name has been updated.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Error parsing server response", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error.networkResponse != null && error.networkResponse.data != null) {
                            String errorMsg = new String(error.networkResponse.data);
                            Log.e("VolleyError", errorMsg);
                        }
                        Toast.makeText(getApplicationContext(), "Server error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                        error.printStackTrace();
                    }
                }) {
        };
    }

    private void updateLastName(final String newLastName)
    {
        String url = "http://coms-3090-052.class.las.iastate.edu:8080/"; //////////////////////////////////////////////////////////////////////////////////////////////////

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("lastname", newLastName);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error creating request body", Toast.LENGTH_SHORT).show();
            return;
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.PUT, url, jsonBody, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("AccountSettingsActivity", "Server Response: " + response.toString());
                        try {
                            String status = response.getString("status");
                            if (status.equals("200 ok")) {
                                Toast.makeText(getApplicationContext(), "Last name has been updated.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Error parsing server response", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error.networkResponse != null && error.networkResponse.data != null) {
                            String errorMsg = new String(error.networkResponse.data);
                            Log.e("VolleyError", errorMsg);
                        }
                        Toast.makeText(getApplicationContext(), "Server error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                        error.printStackTrace();
                    }
                }) {
        };
    }

    private void updatePhoneNumber(final int newPhoneNumber)
    {
        String url = "http://coms-3090-052.class.las.iastate.edu:8080/"; //////////////////////////////////////////////////////////////////////////////////////////////////

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("firstname", newPhoneNumber);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error creating request body", Toast.LENGTH_SHORT).show();
            return;
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.PUT, url, jsonBody, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("AccountSettingsActivity", "Server Response: " + response.toString());
                        try {
                            String status = response.getString("status");
                            if (status.equals("200 ok")) {
                                Toast.makeText(getApplicationContext(), "Phone number has been updated.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Error parsing server response", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error.networkResponse != null && error.networkResponse.data != null) {
                            String errorMsg = new String(error.networkResponse.data);
                            Log.e("VolleyError", errorMsg);
                        }
                        Toast.makeText(getApplicationContext(), "Server error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                        error.printStackTrace();
                    }
                }) {
        };
    }





}




