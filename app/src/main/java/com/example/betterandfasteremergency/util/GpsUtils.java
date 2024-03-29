package com.example.betterandfasteremergency.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.location.LocationManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class GpsUtils {
    /* access modifiers changed from: private */
    public Context context;
    private LocationManager locationManager;
    private LocationRequest locationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private SettingsClient mSettingsClient;

    public interface onGpsListener {
        void gpsStatus(boolean z);
    }

    public GpsUtils(Context context2) {
        this.context = context2;
        this.locationManager = (LocationManager) context2.getSystemService(Context.LOCATION_SERVICE);
        this.mSettingsClient = LocationServices.getSettingsClient(context2);
        LocationRequest create = LocationRequest.create();
        this.locationRequest = create;
        create.setPriority(100);
        this.locationRequest.setInterval(10000);
        this.locationRequest.setFastestInterval(2000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(this.locationRequest);
        this.mLocationSettingsRequest = builder.build();
        builder.setAlwaysShow(true);
    }

    public void turnGPSOn(final onGpsListener onGpsListener2) {
        if (!this.locationManager.isProviderEnabled("gps")) {
            this.mSettingsClient.checkLocationSettings(this.mLocationSettingsRequest).addOnSuccessListener((Activity) this.context, new OnSuccessListener<LocationSettingsResponse>() {
                public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                    onGpsListener ongpslistener = onGpsListener2;
                    if (ongpslistener != null) {
                        ongpslistener.gpsStatus(true);
                    }
                }
            }).addOnFailureListener((Activity) this.context, (OnFailureListener) new OnFailureListener() {
                public void onFailure(Exception e) {
                    int statusCode = ((ApiException) e).getStatusCode();
                    if (statusCode == 6) {
                        try {
                            ((ResolvableApiException) e).startResolutionForResult((Activity) GpsUtils.this.context, 1001);
                        } catch (IntentSender.SendIntentException e2) {
                            Log.i("ContentValues", "PendingIntent unable to execute request.");
                        }
                    } else if (statusCode == 8502) {
                        Log.e("ContentValues", "Location settings are inadequate, and cannot be fixed here. Fix in Settings.");
                        Toast.makeText((Activity) GpsUtils.this.context, "Location settings are inadequate, and cannot be fixed here. Fix in Settings.", 1).show();
                    }
                }
            });
        } else if (onGpsListener2 != null) {
            onGpsListener2.gpsStatus(true);
        }
    }
}