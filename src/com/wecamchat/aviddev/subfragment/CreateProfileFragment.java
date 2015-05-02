package com.wecamchat.aviddev.subfragment;

import java.io.File;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.wecamchat.aviddev.R;
import com.wecamchat.aviddev.activity.AvidFragmentBaseActivity;
import com.wecamchat.aviddev.api.ErrorObject;
import com.wecamchat.aviddev.constant.AppConstant;
import com.wecamchat.aviddev.cp.AvidCPHelper;
import com.wecamchat.aviddev.fragment.AvidBaseFragment;
import com.wecamchat.aviddev.fragment.ProfileFragment;
import com.wecamchat.aviddev.httpClient.AppHttpRequest;
import com.wecamchat.aviddev.httpClient.AppRequestBuilder;
import com.wecamchat.aviddev.httpClient.AppResponseListener;
import com.wecamchat.aviddev.httpClient.AppRestClient;
import com.wecamchat.aviddev.model.bo.CapturedData;
import com.wecamchat.aviddev.model.bo.MediaContent;
import com.wecamchat.aviddev.model.bo.Profile;
import com.wecamchat.aviddev.util.ImageVideoManager;
import com.wecamchat.aviddev.util.ImageVideoManager.ImageVideoData;
import com.wecamchat.aviddev.util.PreferenceKeeper;
import com.wecamchat.aviddev.util.view.Utils;

import eu.janmuller.android.simplecropimage.CropImage;

@SuppressLint("ValidFragment")
public class CreateProfileFragment extends AvidBaseFragment {

    private static final String API_TAG = AvidBaseFragment.class.getName();
    private View view;
    private EditText nameEditText;

    private ImageView yesImageView;
    private ImageView noImageView;
    private ImageView camImageView;

    private CapturedData profileImageData;
    private String filePath;

    int prePosition;
    int position;
    private AvidCPHelper helper;

    public CreateProfileFragment() {
    }

    public CreateProfileFragment(int prePosition, int position) {
        this.prePosition = prePosition;
        this.position = position;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_create_profile, container,
                false);

