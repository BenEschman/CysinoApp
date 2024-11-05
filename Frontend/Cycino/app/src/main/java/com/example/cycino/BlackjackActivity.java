package com.example.cycino;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Environment;
import android.provider.Settings;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;


public class BlackjackActivity extends AppCompatActivity {

    private ImageView dCard1;
    private ImageView dCard2;
    private ImageView p1Card1;
    private ImageView p1Card2;

    private Button hitButton;
    private Button standButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blackjack);

        File sdcard = Environment.getExternalStorageDirectory();

        String cardName1 = "clubs_3";
        String cardName2 = "card_back";
        dCard1 = findViewById(R.id.dealerCard1);
        dCard2 = findViewById(R.id.dealerCard2);
        p1Card1 = findViewById(R.id.playerCard1);
        p1Card2 = findViewById(R.id.playerCard2);
        hitButton = findViewById(R.id.hitButton);
        standButton = findViewById(R.id.standButton);

        hitButton.setText("HIT");
        standButton.setText("STAND");

        File cardFile1 = new File(sdcard,"Pictures/"+cardName1+".png");


        Bitmap card1 = BitmapFactory.decodeFile(cardFile1.getAbsolutePath());

        dCard1.setImageBitmap(card1);
        dCard2.setImageURI(Uri.fromFile(new File(sdcard,"Pictures/"+cardName2+".png")));
    }
}
