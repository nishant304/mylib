package com.wecamchat.aviddev.subfragment;

import android.app.FragmentManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.wecamchat.aviddev.R;
import com.wecamchat.aviddev.constant.AppConstant;
import com.wecamchat.aviddev.fragment.AvidBaseFragment;
import com.wecamchat.aviddev.intrface.OnDeleteImageClick;

public class ConfirmImageDeleteFragment extends AvidBaseFragment {

    View view;
    ImageView iv_confirm_delete_no;
    ImageView iv_confirm_delete_yes;

    private LinearLayout tranculentLayout;

    private RelativeLayout rl_root_image_delete;

    OnDeleteImageClick onDeleteImageClick;
    private RelativeLayout rl_image_delete_bottom_layout;
    private Animation fadeInAnimation;
    private Animation fadeoutAnimation;

    public ConfirmImageDeleteFragment(OnDeleteImageClick onDeleteImageClick) {

        this.onDeleteImageClick = onDeleteImageClick;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_confirm_delete, container,
                false);
        loadAnimations();
        initUi();
        registerListner();
        return view;
    }

    private void initUi() {

        iv_confirm_delete_no = (ImageView) view
                .findViewById(R.id.iv_confirm_delete_no);
        iv_confirm_delete_yes = (ImageView) view
                .findViewById(R.id.iv_confirm_delete_yes);
        rl_root_image_delete = (RelativeLayout) view
                .findViewById(R.id.rl_root_image_delete);
        rl_image_delete_bottom_layout = (RelativeLayout) view
                .findViewById(R.id.rl_image_delete_bottom_layout);

        tranculentLayout = (LinearLayout) view
                .findViewById(R.id.delete_cnf_screen_tranculent_layout);

        setBackgroundColorWithDelay();

    }

    private void registerListner() {

        iv_confirm_delete_no.setOnClickListener(this);
        iv_confirm_delete_yes.setOnClickListener(this);
        rl_root_image_delete.setOnClickListener(this);
        rl_image_delete_bottom_layout.setOnClickListener(this);
    }

    @Override
    public void onClick(final View v) {

        switch (v.getId()) {

        case R.id.iv_confirm_delete_no:
            hideTranculentbg();
            removeFragmentWithDelayAnimation(AppConstant.FragmentStackName.CONFIRM_IMAGE_DELETE_FRAGMENT);
            onDeleteImageClick.isCrossClicked();

            break;

        case R.id.iv_confirm_delete_yes:
            hideTranculentbg();
            removeFragmentWithDelayAnimation(AppConstant.FragmentStackName.CONFIRM_IMAGE_DELETE_FRAGMENT);
            removeImageWithDelay();
            break;

        case R.id.rl_root_image_delete:
            hideTranculentbg();
            removeFragmentWithDelayAnimation(AppConstant.FragmentStackName.CONFIRM_IMAGE_DELETE_FRAGMENT);
            break;
        case R.id.rl_image_delete_bottom_layout:
            break;
        }

    }

    private void loadAnimations() {
        fadeInAnimation = AnimationUtils.loadAnimation(getActivity(),
                R.anim.confirm_delete_bg_color_fadein);
        fadeoutAnimation = AnimationUtils.loadAnimation(getActivity(),
                R.anim.confirm_delete_bg_color_fadeout);

        // fadeoutAnimation.setStartOffset(-300);

    }

    private void setBackgroundColorWithDelay() {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {

                tranculentLayout.setVisibility(View.VISIBLE);
                tranculentLayout.setAnimation(fadeInAnimation);
            }
        }, 250);
    }

    private void removeImageWithDelay() {
        onDeleteImageClick.isTickClicked();
    }

    private void hideTranculentbg() {

        tranculentLayout.setAnimation(fadeoutAnimation);
        tranculentLayout.setVisibility(View.INVISIBLE);

    }

    public void removeFragmentWithDelayAnimation(final String mFragmentName) {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {

                if (getFragmentManager() != null) {
                    getFragmentManager().popBackStack(mFragmentName,
                            FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }
            }
        }, 250);

    }

    @Override
    public boolean onBackPressed() {
        // TODO Auto-generated method stub
        return false;
    }

}
