package com.espiandev.redux.network;

import com.android.volley.VolleyError;

public interface Responder<T> {
    void onSuccessResponse(T response);
    void onErrorResponse(Exception error);
}
