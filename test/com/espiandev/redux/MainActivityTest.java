package com.espiandev.redux;

import android.app.Fragment;
import android.preference.PreferenceManager;
import android.widget.TextView;

import com.espiandev.redux.animation.AnimationFactory;
import com.espiandev.redux.auth.LoginFragment;
import com.espiandev.redux.auth.TokenStorage;
import com.espiandev.redux.network.NetworkHelper;
import com.espiandev.redux.search.SearchFragment;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import static org.junit.Assert.assertEquals;
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
    public void testIfNoAuthTokenIsStored_ThenLogInIsStacked() {
        when(mockTokenStorage.hasToken()).thenReturn(false);
        ArgumentCaptor<Fragment> captor = ArgumentCaptor.forClass(Fragment.class);

        launchActivityController.create();

        verify(mockStacker).addFragment(captor.capture());
        assertEquals(LoginFragment.class, captor.getValue().getClass());
    }

    @Test
    public void testIfAuthTokenIsStored_ThenSearchIsStacked() {
        when(mockTokenStorage.hasToken()).thenReturn(true);
        ArgumentCaptor<Fragment> captor = ArgumentCaptor.forClass(Fragment.class);

        launchActivityController.create();

        verify(mockStacker).addFragment(captor.capture());
        assertEquals(SearchFragment.class, captor.getValue().getClass());
    }

    @Test
    public void testWhenLoggedIn_TheLogInFragmentIsRemoved() {
        when(mockTokenStorage.hasToken()).thenReturn(false);

        launchActivityController.create();
        launchActivity.onLogin();

        verify(mockStacker).removeFragment();
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
