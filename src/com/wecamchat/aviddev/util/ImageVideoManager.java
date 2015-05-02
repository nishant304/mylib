package com.wecamchat.aviddev.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.media.MediaRecorder;
import android.media.MediaRecorder.OnErrorListener;
import android.media.MediaRecorder.OnInfoListener;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.FrameLayout;

import com.wecamchat.aviddev.R;
import com.wecamchat.aviddev.activity.AvidFragmentBaseActivity;
import com.wecamchat.aviddev.constant.AppConstant;
import com.wecamchat.aviddev.model.bo.CapturedData;
import com.wecamchat.aviddev.util.view.CameraPreview;
import com.wecamchat.aviddev.util.view.Utils;

public class ImageVideoManager {

    private View view;
    private AvidFragmentBaseActivity context;

    private Camera mCamera;
    private MediaRecorder mMediaRecorder;
    private CameraPreview mPreview;

    private FrameLayout cameraFrameLayout;

    private String TAG = "ProfileFragment";
    private boolean isRecording = false;

    private ImageVideoData returnData;

    private Button captureButton;
    private Button flipCameraButton;

    private String videoFileName;

    private Chronometer chronometer;

    private FrameLayout previewLayout;

    private int currentCameraId = Camera.CameraInfo.CAMERA_FACING_BACK;

    public ImageVideoManager(Context context, View v, ImageVideoData returnData) {
        this.view = v;
        this.context = (AvidFragmentBaseActivity) context;
        this.returnData = returnData;
        cameraFrameLayout = (FrameLayout) view
                .findViewById(R.id.layout_my_profile_camera_layout);
        previewLayout = (FrameLayout) view.findViewById(R.id.camera_preview);
    }

    /**
     * Helper method to access the camera returns null if it cannot get the
     * camera or does not exist
     * 
     * @return
     */
    private Camera getCameraInstance(int currentCameraId) {
        Camera c = null;
        try {
            c = Camera.open(currentCameraId); // attempt to get a Camera
                                              // instance
        } catch (Exception e) {
            // Camera is not available (in use or does not exist)
            e.printStackTrace();
        }
        return c; // returns null if camera is unavailable
    }

    private File getOutputMediaFile(boolean isImage) {
        Boolean isSDPresent = android.os.Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED);

        File mediaStorageDir = null;

        if (isSDPresent) {
            mediaStorageDir = new File(
                    Environment
                            .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    AppConstant.MEDIA_STORAGE_NAME);
        } else {
            mediaStorageDir = new File(context.getDir(
                    Environment.DIRECTORY_PICTURES,
                    Context.MODE_WORLD_WRITEABLE),
                    AppConstant.MEDIA_STORAGE_NAME);
        }

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("AvidMedia", "failed to create directory");
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        File mediaFile;

