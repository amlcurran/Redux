package com.espiandev.redux;

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

        assertEquals("i.bbcredux.com/user/login?username=USERNAME&password=PASSWORD", url);
    }

}
