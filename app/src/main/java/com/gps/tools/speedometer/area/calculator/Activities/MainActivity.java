package com.gps.tools.speedometer.area.calculator.Activities;

import static com.gps.tools.speedometer.area.calculator.AddsManager.AppOpenResumeAd.isAppOpenAdShow;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.messaging.FirebaseMessaging;
import com.gps.tools.speedometer.area.calculator.Activities.Altimeter.AltiMeterActivity;
import com.gps.tools.speedometer.area.calculator.Activities.AreaCalculator.AreaCalculatorMapActivity;
import com.gps.tools.speedometer.area.calculator.Activities.Compass.CompassActivity;
import com.gps.tools.speedometer.area.calculator.Activities.Converter.ConverterActivity;
import com.gps.tools.speedometer.area.calculator.Activities.DistanceCalculator.DistanceCalculatorActivity;
import com.gps.tools.speedometer.area.calculator.Activities.Mirror.MirrorActivity;
import com.gps.tools.speedometer.area.calculator.Activities.NearbyObjectDetector.NearByObjectActivity;
import com.gps.tools.speedometer.area.calculator.Activities.PedoMeter.PedoMeterActivity;
import com.gps.tools.speedometer.area.calculator.Activities.SoundDetector.SoundDetectorActivity;
import com.gps.tools.speedometer.area.calculator.Activities.SpeedoMeter.SpeedometerActivity;
import com.gps.tools.speedometer.area.calculator.AddsManager.AdCallBack;
import com.gps.tools.speedometer.area.calculator.AddsManager.AdmobInterstitialAd;
import com.gps.tools.speedometer.area.calculator.AddsManager.AppOpenResumeAd;
import com.gps.tools.speedometer.area.calculator.AddsManager.ShowInterstitials;
import com.gps.tools.speedometer.area.calculator.AddsManager.TinyDB;
import com.gps.tools.speedometer.area.calculator.BillingMethod.GPSToolsBillingHelper;
import com.gps.tools.speedometer.area.calculator.R;
import com.gps.tools.speedometer.area.calculator.permission.BuildConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, AdCallBack {

    CardView soundDetector, nearbyObject, compass, converter, mirror, pedometer, speedoMeter, altiMeter, areaCalculaotr;
    CardView distance_calculator, street_view, gps_navigation;
    Intent i;
    private Toolbar toolbar;

    BillingProcessor bp;
    LinearLayout isShowDialogue;
    private boolean isiconSelected = false;
    Context context;
   private static String ardt;
    com.facebook.ads.InterstitialAd fb;
    //
    com.facebook.ads.AdView adViewFb;
    AdmobInterstitialAd interstitialAd;
    // AdView mAdViewMob;
    private static final String TAG = "Adds";
    private UnifiedNativeAd nativeAd;
    android.app.AlertDialog alert;
    AlertDialog alertt;

    private FirebaseAnalytics mFirebaseAnalytics;
    View bannerBottomView;
    LinearLayout bannerView;
    ConstraintLayout adviewConstraint;

    boolean isGpsEnabled;
    private static final int PERMISSION_REQUEST_CODE = 200;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    private Boolean isShowAdOnAreAndDis = false;
    private GPSToolsBillingHelper billingHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        billingHelper = new GPSToolsBillingHelper(this);

//        bannerBottomView = findViewById(R.id.smallAdBanner);
//        bannerView = bannerBottomView.findViewById(R.id.adContainer);
        adviewConstraint = findViewById(R.id.cons_Ad_view);

        isShowDialogue = findViewById(R.id.diallayout);
        interstitialAd.gpsToolsBannerAdMob(adviewConstraint,this);


        // adds = new AppAdds();
      /*  fb = adds.loadFbInterstitialHome(this);
        admob = adds.loadAdMobInterstitialHome(this);*/

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);


        FirebaseMessaging.getInstance().subscribeToTopic(BuildConfig.APPLICATION_ID);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "item_id");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "item_name");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        Menu menu= navigationView.getMenu();
        soundDetector = (CardView) findViewById(R.id.card_view_sound_detector);
        nearbyObject = (CardView) findViewById(R.id.card_view_nearby_object);
        compass = (CardView) findViewById(R.id.card_view_compass);
        converter = (CardView) findViewById(R.id.card_view_converter);
        mirror = (CardView) findViewById(R.id.card_view_mirror);
        pedometer = (CardView) findViewById(R.id.card_view_pedometer);
        speedoMeter = (CardView) findViewById(R.id.card_view_speedometer);
        altiMeter = (CardView) findViewById(R.id.card_view_Altimeter);
        areaCalculaotr = (CardView) findViewById(R.id.card_view_area);
        distance_calculator = (CardView) findViewById(R.id.card_view_distance_calculator);

        RelativeLayout dial=findViewById(R.id.mainDial);
        dial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dial.setVisibility(view.GONE);
            }
        });
        // street_view = (CardView) findViewById(R.id.card_view_street_view);
        //  gps_navigation = (CardView) findViewById(R.id.card_view_navigation);
        isGpsEnabled = false;


        soundDetector.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SoundDetectorActivity.class);
                ShowInterstitials.showAdAfterFirstClickEnabled(MainActivity.this, interstitialAd.mInterstitialAd, i);


