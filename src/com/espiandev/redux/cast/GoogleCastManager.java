package com.espiandev.redux.cast;

import android.content.Context;
import android.support.v7.media.MediaRouteSelector;
import android.support.v7.media.MediaRouter;

import com.espiandev.redux.assets.Asset;
import com.espiandev.redux.cast.ui.CastActivityIndicator;
import com.espiandev.redux.network.ReduxUrlHelper;
import com.google.android.gms.cast.CastDevice;
import com.google.android.gms.cast.CastMediaControlIntent;
import com.google.android.gms.cast.RemoteMediaPlayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex Curran on 14/02/2014.
 */
public class GoogleCastManager extends MediaRouter.Callback implements CastManager,
        RemoteMediaPlayer.OnStatusUpdatedListener, RemoteMediaPlayer.OnMetadataUpdatedListener,
        CastConnector.CastConnectorCallbacks {

    public static final String APP_ID = CastMediaControlIntent.DEFAULT_MEDIA_RECEIVER_APPLICATION_ID;
    private final CastActivityIndicator activityIndicator;
    private final ReduxUrlHelper urlHelper;
    private Context context;
    private MediaRouter mediaRouter;
    private CastConnector<CastDevice> connector;
    private RemoteController remoteController;
    private boolean canCast;
    private final List<CastableDevice> routeInfoList = new ArrayList<>();

    public GoogleCastManager(Context context, MediaRouter mediaRouter, CastActivityIndicator activityIndicator, CastConnector<CastDevice> connector) {
        this.context = context;
        this.mediaRouter = mediaRouter;
        this.connector = connector;
        this.connector.setCallbacks(this);
        this.activityIndicator = activityIndicator;
        this.urlHelper = new ReduxUrlHelper(context);
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
    public RemoteController playAsset(Asset asset) {
        if (canCast()) {
            remoteController.load(asset, urlHelper);
            return remoteController;
        }
        return null;
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
        activityIndicator.onCastDevicesFound(router.getRoutes());
    }

    @Override
    public void onRouteRemoved(MediaRouter router, MediaRouter.RouteInfo route) {
        activityIndicator.onCastDevicesFound(router.getRoutes());
    }

    @Override
    public void onRouteSelected(MediaRouter router, MediaRouter.RouteInfo route) {
        CastDevice castDevice = CastDevice.getFromBundle(route.getExtras());
        connector.connect(castDevice);
        activityIndicator.onCastDeviceConnecting(route.getName());
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
        activityIndicator.onCastDeviceConnected(null);
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
