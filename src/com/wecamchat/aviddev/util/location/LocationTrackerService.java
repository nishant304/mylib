package com.wecamchat.aviddev.util.location;

import java.util.HashMap;
import java.util.Map;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.wecamchat.aviddev.api.ApiName;
import com.wecamchat.aviddev.api.ApiOutput;
import com.wecamchat.aviddev.api.ApiResponseHandler;
import com.wecamchat.aviddev.api.AvidApiClient;
import com.wecamchat.aviddev.api.AvidUrls;
import com.wecamchat.aviddev.api.ErrorObject;
import com.wecamchat.aviddev.api.io.ProfileEditInput;
import com.wecamchat.aviddev.api.io.ProfileEditOutput;
import com.wecamchat.aviddev.constant.AppConstant.LOCATION_CONSTANTS;
import com.wecamchat.aviddev.util.PreferenceKeeper;

/**
 * The purpose of class is to track user location and update server from time to
 * time
 * 
 * @author abhilash
 * 
 */
public class LocationTrackerService extends Service implements LocationListener {

    private final String TAG = "LocationTrackerService";
    private LocationManager locationManager;
    private LocationProvider gpsLocationProvider;
    private LocationProvider networkLocationProvider;
    private boolean isGpsEnabled = false;
    private boolean isNetworkEnabled = false;
    private PreferenceKeeper keeper;
    private AvidApiClient caller;

    ApiResponseHandler apiResponseHandler;

    @Override
    public void onLocationChanged(Location location) {
        // System.out.println("***********************************************************************");
        // System.out.println("New Location  Lattitude :: "+location.getLatitude()
        // + " Longitude :: "+ location.getLongitude());
        //
        Location oldLocation = keeper.getUserLocationInLatLng();
        // System.out.println("Old Location  Lattitude :: "+oldLocation.getLatitude()
        // + " Longitude :: "+oldLocation.getLongitude());
        //
        // System.out.println("***********************************************************************");
        //

        if (oldLocation.getLatitude() != location.getLatitude()
                || oldLocation.getLongitude() != location.getLongitude()) {
            // update location to server
            updateLocation(location);
        }
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "loc service started");
        caller = AvidApiClient.getInstance();
        String currentProvider = "";
        keeper = PreferenceKeeper.getInstance(getApplicationContext());
        Log.e(TAG, "Service started");
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        gpsLocationProvider = locationManager
                .getProvider(LocationManager.GPS_PROVIDER);
        networkLocationProvider = locationManager
                .getProvider(LocationManager.NETWORK_PROVIDER);

        if (gpsLocationProvider != null
                && locationManager
                        .isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            isGpsEnabled = true;
        }

        if (networkLocationProvider != null
                && locationManager
                        .isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            isNetworkEnabled = true;
        }

        if (isNetworkEnabled) {
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    LOCATION_CONSTANTS.MINIMUM_TIME_INTERVAL,
                    LOCATION_CONSTANTS.MINIMUM_DISTANCE, this);
            currentProvider = LocationManager.NETWORK_PROVIDER;
        }

        if (isGpsEnabled) {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    LOCATION_CONSTANTS.MINIMUM_TIME_INTERVAL,
                    LOCATION_CONSTANTS.MINIMUM_DISTANCE, this);
            currentProvider = LocationManager.GPS_PROVIDER;
        }

        Location lastKnownLocation = locationManager
                .getLastKnownLocation(currentProvider);
        if (lastKnownLocation != null)
            updateLocation(lastKnownLocation);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "service destroyed");
        locationManager.removeUpdates(this);
        super.onDestroy();
    }

    /**
     * make api call
     * 
     * @param location
     */
    private void updateLocation(Location location) {
        Log.e(TAG, "location updated");
        if (keeper.isProfileActivate()) {
            try {
                keeper.setUserLocation(location);

                initializeApiResponseHandler();

                ProfileEditInput input = new ProfileEditInput();

                input.setLat(location.getLatitude());
                input.setLng(location.getLongitude());

                caller.post(1, AvidUrls.BASE_URL + AvidUrls.PROFILE_EDIT,
                        input.getParams(), getHeaders(), ApiName.profileEdit,
                        new ProfileEditOutput(), getApiResponseHandler());
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }

    }

    private void initializeApiResponseHandler() {
        apiResponseHandler = new ApiResponseHandler() {

            @Override
            public void onSuccess(int reqId, ApiOutput output, ApiName type) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onFailure(int reqId, ErrorObject errorObject,
                    ApiName type) {
                // TODO Auto-generated method stub

            }

            @Override
            public void VolleyError(int reqId,
                    com.android.volley.VolleyError error, ApiName type) {
                // TODO Auto-generated method stub
            }
        };

    }

    private ApiResponseHandler getApiResponseHandler() {

        return apiResponseHandler;
    }

    /**
     * This method is used to return map with header informations for api.
     * 
     * @return Map
     */
    public Map<String, String> getHeaders() {

        Map<String, String> params = new HashMap<String, String>();
        // put content type here
        params.put("Content-Type", "application/x-www-form-urlencoded");

        PreferenceKeeper keeper = PreferenceKeeper.getInstance(this);

        String key = keeper.getProfileCookieKey();
        String value = keeper.getProfileCookieValue();

        if ((key != null && !key.equalsIgnoreCase(""))
                && (value != null && !value.equalsIgnoreCase(""))) {
            params.put(key, value);
        }

        return params;

    }

}
