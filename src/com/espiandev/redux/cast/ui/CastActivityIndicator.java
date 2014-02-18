package com.espiandev.redux.cast.ui;

import com.espiandev.redux.cast.CastableDevice;

import java.util.List;

public interface CastActivityIndicator {
    void onCastDevicesFound(List<CastableDevice> routes);
    void onCastDeviceConnecting(CastableDevice route);
    void onCastDeviceConnected(CastableDevice route);
    void onCastDeviceDisconnected(CastableDevice route);
}
