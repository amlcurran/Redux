package com.espiandev.redux.network;

public interface Responder<T> {
    void onSuccessResponse(T response);
    void onErrorResponse(Exception error);
}
