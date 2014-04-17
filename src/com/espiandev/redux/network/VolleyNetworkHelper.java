package com.espiandev.redux.network;

import android.graphics.Bitmap;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.espiandev.redux.auth.TokenStorage;
import com.espiandev.redux.auth.Validator;

public class VolleyNetworkHelper implements NetworkHelper {

    private final RequestQueue requestQueue;
    private final TokenStorage tokenStorage;
    private final ReduxUrlHelper urlHelper;
    private final ImageLoader imageLoader;
    private final Validator validator;

    public VolleyNetworkHelper(RequestQueue requestQueue, TokenStorage tokenStorage) {
        this.requestQueue = requestQueue;
        this.tokenStorage = tokenStorage;
        this.imageLoader = new ImageLoader(requestQueue, new NullImageCache());
        this.urlHelper = new ReduxUrlHelper();
        this.validator = new Validator();
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
        if (!validator.isUsernameValid(username)) {
            responder.onErrorResponse(new InvalidUsernameError());
        } else if (!validator.isPasswordValid(password)) {
            responder.onErrorResponse(new InvalidPasswordError());
        } else {
            performGet(urlHelper.buildLoginUrl(username, password), responder);
        }
    }

    @Override
    public void search(String query, Responder<String> responder, int resultPage) {
        performGet(urlHelper.buildSearchUrl(query, tokenStorage.getToken(), resultPage), responder);
    }

    @Override
    public void image(String uuid, String key, final Responder<Bitmap> bitmapResponder) {
        imageLoader.get(urlHelper.buildImageUrl(uuid, key), new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
                bitmapResponder.onSuccessResponse(imageContainer.getBitmap());
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                bitmapResponder.onErrorResponse(error);
            }
        });
    }
}
