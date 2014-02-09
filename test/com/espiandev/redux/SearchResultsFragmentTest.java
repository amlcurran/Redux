package com.espiandev.redux;

import com.espiandev.redux.animation.AnimationFactory;
import com.espiandev.redux.auth.TokenStorage;
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

import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = "AndroidManifest.xml")
public class SearchResultsFragmentTest {

    public static final String QUERY_STRING = "queryString";
    private FragmentTestingActivity activity;
    private SearchResultsFragment loginFragment;
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
        loginFragment = SearchResultsFragment.newInstance(QUERY_STRING, null);
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
    public void testTheQuery_IsSetAsTheFragmentsTitle() {
        assertTrue(activity.getTitleHostTitle().toString().contains(QUERY_STRING));
    }

}
