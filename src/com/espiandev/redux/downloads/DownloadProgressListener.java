package com.espiandev.redux.downloads;

public interface DownloadProgressListener {
    public void onProgressUpdate(String uuid, float fraction);
    public void onDownloadCompleted(long uuid);
    public void onDownloadFailed(String uuid, Exception reason);
}
