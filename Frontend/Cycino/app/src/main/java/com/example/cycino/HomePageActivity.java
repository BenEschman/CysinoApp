package com.example.cycino;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class HomePageActivity extends AppCompatActivity {

    private Button lobby,leaderboard,friends,settings, rulesBtn, bankBtn ;

    private String username;
    private Integer UUID;

    /**
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        Intent intent = getIntent();
        username = intent.getStringExtra("USERNAME");
        UUID = intent.getIntExtra("UUID",-1);

        lobby = findViewById(R.id.lobbyButton);
        leaderboard = findViewById(R.id.leaderboardButton);
        friends = findViewById(R.id.friendsButton);
        settings = findViewById(R.id.settingsButton);
        rulesBtn = findViewById(R.id.rulesButton);
        bankBtn = findViewById(R.id.bankButton);

        lobby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomePageActivity.this,BlackjackActivity.class);
                i.putExtra("USERNAME",username);
                i.putExtra("UUID",UUID);
                startActivity(i);

            }
        });
        leaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomePageActivity.this,LeaderboardActivity.class);
                i.putExtra("USERNAME",username);
                i.putExtra("UUID",UUID);
                startActivity(i);

            }
        });
        friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomePageActivity.this,FriendPageActivity.class);
                i.putExtra("USERNAME",username);
                i.putExtra("UUID",UUID);
                startActivity(i);

            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomePageActivity.this,MainSettingsActivity.class);
                i.putExtra("USERNAME",username);
                i.putExtra("UUID",UUID);
                startActivity(i);

            }
        });
        rulesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomePageActivity.this,RulesActivity.class);
                i.putExtra("USERNAME",username);
                i.putExtra("UUID",UUID);
                startActivity(i);
            }
        });

        bankBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomePageActivity.this,BankActivity.class);
                i.putExtra("USERNAME",username);
                i.putExtra("UUID",UUID);
                startActivity(i);
            }
        });


    }
}
