package com.espiandev.redux.network;

import com.espiandev.redux.network.ReduxUrlHelper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = "AndroidManifest.xml")
public class ReduxUrlHelperTest {

    @Test
    public void testUrlHelperFormatsALogInUrlCorrectly() {
        ReduxUrlHelper helper = new ReduxUrlHelper();
        String url = helper.buildLoginUrl("USERNAME", "PASSWORD");

        assertEquals("https://i.bbcredux.com/user/login?username=USERNAME&password=PASSWORD", url);
    }

}
