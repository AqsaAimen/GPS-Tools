package com.gps.tools.speedometer.area.calculator.Activities.SoundDetector;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;

import java.util.ArrayList;

public class GraphSingleLine extends View {
    private int xAxisValue = 0;
    private Paint xPaint;
    private ArrayList<Point> xPoints = new ArrayList<>();
    private int yAxisValue = 1;

    public GraphSingleLine(Context context) {
        super(context);
        setDrawingCacheEnabled(true);
        initPaint();
    }

    public GraphSingleLine(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        initPaint();
    }

    public GraphSingleLine(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initPaint();
    }

    private void initPaint() {
        this.xPaint = new Paint(1);
        this.xPaint.setColor(ViewCompat.MEASURED_STATE_MASK);
        this.xPaint.setStrokeWidth(7.0f);
        this.xPaint.setStyle(Style.STROKE);
    }

    public void draw(double i, int i2) {
        if (this.yAxisValue != 1) {
            this.xAxisValue += 8;
            this.xPoints.add(new Point(this.xAxisValue, (int) ((i * i2) + this.yAxisValue)));
            invalidate();
        }
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.yAxisValue = canvas.getHeight() / 2;
        if (this.xPoints.size() >= 2) {
            int i = 0;
            while (i < this.xPoints.size() - 1) {
                Point point = (Point) this.xPoints.get(i);
                i++;
                Point point2 = (Point) this.xPoints.get(i);
                canvas.drawLine((float) point.x, (float) point.y, (float) point2.x, (float) point2.y, this.xPaint);
            }
            if (this.xAxisValue > canvas.getWidth()) {
                this.xPoints.clear();
                this.xAxisValue = 1;
            }
        }
    }
}
