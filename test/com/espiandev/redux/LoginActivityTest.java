package com.espiandev.redux;

import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;

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
        clickSubmitButton();

        String expectedString = activity.getString(R.string.login_error_username);
        String actualString = String.valueOf(((TextView) activity.findViewById(R.id.login_error))
                .getText());

        assertEquals(expectedString, actualString);
        assertEquals(View.VISIBLE, activity.findViewById(R.id.login_error).getVisibility());
    }

    @Test
    public void testWhenLoginClicked_AndPasswordIsEmpty_APasswordErrorIsShown() {
        ((TextView) activity.findViewById(R.id.login_username)).setText("username");

        clickSubmitButton();

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
        clickSubmitButton();

        // Give a valid password to hide it
        ((TextView) activity.findViewById(R.id.login_password)).setText("password");
        clickSubmitButton();

        assertEquals(View.INVISIBLE, activity.findViewById(R.id.login_error).getVisibility());
    }

    private boolean clickSubmitButton() {
        return activity.findViewById(R.id.login_submit).performClick();
    }

    @Test
    public void testWhenValidCredentialsSupplied_ARequestIsLaunchedForLogin() {
        setUpValidCredentials();
        clickSubmitButton();

        ArgumentCaptor<Request> requestArgumentCaptor = ArgumentCaptor.forClass(Request.class);
        verify(mockRequestQueue).add(requestArgumentCaptor.capture());
        assertTrue(requestArgumentCaptor.getValue().getUrl().contains(ReduxUrlHelper.PATH_LOGIN));
    }

    @Test
    public void testWhenLogInIsSubmitted_TheLoadingSpinnerIsAnimatedIn() {
        setUpValidCredentials();
        clickSubmitButton();

        assertEquals(View.INVISIBLE, activity.findViewById(R.id.login_credentials_host).getVisibility());
        assertEquals(View.VISIBLE, activity.findViewById(R.id.login_spinner).getVisibility());
    }

    @Test
    public void testWhenLogInIsSubmitted_TheErrorViewIsHidden() {
        setUpValidCredentials();
        clickSubmitButton();

        assertEquals(View.INVISIBLE, activity.findViewById(R.id.login_error).getVisibility());
    }

    @Test
    public void testWhenLogInResponseReturns_TheLoadingSpinnerIsAnimatedOut() {
        setUpValidCredentials();
        clickSubmitButton();
        activity.onResponse("blah");

        assertEquals(View.INVISIBLE, activity.findViewById(R.id.login_spinner).getVisibility());
    }

    @Test
    public void testWhenLogInErrorResponseReturns_TheCredentialsAreAnimatedBackIn() {
        setUpValidCredentials();
        clickSubmitButton();
        activity.onErrorResponse(null);

        assertEquals(View.VISIBLE, activity.findViewById(R.id.login_credentials_host).getVisibility());
    }

    @Test
     public void testWhenLogInErrorResponseReturnsDueToAuth_ThisIsShownToTheUser() {
        setUpValidCredentials();
        clickSubmitButton();
        activity.onErrorResponse(new AuthFailureError());

        String expected = activity.getString(R.string.login_error_auth);

        assertEquals(View.VISIBLE, activity.findViewById(R.id.login_error).getVisibility());
        assertEquals(expected, ((TextView) activity.findViewById(R.id.login_error)).getText());
    }

    private void setUpValidCredentials() {
        ((TextView) activity.findViewById(R.id.login_username)).setText("username");
        ((TextView) activity.findViewById(R.id.login_password)).setText("password");
    }

    private void setUpInvalidCredentials() {
        ((TextView) activity.findViewById(R.id.login_username)).setText("username");
    }

}
