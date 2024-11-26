package com.example.cycino;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;

import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;


/**
 * The BlackjackActivity is the activity that runs the game of blackjack.
 * It handles both frontend and backend interactions.
 * Includes all functions of basic blackjack
 * @author Sam Craft
 */


public class BlackjackActivity extends AppCompatActivity {

    String url = "http://coms-3090-052.class.las.iastate.edu:8080/blackjack/";

    /**
     * ImageView for card
     */
    private ImageView dCard1,dCard2, p1Card1, p1Card2, p2Card1, p2Card2;

    /**
     * TextView for game info
     */
    private TextView dScore, pScore, winStatus;

    /**
     * LinearLayout for card formatting
     */
    private LinearLayout dCards, p1Cards, p2Cards, cardR1, cardR2;

    /**
     * Button for Hit
     */
    private Button hitButton;
    /**
     * Button for Stand
     */
    private Button standButton;
    /**
     * Button for Start
     */
    private Button startButton;
    /**
     * Button for Split
     */
    private Button splitButton;
    /**
     * Button for Deal
     */
    private Button dealButton;
    /**
     * Button for Double
     */
    private Button doubleButton;

    RequestQueue requestQueue;

    /**
     * Current Lobby ID
     */
    private static Integer lobbyID = 56;
    /**
     * User's user ID
     */
    private static Integer userID;

    /**
     * Number of cards dealer has
     */
    private int dCardNum = 0;
    /**
     * number of cards player has
     */
    private int p1CardNum = 0;
    /**
     * Number of players in game
     */
    private Integer numPlayers;

    /**
     * ImageView for extra cards
     */
    private ImageView d1x1, d1x2, d1x3, d1x4, d1x5;
    /**
     * ImageView for extra cards
     */
    private ImageView p1x1, p1x2, p1x3, p1x4, p1x5;


    /**
     * Chat Buttons
     */
    private Button sendBtn, chatToggleBtn, backBtn, imageBtn;
    /**
     * Chat message box
     */
    private EditText msgEtx;
    /**
     * Chat message area
     */
    private TextView msgTv;
    /**
     * Chat outer boundary
     */
    private ScrollView chatArea;
    /**
     * Boolean to check if chat is open or closed
     */
    private boolean chatOpen = true;
    /**
     * VM URL
     */
    private String serverURL = "ws://coms-3090-052.class.las.iastate.edu:8080/chat/";
    /**
     * Current user's username
     */
    private String username;

    /**
     * File that holds the emulator's external storage location
     */
    File sdcard;

