package com.wecamchat.aviddev.subfragment;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wecamchat.aviddev.R;
import com.wecamchat.aviddev.constant.AppConstant;
import com.wecamchat.aviddev.fragment.AvidBaseFragment;
import com.wecamchat.aviddev.model.bo.CapturedData;
import com.wecamchat.aviddev.util.ImageVideoManager;
import com.wecamchat.aviddev.util.ImageVideoManager.ImageVideoData;

@SuppressLint("ValidFragment")
public class CameraOpenFragment extends AvidBaseFragment {

    private ImageVideoData imageVideoData;
    private View view;
    private boolean isImage = true;

    public CameraOpenFragment(ImageVideoData imageVideoData, boolean isImage) {
        this.imageVideoData = imageVideoData;
        this.isImage = isImage;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.layout_image_video_capture, container,
                false);

        if (isImage) {
            ImageVideoManager takeImage = new ImageVideoManager(getActivity(),
                    view, new ImageVideoData() {

                        @Override
                        public void onDataReceive(CapturedData capturedData) {
                            imageVideoData.onDataReceive(capturedData);
                            getFragmentManager()
                                    .popBackStack(
                                            AppConstant.FragmentStackName.CAMERA_OPEN_FRAGMENT,
                                            FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        }
                    });
            takeImage.captureImage();
        } else {
            ImageVideoManager video = new ImageVideoManager(getActivity(),
                    view, new ImageVideoData() {

                        @Override
                        public void onDataReceive(CapturedData capturedData) {
                            imageVideoData.onDataReceive(capturedData);
                            getFragmentManager()
                                    .popBackStack(
                                            AppConstant.FragmentStackName.CAMERA_OPEN_FRAGMENT,
                                            FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        }
                    });
            video.startVideoRecording();
        }
        return view;
    }

    public interface ProcessComplete {
        public void onProcessComplete();
    }

    @Override
    public boolean onBackPressed() {
        // TODO Auto-generated method stub
        return false;
    }

}
