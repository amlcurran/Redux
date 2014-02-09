package com.espiandev.redux.testutils;

import com.android.volley.RequestQueue;
import com.espiandev.redux.network.VolleyHelper;

public class MockVolleyHelper implements VolleyHelper {

    private RequestQueue requestQueue;

    public MockVolleyHelper(RequestQueue requestQueue) {
        this.requestQueue = requestQueue;
    }

    @Override
    public RequestQueue getRequestQueue() {
        return requestQueue;
    }
}
