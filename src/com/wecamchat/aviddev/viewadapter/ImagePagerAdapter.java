package com.wecamchat.aviddev.viewadapter;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.Service;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.wecamchat.aviddev.R;

public class ImagePagerAdapter extends PagerAdapter {
	private LayoutInflater inflater;
	private String[] imageUrls;
	private DisplayImageOptions options = new DisplayImageOptions.Builder()
			.cacheInMemory(true).cacheOnDisc(true).considerExifParams(true)
			.showImageOnFail(R.drawable.user_image)
			.showImageOnLoading(R.drawable.user_image).build();
	private Context context;
	private ImageLoader imageLoader;
	private float yCordDown;
	private float yCordUp;
	private float deltaY;

	public ImagePagerAdapter(Context context, String[] urls,
			ImageLoader imageLoader) {
		this.imageUrls = urls;
		this.context = context;
		inflater = (LayoutInflater) context
				.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
		this.imageLoader = imageLoader;
	}

	@Override
	public int getCount() {
		return imageUrls.length;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0.equals(arg1);
	}

	@Override
	public void destroyItem(View arg0, int arg1, Object arg2) {
		((ViewGroup) arg0).removeView((View) arg2);
	}

	@Override
	public void finishUpdate(View arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object instantiateItem(View arg0, int arg1) {
		View imageLayout = inflater.inflate(
				R.layout.fragment_half_map_user_image_adapter, null);
		ImageView imageView = (ImageView) imageLayout
				.findViewById(R.id.fragment_half_map_user_imageView);
		// final ProgressBar spinner = (ProgressBar) imageLayout
		// .findViewById(R.id.loading);

		imageLoader.displayImage(imageUrls[arg1], imageView, options,
				new SimpleImageLoadingListener() {
					@Override
					public void onLoadingStarted(String imageUri, View view) {
						// spinner.setVisibility(View.VISIBLE);
					}

					@Override
					public void onLoadingFailed(String imageUri, View view,
							FailReason failReason) {
						String message = null;
						switch (failReason.getType()) {
						case IO_ERROR:
							message = "Input/Output error";
							break;
						case DECODING_ERROR:
							message = "Image can't be decoded";
							break;
						case NETWORK_DENIED:
							message = "Downloads are denied";
							break;
						case OUT_OF_MEMORY:
							message = "Out Of Memory error";
							break;
						case UNKNOWN:
							message = "Unknown error";
							break;
						}

						Toast.makeText(context, message, Toast.LENGTH_SHORT)
								.show();

						// spinner.setVisibility(View.GONE);
					}

					@Override
					public void onLoadingComplete(String imageUri, View view,
							Bitmap loadedImage) {
						// spinner.setVisibility(View.GONE);
						Log.d("nishant", "image loaded successfully");
					}
				});

		imageLayout.setOnTouchListener(layoutTouchListener);

		((ViewGroup) arg0).addView(imageLayout, 0);
		return imageLayout;
	}

	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public Parcelable saveState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void startUpdate(View arg0) {
		// TODO Auto-generated method stub

	}

	OnTouchListener layoutTouchListener = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction()) {

			case MotionEvent.ACTION_DOWN: {
				deltaY = 0;
				yCordDown = event.getY();
				return true;
			}

			case MotionEvent.ACTION_CANCEL:
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_MOVE: {

				yCordUp = event.getY();
				deltaY = yCordUp - yCordDown;
				if (deltaY > 50) {

					// float y = Math.abs((yCordUp - yCordDown));
					// float x = Math.abs((xCordUp - xCordDown));
					//
					// if (y > x && y >= 200) {
					((Activity) context).getFragmentManager().popBackStack(
							"M1", FragmentManager.POP_BACK_STACK_INCLUSIVE);
					// }
					return false;
				}
				return true;

			}
			}
			return false;
		}
	};

}
