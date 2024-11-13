package com.example.cycino;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.cycino.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainSettingsActivity extends AppCompatActivity {

    private Button backButton;
    private Button logOutButton;
    private SeekBar brightnessDial;
    private TextView brightnessLabel;
    private Switch notificationsSwitch;
    private Button accountSettingsButton;
    private Switch onlineStatusSwitch;
    private Button resetLeaderboardButton;
    private Button updateDealerStandsOnButton;
    private Button updateMinBetButton;
    private Button updateMaxBetButton;
    private Button updateNumberOfDecksButton;
    private EditText dealerStandsOnEdit;
    private EditText minBetEdit;
    private EditText maxBetEdit;
    private EditText numberOfDecksEdit;
    private TextView dealerStandOnTV ;
    private TextView minBetTV ;
    private TextView maxBetTV ;
    private TextView deckNumberTV ;
    private int userID = 7;
    private int dealerStandOn ;
    private int minBet ;
    private int maxBet ;
    private int numberOfDecks ;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainsettings); // Link the XML layout to this Java file

        // Initialize UI elements
        backButton = findViewById(R.id.backButton);
        logOutButton = findViewById(R.id.logOutButton);
        brightnessDial = findViewById(R.id.brightnessDial);
        brightnessLabel = findViewById(R.id.brightnessLabel);
        notificationsSwitch = findViewById(R.id.notificationsSwitch);
        accountSettingsButton = findViewById(R.id.accountSettingsButton);
        onlineStatusSwitch = findViewById(R.id.onlineStatusSwitch);
        resetLeaderboardButton = findViewById(R.id.resetLeaderboardButton); // Added
        updateDealerStandsOnButton = findViewById(R.id.updateDealerStandOnButton);
        updateMinBetButton = findViewById(R.id.updateMinBetButton);
        updateMaxBetButton = findViewById(R.id.updateMaxBetButton);
        updateNumberOfDecksButton = findViewById(R.id.updateNumberOfDecksButton);
        updateDealerStandsOnButton = findViewById(R.id.updateDealerStandOnButton);
        dealerStandsOnEdit = findViewById(R.id.dealerStandOnEdit);
        minBetEdit = findViewById(R.id.minBetEdit);
        maxBetEdit = findViewById(R.id.maxBetEdit);
        numberOfDecksEdit = findViewById(R.id.numberOfDecksEdit);
        dealerStandOnTV = findViewById(R.id.dealerStandOnDisplay);
        minBetTV = findViewById(R.id.minBetDisplay);
        maxBetTV = findViewById(R.id.maxBetDisplay);
        deckNumberTV = findViewById(R.id.numberOfDecksDisplay);

        // EVENTUALLY GET THE USER ID, FOR NOW JUST USE GLOBAL VARIABLE FOR TESTING!!!!!!!!

        getSettings(userID) ; // Sets the user settings (global vars)
        updateSettingsDisplay(dealerStandOn, minBet, maxBet, numberOfDecks);


        // Set the initial brightness level for this activity
        int savedBrightnessLevel = getSharedPreferences("AppSettingsPrefs", MODE_PRIVATE)
                .getInt("BrightnessLevel", 50); // Default to 50%
        setActivityBrightness(savedBrightnessLevel);

        // Set the SeekBar to the saved brightness level
        brightnessDial.setProgress(savedBrightnessLevel);
        brightnessLabel.setText("Brightness: " + savedBrightnessLevel);

        // Set listeners for buttons
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle back button click
                Intent intent = new Intent(MainSettingsActivity.this, HomePageActivity.class);
                startActivity(intent); // Start the HomePagePlaceholder activity
            }
        });

        accountSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle account settings button click
                Intent intent = new Intent(MainSettingsActivity.this, AccountSettingsActivity.class);
                startActivity(intent); // Start the AccountSettings activity
            }
        });

        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Log out the user through mySQL and take them to the sign-in page.
            }
        });

        // Set a listener for the Reset Leaderboard button
        resetLeaderboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Placeholder action for resetting the leaderboard
                Toast.makeText(MainSettingsActivity.this, "Leaderboard has been reset", Toast.LENGTH_SHORT).show();
            }
        });

        brightnessDial.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Adjust screen brightness based on the progress value
                setSystemBrightness(progress);

                // Update the brightness label
                brightnessLabel.setText("Brightness: " + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Optional: Add any logic you want when the user starts interacting with the SeekBar
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Save the brightness value to SharedPreferences when the user stops changing the value
                int brightnessLevel = seekBar.getProgress();
                getSharedPreferences("AppSettingsPrefs", MODE_PRIVATE)
                        .edit()
                        .putInt("BrightnessLevel", brightnessLevel)
                        .apply();

                // Update the brightness in a userSettings MySQL table
                Toast.makeText(MainSettingsActivity.this, "Brightness saved", Toast.LENGTH_SHORT).show();
            }
        });

        updateNumberOfDecksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numberOfDecksValue = numberOfDecksEdit.getText().toString();
                if (!numberOfDecksValue.isEmpty()) {
                    try {
                        int value = Integer.parseInt(numberOfDecksValue);
                        if (value >= 1 && value <= 5) {
                            updateGameSettings(dealerStandOn, maxBet, minBet, value, userID);
                            numberOfDecks = value ;
                        } else {
                            Toast.makeText(MainSettingsActivity.this, "Please enter a value between 1 and 5 decks.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (NumberFormatException e) {
                        Toast.makeText(MainSettingsActivity.this, "Invalid input. Please enter a valid number.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainSettingsActivity.this, "Please enter a value", Toast.LENGTH_SHORT).show();
                }
            }
        });

        updateMaxBetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maxBetValue = maxBetEdit.getText().toString();
                if (!maxBetValue.isEmpty()) {
                    try {
                        int value = Integer.parseInt(maxBetValue);
                        if (value >= 1 && value > minBet) {
                            updateGameSettings(dealerStandOn, value, minBet, numberOfDecks, userID);
                            maxBet = value ;
                        } else {
                            Toast.makeText(MainSettingsActivity.this, "Please enter a valid maxBet size", Toast.LENGTH_SHORT).show();
                        }
                    } catch (NumberFormatException e) {
                        Toast.makeText(MainSettingsActivity.this, "Invalid input. Please enter a valid number.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainSettingsActivity.this, "Please enter a value", Toast.LENGTH_SHORT).show();
                }
            }
        });

        updateMinBetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String minBetValue = minBetEdit.getText().toString();
                if (!minBetValue.isEmpty()) {
                    try {
                        int value = Integer.parseInt(minBetValue);
                        if (value >= 1 && value < maxBet) {
                            updateGameSettings(dealerStandOn, maxBet, value, numberOfDecks, userID);
                            minBet = value ;
                        } else {
                            Toast.makeText(MainSettingsActivity.this, "Please enter a valid minBet size", Toast.LENGTH_SHORT).show();
                        }
                    } catch (NumberFormatException e) {
                        Toast.makeText(MainSettingsActivity.this, "Invalid input. Please enter a valid number.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainSettingsActivity.this, "Please enter a value", Toast.LENGTH_SHORT).show();
                }
            }
        });

        updateDealerStandsOnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dealerStandOnValue = dealerStandsOnEdit.getText().toString();
                if (!dealerStandOnValue.isEmpty()) {
                    try {
                        int value = Integer.parseInt(dealerStandOnValue);
                        if (value >= 15 && value <= 21) {
                            updateGameSettings(value, maxBet, minBet, numberOfDecks ,userID);
                            dealerStandOn = value ;
                        } else {
                            Toast.makeText(MainSettingsActivity.this, "Please enter a value inclusively between 15 and 21", Toast.LENGTH_SHORT).show();
                        }
                    } catch (NumberFormatException e) {
                        Toast.makeText(MainSettingsActivity.this, "Invalid input. Please enter a valid number.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainSettingsActivity.this, "Please enter a value", Toast.LENGTH_SHORT).show();
                }
            }
        });


        onlineStatusSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                handleOnlineStatusSwitchChange(isChecked);
            }
        });

        notificationsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                handleNotificationsSwitchChange(isChecked);
            }
        });

    }


    /**
     * @param isEnabled
     */
    private void handleNotificationsSwitchChange(boolean isEnabled) {
        String url = "" ;
        if (isEnabled) {
            JSONObject jsonBody = new JSONObject();
            try {
                jsonBody.put("isEnabled", isEnabled);
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Error creating request body", Toast.LENGTH_SHORT).show();
                return;
            }
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.PUT, url, jsonBody, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("AccountSettings", "Server Response: " + response.toString());
                            try {
                                String status = response.getString("status");
                                if (status.equals("200 ok")) {
                                    Toast.makeText(getApplicationContext(), "Notification preference has been updated.", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(), "Error parsing server response", Toast.LENGTH_LONG).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (error.networkResponse != null && error.networkResponse.data != null) {
                                String errorMsg = new String(error.networkResponse.data);
                                Log.e("VolleyError", errorMsg);
                            }
                            Toast.makeText(getApplicationContext(), "Server error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                            error.printStackTrace();
                        }
                    }) {
            };
            Toast.makeText(MainSettingsActivity.this, "Notifications are enabled", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainSettingsActivity.this, "Notifications are disabled", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * @param brightnessLevel
     */
    private void setActivityBrightness(int brightnessLevel) {
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.screenBrightness = brightnessLevel / 100.0f; // Convert from 0-100 to 0-1 scale
        getWindow().setAttributes(layoutParams);
    }

    /**
     * @param brightnessLevel
     */
    private void setSystemBrightness(int brightnessLevel) {
        try {
            if (Settings.System.canWrite(this)) {
                int systemBrightness = (int) (brightnessLevel * 2.55); // Convert from 0-100 to 0-255 scale
                Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, systemBrightness);
            } else {
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(android.net.Uri.parse("package:" + getPackageName()));
                startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Unable to change brightness", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * @param isEnabled
     */
    private void handleOnlineStatusSwitchChange(boolean isEnabled) {
        if (isEnabled) {
            String url = "http://coms-3090-052.class.las.iastate.edu:8080/";  //Add user settings endpoint
            JSONObject jsonBody = new JSONObject();
            try {
                jsonBody.put("isEnabled", isEnabled);
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Error creating request body", Toast.LENGTH_SHORT).show();
                return;
            }
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.PUT, url, jsonBody, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("AccountSettings", "Server Response: " + response.toString());
                            try {
                                String status = response.getString("status");
                                if (status.equals("200 ok")) {
                                    Toast.makeText(getApplicationContext(), "Online Status preference has been updated.", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(), "Error parsing server response", Toast.LENGTH_LONG).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (error.networkResponse != null && error.networkResponse.data != null) {
                                String errorMsg = new String(error.networkResponse.data);
                                Log.e("VolleyError", errorMsg);
                            }
                            Toast.makeText(getApplicationContext(), "Server error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                            error.printStackTrace();
                        }
                    }) {
            };
            Toast.makeText(MainSettingsActivity.this, "Notifications are enabled", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainSettingsActivity.this, "Notifications are disabled", Toast.LENGTH_SHORT).show();
        }
    }

    // Update in user settings database table if needed.

    /**
     * @param dealerStandOn
     * @param maxBet
     * @param minBet
     * @param amountOfDecks
     * @param userID
     */
    private void updateGameSettings(int dealerStandOn, int maxBet, int minBet, int amountOfDecks, int userID) {
        String url = "http://coms-3090-052.class.las.iastate.edu:8080/gameSettings/blackjack/user/" + userID + "/update";
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("dealerStandOn", dealerStandOn);
            jsonBody.put("maxBet", maxBet);  // example values for other fields
            jsonBody.put("minBet", minBet);
            jsonBody.put("amountOfDecks", amountOfDecks);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error creating request body", Toast.LENGTH_SHORT).show();
            return;
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.PUT, url, jsonBody, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("AccountSettings", "Server Response: " + response.toString());
                        try {
                            String status = response.getString("status");
                            if (status.equals("200 OK")) {
                                Toast.makeText(getApplicationContext(), "Settings have been updated successfully.", Toast.LENGTH_SHORT).show();
                                updateSettingsDisplay(dealerStandOn, minBet, maxBet, numberOfDecks) ;
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Unexpected status: " + status, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Error parsing server response", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error.networkResponse != null && error.networkResponse.data != null) {
                            String errorMsg = new String(error.networkResponse.data);
                            Log.e("VolleyError", "Error message: " + errorMsg);
                        }
                        else {
                            Log.e("VolleyError", "Unknown network error", error);
                        }
                        Toast.makeText(getApplicationContext(), "Server error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(jsonObjectRequest);
    }

    /**
     * @param userID
     */
    private void getSettings(int userID)
    {
        String url = "http://coms-3090-052.class.las.iastate.edu:8080/gameSettings/blackjack/user/" + userID  ;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            dealerStandOn = response.getInt("dealerStandOn") ;
                            maxBet = response.getInt("maxBet");
                            minBet = response.getInt("minBet");
                            numberOfDecks = response.getInt("amountOfDecks");
                            updateSettingsDisplay(dealerStandOn, minBet, maxBet, numberOfDecks) ;
                            Toast.makeText(getApplicationContext(), "Settings loaded successfully", Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Error parsing settings", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(getApplicationContext(), "Get did not work!", Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(jsonObjectRequest);
    }


    /**
     * @param dealerStandOn
     * @param minBet
     * @param maxBet
     * @param numberOfDecks
     */
    private void updateSettingsDisplay(int dealerStandOn, int minBet, int maxBet, int numberOfDecks) {
        // Update each TextView with the new values
        dealerStandOnTV.setText("Dealer Stand-On Value: " + dealerStandOn);
        minBetTV.setText("Minimum Bet: " + minBet);
        maxBetTV.setText("Maximum Bet: " + maxBet);
        deckNumberTV.setText("Number of Decks: " + numberOfDecks);
    }
}






