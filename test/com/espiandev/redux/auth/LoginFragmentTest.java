package com.espiandev.redux.auth;

import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.espiandev.redux.FragmentTestingActivity;
import com.espiandev.redux.R;
import com.espiandev.redux.network.NetworkHelper;
import com.espiandev.redux.testutils.NonAnimationFactory;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = "AndroidManifest.xml")
public class LoginFragmentTest {

    public static final String VALID_USERNAME = "username";
    public static final String VALID_PASSWORD = "password";
    private FragmentTestingActivity activity;
    private LoginFragment loginFragment;
    @Mock
    private TokenStorage mockTokenStorage;
    @Mock
    private NetworkHelper mockNetworkHelper;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        ActivityController<FragmentTestingActivity> controller = Robolectric.buildActivity(FragmentTestingActivity.class);
        activity = controller.get();
        loginFragment = new LoginFragment();
        activity.animationFactory = new NonAnimationFactory();
        activity.tokenStorage = mockTokenStorage;
        activity.networkHelper = mockNetworkHelper;
        activity.setFragment(loginFragment);
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

        verify(mockNetworkHelper).login(VALID_USERNAME, VALID_PASSWORD, loginFragment);
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
        loginFragment.onSuccessResponse("blah");

        assertEquals(View.INVISIBLE, activity.findViewById(R.id.login_spinner).getVisibility());
    }

    @Test
    public void testWhenLogInResponseReturns_TheTokenIsStored() {
        setUpValidCredentials();
        clickSubmitButton();
        String response = "blah";

        loginFragment.onSuccessResponse(response);

        verify(mockTokenStorage).storeToken(response);
    }

    @Test
    public void testWhenTheTokenIsStored_TheLogInListenerIsNotified() {
        setUpValidCredentials();
        clickSubmitButton();
        String response = "blah";

        when(mockTokenStorage.storeToken(any(String.class))).thenReturn(true);
        loginFragment.onSuccessResponse(response);

        assertTrue("LoginListener wasn't notified of login", activity.onLoginCalled);
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
        ((TextView) activity.findViewById(R.id.login_username)).setText(VALID_USERNAME);
        ((TextView) activity.findViewById(R.id.login_password)).setText(VALID_PASSWORD);
    }

    private void setUpInvalidCredentials() {
        ((TextView) activity.findViewById(R.id.login_username)).setText("username");
    }

}
