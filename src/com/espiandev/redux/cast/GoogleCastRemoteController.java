package com.espiandev.redux.cast;

import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.RemoteMediaPlayer;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.IOException;

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

    @Override
    public void play() {
        try {
            remoteMedia.play(apiClient);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void pause() {
        try {
            remoteMedia.pause(apiClient);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void seekTo(long millis) {
        remoteMedia.seek(apiClient, millis);
    }
}
