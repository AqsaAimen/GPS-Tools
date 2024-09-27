package com.gps.tools.speedometer.area.calculator.Activities.Compass;

import android.annotation.SuppressLint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.exifinterface.media.ExifInterface;
import androidx.fragment.app.Fragment;

import com.google.android.gms.ads.AdView;


import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.gps.tools.speedometer.area.calculator.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class SimpleFragment extends Fragment implements SensorEventListener {

    private float currentDegree = 0.0f;
    TextView dmw;
    private TextView heading;
    private ImageView imageView;
    TextView loc;
    private SensorManager mSensorManager;
    TextView txtmonth;
    TextView txtyear;

    InterstitialAd admob;
    com.facebook.ads.InterstitialAd fb;
    
    com.facebook.ads.AdView adViewFb;
    AdView mAdViewMob;
    View v;




    public SimpleFragment() {
        // Required empty public constructor
    }

    @SuppressLint("WrongConstant")
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_simple, container, false);



        heading = (TextView) v.findViewById(R.id.heading);
        imageView = (ImageView) v.findViewById(R.id.imageView);
        loc = (TextView) v.findViewById(R.id.loc);
        dmw = (TextView) v.findViewById(R.id.dmw);
        txtmonth = (TextView) v.findViewById(R.id.txtmonth);
        txtyear = (TextView) v.findViewById(R.id.txtyear);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss aa");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("gmt"));
        String format = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss aa").format(Calendar.getInstance().getTime());
        System.out.println(format);
        this.loc.setText(format);
        Calendar instance = Calendar.getInstance();
        @SuppressLint("WrongConstant") int i = instance.get(7);
        @SuppressLint("WrongConstant") int i2 = instance.get(2);
        this.txtyear.setText(String.valueOf(instance.get(1)));
        switch (i) {
            case 1:
                this.dmw.setText("Sunday");
                break;
            case 2:
                this.dmw.setText("MONDAY");
                break;
            case 3:
                this.dmw.setText("TUESDAY");
                break;
            case 4:
                this.dmw.setText("WEDNESDAY");
                break;
            case 5:
                this.dmw.setText("THURSDAY");
                break;
            case 6:
                this.dmw.setText("FRIDAY");
                break;
            case 7:
                this.dmw.setText("SATURDAY");
                break;
        }
        switch (i2) {
            case 0:
                this.txtmonth.setText("JANUARY");
                break;
            case 1:
                this.txtmonth.setText("FEBRUARY");
                break;
            case 2:
                this.txtmonth.setText("MARCH");
                break;
            case 3:
                this.txtmonth.setText("APRIL");
                break;
            case 4:
                this.txtmonth.setText("MAY");
                break;
            case 5:
                this.txtmonth.setText("JUNE");
                break;
            case 6:
                this.txtmonth.setText("JULY");
                break;
            case 7:
                this.txtmonth.setText("AUGUST");
                break;
            case 8:
                this.txtmonth.setText("SEPTEMBER");
                break;
            case 9:
                this.txtmonth.setText("OCTOBER");
                break;
            case 10:
                this.txtmonth.setText("NOVEMBER");
                break;
            case 11:
                this.txtmonth.setText("DECEMBER");
                break;
        }
        if (!getActivity().getPackageManager().hasSystemFeature("android.hardware.sensor.compass")) {
            Log.d("COMPASS_SENSOR", "Device has no compass");
          //  Toast.makeText(getContext(), "Couldn't support your device, You have no Compass Sensors", 1).show();
        }
        this.mSensorManager = (SensorManager) getActivity().getSystemService("sensor");
        this.mSensorManager.registerListener(this, this.mSensorManager.getDefaultSensor(3), 0);
        return v;

    }

    private String getDegToGeo(float f) {
        return (f < 23.0f || f > 337.0f) ? "N" : (f <= 22.0f || f >= 68.0f) ? (f <= 67.0f || f >= 113.0f) ? (f <= 112.0f || f >= 158.0f) ? (f <= 157.0f || f >= 203.0f) ? (f <= 202.0f || f >= 248.0f) ? (f <= 247.0f || f >= 293.0f) ? (f <= 292.0f || f >= 338.0f) ? "" : "NW" : ExifInterface.LONGITUDE_WEST : "SW" : ExifInterface.LATITUDE_SOUTH : "SE" : ExifInterface.LONGITUDE_EAST : "NE";
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

            float f = sensorEvent.values[0];
            float round = (float) Math.round(sensorEvent.values[0]);
            TextView textView = this.heading;
            StringBuilder sb = new StringBuilder();
            sb.append(Float.toString(round));
            sb.append("° ");
            sb.append(getDegToGeo(f));
            textView.setText(sb.toString());
            float f2 = -f;
            RotateAnimation rotateAnimation = new RotateAnimation(this.currentDegree, f2, 1, 0.5f, 1, 0.5f);
            rotateAnimation.setInterpolator(new LinearInterpolator());
            rotateAnimation.setFillAfter(true);
            rotateAnimation.setDuration(210);
            this.imageView.startAnimation(rotateAnimation);
            this.imageView.animate();
            this.currentDegree = f2;

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public void onResume() {
        super.onResume();
        this.mSensorManager.registerListener(this, this.mSensorManager.getDefaultSensor(3), 0);
    }

    public void onPause() {
        super.onPause();
        this.mSensorManager.unregisterListener(this);
    }


}
