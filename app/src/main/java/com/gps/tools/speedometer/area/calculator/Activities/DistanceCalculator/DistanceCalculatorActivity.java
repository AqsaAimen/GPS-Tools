package com.gps.tools.speedometer.area.calculator.Activities.DistanceCalculator;

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
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
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
import com.gps.tools.speedometer.area.calculator.Activities.AreaCalculator.MathKt;
import com.gps.tools.speedometer.area.calculator.Activities.AreaCalculator.SphericalUtil;
import com.gps.tools.speedometer.area.calculator.Activities.AreaCalculator.SqliteOpenHelperClass;
import com.gps.tools.speedometer.area.calculator.Activities.Converter.ConverterActivity;
import com.gps.tools.speedometer.area.calculator.Activities.MainActivity;
import com.gps.tools.speedometer.area.calculator.AddsManager.AdCallBack;
import com.gps.tools.speedometer.area.calculator.AddsManager.AdmobInterstitialAd;
import com.gps.tools.speedometer.area.calculator.AddsManager.ShowInterstitials;
import com.gps.tools.speedometer.area.calculator.CheckNetwork.CheckNetworkLimited;
import com.gps.tools.speedometer.area.calculator.Model.datamodel;
import com.gps.tools.speedometer.area.calculator.PermissionScreen.PrivacyPolicyScreenActivity;
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


public class DistanceCalculatorActivity extends AppCompatActivity implements LocationListener, GoogleApiClient.ConnectionCallbacks, OnMapReadyCallback, AdCallBack {

    GoogleMap mMap;
    TextView Latitude, Longitude, rectangular_box;
    Location currentLocation;
    double lat=0.0;
    double lng=0.0;
    double latitude, longitude;
    double passlat= 0.0;
    double passlng= 0.0;
    String rectangular;
    private int mapType = GoogleMap.MAP_TYPE_NORMAL;

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
    public double distanceKilometer;
    CameraUpdate update = null;
    public ClipboardManager clipboardManager;
    ImageView back_button;
    Intent i;
    AdmobInterstitialAd interstitialAd;

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
    public ArrayList<Integer> iconList = new ArrayList<>();
    public ArrayList<Integer> markersList = new ArrayList<>();
    public PolygonOptions polygonOption = new PolygonOptions();
    private boolean isMap2d = true;
    private double kms;
    public LatLng latLngto;
    public LatLng latlng;
    private GoogleMap googleMap;
    public final Stack<Polyline> lines = new Stack<>();
    private Polygon polygon;
    private Polyline polyline;
    private View popUpView;
    public static final String TAG = "APPffffff";

    TextView textViewDistance;
    ImageView clear;

    double valueIn = 0.0;
    double valueInMeter = 0.0d;
    double valueInKiloMeter = 0.0;
    double valueInMiles = 0.0;
    double valueInFeet = 0.0;

    double valueInMe = 0.0d;
    double valueInKiloMe = 0.0;
    double valueInMil = 0.0;
    double valueInFee = 0.0;
    private String myUnit = "km";

    ImageView currentLocationBtn;
    ImageView map_type;
    CardView mCardView_type;
    PopupWindow popupWindow;
    View popupView;

    ImageView btn_3d, btn_2d, btn_distance, btn_pdf;

    CardView mCardView_distance;

    LayoutInflater layoutInflater;


    ImageView Cancel;

    InterstitialAd admob;
    com.facebook.ads.InterstitialAd fb;
    
    com.facebook.ads.AdView adViewFb;
    AdView mAdViewMob;

    private List<datamodel> list = new ArrayList<>();


    TextView textView7, textView8, textView9, textView10, textView11, textView12;

    String Met;

    StringBuilder sb7 = new StringBuilder();
    StringBuilder sb8 = new StringBuilder();
    StringBuilder sb9 = new StringBuilder();
    StringBuilder sb10 = new StringBuilder();
    StringBuilder sb11 = new StringBuilder();
    StringBuilder sb12 = new StringBuilder();
    private Boolean isloaded;
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
        

