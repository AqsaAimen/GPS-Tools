package com.gps.tools.speedometer.area.calculator.Activities.Compass;

import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

public class ShowCamera extends SurfaceView implements SurfaceHolder.Callback {

    private Camera cam;
    private SurfaceHolder surfaceHolder;

    public ShowCamera(Context context, Camera camera) {
        super(context);
        this.cam = camera;
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

        try{

            cam.setPreviewDisplay(surfaceHolder);
            cam.startPreview();
            cam.setDisplayOrientation(90);

        }catch (IOException e){

            e.printStackTrace();


        }


    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

//        cam.stopPreview();
//        cam.release();
    //   cam = null;

    }
}
