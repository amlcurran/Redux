package com.espiandev.redux.downloads;

import com.espiandev.redux.assets.Asset;

public interface Downloader {
    long requestDownload(Asset asset);
    void monitorProgress(long id, DownloadProgressListener progressListener);
}
