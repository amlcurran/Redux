package com.espiandev.redux.search;

import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.espiandev.redux.R;
import com.espiandev.redux.testing.BaseFragmentTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
    public void testAValidSearch_AnimatesOutTheQueryBarAndShowsSpinner() {
        setSearchQuery("query");
        clickSearchButton();

        assertTrue(activity.onSearchResultCalled);
    }

    @Test
    public void testPerformingAnActionOnTheEditText_StartsASearch() {
        setSearchQuery("query");
        ((EditText) activity.findViewById(R.id.search_query)).onEditorAction(EditorInfo.IME_ACTION_SEARCH);

        assertTrue(activity.onSearchResultCalled);
    }

    @Test
    public void testWhenActivityCreated_PreviousSearchResultsAreSetOnTheListView() {
        assertEquals(PreviousResultsAdapter.class, fragment.previousResultsList.getAdapter().getClass());
    }

    private void setSearchQuery(String query) {
        ((TextView) activity.findViewById(R.id.search_query)).setText(query);
    }

    private void clickSearchButton() {
        activity.findViewById(R.id.search_submit).performClick();
    }

}
