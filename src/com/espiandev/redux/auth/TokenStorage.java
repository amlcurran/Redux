package com.espiandev.redux.auth;

public interface TokenStorage {
    boolean storeToken(String token);
    boolean hasToken();
    String getToken();
    String extractToken(String jsonResponseString);
    void storeCredentials(String username, String password);
    void purgeCredentials();
}
