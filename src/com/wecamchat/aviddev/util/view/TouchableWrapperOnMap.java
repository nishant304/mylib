package com.wecamchat.aviddev.util.view;

import android.content.Context;
import android.view.MotionEvent;
import android.widget.FrameLayout;

public class TouchableWrapperOnMap extends FrameLayout {

    private OnTouchOnMapInteraction onMapTouch;

    public TouchableWrapperOnMap(Context context,
            OnTouchOnMapInteraction listener) {
        super(context);

        try {
            onMapTouch = listener;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement onMapTouch");
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
        case MotionEvent.ACTION_DOWN:

            break;
        case MotionEvent.ACTION_UP:

            onMapTouch.onMapTouch();

            break;
        }
        return super.dispatchTouchEvent(ev);
    }

    // MapFragment must implement this interface
    public interface OnTouchOnMapInteraction {
        public void onMapTouch();
    }
}
