package com.example.cycino;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class BaccaratActivity extends AppCompatActivity {

    public int lobbyID = 2 ;
    public String userUsername = "Filip" ;
    private Button sendBtn, chatToggleBtn, tieBtn, bankerBtn, playerBtn;
    private EditText msgEtx;
    private TextView msgTv;
    private ImageView playerCard1, playerCard2, playerCard3, bankerCard1, bankerCard2, bankerCard3 ;
    private ScrollView chatArea;
    private boolean chatOpen = true;
    private String serverURL = "ws://coms-3090-052.class.las.iastate.edu:8080/chat/" + lobbyID + "/" + userUsername ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baccarat);

        // Initialize chat components
        sendBtn = findViewById(R.id.sendBtn);
        chatToggleBtn = findViewById(R.id.toggleChat);
        msgEtx = findViewById(R.id.msgEdt);
        msgTv = findViewById(R.id.tx1);
        chatArea = findViewById(R.id.chatView);
        tieBtn = findViewById(R.id.tieButton);
        playerBtn = findViewById(R.id.playerButton);
        bankerBtn = findViewById(R.id.bankerButton);
        playerCard1 = findViewById(R.id.playerCard1);
        playerCard2 = findViewById(R.id.playerCard2);
        playerCard3 = findViewById(R.id.playerCard3);


        // Get username from intent
//        Intent inIntent = getIntent();
//        username = inIntent.getStringExtra("USERNAME");

        // Initialize WebSocket connection
        String serverUrl = serverURL ;
        Intent serviceIntent = new Intent(this, WebSocketService.class);
        serviceIntent.setAction("CONNECT");
        serviceIntent.putExtra("key", "chat1");
        serviceIntent.putExtra("url", serverUrl);
        startService(serviceIntent);

        // Set up send button listener
        sendBtn.setOnClickListener(v -> {
            String msgStr = msgEtx.getText().toString();
            if (!msgStr.isEmpty()) {
                Intent intent = new Intent("SendWebSocketMessage");
                intent.putExtra("key", "chat1");
                intent.putExtra("message", msgStr);
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
                msgEtx.setText("");
            }
        });

        // Toggle chat visibility
        chatToggleBtn.setOnClickListener(v -> {
            if (chatOpen) {
                sendBtn.setVisibility(View.GONE);
                msgEtx.setVisibility(View.GONE);
                chatArea.setVisibility(View.GONE);
                chatToggleBtn.setText("Open Chat");
                chatOpen = false;
            } else {
                sendBtn.setVisibility(View.VISIBLE);
                msgEtx.setVisibility(View.VISIBLE);
                chatArea.setVisibility(View.VISIBLE);
                chatToggleBtn.setText("Close Chat");
                chatOpen = true;
            }
        });

        tieBtn.setOnClickListener(v -> handleDecision("TIE"));
        playerBtn.setOnClickListener(v -> handleDecision("PLAYER"));
        bankerBtn.setOnClickListener(v -> handleDecision("BANKER"));
    }

    // BroadcastReceiver for receiving WebSocket messages
    private BroadcastReceiver messageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String key = intent.getStringExtra("key");

            if ("chat1".equals(key)) {
                String message = intent.getStringExtra("message");

                if (message != null) {
                    // Check if the message starts with #BACCARAT
                    if (message.startsWith("#BACCARAT")) {
                        handleBaccaratMessage(message); // Handle game-specific logic
                    } else if (!message.contains("DONOTSEND")) {
                        // Display other messages in chat
                        runOnUiThread(() -> {
                            String s = msgTv.getText().toString();
                            msgTv.setText(s + message + "\n");
                            chatArea.fullScroll(View.FOCUS_DOWN);
                        });
                    }
                }
            }
        }
    };

    private void handleBaccaratMessage(String gameInfo)
    {
        String[] lines = gameInfo.split("\n");

        String calls = null;
        String gameState = null;
        String gameResult = null;
        String playerCards = null;
        String bankerCards = null;

        // Parse each line to extract data
        for (String line : lines) {
            if (line.startsWith("CALLS:")) {
                calls = line.substring("CALLS:".length()).trim();
            } else if (line.startsWith("GAMESTATE:")) {
                gameState = line.substring("GAMESTATE:".length()).trim();
            } else if (line.startsWith("GAMERESULT:")) {
                gameResult = line.substring("GAMERESULT:".length()).trim();
            } else if (line.startsWith("PLAYER")) {
                playerCards = line.substring("PLAYER".length()).trim();
            } else if (line.startsWith("BANKER")) {
                bankerCards = line.substring("BANKER".length()).trim();
            }
        }

        if (playerCards != null) {
            updatePlayerCards(playerCards);
        }
//        if (bankerCards != null) {
//            updateBankerCards(bankerCards);
//        }
    }

    private void updatePlayerCards(String playerCards) {
        // Split the cards string into individual card names
        String[] cardArray = playerCards.split(" ");

        // Reference all player card ImageViews in an array
        ImageView[] playerCardViews = {playerCard1, playerCard2, playerCard3};

        // Loop through the card views and set the appropriate card drawable
        for (int i = 0; i < playerCardViews.length; i++) {
            if (i < cardArray.length) {
                String cardName = cardArray[i];
                int resourceId = getResources().getIdentifier(cardName.toLowerCase(), "drawable", getPackageName());

                if (resourceId != 0) {
                    // Set the drawable resource and make the card visible
                    playerCardViews[i].setImageResource(resourceId);
                    playerCardViews[i].setVisibility(View.VISIBLE);
                } else {
                    // If drawable not found, hide the card (fallback scenario)
                    playerCardViews[i].setVisibility(View.INVISIBLE);
                }
            } else {
                // Hide remaining ImageViews if there are fewer cards
                playerCardViews[i].setVisibility(View.INVISIBLE);
            }
        }
    }

    private void updateBankerCards(String playerCards)
    {

    }



    private void handleBet(int betAmount)
    {

    }



    private void handleDecision(String decision)
    {
        hideDecisionButtons() ;
        String decStr =  "#BACCARAT: PICK " + decision ;
        Intent intent = new Intent("SendWebSocketMessage");
        intent.putExtra("key", "chat1");
        intent.putExtra("message", decStr);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private void hideDecisionButtons()
    {
        tieBtn.setVisibility(View.GONE);
        playerBtn.setVisibility(View.GONE);
        bankerBtn.setVisibility(View.GONE);
    }


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



}