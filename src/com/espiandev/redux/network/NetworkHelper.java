package com.espiandev.redux.network;

import com.espiandev.redux.Asset;

public interface NetworkHelper {
    void performGet(String url, Responder<String> responder);

    void login(String username, String password, Responder<String> responder);

    void search(String query, Responder<String> responder);

    void details(String uuid, Responder<Asset> assetResponder);
}
