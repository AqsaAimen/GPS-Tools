package com.gps.tools.speedometer.area.calculator.Activities.Compass;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.exifinterface.media.ExifInterface;
import androidx.fragment.app.Fragment;

import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.gps.tools.speedometer.area.calculator.R;
import com.gps.tools.speedometer.area.calculator.permission.PermissionUtils;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build.VERSION;
import android.util.Log;
import android.view.animation.RotateAnimation;
import android.widget.AnalogClock;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class MilitaryFragment extends Fragment implements SensorEventListener, LocationListener {
    private AnalogClock clock;
    ImageView compassdial;
    String current = "";
    private float currentDegree = 0.0f;
    private float currentDegree1 = 0.0f;
    TextView dmw;
    /* access modifiers changed from: private */
    public ImageView hand_second;
    double latc = 0.0d;
    double latt = 0.0d;
    TextView loc;
    double longc = 0.0d;
    double longt = 0.0d;
    private SensorManager mSensorManager;
    ImageView navigator;
    String target = "";
    ImageView targetneedle;
    TextView tvcurrent;
    TextView tvcurrentn;
    TextView tvtargetn;
    TextView txtdy;
    TextView txtmonth;
    TextView txtwy;
    TextView txtyear;
    TextView utc;

    InterstitialAd admob;
    com.facebook.ads.InterstitialAd fb;
    
    com.facebook.ads.AdView adViewFb;
    AdView mAdViewMob;
    View inflate;

    public MilitaryFragment() {
        // Required empty public constructor
    }

    class CountDownRunner implements Runnable {
        CountDownRunner() {

        }
        public void run() {


           // throw new UnsupportedOperationException("Method not decompiled: com.example.gpstools.Compass.MilitaryFragment.CountDownRunner.run():void");
        }
    }

    private String getDegToGeo(float f) {
        return (f < 23.0f || f > 337.0f) ? "N" : (f <= 22.0f || f >= 68.0f) ? (f <= 67.0f || f >= 113.0f) ? (f <= 112.0f || f >= 158.0f) ? (f <= 157.0f || f >= 203.0f) ? (f <= 202.0f || f >= 248.0f) ? (f <= 247.0f || f >= 293.0f) ? (f <= 292.0f || f >= 338.0f) ? "" : "NW" : ExifInterface.LONGITUDE_WEST : "SW" : ExifInterface.LATITUDE_SOUTH : "SE" : ExifInterface.LONGITUDE_EAST : "NE";
    }

    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    public void onLocationChanged(Location location) {
    }

    @SuppressLint("WrongConstant")
    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        inflate = layoutInflater.inflate(R.layout.fragment_military, viewGroup, false);


        if (!(ActivityCompat.checkSelfPermission(getActivity(), PermissionUtils.Manifest_ACCESS_FINE_LOCATION) == 0 || ActivityCompat.checkSelfPermission(getActivity(), PermissionUtils.Manifest_ACCESS_COARSE_LOCATION) == 0 || VERSION.SDK_INT < 23)) {
            requestPermissions(new String[]{PermissionUtils.Manifest_ACCESS_FINE_LOCATION, PermissionUtils.Manifest_ACCESS_COARSE_LOCATION}, 1);
        }
        try {
            new Thread(new CountDownRunner()).start();
        } catch (Exception unused) {
        }
        this.clock = (AnalogClock) inflate.findViewById(R.id.analogClock);
        this.loc = (TextView) inflate.findViewById(R.id.loc);
        this.dmw = (TextView) inflate.findViewById(R.id.dmw);
        this.txtmonth = (TextView) inflate.findViewById(R.id.txtmonth);
        this.txtyear = (TextView) inflate.findViewById(R.id.txtyear);
        this.hand_second = (ImageView) inflate.findViewById(R.id.hand_second);
        this.compassdial = (ImageView) inflate.findViewById(R.id.main_image_dial);
        this.targetneedle = (ImageView) inflate.findViewById(R.id.targetneedle);
        this.navigator = (ImageView) inflate.findViewById(R.id.navigator);
        this.tvcurrent = (TextView) inflate.findViewById(R.id.tvcurrent);
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
           // Toast.makeText(getActivity(), "Couldn't support your device, You have no Compass Sensors", 1).show();
        }
        this.mSensorManager = (SensorManager) getActivity().getSystemService("sensor");
        return inflate;
    }

   /* public void doRotate() {
        try {
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    try {
                        Date date = new Date();
                        int hours = date.getHours();
                        int minutes = date.getMinutes();
                        int seconds = date.getSeconds();
                        StringBuilder sb = new StringBuilder();
                        sb.append(hours);
                        sb.append(":");
                        sb.append(minutes);
                        sb.append("::");
                        sb.append(seconds);
                        sb.toString();
                        RotateAnimation rotateAnimation = new RotateAnimation((float) ((seconds - 1) * 6), (float) (seconds * 6), 1, 0.5f, 1, 0.5f);
                        rotateAnimation.setInterpolator(new LinearInterpolator());
                        rotateAnimation.setDuration(1000);
                        rotateAnimation.setFillAfter(true);
                        MilitaryFragment.this.hand_second.startAnimation(rotateAnimation);
                    } catch (Exception unused) {
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    public void onResume() {
        super.onResume();
        if (this.mSensorManager != null) {
            this.mSensorManager.registerListener(this, this.mSensorManager.getDefaultSensor(3), 1);
        }
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null) {
            this.current = extras.getString("current");
            this.target = extras.getString("target");
            this.latc = extras.getDouble("latc");
            this.longc = extras.getDouble("longc");
            this.latt = extras.getDouble("latt");
            this.longt = extras.getDouble("longt");
            this.tvtargetn.setText(this.target);
            this.tvcurrentn.setText(this.current);
        }
    }

    public void onPause() {
        super.onPause();
        if (this.mSensorManager != null) {
            this.mSensorManager.unregisterListener(this);
        }
    }

    public void onSensorChanged(SensorEvent sensorEvent) {
        SensorEvent sensorEvent2 = sensorEvent;
        try {
            float round = (float) Math.round(sensorEvent2.values[0]);
            Math.round(sensorEvent2.values[0]);
            this.currentDegree1 = (float) getDirection(this.latc, this.longc, this.latt, this.longt);
            TextView textView = this.tvcurrent;
            StringBuilder sb = new StringBuilder();
            sb.append(" ");
            sb.append(Float.toString(round));
            sb.append("°");
            sb.append(getDegToGeo(round));
            textView.setText(sb.toString());
            RotateAnimation rotateAnimation = new RotateAnimation(this.currentDegree, round, 1, 0.5f, 1, 0.5f);
            RotateAnimation rotateAnimation2 = new RotateAnimation(this.currentDegree1, this.currentDegree1, 1, 0.5f, 1, 0.5f);
            rotateAnimation.setDuration(210);
            rotateAnimation.setFillAfter(true);
            rotateAnimation2.setDuration(210);
            rotateAnimation2.setFillAfter(true);
            this.targetneedle.startAnimation(rotateAnimation2);
            this.navigator.startAnimation(rotateAnimation);
            this.currentDegree = round;
            this.currentDegree1 = (float) getDirection(this.latc, this.longc, this.latt, this.longt);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onStatusChanged(String str, int i, Bundle bundle) {
        Log.d("Latitude", NotificationCompat.CATEGORY_STATUS);
    }

    public void onProviderEnabled(String str) {
        Log.d("Latitude", "enable");
    }

    public void onProviderDisabled(String str) {
        Log.d("Latitude", "disable");
    }

    private double getDirection(double d, double d2, double d3, double d4) {
        return (double) Math.round(Math.toDegrees(Math.atan2(Math.abs(d2 - d4), Math.log(Math.tan((d3 / 2.0d) + 0.7853981633974483d) / Math.tan((d / 2.0d) + 0.7853981633974483d)))));
    }


}

