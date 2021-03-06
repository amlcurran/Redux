package com.espiandev.redux.cast;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.espiandev.redux.R;
import com.espiandev.redux.assets.AssetView;

public class CastAssetView extends AssetView implements View.OnClickListener, RemoteController.Listener, SeekBar.OnSeekBarChangeListener {

    private View playButton;
    private View pauseButton;
    private View bufferingSpinner;
    private RemoteController controller;
    private SeekBar scrubBar;
    private TextView elapsedText;
    private TextView totalText;

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
        bufferingSpinner = findViewById(R.id.asset_cast_progress);
        elapsedText = (TextView) findViewById(R.id.asset_cast_elapsed);
        totalText = (TextView) findViewById(R.id.asset_cast_total);
        scrubBar = (SeekBar) findViewById(R.id.cast_view_seek);
        if (!isInEditMode()) {
            scrubBar.setOnSeekBarChangeListener(this);
            playButton.setOnClickListener(this);
            pauseButton.setOnClickListener(this);
            setVisibility(GONE);
        }
        hidePlayButton();
        hidePauseButton();
    }

    public void setRemoteController(RemoteController controller) {
        this.controller = controller;
        this.controller.setListener(this);
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "alpha", 1);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                setAlpha(0);
                setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
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
        showPlayButton();
        hidePauseButton();
        hideSpinner();
    }

    @Override
    public void onResumed() {
        hidePlayButton();
        showPauseButton();
        hideSpinner();
    }

    @Override
    public void onBuffering() {
        showSpinner();
    }

    @Override
    public void onElapsedTimeChanged(Time elapsedTime) {
        scrubBar.setProgress((int) elapsedTime.getMillis());
        elapsedText.setText(elapsedTime.formatHms());
    }

    @Override
    public void onTotalTimeChanged(Time totalTime) {
        scrubBar.setMax((int) totalTime.getMillis());
        totalText.setText(totalTime.formatHms());
    }

    private void showSpinner() {
        bufferingSpinner.setVisibility(VISIBLE);
    }

    private void hideSpinner() {
        bufferingSpinner.setVisibility(GONE);
    }

    private void hidePauseButton() {
        pauseButton.setVisibility(GONE);
    }

    private void showPlayButton() {
        playButton.setVisibility(VISIBLE);
    }

    private void showPauseButton() {
        pauseButton.setVisibility(VISIBLE);
    }

    private void hidePlayButton() {
        playButton.setVisibility(GONE);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        controller.seekTo(Time.fromMillis(seekBar.getProgress()));
    }
}
