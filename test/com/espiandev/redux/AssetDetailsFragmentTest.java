package com.espiandev.redux;

import com.espiandev.redux.testing.BaseFragmentTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = "AndroidManifest.xml")
public class AssetDetailsFragmentTest extends BaseFragmentTest<AssetDetailsFragment> {

    public static final String PINGU_UUID = "26a141fc-8511-4fef-aa2b-af1d1de5a75a";
    private static final String PINGU = "pingu";

    @Override
    protected void createFragment() {
        Channel mockChannel = mock(Channel.class);
        Asset asset = mock(Asset.class);

        when(asset.getName()).thenReturn(PINGU);
        when(asset.getChannel()).thenReturn(mockChannel);

        fragment = AssetDetailsFragment.newInstance(asset);
    }

    @Test
    public void testTitleIsSet_AsTheProgrammesTitle() {
        assertEquals(PINGU, activity.getTitleHostTitle());
    }

}