//               interstitialAd.showAdAfterFirstClickEnabled(MainActivity.this,()->{
//                   i = new Intent(getApplicationContext(), SoundDetectorActivity.class);
//                   startActivity(i);
//               });

            }
        });

        nearbyObject.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), NearByObjectActivity.class);
                ShowInterstitials.showAdAfterFirstClickEnabled(MainActivity.this, interstitialAd.mInterstitialAd, i);

               /* interstitialAd.showAdAfterFirstClickEnabled(MainActivity.this,()->{
                    i = new Intent(getApplicationContext(), NearByObjectActivity.class);
                    startActivity(i);
                });*/
            }
        });

        compass.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), CompassActivity.class);
                ShowInterstitials.showAdAfterFirstClickEnabled(MainActivity.this, interstitialAd.mInterstitialAd, i);
                /*interstitialAd.showAdAfterFirstClickEnabled(MainActivity.this,()->{
                    i = new Intent(getApplicationContext(), CompassActivity.class);
                    startActivity(i);
                });*/
            }
        });

        converter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ConverterActivity.class);
                ShowInterstitials.showAdAfterFirstClickEnabled(MainActivity.this, interstitialAd.mInterstitialAd, i);

                /*interstitialAd.showAdAfterFirstClickEnabled(MainActivity.this,()->{
                    i = new Intent(getApplicationContext(), ConverterActivity.class);
                    startActivity(i);
                });*/
            }
        });

        mirror.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MirrorActivity.class);
                ShowInterstitials.showAdAfterFirstClickEnabled(MainActivity.this, interstitialAd.mInterstitialAd, i);

                /*interstitialAd.showAdAfterFirstClickEnabled(MainActivity.this,()->{
                    i = new Intent(getApplicationContext(), MirrorActivity.class);
                    startActivity(i);
                });*/
            }
        });


        pedometer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), PedoMeterActivity.class);
                ShowInterstitials.showAdAfterFirstClickEnabled(MainActivity.this, interstitialAd.mInterstitialAd, i);

               /*interstitialAd.showAdAfterFirstClickEnabled(MainActivity.this,()->{
                   i = new Intent(getApplicationContext(), PedoMeterActivity.class);
                   startActivity(i);
               });*/
            }
        });

        altiMeter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AltiMeterActivity.class);
                ShowInterstitials.showAdAfterFirstClickEnabled(MainActivity.this, interstitialAd.mInterstitialAd, i);

               /* interstitialAd.showAdAfterFirstClickEnabled(MainActivity.this,()->{
                    i = new Intent(getApplicationContext(), AltiMeterActivity.class);
                    startActivity(i);
                });*/
            }
        });

        speedoMeter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SpeedometerActivity.class);
                ShowInterstitials.showAdAfterFirstClickEnabled(MainActivity.this, interstitialAd.mInterstitialAd, i);

                /*interstitialAd.showAdAfterFirstClickEnabled(MainActivity.this,()->{
                    i = new Intent(getApplicationContext(), SpeedometerActivity.class);
                    startActivity(i);
                });*/
            }
        });

        areaCalculaotr.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                        i = new Intent(getApplicationContext(), AreaCalculatorMapActivity.class);
                        startActivity(i);


                }
            });


        distance_calculator.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                {

                        i = new Intent(getApplicationContext(), DistanceCalculatorActivity.class);
                        startActivity(i);

                }
            }
        });

       /* street_view.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=com.streetview.voice.navigation.traffic.status"));
                startActivity(viewIntent);

            }
        });

        gps_navigation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=gps.navigation.maps.directions.location.finder"));
                startActivity(viewIntent);
            }
        });*/



    }



    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_rate_us) {
            RatingDialogCustom ratingDialog= new RatingDialogCustom();
            ratingDialog.showExitOnlyDialog(MainActivity.this);
            //RelativeLayout dial=findViewById(R.id.mainDial);
            //dial.setVisibility(View.VISIBLE);
            //showRatingDialoge();
           // Intent viewintent= new Intent(this, ExitOnlyDialog.class);
            //startActivity(viewintent);

            isAppOpenAdShow(MainActivity.this,false);


        } else if (id == R.id.nav_share) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Hey check out my app at: https://play.google.com/store/apps/details?id=" + getPackageName());
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
            isAppOpenAdShow(MainActivity.this,false);

        } else if (id == R.id.nav_priv_policy) {

            Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse("https://gpstoolsandconverter.blogspot.com/2024/07/privacy-policy.html"));
            startActivity(viewIntent);
            isAppOpenAdShow(MainActivity.this,false);

        } else if (id == R.id.nav_more) {

            i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("https://play.google.com/store/apps/developer?id=Navigation+Apps+Studio"));
            startActivity(i);
            isAppOpenAdShow(MainActivity.this,false);
        } else if (id == R.id.nav_remove_add) {
             billingHelper.purchaseProduct();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);

    }



    public void onBackPressed() {

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        } else {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                            finishAffinity();
                            AppOpenResumeAd.appOpenAd=null;
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            alertt = builder.create();
            alertt.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialogInterface) {
                    alertt.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
                    alertt.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
                }
            });
            alertt.show();


        }


    }


    @Override
    protected void onResume() {
        super.onResume();
        isAppOpenAdShow(MainActivity.this,true);
        //visibilitygone();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!bp.handleActivityResult(requestCode, resultCode, data))
            super.onActivityResult(requestCode, resultCode, data);
    }


    public void initView() {
        //  mCamera = Camera.open(findFrontFacingCamera());
        //    Toast.makeText(this, "hii this is my view", Toast.LENGTH_SHORT).show();
    }

    private boolean checkLoactionPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //   mCamera = Camera.open(findFrontFacingCamera());
            //  Toast.makeText(this, "You have already granted this permission!", Toast.LENGTH_SHORT).show();
        } else {
            requestLocationPermission();
        }
        return false;
    }


    private void requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            new AlertDialog.Builder(this)
                    .setTitle("Location Permission")
                    .setMessage("Location Permission Needed To Access Device Location")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            checkLoactionPermission();
                        }
                    })
                    .create().show();

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {


      /*  if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
                initView();
            } else {
              //  Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
                requestLocationPermission();
            }
        }*/


        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS: {

                Map<String, Integer> perms = new HashMap<>();
                // Initialize the map with both permissions
                perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.RECORD_AUDIO, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
                // Fill with actual results from user
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    // Check for both permissions
                    if (perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED && perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        Log.d(TAG, "Camera, Audio & Location services permission granted");
                        // process the normal flow
                        //else any one or both the permissions are not granted
                    } else {
                        Log.d(TAG, "Some permissions are not granted ask again ");
                        //permission is denied (this is the first time, when "never ask again" is not checked) so ask again explaining the usage of permission
//                        // shouldShowRequestPermissionRationale will return true
                        //show the dialog or snackbar saying its necessary and try again otherwise proceed with setup.
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA) || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECORD_AUDIO) || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                            showDialogOK("Camera, Audio and Location Services Permission required for this app",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    checkAndRequestPermissions();
                                                    break;
                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    permision1();
                                                    break;
                                            }
                                        }
                                    });
                        }
                        //permission is denied (and never ask again is  checked)
                        //shouldShowRequestPermissionRationale will return false
                        else {
                            Toast.makeText(this, "Go to settings and enable permissions", Toast.LENGTH_LONG)
                                    .show();
                            //                            //proceed with logic by disabling the related features or quit the app.
                        }
                    }
                }
            }
        }
    }

    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }

    private boolean checkAndRequestPermissions() {
        int permissionCamera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int permissionAudio = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        int locationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (locationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (permissionCamera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (permissionAudio != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.RECORD_AUDIO);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    private boolean checkLoactionPermissio() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA) || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECORD_AUDIO) || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            //   mCamera = Camera.open(findFrontFacingCamera());
            //  Toast.makeText(this, "You have already granted this permission!", Toast.LENGTH_SHORT).show();
        } else {
            //  onRequestPermissionsResult();
        }
        return false;
    }

    private void permision1() {


        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA) || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECORD_AUDIO) || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            showDialogOK("Camera, Audio and Location Services Permission required for this app", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:
                            checkAndRequestPermissions();
                            break;
                        case DialogInterface.BUTTON_NEGATIVE:
                            permision2();
                            break;
                    }
                }
            });
        }
    }

    private void permision2() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA) || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECORD_AUDIO) || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            showDialogOK("Camera, Audio and Location Services Permission required for this app", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:
                            checkAndRequestPermissions();
                            break;
                        case DialogInterface.BUTTON_NEGATIVE:
                            permision1();
                            break;
                    }
                }
            });
        }
    }

    @Override
    public void onAdLoaded() {
        Log.d("bhbhbhbhb", "onAdLoaded from main activity: ");
    }

    @Override
    public void onFailedToLoadAd(@NonNull LoadAdError loadAdError) {
        Log.d("bhbhbhbhb", "onFailedToLoadAd from main activity: ");
    }



}

