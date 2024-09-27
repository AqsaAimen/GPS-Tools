package com.gps.tools.speedometer.area.calculator.Activities.SpeedoMeter;

import static com.gps.tools.speedometer.area.calculator.AddsManager.AdmobInterstitialAd.isFirstCLickEnabled;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.gps.tools.speedometer.area.calculator.Activities.MainActivity;
import com.gps.tools.speedometer.area.calculator.Activities.PedoMeter.PedoMeterActivity;
import com.gps.tools.speedometer.area.calculator.AddsManager.AdCallBack;
import com.gps.tools.speedometer.area.calculator.AddsManager.AdmobInterstitialAd;
import com.gps.tools.speedometer.area.calculator.AddsManager.ShowInterstitials;
import com.gps.tools.speedometer.area.calculator.Booleans.Booleans;
import com.gps.tools.speedometer.area.calculator.DataBase.DBHandler;
import com.gps.tools.speedometer.area.calculator.Model.SpeedoMeterHistory;
import com.gps.tools.speedometer.area.calculator.PermissionScreen.PrivacyPolicyScreenActivity;
import com.gps.tools.speedometer.area.calculator.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import de.nitri.gauge.Gauge;

public class SpeedometerActivity extends AppCompatActivity implements AdCallBack {

    private static final String TAG = "AnalogSpeedMeterScreen";
    private Context context;
    private Location startLocation;
    private Location endLocation;
    private Gauge gauge;
    AdView adviewspeedometer;
    private double curTime = 0;
    private double oldLat = 0.0;
    private double oldLon = 0.0;
    private double EARTH_RADIUS = 6371000;
    private float max_speed, avg_speed, speedSum;
    private double distance;
    private Button start;
    private Calendar c;
    private long startTime, endTime;
    private int counter;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private TextView maxSpeed, avgSpeed, distanceCovered, timeToDrive;
    private long difference;
    private long seconds;
    private long minutes;
    private long hours;
    private String driveTime;
    private String strStartTime, strStartDate;
    private String strEndTime, strEndDate;
    private DBHandler db;
    private Boolean isloaded;
    ImageView back_button;
    AdmobInterstitialAd interstitialAd;
    com.facebook.ads.InterstitialAd fb;
    
    com.facebook.ads.AdView adViewFb;
    AdView mAdViewMob;
    ConstraintLayout adviewConstraint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
       // interstitialAd.preloadAd(this);
        setContentView(R.layout.activity_speedometer);
        adviewConstraint = findViewById(R.id.cons_Ad_view);
         adviewspeedometer= findViewById(R.id.adView_speedometer);
         start= findViewById(R.id.startt);
         interstitialAd.gpsToolsBannerAdMob(adviewConstraint ,this);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        bindViewsAndSetValues();
        isloaded= false;

