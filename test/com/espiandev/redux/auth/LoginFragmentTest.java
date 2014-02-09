package com.espiandev.redux.auth;

import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.espiandev.redux.FragmentTestingActivity;
import com.espiandev.redux.R;
import com.espiandev.redux.ReduxUrlHelper;
import com.espiandev.redux.auth.LoginFragment;
import com.espiandev.redux.auth.TokenStorage;
import com.espiandev.redux.testutils.MockVolleyHelper;
import com.espiandev.redux.testutils.NonAnimationFactory;

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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = "AndroidManifest.xml")
public class LoginFragmentTest {

    private FragmentTestingActivity activity;
    private LoginFragment loginFragment;
    @Mock
    private RequestQueue mockRequestQueue;
    @Mock
    private TokenStorage mockTokenStorage;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        MockVolleyHelper mockVolleyHelper = new MockVolleyHelper(mockRequestQueue);

        ActivityController<FragmentTestingActivity> controller = Robolectric.buildActivity(FragmentTestingActivity.class);
        activity = controller.get();
        loginFragment = new LoginFragment();
        activity.animationFactory = new NonAnimationFactory();
        activity.tokenStorage = mockTokenStorage;
        activity.setFragment(loginFragment);
        activity.volleyHelper = mockVolleyHelper;
        controller.create();

    }

    @Test
    public void testWhenLoginClicked_AndUsernameIsEmpty_AUsernameErrorIsShown() {
        clickSubmitButton();

        String expectedString = activity.getString(R.string.login_error_username);
        String actualString = String.valueOf(activity.getTitleHostSubtitle());

        assertEquals(expectedString, actualString);
    }

    @Test
    public void testWhenLoginClicked_AndPasswordIsEmpty_APasswordErrorIsShown() {
        ((TextView) activity.findViewById(R.id.login_username)).setText("username");

        clickSubmitButton();

        String expectedString = activity.getString(R.string.login_error_password);
        String actualString = String.valueOf(activity.getTitleHostSubtitle());

        assertEquals(expectedString, actualString);
    }

    private void assertNoSubtitle() {
        assertNull(activity.getTitleHostSubtitle());
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

        assertNoSubtitle();
    }

    @Test
    public void testWhenLogInResponseReturns_TheLoadingSpinnerIsAnimatedOut() {
        setUpValidCredentials();
        clickSubmitButton();
        loginFragment.onResponse("blah");

        assertEquals(View.INVISIBLE, activity.findViewById(R.id.login_spinner).getVisibility());
    }

    @Test
    public void testWhenLogInResponseReturns_TheTokenIsStored() {
        setUpValidCredentials();
        clickSubmitButton();
        String response = "blah";

        loginFragment.onResponse(response);

        verify(mockTokenStorage).storeToken(response);
    }

    @Test
    public void testWhenLogInErrorResponseReturns_TheCredentialsAreAnimatedBackIn() {
        setUpValidCredentials();
        clickSubmitButton();
        loginFragment.onErrorResponse(null);

        assertEquals(View.VISIBLE, activity.findViewById(R.id.login_credentials_host).getVisibility());
    }

    @Test
    public void testWhenLogInErrorResponseReturnsDueToAuth_ThisIsShownToTheUser() {
        setUpValidCredentials();
        clickSubmitButton();
        loginFragment.onErrorResponse(new AuthFailureError());

        String expected = activity.getString(R.string.volley_error_auth);

        assertEquals(expected, activity.getTitleHostSubtitle());
    }

    private void setUpValidCredentials() {
        ((TextView) activity.findViewById(R.id.login_username)).setText("username");
        ((TextView) activity.findViewById(R.id.login_password)).setText("password");
    }

    private void setUpInvalidCredentials() {
        ((TextView) activity.findViewById(R.id.login_username)).setText("username");
    }

}
