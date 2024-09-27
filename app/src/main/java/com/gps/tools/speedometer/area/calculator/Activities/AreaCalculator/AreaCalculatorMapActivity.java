package com.gps.tools.speedometer.area.calculator.Activities.AreaCalculator;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.window.OnBackInvokedDispatcher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.ads.AdView;

import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.gps.tools.speedometer.area.calculator.Activities.Altimeter.AltiMeterActivity;
import com.gps.tools.speedometer.area.calculator.Activities.MainActivity;
import com.gps.tools.speedometer.area.calculator.AddsManager.AdCallBack;
import com.gps.tools.speedometer.area.calculator.AddsManager.AdmobInterstitialAd;
import com.gps.tools.speedometer.area.calculator.AddsManager.ShowInterstitials;
import com.gps.tools.speedometer.area.calculator.R;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Stack;

import com.gps.tools.speedometer.area.calculator.CheckNetwork.CheckNetworkLimited;
public class AreaCalculatorMapActivity extends AppCompatActivity implements LocationListener, GoogleApiClient.ConnectionCallbacks, OnMapReadyCallback, AdCallBack{


    GoogleMap mMap;
    TextView Latitude, Longitude, rectangular_box;
    Location currentLocation;
    private Boolean isloaded;
    double lat=0.0;
    double lng=0.0;
    double passlat= 0.0;
    double passlng= 0.0;
    @NonNull
    @Override
    public OnBackInvokedDispatcher getOnBackInvokedDispatcher() {
        return super.getOnBackInvokedDispatcher();
    }

    double latitude, longitude;
    String rectangular;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;
    Context context;
    Circle circle;
    Geocoder geocoder;
    List<Address> addressesList;
    RelativeLayout mRelativeLayout;
    boolean result;
    AlertDialog alert;

    boolean locationPermission;
    boolean isGpsEnabled;
    boolean isNetWorkEnabled = false;
    LocationRequest locationRequest;
    LocationManager locationmanager;
    private LocationManager mService;
    String address;
    public double distance;
    CameraUpdate update = null;
    public ClipboardManager clipboardManager;
    ImageView back_button;
    Intent i;


    private HashMap _$_findViewCache;
    private double area;
    private String areaKm = "N/A";
    private int count;
    private String placeSearch = "";
    public LatLng makerLatLng;
    private int currentMarker = R.drawable.b_pointer_new;
    MarkerOptions markerOptions = new MarkerOptions();
    private double feets;
    public PopupWindow mapTypeMenu;
    private SqliteOpenHelperClass helperClass;

    private int f3945i;
    private Stack<LatLng> trace = new Stack<>();
    public  ArrayList<Integer> iconList = new ArrayList<>();
    public  ArrayList<Integer> markersList = new ArrayList<>();
    public  PolygonOptions polygonOption = new PolygonOptions();
    private boolean isMap2d = true;
    private double kms;
    public LatLng latLngto;
    public LatLng latlng;
    public final Stack<Polyline> lines = new Stack<>();
    private Polygon polygon;
    private Polyline polyline;
    AdmobInterstitialAd interstitialAd;
    private View popUpView;
    public static final String TAG = "APP";

    TextView textView;
    ImageView clear;

    private double valueInMeterSquare = 0.0;
    private double valueInKiloMeterSquare = 0.0;
    private double valueInMilesquare = 0.0;
    private double valueInFeetSquare = 0.0;
    private String myUnit = "m²";
   /* private String myUnitt = "km²";
    private String myUnittt = "mi²";
    private String myUnitttt = "feet²";*/


    ImageView currentLocationBtn;
    ImageView map_type;
    CardView mCardView_type;
    PopupWindow popupWindow;
    View popupView;

    ImageView btn_3d, btn_2d, btn_area, btn_pdf;

    CardView mCardView_area, m_card_area;
    ImageView Cancel;

    LayoutInflater layoutInflater;


    com.facebook.ads.InterstitialAd fb;
    
    com.facebook.ads.AdView adViewFb;
    AdView mAdViewMob;

    MapView mMapView;
    private int mapType = GoogleMap.MAP_TYPE_NORMAL;


    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
        
