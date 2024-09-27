package com.gps.tools.speedometer.area.calculator.Activities.Altimeter;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.github.anastr.speedviewlib.DeluxeSpeedView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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

import java.io.IOException;
import java.util.List;

public class AltiMeterActivity extends AppCompatActivity implements SensorEventListener, AdCallBack {
    private Sensor aSensor;
    double height;
    String address;
    private SensorManager mSensorManager;
    public DeluxeSpeedView speedometer;
    ImageView back_button;

    //  InterstitialAd admob;
    com.facebook.ads.InterstitialAd fb;

    double lat, lng;
    GoogleMap mMap;
    AdmobInterstitialAd interstitialAd;
    com.facebook.ads.AdView adViewFb;
    List<Address> addressesList;
    Location currentLocation;
    FrameLayout FrameLayoutForMedium;
    private static final int REQUEST_CODE = 101;
    private Boolean isloaded;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;
    Geocoder geocoder;
    AlertDialog alert;
    double passlat = 0.0;
    double passlng = 0.0;
    Context context;
    boolean locationPermission;
    LocationManager locationmanager;
    boolean isGpsEnabled;
    boolean result;
    boolean isNetWorkEnabled = false;
    public static final String LOCATION_SERVICE = "location";

    private LocationCallback locationCallback;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private static final int REQUEST_LOCATION_PERMISSION = 1;

