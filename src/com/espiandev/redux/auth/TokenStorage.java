package com.espiandev.redux.auth;

public interface TokenStorage {
    boolean storeToken(String jsonString);
    boolean hasToken();
    String getToken();
}
