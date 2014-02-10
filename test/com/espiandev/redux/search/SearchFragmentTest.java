package com.espiandev.redux.search;

import android.widget.TextView;

import com.espiandev.redux.R;
import com.espiandev.redux.testing.BaseFragmentTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = "AndroidManifest.xml")
public class SearchFragmentTest extends BaseFragmentTest<SearchFragment> {

    @Override
    protected void createFragment() {
        fragment = new SearchFragment();
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

        verify(mockNetworkHelper).search("query", fragment, 0);
    }

    @Test
    public void testAValidSearch_AnimatesOutTheQueryBarAndShowsSpinner() {
        setSearchQuery("query");
        clickSearchButton();

        verify(mockAnimationFactory).upAndOut(activity.findViewById(R.id.search_query_host));
        verify(mockAnimationFactory).upAndIn(activity.findViewById(R.id.spinner));
    }

    @Test
    public void testASearchResponse_NotifiesTheListenerOfASuccessfulSearch() {
        fragment.onSuccessResponse("{ 'successResponse' : 'true' }");

        assertTrue("onSearchResult wasn't called", activity.onSearchResultCalled);
    }

    private void setSearchQuery(String query) {
        ((TextView) activity.findViewById(R.id.search_query)).setText(query);
    }

    private void clickSearchButton() {
        activity.findViewById(R.id.search_submit).performClick();
    }

}
