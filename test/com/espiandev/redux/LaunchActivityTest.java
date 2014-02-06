package com.espiandev.redux;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowIntent;
import org.robolectric.util.ActivityController;

import android.content.Intent;
import android.preference.PreferenceManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.robolectric.Robolectric.shadowOf;

/**
 * Created by Alex Curran on 06/02/2014.
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = "AndroidManifest.xml")
public class LaunchActivityTest {

    private LaunchActivity launchActivity;
    private ActivityController<LaunchActivity> launchActivityController;

    @Before
    public void setUp() {
        launchActivityController = Robolectric.buildActivity(LaunchActivity.class);
        launchActivity = launchActivityController.get();
    }

    @Test
    public void testIfNoAuthTokenIsStored_LogInActivityIsOpened() {
        launchActivityController.create();
        ShadowIntent intent = shadowOf(shadowOf(launchActivity).getNextStartedActivity());

        assertEquals(intent.getIntentClass(), LoginActivity.class);
    }

    @Test
    public void testIfAuthTokenIsStored_LogInActivityIsNotOpened() {
        PreferenceManager.getDefaultSharedPreferences(launchActivity).edit()
                .putString("auth_token", "token").apply();
        launchActivityController.create();
        Intent intent = shadowOf(launchActivity).getNextStartedActivity();

        assertNull(intent);
    }

    @After
    public void tearDown() {
        PreferenceManager.getDefaultSharedPreferences(launchActivity).edit()
                .remove("auth_token").apply();
    }

}