        back_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onBackPressed();
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (start.getText().toString().equalsIgnoreCase("start")) {
                    if (Booleans.locationPermissionn) {
                        if (checkGPSStatus(context)) {
                            refreshValues();
                            updateUItoDefaults();
                            startLocationUpdates();
                            c = Calendar.getInstance();
                            SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
                            strStartDate = dateFormat.format(c.getTime());
                            strStartTime = timeFormat.format(c.getTime());
                            startTime = System.currentTimeMillis();
                            start.setText("End");

                        } else {
                            showSettingsAlert();
                        }
                    }
                } else if (start.getText().toString().equalsIgnoreCase("end")) {
                    c = Calendar.getInstance();
                    SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
                    strEndDate = dateFormat.format(c.getTime());
                    strEndTime = timeFormat.format(c.getTime());
                    endTime = System.currentTimeMillis();
                    start.setText("Start");
                    calculateDistance();
                    calculateTimeDifference();
                    stopLocationUpdates();
                    updateUI();
                    //saveHistory();
                }
            }
        });

    }


    /*private void loadBanner(AdView adviewspeedometer) {


        // Start loading the ad in the background.
        AdRequest adRequest = new AdRequest.Builder().build();
        adviewspeedometer.loadAd(adRequest);
        adviewspeedometer.setAdListener(new AdListener() {
            @Override
            public void onAdClicked() {
                super.onAdClicked();
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                adviewspeedometer.setVisibility(View.GONE);
            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                adviewspeedometer.setVisibility(View.VISIBLE);

            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }

            @Override
            public void onAdSwipeGestureClicked() {
                super.onAdSwipeGestureClicked();
            }
        });
    }*/

    private void bindViewsAndSetValues() {

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult != null) {
                    for (Location location : locationResult.getLocations()) {
                        if (location != null) {
                            Log.d("kkkk", "Location: " + location);
                            if (startLocation == null) {
                                startLocation = location;
                                Log.d("kkkk", "start Location: " +startLocation);
                            }
                            getSpeed(location);
                            endLocation = location;
                            Log.d("kkkk", "End Location: " + endLocation);
                        }
                    }
                }

            }
        };
        strStartTime = "";
        strStartDate = "";
        strEndTime = "";
        strEndDate = "";
        startTime = 0;
        endTime = 0;
        difference = 0;
        seconds = 0;
        minutes = 0;
        hours = 0;
        driveTime = "";
        max_speed = 0;
        avg_speed = 0;
        speedSum = 0;
        distance = 0;
        max_speed = 0;
        speedSum = 0;
        counter = 0;
        startLocation = null;
        endLocation = null;
        context = this;
        start = findViewById(R.id.startt);
        gauge = findViewById(R.id.speedView);
        gauge.setMaxValue(300);
        gauge.setMinValue(0);
        gauge.setValuePerNick(5);
        gauge.setLowerTextSize(50);
        gauge.setLowerText("Km/h");

        maxSpeed = findViewById(R.id.maxSpeed);
        avgSpeed = findViewById(R.id.avgSpeed);
        distanceCovered = findViewById(R.id.distance);
        timeToDrive = findViewById(R.id.driveTime);
        back_button = (ImageView) findViewById(R.id.drawer_icon_speedometer);
        db = new DBHandler(context);
    }

