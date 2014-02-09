package com.espiandev.redux;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;

import javax.net.ssl.SSLSocketFactory;

public class ReduxApp extends Application {

    private RequestQueue requestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        HurlStack hurlStack = new HurlStack(null, (SSLSocketFactory) SSLSocketFactory.getDefault());
        requestQueue = Volley.newRequestQueue(this, hurlStack);
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }
}
