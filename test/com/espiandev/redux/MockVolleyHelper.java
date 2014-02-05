package com.espiandev.redux;

import com.android.volley.RequestQueue;

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
