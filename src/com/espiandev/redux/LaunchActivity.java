package com.espiandev.redux;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class LaunchActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        if (!hasAuthToken()) {
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    private boolean hasAuthToken() {
        return PreferenceManager.getDefaultSharedPreferences(this).contains("auth_token");
    }
}
