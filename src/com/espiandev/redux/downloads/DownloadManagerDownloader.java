package com.espiandev.redux.downloads;

import android.app.DownloadManager;
import android.net.Uri;
import android.os.Environment;

import com.espiandev.redux.assets.Asset;
import com.espiandev.redux.network.ReduxUrlHelper;

import java.io.File;

public class DownloadManagerDownloader implements Downloader {

    private final DownloadManager downloadManager;
    private final ReduxUrlHelper urlHelper;

    public DownloadManagerDownloader(DownloadManager downloadManager) {
        this.downloadManager = downloadManager;
        this.urlHelper = new ReduxUrlHelper();
    }

    @Override
    public void requestDownload(Asset asset) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(urlHelper.buildDownloadUrl(asset)));
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_MOVIES, getSubPath(asset));
        request.setMimeType("video/mpeg").setTitle(asset.getName());
        downloadManager.enqueue(request);
    }

    private String getSubPath(Asset asset) {
        File moviesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
        String testFileName = String.format("%1$s.mp4", asset.getName());
        File testFile = new File(moviesDir, testFileName);
        int current = 0;
        while (testFile.exists()) {
            current++;
            testFileName = String.format("%1$s-%2$d.mp4", asset.getName(), current);
            testFile = new File(moviesDir, testFileName);
        }
        return testFileName;
    }
}
