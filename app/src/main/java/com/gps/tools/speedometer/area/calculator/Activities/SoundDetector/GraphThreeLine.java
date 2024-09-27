package com.gps.tools.speedometer.area.calculator.Activities.SoundDetector;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

import com.gps.tools.speedometer.area.calculator.R;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class GraphThreeLine extends View {
    private int canvasMoveLeft = -1;
    private int xAxisValue = 0;
    private Paint xPaint;
    private ArrayList<Point> xPoints = new ArrayList<>();
    private int yAxisValue = -1;
    private Paint yPaint;
    private ArrayList<Point> yPoints = new ArrayList<>();
    private Paint zPaint;
    private ArrayList<Point> zPoints = new ArrayList<>();

    public GraphThreeLine(Context context) {
        super(context);
        setDrawingCacheEnabled(true);
        initPaint();
    }

    public GraphThreeLine(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        initPaint();
    }

    public GraphThreeLine(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initPaint();
    }

    private void initPaint() {
        this.xPaint = new Paint(1);
        this.xPaint.setColor(getResources().getColor(R.color.X));
        this.xPaint.setStrokeWidth(8.0f);
        this.xPaint.setStyle(Style.STROKE);
        this.yPaint = new Paint(1);
        this.yPaint.setColor(getResources().getColor(R.color.Y));
        this.yPaint.setStrokeWidth(8.0f);
        this.yPaint.setStyle(Style.STROKE);
        this.zPaint = new Paint(1);
        this.zPaint.setColor(getResources().getColor(R.color.Z));
        this.zPaint.setStrokeWidth(8.0f);
        this.zPaint.setStyle(Style.STROKE);
    }

    public void draw(int i, int i2, int i3, int i4) {
        if (this.yAxisValue != -1) {
            this.xAxisValue += 3;
            this.xPoints.add(new Point(this.xAxisValue, (i * i4) + this.yAxisValue));
            this.yPoints.add(new Point(this.xAxisValue, (i2 * i4) + this.yAxisValue));
            this.zPoints.add(new Point(this.xAxisValue, (i3 * i4) + this.yAxisValue));
            invalidate();
        }
    }

    public void restart() {
        this.xPoints.clear();
        this.yPoints.clear();
        this.zPoints.clear();
        this.xAxisValue = 1;
        invalidate();
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.yAxisValue = canvas.getHeight() / 2;
        if (this.xPoints.size() >= 2) {
            int i = 0;
            while (i < this.xPoints.size() - 1) {
                Point point = (Point) this.xPoints.get(i);
                int i2 = i + 1;
                Point point2 = (Point) this.xPoints.get(i2);
                Point point3 = (Point) this.yPoints.get(i);
                Point point4 = (Point) this.yPoints.get(i2);
                Point point5 = (Point) this.zPoints.get(i);
                Point point6 = (Point) this.zPoints.get(i2);
                canvas.drawLine((float) point.x, (float) point.y, (float) point2.x, (float) point2.y, this.xPaint);
                Canvas canvas2 = canvas;
                canvas2.drawLine((float) point3.x, (float) point3.y, (float) point4.x, (float) point4.y, this.yPaint);
                canvas2.drawLine((float) point5.x, (float) point5.y, (float) point6.x, (float) point6.y, this.zPaint);
                i = i2;
            }
            if (this.xAxisValue > canvas.getWidth()) {
                this.xPoints.clear();
                this.yPoints.clear();
                this.zPoints.clear();
                this.xAxisValue = 1;
            }
        }
    }
}
