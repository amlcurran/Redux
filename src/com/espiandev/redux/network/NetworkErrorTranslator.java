package com.espiandev.redux.network;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.TimeoutError;
import com.espiandev.redux.R;
import com.espiandev.redux.ResourceStringProvider;
import com.espiandev.redux.auth.TokenError;

public class NetworkErrorTranslator {

    public static String getErrorString(ResourceStringProvider provider, Exception error) {
        if (error instanceof AuthFailureError) {
            return provider.getString(R.string.volley_error_invalid_cred);
        } else if (error instanceof TimeoutError) {
            return provider.getString(R.string.volley_error_timeout);
        } else if (error instanceof NetworkError) {
            return provider.getString(R.string.volley_error_network);
        } else if (error instanceof TokenError) {
            return provider.getString(R.string.volley_error_token);
        } else if (error instanceof InvalidPasswordError) {
            return provider.getString(R.string.login_error_password);
        } else if (error instanceof InvalidUsernameError) {
            return provider.getString(R.string.login_error_username);
        }
        return provider.getString(R.string.volley_error_generic);
    }

}
