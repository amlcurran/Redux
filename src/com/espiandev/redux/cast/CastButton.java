package com.espiandev.redux.cast;

import android.content.Context;
import android.support.v7.media.MediaRouter;
import android.support.v7.mediarouter.R;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import java.util.List;

public class CastButton extends ImageView implements CastActivityIndicator {

    public CastButton(Context context) {
        this(context, null, 0);
    }

    public CastButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CastButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setVisibility(INVISIBLE);
        setImageResource(R.drawable.mr_ic_media_route_holo_light);
    }

    @Override
    public void onCastDevicesFound(List<MediaRouter.RouteInfo> routes) {
        setVisibility(routes.size() == 0 ? View.INVISIBLE : View.VISIBLE);
    }

    @Override
    public void onCastDeviceConnecting(MediaRouter.RouteInfo route) {

    }

    @Override
    public void onCastDeviceConnected(MediaRouter.RouteInfo route) {

    }

    @Override
    public void onCastDeviceDisconnected(MediaRouter.RouteInfo route) {

    }
}