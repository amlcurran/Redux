package com.espiandev.redux;

import android.view.View;
import android.widget.TextView;

import com.android.volley.RequestQueue;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = "AndroidManifest.xml")
public class LoginActivityTest {

    private LoginActivity activity;
    @Mock
    private RequestQueue mockRequestQueue;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        activity = Robolectric.buildActivity(LoginActivity.class).create().get();
        MockVolleyHelper mockVolleyHelper = new MockVolleyHelper(mockRequestQueue);

        activity.animationFactory = new NonAnimationFactory();
        activity.volleyHelper = mockVolleyHelper;
    }

    @Test
    @Ignore
    public void testWhenActivityStarted_UsernameFieldHasFocus() {
        assertEquals(activity.findViewById(R.id.login_username), activity.getCurrentFocus());
    }

    @Test
    public void testWhenActivityIsStarted_TheErrorViewIsHidden() {
        assertEquals(View.INVISIBLE, activity.findViewById(R.id.login_error).getVisibility());
    }

    @Test
    public void testWhenLoginClicked_AndUsernameIsEmpty_AUsernameErrorIsShown() {
        activity.findViewById(R.id.login_submit).performClick();

        String expectedString = activity.getString(R.string.login_error_username);
        String actualString = String.valueOf(((TextView) activity.findViewById(R.id.login_error))
                .getText());

        assertEquals(expectedString, actualString);
        assertEquals(View.VISIBLE, activity.findViewById(R.id.login_error).getVisibility());
    }

    @Test
    public void testWhenLoginClicked_AndPasswordIsEmpty_APasswordErrorIsShown() {
        ((TextView) activity.findViewById(R.id.login_username)).setText("username");

        activity.findViewById(R.id.login_submit).performClick();

        String expectedString = activity.getString(R.string.login_error_password);
        String actualString = String.valueOf(((TextView) activity.findViewById(R.id.login_error))
                .getText());

        assertEquals(expectedString, actualString);
        assertEquals(View.VISIBLE, activity.findViewById(R.id.login_error).getVisibility());
    }

    @Test
    public void testWhenLoginClicked_AndUsernameAndPasswordIsValid_TheErrorViewIsHidden() {
        // Show the error view
        ((TextView) activity.findViewById(R.id.login_username)).setText("username");
        activity.findViewById(R.id.login_submit).performClick();

        // Give a valid password to hide it
        ((TextView) activity.findViewById(R.id.login_password)).setText("password");
        activity.findViewById(R.id.login_submit).performClick();

        assertEquals(View.INVISIBLE, activity.findViewById(R.id.login_error).getVisibility());
    }

    @Test
    public void testWhenValidCredentialsSupplied_ARequestIsLaunchedForLogin() {



    }

}
