package com.example.cycino;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.AbstractCollection;


public class BlackjackActivity extends AppCompatActivity {

    String url = "http://coms-3090-052.class.las.iastate.edu:8080/blackjack/";

    File sdcard = Environment.getExternalStorageDirectory();

    private ImageView dCard1;
    private ImageView dCard2;
    private ImageView p1Card1;
    private ImageView p1Card2;
    private ImageView p2Card1;
    private ImageView p2Card2;
    private ImageView p3Card1;
    private ImageView p3Card2;
    private ImageView p4Card1;
    private ImageView p4Card2;

    private LinearLayout dCards;
    private LinearLayout p1Cards;
    private LinearLayout p2Cards;
    private LinearLayout p3Cards;
    private LinearLayout p4Cards;

    private LinearLayout cardR1;
    private LinearLayout cardR2;

    private Button hitButton;
    private Button standButton;
    private Button startButton;
    private Button splitButton;
    private Button dealButton;
    private Button doubleButton;

    RequestQueue requestQueue;

    private static int lobbyID = 1;
    private static int playerID = 1;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blackjack);

        requestQueue = Volley.newRequestQueue(BlackjackActivity.this);

        int numPlayers = 2;
        int playerNum = 1;

        dCard1 = findViewById(R.id.dealerCard1);
        dCard2 = findViewById(R.id.dealerCard2);
        p1Card1 = findViewById(R.id.player1Card1);
        p1Card2 = findViewById(R.id.player1Card2);
        p2Card1 = findViewById(R.id.player2Card1);
        p2Card2 = findViewById(R.id.player2Card2);
        p3Card1 = findViewById(R.id.player3Card1);
        p3Card2 = findViewById(R.id.player3Card2);
        p4Card1 = findViewById(R.id.player4Card1);
        p4Card2 = findViewById(R.id.player4Card2);

        dCards = findViewById(R.id.dealerCards);
        p1Cards = findViewById(R.id.player1Cards);
        p2Cards = findViewById(R.id.player2Cards);
        p3Cards = findViewById(R.id.player3Cards);
        p4Cards = findViewById(R.id.player4Cards);

        cardR1 = findViewById(R.id.cardsR1);
        cardR2 = findViewById(R.id.cardsR2);

        hitButton = findViewById(R.id.hitButton);
        standButton = findViewById(R.id.standButton);
        startButton = findViewById(R.id.startButton);
        splitButton = findViewById(R.id.splitButton);
        dealButton = findViewById(R.id.dealButton);
        doubleButton = findViewById(R.id.doubleButton);

        hitButton.setText("HIT");
        hitButton.setTextColor(0xFFFFFFFF);
        hitButton.setBackgroundColor(0xFFFF0000);
        hitButton.setVisibility(View.GONE);
        standButton.setText("STAND");
        standButton.setTextColor(0xFFFFFFFF);
        standButton.setBackgroundColor(0xFFFF0000);
        standButton.setVisibility(View.GONE);
        splitButton.setText("SPLIT");
        splitButton.setTextColor(0xFFFFFFFF);
        splitButton.setBackgroundColor(0xFFFF0000);
        splitButton.setVisibility(View.GONE);

        dealButton.setText("DEAL");
        dealButton.setTextColor(0xFFFFFFFF);
        dealButton.setBackgroundColor(0xFFFF0000);
        dealButton.setVisibility(View.GONE);

        startButton.setText("START GAME");
        startButton.setTextColor(0xFFFFFFFF);
        startButton.setBackgroundColor(0xFFFF0000);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGame(numPlayers,playerNum,lobbyID);
                startButton.setVisibility(View.GONE);
//                splitButton.setVisibility(View.VISIBLE);
//                standButton.setVisibility(View.VISIBLE);
//                hitButton.setVisibility(View.VISIBLE);
                dealButton.setVisibility(View.VISIBLE);
                dCards.setVisibility(View.VISIBLE);


            }
        });



    }

    private void startGame(int numPlayers, int playerNum, int lobbyID) {



        String cardBack = "card_back";

        dCard1.setImageURI(Uri.fromFile(new File(sdcard,"Pictures/"+cardBack+".png")));
        dCard2.setImageURI(Uri.fromFile(new File(sdcard,"Pictures/"+cardBack+".png")));

        if (numPlayers > 0) {

            cardR1.setVisibility(View.VISIBLE);
            p1Cards.setVisibility(View.VISIBLE);
            p1Card1.setImageURI(Uri.fromFile(new File(sdcard,"Pictures/"+cardBack+".png")));
            p1Card2.setImageURI(Uri.fromFile(new File(sdcard,"Pictures/"+cardBack+".png")));

        }

        if (numPlayers > 1) {


            cardR2.setVisibility(View.VISIBLE);
            p3Cards.setVisibility(View.VISIBLE);
            p3Card1.setImageURI(Uri.fromFile(new File(sdcard,"Pictures/"+cardBack+".png")));
            p3Card2.setImageURI(Uri.fromFile(new File(sdcard,"Pictures/"+cardBack+".png")));


            switch (playerNum) {
                case 1:
                    p1Cards.setBackgroundColor(0xFF06402B);
                    break;

                case 2:
                    p2Cards.setBackgroundColor(0xFF06402B);
                    break;

                case 3:
                    p3Cards.setBackgroundColor(0xFF06402B);
                    break;

                case 4:
                    p4Cards.setBackgroundColor(0xFF06402B);
                    break;

            }

        }

        if (numPlayers > 2) {

            p4Card1.setVisibility(View.INVISIBLE);
            p4Card2.setVisibility(View.INVISIBLE);
            p4Cards.setVisibility(View.VISIBLE);
            p2Cards.setVisibility(View.VISIBLE);
            p2Card1.setImageURI(Uri.fromFile(new File(sdcard,"Pictures/"+cardBack+".png")));
            p2Card2.setImageURI(Uri.fromFile(new File(sdcard,"Pictures/"+cardBack+".png")));

        }

        if (numPlayers > 3) {



            p4Card1.setVisibility(View.VISIBLE);
            p4Card2.setVisibility(View.VISIBLE);
            p4Card1.setImageURI(Uri.fromFile(new File(sdcard,"Pictures/"+cardBack+".png")));
            p4Card2.setImageURI(Uri.fromFile(new File(sdcard,"Pictures/"+cardBack+".png")));

        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url+"create/"+lobbyID, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle the successful response here
                        try {
                            String status = response.getString("status") ;
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Error parsing server response", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle the error response
                        Toast.makeText(getApplicationContext(), "Server error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                        error.printStackTrace();
                    }
                });
        // Add the request to the RequestQueue
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);



    }

    private void deal(){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url+"deal/"+lobbyID, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public synchronized void onResponse(JSONObject response) {
                        try {
                            response.getString("card1");
                            response.getString("card2");

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

    private void hit(){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url+"deal/"+lobbyID, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public synchronized void onResponse(JSONObject response) {
                        try {
                            response.getString("card");

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

    private void stand() {

    }

    private void split() {

    }



}


