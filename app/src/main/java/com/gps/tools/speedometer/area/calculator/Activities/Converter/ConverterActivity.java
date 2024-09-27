package com.gps.tools.speedometer.area.calculator.Activities.Converter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.android.gms.ads.LoadAdError;
import com.gps.tools.speedometer.area.calculator.Activities.AreaCalculator.AreaCalculatorMapActivity;
import com.gps.tools.speedometer.area.calculator.Activities.MainActivity;
import com.gps.tools.speedometer.area.calculator.Activities.Mirror.MirrorActivity;
import com.gps.tools.speedometer.area.calculator.AddsManager.AdCallBack;
import com.gps.tools.speedometer.area.calculator.AddsManager.AdmobInterstitialAd;
import com.gps.tools.speedometer.area.calculator.AddsManager.ShowInterstitials;
import com.gps.tools.speedometer.area.calculator.PermissionScreen.PrivacyPolicyScreenActivity;
import com.gps.tools.speedometer.area.calculator.R;

import org.checkerframework.checker.units.qual.C;

public class ConverterActivity extends AppCompatActivity implements AdCallBack {

    CardView lengthBtn, areaBtn, volumeBtn, weightBtn, temBtn, speedBtn, pressureBtn, powerBtn, workBtn, dataBtn, energyBtn, forceBtn;

    public static final int LENGTH = 0;
    public static final int AREA = 1;
    public static final int VOLUME = 2;
    public static final int WEIGHT = 3;
    public static final int TEMPERATURE = 4;
    public static final int SPEED = 5;
    public static final int PRESSURE = 6;
    public static final int POWER = 7;
    public static final int WORK = 8;
    public static final int DATA = 9;
    public static final int ENERGY = 10;
    public static final int Force = 11;

    ImageView back_button;

    int a = 0;
    //Context mctx = this;
    FrameLayout FrameLayoutForMedium;
    AdmobInterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter_main);

        
        
        


        //Log.d(TAG, "onCreate: Splash");


        // loadAdds();



        FrameLayoutForMedium = findViewById(R.id.ad_view_converter);
        interstitialAd.gpsToolsAdMobMeduimBanner(FrameLayoutForMedium, this);
        lengthBtn = (CardView) findViewById(R.id.card_view_length);
        areaBtn = (CardView) findViewById(R.id.card_view_area);
        volumeBtn = (CardView) findViewById(R.id.card_view_volume);
        weightBtn = (CardView) findViewById(R.id.card_view_weight);
        temBtn = (CardView) findViewById(R.id.card_view_tem);
        speedBtn = (CardView) findViewById(R.id.card_view_speed);
        pressureBtn = (CardView) findViewById(R.id.card_view_pressure);
        powerBtn = (CardView) findViewById(R.id.card_view_power);
        workBtn = (CardView) findViewById(R.id.card_view_work);
        dataBtn = (CardView) findViewById(R.id.card_view_data);
        energyBtn = (CardView) findViewById(R.id.card_view_energy);
        forceBtn = (CardView) findViewById(R.id.card_view_force);


        back_button = (ImageView) findViewById(R.id.drawer_icon_converter);

        back_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onBackPressed();
            }
        });

       lengthBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent i = new Intent(getApplicationContext(),ConverterDetailActivity.class);
               ShowInterstitials.showAdWithOnlyFirstAndThirdEnabled(ConverterActivity.this, interstitialAd.mInterstitialAd, i);
           }
       });

        areaBtn.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ConverterDetailActivity.class);
                ShowInterstitials.showAdWithOnlyFirstAndThirdEnabled(ConverterActivity.this, interstitialAd.mInterstitialAd, i);

            }
        });

        volumeBtn.setOnClickListener(new View.OnClickListener() {

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), ConverterDetailActivity.class);
                ShowInterstitials.showAdWithOnlyFirstAndThirdEnabled(ConverterActivity.this, interstitialAd.mInterstitialAd, i);

            }
        });

        weightBtn.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), ConverterDetailActivity.class);
                ShowInterstitials.showAdWithOnlyFirstAndThirdEnabled(ConverterActivity.this, interstitialAd.mInterstitialAd, i);

            }
        });

        temBtn.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), ConverterDetailActivity.class);
                ShowInterstitials.showAdWithOnlyFirstAndThirdEnabled(ConverterActivity.this, interstitialAd.mInterstitialAd, i);

            }
        });

        speedBtn.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), ConverterDetailActivity.class);
                ShowInterstitials.showAdWithOnlyFirstAndThirdEnabled(ConverterActivity.this, interstitialAd.mInterstitialAd, i);

            }
        });

        pressureBtn.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), ConverterDetailActivity.class);
                ShowInterstitials.showAdWithOnlyFirstAndThirdEnabled(ConverterActivity.this, interstitialAd.mInterstitialAd, i);
            }
        });

        powerBtn.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), ConverterDetailActivity.class);
                ShowInterstitials.showAdWithOnlyFirstAndThirdEnabled(ConverterActivity.this, interstitialAd.mInterstitialAd, i);

            }
        });

        workBtn.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), ConverterDetailActivity.class);
                ShowInterstitials.showAdWithOnlyFirstAndThirdEnabled(ConverterActivity.this, interstitialAd.mInterstitialAd, i);

            }
        });

        dataBtn.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), ConverterDetailActivity.class);
                ShowInterstitials.showAdWithOnlyFirstAndThirdEnabled(ConverterActivity.this, interstitialAd.mInterstitialAd, i);

            }
        });
        energyBtn.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), ConverterDetailActivity.class);
                ShowInterstitials.showAdWithOnlyFirstAndThirdEnabled(ConverterActivity.this, interstitialAd.mInterstitialAd, i);

            }
        });

        forceBtn.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), ConverterDetailActivity.class);
                ShowInterstitials.showAdWithOnlyFirstAndThirdEnabled(ConverterActivity.this, interstitialAd.mInterstitialAd, i);
            }
        });

    }




    private void navigateToNextScreen() {
        if (checkForPrivacyScreen()) {
            //...........intent to home Activity
            Intent homeIntent = new Intent(ConverterActivity.this, MainActivity.class);
            startActivity(homeIntent);
            finish();

        } else {
            //.... move to privacy
            Intent privacyIntent = new Intent(ConverterActivity.this, PrivacyPolicyScreenActivity.class);
            startActivity(privacyIntent);
            finish();
        }
    }

    private boolean checkForPrivacyScreen() {
        return PreferenceManager.getDefaultSharedPreferences(this).getBoolean("my_pref", false);
    }
    public void onBackPressed() {
        ShowInterstitials.showBackPressInterstitialAd(ConverterActivity.this, AdmobInterstitialAd.mInterstitialAd);
        finish();
    }


    @Override
    public void onAdLoaded() {
        //Log.d(TAG, "onAdLoaded: ");
    }

    @Override
    public void onFailedToLoadAd(@NonNull LoadAdError loadAdError) {
    }
}