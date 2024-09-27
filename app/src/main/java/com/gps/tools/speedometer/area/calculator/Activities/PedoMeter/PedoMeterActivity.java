package com.gps.tools.speedometer.area.calculator.Activities.PedoMeter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.gps.tools.speedometer.area.calculator.Activities.MainActivity;
import com.gps.tools.speedometer.area.calculator.Activities.Mirror.MirrorActivity;
import com.gps.tools.speedometer.area.calculator.AddsManager.AdCallBack;
import com.gps.tools.speedometer.area.calculator.AddsManager.AdmobInterstitialAd;
import com.gps.tools.speedometer.area.calculator.AddsManager.ShowInterstitials;
import com.gps.tools.speedometer.area.calculator.R;

import java.text.DecimalFormat;

public class PedoMeterActivity extends Activity implements SensorEventListener {

private Boolean isloaded;
    private static double f17279a = 1.02784823d;
    private static double f17280b = 0.708d;
    private int f17281A = 80;
    private boolean f17282B = false;
    private boolean f17283C = false;
    int f17284D = 0;
    private long f17285E = 0;
    private long[] f17286F = {-1, -1, -1, -1};
    private int f17287G = 0;
    private long f17288H = 0;
    private float f17289I = 0.0f;
    private float f17290J = 0.0f;
    private float f17291K = 0.0f;
    DecimalFormat f17292L = new DecimalFormat("#0.00");
    DecimalFormat f17293M = new DecimalFormat("#0.0");
    private TextView f17295c;
    FrameLayout FrameLayoutForMedium;
    private TextView f17296d;

    private TextView f17297e;
    private TextView f17298f;
    private TextView f17299g;
    private TextView f17300h;
    private TextView f17301i;
    AdView ADVIEWPEDOMETER;
    Button f17302j;
    Button f17303k;
    boolean f17304l = false;
    private SensorManager sensorManager;
    private Sensor sensor;
    private float f17307o = 10.0f;
    private float[] f17308p = new float[6];
    private float[] f17309q = new float[2];
    private float f17310r;
    private float[] f17311s = new float[6];
    private float[][] f17312t = {new float[6], new float[6]};
    private float[] f17313u = new float[6];
    private int f17314v = -1;
    private int f17315w = 0;
    private boolean aBoolean = false;
    private SharedPreferences sharedPreferences;
    private int anInt = 70;

    ImageView back_button;

    InterstitialAd admob;
    com.facebook.ads.InterstitialAd fb;
    
    com.facebook.ads.AdView adViewFb;
    AdView mAdViewMob;
    AdmobInterstitialAd interstitialAd;

    private void m18975a() {
        DecimalFormat decimalFormat;
        TextView textView;
        float f;
        String str = "M";
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        try {
            this.f17283C = !this.sharedPreferences.getString("units", str).equals(str);
            this.f17307o = Float.parseFloat(this.sharedPreferences.getString("sensitivity", "10"));
            this.f17281A = Integer.parseInt(this.sharedPreferences.getString("step_length", "80"));
            this.anInt = Integer.parseInt(this.sharedPreferences.getString("body_weight", "70"));
            this.f17282B = this.sharedPreferences.getString("exercise_type", "walking").equals("running");
            this.f17315w = this.sharedPreferences.getInt("stepCount", 0);
            this.f17291K = this.sharedPreferences.getFloat("calories", 0.0f);
            this.f17290J = this.sharedPreferences.getFloat("distance", 0.0f);
            this.f17288H = this.sharedPreferences.getLong("pace", 0);
            this.f17289I = this.sharedPreferences.getFloat("speed", 0.0f);
        } catch (Exception unused) {
        }
        float f2 = ((float) 480) * 0.5f;
        this.f17310r = f2;
        float[] fArr = this.f17309q;
        fArr[0] = -(0.05098581f * f2);
        fArr[1] = -(f2 * 0.016666668f);
        this.f17295c.setText(Integer.toString(this.f17315w));
        this.f17299g.setText(Integer.toString((int) this.f17291K));
        long j = this.f17288H;
        if (j > 0) {
            this.f17296d.setText(Integer.toString((int) j));
        }
        if (this.f17283C) {
            this.f17300h.setText(str);
            this.f17301i.setText("MPH");
            this.f17297e.setText(this.f17292L.format((double) (this.f17290J / 1.61f)));
            float f3 = this.f17289I;
            if (f3 > 0.0f) {
                textView = this.f17298f;
                decimalFormat = this.f17293M;
                f = f3 / 1.61f;
            } else {
                return;
            }
        } else {
            this.f17300h.setText("miles");
            this.f17301i.setText("miles/h");
            this.f17297e.setText(this.f17292L.format((double) this.f17290J));
            f = this.f17289I;
            if (f > 0.0f) {
                textView = this.f17298f;
                decimalFormat = this.f17293M;
            } else {
                return;
            }
        }
        textView.setText(decimalFormat.format((double) f));
    }

