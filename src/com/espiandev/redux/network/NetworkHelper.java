package com.espiandev.redux.network;

import android.graphics.Bitmap;

public interface NetworkHelper {
    void performGet(String url, Responder<String> responder);

    void login(String username, String password, Responder<String> responder);

    void search(String query, Responder<String> responder);

    void image(String uuid, String key, Responder<Bitmap> bitmapResponder);
}
