package com.espiandev.redux.cast;

import android.view.View;

import com.espiandev.redux.cast.ui.CastButton;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = "AndroidManifest.xml")
public class CastButtonTest {

    @Test
    public void testWhenSomeCastDevicesAreFound_TheButtonIsVisible() {
        CastButton button = new CastButton(Robolectric.application);

        List<CastableDevice> routeInfoList = mock(List.class);
        when(routeInfoList.size()).thenReturn(3);

        button.onCastDevicesFound(routeInfoList);

        assertEquals(View.VISIBLE, button.getVisibility());
    }

    @Test
    public void testWhenNoCastDevicesAreFound_TheButtonIsNotVisible() {
        CastButton button = new CastButton(Robolectric.application);

        List<CastableDevice> routeInfoList = mock(List.class);
        when(routeInfoList.size()).thenReturn(0);

        button.onCastDevicesFound(routeInfoList);

        assertEquals(View.INVISIBLE, button.getVisibility());
    }

}
