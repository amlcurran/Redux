package com.espiandev.redux.network;

import android.graphics.Bitmap;

import com.espiandev.redux.assets.Asset;

public interface NetworkHelper {

    void login(String username, String password, Responder<String> responder);

    void search(String query, Responder<String> responder, int resultPage);

    void image(String uuid, String key, Responder<Bitmap> bitmapResponder);

    void assetDetails(String uuid, String key, Responder<Asset> assetResponder);
}
