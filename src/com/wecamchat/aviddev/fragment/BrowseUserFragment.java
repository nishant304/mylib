package com.wecamchat.aviddev.fragment;

import java.util.HashMap;
import java.util.Map;
import android.app.FragmentManager;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import com.wecam.aviddev.api.response.handler.BrowseResponseHandler;
import com.wecamchat.aviddev.R;
import com.wecamchat.aviddev.activity.AvidBaseActivity;
import com.wecamchat.aviddev.api.input.NearMeInput;
import com.wecamchat.aviddev.api.io.UserList;
import com.wecamchat.aviddev.cp.AvidCPHelper;
import com.wecamchat.aviddev.db.BrowseContract;
import com.wecamchat.aviddev.httpClient.AppHttpRequest;
import com.wecamchat.aviddev.httpClient.AppRequestBuilder;
import com.wecamchat.aviddev.httpClient.AppRestClient;
import com.wecamchat.aviddev.intrface.IUserDrawer;
import com.wecamchat.aviddev.intrface.OnSwipeDownMapBrowerDrawerListener;
import com.wecamchat.aviddev.model.bo.User;
import com.wecamchat.aviddev.subfragment.UserDrawerFragment;
import com.wecamchat.aviddev.util.AppUtil;
import com.wecamchat.aviddev.util.view.SimpleViewPagerIndicator;
import com.wecamchat.aviddev.viewadapter.BrowseViewAdapter;

