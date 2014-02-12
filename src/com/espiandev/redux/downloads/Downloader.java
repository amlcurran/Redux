package com.espiandev.redux.downloads;

import com.espiandev.redux.assets.Asset;

public interface Downloader {
    void requestDownload(Asset asset);
}
