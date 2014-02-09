package com.espiandev.redux.network;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.espiandev.redux.ReduxApp;

public class VolleyNetworkHelper implements NetworkHelper {

    private final ReduxApp reduxApp;

    public VolleyNetworkHelper(Context context) {
        reduxApp = (ReduxApp) context.getApplicationContext();
    }

    @Override
    public void performGet(String url, final Responder<String> responder) {
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                responder.onSuccessResponse(s);
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                responder.onErrorResponse(error);
            }
        };
        reduxApp.getRequestQueue().add(new StringRequest(url, listener, errorListener));
    }
}
