package com.wecamchat.aviddev.viewadapter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.wecamchat.aviddev.R;
import com.wecamchat.aviddev.activity.AvidBaseActivity;
import com.wecamchat.aviddev.api.io.User;
import com.wecamchat.aviddev.db.BrowseContract;
import com.wecamchat.aviddev.util.view.CustomTextView;

public class BrowseViewAdapter extends CursorAdapter {

	private LayoutInflater inflater;
	List<User> browseUser = new ArrayList<User>();
	private ImageLoader imageLoader;
	private DisplayImageOptions options;
	private WeakReference<AvidBaseActivity> weakRef;
	private Context context;

	public BrowseViewAdapter(Context context) {
		super(context, null, false);
		weakRef = new WeakReference<AvidBaseActivity>(
				(AvidBaseActivity) context);
		inflater = LayoutInflater.from(context);
		this.context = context;
		initImageLoader();
	}

	public void addAll(List<User> list) {
		this.browseUser.clear();
		browseUser.addAll(list);
		notifyDataSetChanged();
	}

	private void initImageLoader() {
		ImageLoader.getInstance().init(
				ImageLoaderConfiguration.createDefault(context));
		imageLoader = ImageLoader.getInstance();
		options = new DisplayImageOptions.Builder().cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true)
				.showImageOnLoading(R.drawable.user_image)
				.bitmapConfig(Bitmap.Config.RGB_565).build();
	}

	public class ViewHolder {
		public ImageView iv_custom_browser_user_image;
		public CustomTextView tv_custom_browser_username;
		public CustomTextView tv_custom_browser_distance;
		public ImageView im_custom_browser_user_icon;
		public ImageView im_custom_browser_user_border;
	}

	int getSmallResourceIcon(int type) {
		switch (type) {

		case 1:

			return R.drawable.star_ic;
		case 2:

			return R.drawable.small_user_group_ic;

		default:
			return R.drawable.hot_ic;

		}
	}

	int getLargeBorderResource(int type) {
		switch (type) {

		case 1:

			return R.drawable.user_grey_border;

		default:
			return R.drawable.user_green_border;

		}
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		ViewHolder holder = (ViewHolder) view.getTag();
		setData(cursor, holder);
	}

	private void setData(Cursor cursor, ViewHolder holder) {

		try {
			holder.tv_custom_browser_username
					.setText(cursor
							.getString(
									cursor.getColumnIndex(BrowseContract.BrowseEntry.COLUMN_NAME_UNAME))
							.split(" ")[0]);

		} catch (IndexOutOfBoundsException e) {
			holder.tv_custom_browser_username
					.setText(cursor
							.getColumnIndex(BrowseContract.BrowseEntry.COLUMN_NAME_UNAME));
		}
		holder.tv_custom_browser_distance
				.setText(String.valueOf(cursor
						.getColumnIndex(BrowseContract.BrowseEntry.COLUMN_NAME_DISTANCE)));
		holder.im_custom_browser_user_icon
				.setImageResource(getSmallResourceIcon(cursor
						.getInt(BrowseContract.BrowseEntry.COLUMN_POSITION_UTYPE)));

		holder.im_custom_browser_user_border
				.setImageResource(getLargeBorderResource(cursor
						.getInt(BrowseContract.BrowseEntry.COLUMN_POSITION_AC_ST)));

		String url = cursor
				.getString(BrowseContract.BrowseEntry.COLUMN_POSITION_UIMG);
		ImageLoader.getInstance().displayImage(url + "",
				holder.iv_custom_browser_user_image, options);
	}

	@Override
	public View newView(Context contexnt, Cursor cursor, ViewGroup parent) {
		ViewHolder holder = new ViewHolder();
		View view = inflater.inflate(R.layout.layout_custome_browser, parent,
				false);
		holder.iv_custom_browser_user_image = (ImageView) view
				.findViewById(R.id.iv_custom_browser_user_image);
		holder.tv_custom_browser_username = (CustomTextView) view
				.findViewById(R.id.tv_custom_browser_username);
		holder.tv_custom_browser_distance = (CustomTextView) view
				.findViewById(R.id.tv_custom_browser_distance);
		holder.im_custom_browser_user_icon = (ImageView) view
				.findViewById(R.id.im_custom_browser_user_icon);
		holder.im_custom_browser_user_border = (ImageView) view
				.findViewById(R.id.im_custom_browser_user_border);
		view.setTag(holder);
		setData(cursor, holder);
		return view;
	}
}
