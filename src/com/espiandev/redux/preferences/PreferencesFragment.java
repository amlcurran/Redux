package com.espiandev.redux.preferences;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.espiandev.redux.R;
import com.espiandev.redux.ResourceStringProvider;
import com.espiandev.redux.TitledItem;

public class PreferencesFragment extends PreferenceFragment implements TitledItem, ResourceStringProvider {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public CharSequence getHostedSubtitle(ResourceStringProvider stringProvider) {
        return null;
    }

    @Override
    public CharSequence getHostedTitle(ResourceStringProvider stringProvider) {
        return stringProvider.getString(R.string.preferences);
    }
}
