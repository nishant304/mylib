package com.wecamchat.aviddev.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.wecamchat.aviddev.R;
import com.wecamchat.aviddev.util.PreferenceKeeper;

/**
 * This class shows the terms and conditions of wecam which is mandatory for a
 * user to accept otherwise he's not allowed to enter into the app.
 * 
 * @author shivani
 * 
 */
public class TermsAndConditionsActivity extends AvidBaseActivity {

    private Button acceptButton;
    private Button declineButton;

    private WebView termsAndConditionWebView;

    private PreferenceKeeper keeper;

    /**
     * Initializes the activity, manages the listeners of the UI components and
     * loads the webView
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_conditions);

        initViewControls();
        loadWebView();

        keeper = PreferenceKeeper.getInstance(this);
    }

    /**
     * This method initializes and provides settings to the webView
     */
    @SuppressLint("SetJavaScriptEnabled")
    private void loadWebView() {
        termsAndConditionWebView.getSettings().setJavaScriptEnabled(true);
        termsAndConditionWebView.getSettings().setTextZoom(35);
        loadUrlInWebView();
    }

    /**
     * This method loads the webView with the content received from the URL
     */
    private void loadUrlInWebView() {

        termsAndConditionWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                // hideProgressBar();
            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                    String description, String failingUrl) {
                // hideProgressBar();

                // showToastMessage("Some error occured.", false);
            }
        });
        termsAndConditionWebView
                .loadUrl(getString(R.string.terms_and_condition_url));

    }

    /**
     * Method to initialize the UI
     */
    private void initViewControls() {
        termsAndConditionWebView = (WebView) findViewById(R.id.activity_terms_and_conditions_webview);
        acceptButton = (Button) findViewById(R.id.activity_terms_and_conditions_accept_button);
        declineButton = (Button) findViewById(R.id.activity_terms_and_conditions_decline_button);

        acceptButton.setOnClickListener(this);
        declineButton.setOnClickListener(this);
    }

    /**
     * This method handles the click events on the UI components.
     */
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
        case R.id.activity_terms_and_conditions_accept_button:

            if (isNetworkAvailable()) {

                Bundle bundle = new Bundle();
                bundle.putBoolean("isFromBackSplash", true);
                bundle.putBoolean("isFromTOS", true);

                customLauncherActivity(AvidFragmentBaseActivity.class, bundle,
                        0, true);

                keeper.setAppUsed(true);
            } else {
                Toast.makeText(TermsAndConditionsActivity.this,
                        "Please check internet connection.", Toast.LENGTH_SHORT)
                        .show();
            }

            break;
        case R.id.activity_terms_and_conditions_decline_button:

            startSplashActivity();

            break;

        default:
            break;
        }
    }

    private void startSplashActivity() {
        Intent intent = new Intent(TermsAndConditionsActivity.this,
                SplashActivity.class);

        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startSplashActivity();
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        boolean errorInNetwork = activeNetworkInfo != null
                && activeNetworkInfo.isConnected();
        return errorInNetwork;

    }

    private void startBaseActivity() {
        Intent intent = new Intent(TermsAndConditionsActivity.this,
                AvidFragmentBaseActivity.class);

        Bundle bundle = new Bundle();
        bundle.putBoolean("isFromBackSplash", true);
        bundle.putBoolean("isFromTOS", true);

        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

}
