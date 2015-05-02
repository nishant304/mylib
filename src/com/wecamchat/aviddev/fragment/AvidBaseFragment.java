package com.wecamchat.aviddev.fragment;

import java.util.HashMap;
import java.util.Map;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.wecamchat.aviddev.R;
import com.wecamchat.aviddev.activity.AvidFragmentBaseActivity;
import com.wecamchat.aviddev.api.ApiName;
import com.wecamchat.aviddev.api.ApiOutput;
import com.wecamchat.aviddev.api.ApiResponseHandler;
import com.wecamchat.aviddev.api.ErrorObject;
import com.wecamchat.aviddev.constant.AppConstant;
import com.wecamchat.aviddev.constant.AppConstant.DialogConstant;
import com.wecamchat.aviddev.model.bo.Profile;
import com.wecamchat.aviddev.util.PreferenceKeeper;

public abstract class AvidBaseFragment extends Fragment implements

OnClickListener {

    private static PreferenceKeeper prefs;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    protected static final String TAG = "AvidBaseFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private ApiResponseHandler apiResponseHandler;
    private DisplayImageOptions overlayImageOptions;

    /**
     * @return the apiResponseHandler
     */
    public ApiResponseHandler getApiResponseHandler() {
        return apiResponseHandler;
    }

    /**
     * @param apiResponseHandler
     *            the apiResponseHandler to set
     */
    public void setApiResponseHandler(ApiResponseHandler apiResponseHandler) {
        this.apiResponseHandler = apiResponseHandler;
    }

    /**
     * This method is used to return map with header informations for api.
     * 
     * @return Map
     */
    public Map<String, String> getHeaders() {

        Map<String, String> headerParams = new HashMap<String, String>();
        // put content type here
        headerParams.put("Content-Type", "application/x-www-form-urlencoded");
        String key = PreferenceKeeper.getInstance(getActivity())
                .getProfileCookieKey();
        String value = PreferenceKeeper.getInstance(getActivity())
                .getProfileCookieValue();

        if ((key != null && !key.equalsIgnoreCase(""))
                && (value != null && !value.equalsIgnoreCase(""))) {
            headerParams.put(key, value);
        }

        Log.d(TAG, "header value :: " + headerParams.toString());

        return headerParams;

    }

    public boolean checkAndManageLocationHandler() {
        LocationManager locMgr = (LocationManager) getActivity()
                .getSystemService(Context.LOCATION_SERVICE);
        boolean gpsEnabled = locMgr
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean networkEnabled = locMgr
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gpsEnabled == false && networkEnabled == false) {
            showDoubleButtonDialog("",
                    getString(R.string.location_services_not_enabled_msg),
                    getString(R.string.ok), getString(R.string.capital_cancel),
                    DialogConstant.ENABLE_LOCATION_SERVICES);
            return false;
        }
        return true;
    }

    public boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(getActivity());
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode,
                        getActivity(), PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {

                // TODO make handling for this case
            }
            return false;
        }
        return true;
    }

    public void showDoubleButtonDialog(final String title,
            final String message, final String positiveButtonText,
            final String negativeButtonText, final int type) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(message)
                .setTitle(title)
                .setCancelable(true)
                .setPositiveButton(positiveButtonText,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                                showSettingsScreen();

                            }
                        })
                .setNegativeButton(negativeButtonText,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                    int which) {

                                dialog.dismiss();

                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();

    }

    /**
     * Open setting screen of device.
     */

    public void showSettingsScreen() {
        startActivity(new Intent(
                android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
    }

    /*
     * Fragment replacement code using content id on pager
     */

    public void replaceFragment(int containerId,
            String fragmentTagToBeAddedToBackStack,
            String fragmentTagToBeAdded, Fragment className, int enter,
            int exit, int enterPop, int exitPop,
            boolean isNextFragmentNeedsTobeAdded, boolean isCommitIsStateLoss) {

        // TODO: remove these values

        // enter = 0;
        // exit = 0;
        // enterPop = 0;
        // exitPop = 0;
        // ********************************

        FragmentTransaction fragmentTransaction = this.getFragmentManager()
                .beginTransaction();
        if (fragmentTagToBeAddedToBackStack == null
                && isNextFragmentNeedsTobeAdded) {
            return;
        }

        if (fragmentTagToBeAdded == null) {
            return;
        }

        if (fragmentTagToBeAddedToBackStack
                .equalsIgnoreCase(fragmentTagToBeAdded)) {
            return;
        }
        fragmentTransaction.setCustomAnimations(enter, exit, enterPop, exitPop);

        fragmentTransaction.replace(containerId, className);
        if (isNextFragmentNeedsTobeAdded) {
            fragmentTransaction.addToBackStack(fragmentTagToBeAddedToBackStack);
        }
        if (isCommitIsStateLoss) {
            fragmentTransaction.commitAllowingStateLoss();
        } else {
            fragmentTransaction.commit();
        }

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void removeFragmentWithAnimation(String mFragmentName) {

        if (getFragmentManager() != null) {
            getFragmentManager().popBackStack(mFragmentName,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    public abstract boolean onBackPressed();

    /**
     * The purpose of this method is to initialize BaseApiResponseHandler.
     */
    protected void initializeBaseApiResponseHandler() {
        prefs = PreferenceKeeper.getInstance(getActivity());
        setApiResponseHandler(new ApiResponseHandler() {

            @Override
            public void onSuccess(int reqId, ApiOutput output, ApiName type) {
                apiSuccessResponse(reqId, output, type);
            }

            @Override
            public void setCookie(Map<String, String> cookie) {
                // TODO Auto-generated method stub
                super.setCookie(cookie);

                String cookieValue = cookie.get("set-cookie");
                cookieValue = cookieValue.replace("; Path=/; HttpOnly", "");

                Log.d(TAG, "key :: set-cookie");
                Log.d(TAG, "cookie value :: " + cookieValue);

                prefs.setProfileCookieKey("cookie");
                prefs.setProfileCookieValue(cookieValue);
            }

            @Override
            public void onFailure(int reqId, ErrorObject errorObject,
                    ApiName type) {
                showTaost(errorObject.getErrorMessage());

            }

            @Override
            public void VolleyError(int reqId,
                    com.android.volley.VolleyError error, ApiName type) {
                ErrorObject errorObject = new ErrorObject();

                if (error instanceof NetworkError) {

                } else if (error instanceof NoConnectionError) {

                    errorObject
                            .setErrorCode(AppConstant.IErrorCode.notInternetConnErrorCode);
                    errorObject
                            .setErrorMessage(AppConstant.IErrorMessage.notInternetConnectMessage);
                }
                apiFailedResponse(reqId, errorObject, type);

            }

        });

        /**
         * new ApiResponseHandler() {
         * 
         * @Override public void onSuccess(int reqId, ApiOutput output, ApiName
         *           type) { // TODO Auto-generated method stub
         * 
         *           }
         * @Override public void onFailure(int reqId, VolleyError error, ApiName
         *           type) { // TODO Auto-generated method stub
         * 
         *           }
         * @Override public void setCookie(Map<String, String> mapCookie) {
         * 
         * 
         *           // Set<String> keys = mapHeader.keySet(); // for (String
         *           key : keys) { // System.out.println(key + " = " +
         *           mapHeader.get(key)); // }
         * 
         *           prefs.setProfileCookieKey("set-cookie");
         *           prefs.setProfileCookieValue(mapCookie.get("set-cookie"));
         * 
         *           } });
         * 
         * 
         * 
         * 
         */

    }

    protected void apiFailedResponse(int reqId, ErrorObject errorObject,
            ApiName type) {
        hideProgressBar();
        showTaost(errorObject.getErrorMessage());
    }

    protected void apiSuccessResponse(int reqId, ApiOutput output, ApiName type) {
        hideProgressBar();
        System.out.println("Move media ");
        switch (type) {
        case profile_register:
            break;

        default:
            break;
        }

    }

    /**
     * Method is used to hide progressbar.
     */
    public void hideProgressBar() {
        if (getActivity() != null) {
            ((AvidFragmentBaseActivity) getActivity()).hideProgressBar();
        }
    }

    /**
     * Method is used to show progressbar.
     */
    public void showProgressBar() {
        if (getActivity() != null) {
            ((AvidFragmentBaseActivity) getActivity()).showProgressBar();
        }
    }

    protected void showTaost(String message) {
        if (getActivity() != null) {
            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        }
    }

    protected void saveUserData(Profile result) {
        prefs = PreferenceKeeper.getInstance(getActivity());
        prefs.saveUserData(result);
    }

    public DisplayImageOptions getOptionImageLoader() {

        overlayImageOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.user_image).cacheInMemory(true)
                .cacheOnDisc(true).considerExifParams(true).build();
        return overlayImageOptions;
    }

}
