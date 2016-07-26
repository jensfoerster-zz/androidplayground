package de.my.playground.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Random;

public class MapFragment extends SupportMapFragment {

    private static final String TAG = "MapFragment";
    private GoogleMap mMap;
    private Marker mMarker = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMapAsync(mapCompleteListener);
        setRetainInstance(true);
    }

    private OnMapReadyCallback mapCompleteListener =
            new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    mMap = googleMap;

                    if (ActivityCompat.checkSelfPermission(
                            getContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(
                            getContext(),
                            Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        return;
                    }
                    mMap.setMyLocationEnabled(true);
                    mMap.setIndoorEnabled(false);
                    mMap.setBuildingsEnabled(false);
                    mMap.setTrafficEnabled(false);

                    LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
                    Criteria criteria = new Criteria();

                    Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
                    if (location != null) {
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                new LatLng(location.getLatitude(), location.getLongitude()), 13));
                        CameraPosition cameraPosition = new CameraPosition.Builder()
                                .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                                .zoom(17)                   // Sets the zoom
                                .build();                   // Creates a CameraPosition from the builder
                        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                    }
                    mMap.setOnMapClickListener(mMapClickListener);
                }
            };

    private GoogleMap.OnMapClickListener mMapClickListener = this::handleLocationClick;

    private boolean updateRunning = false;

    /***
     * This will set a marker on the map.
     * In addition it will send a telnet request to the mock location API
     * introduced by "mockLocationFix"
     * '''⌐(ಠ۾ಠ)¬'''
     *
     * @param latLng
     */
    private void handleLocationClick(final LatLng latLng) {
        if (updateRunning) return;
        updateRunning = true;

        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                if (mMarker != null) {
                    mMarker.setPosition(latLng);
                } else {
                    mMarker = mMap.addMarker(new MarkerOptions().position(latLng));
                }
                mMarker.setAlpha(.5f);
            }

            @Override
            protected Boolean doInBackground(Void[] objects) {
                try {
                    boolean success;
                    Thread.sleep(2000);
                    success = postToMockLocationFixup(latLng);
                    return success;
                } catch (Exception e) {
                    return false;
                } finally {
                    updateRunning = false;
                }
            }

            @Override
            protected void onPostExecute(Boolean success) {
                super.onPostExecute(success);
                if (success) {
                    if (mMarker != null) {
                        mMarker.setPosition(latLng);
                    } else {
                        mMarker = mMap.addMarker(new MarkerOptions().position(latLng));
                    }
                    mMarker.setAlpha(1.0f);
                } else {
                    if (mMarker != null) {
                        mMarker.setAlpha(.7f);
                    }
                }
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private boolean postToMockLocationFixup(LatLng latLng) {
        try {
            InetAddress serverAddr = InetAddress.getByName("127.0.0.1");
            Socket socket = new Socket(serverAddr, 5554);
            PrintWriter output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            output.println("geo fix " + latLng.longitude + " " + latLng.latitude);
            output.close();
            socket.close();
            //Log.v(TAG, "sent \"geo fix " + latLng.longitude + " " + latLng.latitude + "\"");
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

