package com.espiandev.redux.navigation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.espiandev.redux.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = "AndroidManifest.xml")
public class FragmentManagerStackerTest {

    private FragmentManagerStackerActivity activity;
    private FragmentManagerStacker stacker;

    @Before
    public void setUp() {
        ActivityController<FragmentManagerStackerActivity> activityController = Robolectric.buildActivity(FragmentManagerStackerActivity.class);
        activity = activityController.create().get();
        stacker = new FragmentManagerStacker(activity);
    }

    @Test
    public void testPushFragment_SetsAFragmentOnTheFrame() {
        Fragment fragment = new Fragment();

        stacker.pushFragment(fragment);

        assertEquals(fragment, getPoppedFragment());
    }

    @Test
    public void testAddFragment_SetsAFragmentOnTheFrame() {
        Fragment fragment = new Fragment();

        stacker.addFragment(fragment);

        assertEquals(fragment, getPoppedFragment());
    }

    @Test
    public void testRemoveFragment_RemovesAFragmentOnTheFrame() {
        Fragment fragment = new Fragment();

        stacker.addFragment(fragment);
        stacker.removeFragment();

        assertNull(getPoppedFragment());
    }

    private Fragment getPoppedFragment() {
        return activity.getSupportFragmentManager().findFragmentById(R.id.host_frame);
    }

    private static class FragmentManagerStackerActivity extends FragmentActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
        }
    }

}
