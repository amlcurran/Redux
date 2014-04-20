package com.espiandev.redux.assets;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.widget.ImageView;

import com.espiandev.redux.R;
import com.espiandev.redux.network.ReduxUrlHelper;
import com.espiandev.redux.testing.BaseFragmentTest;
import com.espiandev.redux.testing.TestAsset;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.robolectric.Robolectric.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = "AndroidManifest.xml")
public class AssetDetailsFragmentTest extends BaseFragmentTest<AssetDetailsFragment> {

    private TestAsset asset;

    @Override
    protected void createFragment() {
        asset = new TestAsset();
        fragment = AssetDetailsFragment.newInstance(asset);
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
    public void testTitleIsSet_AsTheProgrammesTitle() {
        assertEquals(TestAsset.NAME, activity.getTitleHostTitle());
    }

    @Test
    public void testWhenThePlayButtonIsClicked_AVideoUrlIsFired() {
        ReduxUrlHelper helper = new ReduxUrlHelper();

        activity.findViewById(R.id.button_play).performClick();

        Intent intent = shadowOf(activity).getNextStartedActivity();
        Uri firedUri = shadowOf(intent).getData();
        assertEquals(helper.buildDownloadUrl(asset, null), String.valueOf(firedUri));
    }

    @Test
    public void testWhenTheCastButtonIsClicked_AndCastingIsPossible_CastingStarts() {
        when(mockCastManager.canCast()).thenReturn(true);

        activity.findViewById(R.id.button_cast).performClick();

        verify(mockCastManager).playAsset(asset);
    }

    @Test
    public void testWhenTheDownloadButtonIsClicked_TheDownloadIsRequested() {
        activity.findViewById(R.id.button_download).performClick();

        verify(mockDownloader).requestDownload(asset);
    }

}
