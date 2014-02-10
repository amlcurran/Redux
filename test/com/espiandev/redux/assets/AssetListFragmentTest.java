package com.espiandev.redux.assets;

import android.widget.ListView;

import com.espiandev.redux.R;
import com.espiandev.redux.testing.BaseFragmentTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = "AndroidManifest.xml")
public class AssetListFragmentTest extends BaseFragmentTest<AssetListFragment> {

    public static final String QUERY_STRING = "queryString";

    @Override
    protected void createFragment() {
        fragment = AssetListFragment.newInstance(QUERY_STRING);
    }

    private ArrayList<Asset> createAssetArray() {
        ArrayList<Asset> list = new ArrayList<Asset>();
        list.add(mock(Asset.class));
        list.add(mock(Asset.class));
        return list;
    }

    @Test
    public void testTheQuery_IsSetAsTheFragmentsTitle() {
        assertTrue(activity.getTitleHostSubtitle().toString().contains(QUERY_STRING));
    }

    @Test
    public void testTheAdapterIsSetOnTheListView() {
        assertEquals(fragment.adapter, ((ListView) activity.findViewById(R.id.results_list)).getAdapter());
    }

    @Test
    public void testASearchRequestIsStarted() {
        verify(mockNetworkHelper).search(QUERY_STRING, fragment, 0);
    }

    @Test
    public void testWhenTheSearchRequestIsStarted_TheLoadingViewIsFadedIn_AndListViewOut() {
        verify(mockAnimationFactory).upAndOut(activity.findViewById(R.id.results_list));
        verify(mockAnimationFactory).upAndIn(activity.findViewById(R.id.spinner));
    }

    @Test
    public void testWhenTheNetworkResponseReturns_TheReturnedListIsSetOnTheAdapter() {
        String response = "{ 'json' : 'result' }";
        ArrayList<Asset> assetArray = createAssetArray();
        when(mockListParser.parseResultList(response)).thenReturn(assetArray);

        fragment.onSuccessResponse(response);

        assertEquals(assetArray.get(1), fragment.adapter.getItem(1));
    }

    @Test
    public void testWhenTheNetworkResponseReturns_TheSpinnerIsFadedOutAndListViewBackIn() {
        String response = "{ 'json' : 'result' }";
        ArrayList<Asset> assetArray = createAssetArray();
        when(mockListParser.parseResultList(response)).thenReturn(assetArray);

        fragment.onSuccessResponse(response);

        verify(mockAnimationFactory).downAndIn(activity.findViewById(R.id.results_list));
        verify(mockAnimationFactory).downAndOut(activity.findViewById(R.id.spinner));
    }

    @Test
    public void testWhenAnItemIsClicked_TheListenerIsNotifiedOfTheSelection() {
        ListView listView = (ListView) activity.findViewById(R.id.results_list);

        ArrayList<Asset> assetArray;
        fragment.adapter.addAll(assetArray = createAssetArray());
        listView.performItemClick(fragment.adapter.getView(1, null, listView), 1, 1);

        assertEquals(assetArray.get(1), activity.selectedAsset);
    }

}
