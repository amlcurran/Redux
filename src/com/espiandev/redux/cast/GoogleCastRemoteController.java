package com.espiandev.redux.cast;

import com.espiandev.redux.assets.Asset;
import com.espiandev.redux.network.ReduxUrlHelper;
import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.MediaMetadata;
import com.google.android.gms.cast.MediaStatus;
import com.google.android.gms.cast.RemoteMediaPlayer;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.IOException;

public class GoogleCastRemoteController implements RemoteController, RemoteMediaPlayer.OnStatusUpdatedListener {

    private final RemoteMediaPlayer remoteMedia;
    private final GoogleApiClient apiClient;
    private Listener listener;

    public GoogleCastRemoteController(RemoteMediaPlayer remoteMedia, GoogleApiClient apiClient) {
        this.remoteMedia = remoteMedia;
        this.remoteMedia.setOnStatusUpdatedListener(this);
        this.apiClient = apiClient;
    }

    @Override
    public void load(Asset asset, ReduxUrlHelper urlHelper) {
        MediaMetadata mediaMetadata = new MediaMetadata();
        mediaMetadata.putString(MediaMetadata.KEY_TITLE, asset.getName());
        String contentUrl = urlHelper.buildDownloadUrl(asset);
        MediaInfo mediaInfo = new MediaInfo.Builder(contentUrl)
                .setContentType("video/mp4")
                .setStreamType(MediaInfo.STREAM_TYPE_BUFFERED)
                .setMetadata(mediaMetadata)
                .build();
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
    public void seekTo(Time time) {
        remoteMedia.seek(apiClient, time.getMillis());
    }

    @Override
    public void setListener(Listener listener) {
        if (listener == null) {
            this.listener = Listener.NONE;
        } else {
            this.listener = listener;
        }
        onStatusUpdated();
    }

    @Override
    public void onStatusUpdated() {

        if (remoteMedia.getMediaStatus() == null) {
            return;
        }

        switch (remoteMedia.getMediaStatus().getPlayerState()) {

            case MediaStatus.PLAYER_STATE_PLAYING:
                listener.onResumed();
                break;

            case MediaStatus.PLAYER_STATE_PAUSED:
                listener.onPaused();
                break;

            case MediaStatus.PLAYER_STATE_BUFFERING:
                listener.onBuffering();
                break;

        }

        Time elapsedTime = Time.fromMillis(remoteMedia.getApproximateStreamPosition());
        Time totalTime = Time.fromMillis(remoteMedia.getStreamDuration());
        listener.onTotalTimeChanged(totalTime);
        listener.onElapsedTimeChanged(elapsedTime);

    }


}
