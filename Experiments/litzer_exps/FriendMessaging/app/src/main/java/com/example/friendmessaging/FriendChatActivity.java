package com.example.friendmessaging;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.net.URI;
import java.net.URISyntaxException;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

public class FriendChatActivity extends AppCompatActivity {

    private WebSocketClient mWebSocketClient;

    private ScrollView chatArea ;
    private EditText mInput;
    private TextView mOutput;
    private Button bConnect, bDisconnect, SendButton, backMainButton;
    private LinearLayout messageContainer;

    private String serverURL ;
    private String username = "Evan" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendchat); // Ensure XML file is named activity_chat.xml

        // Initialize UI elements
        backMainButton = findViewById(R.id.backMainBtn);
        mInput = findViewById(R.id.msgEdt);
        chatArea = findViewById(R.id.chatScrollView) ;
        SendButton = findViewById(R.id.sendBtn);
        messageContainer = findViewById(R.id.messageContainer);
        mOutput = new TextView(this); // TextView to display chat messages dynamically

        serverURL = "ws://echo.websocket.org" ;
        //String serverUrl = serverURL + username

        Intent serviceIntent = new Intent(this, WebSocketService.class);
        serviceIntent.setAction("CONNECT");
        serviceIntent.putExtra("key", "chat1");
        serviceIntent.putExtra("url", serverURL);
        startService(serviceIntent);

        // Send button click listener
        SendButton.setOnClickListener(v -> {
            String message = mInput.getText().toString();
            if (message != null && message.length() > 0) {
                // Send the message through the WebSocket
                Intent intent = new Intent("SendWebSocketMessage");
                intent.putExtra("key", "chat1");
                intent.putExtra("message", message);
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
                mInput.setText("") ;
            }
        });

        backMainButton.setOnClickListener(v -> {
            Intent intent = new Intent(FriendChatActivity.this, MainActivity.class);
            startActivity(intent) ;
            finish();
        });
    }

    // For receiving messages
    // only react to messages with tag "chat1"
    private BroadcastReceiver messageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String key = intent.getStringExtra("key");
            if ("chat1".equals(key)) {
                String message = intent.getStringExtra("message");
                runOnUiThread(() -> {
                    String s = mOutput.getText().toString();
                    mOutput.setText(s + message + "\n");
                    chatArea.fullScroll(View.FOCUS_DOWN);
                });
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




}