package com.gps.tools.speedometer.area.calculator.AddsManager;

import static com.gps.tools.speedometer.area.calculator.AddsManager.AdmobInterstitialAd.isFirstCLickEnabled;
import static com.gps.tools.speedometer.area.calculator.AddsManager.AdmobInterstitialAd.preloadInterstitialAd;
import static com.gps.tools.speedometer.area.calculator.AddsManager.AppOpenResumeAd.isAppOpenAdShow;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.interstitial.InterstitialAd;

public class ShowInterstitials {
    public static Integer divider = 0;

    public static void showAdAfterFirstClickEnabled(Activity activity, InterstitialAd interstitialAd, Intent intent) {
        TinyDB db = new TinyDB(activity);
        if (!db.isAppPurchased()) {
            Log.d("ladkfjaskljflkaslk", "showAdAfterFirstClickEnabled: " + isFirstCLickEnabled);
            Log.d("ladkfjaskljflkaslk", "showAdAfterFirstClickEnabled interstitialAd: " + interstitialAd);
            if (isFirstCLickEnabled) {
                if (interstitialAd != null) {
                    isAppOpenAdShow(activity, false);
                    Log.d("ladkfjaskljflkaslk", "will show: " + isFirstCLickEnabled);
                    interstitialAd.show(activity);
                    interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdClicked() {
                            super.onAdClicked();
                        }

                        @Override
                        public void onAdDismissedFullScreenContent() {
                            super.onAdDismissedFullScreenContent();
                            AdmobInterstitialAd.mInterstitialAd = null;
                            preloadInterstitialAd(activity);
                            activity.startActivity(intent);
//                            isAppOpenAdShow(activity, true);
                            Log.d("ladkfjaskljflkaslk", "onAdDismissedFullScreenContent ad dismissed : ");
                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                            super.onAdFailedToShowFullScreenContent(adError);
                            Log.d("ladkfjaskljflkaslk", "onAdFailedToShowFullScreenContent ad failed : ");
                            preloadInterstitialAd(activity);
                            activity.startActivity(intent);
                        }

                        @Override
                        public void onAdImpression() {
                            super.onAdImpression();
                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                            super.onAdShowedFullScreenContent();
                            isAppOpenAdShow(activity, false);
                        }
                    });
                } else {
                    Log.d("vhvhvhhvhvh", "npo avv: " + isFirstCLickEnabled);
                    preloadInterstitialAd(activity);
                    activity.startActivity(intent);
                }
            } else {
                Log.d("vhvhvv", "showAdAfterkjhjkhkjhkFirstClickEnabled: " + isFirstCLickEnabled);
                activity.startActivity(intent);
                isFirstCLickEnabled = true;
            }
        } else {
            activity.startActivity(intent);
        }

    }

    public static void showAdWithOnlyFirstAndThirdEnabled(Activity activity, InterstitialAd interstitialAd, Intent intent) {
        TinyDB db = new TinyDB(activity);
        if (!db.isAppPurchased()) {
            divider++;
            Log.d("AdDebugfff", "showAdWithOnlyFirstAndThirdEnabled: divider=" + divider);

            if (divider == 1 || divider == 3) {
                Log.d("AdDebugfff", "Condition true for divider: " + divider);

                if (interstitialAd != null) {
                    isAppOpenAdShow(activity, false);
                    Log.d("AdDebugfff", "InterstitialAd is loaded and will be shown: " + divider);
                    interstitialAd.show(activity);


                    interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdClicked() {
                            super.onAdClicked();
                            Log.d("AdDebugfff", "Ad clicked.");
                        }

                        @Override
                        public void onAdDismissedFullScreenContent() {
                            super.onAdDismissedFullScreenContent();
                            Log.d("AdDebugfff", "Ad dismissed.");
                            AdmobInterstitialAd.mInterstitialAd = null;
                            preloadInterstitialAd(activity);
                            activity.startActivity(intent);
                            //isAppOpenAdShow(activity, true);
                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                            super.onAdFailedToShowFullScreenContent(adError);
                            Log.d("AdDebugfff", "Ad failed to show: " + adError);
                            preloadInterstitialAd(activity);
                            activity.startActivity(intent);

                        }

                        @Override
                        public void onAdImpression() {
                            super.onAdImpression();
                            Log.d("AdDebugfff", "Ad impression logged.");
                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                            super.onAdShowedFullScreenContent();
                            Log.d("AdDebugfff", "Ad showed fullscreen content.");
                            isAppOpenAdShow(activity, false);
                        }
                    });

                } else {
                    Log.d("AdDebugfff", "InterstitialAd is null, preloading ad.");
                    preloadInterstitialAd(activity);
                    activity.startActivity(intent);
                }
            } else {
                Log.d("AdDebugfff", "Condition false for divider: " + divider);
                activity.startActivity(intent);
            }
        } else {
            Log.d("AdDebugfff", "App is purchased, not showing ads.");
            activity.startActivity(intent);
        }
    }

    public static void showBackPressInterstitialAd(Activity activity, InterstitialAd interstitialAd) {
        TinyDB db = new TinyDB(activity);
        if (!db.isAppPurchased()) {
            if (interstitialAd != null) {
                isAppOpenAdShow(activity, false);
                interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        super.onAdDismissedFullScreenContent();
                        AdmobInterstitialAd.mInterstitialAd = null;
                        Log.d("dahfsjdhjdas", "onAdDismissedFullScreenContent AdmobInterstitialAd.mInterstitialAd : " + AdmobInterstitialAd.mInterstitialAd);
                        preloadInterstitialAd(activity);
                        isAppOpenAdShow(activity, true);
                        activity.finish();
                    }
                    @Override
                    public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                        super.onAdFailedToShowFullScreenContent(adError);
                        Log.d("AdDebug", "onAdFailedToShowFullScreenContent: " + adError.getMessage());
                        AdmobInterstitialAd.mInterstitialAd = null;
                        preloadInterstitialAd(activity);
                        isAppOpenAdShow(activity, true);
                        activity.finish();
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        super.onAdShowedFullScreenContent();
                        Log.d("AdDebug", "onAdShowedFullScreenContent");
                        isAppOpenAdShow(activity, false);
                    }
                });
                interstitialAd.show(activity);
            } else {
                Log.d("AdDebug", "mInterstitialAd is null");
                activity.finish();
                preloadInterstitialAd(activity);
            }
        } else {
            activity.finish();
        }

    }

}
