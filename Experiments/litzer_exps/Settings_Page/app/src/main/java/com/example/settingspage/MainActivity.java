package com.example.settingspage;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private Button backButton;
    private Button logOutButton;
    private SeekBar brightnessDial;
    private TextView brightnessLabel;
    private Switch notificationsSwitch;
    private Button accountSettingsButton;
    private Switch onlineStatusSwitch;
    private Button resetLeaderboardButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Link the XML layout to this Java file

        // Initialize UI elements
        backButton = findViewById(R.id.backButton);
        logOutButton = findViewById(R.id.logOutButton);
        brightnessDial = findViewById(R.id.brightnessDial);
        brightnessLabel = findViewById(R.id.brightnessLabel);
        notificationsSwitch = findViewById(R.id.notificationsSwitch);
        accountSettingsButton = findViewById(R.id.accountSettingsButton);
        onlineStatusSwitch = findViewById(R.id.onlineStatusSwitch);
        resetLeaderboardButton = findViewById(R.id.resetLeaderboardButton); // Added

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
                Intent intent = new Intent(MainActivity.this, HomePagePlaceholder.class);
                startActivity(intent); // Start the HomePagePlaceholder activity
            }
        });

        accountSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle account settings button click
                Intent intent = new Intent(MainActivity.this, AccountSettings.class);
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
                Toast.makeText(MainActivity.this, "Leaderboard has been reset", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(MainActivity.this, "Brightness saved", Toast.LENGTH_SHORT).show();
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

    private void handleNotificationsSwitchChange(boolean isEnabled) {
        if (isEnabled) {
            String url = "http://coms-3090-052.class.las.iastate.edu:8080/" ;  //Add user settings endpoint
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
            Toast.makeText(MainActivity.this, "Notifications are enabled", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "Notifications are disabled", Toast.LENGTH_SHORT).show();
        }
    }

    private void setActivityBrightness(int brightnessLevel) {
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.screenBrightness = brightnessLevel / 100.0f; // Convert from 0-100 to 0-1 scale
        getWindow().setAttributes(layoutParams);
    }

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

    private void handleOnlineStatusSwitchChange(boolean isEnabled) {
        if (isEnabled) {
            String url = "http://coms-3090-052.class.las.iastate.edu:8080/" ;  //Add user settings endpoint
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
            Toast.makeText(MainActivity.this, "Notifications are enabled", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "Notifications are disabled", Toast.LENGTH_SHORT).show();
        }
    }

        // Update in user settings database table if needed.
    }
