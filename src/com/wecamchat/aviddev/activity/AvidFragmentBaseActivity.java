package com.wecamchat.aviddev.activity;

import java.util.ArrayList;

import net.hockeyapp.android.CrashManager;
import net.hockeyapp.android.UpdateManager;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.ViewFlipper;

import com.wecamchat.aviddev.R;
import com.wecamchat.aviddev.constant.AppConstant;
import com.wecamchat.aviddev.fragment.AvidBaseFragment;
import com.wecamchat.aviddev.fragment.BrowseUserFragment;
import com.wecamchat.aviddev.fragment.ChatFragment;
import com.wecamchat.aviddev.fragment.FilterFragment;
import com.wecamchat.aviddev.fragment.MapUserFragment;
import com.wecamchat.aviddev.fragment.ProfileFragment;
import com.wecamchat.aviddev.fragment.WinkFragment;
import com.wecamchat.aviddev.intrface.OnCurrentMarkerClickListener;
import com.wecamchat.aviddev.subfragment.CreateProfileFragment;
import com.wecamchat.aviddev.util.PreferenceKeeper;
import com.wecamchat.aviddev.util.location.LocationTrackerService;

/**
 * This class is Base Activity of Avid App, controls base layout controls and
 * common listener.
 * 
 * @author gaurav
 * 
 */

