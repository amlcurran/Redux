package com.espiandev.redux;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class Main extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!hasAuthToken()) {
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    private boolean hasAuthToken() {
        return PreferenceManager.getDefaultSharedPreferences(this).contains("auth_token");
    }
}
