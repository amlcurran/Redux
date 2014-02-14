package com.espiandev.redux.cast;

import com.google.android.gms.cast.Cast;
import com.google.android.gms.cast.CastDevice;
import com.google.android.gms.cast.CastMediaControlIntent;
import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.MediaMetadata;
import com.google.android.gms.cast.RemoteMediaPlayer;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import com.espiandev.redux.assets.Asset;
import com.espiandev.redux.network.ReduxUrlHelper;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.MediaRouteButton;
import android.support.v7.media.MediaRouteSelector;
import android.support.v7.media.MediaRouter;

import java.io.IOException;

/**
 * Created by Alex Curran on 14/02/2014.
 */
public class GoogleCastManager extends MediaRouter.Callback implements CastManager,
        RemoteMediaPlayer.OnStatusUpdatedListener, RemoteMediaPlayer.OnMetadataUpdatedListener {

    public static final String APP_ID
            = CastMediaControlIntent.DEFAULT_MEDIA_RECEIVER_APPLICATION_ID;
    private Context context;
    private MediaRouter mediaRouter;
    private MediaRouteButton routeButton;
    private GoogleApiClient apiClient;

    public GoogleCastManager(Context context, MediaRouter mediaRouter,
            MediaRouteButton routeButton) {
        this.context = context;
        this.mediaRouter = mediaRouter;
        this.routeButton = routeButton;
    }

    @Override
    public void resumeScanning() {
        MediaRouteSelector selector = createSelector();
        routeButton.setRouteSelector(selector);
        mediaRouter.addCallback(selector, this, MediaRouter.CALLBACK_FLAG_PERFORM_ACTIVE_SCAN);
    }

    @Override
    public void pauseScanning() {
        mediaRouter.removeCallback(this);
    }

    @Override
    public void playAsset(Asset asset) {
        if (apiClient.isConnected()) {
            MediaMetadata mediaMetadata = new MediaMetadata();
            mediaMetadata.putString(MediaMetadata.KEY_TITLE, asset.getName());
            MediaInfo mediaInfo = new MediaInfo.Builder(new ReduxUrlHelper().buildDownloadUrl(asset))
                    .setContentType("video/mp4")
                    .setStreamType(MediaInfo.STREAM_TYPE_BUFFERED)
                    .setMetadata(mediaMetadata)
                    .build();
            remoteMedia.load(apiClient, mediaInfo, true);
        }
    }

    private MediaRouteSelector createSelector() {
        return new MediaRouteSelector.Builder()
                .addControlCategory(CastMediaControlIntent.categoryForCast(APP_ID))
                .build();
    }

    @Override
    public void onRouteSelected(MediaRouter router, MediaRouter.RouteInfo route) {

        CastDevice castDevice = CastDevice.getFromBundle(route.getExtras());
        Cast.CastOptions.Builder apiOptionsBuilder = Cast.CastOptions
                .builder(castDevice, castClientListener);

        apiClient = new GoogleApiClient.Builder(context)
                .addApi(Cast.API, apiOptionsBuilder.build())
                .addConnectionCallbacks(connectionCallbacks)
                .addOnConnectionFailedListener(connectionCallbacks)
                .build();

        apiClient.connect();
    }

    private Cast.Listener castClientListener = new Cast.Listener() {

        @Override
        public void onVolumeChanged() {
            super.onVolumeChanged();
        }

    };

    private RemoteMediaPlayer remoteMedia;
    private ConnectionHandler connectionCallbacks = new ConnectionHandler() {

        @Override
        public void onConnected(Bundle bundle) {
            try {
                Cast.CastApi.launchApplication(apiClient, APP_ID, false)
                        .setResultCallback(
                                new ResultCallback<Cast.ApplicationConnectionResult>() {
                                    @Override
                                    public void onResult(Cast.ApplicationConnectionResult result) {
                                        Status status = result.getStatus();
                                        if (status.isSuccess()) {
                                            remoteMedia = new RemoteMediaPlayer();
                                            remoteMedia.setOnMetadataUpdatedListener(
                                                    GoogleCastManager.this);
                                            remoteMedia.setOnStatusUpdatedListener(
                                                    GoogleCastManager.this);
                                            try {
                                                Cast.CastApi.setMessageReceivedCallbacks(apiClient, remoteMedia.getNamespace(), remoteMedia);
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onConnectionFailed(ConnectionResult connectionResult) {
            super.onConnectionFailed(connectionResult);
        }
    };

    @Override
    public void onStatusUpdated() {

    }

    @Override
    public void onMetadataUpdated() {

    }
}
