package com.espiandev.redux;

import android.widget.ListView;

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

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

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
    private ArrayList<Asset> assetArray;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        ActivityController<FragmentTestingActivity> controller = Robolectric.buildActivity(FragmentTestingActivity.class);
        activity = controller.get();
        assetArray = createAssetArray();
        loginFragment = SearchResultsFragment.newInstance(QUERY_STRING, assetArray);
        attachMocks();
        activity.setFragment(loginFragment);
        controller.create();

    }

    private ArrayList<Asset> createAssetArray() {
        ArrayList<Asset> list = new ArrayList<Asset>();
        list.add(mock(Asset.class));
        list.add(mock(Asset.class));
        return list;
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

    @Test
    public void testTheResultsArrayPassedIn_IsSetOnTheAdapter() {
        assertEquals(assetArray.get(0), loginFragment.adapter.getItem(0));
    }

    @Test
    public void testTheAdapterIsSetOnTheListView() {
        assertEquals(loginFragment.adapter, ((ListView) activity.findViewById(R.id.results_list)).getAdapter());
    }

}