        // Check Google Map Service
        if (googleServicesAvailable()) {
            //  Toast.makeText(this, "Available Service!!!", Toast.LENGTH_SHORT).show();
            setContentView(R.layout.activity_area_calculator);

            geocoder = new Geocoder(this, Locale.getDefault());
            context = this;



            textView = (TextView) findViewById(R.id.tv_area);
            clear = (ImageView) findViewById(R.id.image_clear_icon);
            currentLocationBtn = findViewById(R.id.current_location);

            map_type    = findViewById(R.id.button_maps_type);
            //   mCardView_type  = (CardView) findViewById(R.id.card_vieww);
            mCardView_area  = (CardView) findViewById(R.id.card_view_area_cal);

            btn_3d = (ImageView) findViewById(R.id.button_3d);
            btn_2d = (ImageView) findViewById(R.id.button_2d);
            btn_area = (ImageView) findViewById(R.id.button_fm);
            // btn_pdf = (ImageView) findViewById(R.id.button_pdf);

            Cancel = (ImageView) findViewById(R.id.cancel_area);


            SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_area);
            if (supportMapFragment != null) {
                supportMapFragment.getMapAsync(this);
            }

            back_button = (ImageView) findViewById(R.id.drawer_icon_area);

            locationPermission = false;
            isGpsEnabled = false;