    /**
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blackjack);

        sdcard = Environment.getExternalStorageDirectory();
        requestQueue = Volley.newRequestQueue(BlackjackActivity.this);

        Intent inIntent = getIntent();
        username = inIntent.getStringExtra("USERNAME");
        userID = inIntent.getIntExtra("UUID",-1);



        Integer playerNum = 1;

        dCard1 = findViewById(R.id.dealerCard1);
        dCard2 = findViewById(R.id.dealerCard2);
        p1Card1 = findViewById(R.id.player1Card1);
        p1Card2 = findViewById(R.id.player1Card2);
        p2Card1 = findViewById(R.id.player2Card1);
        p2Card2 = findViewById(R.id.player2Card2);

        dScore = findViewById(R.id.dScore);
        pScore = findViewById(R.id.pScore);
        winStatus = findViewById(R.id.winStatus);

        dCards = findViewById(R.id.dealerCards);
        p1Cards = findViewById(R.id.player1Cards);
        p2Cards = findViewById(R.id.player2Cards);

        cardR1 = findViewById(R.id.cardsR1);
        cardR2 = findViewById(R.id.cardsR2);

        hitButton = findViewById(R.id.hitButton);
        standButton = findViewById(R.id.standButton);
        startButton = findViewById(R.id.startButton);
        splitButton = findViewById(R.id.splitButton);
        dealButton = findViewById(R.id.dealButton);
        doubleButton = findViewById(R.id.doubleButton);

        sendBtn = (Button) findViewById(R.id.sendBtn);
        chatToggleBtn = (Button) findViewById(R.id.backMainBtn);
        msgEtx = (EditText) findViewById(R.id.msgEdt);
        msgTv = (TextView) findViewById(R.id.tx1);
        chatArea = findViewById(R.id.chatView);
        backBtn = findViewById(R.id.backButton);
        imageBtn = findViewById(R.id.imageBtn);

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

        doubleButton.setText("DOUBLE");
        doubleButton.setTextColor(0xFFFFFFFF);
        doubleButton.setBackgroundColor(0xFFFF0000);
        doubleButton.setVisibility(View.GONE);

        dealButton.setText("DEAL");
        dealButton.setTextColor(0xFFFFFFFF);
        dealButton.setBackgroundColor(0xFFFF0000);
        dealButton.setVisibility(View.GONE);

        startButton.setText("START GAME");
        startButton.setTextColor(0xFFFFFFFF);
        startButton.setBackgroundColor(0xFFFF0000);

        ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
                registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                    // Callback is invoked after the user selects a media item or closes the
                    // photo picker.
                    if (uri != null) {
                        Log.d("PhotoPicker", "Selected URI: " + uri);

                    } else {
                        Log.d("PhotoPicker", "No media selected");
                    }
                });

        String serverUrl = serverURL + "1/" + username;
        Intent serviceIntent = new Intent(this, WebSocketService.class);
        serviceIntent.setAction("CONNECT");
        serviceIntent.putExtra("key", "chat1");
        serviceIntent.putExtra("url", serverUrl);
        startService(serviceIntent);

        sendBtn.setOnClickListener(v -> {

            String msgStr = msgEtx.getText().toString();

            if (!(msgStr.isEmpty())) {

                // broadcast this message to the WebSocketService
                // tag it with the key - to specify which WebSocketClient (connection) to send
                // in this case: "chat1"
                Intent intent = new Intent("SendWebSocketMessage");
                intent.putExtra("key", "chat1");
                intent.putExtra("message", msgStr);
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
                msgEtx.setText("");

            }


        });

        imageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickMedia.launch(new PickVisualMediaRequest.Builder()
                        .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                        .build());
            }
        });

        /* back button listener */
        chatToggleBtn.setOnClickListener(view -> {

                    if (chatOpen) {
                        sendBtn.setVisibility(View.GONE);
                        imageBtn.setVisibility(View.GONE);
                        msgEtx.setVisibility(View.GONE);
                        chatArea.setVisibility(View.GONE);
                        chatToggleBtn.setText("Open Chat");

                        chatOpen = false;
                    } else {

                        sendBtn.setVisibility(View.VISIBLE);
                        imageBtn.setVisibility(View.VISIBLE);
                        msgEtx.setVisibility(View.VISIBLE);
                        chatArea.setVisibility(View.VISIBLE);
                        chatToggleBtn.setText("Close Chat");

                        chatOpen = true;

                    }
                });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BlackjackActivity.this,HomePageActivity.class);
                i.putExtra("USERNAME",username);
                i.putExtra("UUID",userID);
                startActivity(i);
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGame(numPlayers,playerNum,lobbyID);
                startButton.setVisibility(View.GONE);
                dealButton.setVisibility(View.VISIBLE);
                dCards.setVisibility(View.VISIBLE);


            }
        });

        dealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deal();
            }
        });

        hitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hit();
            }
        });

        standButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stand();
            }
        });



    }

    /**
     *
     * Starts the game. Sets up all frontend components, makes calls to backend to start a new game.
     * @param numPlayers
     * @param playerNum
     * @param lobbyID
     */
    private void startGame(int numPlayers, int playerNum, int lobbyID) {

        p1Cards.removeView(p1x1);
        p1Cards.removeView(p1x2);
        p1Cards.removeView(p1x3);
        p1Cards.removeView(p1x4);
        p1Cards.removeView(p1x5);
        dCards.removeView(d1x1);
        dCards.removeView(d1x2);
        dCards.removeView(d1x3);
        dCards.removeView(d1x4);
        dCards.removeView(d1x5);

        String cardBack = "card_back";

        dCard1.setImageURI(Uri.fromFile(new File(sdcard,"Android/media/"+cardBack+".png")));
        dCard2.setImageURI(Uri.fromFile(new File(sdcard,"Android/media/"+cardBack+".png")));

        if (numPlayers > 0) {

            cardR1.setVisibility(View.VISIBLE);
            p1Cards.setVisibility(View.VISIBLE);
            p1Card1.setImageURI(Uri.fromFile(new File(sdcard,"Android/media/"+cardBack+".png")));
            p1Card2.setImageURI(Uri.fromFile(new File(sdcard,"Android/media/"+cardBack+".png")));

        }

        if (numPlayers > 1) {


            cardR2.setVisibility(View.VISIBLE);
            p2Cards.setVisibility(View.VISIBLE);
            p2Card1.setImageURI(Uri.fromFile(new File(sdcard,"Android/media/"+cardBack+".png")));
            p2Card2.setImageURI(Uri.fromFile(new File(sdcard,"Android/media/"+cardBack+".png")));


            switch (playerNum) {
                case 1:
                    p1Cards.setBackgroundColor(0xFF06402B);
                    break;

                case 2:
                    p2Cards.setBackgroundColor(0xFF06402B);
                    break;

            }

        }

        JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest(Request.Method.DELETE, url+"delete/"+lobbyID, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public synchronized void onResponse(JSONObject response) {


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(getApplicationContext(), "view failed", Toast.LENGTH_LONG).show();
            }
        });

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url+"create/"+lobbyID, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle the successful response here
                        try {
                            String status = response.getString("status");
                            setNumPlayers(response.getInt("players"));
                            System.out.println(response);
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

        queue.add(jsonObjectRequest2);
        myWait(1000);
        queue.add(jsonObjectRequest);



    }

    /**
     *Deals the hand for the game. Deals for everyone at the table
     */
    private void deal(){

        winStatus.setText("");

        p1CardNum = 2;
        dCardNum = 2;

                //splitButton.setVisibility(View.VISIBLE);
                standButton.setVisibility(View.VISIBLE);
                hitButton.setVisibility(View.VISIBLE);
                dealButton.setVisibility(View.GONE);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url+"deal/"+lobbyID, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public synchronized void onResponse(JSONObject response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(getApplicationContext(), "view failed", Toast.LENGTH_LONG).show();
            }
        });

        JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest(Request.Method.GET, url+"getdealer/"+lobbyID, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public synchronized void onResponse(JSONObject response) {


                        try {
                            JSONObject card1 = response.getJSONObject("card1");
                            JSONObject card2 = response.getJSONObject("card2");

                            Integer score = response.getInt("score");
                            dScore.setText("Dealer Score: "+ (score-card2.getInt("value")));

                            String card1S = card1.getString("suit").toLowerCase() + "_" + card1.getString("number");
                            String card2S = card2.getString("suit").toLowerCase() + "_" + card2.getString("number");

                            dCard1.setImageURI(Uri.fromFile(new File(sdcard,"Android/media/"+card1S+".png")));
                            //dCard2.setImageURI(Uri.fromFile(new File(sdcard,"Android/media/"+card2S+".png")));
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

        JsonObjectRequest jsonObjectRequest3 = new JsonObjectRequest(Request.Method.GET, url+"gethand/"+lobbyID+"/"+userID, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public synchronized void onResponse(JSONObject response) {

                        try {
                            JSONObject card1 = response.getJSONObject("card1");
                            JSONObject card2 = response.getJSONObject("card2");

                            Integer score = response.getInt("score");
                            pScore.setText("Your Score: "+score);

                            String card1S = card1.getString("suit").toLowerCase() + "_" + card1.getString("number");
                            String card2S = card2.getString("suit").toLowerCase() + "_" + card2.getString("number");

                            p1Card1.setImageURI(Uri.fromFile(new File(sdcard,"Android/media/"+card1S+".png")));
                            p1Card2.setImageURI(Uri.fromFile(new File(sdcard,"Android/media/"+card2S+".png")));
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

        class MyRunnable implements Runnable {
            public synchronized void run() {
                // Code to be executed in the new thread
                requestQueue.add(jsonObjectRequest);
                try {
                    wait(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                requestQueue.add(jsonObjectRequest2);
                requestQueue.add(jsonObjectRequest3);

            }
        }
        Thread thread = new Thread(new MyRunnable());
        thread.start();



    }

    /**
     *Sends a request to the backend to hit for the user connected from the device
     */
    private void hit(){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url+"hit/"+lobbyID+"/"+userID, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public synchronized void onResponse(JSONObject response) {
                        try {
                            JSONObject card = response.getJSONObject("card");
                            String cardS  = card.getString("suit").toLowerCase() + "_" + card.getString("number");
                            Integer score = response.getInt("score");

                            pScore.setText("Your Score: "+score);

                            System.out.println(response);

                            addPlayer1Card(cardS);

                            if (score >= 21) {
                                    finishGame();

                            }

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
    /**
     *Sends a request to the backend to stand for the user connected from the device
     */
    private void stand() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url+"stand/"+lobbyID+"/"+userID, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public synchronized void onResponse(JSONObject response) {
                        try {
                            System.out.println(response);
                            Integer score = response.getInt("score");
                            pScore.setText("Your Score: "+score);

                            finishGame();

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

    /**
     *Shows the rest of the dealers hand and finishes up the game
     */
    private void finishGame() {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url+"finish/"+lobbyID, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public synchronized void onResponse(JSONObject response) {

                        try {
                            System.out.println(response);
                            JSONArray dealerCards = response.getJSONArray("dealer");
                            winStatus.setText(response.getString(userID.toString()));
                            Integer score = response.getInt("dscore");
                            dScore.setText("Dealer Score: "+score);

                            for (int i = 0; i < dealerCards.length(); i++) {
                                JSONObject card = dealerCards.getJSONObject(i);
                                if (i == 1) {
                                    JSONObject card2 = dealerCards.getJSONObject(1);

                                    String card2S = card2.getString("suit").toLowerCase() + "_" + card2.getString("number");
                                    dCard2.setImageURI(Uri.fromFile(new File(sdcard,"Android/media/"+card2S+".png")));

                                }

                                if (i > 1) {
                                    String cardS = card.getString("suit").toLowerCase() + "_" + card.getString("number");
                                    addDealerCard(cardS);
                                }

                                System.out.println(card);

                            }

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



        requestQueue.add(jsonObjectRequest);

        standButton.setVisibility(View.GONE);
        hitButton.setVisibility(View.GONE);
        startButton.setVisibility(View.VISIBLE);

    }

    /**
     * Adds a card to dealer's hand
     * @param cardS
     */
    private void addDealerCard(String cardS) {

        dCardNum++;
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);


        ImageView dCardNew = new ImageView(this);
        dCardNew.setImageURI(Uri.fromFile(new File(sdcard,"Android/media/"+cardS+".png")));
        dCardNew.setLayoutParams(param);
        dCardNew.setPadding(2,2,2,2);
        dCards.addView(dCardNew);

        switch (dCardNum){
            case 3:
                d1x1 = dCardNew;
                break;
            case 4:
                d1x2 = dCardNew;
                break;
            case 5:
                d1x3 = dCardNew;
                break;
            case 6:
                d1x4 = dCardNew;
                break;
            case 7:
                d1x5 = dCardNew;
                break;
        }

    }

    /**
     * Adds a card to player's hand
     * @param cardS
     */
    private void addPlayer1Card(String cardS) {

        p1CardNum++;
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);


        ImageView p1CardNew = new ImageView(this);
        p1CardNew.setImageURI(Uri.fromFile(new File(sdcard,"Android/media/"+cardS+".png")));
        p1CardNew.setLayoutParams(param);
        p1CardNew.setPadding(2,2,2,2);
        p1Cards.addView(p1CardNew);

        switch (p1CardNum){
            case 3:
                p1x1 = p1CardNew;
                break;
            case 4:
                p1x2 = p1CardNew;
                break;
            case 5:
                p1x3 = p1CardNew;
                break;
            case 6:
                p1x4 = p1CardNew;
                break;
            case 7:
                p1x5 = p1CardNew;
                break;
        }

    }

    /**
     * Timer in a new thread
     * @param waitTime
     */
    private void myWait(int waitTime) {
        class MyRunnable implements Runnable {
            public synchronized void run() {
                // Code to be executed in the new thread

                try {
                    wait(waitTime);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        }
        Thread thread = new Thread(new MyRunnable());
        thread.start();
    }

        private BroadcastReceiver messageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String key = intent.getStringExtra("key");
                if ("chat1".equals(key)) {
                    String message = intent.getStringExtra("message");

                    if (message.contains("DONOTSEND")) {

                    }
                    else {
                        runOnUiThread(() -> {
                            String s = msgTv.getText().toString();
                            msgTv.setText(s + message + "\n");
                            chatArea.fullScroll(View.FOCUS_DOWN);


                        });
                    }
                }

            }
        };

        @Override
        protected void onStart() {
            super.onStart();
            LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver,
                    new IntentFilter("WebSocketMessageReceived"));
        }

        @Override
        protected void onStop() {
            super.onStop();
            LocalBroadcastManager.getInstance(this).unregisterReceiver(messageReceiver);
        }

    /**
     * Setter for global numPlayers variable
     * @param num
     */
        private void setNumPlayers(int num) {
            numPlayers = num;
        }


}


