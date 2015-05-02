package com.wecamchat.aviddev.viewadapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.wecamchat.aviddev.R;
import com.wecamchat.aviddev.activity.AvidFragmentBaseActivity;
import com.wecamchat.aviddev.util.view.CustomTextView;

public class LocationDrawerViewAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    AvidFragmentBaseActivity activity;

    public LocationDrawerViewAdapter(AvidFragmentBaseActivity activity) {

        this.activity = activity;

        inflater = LayoutInflater.from(activity);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object getItem(int i) {
        return 0;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {

            convertView = inflater.inflate(
                    R.layout.layout_custome_location_drawer, null);

        }

        return convertView;

    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    public class ViewHolder {

        public CustomTextView tv_custom_browser_distance;

    }

}
