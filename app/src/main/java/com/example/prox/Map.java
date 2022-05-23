package com.example.prox;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Map extends Fragment {

    MarkerOptions marker;
    LatLng spawnLocation;
    GoogleMap mMap;
    ImageButton search_button_map;
    TextInputEditText search_input_map;
/*    ArrayList<Marker> markers = new ArrayList<>();*/
    SharedPreferences sp;
    String place;
    List<Address> listAddress = new ArrayList<>();

    private OnMapReadyCallback callback = new OnMapReadyCallback() {


        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;

            spawnLocation = new LatLng(52.827298,-6.936106);

            //LatLng sydney = new LatLng(-34, 151);
            //googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));


            enableMyLocation();

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(spawnLocation, 15));

            search_button_map.callOnClick();
        }
    };

    @SuppressLint("MissingPermission")
    private void enableMyLocation() {
        // 1. Check if permissions are granted, if so, enable the my location layer
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            return;
        }

        String perms[] = {"android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION"};
        // 2. Otherwise, request location permissions from the user.
        ActivityCompat.requestPermissions(getActivity(),perms,200);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        search_button_map = view.findViewById(R.id.search_button_map);
        search_input_map = view.findViewById(R.id.search_input_map);

        sp = getActivity().getSharedPreferences("user_details", Context.MODE_PRIVATE);
        place = sp.getString("map", "");
        search_input_map.setText(place);


        search_button_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getActivity().getCurrentFocus().onEditorAction(EditorInfo.IME_ACTION_DONE);

                String location = search_input_map.getText().toString();
                if(location==null)
                {
                    Toast.makeText(getActivity(), "Please enter location name!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
                    try {
                        String options[] = {"products from " + location + " carlow", location + " carlow","products from " + location + " ireland", location + " ireland", location + " product carlow", "product "+ location + " carlow"};
                        listAddress.clear();
                        mMap.clear();

                        for(String option : options)
                        {
                            listAddress = geocoder.getFromLocationName(option,10);

                            if(listAddress.size()>0)
                            {
                                for (Address Address : listAddress) {

                                    //Toast.makeText(getActivity(),String.valueOf(Address.getLatitude()), Toast.LENGTH_SHORT).show();
                                    LatLng latLng = new LatLng(Address.getLatitude(),Address.getLongitude());

                                    marker = new MarkerOptions().title(Address.getFeatureName())
                                            .position(latLng);

                                    mMap.addMarker(marker);
                                }


                                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(spawnLocation,13);
                                mMap.animateCamera(cameraUpdate);
                            }
                        }



                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
}