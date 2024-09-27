package com.gps.tools.speedometer.area.calculator.Activities.Compass;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;

import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.gps.tools.speedometer.area.calculator.R;

import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class ARFragment extends Fragment implements SensorEventListener {


    private ImageView imageView;
    private float aFloat = 0.0f;
    private SensorManager sensorManager;
    TextView textView;
    FrameLayout frameLayout;
    Camera camera;
    private final String TAG = "CameraPreview";
    public ShowCamera showCamera;


    TextView dmw;
    private TextView heading;
    private ImageView imageView1;
    TextView loc;
    TextView txtmonth;
    TextView txtyear;

    InterstitialAd admob;
    com.facebook.ads.InterstitialAd fb;
    
    com.facebook.ads.AdView adViewFb;
    AdView mAdViewMob;
    View v;



    public ARFragment() {

    }

    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @SuppressLint("WrongConstant")
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_ar, container, false);



        camera= switch_on_camera();

        this.imageView = (ImageView) v.findViewById(R.id.imageViewCompass);
        this.frameLayout = (FrameLayout) v.findViewById(R.id.camera_preview);
        showCamera = new ShowCamera(getContext(),camera);
        frameLayout.addView(showCamera);
        this.textView = (TextView) v.findViewById(R.id.textViewHeading);

       // heading = (TextView) v.findViewById(R.id.heading);
       // imageView = (ImageView) v.findViewById(R.id.imageView);
        loc = (TextView) v.findViewById(R.id.loc_ar);
        dmw = (TextView) v.findViewById(R.id.dmw_ar);
        txtmonth = (TextView) v.findViewById(R.id.txtmonth_ar);
        txtyear = (TextView) v.findViewById(R.id.txtyear_ar);
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
            //Toast.makeText(getContext(), "Couldn't support your device, You have no Compass Sensors", 1).show();
        }
        sensorManager = (SensorManager) getActivity().getSystemService("sensor");
        sensorManager.registerListener(this, this.sensorManager.getDefaultSensor(3), 0);
        return v;

    }

    public Camera switch_on_camera() {


        try {
            camera = Camera.open();
            Parameters parameters = camera.getParameters();
           // obj.startPreview();

            if (getContext().getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {

                parameters.set("orientation", "portrait");
                camera.setDisplayOrientation(90);
                parameters.setRotation(90);
            } else {

                parameters.set("orientation", "landscape");
                camera.setDisplayOrientation(0);
                parameters.setRotation(0);

            }
        }catch(RuntimeException e) {
            Log.e(TAG, "Error" + e);
           // Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
        }

        return camera;

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        float round = (float) Math.round(sensorEvent.values[0]);
        TextView textView = this.textView;
        StringBuilder sb = new StringBuilder();
        sb.append(Integer.toString(Math.round(round)));
        sb.append(" °");
        textView.setText(sb.toString());
        float f = -round;
        RotateAnimation rotateAnimation = new RotateAnimation(this.aFloat, f, 1, 0.5f, 1, 0.5f);
        rotateAnimation.setDuration(210);
        rotateAnimation.setFillAfter(true);
        this.imageView.startAnimation(rotateAnimation);
        this.aFloat = f;
    }

    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
        if (camera != null) {
            camera.stopPreview();
            camera.setPreviewCallback(null);
            camera = null;
        }
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        try {
            if (sensorManager != null) {
                camera = Camera.open();
                camera.setPreviewCallback(null);
                showCamera = new ShowCamera(getContext(), camera);
                frameLayout.addView(showCamera);
                camera.startPreview();
                sensorManager.registerListener(this, sensorManager.getDefaultSensor(3), 1);
            }
        }catch (Exception e) {
            Log.d(TAG, "Error starting camera preview: " + e.getMessage());
        }
    }


    public void onDestroy() {
        super.onDestroy();
    }


}

