package com.example.cycino;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class FriendChatActivity extends AppCompatActivity {

    private Button backButton;
    private Button friendNameButton;
    private Button sendButton;
    private EditText messageEditText;
    private ScrollView chatScrollView;
    private LinearLayout chatContainer;
    private int userID = 4;
    private String serverURL;
    private int recipientID = 2 ;
    private boolean isNotificationsOn ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendchat); // Ensure this matches your XML filename

        // Initialize UI elements
        backButton = findViewById(R.id.backButton);
        friendNameButton = findViewById(R.id.friendNameButton);
        sendButton = findViewById(R.id.sendButton);
        messageEditText = findViewById(R.id.messageEditText);
        chatScrollView = findViewById(R.id.chatScrollView);
        chatContainer = findViewById(R.id.chatContainer);

        String recipientSR = "@" + recipientID + " " ; // SET WHEN USER CLICKS ON CHAT WITH SPECIFIC FRIEND

        String serverUrl = "ws://coms-3090-052.class.las.iastate.edu:8080/directMessaging/" + userID;

        Intent serviceIntent = new Intent(this, WebSocketService.class);
        serviceIntent.setAction("CONNECT");
        serviceIntent.putExtra("key", "chat2");
        serviceIntent.putExtra("url", serverUrl);
        startService(serviceIntent);

        sendButton.setOnClickListener(v -> {
            String messageContent = messageEditText.getText().toString().trim();
            if (!messageContent.isEmpty()) {
                String message = recipientSR + messageContent;

                Intent intent = new Intent("SendWebSocketMessage");
                intent.putExtra("key", "chat2");
                intent.putExtra("message", message);
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

                messageEditText.setText("");
            } else {
                Toast.makeText(FriendChatActivity.this, "Please enter a message.", Toast.LENGTH_SHORT).show();
            }
        });
        // Set up click listeners
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {






                finish(); // Closes the activity and returns to the previous screen
            }
        });
    }

    private BroadcastReceiver messageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String key = intent.getStringExtra("key");
            if ("chat2".equals(key)) {
                String message = intent.getStringExtra("message");

                // Check if the message is from the current user
                if (!message.startsWith("[DM] " + userID + ":")) {
                    // Show a toast for a new message from the recipient
                    Toast.makeText(FriendChatActivity.this, "You've got a new message from " + recipientID, Toast.LENGTH_SHORT).show();

                    runOnUiThread(() -> addMessageToChat(message, false));
                } else {
                    // This is a sent message echo from the server
                    runOnUiThread(() -> addMessageToChat(message, true));
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

    private void addMessageToChat(String message, boolean isSentByUser) {
        // Create a new TextView for the message
        TextView messageTextView = new TextView(this);
        messageTextView.setText(message);
        messageTextView.setTextSize(16);
        messageTextView.setPadding(16, 8, 16, 8);

        // Set different styles for messages sent by user vs. received
        if (isSentByUser) {
            messageTextView.setBackgroundResource(R.drawable.sent_message_background); // Use a custom drawable for sent messages
            messageTextView.setTextColor(Color.WHITE);
            // Align the message to the right
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 8, 0, 8);
            params.gravity = Gravity.END;
            messageTextView.setLayoutParams(params);
        } else {
            messageTextView.setBackgroundResource(R.drawable.received_message_background); // Use a custom drawable for received messages
            messageTextView.setTextColor(Color.BLACK);
            // Align the message to the left
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 8, 0, 8);
            params.gravity = Gravity.START;
            messageTextView.setLayoutParams(params);
        }

        // Add the TextView to the chat container
        chatContainer.addView(messageTextView);

        // Scroll to the bottom of the ScrollView to show the new message
        chatScrollView.post(() -> chatScrollView.fullScroll(View.FOCUS_DOWN));
    }
}