        if (isImage) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".png");
        } else {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "VID_" + timeStamp + ".mp4");
        }

        return mediaFile;
    }

    /************************************ Video Recording *********************************/

    public void startVideoRecording() {

        if (prepareVideoRecorder()) {
            captureButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isRecording) {
                        stopVideoCapture();
                    } else {
                        startVideoCapture();
                    }
                }
            });

        } else {
            // prepare didn't work, release the camera
            releaseMediaRecorder();
            // inform user
        }

    }

    private void startVideoCapture() {
        // Camera is available and unlocked, MediaRecorder
        // is prepared, now you can start recording
        mMediaRecorder.start();
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
        // inform the user that recording has started
        captureButton.setText("Stop");
        isRecording = true;
    }

    private void stopVideoCapture() {
        // stop recording and release camera
        mMediaRecorder.stop(); // stop the recording
        releaseMediaRecorder(); // release the MediaRecorder
                                // object
        stopTimer();
        // inform the user that recording has stopped
        captureButton.setText("Record");
        isRecording = false;

        if (videoFileName != null) {
            CapturedData capturedVideo = new CapturedData();
            capturedVideo.setFilePath(videoFileName);

            Bitmap bMapSmall = ThumbnailUtils.createVideoThumbnail(
                    videoFileName, MediaStore.Video.Thumbnails.MICRO_KIND);
            Bitmap bMapLarge = ThumbnailUtils
                    .createVideoThumbnail(videoFileName,
                            MediaStore.Video.Thumbnails.FULL_SCREEN_KIND);

            capturedVideo.setSmallImage(Utils.getCircularBitmap(bMapSmall));
            capturedVideo.setLargeImage(Utils.getCircularBitmap(bMapLarge));
            capturedVideo.setImage(false);

            if (bMapSmall != null) {
                returnData.onDataReceive(capturedVideo);
            }
        }
    }

    private boolean prepareVideoRecorder() {
        initVideoCapture();
        mCamera = getCameraInstance(currentCameraId);
        mCamera.setDisplayOrientation(90);
        // Create our Preview view and set it as the content of our
        // activity.
        mPreview = new CameraPreview(context, mCamera,
                new CameraPreview.VideoHandler() {

                    @Override
                    public void onSurfaceCreated(SurfaceHolder holder) {
                        Log.d(TAG, "onSurfaceCreated");
                        // Step 5: Set the preview output
                        mMediaRecorder.setPreviewDisplay(holder.getSurface());
                        mMediaRecorder
                                .setMaxDuration(AppConstant.VIDEO_CAPTURE_LIMIT);

                        if (currentCameraId == Camera.CameraInfo.CAMERA_FACING_BACK) {
                            mMediaRecorder.setOrientationHint(90);
                        } else {
                            mMediaRecorder.setOrientationHint(270);
                            mMediaRecorder.setVideoSize(320, 240);
                        }

                        // Step 6: Prepare configured MediaRecorder
                        try {
                            mMediaRecorder.prepare();
                            // startVideoCapture();
                        } catch (IllegalStateException e) {
                            Log.d(TAG,
                                    "IllegalStateException preparing MediaRecorder: "
                                            + e.getMessage());
                            releaseMediaRecorder();
                        } catch (IOException e) {
                            Log.d(TAG, "IOException preparing MediaRecorder: "
                                    + e.getMessage());
                            releaseMediaRecorder();
                        }
                    }

                    @Override
                    public void onSurfaceDestroyed() {
                        releaseMediaRecorder();
                    }
                });
        previewLayout.addView(mPreview);
        mMediaRecorder = new MediaRecorder();

        // Step 1: Unlock and set camera to MediaRecorder
        mCamera.unlock();
        mMediaRecorder.setCamera(mCamera);

        // Step 2: Set sources
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);

        // Step 3: Set a CamcorderProfile (requires API Level 8 or higher)
        // if (currentCameraId == Camera.CameraInfo.CAMERA_FACING_BACK) {
        // mMediaRecorder.setProfile(CamcorderProfile
        // .get(CamcorderProfile.QUALITY_HIGH));
        // } else {
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
        mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
        mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.DEFAULT);
        // }

        // Step 4: Set output file
        videoFileName = getOutputMediaFile(false).toString();
        mMediaRecorder.setOutputFile(videoFileName);
        mMediaRecorder.setOnErrorListener(new OnErrorListener() {

            @Override
            public void onError(MediaRecorder mr, int what, int extra) {
                Log.d(TAG, mr + ", what = " + what);
            }
        });

        mMediaRecorder.setOnInfoListener(new OnInfoListener() {
            @Override
            public void onInfo(MediaRecorder mr, int what, int extra) {
                if (what == MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED) {
                    Log.v("VIDEOCAPTURE", "Maximum Duration Reached");
                    stopVideoCapture();
                }
            }
        });

        flipCameraButton = (Button) view.findViewById(R.id.flip_camera);
        if (Camera.getNumberOfCameras() > 1) {
            flipCameraButton.setVisibility(View.VISIBLE);
            flipCameraButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    File file = new File(videoFileName);
                    if (file.exists()) {
                        file.delete();
                    }
                    // swap the id of the camera to be used
                    if (currentCameraId == Camera.CameraInfo.CAMERA_FACING_BACK) {
                        currentCameraId = Camera.CameraInfo.CAMERA_FACING_FRONT;
                    } else {
                        currentCameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
                    }
                    stopTimer();
                    releaseMediaRecorder();
                    prepareVideoRecorder();
                }

            });
        }

        return true;

    }

    private void stopTimer() {
        chronometer.stop();
        chronometer.setBase(SystemClock.elapsedRealtime());
    }

    private void initVideoCapture() {
        isRecording = false;
        videoFileName = null;
        captureButton = (Button) view.findViewById(R.id.button_capture);
        captureButton.setText("Record");

        cameraFrameLayout.setVisibility(View.VISIBLE);
        chronometer = (Chronometer) view.findViewById(R.id.chronometer);
        chronometer.setVisibility(View.VISIBLE);
    }

    private void releaseMediaRecorder() {
        if (mMediaRecorder != null) {
            mMediaRecorder.reset(); // clear recorder configuration
            mMediaRecorder.release(); // release the recorder object
            mMediaRecorder = null;
            // lock camera for later use
            previewLayout.removeAllViews();
            cameraFrameLayout.setVisibility(View.GONE);
            chronometer.setVisibility(View.GONE);
        }
    }

    /*************************************** Image Capturing **************************************/

    public void captureImage() {
        checkNumberOfCameras();
        captureButton = (Button) view.findViewById(R.id.button_capture);
        captureButton.setText("Capture");
        if (checkCameraHardware(context)) {
            // Create an instance of Camera
            cameraFrameLayout.setVisibility(View.VISIBLE);

            mCamera = getCameraInstance(currentCameraId);
            mCamera.setDisplayOrientation(90);
            // Create our Preview view and set it as the content of our
            // activity.
            mPreview = new CameraPreview(context, mCamera, null);
            previewLayout.addView(mPreview);

            // Add a listener to the Capture button

            captureButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // get an image from the camera
                    context.showProgressBar();
                    mCamera.takePicture(null, null, mPicture);
                    cameraFrameLayout.setVisibility(View.GONE);
                }
            });
        }

    }

    private void checkNumberOfCameras() {
        flipCameraButton = (Button) view.findViewById(R.id.flip_camera);

        if (Camera.getNumberOfCameras() > 1) {
            flipCameraButton.setVisibility(View.VISIBLE);

            flipCameraButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    mCamera.stopPreview();
                    FrameLayout preview = (FrameLayout) view
                            .findViewById(R.id.camera_preview);
                    preview.removeAllViews();
                    // NB: if you don't release the current camera before
                    // switching, you app will crash
                    mCamera.release();

                    // swap the id of the camera to be used
                    if (currentCameraId == Camera.CameraInfo.CAMERA_FACING_BACK) {
                        currentCameraId = Camera.CameraInfo.CAMERA_FACING_FRONT;
                    } else {
                        currentCameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
                    }
                    mCamera = Camera.open(currentCameraId);
                    mCamera.setDisplayOrientation(90);
                    // this step is critical or preview on new camera will
                    // no know where to render to
                    mPreview = new CameraPreview(context, mCamera, null);

                    preview.addView(mPreview);
                    // mCamera.setPreviewDisplay(previewHolder);
                    mCamera.startPreview();
                }
            });

        }

    }

    private PictureCallback mPicture = new PictureCallback() {

        @Override
        public void onPictureTaken(final byte[] data, Camera camera) {

            previewLayout.removeAllViews();

            final File pictureFile = getOutputMediaFile(true);
            if (pictureFile == null) {
                Log.d(TAG,
                        "Error creating media file, check storage permissions: ");
                return;
            }

            new CropImage(pictureFile, data).execute();
        }
    };

    private class CropImage extends AsyncTask<Void, Void, Bitmap> {

        File pictureFile;
        byte[] data;

        // @Override
        // protected void onPreExecute() {
        // super.onPreExecute();
        // context.showProgressBar();
        // }

        public CropImage(File pictureFile, byte[] data) {
            this.pictureFile = pictureFile;
            this.data = data;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            Bitmap bitmap = adjustOrientation(pictureFile, data);
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            CapturedData capturedImage = new CapturedData();
            capturedImage.setFilePath(pictureFile.getAbsolutePath());
            capturedImage.setLargeImage(bitmap);
            capturedImage.setSmallImage(ThumbnailUtils.extractThumbnail(bitmap,
                    100, 100));
            capturedImage.setImage(true);
            returnData.onDataReceive(capturedImage);
            context.hideProgressBar();
        }
    }

    private Bitmap adjustOrientation(File pictureFile, byte[] data) {
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);

            Bitmap realImage = BitmapFactory.decodeByteArray(data, 0,
                    data.length);

            if (currentCameraId == Camera.CameraInfo.CAMERA_FACING_BACK) {
                realImage = rotate(realImage, 90);
                // ExifInterface exif = new
                // ExifInterface(pictureFile.toString());
                //
                // Log.d("EXIF value",
                // exif.getAttribute(ExifInterface.TAG_ORIENTATION));
                // if (exif.getAttribute(ExifInterface.TAG_ORIENTATION)
                // .equalsIgnoreCase("6")) {
                // realImage = rotate(realImage, 90);
                // } else if (exif.getAttribute(ExifInterface.TAG_ORIENTATION)
                // .equalsIgnoreCase("8")) {
                // realImage = rotate(realImage, 270);
                // } else if (exif.getAttribute(ExifInterface.TAG_ORIENTATION)
                // .equalsIgnoreCase("3")) {
                // realImage = rotate(realImage, 180);
                // } else if (exif.getAttribute(ExifInterface.TAG_ORIENTATION)
                // .equalsIgnoreCase("0")) {
                // realImage = rotate(realImage, 90);
                // }
            } else {
                realImage = rotate(realImage, 270);
            }

            realImage = getBitmapClippedCircle(realImage);

            boolean bo = realImage.compress(Bitmap.CompressFormat.PNG, 50, fos);

            Log.e("Image compressed???", bo + "");
            fos.close();

            return realImage;

        } catch (FileNotFoundException e) {
            Log.d("Info", "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d("TAG", "Error accessing file: " + e.getMessage());
        }
        return null;
    }

    private Bitmap getBitmapClippedCircle(Bitmap bitmap) {
        DisplayMetrics displayMetrics = context.getResources()
                .getDisplayMetrics();
        float density = displayMetrics.density;
        float height = displayMetrics.heightPixels;
        float width = displayMetrics.widthPixels;
        float radius = density * AppConstant.CAMERA_IMAGE_SIZE;
        Bitmap cbitmap = Bitmap.createBitmap((int) radius * 2,
                (int) radius * 2, Config.ARGB_8888);
        Matrix matrix = new Matrix();
        matrix.setScale(width / (float) bitmap.getWidth(), height
                / (float) bitmap.getHeight());
        matrix.postTranslate(-((width / 2) - radius), -((height / 2) - radius));

        final Path path = new Path();
        path.addCircle(radius, radius, radius, Direction.CCW);

        final Canvas canvas = new Canvas(cbitmap);
        canvas.clipPath(path);
        canvas.drawBitmap(bitmap, matrix, null);
        return cbitmap;
    }

    public static Bitmap rotate(Bitmap bitmap, int degree) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Matrix mtx = new Matrix();
        // mtx.postRotate(degree);
        mtx.setRotate(degree);
        return Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, true);
    }

    /** Check if this device has a camera */
    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    public interface ImageVideoData {
        public void onDataReceive(CapturedData capturedData);
    }

}
