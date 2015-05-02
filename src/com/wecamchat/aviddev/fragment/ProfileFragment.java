package com.wecamchat.aviddev.fragment;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.TimeZone;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.wecamchat.aviddev.R;
import com.wecamchat.aviddev.activity.AvidFragmentBaseActivity;
import com.wecamchat.aviddev.api.ApiName;
import com.wecamchat.aviddev.api.ApiOutput;
import com.wecamchat.aviddev.api.AvidApiClient;
import com.wecamchat.aviddev.api.AvidUrls;
import com.wecamchat.aviddev.api.ErrorObject;
import com.wecamchat.aviddev.api.io.HotUnhotOutput;
import com.wecamchat.aviddev.api.io.MediaContentOutput;
import com.wecamchat.aviddev.api.io.MediaDeleteOutput;
import com.wecamchat.aviddev.api.io.ProfileEditInput;
import com.wecamchat.aviddev.api.io.ProfileEditOutput;
import com.wecamchat.aviddev.api.io.ProfileMediaMoveOutput;
import com.wecamchat.aviddev.constant.AppConstant;
import com.wecamchat.aviddev.cp.AvidCPHelper;
import com.wecamchat.aviddev.httpClient.AppHttpRequest;
import com.wecamchat.aviddev.httpClient.AppRequestBuilder;
import com.wecamchat.aviddev.httpClient.AppResponseListener;
import com.wecamchat.aviddev.httpClient.AppRestClient;
import com.wecamchat.aviddev.intrface.OnDeleteImageClick;
import com.wecamchat.aviddev.intrface.OnHotIconClick;
import com.wecamchat.aviddev.model.bo.CapturedData;
import com.wecamchat.aviddev.model.bo.ExpandableFilter;
import com.wecamchat.aviddev.model.bo.FilterOptions;
import com.wecamchat.aviddev.model.bo.MediaContent;
import com.wecamchat.aviddev.model.bo.MetaData;
import com.wecamchat.aviddev.model.bo.Profile;
import com.wecamchat.aviddev.model.bo.User;
import com.wecamchat.aviddev.subfragment.CameraOpenFragment;
import com.wecamchat.aviddev.subfragment.ConfirmFlameToggleFragment;
import com.wecamchat.aviddev.subfragment.ConfirmImageDeleteFragment;
import com.wecamchat.aviddev.subfragment.SettingsFragment;
import com.wecamchat.aviddev.util.ImageVideoManager;
import com.wecamchat.aviddev.util.ImageVideoManager.ImageVideoData;
import com.wecamchat.aviddev.util.PreferenceKeeper;
import com.wecamchat.aviddev.util.view.Utils;
import com.wecamchat.aviddev.viewadapter.ProfileExpandableAdapter;

import eu.janmuller.android.simplecropimage.CropImage;

