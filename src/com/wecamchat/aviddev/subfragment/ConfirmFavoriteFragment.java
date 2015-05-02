package com.wecamchat.aviddev.subfragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wecamchat.aviddev.R;
import com.wecamchat.aviddev.constant.AppConstant;
import com.wecamchat.aviddev.fragment.AvidBaseFragment;

public class ConfirmFavoriteFragment extends AvidBaseFragment {

    private View view;
    ImageView iv_confirm_fav_yes;
    ImageView iv_confirm_fav_no;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_confirm_favorite, container,
                false);
        initUi();
        registerListner();
        return view;
    }

    private void initUi() {
        iv_confirm_fav_yes = (ImageView) view
                .findViewById(R.id.iv_confirm_fav_yes);
        iv_confirm_fav_no = (ImageView) view
                .findViewById(R.id.iv_confirm_fav_no);

    }

    private void registerListner() {
        iv_confirm_fav_yes.setOnClickListener(this);
        iv_confirm_fav_no.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.iv_confirm_fav_no:

            break;

        case R.id.iv_confirm_fav_yes:

            break;
        }
        removeFragmentWithAnimation(AppConstant.FragmentStackName.CONFIRM_FAVORITE_FRAGMENT);
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }
}
