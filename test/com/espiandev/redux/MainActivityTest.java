package com.espiandev.redux;

import com.espiandev.redux.animation.AnimationFactory;
import com.espiandev.redux.auth.TokenStorage;
import com.espiandev.redux.network.NetworkHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import android.preference.PreferenceManager;
import android.widget.TextView;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Alex Curran on 06/02/2014.
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = "AndroidManifest.xml")
public class MainActivityTest {

    private MainActivity launchActivity;
    private ActivityController<? extends MainActivity> launchActivityController;
    @Mock
    private static TokenStorage mockTokenStorage;
    @Mock
    public static AnimationFactory mockAnimationFactory;
    @Mock
    public static Stacker mockStacker;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        launchActivityController = Robolectric.buildActivity(MockedMainActivity.class);
        launchActivity = launchActivityController.get();
        launchActivity.animationFactory = mockAnimationFactory;
        launchActivity.stacker = mock(Stacker.class);
    }

    @Test
    public void testSetTitle_SetsTheHighBanner() {
        String title = "A TITLE";
        launchActivityController.create();
        launchActivity.setTitle(title);
        verify(mockAnimationFactory).fadeAndChangeText((TextView) launchActivity.findViewById(R.id.high_banner), title);
    }

    @Test
    public void testSetSubtitle_SetsTheLowBanner() {
        String subtitle = "A SUBTITLE";
        launchActivityController.create();
        launchActivity.setSubtitle(subtitle);
        verify(mockAnimationFactory).fadeAndChangeText((TextView) launchActivity.findViewById(R.id.low_banner), subtitle);
    }

    @Test
    public void testIfNoAuthTokenIsStored_LogInActivityIsOpened() {
        when(mockTokenStorage.hasToken()).thenReturn(false);
        launchActivityController.create();
        assertTrue(false);
    }

    @Test
    public void testIfAuthTokenIsStored_LogInActivityIsNotOpened() {
        when(mockTokenStorage.hasToken()).thenReturn(false);
        launchActivityController.create();
        assertTrue(false);
    }

    @After
    public void tearDown() {
        PreferenceManager.getDefaultSharedPreferences(launchActivity).edit()
                .remove("auth_token").apply();
    }

    public static class MockedMainActivity extends MainActivity {

        @Override
        protected void createWorld() {
            this.networkHelper = mock(NetworkHelper.class);
            this.animationFactory = mockAnimationFactory;
            this.tokenStorage = mockTokenStorage;
            this.stacker = mockStacker;
        }
    }

}
