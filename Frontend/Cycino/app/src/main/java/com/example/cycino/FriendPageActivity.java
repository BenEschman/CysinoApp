package com.example.cycino;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class FriendPageActivity extends AppCompatActivity {

    private TextView[] followerName;
    private TextView userName;
    private Button[] chatButton;
    private Button[] profileButton;
    private EditText usernameQuery;
    private RequestQueue requestQueue;

    String currUserName = "FUCKED";

    JSONArray friends;
    Integer[] friendsID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendpage);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        followerName = new TextView[]{
                findViewById(R.id.friend1Name),
                findViewById(R.id.friend2Name),
                findViewById(R.id.friend3Name),
                findViewById(R.id.friend4Name),
        };

        chatButton = new Button[]{
                findViewById(R.id.friend1Chat),
                findViewById(R.id.friend2Chat),
                findViewById(R.id.friend3Chat),
                findViewById(R.id.friend4Chat),
        };

        profileButton = new Button[]{
                findViewById(R.id.friend1Profile),
                findViewById(R.id.friend2Profile),
                findViewById(R.id.friend3Profile),
                findViewById(R.id.friend4Profile),
        };

        friendsID = new Integer[4];

        int userID = 1;
        getFollowerList(userID);
        //getUserName(userID);

        profileButton[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FriendPageActivity.this,ViewActivity.class);
                intent.putExtra("UUID",friendsID[0]);
                startActivity(intent);

            }
        });
        profileButton[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FriendPageActivity.this,ViewActivity.class);
                intent.putExtra("UUID",friendsID[1]);
                startActivity(intent);

            }
        });
        profileButton[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FriendPageActivity.this,ViewActivity.class);
                intent.putExtra("UUID",friendsID[2]);
                startActivity(intent);

            }
        });
        profileButton[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FriendPageActivity.this,ViewActivity.class);
                intent.putExtra("UUID",friendsID[3]);
                startActivity(intent);

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
                            friends = response;
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject follower = response.getJSONObject(i);
                                Integer followingId = follower.getInt("followingID");
                                friendsID[i] = followingId;
                                followerName[i].setText(followingId.toString());

                                //followerName[i].setText(getUserName(followingId));

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

    private void getUserName(Integer id) {
        //String url = "https://10c011fe-3b08-4ae2-96a7-71049edb34ae.mock.pstmn.io/getData";
        String url = "http://coms-3090-052.class.las.iastate.edu:8080/users/"+id;
        String out = null;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getApplicationContext(),"view worked", Toast.LENGTH_LONG).show();

                        try {
                            userName.setText(response.getString("firstName") + " " + response.getString("lastName"));
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
}