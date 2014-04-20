package com.espiandev.redux.cast;

import android.widget.SeekBar;

import com.espiandev.redux.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = "AndroidManifest.xml")
public class CastAssetViewTest {

    public static final int MILLIS = 1000;
    private CastAssetView assetView;

    @Mock
    public RemoteController mockRemoteController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        assetView = new CastAssetView(Robolectric.application, null);
        assetView.setRemoteController(mockRemoteController);
    }

    @Test
    public void testTheSeekBar_ScrubsTheVideo() {
        ((SeekBar) assetView.findViewById(R.id.cast_view_seek)).setProgress(MILLIS);

        verify(mockRemoteController).seekTo(Time.fromMillis(MILLIS));
    }

}