        // Check Google Map Service
        if (googleServicesAvailable()) {
            //  Toast.makeText(this, "Available Service!!!", Toast.LENGTH_SHORT).show();
            setContentView(R.layout.activity_distance_calculator);

            geocoder = new Geocoder(this, Locale.getDefault());
            context = this;


            textView7 = findViewById(R.id.distanceM);
            textView8 = findViewById(R.id.distanceKM);
            textView9 = findViewById(R.id.distanceyd);
            textView10 = findViewById(R.id.distancemi);
            textView11 = findViewById(R.id.distancenm);
            textView12 = findViewById(R.id.distanceft);

            Met = textView7.toString().trim();


            textViewDistance = (TextView) findViewById(R.id.tv_distance);


            clear = (ImageView) findViewById(R.id.image_clear_icon_distance);
            currentLocationBtn = findViewById(R.id.current_location_distance);

            map_type = findViewById(R.id.button_maps_type_distance);

            mCardView_type = (CardView) findViewById(R.id.card_vieww);
            mCardView_distance = (CardView) findViewById(R.id.card_view_area_dist);

            btn_3d = (ImageView) findViewById(R.id.button_3d_distance);
            btn_2d = (ImageView) findViewById(R.id.button_2d_distance);
            btn_distance = (ImageView) findViewById(R.id.button_fm_distance);

            Cancel = (ImageView) findViewById(R.id.cancel_distance);
            // btn_pdf = (ImageView) findViewById(R.id.button_pdf);


            SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_distance);
            if (supportMapFragment != null) {
                supportMapFragment.getMapAsync(this);
            }

            back_button = (ImageView) findViewById(R.id.drawer_icon_distance);

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

        textViewDistance.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //  textViewDistance.setEnabled(false);
                mCardView_distance.setVisibility(View.VISIBLE);
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

                mCardView_distance.setVisibility(View.GONE);

            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                polygonOption.getPoints().size();

