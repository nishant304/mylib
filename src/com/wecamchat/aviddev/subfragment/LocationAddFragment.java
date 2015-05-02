package com.wecamchat.aviddev.subfragment;

import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.gms.maps.model.Marker;
import com.wecamchat.aviddev.R;
import com.wecamchat.aviddev.fragment.AvidBaseFragment;
import com.wecamchat.aviddev.intrface.OnSwipeDownMapBrowerDrawerListener;
import com.wecamchat.aviddev.util.view.CustomEditText;

public class LocationAddFragment extends AvidBaseFragment {

    private View view;
    private String addresses;
    private CustomEditText et_add_location_address;
    private ImageView iv_add_location_cross_btn_ic;
    Marker marker;
    OnSwipeDownMapBrowerDrawerListener listener;
    private ImageView iv_add_location_add_btn_ic;

    public LocationAddFragment(String addresses, Marker marker,
            OnSwipeDownMapBrowerDrawerListener listener) {
        this.addresses = addresses;
        this.marker = marker;
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.layout_add_location, container, false);

        setUI();

        return view;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onViewCreated(view, savedInstanceState);
        setUIListener();

        et_add_location_address.setText(addresses);

    }

    private void setUI() {
        // TODO Auto-generated method stub
        et_add_location_address = (CustomEditText) view
                .findViewById(R.id.et_add_location_address);
        iv_add_location_cross_btn_ic = (ImageView) view
                .findViewById(R.id.iv_add_location_cross_btn_ic);
        iv_add_location_add_btn_ic = (ImageView) view
                .findViewById(R.id.iv_add_location_add_btn_ic);
    }

    private void setUIListener() {
        // TODO Auto-generated method stub
        iv_add_location_cross_btn_ic
                .setOnClickListener((android.view.View.OnClickListener) this);
        iv_add_location_add_btn_ic
                .setOnClickListener((android.view.View.OnClickListener) this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
        case R.id.iv_add_location_cross_btn_ic:
            marker.remove();

        case R.id.iv_add_location_add_btn_ic:

            break;

        }
        listener.onSwipeDownMapBrowerDrawer();
        if (getFragmentManager() != null)
            getFragmentManager().popBackStack("M",
                    FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }
}
