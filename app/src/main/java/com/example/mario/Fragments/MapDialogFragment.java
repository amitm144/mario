package com.example.mario.Fragments;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.example.mario.Model.User;
import com.example.mario.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapDialogFragment extends DialogFragment {
    private GoogleMap googleMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        // Retrieve the GoogleMap object asynchronously
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.maps);
        mapFragment.getMapAsync(googleMap -> {
            this.googleMap = googleMap;
            // Set the map location when the GoogleMap object is initialized
            Bundle bundle = getArguments();
            User user = (User) bundle.getSerializable("user");
            setMapLocation(user.getLat(), user.getLon(), user.getName());
        });

        return v;
    }

    public void setMapLocation(double lat, double lng, String name) {
        // Check if the GoogleMap object is initialized
        if (googleMap != null) {
            googleMap.clear();
            googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(lat, lng)).title(name));
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(lat, lng))      // Sets the center of the map to location user
                    .zoom(16)                   // Sets the zoom
                    .build();                   // Creates a CameraPosition from the builder
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int widthInDp = 350;
            int heightInDp = 500;
            float scale = getResources().getDisplayMetrics().density;
            int widthInPixels = (int) (widthInDp * scale + 0.5f);
            int heightInPixels = (int) (heightInDp * scale + 0.5f);

            // Set the width and height of the dialog
            dialog.getWindow().setLayout(widthInPixels, heightInPixels);
        }
    }
}
