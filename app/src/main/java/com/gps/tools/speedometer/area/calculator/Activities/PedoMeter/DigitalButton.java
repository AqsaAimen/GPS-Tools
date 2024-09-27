package com.gps.tools.speedometer.area.calculator.Activities.PedoMeter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

@SuppressLint("AppCompatCustomView")
public class DigitalButton extends Button {
    public DigitalButton(Context context) {
        super(context);
        m18881a(null);
    }

    public DigitalButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        m18881a(attributeSet);
    }

    public DigitalButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        m18881a(attributeSet);
    }

    /* renamed from: a */
    private void m18881a(AttributeSet attributeSet) {
        if (attributeSet != null) {
            setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/digital-7.ttf"));
        }
    }
}
