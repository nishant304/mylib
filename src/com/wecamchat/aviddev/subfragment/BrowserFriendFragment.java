package com.wecamchat.aviddev.subfragment;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.wecamchat.aviddev.R;
import com.wecamchat.aviddev.constant.AppConstant;
import com.wecamchat.aviddev.dataadapter.DataAdapter;
import com.wecamchat.aviddev.fragment.AvidBaseFragment;
import com.wecamchat.aviddev.model.bo.User;
import com.wecamchat.aviddev.util.PreferenceKeeper;
import com.wecamchat.aviddev.util.view.SimpleViewPagerIndicator;

@SuppressLint("ValidFragment")
public class BrowserFriendFragment extends AvidBaseFragment {

    public static boolean setFriendFlag;
    private View view;
    private User user;
    private ViewPager userImagePager;
    private ArrayList<RadioButton> pageIndicatorList = null;

    // ImageView iv_user_profile_pic;
    ImageView iv_user_profile_headlinebar_fav;
    ImageView iv_user_profile_headlinebar_friend;

    private TextView tv_friend_username, tv_user_wink_count, tv_user_headline,
            tv_user_last_seen_time, tv_user_distance, tv_user_age,
            tv_user_orientation, tv_user_body_type, tv_user_temperment,
            tv_user_size, tv_user_hiv_status, tv_user_up_for, tv_user_role,
            tv_user_persona, tv_user_body_hair, tv_user_cut, tv_user_eye_color,
            tv_user_drink, tv_user_height, tv_user_ethnicity, tv_user_out_to,
            tv_user_smoke, tv_user_hair_color;
    private ImageLoader imageLoader;
    PreferenceKeeper prefs;
    private SimpleViewPagerIndicator dotListIndicator;

