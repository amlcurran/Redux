package com.espiandev.redux.cast;

import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.RemoteMediaPlayer;
import com.google.android.gms.common.api.GoogleApiClient;

public class GoogleCastRemoteController implements RemoteController {

    private final RemoteMediaPlayer remoteMedia;
    private final GoogleApiClient apiClient;

    public GoogleCastRemoteController(RemoteMediaPlayer remoteMedia, GoogleApiClient apiClient) {
        this.remoteMedia = remoteMedia;
        this.apiClient = apiClient;
    }

    @Override
    public void load(MediaInfo mediaInfo) {
        remoteMedia.load(apiClient, mediaInfo, true);
    }
}
