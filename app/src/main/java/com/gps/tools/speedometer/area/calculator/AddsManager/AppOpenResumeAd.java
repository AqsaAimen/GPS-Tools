package com.gps.tools.speedometer.area.calculator.AddsManager;

import static com.gps.tools.speedometer.area.calculator.AddsManager.AdmobInterstitialAd.admob_app_resume_add;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;

import java.util.Date;

public class AppOpenResumeAd implements Application.ActivityLifecycleCallbacks {

    private Application application;
    private boolean isAdShow = true;
    private Activity currentActivity;
    public static AppOpenAd appOpenAd;

    private boolean isLoadingAd = false;
    private boolean isShowingAd = false;
    private long loadTime = 0;
    private  TinyDB db;

    public AppOpenResumeAd(Application application) {
        this.application = application;
        application.registerActivityLifecycleCallbacks(this);
        ProcessLifecycleOwner.get().getLifecycle().addObserver(new DefaultLifecycleObserver() {
            @Override
            public void onStart(LifecycleOwner owner) {
                DefaultLifecycleObserver.super.onStart(owner);
                Log.e("-->", "ON_START");
                if (checkAdShow(application)) {
                    if (!checkInterAdShow(application)) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                showAdIfAvailable();
                            }
                        }, 400);
                    }
                    Log.e("-->", "showAdIfAvailable");
                } else {
                    Log.e("-->", "else");
                }
            }
        });
    }

    public interface OnShowAdCompleteListener {
        void onShowAdComplete();
    }

    private void loadAd() {
        if (isLoadingAd || isAdAvailable()) {
            return;
        }

        isLoadingAd = true;
        AdRequest request = new AdRequest.Builder().build();

        try {
            AppOpenAd.load(application, admob_app_resume_add, request, AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT,
                    new AppOpenAd.AppOpenAdLoadCallback() {
                        @Override
                        public void onAdLoaded(AppOpenAd ad) {
                            appOpenAd = ad;
                            isLoadingAd = false;
                            loadTime = new Date().getTime();
                            Log.e("jcbjsbncnsc", "OpenAdLoaded resume : ");
                        }

                        @Override
                        public void onAdFailedToLoad(LoadAdError loadAdError) {
                            isLoadingAd = false;
                            Log.e("jcbjsbncnsc", "OpenAdFailed resume : " + loadAdError.getCode());
                        }
                    });
        } catch (Exception e) {
            // Handle the exception
        }
    }

    private boolean wasLoadTimeLessThanNHoursAgo(long numHours) {
        long dateDifference = new Date().getTime() - loadTime;
        long numMilliSecondsPerHour = 3600000;
        return dateDifference < numMilliSecondsPerHour * numHours;
    }

    private boolean isAdAvailable() {
        return appOpenAd != null && wasLoadTimeLessThanNHoursAgo(4);
    }

    public void showAdIfAvailable() {
        showAdIfAvailable(new OnShowAdCompleteListener() {
            @Override
            public void onShowAdComplete() {
                // Ad shown callback
            }
        });
    }

    private void showAdIfAvailable(final OnShowAdCompleteListener onShowAdCompleteListener) {
        if (isShowingAd) {
            return;
        }

        if (!isAdAvailable()) {
            onShowAdCompleteListener.onShowAdComplete();
            loadAd();
            return;
        }

        appOpenAd.setFullScreenContentCallback(new FullScreenContentCallback() {
            @Override
            public void onAdDismissedFullScreenContent() {
                isShowingAd = false;
                onShowAdCompleteListener.onShowAdComplete();
            }

            @Override
            public void onAdFailedToShowFullScreenContent(AdError adError) {
                appOpenAd = null;
                isShowingAd = false;
                Log.e("-->", adError.getMessage());
                onShowAdCompleteListener.onShowAdComplete();
                loadAd();
            }

            @Override
            public void onAdShowedFullScreenContent() {
                appOpenAd = null;
                loadAd();
            }
        });

        isShowingAd = true;
        if (currentActivity != null) {
            appOpenAd.show(currentActivity);
        }
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
    }

    @Override
    public void onActivityStarted(Activity activity) {
        if (!isShowingAd) {
            currentActivity = activity;
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {
    }

    @Override
    public void onActivityPaused(Activity activity) {
    }

    @Override
    public void onActivityStopped(Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
    }

    public static void isAppOpenAdShow(Context context, boolean showOpenAd) {
        TinyDB tinyDB = new TinyDB(context);
        tinyDB.putBoolean("showOpenAd", showOpenAd);
    }

    private boolean checkAdShow(Context context) {
        TinyDB tinyDB = new TinyDB(context);
        return tinyDB.getBoolean("showOpenAd");
    }

    private void isInterAdShow(Context context, boolean isInterAdShow) {
        TinyDB tinyDB = new TinyDB(context);
        tinyDB.putBoolean("isInterAdShow", isInterAdShow);
    }

    private boolean checkInterAdShow(Context context) {
        TinyDB tinyDB = new TinyDB(context);
        return tinyDB.getBoolean("isInterAdShow");
    }
}
