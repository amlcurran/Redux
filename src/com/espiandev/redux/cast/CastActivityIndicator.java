package com.espiandev.redux.cast;

import android.support.v7.media.MediaRouter;

import java.util.List;

public interface CastActivityIndicator {
    void onCastDevicesFound(List<MediaRouter.RouteInfo> routes);
    void onCastDeviceConnecting(MediaRouter.RouteInfo route);
    void onCastDeviceConnected(MediaRouter.RouteInfo route);
    void onCastDeviceDisconnected(MediaRouter.RouteInfo route);
}
