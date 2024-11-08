package com.example.cycino;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private TextView[] followers;
    private EditText usernameQuery;
    private Button followButton;
    private Button unfollowButton;
    private RequestQueue requestQueue;
    private Button notisButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        notisButton = findViewById(R.id.muteNotificationsButton);
        usernameQuery = findViewById(R.id.usernameEditText);
        followButton = findViewById(R.id.followButton);
        unfollowButton = findViewById(R.id.unfollowButton);
        followers = new TextView[]{
                findViewById(R.id.user1),
                findViewById(R.id.user2),
                findViewById(R.id.user3),
                findViewById(R.id.user4),
                findViewById(R.id.user5),
                findViewById(R.id.user6),
                findViewById(R.id.user7),
                findViewById(R.id.user8),
                findViewById(R.id.user9),
                findViewById(R.id.user10)
        };

        int userID = 5;
        getFollowerList(userID);

        followButton.setOnClickListener(view -> {
            String userIdInput = usernameQuery.getText().toString().trim();
            if (!userIdInput.isEmpty()) {
                int followingID = Integer.parseInt(userIdInput);
                followUser(userID, followingID);
            } else {
                Toast.makeText(getApplicationContext(), "Please enter a valid username", Toast.LENGTH_SHORT).show();
            }
        });

        unfollowButton.setOnClickListener(view -> {
            String userIdInput = usernameQuery.getText().toString().trim();
            if (!userIdInput.isEmpty()) {
                try {
                    int followingID = Integer.parseInt(userIdInput); // Parse the user input directly as an integer (user ID)
                    unfollowUser(userID, followingID);
                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "Please enter a valid numeric user ID", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Please enter a user ID", Toast.LENGTH_SHORT).show();
            }
        });

        notisButton.setOnClickListener(view -> {
            String userIdInput = usernameQuery.getText().toString().trim();
            if (!userIdInput.isEmpty()) {
                try {
                    int followingID = Integer.parseInt(userIdInput); // Parse the user input directly as an integer (user ID)
                    muteNotis(userID, followingID);
                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "Please enter a valid numeric user ID", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Please enter a user ID", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void getFollowerList(final int userID) {
        String url = "http://coms-3090-052.class.las.iastate.edu:8080/users/" + userID + "/following";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < followers.length; i++) {
                                if (i < response.length()) {
                                    JSONObject follower = response.getJSONObject(i);
                                    int followingId = follower.getInt("followingID");
                                    boolean notis = follower.getBoolean("muteNotifications");
                                    // Display the followingID in the respective TextView
                                    followers[i].setText("Following ID: " + followingId + "   Mute Notifications: " + notis);
                                } else {
                                    // Clear any unused TextView
                                    followers[i].setText("");
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Error parsing JSON data", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Error fetching data", Toast.LENGTH_LONG).show();
                    }
                }
        );

        requestQueue.add(jsonArrayRequest);
    }

    private void followUser(final int userID, final int followingID) {
        String url = "http://coms-3090-052.class.las.iastate.edu:8080/users/" + userID + "/follow";

        // Create the JSON object for the request body
        JSONObject followBody = new JSONObject();
        try {
            followBody.put("followingID", followingID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, url, followBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String status = response.getString("status");
                            if (status.equals("200 OK")) {
                                Toast.makeText(getApplicationContext(), "Successfully followed user " + followingID, Toast.LENGTH_SHORT).show();
                                // Refresh the follower list to reflect the update
                                getFollowerList(userID);
                            } else {
                                Toast.makeText(getApplicationContext(), "Failed to follow user " + followingID, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Error parsing response", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Error sending request", Toast.LENGTH_LONG).show();
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }

    private void unfollowUser(final int userID, final int followingID) {
        String url = "http://coms-3090-052.class.las.iastate.edu:8080/users/" + userID + "/unfollow/" + followingID;

        // Create the JSON object for the request body

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.DELETE, url, null, // Changed to POST
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String status = response.getString("status");
                            if (status.equals("200 OK")) {
                                Toast.makeText(getApplicationContext(), "Successfully unfollowed user " + followingID, Toast.LENGTH_SHORT).show();
                                // Refresh the follower list to reflect the update
                                getFollowerList(userID);
                            } else {
                                Toast.makeText(getApplicationContext(), "Failed to unfollow user " + followingID, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Error parsing response", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Error sending request", Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };

        requestQueue.add(jsonObjectRequest);
    }

    private void muteNotis(final int userId, final int followingId) {
        String url = "http://coms-3090-052.class.las.iastate.edu:8080/users/" + userId + "/follow/update";

        // Create the JSON object for the request body
        JSONObject muteNotisObject = new JSONObject();
        try {
            muteNotisObject.put("followingID", followingId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.PUT, url, muteNotisObject, // Changed to POST
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String status = response.getString("status");
                            if (status.equals("200 OK")) {
                                Toast.makeText(getApplicationContext(), "Successfully changed user's notification setting of" + followingId, Toast.LENGTH_SHORT).show();
                                // Refresh the follower list to reflect the update
                                getFollowerList(userId);
                            } else {
                                Toast.makeText(getApplicationContext(), "Failed to change user " + followingId + "notification setting.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Error parsing response", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Error sending request", Toast.LENGTH_LONG).show();
                    }
                });

        requestQueue.add(jsonObjectRequest);
    }
}