        helper = AvidCPHelper.getInstance(getActivity());
        initUI();
        return view;
    }

    private void initUI() {
        nameEditText = (EditText) view
                .findViewById(R.id.fragment_create_profile_name_editText);
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(),
                "fonts/GlyphaLTStd.otf");
        nameEditText.setTypeface(font);

        yesImageView = (ImageView) view
                .findViewById(R.id.fragment_create_profile_yes_button);
        noImageView = (ImageView) view
                .findViewById(R.id.fragment_create_profile_no_button);

        camImageView = (ImageView) view
                .findViewById(R.id.fragment_create_profile_cam_imageView);

        yesImageView.setOnClickListener(this);
        noImageView.setOnClickListener(this);
        camImageView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
        case R.id.fragment_create_profile_yes_button:
            if (nameEditText.getText().toString().trim().length() != 0
                    && profileImageData != null) {

                showProgressBar();

                callImageUploadApi();
            } else {
                Toast.makeText(getActivity(), "Incomplete profile info.",
                        Toast.LENGTH_LONG).show();
            }
            break;
        case R.id.fragment_create_profile_no_button:
            getFragmentManager().popBackStack(
                    AppConstant.FragmentStackName.CREATE_PROFILE_FRAGMENT,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE);
            break;
        case R.id.fragment_create_profile_cam_imageView:
            showSelectProfileImageDialog();
            break;

        default:
            break;
        }
    }

    private void showSelectProfileImageDialog() {
        String options[] = new String[] { "Click an image", "Record a video",
                "Upload from Gallery", "Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose an option");

        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // the user clicked on options[which]
                switch (which) {
                case 0:
                    onOpenCamera();
                    break;
                case 1:
                    onOpenVideo();
                    break;
                case 2:
                    onOpenGallary();
                    break;
                default:
                    break;
                }
            }
        });

        builder.show();
    }

    private void onOpenCamera() {
        ImageVideoData imageVideoData = new ImageVideoData() {

            @Override
            public void onDataReceive(CapturedData capturedData) {
                profileImageData = capturedData;
                camImageView.setImageBitmap(profileImageData.getLargeImage());

                filePath = profileImageData.getFilePath();
            }
        };

        replaceFragment(R.id.activity_avid_base_no_footer_second_layout,

        AppConstant.FragmentStackName.CAMERA_OPEN_FRAGMENT, "S1",
                new CameraOpenFragment(imageVideoData, true), 0, 0, 0, 0, true,
                false);
    }

    private void onOpenVideo() {

        ImageVideoManager.ImageVideoData video = new ImageVideoManager.ImageVideoData() {

            @Override
            public void onDataReceive(CapturedData capturedData) {
                profileImageData = capturedData;
                camImageView.setImageBitmap(profileImageData.getLargeImage());

            }
        };

        replaceFragment(R.id.activity_avid_base_no_footer_second_layout,
                AppConstant.FragmentStackName.CAMERA_OPEN_FRAGMENT, "S1",
                new CameraOpenFragment(video, false), 0, 0, 0, 0, true, false);

    }

    private void onOpenGallary() {

        if (Environment.getExternalStorageState().equals("mounted")) {
            Intent intent = new Intent();

            // intent.setType("image/*,video/*");
            intent.setType("image/* video/*");
            intent.setAction(Intent.ACTION_PICK);

            intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT,
                    AppConstant.VIDEO_CAPTURE_LIMIT);

            startActivityForResult(
                    intent,
                    AppConstant.CameraCaptureConstants.REQ_CODE_GALLERY_IMAGE_AND_VIDEO_PICK);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        try {

            if (resultCode == Activity.RESULT_OK) {

                switch (requestCode) {

                case AppConstant.CameraCaptureConstants.REQ_CODE_GALLERY_IMAGE_AND_VIDEO_PICK:

                    Uri selectedImage = data.getData();

                    String[] filePathColumn = { MediaColumns.DATA };
                    Cursor cursor = getActivity().getContentResolver().query(
                            selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

                    filePath = cursor.getString(columnIndex);
                    cursor.close();
                    if (Utils.isImageFile(filePath)) {
                        sendImageToCrop(filePath);
                    } else {
                        File imgFile = new File(filePath);
                        if (imgFile.exists()) {
                            CapturedData capturedVideo = new CapturedData();
                            capturedVideo.setFilePath(filePath);
                            Bitmap bMapSmall = ThumbnailUtils
                                    .createVideoThumbnail(
                                            filePath,
                                            MediaStore.Video.Thumbnails.MICRO_KIND);
                            Bitmap bMapLarge = ThumbnailUtils
                                    .createVideoThumbnail(
                                            filePath,
                                            MediaStore.Video.Thumbnails.FULL_SCREEN_KIND);
                            capturedVideo.setSmallImage(Utils
                                    .getCircularBitmap(bMapSmall));
                            capturedVideo.setLargeImage(Utils
                                    .getCircularBitmap(bMapLarge));
                            capturedVideo.setImage(false);
                            profileImageData = capturedVideo;
                            camImageView.setImageBitmap(profileImageData
                                    .getLargeImage());
                        }
                    }
                    break;
                case AppConstant.CameraCaptureConstants.CROP_RESULT_CODE:
                    try {
                        filePath = data.getStringExtra(CropImage.IMAGE_PATH);
                        if (filePath == null) {
                            return;
                        }
                        File imgFile = new File(filePath);
                        if (imgFile.exists()) {
                            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile
                                    .getAbsolutePath());
                            CapturedData capturedImage = new CapturedData();
                            capturedImage.setFilePath(filePath);
                            capturedImage.setLargeImage(myBitmap);
                            capturedImage.setSmallImage(ThumbnailUtils
                                    .extractThumbnail(myBitmap, 100, 100));
                            capturedImage.setImage(true);
                            profileImageData = capturedImage;
                            camImageView.setImageBitmap(profileImageData
                                    .getLargeImage());
                        }
                    } catch (NullPointerException e) {
                    }
                    break;
                default:
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendImageToCrop(String filePath2) {
        Intent intent = new Intent(getActivity(), CropImage.class);
        intent.putExtra(CropImage.IMAGE_PATH, filePath);
        intent.putExtra(CropImage.SCALE, true);
        intent.putExtra(CropImage.CIRCLE_CROP, "");
        startActivityForResult(intent,
                AppConstant.CameraCaptureConstants.CROP_RESULT_CODE);
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    private void callImageUploadApi() {
        boolean isImage = profileImageData.isImage();
        String mName = nameEditText.getText().toString().trim();

        AppHttpRequest request;
        if (isImage) {
            request = AppRequestBuilder.registerMeWithImage(filePath, mName,
                    new AppResponseListener<Profile>(Profile.class) {
                        @Override
                        public void onSuccess(Profile output) {
                            hideProgressBar();
                            performOnSuccess(output);
                        }

                        @Override
                        public void onError(ErrorObject error) {
                            hideProgressBar();
                            showTaost(error.getErrorMessage());
                        }
                    });
        } else {
            request = AppRequestBuilder.registerMeWithVideo(filePath, mName,
                    new AppResponseListener<Profile>(Profile.class) {

                        @Override
                        public void onSuccess(Profile output) {
                            hideProgressBar();
                            performOnSuccess(output);
                        }

                        @Override
                        public void onError(ErrorObject error) {
                            hideProgressBar();
                            showTaost(error.getErrorMessage());
                        }
                    });
        }
        AppRestClient.getClient().sendRequest(request, API_TAG);
    }

    private void performOnSuccess(Profile profile) {
        showTaost("Registered successfully.");

        PreferenceKeeper prefs = PreferenceKeeper.getInstance(getActivity());

        prefs.setProfileActivate(true);

        List<MediaContent> mediaContent = profile.getPics();
        prefs.setProfileRegistrationImageUrl(mediaContent.get(0).getUrl());

        prefs.setUserName(profile.getName());

        getFragmentManager().popBackStack(
                AppConstant.FragmentStackName.CREATE_PROFILE_FRAGMENT,
                FragmentManager.POP_BACK_STACK_INCLUSIVE);

        ((AvidFragmentBaseActivity) getActivity()).replaceFragment(
                new ProfileFragment(), prePosition, position);

        helper.bulkInsertMediaContent(mediaContent);

    }

    @Override
    public void onStop() {
        super.onStop();
        AppRestClient.getClient().cancelAll(API_TAG);
    }
}
