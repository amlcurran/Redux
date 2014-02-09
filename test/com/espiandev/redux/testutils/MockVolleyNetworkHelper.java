package com.espiandev.redux.testutils;

import com.android.volley.RequestQueue;
import com.espiandev.redux.network.VolleyNetworkHelper;

public class MockVolleyNetworkHelper extends VolleyNetworkHelper {

    public MockVolleyNetworkHelper(RequestQueue requestQueue) {
        super(requestQueue);
    }
}
