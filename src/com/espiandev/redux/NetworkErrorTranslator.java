package com.espiandev.redux;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.TimeoutError;
import com.espiandev.redux.auth.TokenError;

public class NetworkErrorTranslator {

    public static String getErrorString(ResourceStringProvider provider, Exception error) {
        if (error instanceof AuthFailureError) {
            return provider.getString(R.string.volley_error_auth);
        } else if (error instanceof TimeoutError) {
            return provider.getString(R.string.volley_error_timeout);
        } else if (error instanceof NetworkError) {
            return provider.getString(R.string.volley_error_network);
        } else if (error instanceof TokenError) {
            return provider.getString(R.string.volley_error_token);
        }
        return provider.getString(R.string.volley_error_generic);
    }

}
