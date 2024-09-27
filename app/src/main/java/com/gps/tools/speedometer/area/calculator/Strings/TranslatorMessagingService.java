package com.gps.tools.speedometer.area.calculator.Strings;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.widget.RemoteViews;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.gps.tools.speedometer.area.calculator.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;

public final class TranslatorMessagingService extends FirebaseMessagingService {

    private String title;
    private String body;
    private String description;
    private String icon;
    private Bitmap bitmap;
    private Bitmap bitIcon;
    private final String CHANNEL_ID_MY_FCM = "id_channel";
    private final String CHANNEL_NAME_MY_FCM = "name_channel";
    private final String CHANNEL_DESCRIPTION_MY_FCM = "description_channel";
    private final String TAG = "SplashActivity";


    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        try {
            title = (String)remoteMessage.getData().get("title");
            body = (String)remoteMessage.getData().get("app_url");
            description = (String)remoteMessage.getData().get("short_desc");
            icon = (String)remoteMessage.getData().get("icon");
            bitIcon = (Bitmap) getBitmapImageFromRemoteUrl(icon);
           // this.bitmap = (Bitmap) getBitmapImageFromRemoteUrl(remoteMessage.getData().get("feature"));

        } catch (Exception e){

        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID_MY_FCM, CHANNEL_NAME_MY_FCM, NotificationManager.IMPORTANCE_HIGH);
            mChannel.setDescription(this.CHANNEL_DESCRIPTION_MY_FCM);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.BLUE);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            try {
                assert mNotificationManager != null;
                mNotificationManager.createNotificationChannel(mChannel);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        displayFcmNotification();

}

private final void displayFcmNotification() {

        RemoteViews  bigView = new RemoteViews(getPackageName(), R.layout.custom_notification_large_new);
        RemoteViews  smallView = new RemoteViews(getPackageName(), R.layout.custom_notification_small_new);
        bigView.setTextViewText(R.id.text, description);
        smallView.setTextViewText(R.id.text, description);
        bigView.setTextViewText(R.id.title, title);
        smallView.setTextViewText(R.id.title, title);
        smallView.setImageViewBitmap(R.id.image_app, this.bitIcon);
       // bigView.setImageViewBitmap(R.id.image_app, this.bitmap);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID_MY_FCM).setSmallIcon(R.drawable.ic_notification_bell).setCustomBigContentView(bigView).setCustomContentView(smallView);
        Intent resultIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(body));
        resultIntent.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
        resultIntent.setFlags(FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent  pendingInt = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingInt);
        NotificationManager notificaitonManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificaitonManager.notify(1, builder.build());
    }


    private  final Bitmap getBitmapImageFromRemoteUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            URLConnection var = url.openConnection();
                HttpURLConnection connection = (HttpURLConnection) var;
                connection.setDoInput(true);
                connection.connect();
                InputStream input = (InputStream) null;
                try {
                    input = connection.getInputStream();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return BitmapFactory.decodeStream(input);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}