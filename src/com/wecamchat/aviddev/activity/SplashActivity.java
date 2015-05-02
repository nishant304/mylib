package com.wecamchat.aviddev.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.wecamchat.aviddev.R;
import com.wecamchat.aviddev.api.ErrorObject;
import com.wecamchat.aviddev.constant.AppConstant;
import com.wecamchat.aviddev.httpClient.AppRequestBuilder;
import com.wecamchat.aviddev.httpClient.AppResponseListener;
import com.wecamchat.aviddev.httpClient.AppRestClient;
import com.wecamchat.aviddev.util.view.CustomEditText;

/**
 * This is Splash Screen. Splash Screen show black screen for 1500 ms after it
 * play video.
 * 
 * @author gaurav
 * 
 */
public class SplashActivity extends AvidBaseActivity {

    protected static final String TAG = "SplashActivity";
    private static final String API_TAG = SplashActivity.class.getName();
    public final int SPLASH_DISPLAY_TIME = 1500;
    private VideoView videoView;
    private RelativeLayout acOptionRelativeLayout;
    private MoveToHome handler;
    private ImageView logoImageView;
    private ImageView progressBarImageView;
    private Animation fadeInAnimation;
    private Animation fadeoutAnimation;
    private Dialog accRetrivalDialog;
    private Dialog smsVerificationDialog;
    private String numberEntered = "";
    private String emailEntered = "";
    private CustomEditText mobileCustomEditText;
    private CustomEditText emailCustomEditText;
    private CustomEditText vCCustomEditText;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        handler = new MoveToHome();
        setContentView(R.layout.activity_splash_screen);
        initUi();
        handler.sendEmptyMessageDelayed(0, SPLASH_DISPLAY_TIME);
        loadAnimations();
    }

    private void initUi() {

        Log.d(TAG, "method call from init ui.");
        logoImageView = (ImageView) findViewById(R.id.logo_imageview);
        videoView = (VideoView) findViewById(R.id.splash_video_view);
        acOptionRelativeLayout = (RelativeLayout) findViewById(R.id.account_option_layout);
        // TODO::Tushar Visiblity of progress bar can be integrated with
        // restClient
        progressBarImageView = (ImageView) findViewById(R.id.activity_splash_progress_bar_imageview);
    }

    /**
     * Move to the next activity once the splash screen stays for the defined
     * period of time
     */

    private class MoveToHome extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (!getPreferenceKeeper().isAppUsed()) {
                showFadeInAnim();
                String uriPath = AppConstant.getVideoPath(SplashActivity.this,
                        R.raw.mc_loading_video);
                customVideoLauncher(videoView, uriPath, true);
            } else {
                showColdStartFadeInAnim();
                String uriPath = AppConstant.getVideoPath(SplashActivity.this,
                        R.raw.cold_start_video);
                customVideoLauncher(videoView, uriPath, false);
            }
        }
    }

    private void showFadeInAnim() {
        videoView.setVisibility(View.VISIBLE);
        videoView.setAnimation(fadeInAnimation);
        acOptionRelativeLayout.setVisibility(View.VISIBLE);
        logoImageView.setVisibility(View.VISIBLE);
        acOptionRelativeLayout.setAnimation(fadeInAnimation);
        logoImageView.setAnimation(fadeInAnimation);
    }

    private void showFadeOutAnim() {
        acOptionRelativeLayout.setAnimation(fadeoutAnimation);
        logoImageView.setAnimation(fadeoutAnimation);
        videoView.setAnimation(fadeoutAnimation);
        acOptionRelativeLayout.setVisibility(View.INVISIBLE);
        logoImageView.setVisibility(View.INVISIBLE);
        videoView.setVisibility(View.INVISIBLE);
    }

    /**
     * Same method is used when click on I have account image view.
     */
    public void onClickIHaveAccount(View v) {
        showAccountRecoverDialog();
        logoImageView.setVisibility(View.GONE);
        acOptionRelativeLayout.setVisibility(View.GONE);
    }

    /**
     * Same method is used when click on I am new image view.
     */
    public void onClickIAmNew(View v) {
        showFadeOutAnim();
        customLauncherActivity(TermsAndConditionsActivity.class, null, 0, true);
    }

    public void onClickAccountRecoverNoButton(View v) {
        logoImageView.setVisibility(View.VISIBLE);
        acOptionRelativeLayout.setVisibility(View.VISIBLE);
        accRetrivalDialog.dismiss();
    }

    public void onClickAccountRecoverYesButton(View v) {
        if (emailEntered != null && emailEntered.length() > 1) {
            if (mobileCustomEditText.isFocused()) {
                numberEntered = mobileCustomEditText.getText().toString()
                        .trim();
            } else {
                numberEntered = "";
            }
        } else {
            numberEntered = mobileCustomEditText.getText().toString().trim();
        }

        if (numberEntered != null && numberEntered.length() > 1) {
            if (emailCustomEditText.isFocused()) {
                emailEntered = emailCustomEditText.getText().toString().trim();
            } else {
                emailEntered = "";
            }
        } else {
            emailEntered = emailCustomEditText.getText().toString().trim();
        }

        if (isEnteredDataValid(emailEntered, numberEntered)) {
            recoverAccount(emailEntered, numberEntered);
        }

    }

    public void onClickVCDialogNoButton(View v) {
        smsVerificationDialog.dismiss();
        /**
         * handler activates looping video
         */
        handler.handleMessage(null);
    }

    public void onClickVCDialogYesButton(View v) {

        String verificationCode = vCCustomEditText.getText().toString().trim();
        String email = null;
        String number = null;
        if (emailEntered != null && emailEntered.length() > 1) {
            email = emailEntered;
        } else {
            number = numberEntered;
        }

        AppRestClient.getClient().cancelAll(API_TAG);
        AppRestClient.getClient().sendRequest(
                AppRequestBuilder.userVerify(verificationCode, email, number,
                        new AppResponseListener<String>(String.class) {

                            @Override
                            public void onSuccess(String message) {
                                showToast(message);
                                getPreferenceKeeper().setProfileActivate(true);
                                getPreferenceKeeper().setAccountVerified(true);
                                getPreferenceKeeper().setAppUsed(true);
                                customLauncherActivity(
                                        AvidFragmentBaseActivity.class, null,
                                        0, true);
                                hideProgressBar();
                                SplashActivity.this.finish();
                            }

                            @Override
                            public void onError(ErrorObject error) {
                                showToast(error.getErrorMessage());
                                hideProgressBar();
                                /**
                                 * handler activates looping video
                                 */
                                handler.handleMessage(null);
                            }
                        }), API_TAG);
        smsVerificationDialog.dismiss();
        showProgressBar();

    }

    /**
     * This method is used to load animation.
     */
    private void loadAnimations() {
        fadeInAnimation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fadein);
        fadeoutAnimation = AnimationUtils.loadAnimation(
                getApplicationContext(), R.anim.fadeout);
    }

    @Override
    protected void onStop() {
        AppRestClient.getClient().cancelAll(API_TAG);
        super.onStop();
    }

    private void showAccountRecoverDialog() {
        accRetrivalDialog = new Dialog(this);
        accRetrivalDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        View layout = LayoutInflater.from(this).inflate(
                R.layout.layout_account_have, null);
        accRetrivalDialog.setContentView(layout);

        accRetrivalDialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));
        emailCustomEditText = (CustomEditText) accRetrivalDialog
                .findViewById(R.id.layout_account_retrieval_email_editText);
        mobileCustomEditText = (CustomEditText) accRetrivalDialog
                .findViewById(R.id.layout_account_retrieval_mobile_number_editText);

        emailCustomEditText
                .setOnFocusChangeListener(new OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (!hasFocus) {
                            emailCustomEditText.setTextColor(getResources()
                                    .getColor(R.color.grey_color));
                        } else {
                            emailCustomEditText
                                    .setTextColor(getResources().getColor(
                                            R.color.acc_retrieval_text_color));
                        }
                    }
                });

        mobileCustomEditText
                .setOnFocusChangeListener(new OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (!hasFocus) {
                            mobileCustomEditText.setTextColor(getResources()
                                    .getColor(R.color.grey_color));
                        } else {
                            mobileCustomEditText
                                    .setTextColor(getResources().getColor(
                                            R.color.acc_retrieval_text_color));
                        }
                    }
                });

        ImageView countryImageView = (ImageView) accRetrivalDialog
                .findViewById(R.id.layout_account_retrieval_country_imageview);

        setDefaultCountryCodeAndFlag(countryImageView, mobileCustomEditText);

        accRetrivalDialog.setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                logoImageView.setVisibility(View.VISIBLE);
                acOptionRelativeLayout.setVisibility(View.VISIBLE);
            }
        });
        accRetrivalDialog.show();
    }

    private void showVerificationDialog() {
        smsVerificationDialog = new Dialog(this);
        smsVerificationDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        View layout = LayoutInflater.from(this).inflate(
                R.layout.layout_sms_verification, null);
        smsVerificationDialog.setContentView(layout);

        smsVerificationDialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));
        vCCustomEditText = (CustomEditText) smsVerificationDialog
                .findViewById(R.id.layout_sms_verification_code_editText);

        smsVerificationDialog.show();
    }

    private void recoverAccount(final String emailEntered,
            final String numberEntered) {
        String email = null;
        String number = null;
        if (emailEntered != null && emailEntered.length() > 1) {
            email = emailEntered;
        } else {
            number = numberEntered;
        }
        AppRestClient.getClient().cancelAll(API_TAG);
        AppRestClient.getClient().sendRequest(
                AppRequestBuilder.userRecover(email, number,
                        new AppResponseListener<String>(String.class) {
                            @Override
                            public void onSuccess(String message) {
                                accRetrivalDialog.dismiss();
                                showToast(message);
                                showVerificationDialog();
                            }

                            @Override
                            public void onError(ErrorObject error) {
                                showToast(error.getErrorMessage());
                            }
                        }), API_TAG);

    }

    private boolean isEnteredDataValid(String emailEntered, String numberEntered) {
        if (emailEntered.equalsIgnoreCase("")
                && numberEntered.equalsIgnoreCase("")) {
            showToast("Please enter either phone number or email id.");
            return false;
        }
        if (emailEntered.length() > 0
                && !com.wecamchat.aviddev.util.Validator
                        .isValidEmail(emailEntered)) {
            showToast("Please enter valid email id.");
            return false;
        }
        return true;
    }

    @Override
    protected void showProgressBar() {
        progressBarImageView.setVisibility(View.VISIBLE);
        AnimationDrawable drawable = (AnimationDrawable) progressBarImageView
                .getDrawable();
        drawable.start();
    }

    @Override
    protected void hideProgressBar() {
        AnimationDrawable drawable = (AnimationDrawable) progressBarImageView
                .getDrawable();
        drawable.stop();
        progressBarImageView.setVisibility(View.GONE);
    }

    protected void videoCompleted(MediaPlayer mp) {
        showColdStartFadeOutAnim();
        Bundle bundle = new Bundle();
        bundle.putBoolean("isFromBackSplash", true);
        customLauncherActivity(AvidFragmentBaseActivity.class, bundle, 0, true);
    }

    private void showColdStartFadeInAnim() {
        videoView.setAnimation(fadeInAnimation);
        videoView.setVisibility(View.VISIBLE);
    }

    private void showColdStartFadeOutAnim() {
        videoView.setAnimation(fadeoutAnimation);
        videoView.setVisibility(View.INVISIBLE);
    }

    /**
     * This method is used to play video.
     * 
     * @param videoView
     * @param uriPath
     * @param isLoopMode
     *            manage video play in loop mode or non loop mode.
     */
    public void customVideoLauncher(final VideoView videoView,
            final String uriPath, final boolean isLoopMode) {
        Uri uri = Uri.parse(uriPath);
        videoView.setVideoURI(uri);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(isLoopMode);
                mp.start();
            }
        });

        videoView.setOnCompletionListener(new OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                videoCompleted(mp);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