                try {
                    if (polygonOption.getPoints().size() < 1) {
                        Toast.makeText(DistanceCalculatorActivity.this, "Nothing To Remove!", Toast.LENGTH_LONG).show();
                        return;
                    } else {

                        if (polygonOption.getPoints().size() > 1) {

                            polygonOption.getPoints().clear();
                            mMap.clear();

                            valueInMeter = valueInMeter - valueInMeter;
                            valueInKiloMeter = 0.001 * valueInMeter;
                            textViewDistance.setText(String.format("%.2f", valueInKiloMeter) + " km");

                            Log.e(TAG, "Clear");
                            //  polygon = mMap.addPolygon(polygonOption);
                            // showValuess();
                   /*         sb7.append(" m");
                            textView7.setText(sb7.toString());
                            //textView7.setText(" m");
                            textView8.setText(" km");
                            textView9.setText(" yard");
                            textView10.setText(" mi");
                            textView11.setText(" miles");
                            textView12.setText(" foot");*/

                        }


                    }


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
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                            mMap.addMarker(markerOptions);
                            //  setTrafficEnabled(true);
                            isGpsEnabled = true;

                        } else {
                            // Check if GPS is enabled
                            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                                showSettingsAlertt();
                            }
                        else {
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
                    }
                }
            }

        }
                                              });

        map_type.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                LayoutInflater inflater = LayoutInflater.from(context);
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
                        /*if (result) {
                            if (currentLocation != null) {
                                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                            } else {
                                //   Toast.makeText(this, "Please Enable the GPS", Toast.LENGTH_SHORT).show();
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


                imgSatellite.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                       /* if (result) {
                            if (currentLocation != null) {
                                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                            } else {
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


                imgTerrain.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                        /*if (result) {
                            if (currentLocation != null) {
                                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                            } else {
                                //  Toast.makeText(this, "Please Enable the GPS", Toast.LENGTH_SHORT).show();
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
                lp.x = 90;
                lp.y = 80;
                displayInfo.getWindow().setAttributes(lp);

            }
        });


        mCardView_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                map_type.setVisibility(View.VISIBLE);
                mCardView_type.setVisibility(View.GONE);
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

        btn_distance.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                mCardView_distance.setVisibility(View.VISIBLE);

                value();

            }
        });


    }
    private void setMapType(int mapType) {
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
            Toast.makeText(DistanceCalculatorActivity.this, "Location permission not granted", Toast.LENGTH_SHORT).show();
        }
    }

    private void value(){

        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(1);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.distance_meter);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));


        double d2 = (double) 1000;
        Double.isNaN(d2);
        // textView7 = (TextView) findViewById7;
        sb7 = new StringBuilder();
        sb7.append(decimalFormat().format(valueInMeter));
        sb7.append(" m");
        textView7.setText(sb7.toString());

        double d14 = valueInMeter;
        Double.isNaN(d2);
        kms = d14 / d2;
        // View findViewById8 = findViewById(R.id.distanceKM);
        //  textView8 = (TextView) findViewById8;
        sb8 = new StringBuilder();
        sb8.append(decimalFormat().format(kms));
        sb8.append(" km");
        textView8.setText(sb8.toString());
        sb9 = new StringBuilder();
        sb9.append(decimalFormat().format(kms));
        sb9.append(" km");
        areaKm = sb9.toString();
        double d15 = valueInMeter / 0.9144027578d;
        //   View findViewById9 = findViewById(R.id.distanceyd);
        //    textView9 = (TextView) findViewById9;
        sb10 = new StringBuilder();
        sb10.append(decimalFormat().format(d15));
        sb10.append(" yard");
        textView9.setText(sb10.toString());
        double d16 = valueInMeter;
        double d17 = (double) 1000d;
        Double.isNaN(d17);
        double d18 = d16 * d17;
        //  View findViewById10 = findViewById(R.id.distancemi);
        //   textView10 = (TextView) findViewById10;
        sb11 = new StringBuilder();
        sb11.append(decimalFormat().format(d18));
        sb11.append(" mi");
        textView10.setText(sb11.toString());
        double d19 = valueInMeter / 1609.34449d;
        //  View findViewById11 = findViewById(R.id.distancenm);

        //  textView11 = (TextView) findViewById11;
        sb12 = new StringBuilder();
        sb12.append(decimalFormat().format(d19));
        sb12.append(" miles");
        textView11.setText(sb12.toString());
        double d20 = valueInMeter / 0.3047999902d;
        // View findViewById12 = findViewById(R.id.distanceft);
        // textView12 = (TextView) findViewById12;
        StringBuilder sb13 = new StringBuilder();
        sb13.append(decimalFormat().format(d20));
        sb13.append(" foot");
        textView12.setText(sb13.toString());
    }


    private void animateCameraTwoD() {
        CameraPosition cameraPosition = new CameraPosition.Builder().bearing(1).target(new LatLng(passlat, passlng)).tilt(12).zoom(16).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }

    private void animateCameraThreeD() {

        CameraPosition cameraPosition = new CameraPosition.Builder().bearing(-10).target(new LatLng(passlat, passlng)).tilt(65).zoom(16).build();
        try {
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

                //     polygonOption.fillColor(R.color.debug_blue);
                //     polygonOption.strokeJointType(PolygonOptions.CONTENTS_FILE_DESCRIPTOR);

                if (polygonOption.getPoints().size() < 2) {

                    mMap.clear();

                    polygonOption.add(point);
                    polygonOption.strokeWidth(10F);
                    polygonOption.strokeColor(-16776961);
                    polygon = mMap.addPolygon(polygonOption);

                    showValues();

                }


            }
        });
    }

    private void showValues() {
        try {

            int i = 0;
            int size = polygonOption.getPoints().size();
            Log.e("Size", "" + polygonOption.getPoints().size());
            while (i < size) {
                valueInMeter += SphericalUtil.computeDistanceBetween(polygonOption.getPoints().get(0), polygonOption.getPoints().get(i));
                MathKt.roundToLong(valueInMeter);
                i++;
            }

            if(polygonOption.getPoints().size() > 0){

                valueInKiloMeter = 0.001 * valueInMeter;
                valueInMiles = valueInMeter * 0.000621371;
                valueInFeet = valueInMeter * 3.28084;

                Log.e("AB", String.valueOf(valueInKiloMeter));

                list.add(new datamodel(valueInKiloMeter));


                for(int z=0; z<list.size();z++){

                    Log.e("add", String.valueOf(list.get(z).getVal(valueInKiloMeter)));
                }

            }

            if (myUnit.equals("km")) {
                textViewDistance.setText(String.format("%.2f", valueInKiloMeter) + " km");
            }


        } catch (Exception e) {

        }


        Iterator var2 = polygonOption.getPoints().iterator();

        while(var2.hasNext()) {
            LatLng latLngModel = (LatLng)var2.next();
            addPolygonMarker(latLngModel.latitude, latLngModel.longitude);
        }

    }

    private void showValuess() {
        try {

            int size = list.size();
            Log.e("Sizess", String.valueOf(polygonOption.getPoints().size()));


            list.remove(new datamodel(valueInKiloMeter));

            for(int z=0; z<list.size() ; z++){

                list.get(z);

                Log.e("remove", String.valueOf((list.get(z).getVal(valueInKiloMeter))));
            }


            //   list.remove(size-1);
            //   Log.e("remove", String.valueOf(list.remove(size-1)));

      /*     if(size > 0){



           }*/


            if (myUnit.equals("km")) {
                textViewDistance.setText("Distance : " + String.format("%.2f", valueInKiloMeter) + " km");
            }



        } catch (Exception e) {

        }
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
        textViewDistance.setText(sb.toString());
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


        Log.d("hubjbj", "fetchLastLocation inside : ");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]
                        {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
                Log.d("hubjbj", "fetchLastLocation inside 11 : ");
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
                    Log.d("hubjbj", "onSuccess lat : " + lat);
                    Log.d("hubjbj", "onSuccess lng : " + lng);
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
                        Log.d("hubjbj", "fetchLastLocation insideb catch : ");
                        e.printStackTrace();
                    }

                    // Toast.makeText(getApplicationContext(), "Lat: "+lat+"Long: "+lng, Toast.LENGTH_SHORT).show();
                } else {
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
                    fetchLastLocation();
                }
                break;
        }
    }

    @SuppressLint("MissingPermission")

    public void onLocationChanged(Location location) {


    }
    public void animateCamera( GoogleMap mMap, double latitude, double longitude, float zoomLevel) {
        LatLng latLng = new LatLng(latitude, longitude);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel));
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

    @NotNull
    public final String getPlaceSearch() {
        return this.placeSearch;
    }

    public final void setPlaceSearch(@NotNull String str) {
        this.placeSearch = str;
    }


    public final boolean isMap2d() {
        return this.isMap2d;
    }

    public final void setMap2d(boolean z) {
        this.isMap2d = z;
    }

    public final int getCount() {
        return this.count;
    }

    public final void setCount(int i) {
        this.count = i;
    }

    public final int getCurrentMarker() {
        return this.currentMarker;
    }

    public final void setCurrentMarker(int i) {
        this.currentMarker = i;
    }

    public final void setAnimationZoom(@NotNull View view) {
        Animation loadAnimation = AnimationUtils.loadAnimation(this, R.anim.zoom);
        loadAnimation.setDuration(500);
        view.setAnimation(loadAnimation);
        view.animate();
        loadAnimation.start();
    }

    public final String getStringFromList(Stack<LatLng> stack) {
        String str = "";
        int size = stack.size();
        for (int i = 0; i < size; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(((LatLng) stack.get(i)).latitude);
            sb.append(',');
            sb.append(((LatLng) stack.get(i)).longitude);
            sb.append('|');
            str = sb.toString();
        }
        return str;
    }

    public final void dismissMapTypeMenu() {
        try {
            PopupWindow popupWindow = mapTypeMenu;
            if (popupWindow == null) {
            }
            popupWindow.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        ShowInterstitials.showBackPressInterstitialAd(DistanceCalculatorActivity.this, AdmobInterstitialAd.mInterstitialAd);
    }



    @Override
    public void onAdLoaded() {
        isloaded = true;
    }

    @Override
    public void onFailedToLoadAd(@NonNull LoadAdError loadAdError) {
        //navigateToNextScreen();
        isloaded = false;

    }

    private void navigateToNextScreen() {
        if (checkForPrivacyScreen()) {
            //...........intent to home Activity
            Intent homeIntent = new Intent(DistanceCalculatorActivity.this, MainActivity.class);
            startActivity(homeIntent);
            finish();

        } else {
            //.... move to privacy
            Intent privacyIntent = new Intent(DistanceCalculatorActivity.this, PrivacyPolicyScreenActivity.class);
            startActivity(privacyIntent);
            finish();
        }
    }

    private boolean checkForPrivacyScreen() {
        return PreferenceManager.getDefaultSharedPreferences(this).getBoolean("my_pref", false);
    }






}

