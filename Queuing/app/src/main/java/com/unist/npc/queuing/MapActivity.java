package com.unist.npc.queuing;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import net.daum.mf.map.api.CameraUpdateFactory;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.Map;


/**
 * Created by cheongwh on 2015. 8. 7..
 */
public class MapActivity extends Activity implements LocationListener, MapView.POIItemEventListener{

    /* private final Context mContext; */

    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    boolean isGetLocation = false;
    FrameLayout information;


    Location location;
    double lat;
    double lon;

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;
    protected LocationManager locationManager;

    /*public MapActivity(Context context){
        this.mContext = context;
    }*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        information = (FrameLayout)findViewById(R.id.information);
        information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapActivity.this, RestaurantInfo.class);
                intent.putExtra("name","아웃백 스테이크 신촌점");
                intent.putExtra("cuisine","스테이크, 패밀리 레스토랑");
                intent.putExtra("timing","11:00 - 22:00");
                intent.putExtra("img_large","http://52.69.163.43/queuing/img/large/outback_large.png");
                intent.putExtra("location","서울특별시 서대문구 연세로12길 33 아웃백 스테이크 전문점");
                intent.putExtra("phone_num","02-3147-1871");
                intent.putExtra("x_coordinate",37.5592750);
                intent.putExtra("y_coordinate",126.9387700);
                intent.putExtra("username","정운");
                intent.putExtra("dummy_name","outback_sinchon");

                startActivity(intent);
            }
        });
        getLocation();
        final MapView mapView = new MapView(this);
        mapView.setDaumMapApiKey("6f34a566bab64437f455521185842b3f");
        ViewGroup mapViewContainer = (ViewGroup)findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);
        MapView.setMapTilePersistentCacheEnabled(true);

        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
        mapView.setHDMapTileEnabled(true);
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(37.557627, 126.936976), true);
        mapView.setZoomLevel(2, true);
        mapView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                information.setVisibility(View.GONE);
                return false;
            }
        });
        mapView.setShowCurrentLocationMarker(true);
        mapView.setPOIItemEventListener(this);
        Toast.makeText(getApplicationContext(), "위도: " + lat + "경도: " + lon, Toast.LENGTH_LONG).show();
        addMarker(mapView);
        information.bringToFront();



    }

    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem poiItem){


        Log.d("MAP", "CLICKED");
        MapPoint location;
        location = poiItem.getMapPoint();
        Log.d("LOC", " "+ location);
        mapView.moveCamera(CameraUpdateFactory.newMapPoint(location));
        information.setVisibility(View.VISIBLE);
    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {

    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

    }

    public void addMarker(MapView mapView){
        MapPOIItem marker = new MapPOIItem();
        marker.setShowCalloutBalloonOnTouch(true);
        marker.setShowDisclosureButtonOnCalloutBalloon(false);
        marker.setItemName("Default Marker");
        marker.setTag(0);
        marker.setMapPoint(MapPoint.mapPointWithGeoCoord(37.556228, 126.934614));
        marker.setMarkerType(MapPOIItem.MarkerType.RedPin);
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.BluePin);


        mapView.addPOIItem(marker);
    }




    public Location getLocation(){
        try{
            locationManager = (LocationManager)getApplicationContext().getSystemService(LOCATION_SERVICE);
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if(!isGPSEnabled && !isNetworkEnabled){

            }else{
                this.isGetLocation = true;
                if(isNetworkEnabled){
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                    if(locationManager != null){
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if(location != null){
                            lat = location.getLatitude();
                            lon = location.getLongitude();
                        }
                    }
                }
                if(isGPSEnabled){
                    if(location == null){
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        if(locationManager != null){
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if(location != null){
                                lat = location.getLatitude();
                                lon = location.getLongitude();
                            }
                        }
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return location;
    }

    public void stopUsingGPS(){
        if(locationManager != null){
            locationManager.removeUpdates(MapActivity.this);
        }
    }

    public double getLatitude(){
        if(location != null){
            lat = location.getLatitude();
        }
        return lat;
    }

    public double getLongitude(){
        if(location != null){
            lon = location.getLongitude();
        }
        return lon;
    }

    public boolean isGetLocation(){
        return this.isGetLocation;
    }


    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getApplicationContext());

        alertDialog.setTitle("GPS 사용유무셋팅");
        alertDialog.setMessage("GPS 셋팅이 되지 않았을 수도 있습니다. \n 설정창으로 가시겠습니까?");

        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        getApplicationContext().startActivity(intent);
                    }
                });

        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.show();
    }



    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


}
