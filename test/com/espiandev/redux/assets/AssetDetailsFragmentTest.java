package com.espiandev.redux.assets;

import android.content.Intent;
import android.net.Uri;

import com.espiandev.redux.R;
import com.espiandev.redux.network.ReduxUrlHelper;
import com.espiandev.redux.testing.BaseFragmentTest;
import com.espiandev.redux.testing.TestAsset;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.robolectric.Robolectric.shadowOf;

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
    public void testWhenImageIsClicked_AnIntentIsLaunched() {
        activity.findViewById(R.id.asset_image_view).performClick();

        Intent intent = shadowOf(activity).getNextStartedActivity();
        assertEquals(Uri.parse(new ReduxUrlHelper().buildDownloadUrl(fragment.getAsset())), intent.getData());
    }

    @Test
    public void testTitleIsSet_AsTheProgrammesTitle() {
        assertEquals(TestAsset.NAME, activity.getTitleHostTitle());
    }

}