    public BrowserFriendFragment(User user) {
        if (user != null) {
            this.user = user;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        try {
            setFriendFlag = true;
            prefs = PreferenceKeeper.getInstance(getActivity());
            if (prefs.getHandPrefrence()) {
                view = inflater.inflate(
                        R.layout.layout_base_lh_friends_profile, container,
                        false);
            } else {
                view = inflater.inflate(R.layout.layout_base_friends_profile,
                        container, false);

            }

            userImagePager = (ViewPager) view
                    .findViewById(R.id.layout_friend_profile_user_image_viewPager);
            // iv_user_profile_pic = (ImageView) view
            // .findViewById(R.id.layout_friend_profile_user_image_viewPager);

            // iv_user_profile_pic.setOnTouchListener(this);
            if (user != null) {
                initUi();
                registerListner();
            }

            return view;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void registerListner() {
        iv_user_profile_headlinebar_fav.setOnClickListener(this);
        iv_user_profile_headlinebar_friend.setOnClickListener(this);
    }

    private void initUi() {

        imageLoader = ImageLoader.getInstance();

        tv_friend_username = (TextView) view
                .findViewById(R.id.tv_friend_username);
        tv_user_wink_count = (TextView) view
                .findViewById(R.id.tv_user_wink_count);
        tv_user_headline = (TextView) view.findViewById(R.id.tv_user_headline);
        tv_user_last_seen_time = (TextView) view
                .findViewById(R.id.tv_user_last_seen_time);
        tv_user_distance = (TextView) view.findViewById(R.id.tv_user_distance);
        tv_user_age = (TextView) view.findViewById(R.id.tv_user_age);
        tv_user_orientation = (TextView) view
                .findViewById(R.id.tv_user_orientation);
        tv_user_body_type = (TextView) view
                .findViewById(R.id.tv_user_body_type);
        tv_user_temperment = (TextView) view
                .findViewById(R.id.tv_user_temperment);
        tv_user_size = (TextView) view.findViewById(R.id.tv_user_size);
        tv_user_hiv_status = (TextView) view
                .findViewById(R.id.tv_user_hiv_status);
        tv_user_up_for = (TextView) view.findViewById(R.id.tv_user_up_for);
        tv_user_role = (TextView) view.findViewById(R.id.tv_user_role);
        tv_user_persona = (TextView) view.findViewById(R.id.tv_user_persona);
        tv_user_body_hair = (TextView) view
                .findViewById(R.id.tv_user_body_hair);
        tv_user_cut = (TextView) view.findViewById(R.id.tv_user_cut);
        tv_user_eye_color = (TextView) view
                .findViewById(R.id.tv_user_eye_color);
        tv_user_drink = (TextView) view.findViewById(R.id.tv_user_drink);
        tv_user_height = (TextView) view.findViewById(R.id.tv_user_height);
        tv_user_ethnicity = (TextView) view
                .findViewById(R.id.tv_user_ethnicity);
        tv_user_out_to = (TextView) view.findViewById(R.id.tv_user_out_to);
        tv_user_smoke = (TextView) view.findViewById(R.id.tv_user_smoke);
        tv_user_hair_color = (TextView) view
                .findViewById(R.id.tv_user_hair_color);
        iv_user_profile_headlinebar_fav = (ImageView) view
                .findViewById(R.id.iv_user_profile_headlinebar_fav);
        iv_user_profile_headlinebar_friend = (ImageView) view
                .findViewById(R.id.iv_user_profile_headlinebar_friend);

        // iv_user_profile_pic = (ImageView) view
        // .findViewById(R.id.layout_friend_profile_user_image_viewPager);

        tv_friend_username.setText(user.getuName());
        tv_user_wink_count.setText("" + user.getWinkCount());
        tv_user_headline.setText(user.getProfileDesc());
        tv_user_last_seen_time.setText(user.getLastActiveTime());
        tv_user_distance.setText(user.getDistance());
        tv_user_age.setText("" + user.getAge());

        tv_user_orientation.setText(user.getMetaData().getOrientation());
        tv_user_body_type.setText(user.getMetaData().getBody_type());
        tv_user_temperment.setText(user.getMetaData().getTemperament());
        tv_user_size.setText(user.getMetaData().getSize());
        tv_user_hiv_status.setText(user.getMetaData().getHiv_status());
        tv_user_up_for.setText(user.getMetaData().getUp_for());
        tv_user_role.setText(user.getMetaData().getRole());
        tv_user_persona.setText(user.getMetaData().getPersona());
        tv_user_body_hair.setText(user.getMetaData().getBody_hair());
        tv_user_cut.setText(user.getMetaData().getCut());
        tv_user_eye_color.setText(user.getMetaData().getEye_color());
        tv_user_drink.setText(user.getMetaData().getDrink());
        tv_user_height.setText(user.getMetaData().getHeight());
        tv_user_ethnicity.setText(user.getMetaData().getEthnicity());
        tv_user_out_to.setText(user.getMetaData().getOut_to());
        tv_user_smoke.setText(user.getMetaData().getSmoke());
        tv_user_hair_color.setText(user.getMetaData().getHair_color());

        // imageLoader.displayImage(user.getuImg(), iv_user_profile_pic,
        // options);

        userImagePager = (ViewPager) view
                .findViewById(R.id.layout_friend_profile_user_image_viewPager);
        DataAdapter dataAdapter = new DataAdapter();
        userImagePager
                .setAdapter(new com.wecamchat.aviddev.viewadapter.ImagePagerAdapter(
                        getActivity(), dataAdapter.getImages(), imageLoader));

        dotListIndicator = (SimpleViewPagerIndicator) view
                .findViewById(R.id.layout_friends_dot_list_layout);
        dotListIndicator.setViewPager(userImagePager);
        dotListIndicator.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.iv_user_profile_headlinebar_fav:

            replaceFragment(R.id.activity_avid_base_no_footer,
                    AppConstant.FragmentStackName.CONFIRM_FAVORITE_FRAGMENT,
                    "S1", new ConfirmFavoriteFragment(), 0, 0, 0, 0, true,
                    false);

            break;

        case R.id.iv_user_profile_headlinebar_friend:

            break;
        }
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

}
