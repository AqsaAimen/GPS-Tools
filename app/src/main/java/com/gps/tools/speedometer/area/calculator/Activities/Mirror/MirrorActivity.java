package com.gps.tools.speedometer.area.calculator.Activities.Mirror;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.ads.LoadAdError;
import com.gps.tools.speedometer.area.calculator.Activities.Converter.ConverterActivity;
import com.gps.tools.speedometer.area.calculator.Activities.MainActivity;
import com.gps.tools.speedometer.area.calculator.AddsManager.AdCallBack;
import com.gps.tools.speedometer.area.calculator.AddsManager.AdmobInterstitialAd;
import com.gps.tools.speedometer.area.calculator.AddsManager.ShowInterstitials;
import com.gps.tools.speedometer.area.calculator.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.PictureCallback;
import android.view.View.OnClickListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;


public class MirrorActivity extends Activity implements AdCallBack {


    FrameLayout frameLayout;
    ToggleButton f17228b;
    ImageView btnCapture, btnCapture1;
    TextView tvCaptureOn, tvCaptureOf;
    SeekBar seekBar;
    TextView zoomtv;
    int f17233g = 0;
    boolean f17234h = false;
    LayoutParams layoutParams;
    LayoutParams params;
    private final String TAG = "Mirror";
    public Context myContext = this;
    AlertDialog alert;
    Camera mCamera;
    private Boolean isloaded;
    private static final int REQUEST_CAMERA_PERMISSION = 1;
    boolean cameraPermission;
    boolean result;

    private CameraPreview mPreview;
    private PictureCallback mPicture;
    private boolean cameraFront = false;

    ImageView back_button;
    AdmobInterstitialAd interstitialAd;
    private SurfaceHolder mSurfaceHolder;
    private SurfaceView mSurfaceView;
    private static final int PERMISSION_REQUEST_CODE = 200;



    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_mirror);
        interstitialAd = new AdmobInterstitialAd();


        //checkCameraPermission();


        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        btnCapture = (ImageView) findViewById(R.id.capture);
        btnCapture1 = (ImageView) findViewById(R.id.capture1);

        tvCaptureOn = (TextView) findViewById(R.id.tv_capture);
        tvCaptureOf = (TextView) findViewById(R.id.tv_capture1);

        seekBar = (SeekBar) findViewById(R.id.seekBarZoom);
        zoomtv = (TextView) findViewById(R.id.textViewZoom);


        back_button = (ImageView) findViewById(R.id.drawer_icon_mirror);

        //setCameraDisplayOrientation(mCamera);


        frameLayout = (FrameLayout) findViewById(R.id.camera_preview_);
           setupSurfaceView();
        myContext = this;
         checkRuntimeLocationPermission();
        btnCapture.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(myContext, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {


                    mCamera.stopPreview();
                    btnCapture.setVisibility(View.GONE);
                    btnCapture1.setVisibility(View.VISIBLE);
                    tvCaptureOf.setVisibility(View.VISIBLE);
                    tvCaptureOn.setVisibility(View.GONE);

                    seekBar.setVisibility(View.GONE);
                    zoomtv.setVisibility(View.GONE);


                }

            }
        });

        btnCapture1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                if (ContextCompat.checkSelfPermission(myContext, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {


                    mCamera.startPreview();
                    btnCapture1.setVisibility(View.GONE);
                    btnCapture.setVisibility(View.VISIBLE);
                    tvCaptureOf.setVisibility(View.GONE);
                    tvCaptureOn.setVisibility(View.VISIBLE);

                    seekBar.setVisibility(View.VISIBLE);
                    zoomtv.setVisibility(View.VISIBLE);

                }

            }
        });


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                if (ContextCompat.checkSelfPermission(myContext, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {

                    if (mCamera.getParameters().isZoomSupported()) {

                        try {
                            Parameters params = mCamera.getParameters();
                            if (Build.VERSION.SDK_INT >= 19) {

                                params.setZoom(i);
                                mCamera.setParameters(params);
                            }
                            mCamera.setParameters(params);
                        } catch (Exception unused) {

                        }

                    } else {

                        Parameters params = mCamera.getParameters();
                        params.setZoom(i);
                        mCamera.setParameters(params);


                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {


            }
        });


        back_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    private void setupSurfaceView() {
        mSurfaceView = new SurfaceView(this);
        frameLayout.addView(mSurfaceView);
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(surfaceCallback);
    }
    private final SurfaceHolder.Callback surfaceCallback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            startCameraPreview();
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            if (mSurfaceHolder.getSurface() == null) {
                return;
            }
            mCamera.stopPreview();
            startCameraPreview();
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            releaseCamera();
        }
    };



