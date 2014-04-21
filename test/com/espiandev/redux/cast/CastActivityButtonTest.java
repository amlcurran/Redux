package com.espiandev.redux.cast;

import android.support.v7.media.MediaRouter;
import android.view.View;

import com.espiandev.redux.cast.ui.CastActivityButton;

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
public class CastActivityButtonTest {

    @Test
    public void testWhenSomeCastDevicesAreFound_TheButtonIsVisible() {
        CastActivityButton button = new CastActivityButton(Robolectric.application);

        List<MediaRouter.RouteInfo> routeInfoList = mock(List.class);
        when(routeInfoList.size()).thenReturn(3);

        button.onCastDevicesFound(routeInfoList);

        assertEquals(View.VISIBLE, button.getVisibility());
    }

    @Test
    public void testWhenNoCastDevicesAreFound_TheButtonIsNotVisible() {
        CastActivityButton button = new CastActivityButton(Robolectric.application);

        List<MediaRouter.RouteInfo> routeInfoList = mock(List.class);
        when(routeInfoList.size()).thenReturn(0);

        button.onCastDevicesFound(routeInfoList);

        assertEquals(View.INVISIBLE, button.getVisibility());
    }

}
