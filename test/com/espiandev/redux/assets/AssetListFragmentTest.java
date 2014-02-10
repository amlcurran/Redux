package com.espiandev.redux.assets;

import com.espiandev.redux.R;
import com.espiandev.redux.testing.BaseFragmentTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import android.widget.ListView;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = "AndroidManifest.xml")
public class AssetListFragmentTest extends BaseFragmentTest<AssetListFragment> {

    public static final String QUERY_STRING = "queryString";
    private ArrayList<Asset> assetArray;

    @Override
    protected void createFragment() {
        assetArray = createAssetArray();
        fragment = AssetListFragment.newInstance(QUERY_STRING, assetArray);
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
    public void testTheResultsArrayPassedIn_IsSetOnTheAdapter() {
        assertEquals(assetArray.get(0), fragment.adapter.getItem(0));
    }

    @Test
    public void testTheAdapterIsSetOnTheListView() {
        assertEquals(fragment.adapter, ((ListView) activity.findViewById(R.id.results_list)).getAdapter());
    }

    @Test
    public void testWhenAnItemIsClicked_TheListenerIsNotifiedOfTheSelection() {
        ListView listView = (ListView) activity.findViewById(R.id.results_list);

        listView.performItemClick(fragment.adapter.getView(1, null, listView), 1, 1);

        assertEquals(assetArray.get(1), activity.selectedAsset);
    }

}
