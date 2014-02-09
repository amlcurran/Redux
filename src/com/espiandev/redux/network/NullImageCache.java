package com.espiandev.redux.network;

import android.graphics.Bitmap;

import com.android.volley.toolbox.ImageLoader;

public class NullImageCache implements ImageLoader.ImageCache {
    @Override
    public Bitmap getBitmap(String s) {
        return null;
    }

    @Override
    public void putBitmap(String s, Bitmap bitmap) {

    }
}
