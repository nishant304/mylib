package com.wecamchat.aviddev.activity;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.wecamchat.aviddev.R;
import com.wecamchat.aviddev.cp.AvidCPHelper;
import com.wecamchat.aviddev.dataadapter.BrowseDataAdapter;
import com.wecamchat.aviddev.dataadapter.MapDataAdapter;
import com.wecamchat.aviddev.util.PreferenceKeeper;

public abstract class AvidBaseActivity extends Activity implements
        LoaderManager.LoaderCallbacks<Cursor>, OnClickListener {

    private static final String TAG = "AvidBaseActivity";
    private PreferenceKeeper preferenceKeeper;
    private static long lastActiveTime = 0l;
    private final long TIME_OFFSET = 1000l;
    protected boolean notToProceed = true;

    /**
     * @return the preferenceKeeper
     */
    public PreferenceKeeper getPreferenceKeeper() {
        return preferenceKeeper;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "call avid base activity ");
        initializePrefsKeeper();
        if (toShowPasscodeActivity()) {
            customLauncherActivity(PassCodeActivity.class, null, 0, false);
        }
        getLoaderManager().initLoader(0, null, this);
    }

    private boolean toShowPasscodeActivity() {
        return notToProceed = (getPreferenceKeeper().isPasscodeEnabled() && Calendar
                .getInstance().getTimeInMillis() - lastActiveTime > TIME_OFFSET)
                && !getClass().equals(PassCodeActivity.class);
    }

    private void initializePrefsKeeper() {
        if (preferenceKeeper == null)
            preferenceKeeper = PreferenceKeeper.getInstance(this);
    }

    /**
     * This method is used to launch activity based on parameters.
     * 
     * @param classObj
     * @param bundle
     * @param delayInMilliSecond
     * @param isFinish
     */
    public void customLauncherActivity(
            final Class<? extends Activity> classObj, final Bundle bundle,
            final int delayInMilliSecond, final boolean isFinish) {
        if (delayInMilliSecond == 0) {
            launchNewActivity(classObj, bundle);
        } else {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    launchNewActivity(classObj, bundle);
                }
            }, delayInMilliSecond);
        }
        // finish current activity based on flag.
        if (isFinish)
            finish();
    }

    protected void launchNewActivity(Class<?> classObj, Bundle bundle) {
        Intent intent = new Intent(getBaseContext(), classObj);
        // put data in intenet
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        // fadein and fadeout animation
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        AvidCPHelper helper = AvidCPHelper.getInstance(AvidBaseActivity.this);
        Cursor browseCursor = helper.getCursor(
                AvidCPHelper.Uris.URI_BROWSE_USER, null, null, null, null);
        // check data is already exists.
        if (browseCursor == null || browseCursor.getCount() == 0) {
            new BrowseDataAdapter(getApplicationContext()).inserDummyDataInDb();
        }
        Cursor mapCursor = helper.getCursor(AvidCPHelper.Uris.URI_MAP_USER,
                null, null, null, null);
        // check data is already exists.
        if (mapCursor == null || mapCursor.getCount() == 0) {
            new MapDataAdapter(getApplicationContext()).inserDummyDataInDb();
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onLoaderReset(Loader<Cursor> arg0) {
        // TODO Auto-generated method stub

    }

    protected void showToast(final String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    // TODO::
    protected void showProgressBar() {
    };

    // TODO::
    protected void hideProgressBar() {

    }

    public void setDefaultCountryCodeAndFlag(final ImageView countryImageView,
            final EditText phoneNumberEditText) {
        String countryID = "";
        String countryZipCode = "";
        TelephonyManager manager = (TelephonyManager) this
                .getSystemService(Context.TELEPHONY_SERVICE);
        String phoneNumber = manager.getLine1Number();
        // getNetworkCountryIso
        countryID = manager.getSimCountryIso().toUpperCase();
        String[] rl = this.getResources().getStringArray(R.array.CountryCodes);
        for (int i = 0; i < rl.length; i++) {
            String[] g = rl[i].split(",");
            if (g[1].trim().equals(countryID.trim())) {
                countryZipCode = g[0];
                break;
            }
        }
        // check country zip code
        if (countryZipCode != null && countryZipCode.trim().length() > 0) {
            phoneNumberEditText
                    .setText("+" + countryZipCode + "" + phoneNumber);
            phoneNumberEditText.setSelection(phoneNumberEditText.getText()
                    .length());
        }

        // Load drawable dynamically from country code
        if (countryID != null && countryID.trim().length() > 0) {
            String drawableName = "flag_"
                    + countryID.toLowerCase(Locale.ENGLISH);
            countryImageView.setImageResource(getResId(drawableName));
        }
    }

    private int getResId(String drawableName) {

        try {
            Class<com.wecamchat.aviddev.R.drawable> res = R.drawable.class;
            Field field = res.getField(drawableName);
            int drawableId = field.getInt(null);
            return drawableId;
        } catch (Exception e) {
            Log.e("COUNTRYPICKER", "Failure to get drawable id.", e);
        }
        return -1;
    }

    @Override
    protected void onPause() {
        super.onPause();
        lastActiveTime = Calendar.getInstance().getTimeInMillis();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // if (getPreferenceKeeper().isPasscodeEnabled()
        // && isLastActiveTimeExpired()) {
        // customLauncherActivity(PassCodeActivity.class, null, 0, false);
        // }
    }

}
