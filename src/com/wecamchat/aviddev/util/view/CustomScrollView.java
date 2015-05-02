package com.wecamchat.aviddev.util.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

import com.wecamchat.aviddev.util.slidingdrawer.SlidingUpPanelLayout.PanelState;

public class CustomScrollView extends ScrollView {

	private static final String TAG = CustomScrollView.class.getSimpleName();
	private View mDragView;
	private PanelState panelState;
	private int[] parentLocation = new int[2];
	private int mInitialMotionX = 0;
	private int mInitialMotionY = 0;

	public void setPanelState(PanelState panelState) {
		this.panelState = panelState;
	}

	public void updateParentCoordinates() {
		this.getLocationOnScreen(parentLocation);
		Log.d(TAG, "updateParentCoordinates x: " + parentLocation[0] + " y: "
				+ parentLocation[1]);
	}

	public void setDragView(View dragView) {
		mDragView = dragView;
		updateParentCoordinates();
	}

	public CustomScrollView(Context context) {
		super(context);
	}

	public CustomScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public CustomScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);

	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		Log.d(TAG, "onInterceptTouchEvent");
		// int mInitialMotionX = 0;
		// int mInitialMotionY = 0;
		int x = (int) ev.getX();
		int y = (int) ev.getY();
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN: {
			Log.d(TAG, "onInterceptTouchEvent ACTION_DOWN");
			mInitialMotionX = x;
			mInitialMotionY = y;
			break;
		}

		case MotionEvent.ACTION_MOVE: {
			Log.d(TAG, "onInterceptTouchEvent ACTION_MOVE");
			if (panelState == PanelState.ANCHORED)
				return true;
			// if(panelState == PanelState.EXPANDED && parentLocation[1]<0){
			// return super.onInterceptHoverEvent(ev);
			// }
			if (isDragViewUnder(mInitialMotionX, mInitialMotionY)) {
				return true;
			} else {
				return super.onInterceptHoverEvent(ev);
			}
		}
		case MotionEvent.ACTION_UP: {
			Log.d(TAG, "onInterceptTouchEvent updateParentCoordinates");
			updateParentCoordinates();
		}

		}
		return super.onInterceptHoverEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		Log.d(TAG, "onTouchEvent");
		// int mInitialMotionX = 0;
		// int mInitialMotionY = 0;
		int x = (int) ev.getX();
		int y = (int) ev.getY();
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN: {
			Log.d(TAG, "onTouchEvent ACTION_DOWN");
			mInitialMotionX = x;
			mInitialMotionY = y;
			break;
		}

		case MotionEvent.ACTION_MOVE: {
			Log.d(TAG, "onTouchEvent ACTION_MOVE");
			if (panelState == PanelState.ANCHORED)
				return true;
			// if(panelState == PanelState.EXPANDED && parentLocation[1]<0){
			// return super.onTouchEvent(ev);
			// }
			if (isDragViewUnder(mInitialMotionX, mInitialMotionY)) {
				return true;
			} else {
				return super.onTouchEvent(ev);
			}
		}
		case MotionEvent.ACTION_UP: {
			Log.d(TAG, "onTouchEvent updateParentCoordinates");
			updateParentCoordinates();
		}

		}
		return super.onTouchEvent(ev);
	}

	private boolean isDragViewUnder(int x, int y) {
		Log.d(TAG, "isDragViewUnder x:" + x + " ,y:" + y);
		if (mDragView == null)
			return false;
		int[] viewLocation = new int[2];
		mDragView.getLocationOnScreen(viewLocation);
		// parentView.getLocationOnScreen(parentLocation);
		int screenX = parentLocation[0] + x;
		int screenY = parentLocation[1] + y;
		boolean isDragViewUnder = screenX >= viewLocation[0]
				&& screenX < viewLocation[0] + mDragView.getWidth()
				&& screenY >= viewLocation[1]
				&& screenY < viewLocation[1] + mDragView.getHeight();
		Log.d(TAG, "isDragViewUnder : " + isDragViewUnder);
		return isDragViewUnder;

	}

}
