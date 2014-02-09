package com.espiandev.redux.auth;

import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.espiandev.redux.FragmentTestingActivity;
import com.espiandev.redux.R;
import com.espiandev.redux.animation.AnimationFactory;
import com.espiandev.redux.network.NetworkHelper;

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
    public static final String TOKEN = "blah";
    public static final String VALID_TOKEN_RESPONSE = String.format("{ 'token' : '%s' }", TOKEN);
    private FragmentTestingActivity activity;
    private LoginFragment loginFragment;
    @Mock
    private TokenStorage mockTokenStorage;
    @Mock
    private NetworkHelper mockNetworkHelper;
    @Mock
    private AnimationFactory mockAnimationFactory;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        ActivityController<FragmentTestingActivity> controller = Robolectric.buildActivity(FragmentTestingActivity.class);
        activity = controller.get();
        loginFragment = new LoginFragment();
        attachMocks();
        activity.setFragment(loginFragment);
        controller.create();

    }

    private void attachMocks() {
        activity.animationFactory = mockAnimationFactory;
        activity.tokenStorage = mockTokenStorage;
        activity.networkHelper = mockNetworkHelper;
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

        verifyUpAndOut(R.id.login_credentials_host);
        verifyUpAndIn(R.id.spinner);
    }

    private void verifyUpAndIn(int spinner) {
        verify(mockAnimationFactory).upAndIn(activity.findViewById(spinner));
    }

    private void verifyUpAndOut(int viewId) {
        verify(mockAnimationFactory).upAndOut(activity.findViewById(viewId));
    }

    private void verifyDownAndIn(int viewId) {
        verify(mockAnimationFactory).downAndIn(activity.findViewById(viewId));
    }

    private void verifyDownAndOut(int viewId) {
        verify(mockAnimationFactory).downAndOut(activity.findViewById(viewId));
    }

    @Test
    public void testWhenLogInIsSubmitted_TheErrorViewIsHidden() {
        setUpValidCredentials();
        clickSubmitButton();

        assertNoSubtitle();
    }

    @Test
    public void testWhenLogInResponseReturns_TheLoadingSpinnerIsAnimatedOut() {
        when(mockTokenStorage.storeToken(any(String.class))).thenReturn(true);

        loginFragment.onSuccessResponse(VALID_TOKEN_RESPONSE);

        verifyDownAndOut(R.id.spinner);
    }

    @Test
    public void testWhenLogInResponseReturns_TheTokenIsStored() {
        loginFragment.onSuccessResponse(VALID_TOKEN_RESPONSE);

        verify(mockTokenStorage).storeToken(TOKEN);
    }

    @Test
    public void testWhenTheTokenIsStored_TheLogInListenerIsNotified() {
        setUpValidCredentials();
        clickSubmitButton();

        when(mockTokenStorage.storeToken(any(String.class))).thenReturn(true);
        loginFragment.onSuccessResponse(VALID_TOKEN_RESPONSE);

        assertTrue("LoginListener wasn't notified of login", activity.onLoginCalled);
    }

    @Test
    public void testWhenLogInErrorResponseReturns_TheCredentialsAreAnimatedBackIn() {
        setUpValidCredentials();
        clickSubmitButton();
        loginFragment.onErrorResponse(null);

        verifyDownAndIn(R.id.login_credentials_host);
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