            locationmanager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
            isNetWorkEnabled = locationmanager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            isGpsEnabled = locationmanager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            result = CheckNetworkLimited.isNetworkConnectionAvailable(this);
            if (result) {
                fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
                fetchLastLocation();
                checkRuntimeLocationPermission();
                createLocationRequest();

            } else {

            }
        }

        textView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //  textView.setEnabled(false);
                mCardView_area.setVisibility(View.VISIBLE);
                value();

            }
        });

        back_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                mCardView_area.setVisibility(View.GONE);

            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                try {
                    if (polygonOption.getPoints().size() < 1) {
                        Toast.makeText(AreaCalculatorMapActivity.this, "Nothing To Remove!", Toast.LENGTH_LONG).show();
                        return;
                    }

                    polygonOption.getPoints().remove(polygonOption.getPoints().size() - 1);
                    mMap.clear();
                    polygon = mMap.addPolygon(polygonOption);
                    showValues();

                } catch (Exception e) {
                }
            }
        });

        currentLocationBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (result) {
                    {
                        if (currentLocation != null) {

                            mMap.animateCamera(CameraUpdateFactory.zoomIn());

                            LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                            MarkerOptions markerOptions = new MarkerOptions().position(latLng).title(address);
                            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f));
                            mMap.addMarker(markerOptions);
                            mMap.setBuildingsEnabled(true);
                            isGpsEnabled = true;

                            //  setTrafficEnabled(true);
                        } else {
                            // Check if GPS is enabled
                            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                                showSettingsAlertt();
                            } else {
                                // Toast.makeText(this, "Please Enable the GPS", Toast.LENGTH_SHORT).show();
                                LocationRequest locationRequest = new LocationRequest()
                                        .setInterval(1000)
                                        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                                        .setFastestInterval(1000)
                                        .setNumUpdates(1);

                                LocationCallback locationCallback = new LocationCallback() {
                                    @Override
                                    public void onLocationResult(LocationResult locationResult) {
                                        Location location1 = locationResult.getLastLocation();
                                        if (location1 != null) {
                                            double latitude = location1.getLatitude();
                                            double longitude = location1.getLongitude();

                                            Log.d("fdafdfsd", "onLocationResult latitude : " + latitude);
                                            Log.d("fdafdfsd", "onLocationResult longitude : " + longitude);

                                            if (latitude != 0.0 && longitude != 0.0) {
                                                //TODO map animationCamera

                                                LatLng l=new LatLng(latitude,longitude);
                                                animateCamera(mMap, latitude, longitude, 16);

                                                //MarkerOptions markerOptions = new MarkerOptions().position(l);
                                                // mMap.addMarker(markerOptions);

                                            }
                                        }
                                    }
                                };

                                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {


                                    return;
                                }
                                fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());


                            }
                        }
                    }
                }

            }
        });

        map_type.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                LayoutInflater inflater = LayoutInflater.from(AreaCalculatorMapActivity.this);
                View layout = inflater.inflate(R.layout.traffic_layer_area, null);

                ImageView imgNormal = (ImageView) layout.findViewById(R.id.image_default_view_voice);
                ImageView imgSatellite = (ImageView) layout.findViewById(R.id.image_satellite_view_voice);
                ImageView imgTerrain = (ImageView) layout.findViewById(R.id.image_terrain_view_voice);

                AlertDialog.Builder about = new AlertDialog.Builder(context);
                about.setView(layout);
                AlertDialog displayInfo = about.create();

                imgNormal.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        setMapType(GoogleMap.MAP_TYPE_NORMAL);
                        /*if(result) {
                            if (currentLocation != null){
                                Log.d("fdafdfsdaaa", "onCurrentlocationNormalview : " +currentLocation);
                                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                            }else {
                                //    Toast.makeText(this, "Please Enable the GPS", Toast.LENGTH_SHORT).show();
                                LocationRequest locationRequest = new LocationRequest()
                                        .setInterval(1000)
                                        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                                        .setFastestInterval(1000)
                                        .setNumUpdates(1);

                                LocationCallback locationCallback = new LocationCallback() {
                                    @Override
                                    public void onLocationResult(LocationResult locationResult) {
                                        Location location1 = locationResult.getLastLocation();
                                        if (location1 != null) {
                                            double latitude = location1.getLatitude();
                                            double longitude = location1.getLongitude();

                                            Log.d("fdafdfsd", "onLocationResult latitude : " + latitude);
                                            Log.d("fdafdfsd", "onLocationResult longitude : " + longitude);

                                            if (latitude != 0.0 && longitude != 0.0) {
                                                //TODO map animationCamera
                                                animateCamera(mMap, latitude, longitude, 16);


                                            }
                                        }
                                    }
                                };

                                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                    // TODO: Consider calling
                                    //    ActivityCompat#requestPermissions
                                    // here to request the missing permissions, and then overriding
                                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                    //                                          int[] grantResults)
                                    // to handle the case where the user grants the permission. See the documentation
                                    // for ActivityCompat#requestPermissions for more details.
                                    return;
                                }
                                fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());


                            }
                        }*/

                        displayInfo.dismiss();

                    }
                });


                imgSatellite.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                       /* if(result) {
                            if (currentLocation != null){
                                Log.d("fdafdfsdaaa", "onCurrentlocationSatellite : " +currentLocation);
                                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                            }else {
                                //    Toast.makeText(this, "Please Enable the GPS", Toast.LENGTH_SHORT).show();
                                LocationRequest locationRequest = new LocationRequest()
                                        .setInterval(1000)
                                        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                                        .setFastestInterval(1000)
                                        .setNumUpdates(1);

                                LocationCallback locationCallback = new LocationCallback() {
                                    @Override
                                    public void onLocationResult(LocationResult locationResult) {
                                        Location location1 = locationResult.getLastLocation();
                                        if (location1 != null) {
                                            double latitude = location1.getLatitude();
                                            double longitude = location1.getLongitude();

                                            Log.d("fdafdfsd", "onLocationResult latitude : " + latitude);
                                            Log.d("fdafdfsd", "onLocationResult longitude : " + longitude);

                                            if (latitude != 0.0 && longitude != 0.0) {
                                                //TODO map animationCamera
                                                animateCamera(mMap, latitude, longitude, 16);

                                            }
                                        }
                                    }
                                };

                                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                    // TODO: Consider calling
                                    //    ActivityCompat#requestPermissions
                                    // here to request the missing permissions, and then overriding
                                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                    //                                          int[] grantResults)
                                    // to handle the case where the user grants the permission. See the documentation
                                    // for ActivityCompat#requestPermissions for more details.
                                    return;
                                }
                                fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());


                            }
                        }*/

                        displayInfo.dismiss();

                    }
                });


                imgTerrain.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                        /*if(result) {
                            if (currentLocation != null){
                                Log.d("fdafdfsdaaa", "onCurrentlocationTerrain : " +currentLocation);
                                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                            }else {
                                //    Toast.makeText(this, "Please Enable the GPS", Toast.LENGTH_SHORT).show();
                                LocationRequest locationRequest = new LocationRequest()
                                        .setInterval(1000)
                                        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                                        .setFastestInterval(1000)
                                        .setNumUpdates(1);

                                LocationCallback locationCallback = new LocationCallback() {
                                    @Override
                                    public void onLocationResult(LocationResult locationResult) {
                                        Location location1 = locationResult.getLastLocation();
                                        if (location1 != null) {
                                            double latitude = location1.getLatitude();
                                            double longitude = location1.getLongitude();

                                            Log.d("fdafdfsd", "oncuurentLocationResult latitude : " + latitude);
                                            Log.d("fdafdfsd", "oncurrentLocationResult longitude : " + longitude);

                                            if (latitude != 0.0 && longitude != 0.0) {
                                                //TODO map animationCamera
                                                animateCamera(mMap, latitude, longitude, 16);


                                            }
                                        }
                                    }
                                };

                                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                    // TODO: Consider calling
                                    //    ActivityCompat#requestPermissions
                                    // here to request the missing permissions, and then overriding
                                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                    //                                          int[] grantResults)
                                    // to handle the case where the user grants the permission. See the documentation
                                    // for ActivityCompat#requestPermissions for more details.
                                    return;
                                }
                                fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());


                            }
                        }*/

                        displayInfo.dismiss();

                    }
                });

                displayInfo.show();
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(displayInfo.getWindow().getAttributes());
                lp.width = 910;
                lp.height = 600;
                lp.x=90;
                lp.y=80;
                displayInfo.getWindow().setAttributes(lp);


            }
        });




        btn_3d.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                btn_2d.setVisibility(View.VISIBLE);
                btn_3d.setVisibility(View.GONE);

                animateCameraThreeD();


            }
        });

        btn_2d.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                btn_2d.setVisibility(View.GONE);
                btn_3d.setVisibility(View.VISIBLE);

                animateCameraTwoD();

            }
        });

        btn_area.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                mCardView_area.setVisibility(View.VISIBLE);

                value();
                //  dialog.show();


      /*          layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                popupView = layoutInflater.inflate(R.layout.area_meter,null);
                popupWindow = new PopupWindow(popupView, FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.MATCH_PARENT);*/

            }
        });



    }


    private void setMapType(int mapType) {
        this.mapType= mapType;
        if (currentLocation != null) {
            Log.d("MapTypeChange", "Current Location: " + currentLocation.getLatitude() + ", " + currentLocation.getLongitude());
            mMap.setMapType(mapType);
        } else {
            requestLocationUpdatesAndSetMapType(mapType);
        }
    }

    // Method to request location updates and set map type
    private void requestLocationUpdatesAndSetMapType(int mapType) {
        LocationRequest locationRequest = new LocationRequest()
                .setInterval(1000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setFastestInterval(1000)
                .setNumUpdates(1);

        LocationCallback locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                Location location = locationResult.getLastLocation();
                if (location != null) {
                    Log.d("MapTypeChange", "New Location: " + location.getLatitude() + ", " + location.getLongitude());
                    animateCamera(mMap, location.getLatitude(), location.getLongitude(), 16);
                    mMap.setMapType(mapType);
                }
            }
        };

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
        } else {
            // Handle permission not granted
            Toast.makeText(AreaCalculatorMapActivity.this, "Location permission not granted", Toast.LENGTH_SHORT).show();
        }
    }

    public void animateCamera( GoogleMap mMap, double latitude, double longitude, float zoomLevel) {
        LatLng latLng = new LatLng(latitude, longitude);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel));
    }


    private void value(){

        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(1);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.area_meter);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

        double d2 = (double) 1000;
        Double.isNaN(d2);
        View findViewById7 = findViewById(R.id.areaM);
        TextView textView7 = (TextView) findViewById7;
        StringBuilder sb7 = new StringBuilder();
        sb7.append(decimalFormat().format(valueInMeterSquare));
        sb7.append(" m²");
        textView7.setText(sb7.toString());
        double d14 = valueInMeterSquare;
        Double.isNaN(d2);
        kms = d14 / d2;
        View findViewById8 = findViewById(R.id.areaKM);

        TextView textView8 = (TextView) findViewById8;
        StringBuilder sb8 = new StringBuilder();
        sb8.append(decimalFormat().format(kms));
        sb8.append(" km²");
        textView8.setText(sb8.toString());
        StringBuilder sb9 = new StringBuilder();
        sb9.append(decimalFormat().format(kms));
        sb9.append(" km²");
        areaKm = sb9.toString();
        double d15 = valueInMeterSquare / 0.09290304d;
        View findViewById9 = findViewById(R.id.areaft);

        TextView textView9 = (TextView) findViewById9;
        StringBuilder sb10 = new StringBuilder();
        sb10.append(decimalFormat().format(d15));
        sb10.append(" ft²");
        textView9.setText(sb10.toString());
        double d16 = valueInMeterSquare;
        double d17 = (double) 100;
        Double.isNaN(d17);
        double d18 = d16 * d17;
        View findViewById10 = findViewById(R.id.areaha);

        TextView textView10 = (TextView) findViewById10;
        StringBuilder sb11 = new StringBuilder();
        sb11.append(decimalFormat().format(d18));
        sb11.append(" ha");
        textView10.setText(sb11.toString());
        double d19 = valueInMeterSquare / 25899.880336d;
        View findViewById11 = findViewById(R.id.areami);

        TextView textView11 = (TextView) findViewById11;
        StringBuilder sb12 = new StringBuilder();
        sb12.append(decimalFormat().format(d19));
        sb12.append(" mi²");
        textView11.setText(sb12.toString());
        double d20 = valueInMeterSquare / 4046.8726099d;
        View findViewById12 = findViewById(R.id.areaac);

        TextView textView12 = (TextView) findViewById12;
        String sb13 = decimalFormat().format(d20) +
                " ac(U.S Survey)";
        textView12.setText(sb13);


    }

     void animateCameraTwoD() {

            CameraPosition cameraPosition = new CameraPosition.Builder().bearing(1).target(new LatLng(passlat,passlng)).tilt(12).zoom(16).build();
         Log.d("dddd", "pass latitude : " + passlat);
         Log.d("dddd", "pass longitude : " + passlng);
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        }


    private void animateCameraThreeD() {

            CameraPosition cameraPosition = new CameraPosition.Builder().bearing(-10).target(new LatLng(passlat,passlng)).tilt(65).zoom(16).build();
            try {
                Log.d("dddd", "pass latitude : " + passlat);
                Log.d("dddd", "pass longitude : " + passlng);
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }



    public void Map(View view){

        if (view.getId() == R.id.image_default_view_){
            map_type.setVisibility(View.VISIBLE);
            mCardView_type.setVisibility(View.GONE);
            if(result) {

                if (currentLocation != null){
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                }else {
                    //    Toast.makeText(this, "Please Enable the GPS", Toast.LENGTH_SHORT).show();
                }
            }
        }
        if (view.getId() == R.id.image_satellite_view_){
            map_type.setVisibility(View.VISIBLE);
            mCardView_type.setVisibility(View.GONE);

            if (result) {
                if (currentLocation != null){
                    mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                }else {
                    //   Toast.makeText(this, "Please Enable the GPS", Toast.LENGTH_SHORT).show();
                }

            }
        }
        if (view.getId() == R.id.image_terrain_view_){
            map_type.setVisibility(View.VISIBLE);
            mCardView_type.setVisibility(View.GONE);

            if (result) {
                if (currentLocation != null){
                    mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                }else {
                    //    Toast.makeText(this, "Please Enable the GPS", Toast.LENGTH_SHORT).show();
                }

            }
        }
    }



    public void onMapReady(@NotNull GoogleMap googleMap) {
        mMap = googleMap;
        makerLatLng = new LatLng(33.738045, 73.084488);
        try {
            if (mMap == null) {

                Log.e(TAG, "Map Null");
            }
            LatLng latLng = this.makerLatLng;
            if (latLng == null) {

                Log.e(TAG, "LatLng Null");

            }

            mMap.getUiSettings().setZoomControlsEnabled(true);
            // mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.0f));
        } catch (Exception unused) {

        }
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            public void onMapClick(LatLng point) {
                // Drawing marker on the map

                mMap.clear();
                polygonOption.add(point);
                polygonOption.strokeWidth(10F);
                polygonOption.strokeColor(-16776961);
                polygonOption.fillColor(R.color.debug_blue);
                polygonOption.strokeJointType(PolygonOptions.CONTENTS_FILE_DESCRIPTOR);
                polygon = mMap.addPolygon(polygonOption);

                if (polygonOption.getPoints().size() < 1) {

                }
                Log.e("AAAA", "" + polygonOption.getPoints().size());
                showValues();

            }
        });

