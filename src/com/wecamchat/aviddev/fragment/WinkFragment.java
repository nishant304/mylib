package com.wecamchat.aviddev.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wecamchat.aviddev.R;

public class WinkFragment extends AvidBaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        try {
            return inflater.inflate(R.layout.fragment_wink, container, false);

        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

}
