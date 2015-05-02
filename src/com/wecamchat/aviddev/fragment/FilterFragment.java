package com.wecamchat.aviddev.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.wecamchat.aviddev.R;
import com.wecamchat.aviddev.constant.AppConstant;
import com.wecamchat.aviddev.model.bo.ExpandableFilter;
import com.wecamchat.aviddev.model.bo.FilterOptions;
import com.wecamchat.aviddev.util.PreferenceKeeper;
import com.wecamchat.aviddev.util.seekbar.RangeBar;
import com.wecamchat.aviddev.util.view.Utils;
import com.wecamchat.aviddev.viewadapter.FilterExpandableAdapter;

public class FilterFragment extends AvidBaseFragment {

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

    private ScrollView scrollView;
    private RangeBar ageRangeBar;
    private RangeBar locRangeBar;
    private int ageLeftThumbIndex;
    private int ageRightThumbIndex;
    private int locLeftThumbIndex;
    private String ageLeftThumbText;
    private String ageRightThumbText;
    private String locLeftThumbText;

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

    private PreferenceKeeper prefs;

    private CheckBox hideLocationCheckBox;
    private CheckBox hideInactiveUsersCheckBox;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        try {
            View view;

            prefs = PreferenceKeeper.getInstance(getActivity());
            if (prefs.getHandPrefrence())
                view = inflater.inflate(
                        R.layout.fragment_filter_left_pref_layout, container,
                        false);
            else {
                view = inflater.inflate(
                        R.layout.fragment_filter_right_pref_layout, container,
                        false);
            }
            initializeUI(view);
            setUIAdapters();
            return view;
        } catch (Exception e) {
            return null;
        }
    }

    private void initializeUI(View view) {
        hideLocationCheckBox = (CheckBox) view
                .findViewById(R.id.fragment_filter_hide_location_checkbox);
        hideInactiveUsersCheckBox = (CheckBox) view
                .findViewById(R.id.fragment_filter_hide_inactive_checkbox);

        upForListView = (ExpandableListView) view
                .findViewById(R.id.fragment_filter_up_for_listView);
        upForTextView = (TextView) view
                .findViewById(R.id.fragment_filter_up_for_textView);
        upForLabel = (TextView) view
                .findViewById(R.id.fragment_filter_up_for_label);

        orientationListView = (ExpandableListView) view
                .findViewById(R.id.fragment_filter_orientation_listView);
        orientationTextView = (TextView) view
                .findViewById(R.id.fragment_filter_orientation_textView);
        orientationLabel = (TextView) view
                .findViewById(R.id.fragment_filter_orientation_label);

        bodyTypeListView = (ExpandableListView) view
                .findViewById(R.id.fragment_filter_body_type_listView);
        bodyTypeTextView = (TextView) view
                .findViewById(R.id.fragment_filter_body_type_textView);
        bodyTypeLabel = (TextView) view
                .findViewById(R.id.fragment_filter_body_type_label);

        temperamentListView = (ExpandableListView) view
                .findViewById(R.id.fragment_filter_temperament_listView);
        temperamentTextView = (TextView) view
                .findViewById(R.id.fragment_filter_temperament_textView);
        temperamentLabel = (TextView) view
                .findViewById(R.id.fragment_filter_temperament_label);

        sizeListView = (ExpandableListView) view
                .findViewById(R.id.fragment_filter_size_listView);
        sizeTextView = (TextView) view
                .findViewById(R.id.fragment_filter_size_textView);
        sizeLabel = (TextView) view
                .findViewById(R.id.fragment_filter_size_label);

        hivStatusListView = (ExpandableListView) view
                .findViewById(R.id.fragment_filter_hiv_status_listView);
        hivStatusTextView = (TextView) view
                .findViewById(R.id.fragment_filter_hiv_status_textView);
        hivStatusLabel = (TextView) view
                .findViewById(R.id.fragment_filter_hiv_status_label);

        heightListView = (ExpandableListView) view
                .findViewById(R.id.fragment_filter_height_listView);
        heightTextView = (TextView) view
                .findViewById(R.id.fragment_filter_height_textView);
        heightLabel = (TextView) view
                .findViewById(R.id.fragment_filter_height_label);

        drinkListView = (ExpandableListView) view
                .findViewById(R.id.fragment_filter_drink_listView);
        drinkTextView = (TextView) view
                .findViewById(R.id.fragment_filter_drink_textView);
        drinkLabel = (TextView) view
                .findViewById(R.id.fragment_filter_drink_label);

        eyeColorListView = (ExpandableListView) view
                .findViewById(R.id.fragment_filter_eye_color_listView);
        eyeColorTextView = (TextView) view
                .findViewById(R.id.fragment_filter_eye_color_textView);
        eyeColorLabel = (TextView) view
                .findViewById(R.id.fragment_filter_eye_color_label);

        roleListView = (ExpandableListView) view
                .findViewById(R.id.fragment_filter_role_listView);
        roleTextView = (TextView) view
                .findViewById(R.id.fragment_filter_role_textView);
        roleLabel = (TextView) view
                .findViewById(R.id.fragment_filter_role_label);

        personaListView = (ExpandableListView) view
                .findViewById(R.id.fragment_filter_persona_listView);
        personaTextView = (TextView) view
                .findViewById(R.id.fragment_filter_persona_textView);
        personaLabel = (TextView) view
                .findViewById(R.id.fragment_filter_persona_label);

        bodyHairListView = (ExpandableListView) view
                .findViewById(R.id.fragment_filter_body_hair_listView);
        bodyHairTextView = (TextView) view
                .findViewById(R.id.fragment_filter_body_hair_textView);
        bodyHairLabel = (TextView) view
                .findViewById(R.id.fragment_filter_body_hair_label);

        cutListView = (ExpandableListView) view
                .findViewById(R.id.fragment_filter_cut_listView);
        cutTextView = (TextView) view
                .findViewById(R.id.fragment_filter_cut_textView);
        cutLabel = (TextView) view.findViewById(R.id.fragment_filter_cut_label);

        ethnicityListView = (ExpandableListView) view
                .findViewById(R.id.fragment_filter_ethnicity_listView);
        ethnicityTextView = (TextView) view
                .findViewById(R.id.fragment_filter_ethnicity_textView);
        ethnicityLabel = (TextView) view
                .findViewById(R.id.fragment_filter_ethnicity_label);

        outToListView = (ExpandableListView) view
                .findViewById(R.id.fragment_filter_out_to_listView);
        outToTextView = (TextView) view
                .findViewById(R.id.fragment_filter_out_to_textView);
        outToLabel = (TextView) view
                .findViewById(R.id.fragment_filter_out_to_label);

        smokeListView = (ExpandableListView) view
                .findViewById(R.id.fragment_filter_smoke_listView);
        smokeTextView = (TextView) view
                .findViewById(R.id.fragment_filter_smoke_textView);
        smokeLabel = (TextView) view
                .findViewById(R.id.fragment_filter_smoke_label);

        hairColorListView = (ExpandableListView) view
                .findViewById(R.id.fragment_filter_hair_color_listView);
        hairColorTextView = (TextView) view
                .findViewById(R.id.fragment_filter_hair_color_textView);
        hairColorLabel = (TextView) view
                .findViewById(R.id.fragment_filter_hair_color_label);

        scrollView = (ScrollView) view
                .findViewById(R.id.fragment_filter_root_scrollView);

        ageRangeBar = (RangeBar) view
                .findViewById(R.id.fragment_filter_age_range_bar);
        initializeAgeRangeBar();

        locRangeBar = (RangeBar) view
                .findViewById(R.id.fragment_filter_location_range_bar);
        initializeLocRangeBar();

        setUpListeners();

    }

    private void setUpListeners() {
        // Sets the display values of the indices
        ageRangeBar
                .setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
                    @Override
                    public void onIndexChangeListener(RangeBar rangeBar,
                            int leftThumbIndex1, int rightThumbIndex2) {

                        ageLeftThumbIndex = leftThumbIndex1;
                        ageRightThumbIndex = rightThumbIndex2;

                        ageLeftThumbText = "" + (ageLeftThumbIndex + 13);
                        ageRightThumbText = "" + (ageRightThumbIndex + 13);

                        rangeBar.setLeftThumbText(ageLeftThumbText);
                        rangeBar.setRightThumbText(ageRightThumbText);

                        prefs.setFilterAgeLeftThumb(leftThumbIndex1);
                        prefs.setFilterAgeRightThumb(rightThumbIndex2);

                    }

                });

        locRangeBar
                .setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
                    @Override
                    public void onIndexChangeListener(RangeBar rangeBar,
                            int leftThumbIndex1, int rightThumbIndex2) {

                        locLeftThumbIndex = leftThumbIndex1;
                        locLeftThumbText = "" + (locLeftThumbIndex + 103)
                                + " mi";

                        rangeBar.setLeftThumbText(locLeftThumbText);

                        prefs.setFilterRange(leftThumbIndex1);

                    }
                });

        hideInactiveUsersCheckBox.setChecked(prefs.isFilterHideInactiveUsers());
        hideInactiveUsersCheckBox
                .setOnCheckedChangeListener(new OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton buttonView,
                            boolean isChecked) {
                        prefs.setFilterHideInactiveUsers(isChecked);
                    }
                });

        hideLocationCheckBox.setChecked(prefs.isFilterHideLocations());
        hideLocationCheckBox
                .setOnCheckedChangeListener(new OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton buttonView,
                            boolean isChecked) {
                        prefs.setFilterHideLocations(isChecked);
                    }
                });
    }

    private void initializeLocRangeBar() {
        locRangeBar.setTickCount(200);
        locRangeBar.setSingleThumb(true);

        locLeftThumbIndex = prefs.getFilterRange();
        locLeftThumbText = (locLeftThumbIndex + 240) + " mi";
        locRangeBar.setLeftThumbText("" + locLeftThumbText);
        locRangeBar.setThumbIndices(locLeftThumbIndex, 0);
    }

    /**
     * Method to initialize the range bar
     */
    private void initializeAgeRangeBar() {
        ageRangeBar.setConnectingLineColor(getResources().getColor(
                R.color.range_bar_connecting_line_color));
        ageRangeBar.setTickCount(100);
        ageRangeBar.setConnectingLineWeight(24);
        setAgeBarThumbValues();
    }

    /**
     * Method to set the initial values in the rangeBar thumbs
     */
    private void setAgeBarThumbValues() {
        ageLeftThumbIndex = prefs.getFilterAgeLeftThumb();
        ageRightThumbIndex = prefs.getFilterAgeRightThumb();
        ageLeftThumbText = "" + (ageLeftThumbIndex + 13);
        ageRightThumbText = "" + (ageRightThumbIndex + 13);

        ageRangeBar.setLeftThumbText(ageLeftThumbText);
        ageRangeBar.setRightThumbText(ageRightThumbText);
        ageRangeBar.setThumbIndices(ageLeftThumbIndex, ageRightThumbIndex);
    }

    private void setUIAdapters() {
        setAdapter(upForListView, upForLabel, upForTextView, getActivity()
                .getResources().getStringArray(R.array.filter_up_for_array),
                AppConstant.PreferenceKeeperNames.FILTER_UP_FOR);

        setAdapter(
                orientationListView,
                orientationLabel,
                orientationTextView,
                getActivity().getResources().getStringArray(
                        R.array.filter_orientation_array),
                AppConstant.PreferenceKeeperNames.FILTER_ORIENTATION);

        setAdapter(
                bodyTypeListView,
                bodyTypeLabel,
                bodyTypeTextView,
                getActivity().getResources().getStringArray(
                        R.array.filter_body_type_array),
                AppConstant.PreferenceKeeperNames.FILTER_BODY_TYPE);

        setAdapter(
                temperamentListView,
                temperamentLabel,
                temperamentTextView,
                getActivity().getResources().getStringArray(
                        R.array.filter_temperament_array),
                AppConstant.PreferenceKeeperNames.FILTER_TEMPERAMENT);

        setAdapter(sizeListView, sizeLabel, sizeTextView, getActivity()
                .getResources().getStringArray(R.array.filter_size_array),
                AppConstant.PreferenceKeeperNames.FILTER_SIZE);

        setAdapter(
                hivStatusListView,
                hivStatusLabel,
                hivStatusTextView,
                getActivity().getResources().getStringArray(
                        R.array.filter_hiv_status_array),
                AppConstant.PreferenceKeeperNames.FILTER_HIV_STATUS);

        setAdapter(heightListView, heightLabel, heightTextView, getActivity()
                .getResources().getStringArray(R.array.filter_height_array),
                AppConstant.PreferenceKeeperNames.FILTER_HEIGHT);

        setAdapter(drinkListView, drinkLabel, drinkTextView, getActivity()
                .getResources().getStringArray(R.array.filter_drink_array),
                AppConstant.PreferenceKeeperNames.FILTER_DRINK);

        setAdapter(
                eyeColorListView,
                eyeColorLabel,
                eyeColorTextView,
                getActivity().getResources().getStringArray(
                        R.array.filter_eye_color_array),
                AppConstant.PreferenceKeeperNames.FILTER_EYE_COLOR);

        setAdapter(roleListView, roleLabel, roleTextView, getActivity()
                .getResources().getStringArray(R.array.filter_role_array),
                AppConstant.PreferenceKeeperNames.FILTER_ROLE);

        setAdapter(
                personaListView,
                personaLabel,
                personaTextView,
                getActivity().getResources().getStringArray(
                        R.array.filter_persona_array),
                AppConstant.PreferenceKeeperNames.FILTER_PERSONA);

        setAdapter(
                bodyHairListView,
                bodyHairLabel,
                bodyHairTextView,
                getActivity().getResources().getStringArray(
                        R.array.filter_body_hair_array),
                AppConstant.PreferenceKeeperNames.FILTER_BODY_HAIR);

        setAdapter(cutListView, cutLabel, cutTextView, getActivity()
                .getResources().getStringArray(R.array.filter_cut_array),
                AppConstant.PreferenceKeeperNames.FILTER_CUT);

        setAdapter(
                ethnicityListView,
                ethnicityLabel,
                ethnicityTextView,
                getActivity().getResources().getStringArray(
                        R.array.filter_ethnicity_array),
                AppConstant.PreferenceKeeperNames.FILTER_ETHNICITY);

        setAdapter(outToListView, outToLabel, outToTextView, getActivity()
                .getResources().getStringArray(R.array.filter_out_to_array),
                AppConstant.PreferenceKeeperNames.FILTER_OUT_TO);

        setAdapter(smokeListView, smokeLabel, smokeTextView, getActivity()
                .getResources().getStringArray(R.array.filter_smoke_array),
                AppConstant.PreferenceKeeperNames.FILTER_SMOKE);

        setAdapter(
                hairColorListView,
                hairColorLabel,
                hairColorTextView,
                getActivity().getResources().getStringArray(
                        R.array.filter_hair_color_array),
                AppConstant.PreferenceKeeperNames.FILTER_HAIR_COLOR);
    }

    private void setAdapter(final ExpandableListView listView, TextView label,
            final TextView headerTextView, final String[] stringArray,
            String preferenceName) {

        listView.setAdapter(new FilterExpandableAdapter(getActivity(),
                headerTextView, listView, createList(stringArray),
                preferenceName));
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
            }
        });
    }

    private ExpandableFilter createList(String[] strings) {
        ExpandableFilter filterItem = new ExpandableFilter();
        filterItem.setParent(strings[0]);
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

    @Override
    public boolean onBackPressed() {
        return false;
    }

}
