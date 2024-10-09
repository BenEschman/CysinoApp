package com.example.cycino;

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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class LeaderboardActivity extends AppCompatActivity{

    Gravity gravity = new Gravity();

    final private String[] names = {"name1","name2","name3","name5","name4"};
    final private Integer[] scores = {500000,100000,200,5000,200};
    final private Integer[] numWins = {1000,10,85,20,300};


    private LinearLayout lbNames;
    private LinearLayout lbScores;
    private LinearLayout lbWins;
    private Button testButton;
    private TextView titleText;
    RequestQueue requestQueue;

    final int items = 5;
    final int numLines = 2;
    final TextView[] myNameViews = new TextView[items];
    final TextView[] myScoreViews = new TextView[items];
    final TextView[] myWinViews = new TextView[items];


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_leaderboard);

        lbNames = findViewById(R.id.lb_names);
        lbScores = findViewById(R.id.lb_scores);
        lbWins = findViewById(R.id.lb_wins);
        titleText = findViewById(R.id.text_leaderboard_title);
        testButton = findViewById(R.id.testButton);

        requestQueue = Volley.newRequestQueue(LeaderboardActivity.this);

        titleText.setPadding(0,150,0,0);
        titleText.setTextSize(40);

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


        for (int i = 0; i < items; i++) {

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
            nameView.setText(names[i]);
            nameView.setTextSize(20);
            nameView.setGravity(gravity.LEFT);
            nameView.setPadding(10,0,0,0);

            winView.setText(numWins[i]+"");
            winView.setTextSize(20);
            winView.setGravity(gravity.RIGHT);
            winView.setPadding(10,0,0,0);

            scoreView.setText(scores[i]+"");
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

            // save a reference to the textview for later
            myNameViews[i] = nameView;
            myScoreViews[i] = scoreView;




        }

        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getOneUser(5);
            }
        });

    }

    private void getOneUser(Integer id) {
        //String url = "https://10c011fe-3b08-4ae2-96a7-71049edb34ae.mock.pstmn.io/getData";
        String url = "http://coms-3090-052.class.las.iastate.edu:8080/stats/BLACKJACK";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getApplicationContext(),"It worked", Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(getApplicationContext(),"It failed", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("userId", "5");

                return params;
            }
        };



        // Add the request to the RequestQueue
        requestQueue.add(jsonObjectRequest);
    }
}