/*
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
*/

    public void showSettingsAlert() {
        android.app.AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }


    public static boolean checkGPSStatus(Context context) {
        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean statusOfGPS = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return statusOfGPS;
    }


    private void getSpeed(Location location) {
        if (location != null) {
            double newTime = System.currentTimeMillis();
            double newLat = location.getLatitude();
            double newLon = location.getLongitude();
            if (location.hasSpeed()) {
                float speed = location.getSpeed();
                Toast.makeText(getApplication(),"SPEED : "+String.valueOf(speed*3.6)+"km/h",Toast.LENGTH_SHORT).show();
                speed *= 3.6;
                if (speed > max_speed) {
                    max_speed = speed;
                }
                speedSum += speed;
                counter++;
                calculateAvgSpeed();
                gauge.moveToValue(speed);
                updateUI();
            } else {
                double distance = calculationByDistance(newLat, newLon, oldLat, oldLon);
                double timeDifferent = newTime - curTime;
                double speed = distance / timeDifferent;
                curTime = newTime;
                oldLat = newLat;
                oldLon = newLon;
                float _speed = (float) speed;
                if (speed > max_speed) {
                    max_speed = _speed;
                }
                speedSum += speed;
                counter++;
                calculateAvgSpeed();
                gauge.moveToValue(_speed);
                //Toast.makeText(getApplication(),"SPEED 2 : "+String.valueOf(speed*3.6)+"km/h",Toast.LENGTH_SHORT).show();
            }
        }
    }

    public double calculationByDistance(double lat1, double lon1, double lat2, double lon2) {
        double radius = EARTH_RADIUS;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return radius * c;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void calculateDistance() {
        if (startLocation != null && endLocation != null) {
            distance = startLocation.distanceTo(endLocation);
            distance = distance * 0.001;
        }
    }

    private void calculateAvgSpeed() {
        avg_speed = speedSum / counter;

    }

    private void calculateTimeDifference() {
        difference = (endTime - startTime);
        seconds = difference / 1000 % 60;
        minutes = difference / (60 * 1000) % 60;
        hours = difference / (60 * 60 * 1000);
        driveTime = String.valueOf(hours) + ":" + String.valueOf(minutes) + ":" + String.valueOf(seconds);
        Log.e("time", driveTime);
    }

    private void updateUI() {
        double roundOffMaxSpeed = (double) Math.round(max_speed * 100) / 100;
        maxSpeed.setText(String.valueOf(Math.round(roundOffMaxSpeed)) + " km/h");
        timeToDrive.setText(driveTime);
        double roundOffDistance = (double) Math.round(distance * 100) / 100;
        distanceCovered.setText(String.valueOf(roundOffDistance) + " km");
        double roundOff = (double) Math.round(avg_speed * 100) / 100;
        avgSpeed.setText(String.valueOf(roundOff) + " km/h");
    }

    private void updateUItoDefaults() {

        maxSpeed.setText("00");
        timeToDrive.setText("00");
        distanceCovered.setText("00");
        avgSpeed.setText("00");
    }

    private void refreshValues() {
        strStartTime = "";
        strStartDate = "";
        strEndTime = "";
        strEndDate = "";
        startTime = 0;
        endTime = 0;
        c = Calendar.getInstance();
        difference = 0;
        seconds = 0;
        minutes = 0;
        hours = 0;
        driveTime = "";
        max_speed = 0;
        avg_speed = 0;
        speedSum = 0;
        distance = 0;
        max_speed = 0;
        speedSum = 0;
        counter = 0;
        startLocation = null;
        endLocation = null;
    }

   /* private void saveHistory() {
        SpeedoMeterHistory speedMeterHistory = new SpeedoMeterHistory(0, strStartTime, strStartDate, strEndTime, strEndDate, String.valueOf(Math.round(distance * 100) / 100), driveTime, String.valueOf(Math.round(max_speed * 100) / 100), String.valueOf(Math.round(avg_speed * 100) / 100), String.valueOf(startLocation.getLatitude()), String.valueOf(startLocation.getLongitude()), String.valueOf(endLocation.getLatitude()), String.valueOf(endLocation.getLongitude()));
        db.addHistory(speedMeterHistory);
    }*/


    // .................. location services
    private void stopLocationUpdates() {
        if (locationCallback != null)
            fusedLocationClient.removeLocationUpdates(locationCallback);
    }

    @SuppressLint("MissingPermission")
    private void startLocationUpdates() {
        createLocationRequest();
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null /* Looper */);
    }

    protected void createLocationRequest() {
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @SuppressLint("MissingPermission")
    private void getLastKnownLocation() {
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                        }
                    }
                });
    }



   /* public void onBackPressed() {
        if (isloaded) {
            interstitialAd.showAd(this, () -> {

                super.onBackPressed();
            });
        } else {
            super.onBackPressed();
        }

    }*/
   @Override
   public void onBackPressed() {
       ShowInterstitials.showBackPressInterstitialAd(SpeedometerActivity.this, AdmobInterstitialAd.mInterstitialAd);
   }


    @Override
    public void onAdLoaded() {
        isloaded= true;
        Log.d("bbbb", "onAdLoaded from speedometer activity: ");
       // interstitialAd.showAd(this);
    }

    @Override
    public void onFailedToLoadAd(@NonNull LoadAdError loadAdError) {
     //
        // navigateToNextScreen();
        isloaded= false;
        Log.d("bhbhbhbhb", "onAdLoaded from speedometer activity: ");
    }

    private void navigateToNextScreen() {
        if (checkForPrivacyScreen()) {
            //...........intent to home Activity
            Intent homeIntent = new Intent(SpeedometerActivity.this, MainActivity.class);
            startActivity(homeIntent);
            finish();

        } else {
            //.... move to privacy
            Intent privacyIntent = new Intent(SpeedometerActivity.this, PrivacyPolicyScreenActivity.class);
            startActivity(privacyIntent);
            finish();
        }
    }

    private boolean checkForPrivacyScreen() {
        return PreferenceManager.getDefaultSharedPreferences(this).getBoolean("my_pref", false);
    }
}
