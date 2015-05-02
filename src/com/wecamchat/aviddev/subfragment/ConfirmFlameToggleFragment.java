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
import com.wecamchat.aviddev.intrface.OnHotIconClick;

public class ConfirmFlameToggleFragment extends AvidBaseFragment {

    OnHotIconClick onHotIconClick;
    ImageView iv_confirm_flame_tog_no;
    ImageView iv_confirm_flame_tog_yes;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    private View view;

    public ConfirmFlameToggleFragment(OnHotIconClick onHotIconClick) {
        this.onHotIconClick = onHotIconClick;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_confirm_flame_toggle,
                container, false);
        initUi();
        registerListner();
        return view;
    }

    private void initUi() {

        iv_confirm_flame_tog_no = (ImageView) view
                .findViewById(R.id.iv_confirm_flame_tog_no);
        iv_confirm_flame_tog_yes = (ImageView) view
                .findViewById(R.id.iv_confirm_flame_tog_yes);
    }

    private void registerListner() {

        iv_confirm_flame_tog_no.setOnClickListener(this);
        iv_confirm_flame_tog_yes.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.iv_confirm_flame_tog_no:

            onHotIconClick.isCrossClicked();

            break;

        case R.id.iv_confirm_flame_tog_yes:

            onHotIconClick.isTickClicked();

            break;
        }
        removeFragmentWithAnimation(AppConstant.FragmentStackName.CONFIRM_FLAME_TOGGLE_FRAGMENT);

    }

    @Override
    public boolean onBackPressed() {
        return false;
    }
}
