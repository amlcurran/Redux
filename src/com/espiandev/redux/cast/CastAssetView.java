package com.espiandev.redux.cast;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.espiandev.redux.R;
import com.espiandev.redux.assets.AssetView;

public class CastAssetView extends AssetView implements View.OnClickListener, RemoteController.Listener {

    private View playButton;
    private View pauseButton;
    private RemoteController controller;

    public CastAssetView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CastAssetView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setUpLayout();
    }

    private void setUpLayout() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_asset_cast, this);
        playButton = findViewById(R.id.asset_cast_play);
        pauseButton = findViewById(R.id.asset_cast_pause);
        setVisibility(GONE);
    }

    public void setRemoteController(RemoteController controller) {
        this.controller = controller;
        this.controller.setListener(this);
        setVisibility(VISIBLE);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(playButton)) {
            controller.play();
        } else {
            controller.pause();
        }
    }

    @Override
    public void onPaused() {
        playButton.setVisibility(VISIBLE);
        pauseButton.setVisibility(GONE);
    }

    @Override
    public void onResumed() {
        playButton.setVisibility(GONE);
        pauseButton.setVisibility(VISIBLE);
    }
}
