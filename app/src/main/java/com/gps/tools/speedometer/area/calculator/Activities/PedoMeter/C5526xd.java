package com.gps.tools.speedometer.area.calculator.Activities.PedoMeter;

import android.view.View;
import android.view.View.OnClickListener;


class C5526xd implements OnClickListener {


    final PedoMeterActivity f18247a;

    C5526xd(PedoMeterActivity pedometerMainActivity) {
        this.f18247a = pedometerMainActivity;
    }

    public void onClick(View view) {
        PedoMeterActivity pedometerMainActivity = this.f18247a;
        if (pedometerMainActivity.f17304l) {
            pedometerMainActivity.m18982e();
        } else {
            pedometerMainActivity.m18981d();
        }
    }
}
