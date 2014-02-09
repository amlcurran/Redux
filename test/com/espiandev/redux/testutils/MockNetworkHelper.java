package com.espiandev.redux.testutils;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.espiandev.redux.network.NetworkHelper;
import com.espiandev.redux.network.Responder;

public class MockNetworkHelper implements NetworkHelper {

    private RequestQueue requestQueue;

    public MockNetworkHelper(RequestQueue requestQueue) {
        this.requestQueue = requestQueue;
    }

    @Override
    public void performGet(String url, Responder<String> responder) {
        requestQueue.add(new StringRequest(url, null, null));
    }
}
