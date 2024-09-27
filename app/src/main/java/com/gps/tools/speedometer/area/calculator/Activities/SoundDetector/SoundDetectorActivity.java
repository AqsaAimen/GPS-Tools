package com.gps.tools.speedometer.area.calculator.Activities.SoundDetector;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.github.anastr.speedviewlib.AwesomeSpeedometer;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.gps.tools.speedometer.area.calculator.AddsManager.AdCallBack;
import com.gps.tools.speedometer.area.calculator.AddsManager.AdmobInterstitialAd;
import com.gps.tools.speedometer.area.calculator.AddsManager.ShowInterstitials;
import com.gps.tools.speedometer.area.calculator.R;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
public class SoundDetectorActivity extends AppCompatActivity {

    private int avg;
    private int max;
    private MediaRecorder mediaRecorder;
    private int min = 120;
    ImageView back_button;
    private Context myContext = this;
    private String outputFilePath;
    AdView adviewsounddetector;
    private HashMap<Integer, View> _$_findViewCache = new HashMap<>();
    private MediaPlayer mediaPlayer;
    private static final int PERMISSION_REQUEST_CODE = 200;
    Boolean stopSoundDetecter;
    ConstraintLayout adviewConstraint;

    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_sound_detector);
        //Button stopsound= findViewById(R.id.stopBtn);
        stopSoundDetecter = true;
        // Button startbtn= findViewById(R.id.startbtn);
        // Button stopbtn= findViewById(R.id.stopbtn);
        // Button playbtn= findViewById(R.id.playbtn);
        //checkRuntimeMikePermission();

        adviewConstraint = findViewById(R.id.cons_Ad_view);
        adviewsounddetector = findViewById(R.id.adView_sound);

        AdmobInterstitialAd.gpsToolsBannerAdMob(adviewConstraint, this);


         checkMikePermission();

        back_button = (ImageView) findViewById(R.id.drawer_icon_sound);

        back_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
       /* stopsound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (stopSoundDetecter){
                    stopSoundDetecter = false;
                    isRunSound();
                }else {
                    stopSoundDetecter = true;
                    isRunSound();
                }
            }
        });*/

        /*startbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRecording();
            }
        });
        stopbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopRecording();
            }
        });
        playbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playRecording();
            }
        });*/


   /* private void isRunSound(){
        if (stopSoundDetecter){
            initView();
        }else {
            stopMedia();
        }
    }*/

   /*private void loadBanner(AdView adviewsounddetector) {

        // Create a new ad view.

        AdRequest adRequest = new AdRequest.Builder().build();
        this.adviewsounddetector.loadAd(adRequest);
        this.adviewsounddetector.setAdListener(new AdListener() {
            @Override
            public void onAdClicked() {
                super.onAdClicked();
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                SoundDetectorActivity.this.adviewsounddetector.setVisibility(View.GONE);
            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                SoundDetectorActivity.this.adviewsounddetector.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }

            @Override
            public void onAdSwipeGestureClicked() {
                super.onAdSwipeGestureClicked();
            }
        });
    }*/


    public View _$_findCachedViewById(int i) {
        if (this._$_findViewCache == null) {
            this._$_findViewCache = new HashMap<>();
        }
        View view = this._$_findViewCache.get(i);
        if (view != null) {
            return view;
        }
        View findViewById = findViewById(i);
        this._$_findViewCache.put(i, findViewById);
        return findViewById;
    }

    /* private final void initializeMediaRecorder() {
         try {
             this.mediaRecorder = new MediaRecorder();
             if (this.mediaRecorder != null) {
                 this.mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                 this.mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                 this.mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                 this.mediaRecorder.setOutputFile("/dev/null");
                 this.mediaRecorder.prepare();
                 this.mediaRecorder.start();
                 Log.d("SoundDetectorInfo", "MediaRecorder started successfully.");
                 getAmplitude();
             } else {
                 throw new Exception("MediaRecorder initialization failed.");
             }
         } catch (Exception e) {
             e.printStackTrace();
             Log.e("SoundDetectorInfo", "MediaRecorder initialization failed: " + e.getMessage());
             // Uncomment the following line if you want to show a toast message
             // Toast.makeText(this, "Unable to capture mic!", Toast.LENGTH_SHORT).show();
         }
     }*/
    private static final int REQUEST_PERMISSION_CODE = 1000;

    private boolean checkPermissions() {
        int recordPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        int writePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return recordPermission == PackageManager.PERMISSION_GRANTED && writePermission == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        }, REQUEST_PERMISSION_CODE);
    }

    /* @Override
     public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
         super.onRequestPermissionsResult(requestCode, permissions, grantResults);
         if (requestCode == REQUEST_PERMISSION_CODE) {
             if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                 initializeMediaRecorder();
             } else {
                 // Permission denied
             }
         }
     }*/
    private void initializeMediaRecorder() {
        outputFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording.3gp";
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mediaRecorder.setOutputFile(outputFilePath);
        Log.d("sounddetectedinfo", "error on initilization");
        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public final void getAmplitude() {
        double d = 20.0;
        if (this.mediaRecorder != null) {
            try {
                double log10 = Math.log10(this.mediaRecorder.getMaxAmplitude());
                int i = (int) (d * log10);
                ((AwesomeSpeedometer) _$_findCachedViewById(R.id.dbValueMeter)).speedTo((float) i);

                if (i < this.min) {
                    this.min = i;
                }
                if (i > this.max) {
                    this.max = i;
                }
                this.avg = (this.max + this.min) / 2;
                ((TextView) _$_findCachedViewById(R.id.minTv)).setText(String.valueOf(this.min - 13));
                ((TextView) _$_findCachedViewById(R.id.avgTv)).setText(String.valueOf(this.avg - 13));
                ((TextView) _$_findCachedViewById(R.id.maxTv)).setText(String.valueOf(this.max - 13));
                ((GraphSingleLine) _$_findCachedViewById(R.id.graphMaker)).draw((i - 77) * -1, 3);

                new Handler().postDelayed(this::getAmplitude, 150);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("SoundDetectorInfo", "Error getting amplitude: " + e.getMessage());
            }
        }
    }

    private void startRecording() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String path = sharedPreferences.getString("recordingPath", "");
        // if (mediaRecorder != null) {
        try {
            File customDirectory = new File(getExternalFilesDir(Environment.DIRECTORY_MUSIC), "MyRecordings");
            if (!customDirectory.exists()) {
                customDirectory.mkdirs();
            }
            outputFilePath = customDirectory.getAbsolutePath() + "/recording.3gp";
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.setOutputFile(outputFilePath);
            mediaRecorder.prepare();
            mediaRecorder.start();
            getAmplitude();
        } catch (IllegalStateException e) {
            e.printStackTrace();
            // Handle the error
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // }
    }

    private void stopRecording() {
        if (mediaRecorder != null) {
            try {
                mediaRecorder.stop();
            } catch (IllegalStateException e) {
                e.printStackTrace();
                // Handle the error
            } finally {
                mediaRecorder.release();
                mediaRecorder = null;
                // Save the path to SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("recordingPath", outputFilePath);
                editor.apply();
            }
        }
    }

    private void playRecording() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String path = sharedPreferences.getString("recordingPath", "");

        if (!path.isEmpty()) {
            mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setDataSource(path);
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
                // Handle the error
            }
        } else {
            // Handle the case where the path is empty or not found
        }
    }

 /*   @Override
   public void onPause() {
        super.onPause();
        try {
            stopMedia();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("SoundDetectorInfo", "Error stopping MediaRecorder: " + e.getMessage());
        }
    }*/



   /* @Override
   public void onResume() {
        super.onResume();
        initializeMediaRecorder();
    }*/

    @Override
    public void onBackPressed() {
        ShowInterstitials.showBackPressInterstitialAd(SoundDetectorActivity.this, AdmobInterstitialAd.mInterstitialAd);
    }

    public void initView() {
        //  initializeMediaRecorder();
        startRecording();
        // Toast.makeText(this, "Hi, this is my view", Toast.LENGTH_SHORT).show();
    }

    private boolean checkMikePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
            initView();
            return true;
        } else {
            requestMikePermission();
            return false;
        }
    }

    private void requestMikePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECORD_AUDIO)) {
            new AlertDialog.Builder(this)
                    .setTitle("Mic Permission")
                    .setMessage("Mic Permission Needed To Access Device Mic")
                    .setPositiveButton("OK", (dialog, which) ->
                            ActivityCompat.requestPermissions(SoundDetectorActivity.this,
                                    new String[]{Manifest.permission.RECORD_AUDIO}, PERMISSION_REQUEST_CODE))
                    .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initView();
            } else {
                requestMikePermission();
            }
        }
    }

    private void checkRuntimeMikePermission() {
        Dexter.withActivity(this).withPermission(Manifest.permission.RECORD_AUDIO).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {
                if (ContextCompat.checkSelfPermission(myContext, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
                    initView();
                } else {
                    requestMikePermission();
                }
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

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(myContext);
        alertDialog.setTitle("Audio Settings");
        alertDialog.setMessage("Audio Recorder is not enabled. Do you want to go to settings detail?");
        alertDialog.setPositiveButton("Settings", (dialog, which) -> {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", getPackageName(), null));
            startActivity(intent);
        });
        alertDialog.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        AlertDialog alert = alertDialog.create();
        alert.setOnShowListener(dialogInterface -> {
            alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
            alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
        });
        alert.show();
    }
}

