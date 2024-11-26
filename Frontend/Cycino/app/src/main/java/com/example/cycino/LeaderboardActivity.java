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

/**
 * The LeaderboardActivity allows you to see every player's stats
 * It handles the getting and displaying of all stats
 *
 * @author Sam Craft
 */

public class LeaderboardActivity extends AppCompatActivity{


    /**
     * AndroidStudio function for moving items on screen
     */
    Gravity gravity = new Gravity();


    /**
     * LinearLayout for player Names
     */
    private LinearLayout lbNames;
    /**
     * LinearLayout for player Scores
     */
    private LinearLayout lbScores;
    /**
     *  LinearLayout for player Wins
     */
    private LinearLayout lbWins;
    /**
     *  Button to select blacjack tab
     */
    private Button blackjackButton;
    /**
     * TextView to show current game selected
     */
    private TextView titleText;
    RequestQueue requestQueue;


    /**
     * Global counting variable
     */
    int items;
    /**
     * List of usernames
     */
    List<String> userNames = new ArrayList<String>();
    /**
     * Empty JSONArray for HTTP Reponses
     */
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

        requestQueue = Volley.newRequestQueue(LeaderboardActivity.this);

        titleText.setPadding(0,150,0,0);
        titleText.setTextSize(40);

        blackjackButton.setText("Blackjack");

        getBlackjackStats();
        resetLeaderboard();


        blackjackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    resetLeaderboard();
                    getBlackjackStats();


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
    }

    /**
     *Resets the leaderboard to be completely empty.
     */
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

    /**
     * Updates the leaderboard frontend with the JSON Object passed through
     * @param name
     * @param jObj
     * @throws JSONException
     *
     */
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

        String score = jObj.getString("net");

        scoreView.setText(score.substring(0,score.length()-2));
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

    /**
     *Gets blackjack stats for every user that exists
     */
    private void getBlackjackStats() {
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


    /**
     * Gets the name of one user based off of their user ID
     * @param id
     *
     */
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
