package com.example.settingspage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import androidx.appcompat.app.AppCompatActivity;

public class UserSettings {
    // Fields for user settings
    private int userId;
    private boolean notificationsEnabled;
    private int brightness; // Value between 1-100
    private boolean onlineStatus;

    // Constructor
    public UserSettings(int userId, boolean notificationsEnabled, int brightness, boolean onlineStatus) {
        this.userId = userId;
        this.notificationsEnabled = notificationsEnabled;
        setBrightness(brightness); // Use setter to validate brightness range
        this.onlineStatus = onlineStatus;
    }

    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isNotificationsEnabled() {
        return notificationsEnabled;
    }

    public void setNotificationsEnabled(boolean notificationsEnabled) {
        this.notificationsEnabled = notificationsEnabled;
    }

    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        if (brightness < 1 || brightness > 100) {
            throw new IllegalArgumentException("Brightness must be between 1 and 100.");
        }
        this.brightness = brightness;
    }

    public boolean isOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(boolean onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    // Method to display user settings (for testing/debugging)
    @Override
    public String toString() {
        return "UserSettings{" +
                "userId='" + userId + '\'' +
                ", notificationsEnabled=" + notificationsEnabled +
                ", brightness=" + brightness +
                ", onlineStatus=" + onlineStatus +
                '}';
    }
}
