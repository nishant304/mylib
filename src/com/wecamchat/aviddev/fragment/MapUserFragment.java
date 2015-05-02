package com.wecamchat.aviddev.fragment;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import android.app.FragmentManager;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.wecamchat.aviddev.R;
import com.wecamchat.aviddev.activity.AvidFragmentBaseActivity;
import com.wecamchat.aviddev.constant.AppConstant;
import com.wecamchat.aviddev.cp.AvidCPHelper;
import com.wecamchat.aviddev.dataadapter.DataAdapter;
import com.wecamchat.aviddev.intrface.ILocationDrawer;
import com.wecamchat.aviddev.intrface.IUserDrawer;
import com.wecamchat.aviddev.intrface.OnCurrentMarkerClickListener;
import com.wecamchat.aviddev.intrface.OnHotIconClick;
import com.wecamchat.aviddev.intrface.OnSwipeDownMapBrowerDrawerListener;
import com.wecamchat.aviddev.intrface.RoundBitMapListener;
import com.wecamchat.aviddev.model.bo.User;
import com.wecamchat.aviddev.subfragment.LocationAddFragment;
import com.wecamchat.aviddev.subfragment.UserDrawerFragment;
import com.wecamchat.aviddev.util.PreferenceKeeper;
import com.wecamchat.aviddev.util.location.LocationResult;
import com.wecamchat.aviddev.util.location.LocationUtil;
import com.wecamchat.aviddev.util.view.ExpandableHeightListView;
import com.wecamchat.aviddev.util.view.RoundedImage;
import com.wecamchat.aviddev.util.view.SimpleViewPagerIndicator;
import com.wecamchat.aviddev.util.view.TouchableWrapperOnMap.OnTouchOnMapInteraction;
import com.wecamchat.aviddev.viewadapter.LocationDrawerViewAdapter;
import com.wechat.aviddev.map.cluster.AvidClusterItem;

public class MapUserFragment extends AvidBaseSlidingFragment implements

LoaderManager.LoaderCallbacks<Cursor>, OnMarkerClickListener,