public class AvidFragmentBaseActivity extends AvidBaseActivity implements
        OnCurrentMarkerClickListener, OnTouchListener {

    public static boolean skipPasscode;

    private LinearLayout footerLinearLayout;
    private RadioButton radioButtonProfile;
    private RadioButton radioButtonChat;
    private RadioButton radioButtonWink;
    private RadioButton radioButtonGrid;
    private RadioButton radioButtonMap;
    private RadioButton radioButtonFilter;

    private ArrayList<RadioButton> footerRadioButtonRhAL = null;
    private ArrayList<RadioButton> footerRadioButtonLhAL = null;

    private FragmentTransaction mFragmentTransaction;
    public static String packageName;

    // Default footer position flags
    int prePosition = AppConstant.FooterConstant.FOOTER_DEFAULT_PRE_POSITION;

    // MapUserFragment position set
    int position = AppConstant.FooterConstant.SET_DEFAULT_FRAGMENT_INDEX;

    private ProfileFragment mProfileFragment;
    private ChatFragment mChatFragment;
    private WinkFragment mWinkFragment;
    private BrowseUserFragment mBrowserUserFragment;
    private MapUserFragment mMapUserFragment;
    private FilterFragment mFilterFragment;
    private PreferenceKeeper prefs;
    private AvidBaseFragment currentFragment;
    private float xCordDown;
    private float xCordUp;

    private RadioButton radioButtonLhProfile;

    private RadioButton radioButtonLhChat;

    private RadioButton radioButtonLhWink;

    private RadioButton radioButtonLhGrid;

    private RadioButton radioButtonLhMap;

    private RadioButton radioButtonLhFilter;

    private ViewFlipper switchFooter;

    ArrayList<RadioButton> AL;

    private Bundle bundle;
    private ProgressBar progressBar;
    private LinearLayout progressBarTranslucentLayout;
    private FrameLayout rootFrameLayout;
    private RelativeLayout blackscreen;
    private Handler footerBarHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bundle = getIntent().getExtras();
        if (bundle != null && bundle.getBoolean("isFromTOS")) {
            // no animation, coming from tos screen
        } else {
            // set animation, coming from splash screen
            overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        }

        // set package name
        if (packageName == null) {
            packageName = getPackageName();
        }

        setContentView(R.layout.activity_avid_base, R.layout.layout_footer);
        initViewControls();
        mFragmentTransaction = getFragmentManager().beginTransaction();
        if (savedInstanceState == null) {
            mFragmentTransaction.add(R.id.base_fragment_body_layout,
                    getMapUserFragment()).commit();
        }
    }

    private void setContentView(int bodyLayoutResID, int footerLayoutResID) {
        setContentView(R.layout.activity_avid_base);

        initViewControls(bodyLayoutResID, footerLayoutResID);
    }

    private void initViewControls(int bodyLayoutResID, int footerLayoutResID) {
        footerLinearLayout = (LinearLayout) findViewById(R.id.activity_avid_base_footer_layout);

        inflateFooterView(footerLayoutResID);

    }

    public void inflateFooterView(int footerLayoutResID) {

        View currentFooterView = ((LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
                footerLayoutResID, null);

        footerLinearLayout.removeAllViews();
        footerLinearLayout.addView(currentFooterView,
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        manageFooter();

    }

    public void manageFooter() {

        switchFooter = (ViewFlipper) findViewById(R.id.switchfooter);

        if (getPreferenceObject().getHandPrefrence()) {
            switchFooter.setDisplayedChild(1);
        } else {
            switchFooter.setDisplayedChild(0);
        }
    }

    /**
     * @param isClickable
     * @param viewGroup
     */
    public void enableLayout(boolean isClickable, ViewGroup viewGroup) {
        try {
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View child = viewGroup.getChildAt(i);
                child.setEnabled(isClickable);
                // if child itself is a view group then disable its children too
                if (child instanceof ViewGroup) {
                    enableLayout(isClickable, (ViewGroup) child);
                }
            }
        } catch (Exception e) {
            // e.printStackTrace();
        }
    }

    private void initViewControls() {

        radioButtonProfile = (RadioButton) findViewById(R.id.rb_avid_footer_profile);
        radioButtonChat = (RadioButton) findViewById(R.id.rb_avid_footer_chat);
        radioButtonWink = (RadioButton) findViewById(R.id.rb_avid_footer_smill);
        radioButtonGrid = (RadioButton) findViewById(R.id.rb_avid_footer_grid);
        radioButtonMap = (RadioButton) findViewById(R.id.rb_avid_footer_map);
        radioButtonFilter = (RadioButton) findViewById(R.id.rb_avid_footer_filter);

        radioButtonLhProfile = (RadioButton) findViewById(R.id.rb_avid_footer_lh_profile);
        radioButtonLhChat = (RadioButton) findViewById(R.id.rb_avid_footer_lh_chat);
        radioButtonLhWink = (RadioButton) findViewById(R.id.rb_avid_footer_lh_smill);
        radioButtonLhGrid = (RadioButton) findViewById(R.id.rb_avid_footer_lh_grid);
        radioButtonLhMap = (RadioButton) findViewById(R.id.rb_avid_footer_lh_map);
        radioButtonLhFilter = (RadioButton) findViewById(R.id.rb_avid_footer_lh_filter);

        radioButtonProfile.setOnTouchListener(this);
        radioButtonChat.setOnTouchListener(this);
        radioButtonWink.setOnTouchListener(this);
        radioButtonGrid.setOnTouchListener(this);
        radioButtonMap.setOnTouchListener(this);
        radioButtonFilter.setOnTouchListener(this);

        radioButtonLhProfile.setOnTouchListener(this);
        radioButtonLhChat.setOnTouchListener(this);
        radioButtonLhWink.setOnTouchListener(this);
        radioButtonLhGrid.setOnTouchListener(this);
        radioButtonLhMap.setOnTouchListener(this);
        radioButtonLhFilter.setOnTouchListener(this);

        footerRadioButtonRhAL = new ArrayList<RadioButton>();

        footerRadioButtonLhAL = new ArrayList<RadioButton>();

        footerRadioButtonRhAL.add(radioButtonProfile);
        footerRadioButtonRhAL.add(radioButtonChat);
        footerRadioButtonRhAL.add(radioButtonWink);
        footerRadioButtonRhAL.add(radioButtonGrid);
        footerRadioButtonRhAL.add(radioButtonMap);
        footerRadioButtonRhAL.add(radioButtonFilter);

        footerRadioButtonLhAL.add(radioButtonLhProfile);
        footerRadioButtonLhAL.add(radioButtonLhChat);
        footerRadioButtonLhAL.add(radioButtonLhWink);
        footerRadioButtonLhAL.add(radioButtonLhGrid);
        footerRadioButtonLhAL.add(radioButtonLhMap);
        footerRadioButtonLhAL.add(radioButtonLhFilter);

        setFooterImageOnSwipe(AppConstant.FooterConstant.SET_DEFAULT_FRAGMENT_INDEX);

        progressBarTranslucentLayout = (LinearLayout) findViewById(R.id.translucent_layout_for_progress_bar);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        rootFrameLayout = (FrameLayout) findViewById(R.id.ll_activity_avid_base_root);

        blackscreen = (RelativeLayout) findViewById(R.id.activity_avid_base_no_footer);

        initiateRootLayoutListener();
    }

    /**
     * Method is used to hide progressbar.
     */
    public void hideProgressBar() {
        if (progressBar != null && progressBar.getVisibility() == View.VISIBLE) {
            progressBar.setVisibility(View.GONE);
            progressBarTranslucentLayout.setVisibility(View.GONE);
            enableLayout(true, rootFrameLayout);
        }
    }

    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        progressBarTranslucentLayout.setVisibility(View.VISIBLE);
        enableLayout(false, rootFrameLayout);
    }

    public void setFooterImageOnSwipe(int position) {
        AL = getRadioButtonList();
        RadioButton rb;
        for (int index = 0; index < AppConstant.FooterConstant.FOOTER_OPTION_SIZE; index++) {
            rb = AL.get(index);
            if (index == position) {
                rb.setChecked(true);
            } else {
                rb.setChecked(false);
            }
        }
    }

    private ArrayList<RadioButton> getRadioButtonList() {

        if (getPreferenceObject().getHandPrefrence()) {
            return footerRadioButtonLhAL;
        } else {
            return footerRadioButtonRhAL;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("life", "on resume in base activiy called ");
        if (getPreferenceObject().isPasscodeEnabled()
                && !bundle.getBoolean("isFromBackSplash") && !skipPasscode) {
            blackscreen.setBackgroundColor(getResources().getColor(
                    R.color.black_color_code));
            blackscreen.setVisibility(View.VISIBLE);

            customLauncherActivity(PassCodeActivity.class, null, 0, true);

        }
        skipPasscode = false;
        checkForCrashes();
        checkForUpdates();

        if (prefs.isProfileActivate()
                && !isServiceRunning(LocationTrackerService.class)) {
            startLocationService();
        }
    }

    private void checkForCrashes() {

        CrashManager.register(this, AppConstant.hockeyId_Constant());//
    }

    private void checkForUpdates() {
        // TODO Remove this for store builds!
        UpdateManager.register(this, AppConstant.hockeyId_Constant());//
    }

    public void replaceFragment(int containerId, Fragment className,
            boolean isNextFragmentNeedsTobeAdded, boolean isCommitIsStateLoss,
            int inAnim, int outAnim) {

        FragmentTransaction fragmentTransaction = this.getFragmentManager()
                .beginTransaction();

        fragmentTransaction.setCustomAnimations(inAnim, outAnim);

        fragmentTransaction.replace(containerId, className);

        if (isCommitIsStateLoss) {
            fragmentTransaction.commitAllowingStateLoss();
        } else {
            fragmentTransaction.commit();
        }

    }

    /*
     * It called from BrowserUserViewFragment, onItemClick method
     */

    @Override
    public void onBackPressed() {
        if (MapUserFragment.isMapLongPressed) {
            getMapUserFragment().downMapDrawer();
            getMapUserFragment().removeLongPressedMarker();
            MapUserFragment.isMapLongPressed = false;
        }
        if (currentFragment != null && currentFragment.onBackPressed()) {
            return;
        }

        // if (MapUserFragment.isMarkerClick) {
        // getMapUserFragment().downMapDrawer();
        // } else

        super.onBackPressed();
    }

    /**
     * Method is used to return package name.
     */

    @Override
    public String getPackageName() {

        return getApplication().getPackageName();

    }

    /*
     * set footer on first position in click of current marker on
     * MapUserFragment class
     * 
     * postion = 0 it means first position set Like UserProfile
     */
    @Override
    public void onCurrentMarkerClick() {

        setFooterImageOnSwipe(0);
        prePosition = AppConstant.FooterConstant.FOOTER_DEFAULT_PRE_POSITION;
        position = 0; // for my profile selected
    }

    /*
     * used for set user drawer back stack if userdrawer open
     */
    public void onGoneMapFooterClick() {

        if (getFragmentManager() != null) {
            if (getFragmentManager() != null)
                getFragmentManager().popBackStack("M",
                        FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    public PreferenceKeeper getPreferenceObject() {
        if (prefs == null) {
            prefs = PreferenceKeeper.getInstance(this);
        }

        return prefs;
    }

    // 0 = Down , 1= Up , 2 = Move

    public void replaceFragment(AvidBaseFragment fragment, int prePosition2,
            int position2) {
        currentFragment = fragment;
        if (getPreferenceObject().getHandPrefrence()) {
            if (prePosition2 > position2) {
                replaceFragment(R.id.base_fragment_body_layout, fragment,
                        false, false, R.anim.slide_in_right,
                        R.anim.slide_out_left);

            } else {
                replaceFragment(R.id.base_fragment_body_layout, fragment,
                        false, false, R.anim.slide_in_left,
                        R.anim.slide_out_right);
            }

        } else {
            if (prePosition2 > position2) {
                replaceFragment(R.id.base_fragment_body_layout, fragment,
                        false, false, R.anim.slide_in_left,
                        R.anim.slide_out_right);

            } else {
                replaceFragment(R.id.base_fragment_body_layout, fragment,
                        false, false, R.anim.slide_in_right,
                        R.anim.slide_out_left);
            }
        }

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getAction()) {

        case MotionEvent.ACTION_DOWN:

            xCordDown = event.getX();

            break;

        case MotionEvent.ACTION_UP:

            xCordUp = event.getX();
            Log.i("Xup", xCordUp + "");

            if (((xCordUp - xCordDown) > 100) && xCordUp > 0) { // left to right
                Log.i("Xdiff", (xCordUp - xCordDown) + ""); // swipe
                if (getPreferenceObject().getHandPrefrence()) {
                    ++position;
                    if (position == 5) { // used for last position
                        position = 0;
                    }

                } else {
                    --position;
                    if (position == -1) { // used for set initial position
                        position = 4;
                    }
                }

            } else if ((xCordDown - xCordUp) > 100) { // right to left swipe
                if (getPreferenceObject().getHandPrefrence()) {
                    --position;
                    if (position == -1) { // used for set initial position
                        position = 4;
                    }
                } else {
                    ++position;
                    if (position == 5) { // used for last position
                        position = 0;
                    }
                }

            } else {
                position = Integer.parseInt(v.getTag().toString());

            }

            setFooterOnTouch(position);
            break;

        }

        return true;
    }

    private ProfileFragment getProfileFragment() {
        if (mProfileFragment == null) {
            mProfileFragment = new ProfileFragment();
        }
        return mProfileFragment;
    }

    private ChatFragment getChatFragment() {
        if (mChatFragment == null) {
            mChatFragment = new ChatFragment();
        }
        return mChatFragment;
    }

    private WinkFragment getWinkFragment() {
        if (mWinkFragment == null) {
            mWinkFragment = new WinkFragment();
        }
        return mWinkFragment;
    }

    private BrowseUserFragment getBrowseUserViewFragment() {
        if (mBrowserUserFragment == null) {
            mBrowserUserFragment = new BrowseUserFragment();

        }
        return mBrowserUserFragment;
    }

    private MapUserFragment getMapUserFragment() {
        if (mMapUserFragment == null) {
            mMapUserFragment = new MapUserFragment();
        }
        return mMapUserFragment;
    }

    private FilterFragment getFilterFragment() {
        if (mFilterFragment == null) {
            mFilterFragment = new FilterFragment();
        }
        return mFilterFragment;
    }

    /**
     * Check network availability
     * 
     * @return true: if network is available, false otherwise
     */
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        boolean errorInNetwork = activeNetworkInfo != null
                && activeNetworkInfo.isConnected();
        return errorInNetwork;

    }

    /**
     * set listener for managing bottom footer visibility based on keyboard
     */
    private void initiateRootLayoutListener() {
        rootFrameLayout.getViewTreeObserver().addOnGlobalLayoutListener(
                new OnGlobalLayoutListener() {

                    @Override
                    public void onGlobalLayout() {

                        int heightDiff = rootFrameLayout.getRootView()
                                .getHeight() - rootFrameLayout.getHeight();

                        if (heightDiff > 100) {
                            hideFooterBar();
                        } else {
                            showFooterBar();
                        }

                    }
                });

    }

    private void hideFooterBar() {
        if (footerLinearLayout != null) {
            footerLinearLayout.setVisibility(View.GONE);
        }
    }

    private void showFooterBar() {
        // (new ShowFooterSlowly()).execute();
        if (footerBarHandler == null) {
            footerBarHandler = new Handler();
        }
        footerBarHandler.postDelayed(footerBarRunnable, 50);
    }

    Runnable footerBarRunnable = new Runnable() {
        @Override
        public void run() {

            if (footerLinearLayout != null) {
                footerLinearLayout.setVisibility(View.VISIBLE);
            }
        }
    };

    private Dialog passcodeDialog;

    private String passcode_string;

    /*
     * set footer and fragment on touch and on touch swipe on footer layout
     */
    private void setFooterOnTouch(int position) {

        Log.i("Pager", " AvidBase " + position);
        // if (BrowseUserViewFragment.isBrowserClick) {
        // mBrowserUserFragment.browser.removeListener();
        // }
        // // used for onTouch same fragment
        if (prePosition == position) {
            return;
        }

        if (position == 0 && !prefs.isProfileActivate()) {
            replaceFragment(R.id.activity_avid_base_no_footer,
                    AppConstant.FragmentStackName.CREATE_PROFILE_FRAGMENT,
                    "S1", new CreateProfileFragment(prePosition, position), 0,
                    0, 0, 0, true, false);
            return;
        }

        if (position >= 0 && position <= 5) {
            setFooterImageOnSwipe(position);
        }

        switch (position) {
        case 0:
            if (!prefs.isProfileActivate()) {

                replaceFragment(R.id.activity_avid_base_no_footer,

                AppConstant.FragmentStackName.CREATE_PROFILE_FRAGMENT,

                "S1", new CreateProfileFragment(prePosition, position),

                0, 0, 0, 0, true, false);

            } else {

                replaceFragment(getProfileFragment(), prePosition, position);

            }

            break;
        case 1:
            replaceFragment(getChatFragment(), prePosition, position);
            break;
        case 2:

            replaceFragment(getWinkFragment(), prePosition, position);
            break;
        case 3:

            replaceFragment(getBrowseUserViewFragment(), prePosition, position);
            break;
        case 4:

            replaceFragment(getMapUserFragment(), prePosition, position);

            break;
        case 5:

            replaceFragment(R.id.base_fragment_body_layout,
                    getFilterFragment(), false, false, 0, 0);
            break;
        default:
            break;
        }

        onGoneMapFooterClick();

        prePosition = position;
    }

    /*
     * Fragment replacement code using content id on pager
     */

    private void replaceFragment(int containerId,
            String fragmentTagToBeAddedToBackStack,
            String fragmentTagToBeAdded, Fragment className, int enter,
            int exit, int enterPop, int exitPop,
            boolean isNextFragmentNeedsTobeAdded, boolean isCommitIsStateLoss) {

        FragmentTransaction fragmentTransaction = this.getFragmentManager()
                .beginTransaction();
        if (fragmentTagToBeAddedToBackStack == null
                && isNextFragmentNeedsTobeAdded) {
            return;
        }

        if (fragmentTagToBeAdded == null) {
            return;
        }

        if (fragmentTagToBeAddedToBackStack
                .equalsIgnoreCase(fragmentTagToBeAdded)) {
            return;
        }
        fragmentTransaction.setCustomAnimations(enter, exit, enterPop, exitPop);

        fragmentTransaction.replace(containerId, className);
        if (isNextFragmentNeedsTobeAdded) {
            fragmentTransaction.addToBackStack(fragmentTagToBeAddedToBackStack);
        }
        if (isCommitIsStateLoss) {
            fragmentTransaction.commitAllowingStateLoss();
        } else {
            fragmentTransaction.commit();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("life", "on Stop in base activiy called ");

        if (bundle != null) {
            bundle.putBoolean("isFromBackSplash", false);

        }
        if (isServiceRunning(LocationTrackerService.class)) {
            stopLocationService();
        }

    }

    public void startLocationService() {
        // TODO uncomment later
        // Intent intent = new Intent(this, LocationTrackerService.class);
        // startService(intent);
    }

    private void stopLocationService() {
        // TODO uncomment later
        // Intent intent = new Intent(this, LocationTrackerService.class);
        // stopService(intent);
    }

    private boolean isServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (RunningServiceInfo service : manager
                .getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

}
