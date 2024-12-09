package com.example.cycino;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class LeaderboardInteractionActivity extends AppCompatActivity{

    private EditText putID;
    private EditText postID;
    private EditText deleteID;
    private EditText putWins;
    private EditText putlosses;
    private EditText putChips;

    private Button putButton;
    private Button postButton;
    private Button deleteButton;
    private Button returnButton;

    RequestQueue requestQueue;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_leaderboardinteraction);

        putID = findViewById(R.id.putID);
        postID = findViewById(R.id.postID);
        deleteID = findViewById(R.id.deleteID);
        putWins = findViewById(R.id.putWins);
        putlosses = findViewById(R.id.putLosses);
        putChips = findViewById(R.id.putChips);

        putButton = findViewById(R.id.putButton);
        postButton = findViewById(R.id.postButton);
        deleteButton = findViewById(R.id.deleteButton);
        returnButton = findViewById(R.id.returnButton);

        requestQueue = Volley.newRequestQueue(LeaderboardInteractionActivity.this);

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LeaderboardInteractionActivity.this, LeaderboardActivity.class);
                startActivity(intent);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteStat(Integer.valueOf(deleteID.getText().toString()));
            }
        });

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postStat(Integer.valueOf(postID.getText().toString()));
            }
        });

        putButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putStat(Integer.valueOf(putID.getText().toString()),Integer.valueOf(putWins.getText().toString()),Integer.valueOf(putlosses.getText().toString()),Integer.valueOf(putChips.getText().toString()));
            }
        });


    }

    private void deleteStat(Integer userId) {

        String url = "http://coms-3090-052.class.las.iastate.edu:8080/stats/delete/BLACKJACK";

        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(LeaderboardInteractionActivity.this, "Delete Worked", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LeaderboardInteractionActivity.this, "Delete Failed", Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("userId", userId.toString());

                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void postStat(Integer userId) {

        String url = "http://coms-3090-052.class.las.iastate.edu:8080/stats/create/BLACKJACK";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(LeaderboardInteractionActivity.this, "Post Worked", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LeaderboardInteractionActivity.this, "Post Failed", Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("userId", userId.toString());

                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void putStat(Integer userId, Integer wins, Integer loss, Integer chips) {

        String url = "http://coms-3090-052.class.las.iastate.edu:8080/stats/update/"+wins+"/"+loss+"/"+chips+"/BLACKJACK";

        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(LeaderboardInteractionActivity.this, "Put Worked", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LeaderboardInteractionActivity.this, "Put Failed", Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("userId", userId.toString());

                return params;
            }
        };

        requestQueue.add(stringRequest);
    }




}