    @SuppressLint("WrongConstant")
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_alti_meter);

        this.speedometer = (DeluxeSpeedView) findViewById(R.id.speedoMeter);

        FrameLayoutForMedium = findViewById(R.id.ad_view_container);
        // adview= findViewById(R.id.adView_altimeter);
        interstitialAd.gpsToolsAdMobMeduimBanner(FrameLayoutForMedium, this);
        back_button = (ImageView) findViewById(R.id.drawer_icon_altimeter);

        back_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onBackPressed();
            }
        });
        isloaded = false;

        if (!getApplicationContext().getPackageManager().hasSystemFeature("android.hardware.sensor.barometer")) {
            //Toast.makeText(getApplicationContext(), "Altitude Meter will not work!\nBecause Pressure Sensor is not available in this device", 1).show();
        }
        this.mSensorManager = (SensorManager) getSystemService("sensor");
        if (this.mSensorManager != null) {
            this.aSensor = this.mSensorManager.getDefaultSensor(6);
            this.mSensorManager.registerListener(this, this.aSensor, 3);
        }


        locationPermission = false;
        isGpsEnabled = false;

        createLocationRequest();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    Log.d("nnn", "Location result is null" + locationResult);
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    Log.d("nnn", "New location: " + location.toString());
                    updateAltitude(location);
                }
            }
        };
        checkRuntimeLocationPermission();



    }


        /*locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000); // 10 seconds
        locationRequest.setFastestInterval(5000); // 5 seconds


        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    Log.d("fdafdfsdaaa", "onLocationResult location : " + location);
                    if (location.hasAltitude()) {
                        double altitude = location.getAltitude();
                        speedometer.speedTo((float) altitude);
                    }
                }
            }
        };*/




    /* private void requestLocationUpdates() {
         if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                 ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
             ActivityCompat.requestPermissions(this,
                     new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                     LOCATION_PERMISSION_REQUEST_CODE);
             return;
         }
         fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
     }*/
    /*private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            Location location = task.getResult();
                            Log.d("gggg", "onLocationResult location : " + location);
                            double altitude = location.getAltitude();
                            speedometer.speedTo((float) altitude);

                            // Use the altitude as needed
                        }
                    }
                });*/
       /* if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation()
                .addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            Location location = task.getResult();
                            Log.d("gggg", "onLocationResult location : " + location);
                            double altitude = location.getAltitude();
                            speedometer.speedTo((float) altitude);
                            // Use the altitude value
                        } else {
                            // Handle the case where the location is null


                        }
                        }
                    });*/
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
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
                    Log.d("ffff", "onLocationResult location : " + location);
                    lat = Double.parseDouble(String.valueOf(currentLocation.getLatitude()));
                    lng = Double.parseDouble(String.valueOf(currentLocation.getLongitude()));
                    Log.d("fdafdfsd", "onLocationResult latitude : " + lat);
                    Log.d("fdafdfsd", "onLocationResult longitude : " + lng);

                        if (task.isSuccessful() && task.getResult() != null) {
                            location = task.getResult();
                            double altitude = location.getAltitude();
                            speedometer.speedTo((float) altitude);
                            Log.d("fdafdfsd", "onLocationResult altitude : " + altitude);
                        }

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
                                double altitude = location1.getAltitude();
                                speedometer.speedTo((float) altitude);
                                Log.d("fdafdfsd", "onLocationResult latitude : " + latitude);
                                Log.d("fdafdfsd", "onLocationResult longitude : " + longitude);
                                Log.d("fdafdfsd", "onLocationResult altitude : " + altitude);
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
                        getCurrentLocation();
                        return;
                    }
                    fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());



                }
            }
        });*/


    private void checkRuntimeLocationPermission() {
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        if (checkGPSStatus(AltiMeterActivity.this)) {
                            createLocationRequest();
                           getLastKnownLocation();
                           startLocationUpdates();
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
                        token.continuePermissionRequest();
                    }
                }).check();
    }


    protected void createLocationRequest() {
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    /*public boolean checkGPSStatus(Context context) {
        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean statusOfGPS = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        getLastKnownLocation();
        return statusOfGPS;
    }*/
    public boolean checkGPSStatus(Context context) {
        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public void showSettingsAlertt() {
        Context context = this;
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle("Location Settings");
        alertDialog.setMessage("Location is not enabled. Do you want to go to settings detail?");
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(intent);
             getLastKnownLocation();
            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alert = alertDialog.create();
        alert.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
            }
        });
        alert.show();
    }

    public void showSettingsAlert() {
        Context context = this;
        if (context != null) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
            alertDialog.setTitle("GPS Settings");
            alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");
            alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    context.startActivity(intent);
                  getLastKnownLocation();

                }
            });
            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            alert = alertDialog.create();
            alert.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialogInterface) {
                    alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
                    alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
                }
            });
            alert.show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
               getLastKnownLocation();
                updateAltitude(currentLocation);
                startLocationUpdates();
            } else {
                showSettingsAlertt();
            }
        }
    }

    private void getLastKnownLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d("Permissions", "Location permissions not granted");
            return;
        }
        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            updateAltitude(location);
                        } else {
                            startLocationUpdates();
                        }
                    }
                });
    }
    /*private void fetchLastLocation() {

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
    }*/
    private void updateAltitude(Location location) {
        if (location.hasAltitude()) {
            double altitude = location.getAltitude();
            speedometer.speedTo((float) altitude);
        } else {
            // Altitude not available, handle appropriately
            speedometer.speedTo(0); // Or any default value
        }
    }


    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());

    }


    public void onAccuracyChanged(Sensor sensor, int i) {
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    public void onStop() {
        super.onStop();
    }


    public void onPause() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
        super.onPause();

    }


    @SuppressLint("WrongConstant")
    public void onSensorChanged(SensorEvent sensorEvent) {
        this.mSensorManager = (SensorManager) getSystemService("sensor");
        this.aSensor = this.mSensorManager.getDefaultSensor(6);
        this.mSensorManager.registerListener(this, this.aSensor, 3);
        this.height = (double) SensorManager.getAltitude(1013.25f, (float) ((double) sensorEvent.values[0]));
        this.speedometer.speedTo((float) this.height);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        onBackPressed();
        return true;
    }

    public void onBackPressed() {
        ShowInterstitials.showBackPressInterstitialAd(AltiMeterActivity.this, AdmobInterstitialAd.mInterstitialAd);
    }


    @Override
    public void onAdLoaded() {
        isloaded = true;
    }

    @Override
    public void onFailedToLoadAd(@NonNull LoadAdError loadAdError) {
        isloaded = false;
    }

    public void animateCamera(GoogleMap mMap, double latitude, double longitude, float zoomLevel) {
        LatLng latLng = new LatLng(latitude, longitude);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel));
    }

    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
           // getLastKnownLocation();
            startLocationUpdates();
        }
    }
}
