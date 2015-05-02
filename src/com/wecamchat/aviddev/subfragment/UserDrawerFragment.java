package com.wecamchat.aviddev.subfragment;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.wecamchat.aviddev.R;
import com.wecamchat.aviddev.fragment.AvidBaseFragment;
import com.wecamchat.aviddev.intrface.OnSwipeDownMapBrowerDrawerListener;
import com.wecamchat.aviddev.model.bo.User;

public class UserDrawerFragment extends AvidBaseFragment {

    private OnSwipeDownMapBrowerDrawerListener clickOnMapUserDrawser;
    private User userData;

    private boolean isScrolled;
    boolean oneTimeCall;
    private View view;

    public User getUserData() {
        return userData;
    }

    public void setUserData(User userData) {
        this.userData = userData;
    }

    public UserDrawerFragment(
            OnSwipeDownMapBrowerDrawerListener clickOnMapUserDrawser,
            User userData) {
        this.clickOnMapUserDrawser = clickOnMapUserDrawser;
        this.userData = userData;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_user_drawer_test, container,
                false);
        Log.i("MOVE", " onCreateView ");
        isFirstTimeTouchOnScroll = false;
        return view;

    }

    LinearLayout inc;

    float yDown, yUP;
    boolean isFirstTimeTouchOnScroll;

    private OnScrollChangedListener listener;
    private RelativeLayout rl_userdrawer_full_ui;
    private ScrollView scroolview_user_drawer;
    private RelativeLayout rl_userdrawer_half_ui;
    private RelativeLayout rl_userdrawer_include_full;
    private RelativeLayout rl_userdrawer_include_half;
    private ImageView iv_userdrawer_userimage_half_ui;
    private ImageView iv_userdrawer_userimage_full_ui;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onViewCreated(view, savedInstanceState);
        Log.i("MOVE", " onViewCreate");

        scroolview_user_drawer = (ScrollView) view
                .findViewById(R.id.scroolview_user_drawer);

        rl_userdrawer_full_ui = (RelativeLayout) view
                .findViewById(R.id.rl_userdrawer_full_ui);
        rl_userdrawer_half_ui = (RelativeLayout) view
                .findViewById(R.id.rl_userdrawer_half_ui);
        rl_userdrawer_include_full = (RelativeLayout) view
                .findViewById(R.id.rl_userdrawer_include_full);
        rl_userdrawer_include_half = (RelativeLayout) view
                .findViewById(R.id.rl_userdrawer_include_half);

        final TextView tv_trans_userdrawer_full_ui = (TextView) view
                .findViewById(R.id.tv_trans_userdrawer_full_ui);

        iv_userdrawer_userimage_half_ui = (ImageView) view
                .findViewById(R.id.iv_userdrawer_userimage_half_ui);

        iv_userdrawer_userimage_full_ui = (ImageView) view
                .findViewById(R.id.iv_userdrawer_userimage_full_ui);

        iv_userdrawer_userimage_half_ui
                .setOnTouchListener(new OnTouchListener() {

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        isScrolled = false;

                        isFirstTimeTouchOnScroll = true;
                        return true;
                    }
                });

        tv_trans_userdrawer_full_ui.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                isScrolled = true;

                return isScrolled;
            }
        });
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                scroolview_user_drawer.scrollBy(0, 250);
            }
        }, 300);

        scroolview_user_drawer.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return isScrolled;
            }
        });

        listener = new OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {

                int scrollY = scroolview_user_drawer.getScrollY(); // for
                Log.i("MOVE", "scroll ll " + scrollY);
                if (isFirstTimeTouchOnScroll) {
                    if (scrollY < 130) {
                        if (getFragmentManager() != null) {

                            getFragmentManager().popBackStack("M",
                                    FragmentManager.POP_BACK_STACK_INCLUSIVE);
                            // BrowseUserViewFragment.isBrowserClick = false;
                            clickOnMapUserDrawser.onSwipeDownMapBrowerDrawer();
                            removeListener();
                        }
                    } else {
                        scroolview_user_drawer.scrollBy(0, 250 - scrollY);
                    }

                    // up

                    if (scrollY > 320) {
                        if (!oneTimeCall) {
                            scroolview_user_drawer.scrollBy(0, -600);

                            new Handler().postDelayed(new Runnable() {

                                @SuppressLint("NewApi")
                                @Override
                                public void run() {
                                    Log.i("MOVE", " run");

                                    rl_userdrawer_half_ui
                                            .setVisibility(View.GONE);
                                    rl_userdrawer_full_ui
                                            .setVisibility(View.VISIBLE);
                                    rl_userdrawer_include_half
                                            .setVisibility(View.GONE);
                                    rl_userdrawer_include_full
                                            .setVisibility(View.VISIBLE);

                                    oneTimeCall = true;
                                    isFirstTimeTouchOnScroll = false;
                                }
                            }, (long) .001);

                        }
                    }

                }

            }
        };

        setScrollView();
    }

    private void setScrollView() {
        // TODO Auto-generated method stub
        scroolview_user_drawer.getViewTreeObserver()
                .addOnScrollChangedListener(listener);
    }

    public void setOtherUserData(User userData) {
        // TODO Auto-generated method stub

        setUserData(userData);

        if (userData != null) {

            Log.i("ONTOUCH", " url " + userData.getuImg());
            ImageLoader.getInstance().displayImage(userData.getuImg(),
                    iv_userdrawer_userimage_half_ui);

        }
    }

    public void removeListener() {
        if (listener != null) {
            scroolview_user_drawer.getViewTreeObserver()
                    .removeOnScrollChangedListener(listener);
        }

    }

    @Override
    public boolean onBackPressed() {
        return false;
    }
}
