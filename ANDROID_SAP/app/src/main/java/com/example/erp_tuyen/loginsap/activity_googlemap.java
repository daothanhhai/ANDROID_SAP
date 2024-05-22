package com.example.erp_tuyen.loginsap;

import android.graphics.Color;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class activity_googlemap extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_googlemap);
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.myMap);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        LatLng sydney = new LatLng(10.779445390271169, 106.68502689075407);
        //map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 19));
        map.addMarker(new MarkerOptions()
                .title("Khoa Phạm Trainning")
                .snippet("Trung tâm tin học khoa phạm").position(sydney)
        .icon(BitmapDescriptorFactory.fromResource(R.drawable.lotrinh)));
        //map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        LatLng hb=new LatLng(10.762918970616084, 106.68210864749062);
        map.addPolyline(new PolylineOptions().add(
                sydney,
                new LatLng(10.77611488377365, 106.69163585343892),
                hb
                ).width(10)
                .color(Color.RED)
        );
                //https://www.youtube.com/watch?v=CCZPUeY94MU
                //https://www.youtube.com/watch?v=_pLSdpBNons&t=174s
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        map.setMyLocationEnabled(true);

    }
}