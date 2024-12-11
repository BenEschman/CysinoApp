package com.example.cycino;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class ViewFriendActivity extends AppCompatActivity {

    private Button friendsButton, muteBtn, unfollowBtn;
    RequestQueue requestQueue;
    private LinearLayout viewLayout;
    private TextView nameText, biographyText;
    private JSONObject user;
    private boolean isMuted;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewfriend);
        friendsButton = findViewById(R.id.fPage);
        muteBtn = findViewById(R.id.vMuteNotifs);
        unfollowBtn = findViewById(R.id.vRemoveFollow);
        viewLayout = findViewById(R.id.layout_view);
        nameText = findViewById(R.id.userNameText);
        biographyText = findViewById(R.id.biographyText);


        getUserInfo();

        Intent intent = getIntent();
        Integer userID = intent.getIntExtra("UUID",0);
        Integer loggedInUser = intent.getIntExtra("lUUID",0);
        String userName = intent.getStringExtra("USERNAME");

        System.out.println(userID);


        getOneUser(userID);
        checkMuteStatus(loggedInUser, userID);

        friendsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewFriendActivity.this, FriendPageActivity.class);
                intent.putExtra("UUID",loggedInUser);
                intent.putExtra("USERNAME",userName);
                startActivity(intent);
            }
        });

        muteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                muteNotis(loggedInUser,userID);

            }
        });

        unfollowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unfollowUser(loggedInUser,userID);
                Intent intent = new Intent(ViewFriendActivity.this, FriendPageActivity.class);
                intent.putExtra("UUID",loggedInUser);
                intent.putExtra("USERNAME",userName);
                startActivity(intent);

            }
        });


        // Placeholder for profile picture click event
        profilePicture.setOnClickListener(view -> {
            // Show preset image selection dialog or perform other action
            Toast.makeText(this, "Change Profile Picture coming soon!", Toast.LENGTH_SHORT).show();
        });
    }

    private void setUserInfo()
    {
        usernameTV.setText(userUsername);
        fullNameTV.setText(userFirstName + " " + userLastName) ;
        userBioTV.setText(userBio) ;
        userRoleTV.setText(userRole) ;
        phoneNumberTV.setText(userPhoneNumber) ;
    }

    private void getUserInfo() {
        serverUrl = "http://coms-3090-052.class.las.iastate.edu:8080/users/" + userID;
        JsonObjectRequest getRequest = new JsonObjectRequest(
                Request.Method.GET,
                serverUrl,
                null,
                response -> {
                    try {
                        // Extract fields directly from the response JSON
                        userUsername = response.getString("username");
                        userFirstName = response.getString("firstName");
                        userLastName = response.getString("lastName");
                        userPhoneNumber = response.getString("phoneNumber");
                        chips = response.getInt("chips");
                        userRole = response.getString("role");
                        userBio = response.getString("userBiography");

                        // Call setUserInfo to update the UI
                        setUserInfo();

                    } catch (JSONException e) {
                        // Handle JSON parsing errors
                        Toast.makeText(this, "Response parsing error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    // Handle request errors
                    Toast.makeText(this, "Request failed. Please check your connection.", Toast.LENGTH_SHORT).show();
                }
        );

        // Add the request to the queue
        requestQueue.add(getRequest);
    }


    /**
     * Unfollows another user by entering both the current user's ID, and the ID of the user that is attempting to be unfollowed.
     * @param userID
     * @param followingID
     *
     */
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

    /**
     * Mutes the notifications from a different user
     * @param userId
     * @param followingId
     *
     */
    private void muteNotis(final int userId, final int followingId) {
        String url = "http://coms-3090-052.class.las.iastate.edu:8080/users/" + userId + "/follow/update";

        if (isMuted) {
            muteBtn.setText("Mute");
            isMuted = false;
            System.out.println("Changed to false");
        }
        else {
            muteBtn.setText("Unmute");
            isMuted = true;
            System.out.println("Changed to true");
        }

        // Create the JSON object for the request body
        JSONObject muteNotisObject = new JSONObject();
        try {
            muteNotisObject.put("followingID", followingId);
            muteNotisObject.put("muteNotifications",isMuted);
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

    private void checkMuteStatus(Integer loggedInUser, Integer friendID) {

        String url = "http://coms-3090-052.class.las.iastate.edu:8080/users/"+loggedInUser;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getApplicationContext(),"view worked", Toast.LENGTH_LONG).show();

                        try {
                            JSONArray fList = response.getJSONArray("followList");

                            for (int i = 0; i < fList.length(); i++) {
                                JSONObject f = fList.getJSONObject(i);

                                if (f.getInt("followingID") == friendID) {

                                    System.out.println(f.getBoolean("muteNotifications"));

                                   setMuted(f.getBoolean("muteNotifications"));

                                }

                            }

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(getApplicationContext(),"view failed", Toast.LENGTH_LONG).show();
            }
        });

        // Add the request to the RequestQueue
        requestQueue.add(jsonObjectRequest);
    }

    public void setMuted(boolean muted) {
        isMuted = muted;

        System.out.println(isMuted);

        if (muted) {
            muteBtn.setText("Unmute");
        }
        else {
            muteBtn.setText("Mute");
        }
    }

}
