package com.example.cycino;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeaderboardActivity extends AppCompatActivity{

    Gravity gravity = new Gravity();

    final private String[] names = {"name1","name2","name3","name5","name4"};
    final private Integer[] scores = {500000,100000,200,5000,200};
    final private Integer[] numWins = {1000,10,85,20,300};


    private LinearLayout lbNames;
    private LinearLayout lbScores;
    private LinearLayout lbWins;
    private Button blackjackButton;
    private Button demoButton;
    private TextView titleText;
    RequestQueue requestQueue;

    int items;
    final int numLines = 2;
    List<String> userNames = new ArrayList<String>();
    JSONArray responseArr;




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_leaderboard);

        lbNames = findViewById(R.id.lb_names);
        lbScores = findViewById(R.id.lb_scores);
        lbWins = findViewById(R.id.lb_wins);
        titleText = findViewById(R.id.text_leaderboard_title);
        blackjackButton = findViewById(R.id.blackjackLbButton);
        demoButton = findViewById(R.id.demoButton);

        requestQueue = Volley.newRequestQueue(LeaderboardActivity.this);

        titleText.setPadding(0,150,0,0);
        titleText.setTextSize(40);

        blackjackButton.setText("Blackjack");
        demoButton.setText("Demo Features");


        getAllStats();
        resetLeaderboard();


        blackjackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetLeaderboard();

                for (int i = 0; i < items; i++) {

                    try {
                        JSONObject jObj = responseArr.getJSONObject(i);
                        updateLeaderboard(userNames.get(i),jObj);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }


            }
        });

        demoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LeaderboardActivity.this, LeaderboardInteractionActivity.class);
                startActivity(intent);
            }
        });



    }

    private void resetLeaderboard() {

        lbNames.removeAllViews();
        lbScores.removeAllViews();
        lbWins.removeAllViews();

        final TextView nameTitle = new TextView(this);
        final TextView winsTitle = new TextView(this);
        final TextView scoreTitle = new TextView(this);

        nameTitle.setText("Name");
        winsTitle.setText("Wins");
        scoreTitle.setText("Chips");

        nameTitle.setTextSize(28);
        nameTitle.setPadding(10,0,0,0);
        winsTitle.setTextSize(28);
        winsTitle.setGravity(gravity.RIGHT);
        scoreTitle.setTextSize(28);
        scoreTitle.setGravity(gravity.RIGHT);
        scoreTitle.setPadding(0,0,10,0);

        lbNames.addView(nameTitle);
        lbWins.addView(winsTitle);
        lbScores.addView(scoreTitle);
    }

    private void updateLeaderboard(String name, JSONObject jObj) throws JSONException {



        final int numLines = 3;
        final TextView[] lines = new TextView[numLines];

        for (int j = 0; j < numLines; j++) {

            final TextView line = new TextView(this);

            line.setBackgroundColor(0xFF000000);
            line.setTextSize(2);
            lines[j] = line;

        }

        final TextView nameView = new TextView(this);
        final TextView scoreView = new TextView(this);
        final TextView winView = new TextView(this);


        // set some properties of rowTextView or something
        nameView.setText(name);
        nameView.setTextSize(20);
        nameView.setGravity(gravity.LEFT);
        nameView.setPadding(10,0,0,0);

        winView.setText(jObj.getString("wins"));
        winView.setTextSize(20);
        winView.setGravity(gravity.RIGHT);
        winView.setPadding(10,0,0,0);

        scoreView.setText(jObj.getString("net"));
        scoreView.setTextSize(20);
        scoreView.setGravity(gravity.RIGHT);
        scoreView.setPadding(0,0,10,0);


        // add the textview to the linearlayout
        lbNames.addView(nameView);
        lbNames.addView(lines[0]);
        lbWins.addView(winView);
        lbWins.addView(lines[1]);
        lbScores.addView(scoreView);
        lbScores.addView(lines[2]);

    }

    private void getAllStats() {
        //String url = "https://10c011fe-3b08-4ae2-96a7-71049edb34ae.mock.pstmn.io/getData";
        String url = "http://coms-3090-052.class.las.iastate.edu:8080/stats/all/BLACKJACK";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Toast.makeText(getApplicationContext(),"It worked", Toast.LENGTH_LONG).show();
                        System.out.println(response.toString());
                        responseArr = response;
                        items = response.length();

                        try{

                        for (int i = 0; i < items; i++) {
                            JSONObject jObj = response.getJSONObject(i);
                                getOneName(jObj.getInt("userId"));
                                System.out.println(jObj.getInt("userId") + " " + i);

                        }}
                        catch(JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(getApplicationContext(),"It failed", Toast.LENGTH_LONG).show();
            }
        });



        // Add the request to the RequestQueue
        requestQueue.add(jsonArrayRequest);
    }

    private void getOneName(Integer id) {
        //String url = "https://10c011fe-3b08-4ae2-96a7-71049edb34ae.mock.pstmn.io/getData";
        String url = "http://coms-3090-052.class.las.iastate.edu:8080/users/"+id;

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public synchronized void onResponse(JSONObject response) {
                            try {
                                userNames.add(response.getString("firstName") + " " + response.getString("lastName"));
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    Toast.makeText(getApplicationContext(), "view failed", Toast.LENGTH_LONG).show();
                }
            });


        // Add the request to the RequestQueue
        requestQueue.add(jsonObjectRequest);
    }

}
