package com.gps.tools.speedometer.area.calculator.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.gps.tools.speedometer.area.calculator.R;

public  class RatingDialogCustom {
    private Integer rating = 0;
    public void showExitOnlyDialog(Context activity) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.activity_rate_us);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView emojiOne = dialog.findViewById(R.id.emoji11);
        ImageView emojiTwo = dialog.findViewById(R.id.emoji22);
        ImageView emojiThree = dialog.findViewById(R.id.emoji33);
        ImageView emojiFour = dialog.findViewById(R.id.emoji44);
        ImageView emojiFive = dialog.findViewById(R.id.emoji55);
        ImageView closebtn= dialog.findViewById(R.id.closebutton);
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        emojiOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail(activity, 1);
                dialog.dismiss();
            }
        });

        emojiTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail(activity, 2);
                dialog.dismiss();
            }
        });

        emojiThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail(activity, 3);
                dialog.dismiss();
            }
        });

        emojiFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoToStore(activity);
                dialog.dismiss();
            }
        });

        emojiFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoToStore(activity);
                dialog.dismiss();
            }
        });

        // Uncomment if you want to add the following functionalities
            /*
            exit_yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.finish();
                    activity.finishAffinity();
                }
            });

            exit_no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            */

        try {
            dialog.show();
        } catch (Exception e) {
            Log.e("checkthedialog", "showDialog: " + e.getMessage());
        }


    }

    public void sendEmail(Context context, int rating) {
        Intent selectorIntent = new Intent(Intent.ACTION_SENDTO);
        selectorIntent.setData(Uri.parse("mailto:"));

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"navigationappsstudio@gmail.com"});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Rating Live Earth Map");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Rating: " + rating);
        emailIntent.setSelector(selectorIntent);

        context.startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }
    public void gotoToStore(Context mContext) {
        try {
            Intent intentRateApp = new Intent(Intent.ACTION_VIEW);
            if(intentRateApp.resolveActivity(mContext.getPackageManager())!=null){
                intentRateApp.setData( Uri.parse("https://play.google.com/store/apps/details?id=$packageName" + mContext.getPackageName()));
                mContext.startActivity(intentRateApp);

            }

            //mContext.startActivity(intentRateApp);
        } catch (Exception e) {
            e.printStackTrace(); // It is better to log the exception for debugging purposes
        }
    }


}
