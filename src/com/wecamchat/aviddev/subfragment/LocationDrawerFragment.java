package com.wecamchat.aviddev.subfragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.wecamchat.aviddev.R;
import com.wecamchat.aviddev.activity.AvidFragmentBaseActivity;
import com.wecamchat.aviddev.fragment.AvidBaseFragment;
import com.wecamchat.aviddev.fragment.ProfileFragment;
import com.wecamchat.aviddev.util.view.ExpandableHeightListView;
import com.wecamchat.aviddev.viewadapter.LocationDrawerViewAdapter;

public class LocationDrawerFragment extends AvidBaseFragment {

    private View view;

    private ExpandableHeightListView listview_location_drawer;

    private TextView tv_location_drawer_rate_or_review;

    private boolean isRateOrReview;
    private LinearLayout ll_location_drawer_rate_review;

    private ScrollView rootScrollView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        ProfileFragment.setProfileFlag = true;
        view = inflater.inflate(R.layout.fragment_location_drawer_on_map,
                container, false);
        setUI();

        return view;

    }

    private void setUI() {
        tv_location_drawer_rate_or_review = (TextView) view
                .findViewById(R.id.tv_location_drawer_rate_or_review);
        ll_location_drawer_rate_review = (LinearLayout) view
                .findViewById(R.id.ll_location_drawer_rate_review);

        listview_location_drawer = (ExpandableHeightListView) view
                .findViewById(R.id.listview_location_drawer);

        rootScrollView = (ScrollView) view
                .findViewById(R.id.fragment_location_drawer_root_layout);

        setUIListener();
        setListViewData();
    }

    private void setListViewData() {
        listview_location_drawer.setAdapter(new LocationDrawerViewAdapter(
                (AvidFragmentBaseActivity) getActivity()));
    }

    private void setUIListener() {
        tv_location_drawer_rate_or_review
                .setOnClickListener((android.view.View.OnClickListener) this);

        listview_location_drawer.setOnTouchListener(new OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside
            // ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch
                // of child view
                v.getParent().requestDisallowInterceptTouchEvent(false);
                return false;
            }
        });

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
        case R.id.tv_location_drawer_rate_or_review:
            if (isRateOrReview) {
                isRateOrReview = false;
                tv_location_drawer_rate_or_review.setTextColor(Color
                        .parseColor("#45a4d3"));
                tv_location_drawer_rate_or_review.setText("Rate or Review");
                ll_location_drawer_rate_review.setVisibility(View.GONE);
            } else {

                tv_location_drawer_rate_or_review.setTextColor(Color
                        .parseColor("#221f1f"));
                tv_location_drawer_rate_or_review.setText("Rate This Location");

                ll_location_drawer_rate_review.setVisibility(View.VISIBLE);
                isRateOrReview = true;
            }

            break;

        default:
            break;
        }
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }
}
