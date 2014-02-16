package com.espiandev.redux.downloads;

import android.app.DownloadManager;

import com.espiandev.redux.testing.TestAsset;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = "AndroidManifest.xml")
public class DownloadManagerDownloaderTest {

    @Mock
    private DownloadManager mockDownloadManager;

    private Downloader downloader;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        downloader = new DownloadManagerDownloader(mockDownloadManager);
    }

    @Test
    public void testRequestingDownload_EnqueuesARequest() {
        downloader.requestDownload(new TestAsset());

        verify(mockDownloadManager).enqueue(any(DownloadManager.Request.class));
    }

    @Test
    public void testMonitoringProgress_QueriesTheDownloadManager() {
        downloader.monitorProgress(0, null);

        verify(mockDownloadManager).query(any(DownloadManager.Query.class));
    }

}
