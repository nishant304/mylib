package com.wecamchat.aviddev.intrface;

import android.graphics.Bitmap;

public interface RoundBitMapListener {

    void LoadingComplete(Object object, Bitmap bitmap);

    void LoadingFailed(Object object);

}
