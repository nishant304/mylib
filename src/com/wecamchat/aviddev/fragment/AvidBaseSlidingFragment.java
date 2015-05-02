package com.wecamchat.aviddev.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.wecamchat.aviddev.R;
import com.wecamchat.aviddev.util.PreferenceKeeper;
import com.wecamchat.aviddev.util.slidingdrawer.SlidingUpPanelLayout;
import com.wecamchat.aviddev.util.slidingdrawer.SlidingUpPanelLayout.PanelSlideListener;
import com.wecamchat.aviddev.util.slidingdrawer.SlidingUpPanelLayout.PanelState;
import com.wecamchat.aviddev.util.view.CustomScrollView;

public abstract class AvidBaseSlidingFragment extends AvidBaseFragment {

    protected static final String TAG = AvidBaseSlidingFragment.class.getName();
    protected SlidingUpPanelLayout mLayout;
    protected CustomScrollView scrollView;
    protected PanelState panelState;
    private View bottomRightPanelView;
    protected ImageLoader imageLoader;
    protected DisplayImageOptions options;
    private View dragView;

    private boolean isUserMode = true;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        if (PreferenceKeeper.getInstance(getActivity()).getHandPrefrence()) {
            view = inflater.inflate(R.layout.slide_layout_left, container,
                    false);
            addViews(inflater, view);

        } else {
            view = inflater.inflate(R.layout.slide_layout, container, false);
            addViews(inflater, view);
        }

        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.user_image)
                .showImageForEmptyUri(R.drawable.user_image)
                .showImageOnFail(R.drawable.user_image).cacheInMemory(true)
                .cacheOnDisc(true).considerExifParams(true).build();

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setFocusableInTouchMode(true);
        bottomRightPanelView = view.findViewById(R.id.bottom_side_panel);
        mLayout = (SlidingUpPanelLayout) view.findViewById(R.id.sliding_layout);

        mLayout.setDragView(dragView);
        mLayout.setAnchorPoint(0.63f);
        mLayout.hidePanel();
        mLayout.setPanelHeight(0);
        // mLayout.setCoveredFadeColor(0x00000000);
        scrollView = (CustomScrollView) view.findViewById(R.id.scrollView);
        scrollView.setDragView(dragView);

        mLayout.setPanelSlideListener(new PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                Log.i(TAG, "onPanelSlide, offset " + slideOffset);
                panelState = PanelState.DRAGGING;
                if (isUserMode) {
                    bottomRightPanelView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPanelExpanded(View panel) {
                Log.i(TAG, "onPanelExpanded");
                panelState = PanelState.EXPANDED;
                scrollView.setPanelState(panelState);
                mLayout.setDragView(dragView);
                scrollView.updateParentCoordinates();
                if (isUserMode) {

                    bottomRightPanelView.setVisibility(View.VISIBLE);
                }
                onBasePanelExpanded(panel);
            }

            @Override
            public void onPanelCollapsed(View panel) {
                Log.i(TAG, "onPanelCollapsed");
                panelState = PanelState.COLLAPSED;
                scrollView.setPanelState(panelState);
                mLayout.setDragView(dragView);
                scrollView.updateParentCoordinates();
                if (isUserMode) {

                    bottomRightPanelView.setVisibility(View.GONE);
                }
                onBasePanelCollapsed(panel);
            }

            @Override
            public void onPanelAnchored(View panel) {
                Log.i(TAG, "onPanelAnchored");
                panelState = PanelState.ANCHORED;
                scrollView.setPanelState(panelState);
                mLayout.setDragView(mLayout);
                scrollView.updateParentCoordinates();
                if (isUserMode) {

                    bottomRightPanelView.setVisibility(View.VISIBLE);
                }

                onBasePanelAnchored(panel);

            }

            @Override
            public void onPanelHidden(View panel) {
                Log.i(TAG, "onPanelHidden");
                panelState = PanelState.HIDDEN;
                scrollView.setPanelState(panelState);
                mLayout.setDragView(dragView);
                scrollView.updateParentCoordinates();

                bottomRightPanelView.setVisibility(View.GONE);

            }
        });
    }

    protected void onBasePanelExpanded(View panel) {
        // TODO Auto-generated method stub

    }

    protected void onBasePanelAnchored(View panel) {
        // TODO Auto-generated method stub

    }

    protected abstract void addViews(LayoutInflater inflater, View parentView);

    public boolean onBackPressed() {
        if (panelState == PanelState.ANCHORED
                || panelState == PanelState.EXPANDED) {
            Log.d(TAG, "onBackPressed KeyEvent.KEYCODE_BACK");
            mLayout.hidePanel();
            return true;
        }
        return false;
    }

    public void onBasePanelCollapsed(View panel) {
        // TODO Auto-generated method stub

    }

    public void setDragViews(int layoutFriendsProfileHeaderRoot,
            boolean isUserMode) {

        this.isUserMode = isUserMode;
        dragView = getActivity().findViewById(layoutFriendsProfileHeaderRoot);

        scrollView.setDragView(dragView);

        if (isUserMode) {
            mLayout.setAnchorPoint(0.63f);
        } else {
            mLayout.setAnchorPoint(0.50f);
        }

    }

    public void hideBottomRightPanel() {

        bottomRightPanelView.setVisibility(View.GONE);

    }

    public void showBottomRightPanel() {

        bottomRightPanelView.setVisibility(View.VISIBLE);

    }

}
