package com.example.cycino;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class BaccaratActivity extends AppCompatActivity {

    private Button sendBtn, chatToggleBtn, tieBtn, bankerBtn, playerBtn;
    private EditText msgEtx;
    private TextView msgTv;
    private ScrollView chatArea;
    private boolean chatOpen = true;
    private String serverURL = "ws://coms-3090-052.class.las.iastate.edu:8080/chat/";
    private String username;

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


        // Get username from intent
        Intent inIntent = getIntent();
        username = inIntent.getStringExtra("USERNAME");

        // Initialize WebSocket connection
        String serverUrl = serverURL + "1/" + username;
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

        tieBtn.setOnClickListener(v -> handleDecision("Tie"));
        playerBtn.setOnClickListener(v -> handleDecision("Player"));
        bankerBtn.setOnClickListener(v -> handleDecision("Banker"));
    }

    // BroadcastReceiver for receiving WebSocket messages
    private BroadcastReceiver messageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String key = intent.getStringExtra("key");
            if ("chat1".equals(key)) {
                String message = intent.getStringExtra("message");
                if (!message.contains("DONOTSEND")) {
                    runOnUiThread(() -> {
                        String s = msgTv.getText().toString();
                        msgTv.setText(s + message + "\n");
                        chatArea.fullScroll(View.FOCUS_DOWN);
                    });
                }
            }
        }
    };

    private void handleDecision(String decision)
    {
        hideDecisionButtons() ;
        if(decision == "Tie")
        {
            
        }
        else if(decision == "Player")
        {

        }
        else if(decision == "Banker")
        {

        }
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