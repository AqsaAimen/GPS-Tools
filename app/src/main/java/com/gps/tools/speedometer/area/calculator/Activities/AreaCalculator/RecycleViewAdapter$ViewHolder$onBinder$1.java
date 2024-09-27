package com.gps.tools.speedometer.area.calculator.Activities.AreaCalculator;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;


final class RecycleViewAdapter$ViewHolder$onBinder$1 implements OnClickListener {
    final RecycleViewAdapter.newInterface $newInterface22;
    final int $position;

    RecycleViewAdapter$ViewHolder$onBinder$1(RecycleViewAdapter.newInterface newinterface, int i) {
        this.$newInterface22 = newinterface;
        this.$position = i;
    }

    public final void onClick(View view) {
        if (this.$newInterface22 != null) {
            this.$newInterface22.button1(this.$position);
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("nullll");
        sb.append(this.$position);
        Log.e("loggggg", sb.toString());
    }
}
