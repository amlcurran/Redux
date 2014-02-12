package com.espiandev.redux.assets;

import android.app.DownloadManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import com.espiandev.redux.R;
import com.espiandev.redux.testing.BaseFragmentTest;
import com.espiandev.redux.testing.TestAsset;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = "AndroidManifest.xml")
public class AssetDetailsFragmentTest extends BaseFragmentTest<AssetDetailsFragment> {

    @Override
    protected void createFragment() {
        fragment = AssetDetailsFragment.newInstance(new TestAsset());
    }

    @Test
    public void testWhenFragmentIsCreated_AnImageRequestIsSent() {
        verify(mockNetworkHelper).image(TestAsset.UUID, TestAsset.KEY, fragment);
    }

    @Test
    public void testWhenImageComesBack_TheImageViewIsFadedIn() {
        fragment.onSuccessResponse(null);
        verify(mockAnimationFactory).fadeIn(activity.findViewById(R.id.asset_image_view));
    }

    @Test
    public void testWhenImageComesBack_TheResultIsSetOnTheImage() {
        Bitmap bitmapResponse = mock(Bitmap.class);
        fragment.onSuccessResponse(bitmapResponse);
        assertEquals(bitmapResponse, ((BitmapDrawable) ((ImageView) activity.findViewById(R.id.asset_image_view)).getDrawable()).getBitmap());
    }

    @Test
    @Ignore
    public void testWhenImageIsClicked_ADownloadIsRequested() {
        activity.findViewById(R.id.asset_image_view).performClick();

        verify(mockDownloadManager).enqueue(any(DownloadManager.Request.class));
    }

    @Test
    public void testTitleIsSet_AsTheProgrammesTitle() {
        assertEquals(TestAsset.NAME, activity.getTitleHostTitle());
    }

}
