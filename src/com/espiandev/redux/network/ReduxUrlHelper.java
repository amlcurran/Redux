package com.espiandev.redux.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;

import com.espiandev.redux.assets.Asset;

public class ReduxUrlHelper {

    private static final String REDUX_AUTHORITY = "i.bbcredux.com";
    private static final String PATH_LOGIN = "user/login";
    private static final String PATH_SEARCH = "asset/search";
    private static final String IMAGE_PATH = "asset/media/%1$s/%2$s/JPEG-1280x/image.jpg";
    private static final String DOWNLOAD_PATH = "asset/media/%1$s/%2$s/%4$s/%3$s.mp4";
    public static final int PAGE_SIZE = 20;
    private Context context;

    public ReduxUrlHelper(Context context) {
        this.context = context;
    }

    public String buildLoginUrl(String username, String password) {
        return String.valueOf(getBaseUrl()
                .path(PATH_LOGIN)
                .appendQueryParameter("username", username)
                .appendQueryParameter("password", password)
                .build());
    }

    public String buildSearchUrl(String query, String token, int resultPage) {
        return String.valueOf(getBaseUrl().path(PATH_SEARCH)
                .appendQueryParameter("q", query)
                .appendQueryParameter("token", token)
                .appendQueryParameter("offset", String.valueOf(resultPage * PAGE_SIZE))
                .appendQueryParameter("limit", String.valueOf(PAGE_SIZE)).build());
    }

    private Uri.Builder getBaseUrl() {
        return new Uri.Builder().scheme("https")
                .authority(REDUX_AUTHORITY);
    }

    public String buildImageUrl(String uuid, String key) {
        return String.valueOf(getBaseUrl().path(String.format(IMAGE_PATH, uuid, key))
                .build());
    }

    public String buildDownloadUrl(Asset asset) {
        String qualityString = getQualityString(PreferenceManager.getDefaultSharedPreferences(context));
        String downloadPath = String.format(DOWNLOAD_PATH, asset.getUuid(), asset.getKey(), asset.getName(), qualityString);
        return String.valueOf(getBaseUrl().path(downloadPath));
    }

    private String getQualityString(SharedPreferences preferences) {
        if (preferences != null && preferences.getBoolean("highQualityVideo", true)) {
            return "h264_mp4_hi_v1.1";
        }
        return "h264_mp4_lo_v1.0";
    }
}