OnCameraChangeListener, OnMapClickListener, OnMapLongClickListener,
		IUserDrawer, ILocationDrawer {

	private boolean onTouchFlag;
	private boolean isFirstTime;

	private List<User> users;

	private View view = null;

	private FrameLayout rel_fragment;

	private LinearLayout ll_fragment_map;

	private RoundedImage roundedImage = null;

	private MapFragment mMapFragment;

	private GoogleMap mGoogleMap;

	private LatLng myCurrentLocation;

	private BitmapDescriptor locationBitmapDescriptor;

	private int top_margin_on_map_add_location;

	private OnHotIconClick onHotIconClick;

	private OnCurrentMarkerClickListener onCurrentMarkerClick;

	private MarkerOptions markerOption = null;

	private HashMap<Marker, User> haspMap = new HashMap<Marker, User>();

	UserDrawerFragment mapUserDrawerHFriend = null;

	private DisplayImageOptions overlayImageOptions;

	// public TouchableWrapperOnMap mTouchView;

	private LocationResult locationResult;
	private Location location;

	private Bitmap userIcnBitMap;
	private Bitmap starIcnBitMap;
	private Bitmap hotIcnBitMap;

	private Geocoder geocoder;

	private List<Address> addresses;

	public static boolean isMapLongPressed;

	Marker markerLongPressed;

	// For user drawer

	private UserDrawerUI userDrawerUI;

	private LocationDrawerUI locationDrawerUI;

	private boolean isUserDrawerMode = true;

	private LinearLayout userDrawerLayout;
	private LinearLayout locationDrawerLayout;

	public OnTouchOnMapInteraction onMapTouchListener = new OnTouchOnMapInteraction() {

		/*
		 * It is custom method (non-Javadoc)
		 */
		@Override
		public void onMapTouch() {
			onTouchFlag = true;
			/*
			 * if (mGoogleMap != null) {
			 * 
			 * // / Top final LatLng northEastLatLng =
			 * mGoogleMap.getProjection()
			 * .getVisibleRegion().latLngBounds.northeast;
			 * 
			 * // / Bottom final LatLng southWestLatLng =
			 * mGoogleMap.getProjection()
			 * .getVisibleRegion().latLngBounds.southwest;
			 * 
			 * // findDistanceTwoPoint(); }
			 */
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getLoaderManager().initLoader(0, null, this);

	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		initViewControls();

		initialComponents();

		initialBitmapComponets();

		setOtherObject();

		checkGooglePlayService();

	}

	@Override
	public void onResume() {
		super.onResume();
		Log.i("Map", " On Resume");
	}

	private void setOtherObject() {
		markerOption = null;

		top_margin_on_map_add_location = (int) getActivity().getResources()
				.getDimension(R.dimen.top_margin_on_map_add_location);

		onCurrentMarkerClick = (AvidFragmentBaseActivity) getActivity();

		setListner();
	}

	private void setListner() {
		onHotIconClick = new OnHotIconClick() {

			@Override
			public void isTickClicked() {
			}

			@Override
			public void isCrossClicked() {
			}
		};
	}

	private void initialBitmapComponets() {
		try {
			initiaSmallHotIconBitmap();
			initiaSmallStartIconBitmap();
			initiaSmallUserIconBitmap();
			initializeLocationBitmapDesc();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void initialComponents() {
		roundedImage = RoundedImage.getInstance();

		overlayImageOptions = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.user_grey_border)
				.cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
				.build();
	}

	private void initViewControls() {
		ll_fragment_map = (LinearLayout) view
				.findViewById(R.id.ll_fragment_map);

		ll_fragment_map.setOnClickListener(this);

		rel_fragment = (FrameLayout) view.findViewById(R.id.rel_fragment);

		rel_fragment.setOnClickListener(this);

		// ImageView hot_icon = (ImageView) view
		// .findViewById(R.id.iv_fragment_map_hot);
		// hot_icon.setVisibility(View.GONE);

		view.findViewById(R.id.iv_fragment_map_gps).setOnClickListener(this);
		// view.findViewById(R.id.iv_fragment_map_hot).setOnClickListener(this);

		mMapFragment = (MapFragment) getFragmentManager().findFragmentById(
				R.id.fragment_google_map);

		// for drawer ui

		userDrawerLayout = (LinearLayout) view
				.findViewById(R.id.user_drawer_layout);
		locationDrawerLayout = (LinearLayout) view
				.findViewById(R.id.location_drawer_layout);

	}

	private void checkGooglePlayService() {
		try {
			MapsInitializer.initialize(getActivity());
		} catch (Exception e) {
			e.printStackTrace();
			checkPlayServices();
		}
		if (checkPlayServices()) {
			mapSetUp();
		} else {
			Toast toast = Toast.makeText(getActivity(),
					"Please update google play services", Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
			getActivity().finish();
			Intent i = new Intent(android.content.Intent.ACTION_VIEW);
			i.setData(Uri
					.parse("https://play.google.com/store/apps/details?id=com.google.android.gms&hl=en"));
			startActivity(i);
		}
	}

	private void setDefaultLocation() {

		myCurrentLocation = new LatLng(37.0000, -120.0000);

		mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
				myCurrentLocation, AppConstant.MAP_SCREEN_ZOOM_LEVEL));
	}

	private void mapSetUp() {
		try {

			if (mMapFragment != null) {
				Log.i("GGG", "mapsetUp");
				mGoogleMap = mMapFragment.getMap();
				if (mGoogleMap != null) {

					if (!checkAndManageLocationHandler()) {
						setDefaultLocation();
					} else {

						mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
						mGoogleMap.getUiSettings()
								.setZoomControlsEnabled(false);
						mGoogleMap.setOnMarkerClickListener(this);
						mGoogleMap.setOnCameraChangeListener(this);
						mGoogleMap.setOnMapClickListener(this);
						mGoogleMap.setOnMapLongClickListener(this);
						LocationManager locationManager = (LocationManager) getActivity()
								.getSystemService(Context.LOCATION_SERVICE);

						// Creating a criteria object to retrieve provider
						Criteria criteria = new Criteria();

						// Getting the name of the best provider
						String provider = locationManager.getBestProvider(
								criteria, true);

						location = locationManager
								.getLastKnownLocation(provider);

						setLastOrCurrentLocation();

					}

				} else {

				}
			}
		} catch (Exception e) {
			Log.i("ZOOM", e.getMessage() + " Error ");
			e.printStackTrace();
		}
	}

	private void setLastKnowLocation() {
		myCurrentLocation = new LatLng(location.getLatitude(),
				location.getLongitude());

		mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
				myCurrentLocation, AppConstant.MAP_SCREEN_ZOOM_LEVEL));
	}

	private void setMarkerOption() {
		if (markerOption == null) {
			markerOption = new MarkerOptions().position(myCurrentLocation);

			markerOption.anchor(0.3f, 0.4f);
			markerOption.title("MyProfile");
			markerOption.icon(BitmapDescriptorFactory.fromBitmap(roundedImage
					.getRoundedShape(getActivity(), BitmapFactory
							.decodeResource(getActivity().getResources(),
									R.drawable.user_image), null, -1)));

			mGoogleMap.addMarker(markerOption);
		}

	}

	private void moveCamera(LatLng lt, float zoom) {
		mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lt, zoom));
	}

	private void setCurrentLocation() {
		if (location != null) {
			if (PreferenceKeeper.getInstance(getActivity()).getmZoomLevel() != 0) {
				moveCamera(PreferenceKeeper.getInstance(getActivity())
						.getmZoomLevelLAtLng(),
						PreferenceKeeper.getInstance(getActivity())
								.getmZoomLevel());
			} else {

				moveCamera(myCurrentLocation, AppConstant.MAP_SCREEN_ZOOM_LEVEL);
			}

		} else {
			Log.d("LocationNotFound", "LocationNotFound");
		}

	}

	public void setImageLoader(final User myMapData) {
		final LatLng friendLocation = new LatLng(myMapData.getmLatitude(),
				myMapData.getmLongitude());
		ImageSize imageSize = new ImageSize(60, 60);

		ImageLoader.getInstance().loadImage(myMapData.getuImg(), imageSize,
				overlayImageOptions, new ImageLoadingListener() {

					@Override
					public void onLoadingStarted(String arg0, View arg1) {
					}

					@Override
					public void onLoadingFailed(String arg0, View arg1,
							FailReason arg2) {
					}

					@Override
					public void onLoadingComplete(String arg0, View arg1,
							Bitmap imageBitmap) {
						final MarkerOptions option = new MarkerOptions();

						if (myMapData.isUser()) {

							roundedImage.getFinalBitmap(getActivity(),
									imageBitmap,
									setSmallIcon(myMapData.getActiveStatus()),
									myMapData.getUserType(), myMapData,
									new RoundBitMapListener() {

										@Override
										public void LoadingFailed(
												Object myMapData) {
										}

										@Override
										public void LoadingComplete(
												Object myMapData, Bitmap bitmap) {
											option.icon(BitmapDescriptorFactory
													.fromBitmap(bitmap));

											option.anchor(0.3f, 0.4f).position(
													friendLocation);

											Marker marker = mGoogleMap
													.addMarker(option);

											haspMap.put(marker,
													(User) myMapData);

										}
									});
						} else {
							option.icon(getLocationBitmapDesc());
							option.anchor(0.3f, 0.4f).position(friendLocation);

							Marker marker = mGoogleMap.addMarker(option);

							haspMap.put(marker, myMapData);
						}

					}

					private BitmapDescriptor getLocationBitmapDesc() {

						if (locationBitmapDescriptor == null) {
							initializeLocationBitmapDesc();
						}

						return locationBitmapDescriptor;
					}

					@Override
					public void onLoadingCancelled(String arg0, View arg1) {
					}
				});

	}

	private void initializeLocationBitmapDesc() {
		locationBitmapDescriptor = BitmapDescriptorFactory
				.fromResource(R.drawable.map_loc_purple_ic);
	}

	public Bitmap setSmallIcon(int type) {
		switch (type) {
		case 0:
			return getHotIconBitMap();
		case 1:
			return getStartIconBitMap();
		case 2:
			return getUserIconBitMap();
		default:
			return getHotIconBitMap();

		}
	}

	private Bitmap getUserIconBitMap() {

		if (userIcnBitMap == null) {
			initiaSmallUserIconBitmap();
		}

		return userIcnBitMap;
	}

	private void initiaSmallUserIconBitmap() {

		// TODO fix null pointer issue and remove try catch block.

		try {

			userIcnBitMap = BitmapFactory.decodeResource(getActivity()
					.getResources(), R.drawable.user_ic);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private Bitmap getStartIconBitMap() {

		if (starIcnBitMap == null) {
			initiaSmallStartIconBitmap();
		}
		return starIcnBitMap;
	}

	private void initiaSmallStartIconBitmap() {

		// TODO fix null pointer issue and remove try catch block.

		try {

			starIcnBitMap = BitmapFactory.decodeResource(getActivity()
					.getResources(), R.drawable.star_ic);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Bitmap getHotIconBitMap() {
		if (hotIcnBitMap == null) {
			initiaSmallHotIconBitmap();
		}
		return hotIcnBitMap;

	}

	private void initiaSmallHotIconBitmap() {

		// TODO fix null pointer issue and remove try catch block.
		try {
			hotIcnBitMap = BitmapFactory.decodeResource(getActivity()
					.getResources(), R.drawable.hot_ic);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// TODO handle all positive and negative use case of get location

	private void setLastOrCurrentLocation() {
		if (getActivity() != null) {
			Log.i("GGG", "mapsetUp 2 ");
			if (location == null) {
				checkLocationListener();
				setLastKnowLocation();
			} else {
				myCurrentLocation = new LatLng(location.getLatitude(),
						location.getLongitude());
				setMarkerOption();
				setCurrentLocation();
			}
		}
	}

	private void checkLocationListener() {
		LocationResult lr = new LocationResult() {

			@Override
			public void gotLocation(Location location) {
				if (location != null) {

					if (isFirstTime) {
						isFirstTime = false;
						new LocationUtil().getLocation(getActivity(),
								locationResult);
					} else {

						MapUserFragment.this.location = location;
						setLastOrCurrentLocation();

					}

				} else {

					if (getActivity() != null)
						Toast.makeText(getActivity(), "Could not get Location",
								Toast.LENGTH_LONG).show();
				}

			}
		};

		new LocationUtil().getLocation(getActivity(), lr);
	}

	private void createMarkersAsImages(final List<User> mapUsers) {
		try {

			if (mapUsers != null && mapUsers.size() > 0) {

				for (final User myMapData : mapUsers) {

					if (myMapData.getuImg() != null
							&& !myMapData.getuImg().equalsIgnoreCase("")) {
						setImageLoader(myMapData);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	@Override
	public void onDestroyView() {

		if (!getActivity().isFinishing() && mMapFragment != null) {
			getFragmentManager().beginTransaction().remove(mMapFragment)
					.commit();
			Log.i("GGG", "onDestroyView");
		}

		hotIcnBitMap = null;
		starIcnBitMap = null;
		userIcnBitMap = null;
		locationBitmapDescriptor = null;

		super.onDestroyView();

	}

	@Override
	public CursorLoader onCreateLoader(int id, Bundle args) {

		CursorLoader cursorLoader = new CursorLoader(getActivity(),
				AvidCPHelper.Uris.URI_MAP_USER, null, null, null, null);

		return cursorLoader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {

		AvidCPHelper helper = AvidCPHelper.getInstance(getActivity());
		users = helper.getMapUsersFromCursor(arg1);
		Log.i("GGG", "onLoadFinished");
		if (location != null)
			createMarkersAsImages(users);

	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// TODO handle these case

	}

	// private void setMapObject(User userInfo) {
	// if (mapUserDrawerHFriend == null)
	// mapUserDrawerHFriend = new UserDrawerFragment(
	// clickOnMapUserDrawser, userInfo);
	// }

	public void downMapDrawer() {
		slideToDownUserDrawer();
		// isMarkerClick = false;

		if (getFragmentManager() != null)
			getFragmentManager().popBackStack("M",
					FragmentManager.POP_BACK_STACK_INCLUSIVE);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.iv_fragment_map_gps:

			if (onTouchFlag) {
				moveCamera(myCurrentLocation, AppConstant.MAP_SCREEN_ZOOM_LEVEL);
				onTouchFlag = false;
			}

			break;

		default:
			break;
		}
	}

	@Override
	public void onCameraChange(CameraPosition camPos) {

		if (camPos.zoom > AppConstant.MAP_SCREEN_ZOOM_LEVEL) {

			mGoogleMap.animateCamera(CameraUpdateFactory
					.zoomTo(AppConstant.MAP_SCREEN_ZOOM_LEVEL), 1, null);

			PreferenceKeeper.getInstance(getActivity()).setmZoomLevel(
					camPos.zoom);
			PreferenceKeeper.getInstance(getActivity()).setmZoomLevelLatLng(
					String.valueOf(camPos.target.latitude),
					String.valueOf(camPos.target.longitude));

		}

		if (camPos.zoom < AppConstant.MAP_SCREEN_MIN_ZOOM_LEVEL) {

			PreferenceKeeper.getInstance(getActivity()).setmZoomLevel(
					camPos.zoom);
			PreferenceKeeper.getInstance(getActivity()).setmZoomLevelLatLng(
					String.valueOf(camPos.target.latitude),
					String.valueOf(camPos.target.longitude));

			mGoogleMap.animateCamera(CameraUpdateFactory
					.zoomTo(AppConstant.MAP_SCREEN_MIN_ZOOM_LEVEL), 1, null);
		}

	}

	public void slideToAboveUserDrawer() {
		Animation slide = null;
		slide = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
				0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
		// slide = new TranslateAnimation(0.0f, 0.0f, 0.0f, 0.5f);
		slide.setDuration(900);
		slide.setFillAfter(true);
		slide.setFillEnabled(true);
		rel_fragment.startAnimation(slide);

		slide.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {

				rel_fragment.clearAnimation();

				rel_fragment.setPadding(0, 0, 0,
						((top_margin_on_map_add_location)));

			}

		});

	}

	public void slideToDownUserDrawer() {
		Animation slide = null;

		slide = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
				0.0f, Animation.RELATIVE_TO_SELF, 0.0f);

		slide.setDuration(400);
		slide.setFillAfter(true);
		slide.setFillEnabled(true);
		rel_fragment.startAnimation(slide);

		slide.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				rel_fragment.setPadding(0, 0, 0, 0);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {

				rel_fragment.setPadding(0, 0, 0, 0);
			}

		});

	}

	@Override
	public boolean onMarkerClick(Marker marker) {

		if ((marker.getTitle() != null)
				&& marker.getTitle().equalsIgnoreCase("MyProfile")) {
			getFragmentManager().popBackStack(null,
					FragmentManager.POP_BACK_STACK_INCLUSIVE);
			replaceFragment(R.id.base_fragment_body_layout, "MyProfile", "My",
					new ProfileFragment(), 0, 0, 0, 0, false, false);

			onCurrentMarkerClick.onCurrentMarkerClick();

		} else {

			User userInfo = haspMap.get(marker);
			if (userInfo != null && userInfo.isUser()) {

				userDrawerLayout.setVisibility(View.VISIBLE);
				locationDrawerLayout.setVisibility(View.GONE);

				setDragViews(R.id.layout_userdrawer_drag_view, true);

				mLayout.setDragView(getActivity().findViewById(
						R.id.layout_userdrawer_drag_view));

				mLayout.expandPanel(0.63f);

				setUserDrawerData(userInfo);

				showBottomRightPanel();
			} else {

				userDrawerLayout.setVisibility(View.GONE);
				locationDrawerLayout.setVisibility(View.VISIBLE);
				setDragViews(R.id.layout_locationdrawer_drag_view, false);

				mLayout.setDragView(getActivity().findViewById(
						R.id.layout_locationdrawer_drag_view));

				mLayout.expandPanel(0.50f);

				setLocationDrawerData(userInfo);

				hideBottomRightPanel();
			}
		}

		return false;
	}

	// called from MapUserDrawerHFragment , onTouch

	OnSwipeDownMapBrowerDrawerListener clickOnMapUserDrawser = new OnSwipeDownMapBrowerDrawerListener() {

		@Override
		public void onSwipeDownMapBrowerDrawer() {
			downMapDrawer();
			// setCurrentLocation();
		}
	};

	@Override
	public void onPause() {
		super.onPause();

		Log.i("ONPAUSE", "On Pause");
	}

	@Override
	public void onMapClick(LatLng arg0) {

		// if (isMarkerClick) {
		Log.i("MapClick", " map click");
		downMapDrawer();
		// }

	}

	@Override
	public void onMapLongClick(LatLng arg0) {
		isMapLongPressed = true;
		mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(arg0,
				PreferenceKeeper.getInstance(getActivity()).getmZoomLevel()));
		geocoder = new Geocoder(getActivity(), Locale.getDefault());
		try {
			addresses = geocoder.getFromLocation(arg0.latitude, arg0.longitude,
					1);

			int maxLineAddress = addresses.get(0).getMaxAddressLineIndex();

			String str = "";
			for (int i = 0; i < maxLineAddress; i++) {

				str = str + addresses.get(0).getAddressLine(i) + "\n";

			}

			MarkerOptions option = new MarkerOptions();
			markerLongPressed = mGoogleMap.addMarker(option
					.position(arg0)
					.anchor(0.3f, 0.4f)
					.icon(BitmapDescriptorFactory.fromBitmap(roundedImage
							.getRoundedShape(getActivity(), BitmapFactory
									.decodeResource(getResources(),
											R.drawable.location_add_ic), null,
									0))));
			slideToAboveUserDrawer();
			replaceFragment(R.id.map_user_drawer_half, "M", "S",
					new LocationAddFragment(str, markerLongPressed,
							clickOnMapUserDrawser), R.anim.animation_up,
					R.anim.animation_down, R.anim.animation_up,
					R.anim.animation_down, true, false);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void removeLongPressedMarker() {
		markerLongPressed.remove();
	}

	@Override
	public void initLocationDrawerViewControls() {

		locationDrawerUI = new LocationDrawerUI();

		locationDrawerUI.tv_location_drawer_rate_or_review = (TextView) view
				.findViewById(R.id.tv_location_drawer_rate_or_review);
		locationDrawerUI.ll_location_drawer_rate_review = (LinearLayout) view
				.findViewById(R.id.ll_location_drawer_rate_review);

		locationDrawerUI.listview_location_drawer = (ExpandableHeightListView) view
				.findViewById(R.id.listview_location_drawer);

		locationDrawerUI.tv_location_drawer_rate_or_review
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (locationDrawerUI.isRateOrReview) {
							locationDrawerUI.isRateOrReview = false;
							locationDrawerUI.tv_location_drawer_rate_or_review
									.setTextColor(Color.parseColor("#45a4d3"));
							locationDrawerUI.tv_location_drawer_rate_or_review
									.setText("Rate or Review");
							locationDrawerUI.ll_location_drawer_rate_review
									.setVisibility(View.GONE);
						} else {

							locationDrawerUI.tv_location_drawer_rate_or_review
									.setTextColor(Color.parseColor("#221f1f"));
							locationDrawerUI.tv_location_drawer_rate_or_review
									.setText("Rate This Location");

							locationDrawerUI.ll_location_drawer_rate_review
									.setVisibility(View.VISIBLE);
							locationDrawerUI.isRateOrReview = true;
						}

					}
				});

		locationDrawerUI.listview_location_drawer
				.setOnTouchListener(new OnTouchListener() {
					// Setting on Touch Listener for handling the touch inside
					// ScrollView
					@Override
					public boolean onTouch(View v, MotionEvent event) {
						// Disallow the touch request for parent scroll on touch
						// of child view
						v.getParent().requestDisallowInterceptTouchEvent(false);
						return false;
					}
				});

	}

	private void setListViewData() {
		locationDrawerUI.listview_location_drawer.setExpanded(true);
		locationDrawerUI.listview_location_drawer
				.setAdapter(new LocationDrawerViewAdapter(
						(AvidFragmentBaseActivity) getActivity()));

	}

	@Override
	public void setLocationDrawerData(User user) {
		setListViewData();
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
	public void setUserDrawerData(User user) {
		userDrawerUI.tv_friend_username.setText(user.getuName());
		userDrawerUI.tv_user_wink_count.setText("" + user.getWinkCount());
		userDrawerUI.tv_user_headline.setText(user.getProfileDesc());
		userDrawerUI.tv_user_last_seen_time.setText(user.getLastActiveTime());
		userDrawerUI.tv_user_distance.setText(user.getDistance());
		userDrawerUI.tv_user_age.setText("" + user.getAge());
		userDrawerUI.tv_user_orientation.setText(user.getMetaData()
				.getOrientation());
		userDrawerUI.tv_user_body_type.setText(user.getMetaData()
				.getBody_type());
		userDrawerUI.tv_user_temperment.setText(user.getMetaData()
				.getTemperament());
		userDrawerUI.tv_user_size.setText(user.getMetaData().getSize());
		userDrawerUI.tv_user_hiv_status.setText(user.getMetaData()
				.getHiv_status());
		userDrawerUI.tv_user_up_for.setText(user.getMetaData().getUp_for());
		userDrawerUI.tv_user_role.setText(user.getMetaData().getRole());
		userDrawerUI.tv_user_persona.setText(user.getMetaData().getPersona());
		userDrawerUI.tv_user_body_hair.setText(user.getMetaData()
				.getBody_hair());
		userDrawerUI.tv_user_cut.setText(user.getMetaData().getCut());
		userDrawerUI.tv_user_eye_color.setText(user.getMetaData()
				.getEye_color());
		userDrawerUI.tv_user_drink.setText(user.getMetaData().getDrink());
		userDrawerUI.tv_user_height.setText(user.getMetaData().getHeight());
		userDrawerUI.tv_user_ethnicity.setText(user.getMetaData()
				.getEthnicity());
		userDrawerUI.tv_user_out_to.setText(user.getMetaData().getOut_to());
		userDrawerUI.tv_user_smoke.setText(user.getMetaData().getSmoke());
		userDrawerUI.tv_user_hair_color.setText(user.getMetaData()
				.getHair_color());

		// imageLoader.displayImage(user.getuImg(), iv_user_profile_pic,
		// options);

		DataAdapter dataAdapter = new DataAdapter();

		userDrawerUI.userImagePager
				.setAdapter(new com.wecamchat.aviddev.viewadapter.ImagePagerAdapter(
						getActivity(), dataAdapter.getImages(), imageLoader));

		userDrawerUI.dotListIndicator = (SimpleViewPagerIndicator) view
				.findViewById(R.id.layout_friends_dot_list_layout);
		userDrawerUI.dotListIndicator.setViewPager(userDrawerUI.userImagePager);
		userDrawerUI.dotListIndicator.notifyDataSetChanged();

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

	private class LocationDrawerUI {
		private ExpandableHeightListView listview_location_drawer;

		private TextView tv_location_drawer_rate_or_review;

		private boolean isRateOrReview;
		private LinearLayout ll_location_drawer_rate_review;
	}

	@Override
	protected void addViews(LayoutInflater inflater, View parentView) {
		view = parentView;

		ViewGroup slidePanelLayout = (ViewGroup) parentView
				.findViewById(R.id.sliding_layout);

		// base layout
		slidePanelLayout.addView(inflater.inflate(R.layout.fragment_map, null),
				0);
		// user drawer layout

		if (PreferenceKeeper.getInstance(getActivity()).getHandPrefrence()) {
			slidePanelLayout.addView(inflater.inflate(
					R.layout.layout_base_lh_location_and_user_drawer, null), 1);

		} else {
			slidePanelLayout.addView(inflater.inflate(
					R.layout.layout_base_location_and_user_drawer, null), 1);
		}

		// initialize user drawer view controls
		initUserDrawerViewControls();

		// initialize location drawer view controls
		initLocationDrawerViewControls();

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
	protected void onBasePanelExpanded(View panel) {
		super.onBasePanelExpanded(panel);

		showUserDrawerComponent();
	}

	@Override
	protected void onBasePanelAnchored(View panel) {
		super.onBasePanelAnchored(panel);

		hideUserDrawerComponent();

	}

	@Override
	public void setUserDrawerData(Cursor cursor) {
		// TODO Auto-generated method stub

	}

}
