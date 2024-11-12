package com.example.cycino;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LobbyPageActivity extends AppCompatActivity {

    private EditText editTextUsername;
    private Button buttonAddUser;
    private Button buttonRemoveUser ;
    private Button buttonStartGame;
    private Button buttonDeleteLobby;
    private Button buttonBackToHome;
    private Button buttonChangeSettings;
    private ScrollView scrollViewUserList;
    private LinearLayout userListContainer;
    private int lobbyID = 53 ; // FOR TESTING PURPOSES!!!!!!!!!!!
    private int currentUser = 4 ;
    private ArrayList<JSONObject> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobbypage);

        // Initialize UI elements
        editTextUsername = findViewById(R.id.editTextUsername);
        buttonAddUser = findViewById(R.id.buttonAddUser);
        buttonRemoveUser = findViewById(R.id.buttonRemoveUser);
        buttonStartGame = findViewById(R.id.buttonStartGame);
        buttonDeleteLobby = findViewById(R.id.buttonDeleteLobby);
        buttonBackToHome = findViewById(R.id.buttonBackToHome);
        buttonChangeSettings = findViewById(R.id.buttonChangeSettings);
        scrollViewUserList = findViewById(R.id.scrollViewUserList);
        userListContainer = findViewById(R.id.userListContainer);

        loadUsersInLobby(lobbyID);



        // Set up click listeners for buttons
        buttonAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString().trim();
                if (!username.isEmpty()) {
                    getAddUser(username);
                } else {
                    Toast.makeText(LobbyPageActivity.this, "Enter a valid username", Toast.LENGTH_SHORT).show();
                    editTextUsername.setText(""); // Clear the input field
                }
            }
        });

        buttonRemoveUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString().trim();
                if (!username.isEmpty()) {
                    getDeleteUser(username);
                } else {
                    Toast.makeText(LobbyPageActivity.this, "Enter a valid username", Toast.LENGTH_SHORT).show();
                    editTextUsername.setText(""); // Clear the input field
                }
            }
        });



        buttonStartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Placeholder for starting game logic
                Toast.makeText(LobbyPageActivity.this, "Starting game...", Toast.LENGTH_SHORT).show();
            }
        });

        buttonDeleteLobby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Placeholder for deleting lobby logic
                deleteLobby(lobbyID) ;
            }
        });

        buttonBackToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Placeholder for navigating back to home
                Toast.makeText(LobbyPageActivity.this, "Going back to home", Toast.LENGTH_SHORT).show();
                finish(); // Closes the activity
            }
        });

        buttonChangeSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Placeholder for changing settings
                Toast.makeText(LobbyPageActivity.this, "Opening settings", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LobbyPageActivity.this, MainSettingsActivity.class); // CHANGE TO SIGNUP PAGE!!!!!
                startActivity(intent);
            }
        });
    }
    private void addUserToList(JSONObject user, String username) throws JSONException {
        userList.add(user) ;
        android.widget.TextView userTextView = new android.widget.TextView(this);
        userTextView.setText("Username: " + username + "    Role: " + user.getString("role")) ;
        userTextView.setPadding(12, 12, 12, 12);
        userTextView.setTextSize(20);
        userListContainer.addView(userTextView);
    }

    private void addUserToLobby(JSONObject user, String inputUsername) throws JSONException {
        int userID = user.getInt("id") ;
        for (JSONObject existingUser : userList) {
            if (existingUser.getInt("id") == userID) {
                Toast.makeText(this, "User is already in the lobby.", Toast.LENGTH_SHORT).show();
                return; // Exit to prevent adding duplicate
            }
        }
        String url = "http://coms-3090-052.class.las.iastate.edu:8080/lobby/add/" + lobbyID + "/" + userID ;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.PUT, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("LobbyPage", "Server Response: " + response.toString());
                        try {
                            String status = response.getString("status");
                            if (status.equals("200 ok")) {
                                addUserToList(user, inputUsername) ;
                                Toast.makeText(LobbyPageActivity.this, "User has been added to lobby.", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(LobbyPageActivity.this, "Failed to add user to lobby: Are they already in it?", Toast.LENGTH_LONG).show();
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

    private void getAddUser(String inputUsername)
    {
        String url = "http://coms-3090-052.class.las.iastate.edu:8080/login/contains/" + inputUsername ;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String status = response.getString("status");
                            if (status.equals("200 ok")) {
                                JSONObject userObject = response.getJSONObject("user");
                                if (userObject != null) {
                                    addUserToLobby(userObject, inputUsername);
                                } else {
                                    // "user" key is missing in response
                                    Toast.makeText(getApplicationContext(), "User data is missing in the response.", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "User not found", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Error parsing server response", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VolleyError", "Server error: " + error.getMessage());
                        Toast.makeText(getApplicationContext(), "Server error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Accept", "application/json"); // If the server expects it
                return headers;
            }
        };
        // Add the request to the RequestQueue
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);
    }

    private void deleteUserFromList(int userId) throws JSONException {
        // Iterate through the userList to find the user with the given ID
        for (int i = 0; i < userList.size(); i++) {
            JSONObject user = userList.get(i);

            if (user.getInt("id") == userId) {
                // Remove the user from userList
                userList.remove(i);

                // Remove the corresponding TextView from userListContainer
                userListContainer.removeViewAt(i);

                return;
            }
        }
        Toast.makeText(this, "User not found in the lobby list.", Toast.LENGTH_SHORT).show();
    }


    private void getDeleteUser(String inputUsername)
    {
        String url = "http://coms-3090-052.class.las.iastate.edu:8080/login/contains/" + inputUsername ;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String status = response.getString("status");
                            if (status.equals("200 ok")) {
                                JSONObject userObject = response.getJSONObject("user");
                                if (userObject != null) {
                                    deleteUserFromLobby(userObject);
                                } else {
                                    // "user" key is missing in response
                                    Toast.makeText(getApplicationContext(), "User data is missing in the response.", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "User not found", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Error parsing server response", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VolleyError", "Server error: " + error.getMessage());
                        Toast.makeText(getApplicationContext(), "Server error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Accept", "application/json"); // If the server expects it
                return headers;
            }
        };
        // Add the request to the RequestQueue
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);
    }

    private void deleteUserFromLobby(JSONObject user) throws JSONException {
        int userID = user.getInt("id") ;
        String url = "http://coms-3090-052.class.las.iastate.edu:8080/lobby/remove/" + lobbyID + "/" + userID ;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.PUT, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("LobbyPage", "Server Response: " + response.toString());
                        try {
                            String status = response.getString("status");
                            if (status.equals("200 ok")) {
                                deleteUserFromList(userID) ;
                                Toast.makeText(LobbyPageActivity.this, "User has been deleted from lobby.", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(LobbyPageActivity.this, "Failed to delete user from lobby", Toast.LENGTH_LONG).show();
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

    private void deleteLobby(int lobbyID)
    {
        String url = "http://coms-3090-052.class.las.iastate.edu:8080/lobby/delete/" + lobbyID;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.DELETE, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("LobbyPage", "Server Response: " + response.toString());
                        try {
                            String status = response.getString("status");
                            if (status.equals("200 ok")) {
                                Toast.makeText(LobbyPageActivity.this, "Lobby has been deleted.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LobbyPageActivity.this, WelcomeActivity.class); // Go to home page.
                                startActivity(intent);
                                finish();
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
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        // Add the request to the RequestQueue
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);
    }

    private void loadUsersInLobby(int lobbyID) {
        String url = "http://coms-3090-052.class.las.iastate.edu:8080/lobby/" + lobbyID;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Extract the "players" array from the response
                            JSONArray playersArray = response.getJSONArray("players");
                            for (int i = 0; i < playersArray.length(); i++) {
                                JSONObject playerObject = playersArray.getJSONObject(i);

                                // Extract relevant information for display
                                int userId = playerObject.getInt("id");
                                String username = playerObject.getString("username") ;
                                // Add player to list and UI
                                addUserToList(playerObject, username);
                            }
                            Toast.makeText(LobbyPageActivity.this, "Players loaded successfully.", Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Error parsing player list", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VolleyError", "Server error: " + error.getMessage());
                        Toast.makeText(getApplicationContext(), "Failed to load players in lobby", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Accept", "application/json"); // If needed by the server
                return headers;
            }
        };

        // Add the request to the RequestQueue
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);
    }
}
