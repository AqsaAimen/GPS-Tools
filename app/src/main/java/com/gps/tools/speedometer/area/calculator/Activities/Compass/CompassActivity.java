package com.gps.tools.speedometer.area.calculator.Activities.Compass;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.gps.tools.speedometer.area.calculator.Activities.AreaCalculator.AreaCalculatorMapActivity;
import com.gps.tools.speedometer.area.calculator.Activities.MainActivity;
import com.gps.tools.speedometer.area.calculator.Adapter.CompassViewAdapter;
import com.gps.tools.speedometer.area.calculator.AddsManager.AdCallBack;
import com.gps.tools.speedometer.area.calculator.AddsManager.AdmobInterstitialAd;
import com.gps.tools.speedometer.area.calculator.AddsManager.ShowInterstitials;
import com.gps.tools.speedometer.area.calculator.R;

import com.google.android.gms.ads.AdView;
import com.google.android.material.tabs.TabLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class CompassActivity extends AppCompatActivity implements AdCallBack {
   AdmobInterstitialAd interstitialAd;
   private  Boolean isloaded;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    ImageView back_button;
    Intent i;
    AlertDialog alert;
    Context myContext = this;


    InterstitialAd admob;
    com.facebook.ads.InterstitialAd fb;
    
    com.facebook.ads.AdView adViewFb;
    AdView adviewcompass;
    ConstraintLayout adviewConstraint;

    private static final int PERMISSION_REQUEST_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);
        
        
        
      //  checkCameraPermission();

        checkRuntimeLocationPermission();


       // adds.showInterstitialCompass(this, null, fb, null,admob);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        adviewConstraint = findViewById(R.id.cons_Ad_view1);
        back_button     = (ImageView) findViewById(R.id.drawer_icon_compass);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        adviewcompass= findViewById(R.id.adView_compass);
        interstitialAd.gpsToolsBannerAdMob(adviewConstraint ,this);
        tabLayout.addTab(tabLayout.newTab().setText("Simple Compass"));
        tabLayout.addTab(tabLayout.newTab().setText("Military Compass"));
        tabLayout.addTab(tabLayout.newTab().setText("AR Compass"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        final CompassViewAdapter adapter = new CompassViewAdapter(this, getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {


            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }


            public void onTabUnselected(TabLayout.Tab tab) {

            }

            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        back_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onBackPressed();
            }
        });



    }

   /* private void loadBanner(AdView adviewsounddetector) {

        // Create a new ad view.

        AdRequest adRequest = new AdRequest.Builder().build();
        adviewcompass.loadAd(adRequest);
        adviewcompass.setAdListener(new AdListener() {
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
                adviewcompass.setVisibility(View.GONE);
            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                adviewcompass.setVisibility(View.VISIBLE);
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

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }







    public void initView() {
      //  mCamera = Camera.open(findFrontFacingCamera());
        //    Toast.makeText(this, "hii this is my view", Toast.LENGTH_SHORT).show();
    }

    private boolean checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
         //   mCamera = Camera.open(findFrontFacingCamera());
            //  Toast.makeText(this, "You have already granted this permission!", Toast.LENGTH_SHORT).show();
        } else {
            requestCameraPermission();
        }
        return false;
    }


    private void requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {
            new AlertDialog.Builder(this)
                    .setTitle("Camera Permission")
                    .setMessage("Camera Permission Needed To Access Device Camera")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(CompassActivity.this,
                                    new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
                initView();
            } else {
                //  Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
                requestCameraPermission();
            }
        }
    }

    private void checkRuntimeLocationPermission() {
        Dexter.withActivity(this).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {

                if (ContextCompat.checkSelfPermission(myContext, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                   // mCamera = Camera.open(findFrontFacingCamera());
                }else {
                    requestCameraPermission();
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
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(myContext);

        // Setting Dialog Title
        alertDialog.setTitle("Camera Settings");

        // Setting Dialog Message
        alertDialog.setMessage("Camera is not enabled. Do you want to go to settings detail?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", getPackageName(), null));
                myContext.startActivity(intent);
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
    public void onBackPressed() {
        ShowInterstitials.showBackPressInterstitialAd(CompassActivity.this, AdmobInterstitialAd.mInterstitialAd);
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