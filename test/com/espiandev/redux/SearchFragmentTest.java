package com.espiandev.redux;

import android.widget.TextView;

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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = "AndroidManifest.xml")
public class SearchFragmentTest {

    private FragmentTestingActivity activity;
    private SearchFragment searchFragment;
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
        searchFragment = new SearchFragment();
        activity.animationFactory = mockAnimationFactory;
        activity.tokenStorage = mockTokenStorage;
        activity.networkHelper = mockNetworkHelper;
        activity.setFragment(searchFragment);
        controller.create();

    }

    @Test
    public void testIfEmptySearchIsRequested_AnErrorIsShown() {
        clickSearchButton();

        String expectedString = activity.getString(R.string.error_empty_search);
        String actualString = String.valueOf(activity.getTitleHostSubtitle());

        assertEquals(expectedString, actualString);
    }

    @Test
    public void testAValidSearchLaunchesASearch() {
        setSearchQuery("query");
        clickSearchButton();

        verify(mockNetworkHelper).search("query", searchFragment);
    }

    @Test
    public void testAValidSearch_AnimatedOutTheQueryBarAndShowsSpinner() {
        setSearchQuery("query");
        clickSearchButton();

        verify(mockAnimationFactory).upAndOut(activity.findViewById(R.id.search_query_host));
        verify(mockAnimationFactory).upAndIn(activity.findViewById(R.id.spinner));
    }

    @Test
    public void testASearchResponse_NotifiesTheListenerOfAScucessfulSearch() {
        searchFragment.onSuccessResponse("{ 'successResponse' : 'true' }");

        assertTrue("onSearchResult wasn't called", activity.onSearchResultCalled);
    }

    private void setSearchQuery(String query) {
        ((TextView) activity.findViewById(R.id.search_query)).setText(query);
    }

    private void clickSearchButton() {
        activity.findViewById(R.id.search_submit).performClick();
    }

}
