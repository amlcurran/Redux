package com.espiandev.redux.network;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

public class VolleyNetworkHelper implements NetworkHelper {

    private final RequestQueue requestQueue;
    private final ReduxUrlHelper urlHelper;

    public VolleyNetworkHelper(RequestQueue requestQueue) {
        this.requestQueue = requestQueue;
        this.urlHelper = new ReduxUrlHelper();
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
        requestQueue.add(new StringRequest(url, listener, errorListener));
    }

    @Override
    public void login(String username, String password, Responder<String> responder) {
        performGet(urlHelper.buildLoginUrl(username, password), responder);
    }
}
