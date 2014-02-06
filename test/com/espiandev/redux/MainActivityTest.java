package com.espiandev.redux;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.widget.TextView;

import com.espiandev.redux.animation.AnimationFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowIntent;
import org.robolectric.util.ActivityController;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.robolectric.Robolectric.shadowOf;

/**
 * Created by Alex Curran on 06/02/2014.
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = "AndroidManifest.xml")
public class MainActivityTest {

    private MainActivity launchActivity;
    private ActivityController<MainActivity> launchActivityController;
    @Mock
    public AnimationFactory mockAnimationFactory;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        launchActivityController = Robolectric.buildActivity(MainActivity.class);
        launchActivity = launchActivityController.get();
    }

    @Test
    public void testSetTitle_SetsTheHighBanner() {
        String title = "A TITLE";
        launchActivityController.create();
        launchActivity.animationFactory = mockAnimationFactory;
        launchActivity.setTitle(title);
        verify(mockAnimationFactory).fadeAndChangeText((TextView) launchActivity.findViewById(R.id.high_banner), title);
    }

    @Test
    public void testSetSubtitle_SetsTheLowBanner() {
        String subtitle = "A SUBTITLE";
        launchActivityController.create();
        launchActivity.animationFactory = mockAnimationFactory;
        launchActivity.setSubtitle(subtitle);
        verify(mockAnimationFactory).fadeAndChangeText((TextView) launchActivity.findViewById(R.id.low_banner), subtitle);
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
