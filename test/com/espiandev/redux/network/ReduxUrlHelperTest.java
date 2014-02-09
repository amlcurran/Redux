package com.espiandev.redux.network;

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

    @Test
    public void testUrlHelper_FormatsSearchUrlCorrectly() {
        ReduxUrlHelper helper = new ReduxUrlHelper();
        String url = helper.buildSearchUrl("search Query", "authToken");

        assertEquals("https://i.bbcredux.com/asset/search?q=search%20Query&token=authToken", url);
    }

    @Test
    public void testUrlHelper_FormatsImageUrlCorrectly() {
        ReduxUrlHelper helper = new ReduxUrlHelper();
        String url = helper.buildImageUrl("pinguuuid", "pinguKey");

        assertEquals("https://i.bbcredux.com/asset/media/pinguuuid/pinguKey/JPEG-1280x/image.jpg", url);
    }

}
