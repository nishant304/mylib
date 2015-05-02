package com.wecamchat.aviddev.util.location;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class LocationUtil {

    private LocationManager lm;

    private boolean isGpsEnabled;

    private boolean isNetworkLocationEnabled;

    private GetLastLocation lastLocationRunable;

    private Handler handler;

    protected LocationResult locationResult;

    public void getLocation(Context context, LocationResult result) {
        this.context = context;
        this.locationResult = result;
        lm = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);

        // Exceptions will be thrown if provider is not permitted.
        try {
            isGpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
            // Handle exception
        }

        try {
            isNetworkLocationEnabled = lm
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
            // Handle exception
        }

        // Queue GPS location request
        if (isGpsEnabled)
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
                    locationListenerGps);

        // Queue network location request
        if (isNetworkLocationEnabled)
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0,
                    locationListenerNetwork);

        // Create a task that retreives the last known location for the user in
        // case we can't get the current location.
        lastLocationRunable = new GetLastLocation();

        if (!isNetworkLocationEnabled && !isGpsEnabled) {
            // Both network and GPS are disabled.
            // We need to ask the user to enable at least one of them
            // Toast.makeText(MainActivity.this,
            // "Both network and GPS are disabled.", Toast.LENGTH_LONG)
            // .show();

        } else {
            // Use the last known location if we cannot obtain the user's
            // location in 2000 msec
            handler = new Handler();
            handler.postDelayed(lastLocationRunable, 4000);
        }

    }

    public void cancelTimer() {
        handler.removeCallbacks(lastLocationRunable);
        lm.removeUpdates(locationListenerGps);
        lm.removeUpdates(locationListenerNetwork);
    }

    LocationListener locationListenerGps = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            cancelTimer(); // Cancels the timer
            locationResult.gotLocation(location);
            lm.removeUpdates(this);
            lm.removeUpdates(locationListenerNetwork);
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
    };

    LocationListener locationListenerNetwork = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            cancelTimer(); // Cancels the timer
            locationResult.gotLocation(location);
            lm.removeUpdates(this);
            lm.removeUpdates(locationListenerGps);
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
    };

    private ProgressDialog dialog;

    private Context context;

    class GetLastLocation implements Runnable {

        @Override
        public void run() {
            Log.i("TAG", "Using last location");
            lm.removeUpdates(locationListenerGps);
            lm.removeUpdates(locationListenerNetwork);

            // Try to get last known gps location
            Location networkLocation = null, gpsLocation = null;
            if (isGpsEnabled)
                gpsLocation = lm
                        .getLastKnownLocation(LocationManager.GPS_PROVIDER);
            // Try to get last known network location
            if (isNetworkLocationEnabled)
                networkLocation = lm
                        .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            // if there are both values use the latest one
            if (gpsLocation != null && networkLocation != null) {
                if (gpsLocation.getTime() < networkLocation.getTime())
                    locationResult.gotLocation(gpsLocation);
                else
                    locationResult.gotLocation(networkLocation);
                return;
            }

            if (gpsLocation != null) {
                locationResult.gotLocation(gpsLocation);
                return;
            }
            if (networkLocation != null) {
                locationResult.gotLocation(networkLocation);
                return;
            }
            locationResult.gotLocation(null);
        }
    }

}
