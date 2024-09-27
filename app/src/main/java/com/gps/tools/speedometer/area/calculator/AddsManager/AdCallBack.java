package com.gps.tools.speedometer.area.calculator.AddsManager;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.LoadAdError;

public interface AdCallBack {
    void onAdLoaded();
    void onFailedToLoadAd(@NonNull LoadAdError loadAdError);
}
