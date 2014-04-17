package com.espiandev.redux.network;

import com.espiandev.redux.testing.TestAsset;

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
        String url = helper.buildSearchUrl("search Query", "authToken", 0);

        assertEquals("https://i.bbcredux.com/asset/search?q=search%20Query&token=authToken&limit=20", url);
    }

    @Test
    public void testUrlHelper_FormatsImageUrlCorrectly() {
        ReduxUrlHelper helper = new ReduxUrlHelper();
        String url = helper.buildImageUrl("pinguuuid", "pinguKey");

        assertEquals("https://i.bbcredux.com/asset/media/pinguuuid/pinguKey/JPEG-1280x/image.jpg", url);
    }

    @Test
    public void testUrlHelper_FormatsDownloadUrlCorrectly() {
        ReduxUrlHelper helper = new ReduxUrlHelper();

        String url = helper.buildDownloadUrl(new TestAsset());

        assertEquals("https://i.bbcredux.com/asset/media/" +
                TestAsset.UUID + "/" +
                TestAsset.KEY +
                "/h264_mp4_hi_v1.1/" +
                TestAsset.NAME +
                ".mp4", url);
    }

}
