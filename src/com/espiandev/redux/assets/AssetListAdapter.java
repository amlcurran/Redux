package com.espiandev.redux.assets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.espiandev.redux.R;

import java.util.List;

public class AssetListAdapter extends ArrayAdapter<Asset> {

    public AssetListAdapter(Context context, List<Asset> objects) {
        super(context, R.layout.item_result, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_result, parent, false);
        }

        ((TextView) convertView.findViewById(R.id.asset_name)).setText(getItem(position).getName());
        ((TextView) convertView.findViewById(R.id.asset_description)).setText(getItem(position).getDescription());

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
