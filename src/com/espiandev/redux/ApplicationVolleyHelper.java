package com.espiandev.redux;

import android.content.Context;

import com.android.volley.RequestQueue;

public class ApplicationVolleyHelper implements VolleyHelper {

    private final ReduxApp reduxApp;

    public ApplicationVolleyHelper(Context context) {
        reduxApp = (ReduxApp) context.getApplicationContext();
    }

    @Override
    public RequestQueue getRequestQueue() {
        return reduxApp.requestQueue;
    }
}
