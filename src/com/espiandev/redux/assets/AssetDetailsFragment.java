package com.espiandev.redux.assets;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.espiandev.redux.BasicFragment;
import com.espiandev.redux.R;
import com.espiandev.redux.ResourceStringProvider;
import com.espiandev.redux.cast.CastManager;
import com.espiandev.redux.cast.CastManagerProvider;
import com.espiandev.redux.downloads.Downloader;
import com.espiandev.redux.downloads.DownloaderProvider;
import com.espiandev.redux.network.Responder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class AssetDetailsFragment extends BasicFragment implements Responder<Bitmap>, View.OnClickListener {
    private static final String ASSET = "asset";
    private ImageView assetImageView;
    private DateFormat dateFormat = new SimpleDateFormat("E dd MMM yyyy 'at' HH:mm");
    private TextView assetDescriptionView;
    private Downloader downloader;
    private CastManager castManager;

    public static AssetDetailsFragment newInstance(Asset asset) {
        AssetDetailsFragment fragment = new AssetDetailsFragment();
        Bundle b = new Bundle();
        b.putParcelable(ASSET, asset);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_asset_detail, container, false);
        assetImageView = (ImageView) view.findViewById(R.id.asset_image_view);
        assetImageView.setOnClickListener(this);
        assetImageView.setVisibility(View.INVISIBLE);
        assetDescriptionView = (TextView) view.findViewById(R.id.asset_description);
        assetDescriptionView.setText(getAsset().getDescription());
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof DownloaderProvider) {
            downloader = ((DownloaderProvider) activity).getDownloader();
        }
        if (activity instanceof CastManagerProvider) {
            castManager = ((CastManagerProvider) activity).getCastManager();
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        networkHelper.image(getAsset().getUuid(), getAsset().getKey(), this);
    }

    @Override
    public CharSequence getHostedSubtitle(ResourceStringProvider stringProvider) {
        return dateFormat.format(getAsset().getBroadcastDate());
    }

    @Override
    public CharSequence getHostedTitle(ResourceStringProvider stringProvider) {
        return getAsset().getName();
    }

    public Asset getAsset() {
        return (Asset) getArguments().getParcelable(ASSET);
    }

    @Override
    public void onSuccessResponse(Bitmap response) {
        assetImageView.setImageBitmap(response);
        animationFactory.fadeIn(assetImageView);
    }

    @Override
    public void onErrorResponse(Exception error) {

    }

    @Override
    public void onClick(View view) {
        if (castManager.canCast()) {
            castManager.playAsset(getAsset());
        } else {

        }
        //downloader.requestDownload(getAsset());
//        DownloadManager.Request request = new DownloadManager.Request(uri);
//        request.setMimeType("video/mpeg")
//                .setTitle(getAsset().getName());
//        downloader.enqueue(request);
    }
}
