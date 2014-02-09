package com.espiandev.redux.network;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.espiandev.redux.assets.Asset;
import com.espiandev.redux.auth.TokenStorage;

public class VolleyNetworkHelper implements NetworkHelper {

    private final RequestQueue requestQueue;
    private TokenStorage tokenStorage;
    private final ReduxUrlHelper urlHelper;

    public VolleyNetworkHelper(RequestQueue requestQueue, TokenStorage tokenStorage) {
        this.requestQueue = requestQueue;
        this.tokenStorage = tokenStorage;
        this.urlHelper = new ReduxUrlHelper();
    }

    @Override
    public void performGet(String url, final Responder<String> responder) {
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                responder.onSuccessResponse(s);
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                responder.onErrorResponse(error);
            }
        };
        requestQueue.add(new StringRequest(url, listener, errorListener));
    }

    @Override
    public void login(String username, String password, Responder<String> responder) {
        performGet(urlHelper.buildLoginUrl(username, password), responder);
    }

    @Override
    public void search(String query, Responder<String> responder) {
        performGet(urlHelper.buildSearchUrl(query, tokenStorage.getToken()), responder);
    }

    @Override
    public void details(String uuid, Responder<Asset> assetResponder) {

    }
}
