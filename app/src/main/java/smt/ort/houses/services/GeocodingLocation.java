package smt.ort.houses.services;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import smt.ort.houses.model.LocationAddress;

public class GeocodingLocation {
    public static final String BUNDLE_LOCATION_ADDRESS_KEY = "LOCATION_ADDRESS";
    public static final Double DEFAULT_LNG = -58.0282977;
    public static final Double DEFAULT_LAT = -32.6005598;
    private static final String TAG = "GeocodingLocation";

    public static void getAddressFromLocation(final String location, final Context context, final Handler handler) {
        new Thread() {
            @Override
            public void run() {
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                LocationAddress locationAddress = new LocationAddress();
                try {
                    List<Address> addressList = geocoder.getFromLocationName(location, 1);
                    if (addressList != null && addressList.size() > 0) {
                        Address address = addressList.get(0);
                        locationAddress = new LocationAddress(location, address.getLatitude(), address.getLongitude());
                    }
                } catch (IOException e) {
                    Log.e(TAG, "Unable to connect to Geocoder", e);
                } finally {
                    Message message = Message.obtain();
                    message.setTarget(handler);
                    message.what = 1;
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(BUNDLE_LOCATION_ADDRESS_KEY, locationAddress);
                    message.setData(bundle);
                    message.sendToTarget();
                }
            }
        }.start();
    }
}