    private void m18977b() {
        DecimalFormat decimalFormat;
        TextView textView;
        float f;
        this.f17315w++;
        double d = (double) this.f17291K;
        double d2 = (double) this.anInt;
        double d3 = this.f17282B ? f17279a : f17280b;
        Double.isNaN(d2);
        double d4 = d2 * d3;
        int i = this.f17281A;
        double d5 = (double) i;
        Double.isNaN(d5);
        double d6 = (d4 * d5) / 100000.0d;
        Double.isNaN(d);
        this.f17291K = (float) (d + d6);
        float f2 = this.f17290J;
        double d7 = (double) i;
        Double.isNaN(d7);
        this.f17290J = f2 + ((float) (d7 / 100000.0d));
        long currentTimeMillis = System.currentTimeMillis();
        this.f17284D++;
        long j = this.f17285E;
        if (j > 0) {
            long j2 = currentTimeMillis - j;
            long[] jArr = this.f17286F;
            int i2 = this.f17287G;
            jArr[i2] = j2;
            this.f17287G = (i2 + 1) % jArr.length;
            boolean z = false;
            long j3 = 0;
            int i3 = 0;
            while (true) {
                long[] jArr2 = this.f17286F;
                if (i3 >= jArr2.length) {
                    z = true;
                    break;
                } else if (jArr2[i3] < 0) {
                    break;
                } else {
                    j3 += jArr2[i3];
                    i3++;
                }
            }
            if (!z || j3 < 0) {
                this.f17288H = -1;
            } else {
                this.f17288H = (long) Math.round(60000.0f / ((float) (j3 / ((long) this.f17286F.length))));
                this.f17296d.setText(Long.toString(this.f17288H));
            }
        }
        this.f17285E = currentTimeMillis;
        this.f17289I = (((float) (this.f17288H * ((long) this.f17281A))) / 100000.0f) * 60.0f;
        this.f17295c.setText(Integer.toString(this.f17315w));
        this.f17299g.setText(Integer.toString((int) this.f17291K));
        long j4 = this.f17288H;
        if (j4 > 0) {
            this.f17296d.setText(Integer.toString((int) j4));
        }
        if (this.f17283C) {
            this.f17297e.setText(this.f17292L.format((double) (this.f17290J / 1.61f)));
            float f3 = this.f17289I;
            if (f3 > 0.0f) {
                textView = this.f17298f;
                decimalFormat = this.f17293M;
                f = f3 / 1.61f;
            } else {
                return;
            }
        } else {
            this.f17297e.setText(this.f17292L.format((double) this.f17290J));
            f = this.f17289I;
            if (f > 0.0f) {
                textView = this.f17298f;
                decimalFormat = this.f17293M;
            } else {
                return;
            }
        }
        textView.setText(decimalFormat.format((double) f));
    }

    public void m18979c() {
        this.f17315w = 0;
        this.f17291K = 0.0f;
        this.f17290J = 0.0f;
        this.f17289I = 0.0f;
        this.f17288H = 0;
        String str = "0";
        this.f17295c.setText(str);
        this.f17296d.setText(str);
        this.f17297e.setText(this.f17292L.format(0));
        this.f17298f.setText(this.f17293M.format(0));
        this.f17299g.setText(str);
    }


    public void m18981d() {
        this.f17302j.setText(R.string.stop);
        this.f17302j.setBackgroundColor(getResources().getColor(R.color.RED));
        this.f17304l = true;
        this.sensorManager.registerListener(this, this.sensor, 2);
    }