public class BrowseUserFragment extends AvidBaseSlidingFragment implements
		LoaderManager.LoaderCallbacks<Cursor>, OnItemClickListener, IUserDrawer {

	protected static final String TAG = null;
	private static final String API_TAG = BrowseUserFragment.class.getName();
	View view = null;

	// public static boolean isBrowserClick;
	public UserDrawerFragment browser = null;
	private int lastFirstVisiblePosition;

	private GridView gridView;

	private UserDrawerUI userDrawerUI;
	private BrowseViewAdapter adpter;

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		CursorLoader cursorLoader = new CursorLoader(getActivity(),
				AvidCPHelper.Uris.URI_BROWSE_USER, null, null, null, null);

		return cursorLoader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
		AvidCPHelper helper = AvidCPHelper.getInstance(getActivity());
		adpter.swapCursor(cursor);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		setDragViews(R.id.layout_friends_profile_header_root, true);
		Cursor cursor = (Cursor) gridView.getAdapter().getItem(position);
		lastFirstVisiblePosition = gridView.getFirstVisiblePosition();
		gridView.setSelection(position);
		gridView.requestFocus(View.FOCUS_UP);
		mLayout.expandPanel(0.63f);
		setUserDrawerData(cursor);
	}

	@Override
	public void setUserDrawerData(Cursor cursor) {
		userDrawerUI.tv_friend_username.setText(cursor.getString(cursor
				.getColumnIndex(BrowseContract.BrowseEntry.COLUMN_NAME_UNAME)));
		userDrawerUI.tv_user_wink_count
				.setText(""
						+ cursor.getString(cursor
								.getColumnIndex(BrowseContract.BrowseEntry.COLUMN_NAME_WINK_CNT)));

		userDrawerUI.tv_user_headline
				.setText(cursor.getString(cursor
						.getColumnIndex(BrowseContract.BrowseEntry.COLUMN_NAME_PROFILE_DESC)));
		userDrawerUI.tv_user_last_seen_time
				.setText(cursor.getString(cursor
						.getColumnIndex(BrowseContract.BrowseEntry.COLUMN_NAME_LAST_ACT_TIME)));
		userDrawerUI.tv_user_distance
				.setText(cursor.getString(cursor
						.getColumnIndex(BrowseContract.BrowseEntry.COLUMN_NAME_DISTANCE)));
		userDrawerUI.tv_user_age
				.setText(""
						+ cursor.getString(cursor
								.getColumnIndex(BrowseContract.BrowseEntry.COLUMN_NAME_AGE)));
		userDrawerUI.tv_user_orientation
				.setText(cursor.getString(cursor
						.getColumnIndex(BrowseContract.BrowseEntry.COLUMN_NAME_ORIENT)));
		userDrawerUI.tv_user_body_type
				.setText(cursor.getString(cursor
						.getColumnIndex(BrowseContract.BrowseEntry.COLUMN_NAME_BODY_TYPE)));
		userDrawerUI.tv_user_temperment
				.setText(cursor.getString(cursor
						.getColumnIndex(BrowseContract.BrowseEntry.COLUMN_NAME_TEMPER)));
		userDrawerUI.tv_user_size.setText(cursor.getString(cursor
				.getColumnIndex(BrowseContract.BrowseEntry.COLUMN_NAME_SIZE)));
		userDrawerUI.tv_user_hiv_status
				.setText(cursor.getString(cursor
						.getColumnIndex(BrowseContract.BrowseEntry.COLUMN_NAME_HIV_ST)));
		userDrawerUI.tv_user_up_for
				.setText(cursor.getString(cursor
						.getColumnIndex(BrowseContract.BrowseEntry.COLUMN_NAME_UP_FOR)));
		userDrawerUI.tv_user_role.setText(cursor.getString(cursor
				.getColumnIndex(BrowseContract.BrowseEntry.COLUMN_NAME_ROLE)));
		userDrawerUI.tv_user_persona
				.setText(cursor.getString(cursor
						.getColumnIndex(BrowseContract.BrowseEntry.COLUMN_NAME_PERSONA)));
		userDrawerUI.tv_user_body_hair
				.setText(cursor.getString(cursor
						.getColumnIndex(BrowseContract.BrowseEntry.COLUMN_NAME_BODY_HAIR)));
		userDrawerUI.tv_user_cut.setText(cursor.getString(cursor
				.getColumnIndex(BrowseContract.BrowseEntry.COLUMN_NAME_CUT)));
		userDrawerUI.tv_user_eye_color
				.setText(cursor.getString(cursor
						.getColumnIndex(BrowseContract.BrowseEntry.COLUMN_NAME_EYECOLOR)));
		userDrawerUI.tv_user_drink.setText(cursor.getString(cursor
				.getColumnIndex(BrowseContract.BrowseEntry.COLUMN_NAME_DRINK)));
		userDrawerUI.tv_user_height
				.setText(cursor.getString(cursor
						.getColumnIndex(BrowseContract.BrowseEntry.COLUMN_NAME_HEIGHT)));
		userDrawerUI.tv_user_ethnicity
				.setText(cursor.getString(cursor
						.getColumnIndex(BrowseContract.BrowseEntry.COLUMN_NAME_ETHNICITY)));
		userDrawerUI.tv_user_out_to.setText(cursor.getString(cursor
				.getColumnIndex(BrowseContract.BrowseEntry.COLUMN_NAME_OUTTO)));
		userDrawerUI.tv_user_smoke.setText(cursor.getString(cursor
				.getColumnIndex(BrowseContract.BrowseEntry.COLUMN_NAME_SMOKE)));
		userDrawerUI.tv_user_hair_color
				.setText(cursor.getString(cursor
						.getColumnIndex(BrowseContract.BrowseEntry.COLUMN_NAME_HAIRCOLOR)));
		String[] arr = new String[1];
		arr[0] = cursor.getString(cursor
				.getColumnIndex(BrowseContract.BrowseEntry.COLUMN_NAME_UIMG));
		userDrawerUI.userImagePager
				.setAdapter(new com.wecamchat.aviddev.viewadapter.ImagePagerAdapter(
						getActivity(), arr, imageLoader));
		userDrawerUI.dotListIndicator = (SimpleViewPagerIndicator) view
				.findViewById(R.id.layout_friends_dot_list_layout);
		userDrawerUI.dotListIndicator.setViewPager(userDrawerUI.userImagePager);
		userDrawerUI.dotListIndicator.notifyDataSetChanged();
	}

	@Override
	public void onStop() {
		super.onStop();
		AppRestClient.getClient().cancelAll(API_TAG);
	}

	public void makeNewbrowseUserRequest() {
		NearMeInput input = new NearMeInput();
		input.setLat(77.66);
		input.setLng(36.66);
		input.setLimit(10000);
		input.setSkip(0);
		AppHttpRequest request = AppRequestBuilder.getNearMe(input,
				new BrowseResponseHandler(UserList.class, getActivity()));
		AppRestClient.getClient().sendRequest(request, API_TAG);
	}

	@Override
	protected void addViews(LayoutInflater inflater, View parentView) {
		view = parentView;
		ViewGroup slidePanelLayout = (ViewGroup) parentView
				.findViewById(R.id.sliding_layout);

		// base layout
		slidePanelLayout.addView(
				inflater.inflate(R.layout.fragment_browser, null), 0);
		// user drawer layout
		if (((AvidBaseActivity) getActivity()).getPreferenceKeeper()
				.getHandPrefrence()) {

			slidePanelLayout.addView(inflater.inflate(
					R.layout.layout_base_lh_friends_profile, null), 1);

		} else {
			slidePanelLayout.addView(inflater.inflate(
					R.layout.layout_base_friends_profile, null), 1);
		}
		initUserDrawerViewControls();
		makeNewbrowseUserRequest();
		showDataInUi();
		AppUtil.getCreateTableStatement(com.wecamchat.aviddev.api.io.User.class);
	}

	@Override
	public void initUserDrawerViewControls() {

		userDrawerUI = new UserDrawerUI();

		userDrawerUI.tv_friend_username = (TextView) view
				.findViewById(R.id.tv_friend_username);
		userDrawerUI.tv_user_wink_count = (TextView) view
				.findViewById(R.id.tv_user_wink_count);
		userDrawerUI.tv_user_headline = (TextView) view
				.findViewById(R.id.tv_user_headline);
		userDrawerUI.tv_user_last_seen_time = (TextView) view
				.findViewById(R.id.tv_user_last_seen_time);
		userDrawerUI.tv_user_distance = (TextView) view
				.findViewById(R.id.tv_user_distance);
		userDrawerUI.tv_user_age = (TextView) view
				.findViewById(R.id.tv_user_age);
		userDrawerUI.tv_user_orientation = (TextView) view
				.findViewById(R.id.tv_user_orientation);
		userDrawerUI.tv_user_body_type = (TextView) view
				.findViewById(R.id.tv_user_body_type);
		userDrawerUI.tv_user_temperment = (TextView) view
				.findViewById(R.id.tv_user_temperment);
		userDrawerUI.tv_user_size = (TextView) view
				.findViewById(R.id.tv_user_size);
		userDrawerUI.tv_user_hiv_status = (TextView) view
				.findViewById(R.id.tv_user_hiv_status);
		userDrawerUI.tv_user_up_for = (TextView) view
				.findViewById(R.id.tv_user_up_for);
		userDrawerUI.tv_user_role = (TextView) view
				.findViewById(R.id.tv_user_role);
		userDrawerUI.tv_user_persona = (TextView) view
				.findViewById(R.id.tv_user_persona);
		userDrawerUI.tv_user_body_hair = (TextView) view
				.findViewById(R.id.tv_user_body_hair);
		userDrawerUI.tv_user_cut = (TextView) view
				.findViewById(R.id.tv_user_cut);
		userDrawerUI.tv_user_eye_color = (TextView) view
				.findViewById(R.id.tv_user_eye_color);
		userDrawerUI.tv_user_drink = (TextView) view
				.findViewById(R.id.tv_user_drink);
		userDrawerUI.tv_user_height = (TextView) view
				.findViewById(R.id.tv_user_height);
		userDrawerUI.tv_user_ethnicity = (TextView) view
				.findViewById(R.id.tv_user_ethnicity);
		userDrawerUI.tv_user_out_to = (TextView) view
				.findViewById(R.id.tv_user_out_to);
		userDrawerUI.tv_user_smoke = (TextView) view
				.findViewById(R.id.tv_user_smoke);
		userDrawerUI.tv_user_hair_color = (TextView) view
				.findViewById(R.id.tv_user_hair_color);
		userDrawerUI.iv_user_profile_headlinebar_fav = (ImageView) view
				.findViewById(R.id.iv_user_profile_headlinebar_fav);
		userDrawerUI.iv_user_profile_headlinebar_friend = (ImageView) view
				.findViewById(R.id.iv_user_profile_headlinebar_friend);

		userDrawerUI.userImagePager = (ViewPager) view
				.findViewById(R.id.layout_friend_profile_user_image_viewPager);

		userDrawerUI.blockImageView = (ImageView) view
				.findViewById(R.id.iv_friend_profile_block_icon);

		userDrawerUI.friendImageView = (ImageView) view
				.findViewById(R.id.iv_user_profile_headlinebar_friend);
		userDrawerUI.favImageView = (ImageView) view
				.findViewById(R.id.iv_user_profile_headlinebar_fav);

		hideUserDrawerComponent();

	}

	@Override
	public void onBasePanelCollapsed(View panel) {
		super.onBasePanelCollapsed(panel);

		// set scroll position in grid view
		gridView.setSelection(lastFirstVisiblePosition);

		gridView.requestFocus(View.FOCUS_DOWN);

	}

	private class UserDrawerUI {

		private ImageView iv_user_profile_pic;
		private ImageView iv_user_profile_headlinebar_fav;
		private ImageView iv_user_profile_headlinebar_friend;

		private ImageView blockImageView;
		private ImageView friendImageView;
		private ImageView favImageView;

		private TextView tv_friend_username;
		private TextView tv_user_wink_count;
		private TextView tv_user_headline;
		private TextView tv_user_last_seen_time;
		private TextView tv_user_distance;
		private TextView tv_user_age;
		private TextView tv_user_orientation;
		private TextView tv_user_body_type;
		private TextView tv_user_temperment;
		private TextView tv_user_size;
		private TextView tv_user_hiv_status;
		private TextView tv_user_up_for, tv_user_role;
		private TextView tv_user_persona;
		private TextView tv_user_body_hair;
		private TextView tv_user_cut;
		private TextView tv_user_eye_color;
		private TextView tv_user_drink;
		private TextView tv_user_height;
		private TextView tv_user_ethnicity;
		private TextView tv_user_out_to;
		private TextView tv_user_smoke;
		private TextView tv_user_hair_color;

		private ViewPager userImagePager;
		private SimpleViewPagerIndicator dotListIndicator;
	}

	private void showDataInUi() {
		gridView = (GridView) view.findViewById(R.id.gridview);
		gridView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
		gridView.setOnItemClickListener(this);
		adpter = new BrowseViewAdapter(((AvidBaseActivity) getActivity()));
		gridView.setAdapter(adpter);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {

	}

	OnSwipeDownMapBrowerDrawerListener clickOnBrowserUserDrawser = new OnSwipeDownMapBrowerDrawerListener() {

		@Override
		public void onSwipeDownMapBrowerDrawer() {

			downMapBrowerDrawer();
		}

	};

	public void downMapBrowerDrawer() {
		// MapUserFragment.isMarkerClick = false;
		// isBrowserClick = false;
		if (getFragmentManager() != null)
			getFragmentManager().popBackStack("M",
					FragmentManager.POP_BACK_STACK_INCLUSIVE);

		// set scroll position in grid view
		gridView.setSelection(lastFirstVisiblePosition);

		gridView.requestFocus(View.FOCUS_DOWN);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getLoaderManager().initLoader(0, null, this);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	protected void onBasePanelExpanded(View panel) {
		// TODO Auto-generated method stub
		super.onBasePanelExpanded(panel);

		showUserDrawerComponent();
	}

	@Override
	protected void onBasePanelAnchored(View panel) {
		// TODO Auto-generated method stub
		super.onBasePanelAnchored(panel);

		hideUserDrawerComponent();

	}

	private void hideUserDrawerComponent() {
		userDrawerUI.tv_user_wink_count.setVisibility(View.INVISIBLE);
		userDrawerUI.blockImageView.setVisibility(View.INVISIBLE);
		userDrawerUI.friendImageView.setVisibility(View.INVISIBLE);
		userDrawerUI.favImageView.setVisibility(View.INVISIBLE);

	}

	private void showUserDrawerComponent() {

		userDrawerUI.tv_user_wink_count.setVisibility(View.VISIBLE);
		userDrawerUI.blockImageView.setVisibility(View.VISIBLE);
		userDrawerUI.friendImageView.setVisibility(View.VISIBLE);
		userDrawerUI.favImageView.setVisibility(View.VISIBLE);

	}

	@Override
	public void setUserDrawerData(User user) {

	}

}
