package com.wecamchat.aviddev.util.view;

import java.io.IOException;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/** A basic Camera preview class */
public class CameraPreview extends SurfaceView implements
        SurfaceHolder.Callback {
    private final String TAG = "CameraPreview";
    private SurfaceHolder mHolder;
    private Camera mCamera;
    VideoHandler videoHandler;

    public CameraPreview(Context context, Camera camera, VideoHandler vh) {
        super(context);
        mCamera = camera;
        videoHandler = vh;
        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        mHolder = getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        // The Surface has been created, now tell the camera where to draw the
        // preview.
        // mHolder = holder;
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
            Log.d(TAG, "Error setting camera preview: " + e.getMessage());
        }

        if (videoHandler != null) {
            videoHandler.onSurfaceCreated(holder);
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {

        if (videoHandler != null) {
            videoHandler.onSurfaceDestroyed();
        }
        if (mCamera != null) {
            mCamera.lock();
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        // If your preview can change or rotate, take care of those events here.
        // Make sure to stop the preview before resizing or reformatting it.

        mHolder = holder;
        if (mHolder.getSurface() == null) {
            // preview surface does not exist
            return;
        }

        // stop preview before making changes
        try {
            mCamera.stopPreview();
        } catch (Exception e) {
            // ignore: tried to stop a non-existent preview
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here

        // Camera.Parameters mParameters = mCamera.getParameters();
        // Camera.Size bestSize = null;
        //
        // List<Camera.Size> sizeList = mCamera.getParameters()
        // .getSupportedPreviewSizes();
        // bestSize = sizeList.get(sizeList.size() - 1);
        //
        // mParameters.setPreviewSize(bestSize.width, bestSize.height);
        // mCamera.setParameters(mParameters);

        // start preview with new settings
        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();

        } catch (Exception e) {
            Log.d(TAG, "Error starting camera preview: " + e.getMessage());
        }
    }

    public interface VideoHandler {
        public void onSurfaceCreated(SurfaceHolder holder);

        public void onSurfaceDestroyed();
    }

    // @Override
    // protected void onWindowVisibilityChanged(int visibility) {
    // super.onWindowVisibilityChanged(visibility);
    // if (visibility != View.VISIBLE) {
    // surfaceDestroyed(null);
    // }
    // }
}
