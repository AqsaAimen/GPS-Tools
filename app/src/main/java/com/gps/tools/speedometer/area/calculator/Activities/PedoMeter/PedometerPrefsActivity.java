package com.gps.tools.speedometer.area.calculator.Activities.PedoMeter;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.gps.tools.speedometer.area.calculator.R;

public class PedometerPrefsActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener {
    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        addPreferencesFromResource(R.xml.pedometer_prefs);
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String str) {
    }
}
