package com.espiandev.redux.downloads;

import android.app.DownloadManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;

import com.espiandev.redux.assets.Asset;
import com.espiandev.redux.network.ReduxUrlHelper;

import java.io.File;

public class DownloadManagerDownloader implements Downloader {

    private Context context;
    private final DownloadManager downloadManager;
    private final ReduxUrlHelper urlHelper;

    public DownloadManagerDownloader(Context context, DownloadManager downloadManager) {
        this.context = context;
        this.downloadManager = downloadManager;
        this.urlHelper = new ReduxUrlHelper();
    }

    @Override
    public long requestDownload(Asset asset) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(urlHelper.buildDownloadUrl(asset, PreferenceManager.getDefaultSharedPreferences(context))));
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_MOVIES, getSubPath(asset));
        request.setMimeType("video/mpeg").setTitle(asset.getName());
        return downloadManager.enqueue(request);
    }

    @Override
    public void monitorProgress(long id, DownloadProgressListener progressListener) {
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(id);
        Cursor result = downloadManager.query(query);
        if (result != null && result.getCount() > 0) {
            result.moveToFirst();
            checkIfDownloadCompleted(id, result, progressListener);
        }
    }

    private void checkIfDownloadCompleted(long id, Cursor result, DownloadProgressListener progressListener) {
        int downloadStatus = result.getInt(result.getColumnIndex(DownloadManager.COLUMN_STATUS));
        if (downloadStatus == DownloadManager.STATUS_SUCCESSFUL) {
            progressListener.onDownloadCompleted(id);
        }
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
