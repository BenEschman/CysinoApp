package com.example.cycino;

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

import java.io.File;


public class BlackjackActivity extends AppCompatActivity {

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

    private LinearLayout p1Cards;
    private LinearLayout p2Cards;
    private LinearLayout p3Cards;
    private LinearLayout p4Cards;

    private LinearLayout cardR1;
    private LinearLayout cardR2;

    private Button hitButton;
    private Button standButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blackjack);

        int numPlayers = 4;
        int playerNum = 2;

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

        p1Cards = findViewById(R.id.player1Cards);
        p2Cards = findViewById(R.id.player2Cards);
        p3Cards = findViewById(R.id.player3Cards);
        p4Cards = findViewById(R.id.player4Cards);

        cardR1 = findViewById(R.id.cardsR1);
        cardR2 = findViewById(R.id.cardsR2);

        hitButton = findViewById(R.id.hitButton);
        standButton = findViewById(R.id.standButton);

        hitButton.setText("HIT");
        hitButton.setTextColor(0xFFFFFFFF);
        hitButton.setBackgroundColor(0xFFFF0000);
        standButton.setText("STAND");
        standButton.setTextColor(0xFFFFFFFF);
        standButton.setBackgroundColor(0xFFFF0000);


        startGame(numPlayers,playerNum);
    }

    private void startGame(int numPlayers, int playerNum) {

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

            p2Cards.setVisibility(View.VISIBLE);
            p2Card1.setImageURI(Uri.fromFile(new File(sdcard,"Pictures/"+cardBack+".png")));
            p2Card2.setImageURI(Uri.fromFile(new File(sdcard,"Pictures/"+cardBack+".png")));

        }

        if (numPlayers > 2) {

            cardR2.setVisibility(View.VISIBLE);
            p3Cards.setVisibility(View.VISIBLE);
            p4Cards.setVisibility(View.VISIBLE);
            p4Card1.setVisibility(View.INVISIBLE);
            p4Card2.setVisibility(View.INVISIBLE);
            p3Card1.setImageURI(Uri.fromFile(new File(sdcard,"Pictures/"+cardBack+".png")));
            p3Card2.setImageURI(Uri.fromFile(new File(sdcard,"Pictures/"+cardBack+".png")));

        }

        if (numPlayers > 3) {

            p4Card1.setVisibility(View.VISIBLE);
            p4Card2.setVisibility(View.VISIBLE);
            p4Card1.setImageURI(Uri.fromFile(new File(sdcard,"Pictures/"+cardBack+".png")));
            p4Card2.setImageURI(Uri.fromFile(new File(sdcard,"Pictures/"+cardBack+".png")));

        }

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


}