private int findFrontFacingCamera() {
        int cameraId = -1;
        // Search for the front facing camera
        int numberOfCameras = Camera.getNumberOfCameras();

        for (int i = 0; i < numberOfCameras; i++) {
            CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == CameraInfo.CAMERA_FACING_FRONT) {
                cameraId = i;
                cameraFront = true;
                break;
            }

        }

        return cameraId;
    }


   /* public void onResume() {
        super.onResume();
        if (!hasCamera(myContext)) {
            Toast toast = Toast.makeText(myContext, "Sorry, your phone does not have a camera!", Toast.LENGTH_LONG);
            toast.show();
            finish();
        }
        checkRuntimeLocationPermission();
        openCamera();
       startCameraPreview();
       setupSurfaceView();
        this.layoutParams = getWindow().getAttributes();
        this.params = this.layoutParams;
        this.params.screenBrightness = 1.0f;
        mPreview = new CameraPreview(this,mCamera);
        frameLayout.addView(mPreview);
        mPicture = getPictureCallback();
        mPreview.refreshCamera(mCamera);
        cameraPermission = false;

    }*/
   protected void onResume() {
       super.onResume();
       if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
           openCamera();

       } else {
           checkRuntimeLocationPermission();
       }
   }


    public Camera switch_on_camera() {


        try {
            mCamera = Camera.open();
            Parameters parameters = mCamera.getParameters();
            // obj.startPreview();

            if (this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {

                parameters.set("orientation", "portrait");
                mCamera.setDisplayOrientation(90);
                parameters.setRotation(90);
            } else {

                parameters.set("orientation", "landscape");
                mCamera.setDisplayOrientation(0);
                parameters.setRotation(0);

            }
        }catch(RuntimeException e) {
            Log.e(TAG, "Error" + e);
            Toast.makeText(myContext, "Error", Toast.LENGTH_SHORT).show();
        }

        return mCamera;

    }

    protected void onPause() {
        super.onPause();
        //when on Pause, release camera in order to be used from other applications
        releaseCamera();
    }

    private boolean hasCamera(Context context) {
        //check if the device has camera
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            return true;
        } else {
            return false;
        }
    }

    private PictureCallback getPictureCallback() {
        PictureCallback picture = new PictureCallback() {

            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                //make a new picture file
                File pictureFile = getOutputMediaFile();

                if (pictureFile == null) {
                    return;
                }
                try {
                    //write the file
                    FileOutputStream fos = new FileOutputStream(pictureFile);
                    fos.write(data);
                    fos.close();
                    Toast toast = Toast.makeText(myContext, "Picture saved: " + pictureFile.getName(), Toast.LENGTH_LONG);
                    toast.show();

                } catch (FileNotFoundException e) {
                } catch (IOException e) {
                }

                //refresh camera to continue preview
                mPreview.refreshCamera(mCamera);
            }
        };
        return picture;
    }

    OnClickListener captrureListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            mCamera.takePicture(null, null, mPicture);
        }
    };

    private void cameraMethod() {
        String str = "continuous-picture";
        String str2 = "continuous-video";
        try {
            Parameters parameters = mCamera.getParameters();
            if (Build.VERSION.SDK_INT >= 16 && parameters.getSupportedFocusModes().contains(str)) {
                parameters.setFocusMode(str);
            } else if (parameters.getSupportedFocusModes().contains(str2)) {
                parameters.setFocusMode(str2);
            }
            this.mCamera.setParameters(parameters);
        } catch (Exception unused) {
        }
    }

    //make picture and save to a folder
    private static File getOutputMediaFile() {
        //make a new file directory inside the "sdcard" folder
        File mediaStorageDir = new File("/sdcard/", "JCG Camera");

        //if this "JCGCamera folder does not exist
        if (!mediaStorageDir.exists()) {
            //if you cannot make this folder return
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        //take the current timeStamp
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        //and make a media file:
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");

        return mediaFile;
    }

    private void releaseCamera() {
        // stop and release camera
        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }

    }

    public void onBackPressed() {
        ShowInterstitials.showBackPressInterstitialAd(MirrorActivity.this, AdmobInterstitialAd.mInterstitialAd);
    }


    public void initView() {
        mCamera = Camera.open(findFrontFacingCamera());
        //    Toast.makeText(this, "hii this is my view", Toast.LENGTH_SHORT).show();
    }

    private boolean checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            //mCamera = Camera.open(findFrontFacingCamera());
            openCamera();
            startCameraPreview();
           // initView();
             Toast.makeText(this, "You have already granted this permission!", Toast.LENGTH_SHORT).show();
        } else {
            requestCameraPermission();
        }
        return true;
    }


    private void requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {
            new AlertDialog.Builder(this)
                    .setTitle("Camera Permission")
                    .setMessage("Camera Permission Needed To Access Device Camera")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MirrorActivity.this,
                                    new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CODE);
                            startCameraPreview();

                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            checkCameraPermission();
                        }
                    })
                    .create().show();

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
   /* public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
               // initView();
               // mCamera = Camera.open(findFrontFacingCamera());
              openCamera();
               startCameraPreview();
            } else {
                //  Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
                requestCameraPermission();
            }
        }
    }*/
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                showSettingsAlert();
            }
        }
    }

   /* private void checkRuntimeLocationPermission() {

            Dexter.withActivity(this).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
                @Override
                public void onPermissionGranted(PermissionGrantedResponse response) {

                    if (ContextCompat.checkSelfPermission(myContext, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                       // mCamera = Camera.open(findFrontFacingCamera());
                        openCamera();
                        startCameraPreview();
                        setupSurfaceView();
                    } else {
                        requestCameraPermission();
                    }

                }

                @Override
                public void onPermissionDenied(PermissionDeniedResponse response) {
                    showSettingsAlertt();

                }

                @Override
                public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                    token.continuePermissionRequest();
                    openCamera();
                    startCameraPreview();
                    setupSurfaceView();
                }
            }).check();
        }*/
   private void checkRuntimeLocationPermission() {
       Dexter.withActivity(this)
               .withPermission(Manifest.permission.CAMERA)
               .withListener(new PermissionListener() {
                   @Override
                   public void onPermissionGranted(PermissionGrantedResponse response) {
                       openCamera();
                   }

                   @Override
                   public void onPermissionDenied(PermissionDeniedResponse response) {
                       showSettingsAlert();
                   }

                   @Override
                   public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                       token.continuePermissionRequest();
                   }
               }).check();
   }
    private void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Camera Permission");
        alertDialog.setMessage("Camera permission is required. Please enable it in the settings.");
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.fromParts("package", getPackageName(), null));
                startActivity(intent);
            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }
    public void showSettingsAlertt() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(myContext);

        // Setting Dialog Title
        alertDialog.setTitle("Camera Settings");

        // Setting Dialog Message
        alertDialog.setMessage("Camera is not enabled. Do you want to go to settings detail?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", getPackageName(), null));
                myContext.startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alert = alertDialog.create();
        alert.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                alert.getButton(androidx.appcompat.app.AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
                alert.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
            }
        });
        // Showing Alert Message
        alert.show();
    }


    @Override
    public void onAdLoaded() {
        isloaded = true;
    }

    @Override
    public void onFailedToLoadAd(@NonNull LoadAdError loadAdError) {
        isloaded= false;
    }

    private void openCamera() {
        try {
            int cameraId = findFrontFacingCamera();
            if (cameraId != -1) {
                mCamera = Camera.open(cameraId);
                mCamera.setDisplayOrientation(90); // Adjust orientation if needed
                startCameraPreview();

            } else {
                Log.e(TAG, "Front-facing camera not found.");
            }
        } catch (RuntimeException e) {
            Log.e(TAG, "Failed to open camera: " + e.getMessage());
        }
    }

    private void startCameraPreview() {
        if (mCamera != null) {
            try {
                mCamera.setPreviewDisplay(mSurfaceHolder);
                mCamera.startPreview();

            } catch (Exception e) {
                Log.e(TAG, "Error starting camera preview: " + e.getMessage());
                releaseCamera();
            }
        }
    }
}