public class ProfileFragment extends AvidBaseFragment implements
        OnTouchListener, OnClickListener, OnDragListener,
        SurfaceHolder.Callback {

    private final int mUnhotTag = 0;
    private final int mHotTag = 1;
    OnHotIconClick onHotIconClick;
    OnDeleteImageClick onDeleteImageClick;

    public static boolean setProfileFlag;
    // private ImageView profilePicImageView;
    private ImageView userSettingImageView;
    private ImageView userLocationImageView;
    private ImageView feelingHotImageView;

    private ExpandableListView upForListView;
    private ExpandableListView orientationListView;
    private ExpandableListView bodyTypeListView;
    private ExpandableListView temperamentListView;
    private ExpandableListView sizeListView;
    private ExpandableListView hivStatusListView;
    private ExpandableListView heightListView;
    private ExpandableListView drinkListView;
    private ExpandableListView eyeColorListView;
    private ExpandableListView roleListView;
    private ExpandableListView personaListView;
    private ExpandableListView bodyHairListView;
    private ExpandableListView cutListView;
    private ExpandableListView ethnicityListView;
    private ExpandableListView outToListView;
    private ExpandableListView smokeListView;
    private ExpandableListView hairColorListView;

    private TextView upForTextView;
    private TextView orientationTextView;
    private TextView bodyTypeTextView;
    private TextView temperamentTextView;
    private TextView sizeTextView;
    private TextView hivStatusTextView;
    private TextView heightTextView;
    private TextView drinkTextView;
    private TextView eyeColorTextView;
    private TextView roleTextView;
    private TextView personaTextView;
    private TextView bodyHairTextView;
    private TextView cutTextView;
    private TextView ethnicityTextView;
    private TextView outToTextView;
    private TextView smokeTextView;
    private TextView hairColorTextView;

    private TextView upForLabel;
    private TextView orientationLabel;
    private TextView bodyTypeLabel;
    private TextView temperamentLabel;
    private TextView sizeLabel;
    private TextView hivStatusLabel;
    private TextView heightLabel;
    private TextView drinkLabel;
    private TextView eyeColorLabel;
    private TextView roleLabel;
    private TextView personaLabel;
    private TextView bodyHairLabel;
    private TextView cutLabel;
    private TextView ethnicityLabel;
    private TextView outToLabel;
    private TextView smokeLabel;
    private TextView hairColorLabel;

    private View view;
    private TextView tv_my_username;
    private TextView tv_my_time_count;
    private TextView tv_my_wink_count;
    private TextView tv_my_headline;

    private TextView dobLabelTextView;
    private TextView dobTextView;

    private ImageLoader imageLoader;
    private PreferenceKeeper prefs;

    private int crossImageIndex;

    private boolean isHotTickClicked;
    private ScrollView scrollView;

    private User user;

    private DatePickerDialog datePickerDialog;
    private Calendar dob;

    /*
     * All var used for image reorder
     */
    private LinearLayout ll_my_profile_add_image;
    private LayoutParams mLayoutParams;
    private View mInflateView;

    private List<View> mInflateViewStoreArrayList;

    private int mDragViewTag;
    private int mDropViewTag;
    private int mDragViewTagDefault;

    private ImageView iv_my_profile_primary_pic;
    private ImageView iv_profile_camera_pickup;
    private ImageView mUserImageView;
    private String filePath;

    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private MediaPlayer mediaPlayer;
    private AvidCPHelper helper;

    private String KEY = null;
    private ImageView iv_my_profile_play_btn;

    /*
     * All var is used for swipe and set image/video in primary from server side
     */

    private String imageOrVideoUrl;
    private String typeImageOrVideo;
    private List<MediaContent> mediaContentSmallAL;
    private List<MediaContent> mediaContentPrimaryAL;

    boolean type;

    private DisplayImageOptions videoOptions = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.drawable.user_image)
            .showImageForEmptyUri(R.drawable.user_image)
            .showImageOnFail(R.drawable.user_image).cacheInMemory(true)
            .cacheOnDisc(true).considerExifParams(true)
            .displayer(new RoundedBitmapDisplayer(1000)).build();

    private void setSurfaceViewVideo() {

        surfaceView = (SurfaceView) view
                .findViewById(R.id.iv_my_profile_primary_pic_surface_view);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);

        surfaceHolder.setSizeFromLayout();
        mediaPlayer = new MediaPlayer();
    }

    private void setPlayVideoInSurfaceView(Uri uri) {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.reset();
        }

        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setDisplay(surfaceHolder);

        try {
            mediaPlayer.setDataSource(getActivity(), uri);
            mediaPlayer.prepare();

            mediaPlayer.setOnCompletionListener(new OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {
                    iv_my_profile_primary_pic.setVisibility(View.VISIBLE);
                    surfaceView.setVisibility(View.INVISIBLE);
                    mediaPlayer.reset();

                    iv_my_profile_play_btn.setVisibility(View.VISIBLE);

                }
            });
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mediaPlayer.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        try {
            setProfileFlag = true;
            prefs = PreferenceKeeper.getInstance(getActivity());
            if (prefs.getHandPrefrence()) {
                view = inflater.inflate(R.layout.layout_base_lh_my_profile,
                        container, false);
            } else {
                view = inflater.inflate(R.layout.layout_base_my_profile,
                        container, false);
            }
            Log.i("OUT", " On create ");
            imageLoader = ImageLoader.getInstance();
            initUi();
            setUiListener();
            setSurfaceViewVideo();
            setImageReorderViewData();

            setOtherListener();

            initializeBaseApiResponseHandler();

            manageProfileData();

            helper = AvidCPHelper.getInstance(getActivity());

            mediaContentSmallAL = new ArrayList<MediaContent>();
            mediaContentPrimaryAL = new ArrayList<MediaContent>();

            mediaContentDataUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            return view;
        }

        return view;
    }

    private void setImageReorderViewData() {
        ll_my_profile_add_image = (LinearLayout) view
                .findViewById(R.id.ll_my_profile_add_image);

        iv_my_profile_play_btn = (ImageView) view
                .findViewById(R.id.iv_my_profile_play_btn);

        iv_my_profile_primary_pic = (ImageView) view
                .findViewById(R.id.iv_my_profile_primary_pic);

        iv_my_profile_primary_pic.setImageBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.user_image));
        mInflateViewStoreArrayList = new ArrayList<View>();

        mLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        iv_my_profile_primary_pic.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                // / video

                if (mediaContentPrimaryAL.get(0).getType()
                        .equalsIgnoreCase("video")) {
                    iv_my_profile_primary_pic.setVisibility(View.INVISIBLE);
                    surfaceView.setVisibility(View.VISIBLE);
                    setPlayVideoInSurfaceView(Uri.parse(mediaContentPrimaryAL
                            .get(0).getUrl()));

                    iv_my_profile_play_btn.setVisibility(View.INVISIBLE);

                }

            }
        });

        surfaceView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                iv_my_profile_primary_pic.setVisibility(View.VISIBLE);
                surfaceView.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void setUIInflateView() {

        mInflateViewStoreArrayList.clear();

        DisplayImageOptions options;

        for (int i = 0; i < mediaContentSmallAL.size(); i++) {

            mInflateView = ((LayoutInflater) getActivity().getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE)).inflate(
                    R.layout.layout_custom_my_profile, null);

            ImageView largeImage = (ImageView) mInflateView
                    .findViewById(R.id.iv_my_profile_small_pic);

            ImageView cross_Image = (ImageView) mInflateView
                    .findViewById(R.id.iv_my_profile_cross_pic);

            String url = mediaContentSmallAL.get(i).getUrl();
            options = getOptionImageLoader();

            if (!mediaContentSmallAL.get(i).getType().equalsIgnoreCase("image")) {
                options = videoOptions;
                url = url + ".jpeg";
            }

            imageLoader.displayImage(url, largeImage, options);

            mInflateView.setTag(i);
            cross_Image.setTag(i);

            mInflateViewStoreArrayList.add(mInflateView);

            cross_Image.setOnClickListener(this);
            mInflateView.setOnTouchListener(this);
            mInflateView.setOnDragListener(this);

            surfaceView.setOnDragListener(new OnDragListener() {

                @Override
                public boolean onDrag(View dropView, DragEvent event) {
                    switch (event.getAction()) {

                    case DragEvent.ACTION_DRAG_ENTERED:

                        if (mediaPlayer.isPlaying()) {
                            mediaPlayer.reset();
                            iv_my_profile_primary_pic
                                    .setVisibility(View.VISIBLE);
                            surfaceView.setVisibility(View.INVISIBLE);
                            setAndRemoveBitmapInEntered();

                            getApiResponseMediaMove(String.valueOf(prefs
                                    .getProfileRegistrationImageKey()), String
                                    .valueOf(mDragViewTag));
                        }

                        break;

                    case DragEvent.ACTION_DRAG_ENDED:

                        setViewEnded(event);

                    }
                    return true;
                }
            });

            iv_my_profile_primary_pic.setOnDragListener(new OnDragListener() {

                @Override
                public boolean onDrag(View dropView, DragEvent event) {
                    switch (event.getAction()) {

                    case DragEvent.ACTION_DRAG_ENTERED:

                        setAndRemoveBitmapInEntered();
                        getApiResponseMediaMove(String.valueOf(prefs
                                .getProfileRegistrationImageKey()), String
                                .valueOf(mDragViewTag));
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:

                        if (!dropView.equals(event.getLocalState())
                                && mediaContentSmallAL.size() == 1) {

                            setViewEnded(event);
                        }
                    }
                    return true;
                }
            });
        }

        addViewRunTime();

        if (mediaContentSmallAL.size() == AppConstant.MAX_PROFILE_IMAGE)
            iv_profile_camera_pickup.setVisibility(View.GONE);
        else
            iv_profile_camera_pickup.setVisibility(View.VISIBLE);

    }

    private void setViewEnded(DragEvent event) {

        final View view1 = (View) event.getLocalState();
        synchronized (view1) {

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {

                    view1.setVisibility(View.VISIBLE);

                }
            }, 1);

        }
    }

    private void setAndRemoveBitmapInEntered() {

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true).cacheOnDisc(true).considerExifParams(true)
                .displayer(new RoundedBitmapDisplayer(1000)).build();

        Log.i("OUT", " mDragTag " + mDragViewTag);
        /*
         * swipe object in database
         */

        MediaContent mediaLarge = mediaContentPrimaryAL.get(0);
        MediaContent mediaSmall = mediaContentSmallAL.get(mDragViewTag);

        String type = mediaSmall.getType();

        Log.i("TAG", " Key Small " + mediaSmall.getKey());
        Log.i("TAG", " Key Large " + mediaLarge.getKey());

        mediaContentSmallAL.remove(mDragViewTag);
        mediaContentPrimaryAL.remove(0);

        mediaContentSmallAL.add(mDragViewTag, mediaLarge);
        mediaContentPrimaryAL.add(mediaSmall);

        String url = mediaSmall.getUrl();
        if (type.equalsIgnoreCase("image")) {

            iv_my_profile_play_btn.setVisibility(View.INVISIBLE);
            imageLoader.displayImage(url, iv_my_profile_primary_pic);
        } else {

            url = url + ".jpeg";
            iv_my_profile_play_btn.setVisibility(View.VISIBLE);
            imageLoader.displayImage(url, iv_my_profile_primary_pic, options);
        }

        setUIInflateView();
    }

    private void addViewRunTime() {

        ll_my_profile_add_image.removeAllViews();

        for (int i = 0; i < mediaContentSmallAL.size(); i++) {

            Log.i("TAG", " Key " + mediaContentSmallAL.get(i).getKey());

            mLayoutParams.setMargins(15, 0, 15, 0);
            ll_my_profile_add_image.addView(mInflateViewStoreArrayList.get(i),
                    i, mLayoutParams);
            mInflateViewStoreArrayList.get(i).setTag(i);

        }

    }

    @Override
    public boolean onTouch(View mDragView, MotionEvent e) {

        if (e.getAction() == MotionEvent.ACTION_DOWN) {

            mDragViewTag = (Integer.parseInt(mDragView.getTag().toString()));
            DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                    mDragView);
            mDragView.startDrag(null, shadowBuilder, mDragView, 0);
            mDragView.setVisibility(View.INVISIBLE);

            return true;
        }
        return false;
    }

    private void swipeLeftRightLinearLayout(View dropView) {
        if (mediaContentSmallAL.size() > 1) {
            mDropViewTag = (Integer.parseInt(dropView.getTag().toString()));

            Log.i("UPLOAD", "Drag " + mDragViewTag + " , " + mDropViewTag);
            int diff = mDropViewTag - mDragViewTag;
            mDragViewTagDefault = mDropViewTag;

            while (diff != 0) {
                swipeViewLinear();
                if (diff >= 0) {
                    // / right swipe
                    mDropViewTag--;
                    diff--;
                } else {
                    // left swipe
                    mDropViewTag++;
                    diff++;
                }
            }

        }

        addViewRunTime();

        mDragViewTag = mDragViewTagDefault;
    }

    private void swipeViewLinear() {

        if (mediaContentSmallAL.size() != 0) {
            Collections.swap(mediaContentSmallAL, mDropViewTag, mDragViewTag);
        }

        Collections
                .swap(mInflateViewStoreArrayList, mDropViewTag, mDragViewTag);

    }

    @Override
    public boolean onDrag(View dropView, DragEvent event) {

        switch (event.getAction()) {

        case DragEvent.ACTION_DRAG_ENTERED:

            Log.i("MMM", "Drag Enter small ");
            swipeLeftRightLinearLayout(dropView);

            break;

        case DragEvent.ACTION_DRAG_ENDED:

            getApiResponseMediaMove(
                    String.valueOf(prefs.getProfileRegistrationImageKey()),
                    String.valueOf(mDragViewTag));

            if (!dropView.equals(event.getLocalState())) {
                setViewEnded(event);
            }

        }

        return true;
    }

    private void initUi() {
        new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.user_image).cacheInMemory(true)
                .cacheOnDisc(true).considerExifParams(true).build();

        iv_my_profile_primary_pic = (ImageView) view
                .findViewById(R.id.iv_my_profile_primary_pic);

        iv_profile_camera_pickup = (ImageView) view
                .findViewById(R.id.iv_profile_camera_pickup);

        tv_my_username = (TextView) view.findViewById(R.id.tv_my_username);
        tv_my_time_count = (TextView) view.findViewById(R.id.tv_my_time_count);
        tv_my_wink_count = (TextView) view.findViewById(R.id.tv_my_wink_count);
        tv_my_headline = (TextView) view.findViewById(R.id.tv_my_headline);

        dobTextView = (TextView) view.findViewById(R.id.tv_my_dob);
        dobLabelTextView = (TextView) view.findViewById(R.id.tv_my_dob_label);

        userSettingImageView = (ImageView) view
                .findViewById(R.id.iv_my_profile_right_bar_user_settings);

        userLocationImageView = (ImageView) view
                .findViewById(R.id.iv_my_profile_right_bar_user_loc);

        feelingHotImageView = (ImageView) view
                .findViewById(R.id.iv_my_profile_right_bar_user_change_pic);

        /**************************************************/

        upForListView = (ExpandableListView) view
                .findViewById(R.id.fragment_my_profile_up_for_listView);
        upForTextView = (TextView) view
                .findViewById(R.id.fragment_my_profile_up_for_textView);
        upForLabel = (TextView) view
                .findViewById(R.id.fragment_my_profile_up_for_label);

        orientationListView = (ExpandableListView) view
                .findViewById(R.id.fragment_my_profile_orientation_listView);
        orientationTextView = (TextView) view
                .findViewById(R.id.fragment_my_profile_orientation_textView);
        orientationLabel = (TextView) view
                .findViewById(R.id.fragment_my_profile_orientation_label);

        bodyTypeListView = (ExpandableListView) view
                .findViewById(R.id.fragment_my_profile_body_type_listView);
        bodyTypeTextView = (TextView) view
                .findViewById(R.id.fragment_my_profile_body_type_textView);
        bodyTypeLabel = (TextView) view
                .findViewById(R.id.fragment_my_profile_body_type_label);

        temperamentListView = (ExpandableListView) view
                .findViewById(R.id.fragment_my_profile_temperament_listView);
        temperamentTextView = (TextView) view
                .findViewById(R.id.fragment_my_profile_temperament_textView);
        temperamentLabel = (TextView) view
                .findViewById(R.id.fragment_my_profile_temperament_label);

        sizeListView = (ExpandableListView) view
                .findViewById(R.id.fragment_my_profile_size_listView);
        sizeTextView = (TextView) view
                .findViewById(R.id.fragment_my_profile_size_textView);
        sizeLabel = (TextView) view
                .findViewById(R.id.fragment_my_profile_size_label);

        hivStatusListView = (ExpandableListView) view
                .findViewById(R.id.fragment_my_profile_hiv_status_listView);
        hivStatusTextView = (TextView) view
                .findViewById(R.id.fragment_my_profile_hiv_status_textView);
        hivStatusLabel = (TextView) view
                .findViewById(R.id.fragment_my_profile_hiv_status_label);

        heightListView = (ExpandableListView) view
                .findViewById(R.id.fragment_my_profile_height_listView);
        heightTextView = (TextView) view
                .findViewById(R.id.fragment_my_profile_height_textView);
        heightLabel = (TextView) view
                .findViewById(R.id.fragment_my_profile_height_label);

        drinkListView = (ExpandableListView) view
                .findViewById(R.id.fragment_my_profile_drink_listView);
        drinkTextView = (TextView) view
                .findViewById(R.id.fragment_my_profile_drink_textView);
        drinkLabel = (TextView) view
                .findViewById(R.id.fragment_my_profile_drink_label);

        eyeColorListView = (ExpandableListView) view
                .findViewById(R.id.fragment_my_profile_eye_color_listView);
        eyeColorTextView = (TextView) view
                .findViewById(R.id.fragment_my_profile_eye_color_textView);
        eyeColorLabel = (TextView) view
                .findViewById(R.id.fragment_my_profile_eye_color_label);

        roleListView = (ExpandableListView) view
                .findViewById(R.id.fragment_my_profile_role_listView);
        roleTextView = (TextView) view
                .findViewById(R.id.fragment_my_profile_role_textView);
        roleLabel = (TextView) view
                .findViewById(R.id.fragment_my_profile_role_label);

        personaListView = (ExpandableListView) view
                .findViewById(R.id.fragment_my_profile_persona_listView);
        personaTextView = (TextView) view
                .findViewById(R.id.fragment_my_profile_persona_textView);
        personaLabel = (TextView) view
                .findViewById(R.id.fragment_my_profile_persona_label);

        bodyHairListView = (ExpandableListView) view
                .findViewById(R.id.fragment_my_profile_body_hair_listView);
        bodyHairTextView = (TextView) view
                .findViewById(R.id.fragment_my_profile_body_hair_textView);
        bodyHairLabel = (TextView) view
                .findViewById(R.id.fragment_my_profile_body_hair_label);

        cutListView = (ExpandableListView) view
                .findViewById(R.id.fragment_my_profile_cut_listView);
        cutTextView = (TextView) view
                .findViewById(R.id.fragment_my_profile_cut_textView);
        cutLabel = (TextView) view
                .findViewById(R.id.fragment_my_profile_cut_label);

        ethnicityListView = (ExpandableListView) view
                .findViewById(R.id.fragment_my_profile_ethnicity_listView);
        ethnicityTextView = (TextView) view
                .findViewById(R.id.fragment_my_profile_ethnicity_textView);
        ethnicityLabel = (TextView) view
                .findViewById(R.id.fragment_my_profile_ethnicity_label);

        outToListView = (ExpandableListView) view
                .findViewById(R.id.fragment_my_profile_out_to_listView);
        outToTextView = (TextView) view
                .findViewById(R.id.fragment_my_profile_out_to_textView);
        outToLabel = (TextView) view
                .findViewById(R.id.fragment_my_profile_out_to_label);

        smokeListView = (ExpandableListView) view
                .findViewById(R.id.fragment_my_profile_smoke_listView);
        smokeTextView = (TextView) view
                .findViewById(R.id.fragment_my_profile_smoke_textView);
        smokeLabel = (TextView) view
                .findViewById(R.id.fragment_my_profile_smoke_label);

        hairColorListView = (ExpandableListView) view
                .findViewById(R.id.fragment_my_profile_hair_color_listView);
        hairColorTextView = (TextView) view
                .findViewById(R.id.fragment_my_profile_hair_color_textView);
        hairColorLabel = (TextView) view
                .findViewById(R.id.fragment_my_profile_hair_color_label);

        scrollView = (ScrollView) view
                .findViewById(R.id.fragment_my_profile_root_scrollView);

        feelingHotImageView.setSelected(prefs.isHotEnabled());

    }

    private void setUiListener() {

        iv_profile_camera_pickup.setOnClickListener(this);
        userSettingImageView.setOnClickListener(this);
        feelingHotImageView.setOnClickListener(this);
        userLocationImageView.setOnClickListener(this);

        dobLabelTextView.setOnClickListener(this);
        dobTextView.setOnClickListener(this);
    }

    private void setOtherListener() {

        onHotIconClick = new OnHotIconClick() {

            @Override
            public void isTickClicked() {

                feelingHotImageView.setSelected(true);
                ((AvidFragmentBaseActivity) getActivity())
                        .getPreferenceObject().setStatus(true);
                hotUnhotApi(1);
            }

            @Override
            public void isCrossClicked() {
                PreferenceKeeper.getInstance(getActivity()).setStatus(false);
                feelingHotImageView.setSelected(false);
            }
        };

        onDeleteImageClick = new OnDeleteImageClick() {

            @Override
            public void isTickClicked() {

                int res = helper.deleteRowItems(
                        AvidCPHelper.Uris.URI_MEDIA_CONTENT,
                        mediaContentSmallAL.get(crossImageIndex).getKey());
                mediaContentSmallAL.remove(crossImageIndex);

                setUIInflateView();
                mediaDeleteApi(ApiName.mediaDelete);

            }

            @Override
            public void isCrossClicked() {

            }
        };
    }

    private void setImageInArrayList(CapturedData capturedData) {

        if (mediaContentPrimaryAL.size() > 0) {
            if (capturedData.isImage()) {

                String mUrl = "http://104.237.131.161:3001/me/pic/upload/";
                callImageVideoUploadApi(mUrl, capturedData.getFilePath(), true);
            } else {

                String mUrl = "http://104.237.131.161:3001/me/video/upload/";
                callImageVideoUploadApi(mUrl, capturedData.getFilePath(), false);
            }

        }

    }

    private void onOpenCamera() {
        ImageVideoData imageVideoData = new ImageVideoData() {

            @Override
            public void onDataReceive(CapturedData capturedData) {
                setImageInArrayList(capturedData);

            }
        };

        replaceFragment(R.id.activity_avid_base_no_footer,
                AppConstant.FragmentStackName.CAMERA_OPEN_FRAGMENT, "S1",
                new CameraOpenFragment(imageVideoData, true), 0, 0, 0, 0, true,
                false);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        ((AvidFragmentBaseActivity) getActivity()).skipPasscode = true;
        try {

            if (resultCode == Activity.RESULT_OK) {

                switch (requestCode) {

                case AppConstant.CameraCaptureConstants.REQ_CODE_GALLERY_IMAGE_AND_VIDEO_PICK:
                    // Bitmap photo = (Bitmap) data.getExtras().get("data");
                    // mBitmapStoreArrayList.add(photo);
                    // setUIInflateView();

                    Uri selectedImage = data.getData();

                    String[] filePathColumn = { MediaColumns.DATA };
                    Cursor cursor = getActivity().getContentResolver().query(
                            selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    filePath = cursor.getString(columnIndex);
                    cursor.close();
                    if (Utils.isImageFile(filePath)) {
                        sendImageToCrop(filePath);
                    } else {
                        File imgFile = new File(filePath);
                        if (imgFile.exists()) {
                            CapturedData capturedVideo = new CapturedData();
                            capturedVideo.setFilePath(filePath);
                            Bitmap bMapSmall = ThumbnailUtils
                                    .createVideoThumbnail(
                                            filePath,
                                            MediaStore.Video.Thumbnails.MICRO_KIND);
                            Bitmap bMapLarge = ThumbnailUtils
                                    .createVideoThumbnail(
                                            filePath,
                                            MediaStore.Video.Thumbnails.FULL_SCREEN_KIND);
                            capturedVideo.setSmallImage(Utils
                                    .getCircularBitmap(bMapSmall));
                            capturedVideo.setLargeImage(Utils
                                    .getCircularBitmap(bMapLarge));
                            capturedVideo.setImage(false);

                            setImageInArrayList(capturedVideo);

                        }
                    }
                    break;
                case AppConstant.CameraCaptureConstants.CROP_RESULT_CODE:
                    try {
                        filePath = data.getStringExtra(CropImage.IMAGE_PATH);
                        if (filePath == null) {
                            return;
                        }
                        File imgFile = new File(filePath);
                        if (imgFile.exists()) {
                            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile
                                    .getAbsolutePath());

                            CapturedData capturedImage = new CapturedData();
                            capturedImage.setFilePath(filePath);
                            capturedImage.setLargeImage(myBitmap);
                            capturedImage.setSmallImage(ThumbnailUtils
                                    .extractThumbnail(myBitmap, 100, 100));
                            capturedImage.setImage(true);

                            setImageInArrayList(capturedImage);

                        }
                        // callImageUploadApi();
                    } catch (NullPointerException e) {
                    }
                    break;
                default:
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendImageToCrop(String filePath2) {
        Intent intent = new Intent(getActivity(), CropImage.class);
        intent.putExtra(CropImage.IMAGE_PATH, filePath);
        intent.putExtra(CropImage.SCALE, true);
        intent.putExtra(CropImage.CIRCLE_CROP, "");
        startActivityForResult(intent,
                AppConstant.CameraCaptureConstants.CROP_RESULT_CODE);
    }

    private void onOpenVideo() {

        ImageVideoManager.ImageVideoData video = new ImageVideoManager.ImageVideoData() {

            @Override
            public void onDataReceive(CapturedData capturedData) {

                setImageInArrayList(capturedData);

            }
        };

        replaceFragment(R.id.activity_avid_base_no_footer,
                AppConstant.FragmentStackName.CAMERA_OPEN_FRAGMENT, "S1",
                new CameraOpenFragment(video, false), 0, 0, 0, 0, true, false);

    }

    private void onOpenGallary() {

        if (Environment.getExternalStorageState().equals("mounted")) {
            Intent intent = new Intent();

            intent.setType("image/* video/*");
            intent.setAction(Intent.ACTION_PICK);

            intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT,
                    AppConstant.VIDEO_CAPTURE_LIMIT);

            startActivityForResult(
                    intent,
                    AppConstant.CameraCaptureConstants.REQ_CODE_GALLERY_IMAGE_AND_VIDEO_PICK);
        }
    }

    // public Loader<Cursor> onCreateLoader(int id, Bundle args) {
    // CursorLoader cursorLoader = new CursorLoader(getActivity(),
    // AvidCPHelper.Uris.URI_BROWSE_USER, null,
    // BrowseContract.BrowseEntry.COLUMN_NAME_UID + "=1", null, null);
    //
    // return cursorLoader;
    // }
    //
    // @Override
    // public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
    // AvidCPHelper helper = AvidCPHelper.getInstance(getActivity());
    //
    // List<User> browseUsers = helper.getBrowseUsersFromCursor(data);
    //
    // imageLoader = ImageLoader.getInstance();
    // options = new DisplayImageOptions.Builder()
    // .showImageOnLoading(R.drawable.user_image).cacheInMemory(true)
    // .cacheOnDisc(true).considerExifParams(true).build();
    //
    // if (browseUsers.size() > 0) {
    //
    // showDataInUi(browseUsers);
    // }
    //
    // }
    //
    // @Override
    // public void onLoaderReset(Loader<Cursor> loader) {
    //
    // }

    private void showDataInUi(User user) {

        this.user = user;
        // imageLoader.displayImage(user.getuImg(), iv_my_profile_primary_pic,
        // / options);
        tv_my_username.setText(user.getuName());
        // tv_my_time_count;
        tv_my_wink_count.setText("" + user.getWinkCount());
        tv_my_headline.setText(user.getProfileDesc());
        // tv_my_dob
    }

    private void showDataInUi(Profile profile) {
        try {
            // imageLoader.displayImage(profile.getPics().get(0).getUrl(),
            // iv_my_profile_primary_pic, options);

            tv_my_username.setText(profile.getName());
            // tv_my_time_count;

            tv_my_wink_count.setText("" + 15);
            tv_my_headline.setText("");
            // tv_my_dob

            feelingHotImageView.setSelected(prefs.isHotEnabled());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
        case R.id.iv_my_profile_right_bar_user_settings:
            // MapUserFragment.isMarkerClick = false;
            replaceFragment(R.id.base_fragment_body_layout, "M1", "createpost",
                    new SettingsFragment(), R.anim.animation_up, 0, 0, 0, true,
                    false);

            break;

        case R.id.iv_my_profile_cross_pic:
            crossImageIndex = Integer.parseInt(v.getTag().toString());
            Log.i("UPLOAD", "crossImageIndex " + crossImageIndex);

            replaceFragment(
                    R.id.activity_avid_base_no_footer,
                    AppConstant.FragmentStackName.CONFIRM_IMAGE_DELETE_FRAGMENT,
                    "S1", new ConfirmImageDeleteFragment(onDeleteImageClick),
                    R.anim.animation_up, R.anim.animation_down,
                    R.anim.animation_up, R.anim.animation_down, true, false);

            break;

        case R.id.iv_my_profile_right_bar_user_change_pic:
            if (PreferenceKeeper.getInstance(getActivity()).isHotEnabled()) {
                feelingHotImageView.setSelected(false);
                ((AvidFragmentBaseActivity) getActivity())
                        .getPreferenceObject().setStatus(false);
                hotUnhotApi(0);
            } else {
                replaceFragment(
                        R.id.activity_avid_base_no_footer,
                        AppConstant.FragmentStackName.CONFIRM_FLAME_TOGGLE_FRAGMENT,
                        "S1", new ConfirmFlameToggleFragment(onHotIconClick),
                        0, 0, 0, 0, true, false);
            }
            break;

        case R.id.iv_profile_camera_pickup:
            String options[] = new String[] { "Click an image",
                    "Record a video", "Upload from Gallery", "Cancel" };

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Choose an option");

            builder.setItems(options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // the user clicked on options[which]
                    switch (which) {
                    case 0:
                        onOpenCamera();
                        break;
                    case 1:
                        onOpenVideo();
                        break;
                    case 2:
                        onOpenGallary();
                        break;
                    default:
                        break;
                    }
                }
            });

            builder.show();

            break;

        case R.id.tv_my_dob:
        case R.id.tv_my_dob_label:
            launchDatePicker();
            break;
        case R.id.iv_my_profile_right_bar_user_loc:
            // replaceFragment(R.id.activity_avid_base_no_footer,
            // IAppConstant.FragmentStackName.CREATE_PROFILE_FRAGMENT,
            // "S1", new CreateProfileFragment(), 0, 0, 0, 0, true, false);
            break;
        default:
            break;
        }

    }

    private void setUIAdapters() {

        if (user.getMetaData() == null) {
            user.setMetaData(new MetaData());
        }

        setAdapter(upForListView, upForLabel, upForTextView, getActivity()
                .getResources().getStringArray(R.array.profile_up_for_array),
                user.getMetaData().getUp_for(), new SelectionChangeListener() {
                    @Override
                    public void onSelectionChanged(String value) {
                        user.getMetaData().setUp_for(value);
                    }
                });

        setAdapter(
                orientationListView,
                orientationLabel,
                orientationTextView,
                getActivity().getResources().getStringArray(
                        R.array.profile_orientation_array), user.getMetaData()
                        .getOrientation(), new SelectionChangeListener() {
                    @Override
                    public void onSelectionChanged(String value) {
                        user.getMetaData().setOrientation(value);
                    }
                });

        setAdapter(
                bodyTypeListView,
                bodyTypeLabel,
                bodyTypeTextView,
                getActivity().getResources().getStringArray(
                        R.array.profile_body_type_array), user.getMetaData()
                        .getBody_type(), new SelectionChangeListener() {
                    @Override
                    public void onSelectionChanged(String value) {
                        user.getMetaData().setBody_type(value);
                    }
                });

        setAdapter(
                temperamentListView,
                temperamentLabel,
                temperamentTextView,
                getActivity().getResources().getStringArray(
                        R.array.profile_temperament_array), user.getMetaData()
                        .getTemperament(), new SelectionChangeListener() {
                    @Override
                    public void onSelectionChanged(String value) {
                        user.getMetaData().setTemperament(value);
                    }
                });

        setAdapter(sizeListView, sizeLabel, sizeTextView, getActivity()
                .getResources().getStringArray(R.array.profile_size_array),
                user.getMetaData().getSize(), new SelectionChangeListener() {
                    @Override
                    public void onSelectionChanged(String value) {
                        user.getMetaData().setSize(value);
                    }
                });

        setAdapter(
                hivStatusListView,
                hivStatusLabel,
                hivStatusTextView,
                getActivity().getResources().getStringArray(
                        R.array.profile_hiv_status_array), user.getMetaData()
                        .getHiv_status(), new SelectionChangeListener() {
                    @Override
                    public void onSelectionChanged(String value) {
                        user.getMetaData().setHiv_status(value);
                    }
                });

        setAdapter(heightListView, heightLabel, heightTextView, getActivity()
                .getResources().getStringArray(R.array.profile_height_array),
                user.getMetaData().getHeight(), new SelectionChangeListener() {
                    @Override
                    public void onSelectionChanged(String value) {
                        user.getMetaData().setHeight(value);
                    }
                });

        setAdapter(drinkListView, drinkLabel, drinkTextView, getActivity()
                .getResources().getStringArray(R.array.profile_drink_array),
                user.getMetaData().getDrink(), new SelectionChangeListener() {
                    @Override
                    public void onSelectionChanged(String value) {
                        user.getMetaData().setDrink(value);
                    }
                });

        setAdapter(
                eyeColorListView,
                eyeColorLabel,
                eyeColorTextView,
                getActivity().getResources().getStringArray(
                        R.array.profile_eye_color_array), user.getMetaData()
                        .getEye_color(), new SelectionChangeListener() {
                    @Override
                    public void onSelectionChanged(String value) {
                        user.getMetaData().setEye_color(value);
                    }
                });
        setAdapter(roleListView, roleLabel, roleTextView, getActivity()
                .getResources().getStringArray(R.array.profile_role_array),
                user.getMetaData().getRole(), new SelectionChangeListener() {
                    @Override
                    public void onSelectionChanged(String value) {
                        user.getMetaData().setRole(value);
                    }
                });

        setAdapter(
                personaListView,
                personaLabel,
                personaTextView,
                getActivity().getResources().getStringArray(
                        R.array.profile_persona_array), user.getMetaData()
                        .getPersona(), new SelectionChangeListener() {
                    @Override
                    public void onSelectionChanged(String value) {
                        user.getMetaData().setPersona(value);
                    }
                });

        setAdapter(
                bodyHairListView,
                bodyHairLabel,
                bodyHairTextView,
                getActivity().getResources().getStringArray(
                        R.array.profile_body_hair_array), user.getMetaData()
                        .getBody_hair(), new SelectionChangeListener() {
                    @Override
                    public void onSelectionChanged(String value) {
                        user.getMetaData().setBody_hair(value);
                    }
                });

        setAdapter(cutListView, cutLabel, cutTextView, getActivity()
                .getResources().getStringArray(R.array.profile_cut_array), user
                .getMetaData().getCut(), new SelectionChangeListener() {
            @Override
            public void onSelectionChanged(String value) {
                user.getMetaData().setCut(value);
            }
        });

        setAdapter(
                ethnicityListView,
                ethnicityLabel,
                ethnicityTextView,
                getActivity().getResources().getStringArray(
                        R.array.profile_ethnicity_array), user.getMetaData()
                        .getEthnicity(), new SelectionChangeListener() {
                    @Override
                    public void onSelectionChanged(String value) {
                        user.getMetaData().setEthnicity(value);
                    }
                });

        setAdapter(outToListView, outToLabel, outToTextView, getActivity()
                .getResources().getStringArray(R.array.profile_out_to_array),
                user.getMetaData().getOut_to(), new SelectionChangeListener() {
                    @Override
                    public void onSelectionChanged(String value) {
                        user.getMetaData().setOut_to(value);
                    }
                });

        setAdapter(smokeListView, smokeLabel, smokeTextView, getActivity()
                .getResources().getStringArray(R.array.profile_smoke_array),
                user.getMetaData().getSmoke(), new SelectionChangeListener() {
                    @Override
                    public void onSelectionChanged(String value) {
                        user.getMetaData().setSmoke(value);
                    }
                });

        setAdapter(
                hairColorListView,
                hairColorLabel,
                hairColorTextView,
                getActivity().getResources().getStringArray(
                        R.array.profile_hair_color_array), user.getMetaData()
                        .getHair_color(), new SelectionChangeListener() {
                    @Override
                    public void onSelectionChanged(String value) {
                        user.getMetaData().setHair_color(value);
                    }
                });
    }

    private void setAdapter(final ExpandableListView listView,
            final TextView label, final TextView headerTextView,
            final String[] stringArray, String headerName,
            SelectionChangeListener selectionChangeListener) {

        listView.setAdapter(new ProfileExpandableAdapter(getActivity(),
                headerTextView, listView, createList(stringArray, headerName),
                selectionChangeListener));
        listView.setGroupIndicator(null);

        headerTextView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (listView.isGroupExpanded(0)) {
                    listView.collapseGroup(0);
                } else {
                    listView.expandGroup(0);
                }
            }
        });

        label.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (listView.isGroupExpanded(0)) {
                    listView.collapseGroup(0);
                } else {
                    listView.expandGroup(0);
                }
            }
        });

        listView.setOnGroupExpandListener(new OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                int height = Utils.getListViewHeightBasedOnChildren(listView);
                LinearLayout.LayoutParams param = (LinearLayout.LayoutParams) listView
                        .getLayoutParams();
                param.height = height;
                listView.setLayoutParams(param);

                listView.refreshDrawableState();
                scrollView.refreshDrawableState();
                headerTextView.setVisibility(View.GONE);
            }
        });

        // Listview Group collasped listener
        listView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                LinearLayout.LayoutParams param = (LinearLayout.LayoutParams) listView
                        .getLayoutParams();
                param.height = LayoutParams.WRAP_CONTENT;
                listView.setLayoutParams(param);
                listView.refreshDrawableState();
                scrollView.refreshDrawableState();
                headerTextView.setVisibility(View.VISIBLE);

                String lableName = label.getText().toString();
                if (lableName.contains("Up For")) {
                    callEditProfileApi("up_for", headerTextView.getText()
                            .toString());
                } else {
                    callEditProfileApi(lableName, headerTextView.getText()
                            .toString());
                }
            }
        });
    }

    private ExpandableFilter createList(String[] strings, String headerName) {
        ExpandableFilter filterItem = new ExpandableFilter();

        if (headerName != null && !headerName.equals("")) {
            filterItem.setParent(headerName);
        } else {
            filterItem.setParent(getString(R.string.text_not_specified));
        }

        ArrayList<FilterOptions> filterOptions = new ArrayList<FilterOptions>();
        FilterOptions option;
        for (String string : strings) {
            option = new FilterOptions();
            option.setChecked(false);
            option.setFilterOptionName(string);
            filterOptions.add(option);
        }
        filterItem.setChildren(filterOptions);
        return filterItem;
    }

    public interface SelectionChangeListener {
        public void onSelectionChanged(String string);
    }

    /**
     * make a sigleton instance of calender.
     */
    private void getCalenderInstance() {
        if (dob == null) {
            dob = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        }
    }

    /**
     * Open date picker dialog.
     */
    private void launchDatePicker() {
        getCalenderInstance();
        if (datePickerDialog == null) {
            datePickerDialog = new DatePickerDialog(getActivity(),
                    new OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                int monthOfYear, int dayOfMonth) {
                            dob.set(Calendar.YEAR, year);
                            dob.set(Calendar.MONTH, monthOfYear);
                            dob.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                            dobTextView.setText(Utils.getFullDate(dob
                                    .getTimeInMillis()));

                            AvidApiClient client = AvidApiClient.getInstance();
                            ProfileEditInput input = new ProfileEditInput();
                            input.setDob(dob.getTimeInMillis());
                            client.post(0, AvidUrls.BASE_URL
                                    + AvidUrls.PROFILE_EDIT, input.getParams(),
                                    getHeaders(), ApiName.editProfile,
                                    new ProfileEditOutput(),
                                    getApiResponseHandler());

                            KEY = AppConstant.PrefrenceData.Profile.DOB;
                        }
                    }, dob.get(Calendar.YEAR), dob.get(Calendar.MONTH),
                    dob.get(Calendar.DAY_OF_MONTH));

            Calendar maxDate = Calendar.getInstance();
            maxDate.set(Calendar.YEAR, (maxDate.get(Calendar.YEAR) - 18));

            datePickerDialog.getDatePicker().setMaxDate(
                    maxDate.getTimeInMillis());
        }
        datePickerDialog.updateDate(dob.get(Calendar.YEAR),
                dob.get(Calendar.MONTH), dob.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
            int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    // TODO for media move

    private void getApiResponseMediaMove(String key, String position) {

        AvidApiClient mAvidApiClient = AvidApiClient.getInstance();

        mAvidApiClient.get(1, AvidUrls.BASE_URL + AvidUrls.MEDIA_MOVE + "/"
                + key + "/" + position, null, getHeaders(),
                ApiName.profile_media_move, new ProfileMediaMoveOutput(),
                getApiResponseHandler());
    }

    @Override
    protected void apiSuccessResponse(int reqId, ApiOutput output, ApiName type) {
        hideProgressBar();
        switch (type) {
        case profile_media_move:
            ProfileMediaMoveOutput out = (ProfileMediaMoveOutput) output;
            System.out.println("Status code " + out.getStatusCode());
            break;

        case editProfile:
            if (KEY != null
                    && KEY.equals(AppConstant.PrefrenceData.Profile.DOB)) {
                prefs.setUserDOB(dob.getTimeInMillis());
                KEY = null;
            }
            break;
        case mediaDelete:
            if (((MediaDeleteOutput) output).isResult()) {
                setUIInflateView();
            }
            break;
        case hot:

            if (((HotUnhotOutput) output).getStatusCode().equalsIgnoreCase("1")) {

                PreferenceKeeper.getInstance(getActivity()).setStatus(true);

            } else if (((HotUnhotOutput) output).getStatusCode()
                    .equalsIgnoreCase("0")) {

                feelingHotImageView.setSelected(false);
            }

            break;
        case Unhot:
            if (((HotUnhotOutput) output).getStatusCode().equalsIgnoreCase("1")) {
                PreferenceKeeper.getInstance(getActivity()).setStatus(false);

            } else if (((HotUnhotOutput) output).getStatusCode()
                    .equalsIgnoreCase("0")) {
                feelingHotImageView.setSelected(true);
            }
            break;

        default:
            break;
        }
    }

    @Override
    protected void apiFailedResponse(int reqId, ErrorObject errorObject,
            ApiName type) {
        switch (type) {
        case editProfile:
            if (KEY != null
                    && KEY.equals(AppConstant.PrefrenceData.Profile.DOB)) {
                showTaost(errorObject.getErrorMessage());
                KEY = null;
            }
            break;

        default:
            break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        Log.i("OUT", " On Pause ");
        prefs.setUserMetaData(user.getMetaData());
        // callEditMetaDataApi();

        helper.deleteContentProviderData(AvidCPHelper.Uris.URI_MEDIA_CONTENT,
                null, null);

        Log.i("OUT", " Size " + mediaContentSmallAL.size());

        helper.bulkInsertMediaContent(mediaContentPrimaryAL);
        helper.bulkInsertMediaContent(mediaContentSmallAL);
        mediaContentSmallAL.clear();
        mediaContentPrimaryAL.clear();
    }

    // /*
    // * Used to upload image and video in profile fragment
    // */
    //
    // private class ImageUploadAsync extends
    // AsyncTask<Void, Void, MediaContentOutput> {
    //
    // File file;
    // String mUrl;
    //
    // ImageUploadAsync(File file, String mUrl) {
    //
    // this.file = file;
    // this.mUrl = mUrl;
    //
    // }
    //
    // @Override
    // protected MediaContentOutput doInBackground(Void... params) {
    //
    // String response = MultiPartHttpClient.uploadPhoto(file, mUrl,
    // String.valueOf(mediaContentSmallAL.size()), "Profile",
    // ((AvidBaseActivity) getActivity()).getPreferenceObject()
    // .getProfileCookieValue(), type,
    // getApiResponseHandler());
    //
    // Log.i("TAG", "ress " + response);
    //
    // return parseRegisterResponse(response);
    // }
    //
    // @Override
    // protected void onPostExecute(MediaContentOutput result) {
    // super.onPostExecute(result);
    //
    // if (result != null && result.getStatusCode() == 1) {
    // showTaost("Successfully Image Upload .");
    //
    // mediaContentSmallAL.add(result.getResult());
    //
    // setUIInflateView();
    //
    // } else {
    // showTaost("Some error occured. Please try again.");
    // }
    //
    // }
    // }

    private void callImageVideoUploadApi(String url, String filePath,
            boolean type) {

        this.type = type;
        showProgressBar();

        String mUrl = url + mediaContentSmallAL.size();
        File file = new File(filePath);
        initializeBaseApiResponseHandler();

        // new ImageUploadAsync(file, mUrl).execute();
    }

    public MediaContentOutput parseRegisterResponse(final String result) {

        Gson gson = new Gson();

        try {

            MediaContentOutput output = gson.fromJson(result,
                    MediaContentOutput.class);

            return output;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    private void mediaContentDataUpdate() {
        Cursor cursor = helper.getCursorB(AvidCPHelper.Uris.URI_MEDIA_CONTENT,
                null, null, null, null);

        DisplayImageOptions options;

        final List<MediaContent> mediaContent = helper
                .getMediaContentFRomCursor(cursor);

        Log.i("OUT", " " + mediaContent.size());

        mediaContentSmallAL.clear();
        mediaContentPrimaryAL.clear();

        for (int i = 0; i < mediaContent.size(); i++) {
            Log.i("OUT", " " + mediaContent.get(i).getType());

            typeImageOrVideo = mediaContent.get(i).getType();

            if (typeImageOrVideo.equalsIgnoreCase("image")) {
                imageOrVideoUrl = mediaContent.get(i).getUrl();
                options = getOptionImageLoader();
            } else {
                mediaContent.get(i).getUrl();
                imageOrVideoUrl = mediaContent.get(i).getUrl() + ".jpeg";
                options = videoOptions;
            }

            if (i == 0) {
                imageLoader.displayImage(imageOrVideoUrl,
                        iv_my_profile_primary_pic, options);
                mediaContentPrimaryAL.add(mediaContent.get(i));
            } else {
                mediaContentSmallAL.add(mediaContent.get(i));
            }

        }

        if (mediaContentPrimaryAL.get(0).getType().equalsIgnoreCase("image")) {

            iv_my_profile_play_btn.setVisibility(View.INVISIBLE);
        } else {

            iv_my_profile_play_btn.setVisibility(View.VISIBLE);
        }
        setUIInflateView();
    }

    private void manageProfileData() {
        showDataInUi(PreferenceKeeper.getInstance(getActivity()).getUserData());

        setUIAdapters();
        getProfileDataFromServer();
    }

    private void callEditProfileApi(String filterName, String value) {
        initializeBaseApiResponseHandler();

        AvidApiClient client = AvidApiClient.getInstance();

        filterName = filterName.toLowerCase().replace(" ", "_");

        JSONObject json = new JSONObject();
        try {
            json.put(filterName, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String metadata = json.toString();
        ProfileEditInput input = new ProfileEditInput();
        input.setMetaData(metadata);

        client.post(0, AvidUrls.BASE_URL + AvidUrls.PROFILE_EDIT,
                input.getParams(), getHeaders(), ApiName.editProfile,
                new ProfileEditOutput(), getApiResponseHandler());

    }

    private void getProfileDataFromServer() {
        AppHttpRequest request = AppRequestBuilder
                .getMyProfile(new AppResponseListener<Profile>(Profile.class) {

                    @Override
                    public void onSuccess(Profile t) {
                        saveUserData(t);
                        showDataInUi(t);
                    }

                    @Override
                    public void onError(ErrorObject error) {
                        showTaost("Error in fetching the profile data");
                    }
                });

        AppRestClient.getClient().sendRequest(request, null);
    }

    private void hotUnhotApi(final int flag) {
        AppHttpRequest request = AppRequestBuilder.getHotUnhot(flag,
                new AppResponseListener<String>(String.class) {

                    @Override
                    public void onSuccess(String t) {
                        Log.i("mHot", t);
                    }
                    
                    @Override
                    public void onError(ErrorObject error) {
                        if (flag == 1) {
                            feelingHotImageView.setSelected(false);
                            ((AvidFragmentBaseActivity) getActivity())
                                    .getPreferenceObject().setStatus(false);
                        } else if (flag == 0) {
                            feelingHotImageView.setSelected(true);
                            ((AvidFragmentBaseActivity) getActivity())
                                    .getPreferenceObject().setStatus(true);
                        }

                    }
                });
        AppRestClient.getClient().sendRequest(request, TAG);
    }

    private void mediaDeleteApi(ApiName type) {
        // TODO
        initializeBaseApiResponseHandler();
        AvidApiClient mMediaDelete = AvidApiClient.getInstance();
        mMediaDelete.get(1, AvidUrls.BASE_URL + AvidUrls.MEDIA_DELETE
                + crossImageIndex, null, getHeaders(), type,
                new MediaDeleteOutput(), getApiResponseHandler());

    }

    // private void callEditMetaDataApi() {
    // initializeBaseApiResponseHandler();
    //
    // AvidApiClient client = AvidApiClient.getInstance();
    //
    // ProfileEditInput input = new ProfileEditInput();
    // input.setMetaData(new Gson().toJson(user.getMetaData()).toString());
    //
    // client.post(0, AvidUrls.BASE_URL + AvidUrls.PROFILE_EDIT,
    // input.getParams(), getHeaders(), ApiName.editProfile,
    // new ProfileEditOutput(), getApiResponseHandler());
    //
    // }
}
