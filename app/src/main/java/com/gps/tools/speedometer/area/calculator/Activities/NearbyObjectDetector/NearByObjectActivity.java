package com.gps.tools.speedometer.area.calculator.Activities.NearbyObjectDetector;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.github.anastr.speedviewlib.PointerSpeedometer;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.gps.tools.speedometer.area.calculator.Activities.MainActivity;
import com.gps.tools.speedometer.area.calculator.Activities.Mirror.MirrorActivity;
import com.gps.tools.speedometer.area.calculator.AddsManager.AdCallBack;
import com.gps.tools.speedometer.area.calculator.AddsManager.AdmobInterstitialAd;
import com.gps.tools.speedometer.area.calculator.AddsManager.ShowInterstitials;
import com.gps.tools.speedometer.area.calculator.R;

import java.util.HashMap;

public class NearByObjectActivity extends AppCompatActivity implements SensorEventListener, AdCallBack {
AdmobInterstitialAd interstitialAd;
private  Boolean isloaded;
    private HashMap _$_findViewCache;

    private Sensor proximitySensor;
    private SensorManager sensorManager;
    Context context = this;
    ImageView back_button;
     AdView adviewnearbyobj;
    InterstitialAd admob;
    com.facebook.ads.InterstitialAd fb;
    FrameLayout FrameLayoutForMedium;
    com.facebook.ads.AdView adViewFb;
    AdView mAdViewMob;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_by_object);
        
        
        
        FrameLayoutForMedium = findViewById(R.id.ad_view_nearbyobj);
        interstitialAd.gpsToolsAdMobMeduimBanner(FrameLayoutForMedium, this);

        back_button = (ImageView) findViewById(R.id.drawer_icon_nearby);

        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

         @SuppressLint("WrongConstant") Object systemService = getSystemService("sensor");
        if (systemService != null) {
            this.sensorManager = (SensorManager) systemService;
            try {
                SensorManager sensorManager2 = this.sensorManager;
                if (sensorManager2 == null) {
                    // Intrinsics.throwUninitializedPropertyAccessException("sensorManager");
                }
                if (sensorManager2 != null) {
                    SensorManager sensorManager3 = this.sensorManager;
                    if (sensorManager3 == null) {
                        // Intrinsics.throwUninitializedPropertyAccessException("sensorManager");
                    }
                    Sensor defaultSensor = sensorManager3.getDefaultSensor(8);
                    //Intrinsics.checkExpressionValueIsNotNull(defaultSensor, "sensorManager.getDefault…or(Sensor.TYPE_PROXIMITY)");
                    this.proximitySensor = defaultSensor;
                }
            } catch (Exception unused) {
            }
        } else {
            //throw new TypeCastException("null cannot be cast to non-null type android.hardware.SensorManager");
        }

        back_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }




    public View _$_findCachedViewById(int i) {
        if (this._$_findViewCache == null) {
            this._$_findViewCache = new HashMap();
        }
        View view = (View) this._$_findViewCache.get(Integer.valueOf(i));
        if (view != null) {
            return view;
        }
        View findViewById = findViewById(i);
        this._$_findViewCache.put(Integer.valueOf(i), findViewById);
        return findViewById;
    }



    public void onAccuracyChanged(@Nullable Sensor sensor, int i) {
    }


    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        try {
            SensorManager sensorManager2 = this.sensorManager;
            if (sensorManager2 == null) {
               // Intrinsics.throwUninitializedPropertyAccessException("sensorManager");
            }
            SensorEventListener sensorEventListener = this;
            Sensor sensor = this.proximitySensor;
            if (sensor == null) {
                //Intrinsics.throwUninitializedPropertyAccessException("proximitySensor");
            }
            sensorManager2.registerListener(sensorEventListener, sensor, 2);
        } catch (Exception unused) {
        }

    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        try {
            SensorManager sensorManager2 = this.sensorManager;
            if (sensorManager2 == null) {
                //Intrinsics.throwUninitializedPropertyAccessException("sensorManager");
            }
            sensorManager2.unregisterListener(this);
        } catch (Exception unused) {
        }
    }

    @SuppressLint("ResourceType")
    public void onSensorChanged(@Nullable SensorEvent sensorEvent) {
        if (sensorEvent == null) {
            //Intrinsics.throwNpe();
        }
        float[] fArr = sensorEvent.values;
        //Intrinsics.checkExpressionValueIsNotNull(fArr, "p0!!.values");
        if (fArr[0] > ((float) 0)) {
            ((PointerSpeedometer) _$_findCachedViewById(R.id.progressMeter)).speedTo(0.0f, 800);
            TextView textView = (TextView) _$_findCachedViewById(R.id.detectedTv);
           // Intrinsics.checkExpressionValueIsNotNull(textView, "detectedTv");
            textView.setText("");
            PointerSpeedometer pointerSpeedometer = (PointerSpeedometer) _$_findCachedViewById(R.id.progressMeter);
           // Intrinsics.checkExpressionValueIsNotNull(pointerSpeedometer, "progressMeter");
            pointerSpeedometer.setSpeedometerColor(getResources().getColor(R.color.myBlack));
            ((RelativeLayout) _$_findCachedViewById(R.id.container)).setBackgroundResource(17170443);
            return;
        }
        ((PointerSpeedometer) _$_findCachedViewById(R.id.progressMeter)).speedTo(1.0f, 800);
        TextView textView2 = (TextView) _$_findCachedViewById(R.id.detectedTv);
      //  Intrinsics.checkExpressionValueIsNotNull(textView2, "detectedTv");
        textView2.setText(getString(R.string.something_detected));
        UtilHelper.Companion.vibrate(this);
        PointerSpeedometer pointerSpeedometer2 = (PointerSpeedometer) _$_findCachedViewById(R.id.progressMeter);
       // Intrinsics.checkExpressionValueIsNotNull(pointerSpeedometer2, "progressMeter");
        pointerSpeedometer2.setSpeedometerColor(getResources().getColor(R.color.dark_red));
        ((RelativeLayout) _$_findCachedViewById(R.id.container)).setBackgroundColor(ContextCompat.getColor(this, 17170443));
    }


    public void onBackPressed() {
        ShowInterstitials.showBackPressInterstitialAd(NearByObjectActivity.this, AdmobInterstitialAd.mInterstitialAd);
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

