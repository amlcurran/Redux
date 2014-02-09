package com.espiandev.redux.network;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.espiandev.redux.ReduxApp;

public class ApplicationVolleyHelper implements VolleyHelper {

    private final ReduxApp reduxApp;

    public ApplicationVolleyHelper(Context context) {
        reduxApp = (ReduxApp) context.getApplicationContext();
    }

    @Override
    public RequestQueue getRequestQueue() {
        return reduxApp.getRequestQueue();
    }
}