/*        if (mMapView != null && mMapView.findViewById(Integer.parseInt("1")) != null) {
            // Get the button view
            View locationButton = ((View) mMapView.findViewById
                    (Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            // and next place it, on bottom right (as Google Maps app)
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
            // position on right bottom
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 0);
            layoutParams.setMargins(0, 0, 30, 170);

    }*/
    }


    private void showValues() {
        try {
            valueInMeterSquare = SphericalUtil.computeArea(polygonOption.getPoints());

            valueInKiloMeterSquare = 1000000 * valueInMeterSquare;
            valueInMilesquare = valueInMeterSquare * 2589988.1102853;
            valueInFeetSquare = valueInMeterSquare * 0.092903;

        } catch (Exception e) {
        }

        String var1 = this.myUnit;
        switch(var1.hashCode()) {
            case 3557:
                if (var1.equals("m²")) {
                    textView.setText(String.format("%.2f", valueInMeterSquare) + " m²");
                }
                break;
            case 106384:
                if (var1.equals("km²")) {
                    textView.setText(String.format("%.2f", valueInKiloMeterSquare) + " km²");
                }
                break;
            case 108182:
                if (var1.equals("mi²")) {
                    textView.setText(String.format("%.2f", valueInMilesquare) + " mi²");
                }
                break;
            case 97308868:
                if (var1.equals("feet²")) {

                    textView.setText(String.format("%.2f", valueInFeetSquare) + " feet²");
                }
        }


   /*     if (myUnit.equals("m²")) {
                textView.setText("Area " + String.format("%.2f", valueInMeterSquare) + " m²");
            }
            if (myUnit.equals("km²")) {

                textView.setText("Area " + String.format("%.2f", valueInKiloMeterSquare) + " km²");
            }
            if (myUnit.equals("mi²")) {

                textView.setText("Area " + String.format("%.2f", valueInMilesquare) + " mi²");
            }
            if (myUnit.equals("feet²")){

                textView.setText("Area " + String.format("%.2f", valueInFeetSquare) + " feet²");
        }*/

        Iterator var2 = polygonOption.getPoints().iterator();

        while(var2.hasNext()) {
            LatLng latLngModel = (LatLng)var2.next();
            addPolygonMarker(latLngModel.latitude, latLngModel.longitude);
        }

    }

    private void addPolygonMarker(Double lat , Double lng) {
        try {
            mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                    .title(lat + ", " + lng)
                    .position(new LatLng(lat, lng)));

        } catch (Exception e) {
        }
    }


    public final void drawPolyline() {
        int i = 0;
        PolylineOptions addAll = new PolylineOptions().addAll(trace.subList(0, trace.size()));
        if (polyline != null) {
            Polyline polyline2 = polyline;
            if (polyline2 == null) {

            }
            polyline2.remove();
        }
        try {
            GoogleMap googleMap = mMap;
            if (googleMap == null) {
            }
            polyline = googleMap.addPolyline(addAll.width(5.0f).color(-16776961));
        } catch (Exception unused) {
        }
        List subList = this.trace.subList(0, this.trace.size());
        distance = 0.0d;
        int size = subList.size() - 1;
        while (i < size) {
            i++;
            distance += SphericalUtil.computeDistanceBetween((LatLng) subList.get(i), (LatLng) subList.get(i));
            MathKt.roundToLong(distance);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(decimalFormat().format(distance));
        sb.append(" m");
        String sb2 = sb.toString();
        StringBuilder sb3 = new StringBuilder();
        sb3.append("distance /n ");
        sb3.append(sb2);
        Log.e("Area", sb3.toString());
    }


    public final void drawPolygone() {
        PolygonOptions fillColor = new PolygonOptions().addAll(trace.subList(0, trace.size())).strokeColor(-16776961).fillColor(Color.parseColor("#800000FF"));
        if (polygon != null) {
            Polygon polygon2 = polygon;
            if (polygon2 == null) {

            }
            polygon2.remove();
        }
        try {
            GoogleMap googleMap = this.mMap;
            if (googleMap == null) {

            }
            this.polygon = googleMap.addPolygon(fillColor);
        } catch (Exception unused) {
        }
        area = SphericalUtil.computeArea(trace.subList(0, trace.size()));
        StringBuilder sb = new StringBuilder();
        sb.append(decimalFormat().format(area));
        sb.append(" m²");
        textView.setText(sb.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append("m sq ");
        sb2.append(decimalFormat().format(area));
        Log.d("area value", sb2.toString());
        feets();
    }

    public final void addMarkersToList() {
        markersList.add(Integer.valueOf(R.drawable.marker_a));
        markersList.add(Integer.valueOf(R.drawable.marker_b));
        markersList.add(Integer.valueOf(R.drawable.marker_c));
        markersList.add(Integer.valueOf(R.drawable.marker_d));
    }

    public boolean checkGPSStatus(Context context) {
        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean statusOfGPS = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        fetchLastLocation();
        return statusOfGPS;
    }


    protected void createLocationRequest() {
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void checkRuntimeLocationPermission() {
        Dexter.withActivity(this).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {
                if (checkGPSStatus(context)) {
                    locationPermission = true;
                    isGpsEnabled = true;
                    createLocationRequest();
                    fetchLastLocation();


                } else {
                    showSettingsAlert();

                }

            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse response) {
                showSettingsAlertt();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

            }
        }).check();
    }

    public void showSettingsAlertt() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        // Setting Dialog Title
        alertDialog.setTitle("Location Settings");

        // Setting Dialog Message
        alertDialog.setMessage("Location is not enabled. Do you want to go to settings detail?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", getPackageName(), null));
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(intent);
                fetchLastLocation();
                isGpsEnabled= true;
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alert = alertDialog.create();
        alert.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                alert.getButton(androidx.appcompat.app.AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
                alert.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
            }
        });
        // Showing Alert Message
        alert.show();
    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(intent);
                fetchLastLocation();
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alert = alertDialog.create();
        alert.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                alert.getButton(androidx.appcompat.app.AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
                alert.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
            }
        });
        // Showing Alert Message
        alert.show();
    }
    private void fetchLastLocation() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]
                        {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
                return;
            }
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
                    lat = Double.parseDouble(String.valueOf(currentLocation.getLatitude()));
                    lng = Double.parseDouble(String.valueOf(currentLocation.getLongitude()));
                    passlat= lat;
                    passlng= lng;
                    Log.d("hhhh", "newCurrentloc : " + lat);
                    Log.d("hhhh", "newCurrentloc : " + lng);

                    try {
                        addressesList = geocoder.getFromLocation(lat, lng, 1);
                        if (addressesList != null && addressesList.size() != 0) {
                            address = addressesList.get(0).getAddressLine(0);
                            String fullAddress = address;


                            mMap.animateCamera(CameraUpdateFactory.zoomIn());
                            LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                            MarkerOptions markerOptions = new MarkerOptions().position(latLng).title(address);
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                            mMap.addMarker(markerOptions);
                        } else {

                        }


                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // Toast.makeText(getApplicationContext(), "Lat: "+lat+"Long: "+lng, Toast.LENGTH_SHORT).show();
                }else{
                    LocationRequest locationRequest = new LocationRequest()
                            .setInterval(1000)
                            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                            .setFastestInterval(1000)
                            .setNumUpdates(1);

                    LocationCallback locationCallback = new LocationCallback() {
                        @Override
                        public void onLocationResult(LocationResult locationResult) {
                            Location location1 = locationResult.getLastLocation();
                            if (location1 != null) {
                                double latitude = location1.getLatitude();
                                double longitude = location1.getLongitude();
                                passlat= latitude;
                                passlng= longitude;
                                Log.d("fdafdfsd", "onLocationResult latitude : " + latitude);
                                Log.d("fdafdfsd", "onLocationResult longitude : " + longitude);

                                if (latitude != 0.0 && longitude != 0.0) {
                                    //TODO map animationCamera
                                    LatLng l=new LatLng(latitude,longitude);
                                    animateCamera(mMap, latitude, longitude, 16);
                                    MarkerOptions markerOptions = new MarkerOptions().position(l);
                                    mMap.addMarker(markerOptions);

                                }
                            }
                        }
                    };

                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        fetchLastLocation();
                        return;
                    }
                    fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());



                }
            }
        });
    }
    // Method of Google Map Service
    public boolean googleServicesAvailable() {

        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int isAvailable = api.isGooglePlayServicesAvailable(this);
        if (isAvailable == ConnectionResult.SUCCESS) {
            return true;
        } else if (api.isUserResolvableError(isAvailable)) {

            Dialog dialog = api.getErrorDialog(this, isAvailable, 0);
            dialog.show();
        } else {
            Toast.makeText(this, "Cannot Connect to Play Services", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onMapReady(mMap);
                    fetchLastLocation();
                }
                break;

        }
    }

    @SuppressLint("MissingPermission")

    public void onLocationChanged(Location location) {


    }


    protected void onResume() {
        super.onResume();
        // fetchLastLocation();
        onLocationChanged(currentLocation);



    }

    public void onStatusChanged(String s, int i, Bundle bundle) {

    }


    public void onProviderEnabled(String s) {

    }

    public void onProviderDisabled(String s) {

    }

    public void onConnected(@Nullable Bundle bundle) {

    }


    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }



    @NotNull
    public final ArrayList<Integer> getIconList() {
        return this.iconList;
    }

    public final void setIconList(@NotNull ArrayList<Integer> arrayList) {
        this.iconList = arrayList;
    }

    @org.jetbrains.annotations.Nullable
    public final View getPopUpView() {
        return this.popUpView;
    }

    public final void setPopUpView(@org.jetbrains.annotations.Nullable View view) {
        this.popUpView = view;
    }

    @NotNull
    public final LatLng getMakerLatLng() {
        LatLng latLng = this.makerLatLng;
        if (latLng == null) {
        }
        return latLng;
    }

    public final void setMakerLatLng(@NotNull LatLng latLng) {
        this.makerLatLng = latLng;
    }

    public final int getI() {
        return this.f3945i;
    }

    public final void setI(int i) {
        this.f3945i = i;
    }

    public final double getFeets() {
        return this.feets;
    }

    public final void setFeets(double d) {
        this.feets = d;
    }

    @NotNull
    public final String getAreaKm() {
        return this.areaKm;
    }

    public final void setAreaKm(@NotNull String str) {
        this.areaKm = str;
    }

    @NotNull
    public final LatLng getLatlng() {
        LatLng latLng = this.latlng;
        if (latLng == null) {
        }
        return latLng;
    }

    public final void setLatlng(@NotNull LatLng latLng) {
        this.latlng = latLng;
    }

    public final void setLatLngto(@NotNull LatLng latLng) {
        this.latLngto = latLng;
    }

    public final double getKms() {
        return this.kms;
    }

    public final void setKms(double d) {
        this.kms = d;
    }


    public final void feets() {
        try {
            String format = decimalFormat().format(area);
            feets = Double.parseDouble(format) * 10.764d;
        } catch (Exception unused) {
        }
    }

    private final DecimalFormat decimalFormat() {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        decimalFormat.setRoundingMode(RoundingMode.CEILING);
        return decimalFormat;
    }

    public void onBackPressed() {
        ShowInterstitials.showBackPressInterstitialAd(AreaCalculatorMapActivity.this, AdmobInterstitialAd.mInterstitialAd);
    }





    @Override
    public void onAdLoaded() {
        isloaded= true;
    }

    @Override
    public void onFailedToLoadAd(@NonNull LoadAdError loadAdError) {
        isloaded= false;
    }
}

