package com.espiandev.redux.cast;

import android.support.v7.media.MediaRouteSelector;
import android.support.v7.media.MediaRouter;

import com.espiandev.redux.assets.Asset;
import com.espiandev.redux.network.ReduxUrlHelper;
import com.google.android.gms.cast.CastDevice;
import com.google.android.gms.cast.CastMediaControlIntent;
import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.MediaMetadata;
import com.google.android.gms.cast.RemoteMediaPlayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex Curran on 14/02/2014.
 */
public class GoogleCastManager extends MediaRouter.Callback implements CastManager,
        RemoteMediaPlayer.OnStatusUpdatedListener, RemoteMediaPlayer.OnMetadataUpdatedListener,
        CastConnector.CastConnectorCallbacks {

    public static final String APP_ID
            = CastMediaControlIntent.DEFAULT_MEDIA_RECEIVER_APPLICATION_ID;
    private MediaRouter mediaRouter;
    private CastActivityIndicator routeIndicator;
    private CastConnector<CastDevice> connector;
    private RemoteController remoteController;
    private boolean canCast;
    private final List<MediaRouter.RouteInfo> routeInfoList = new ArrayList<>();

    public GoogleCastManager(MediaRouter mediaRouter, CastActivityIndicator routeIndicator, CastConnector<CastDevice> connector) {
        this.mediaRouter = mediaRouter;
        this.routeIndicator = routeIndicator;
        this.connector = connector;
        this.connector.setCallbacks(this);
    }

    @Override
    public void resumeScanning() {
        MediaRouteSelector selector = createSelector();
        mediaRouter.addCallback(selector, this, MediaRouter.CALLBACK_FLAG_PERFORM_ACTIVE_SCAN);
    }

    @Override
    public void pauseScanning() {
        mediaRouter.removeCallback(this);
    }

    @Override
    public void playAsset(Asset asset) {
        if (canCast()) {
            MediaMetadata mediaMetadata = new MediaMetadata();
            mediaMetadata.putString(MediaMetadata.KEY_TITLE, asset.getName());
            MediaInfo mediaInfo = new MediaInfo.Builder(new ReduxUrlHelper().buildDownloadUrl(asset))
                    .setContentType("video/mp4")
                    .setStreamType(MediaInfo.STREAM_TYPE_BUFFERED)
                    .setMetadata(mediaMetadata)
                    .build();
            remoteController.load(mediaInfo);
        }
    }

    @Override
    public boolean canCast() {
        return canCast;
    }

    private MediaRouteSelector createSelector() {
        return new MediaRouteSelector.Builder()
                .addControlCategory(CastMediaControlIntent.categoryForCast(APP_ID))
                .build();
    }

    @Override
    public void onRouteAdded(MediaRouter router, MediaRouter.RouteInfo route) {
        if (!routeInfoList.contains(route)) {
            routeInfoList.add(route);
        }
        routeIndicator.onCastDevicesFound(routeInfoList);
    }

    @Override
    public void onRouteRemoved(MediaRouter router, MediaRouter.RouteInfo route) {
        routeInfoList.remove(route);
        routeIndicator.onCastDevicesFound(routeInfoList);
    }

    @Override
    public void onRouteSelected(MediaRouter router, MediaRouter.RouteInfo route) {
        CastDevice castDevice = CastDevice.getFromBundle(route.getExtras());
        connector.connect(castDevice);
    }

    @Override
    public void onStatusUpdated() {

    }

    @Override
    public void onMetadataUpdated() {

    }

    @Override
    public void onConnected(RemoteController controller) {
        canCast = true;
        this.remoteController = controller;
    }

    @Override
    public void onDisconnected() {
        canCast = false;
    }

    @Override
    public void onConnectionFailed() {
        canCast = false;
    }

    @Override
    public void onVolumeChanged(double volume) {

    }
}
