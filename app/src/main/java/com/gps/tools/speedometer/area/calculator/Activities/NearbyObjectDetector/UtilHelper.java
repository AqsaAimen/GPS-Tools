package com.gps.tools.speedometer.area.calculator.Activities.NearbyObjectDetector;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.view.animation.ScaleAnimation;

import org.jetbrains.annotations.NotNull;


public final class UtilHelper {
    public static final Companion Companion = new Companion(null);


    public static final class Companion {
        private Companion(Object o) {
        }


        public final void vibrate(@NotNull Activity activity) {
            //Intrinsics.checkParameterIsNotNull(activity, "activity");
          Object systemService = activity.getSystemService(Context.VIBRATOR_SERVICE);
            if (systemService != null) {
                Vibrator vibrator = (Vibrator) systemService;
                if (VERSION.SDK_INT >= 26) {
                    vibrator.vibrate(VibrationEffect.createOneShot(500, -1));
                } else {
                    vibrator.vibrate(500);
                }
            } else {
               // throw new TypeCastException("null cannot be cast to non-null type android.os.Vibrator");
            }
        }

        @SuppressLint("WrongConstant")
        public final void openDropdown(@NotNull View view) {
           // Intrinsics.checkParameterIsNotNull(view, "view");
            if (view.getVisibility() != 0) {
                ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 1.0f, 0.0f, 1.0f);
                scaleAnimation.setDuration(2000);
                view.startAnimation(scaleAnimation);
                view.setVisibility(0);
            }
        }

        @SuppressLint("WrongConstant")
        public final void closeDropdown(@NotNull View view) {
           // Intrinsics.checkParameterIsNotNull(view, "view");
            if (view.getVisibility() == 0) {
                ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 1.0f, 1.0f, 0.0f);
                scaleAnimation.setDuration(2000);
            //    scaleAnimation.setAnimationListener(new UtilHelper$Companion$closeDropdown$1(view));
                view.startAnimation(scaleAnimation);
            }
        }
    }
}
