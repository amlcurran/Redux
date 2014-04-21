package com.espiandev.redux.cast.ui;

import android.support.v7.media.MediaRouter;

import com.espiandev.redux.cast.CastableDevice;

import java.util.List;

public interface CastActivityIndicator {
    void onCastDevicesFound(List<MediaRouter.RouteInfo> routes);
    void onCastDeviceConnecting(String route);
    void onCastDeviceConnected(CastableDevice route);
    void onCastDeviceDisconnected(CastableDevice route);
}
