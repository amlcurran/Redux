package com.espiandev.redux;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Main extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(this, LoginActivity.class));
    }
}