    public void m18982e() {
        this.f17302j.setText(R.string.start);
        this.f17302j.setBackgroundColor(getResources().getColor(R.color.GREEN));
        this.f17304l = false;
        this.sensorManager.unregisterListener(this);
    }

    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    @SuppressLint("WrongConstant")
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_pedo_meter);
        
        
        
        FrameLayoutForMedium = findViewById(R.id.ad_view_pedometer);
        interstitialAd.gpsToolsAdMobMeduimBanner(FrameLayoutForMedium, this);


        back_button = (ImageView) findViewById(R.id.drawer_icon_pedometer);

        this.f17295c = (TextView) findViewById(R.id.step_value);
        this.f17296d = (TextView) findViewById(R.id.pace_value);
        this.f17297e = (TextView) findViewById(R.id.distance_value);
        this.f17298f = (TextView) findViewById(R.id.speed_value);
        this.f17299g = (TextView) findViewById(R.id.calories_value);
        this.f17300h = (TextView) findViewById(R.id.distance_units);
        this.f17301i = (TextView) findViewById(R.id.speed_units);
        this.f17302j = (Button) findViewById(R.id.buttonStartStop);
        this.f17302j.getBackground().setAlpha(getResources().getInteger(R.integer.buttonAlpha));
        this.f17302j.setBackgroundColor(getResources().getColor(R.color.GREEN));
        this.f17302j.setOnClickListener(new C5526xd(this));
        this.f17303k = (Button) findViewById(R.id.buttonReset);
        this.f17303k.getBackground().setAlpha(getResources().getInteger(R.integer.buttonAlpha));
        this.f17303k.setOnClickListener(new C5533yd(this));
        this.sensorManager = (SensorManager) getSystemService("sensor");
        this.sensor = this.sensorManager.getDefaultSensor(1);

        back_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    public void onDestroy() {
        super.onDestroy();
    }

    public void onPause() {
        boolean z;
        super.onPause();
        if (this.f17304l) {
            m18982e();
            z = true;
        } else {
            z = false;
        }
        this.aBoolean = z;
        Editor edit = this.sharedPreferences.edit();
        edit.putInt("stepCount", this.f17315w);
        edit.putFloat("calories", this.f17291K);
        edit.putFloat("distance", this.f17290J);
        edit.putLong("pace", this.f17288H);
        edit.putFloat("speed", this.f17289I);
        edit.commit();
    }

    public void onResume() {
        super.onResume();
        m18975a();
        if (this.aBoolean) {
            m18981d();
        }

    }

    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor sensor = sensorEvent.sensor;
        synchronized (this) {
            if (sensor.getType() != 3) {
                boolean z = true;
                char c = sensor.getType() == 1 ? (char) 1 : 0;
                if (c == 1) {
                    float f = 0.0f;
                    for (int i = 0; i < 3; i++) {
                        f += this.f17310r + (sensorEvent.values[i] * this.f17309q[c]);
                    }
                    float f2 = f / 3.0f;
                    int i2 = f2 > this.f17308p[0] ? 1 : f2 < this.f17308p[0] ? -1 : 0;
                    float f3 = (float) i2;
                    if (f3 == (-this.f17311s[0])) {
                        int i3 = f3 > 0.0f ? 0 : 1;
                        this.f17312t[i3][0] = this.f17308p[0];
                        int i4 = 1 - i3;
                        float abs = Math.abs(this.f17312t[i3][0] - this.f17312t[i4][0]);
                        if (abs > this.f17307o) {
                            boolean z2 = abs > (this.f17313u[0] * 2.0f) / 3.0f;
                            boolean z3 = this.f17313u[0] > abs / 3.0f;
                            if (this.f17314v == i4) {
                                z = false;
                            }
                            if (!z2 || !z3 || !z) {
                                this.f17314v = -1;
                            } else {
                                m18977b();
                                this.f17314v = i3;
                            }
                        }
                        this.f17313u[0] = abs;
                    }
                    this.f17311s[0] = f3;
                    this.f17308p[0] = f2;
                }
            }
        }
    }

    public void onBackPressed() {
        ShowInterstitials.showBackPressInterstitialAd(PedoMeterActivity.this, AdmobInterstitialAd.mInterstitialAd);
    }

}
