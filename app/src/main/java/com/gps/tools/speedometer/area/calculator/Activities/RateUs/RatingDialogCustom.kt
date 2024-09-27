package com.gps.tools.speedometer.area.calculator.Activities.RateUs;

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.ImageView
import com.gps.tools.speedometer.area.calculator.R

public class RatingDialogCustom {

    fun showExitOnlyDialog(activity: Context?) {
        val dialog = Dialog(activity!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.activity_rate_us)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val emojiOne = dialog.findViewById<View>(R.id.emoji11) as ImageView
        val emojiTwo = dialog.findViewById<View>(R.id.emoji22) as ImageView
        val emojiThree = dialog.findViewById<View>(R.id.emoji33) as ImageView
        val emojiFour = dialog.findViewById<View>(R.id.emoji44) as ImageView
        val emojiFive = dialog.findViewById<View>(R.id.emoji55) as ImageView

        emojiOne.setOnClickListener {
            sendEmail(context = activity,1)
           dialog.dismiss()
        }
        emojiTwo.setOnClickListener {
            sendEmail(context = activity,2)
           dialog.dismiss()
        }
        emojiThree.setOnClickListener {
            sendEmail(context = activity,3)
           dialog.dismiss()
        }
        emojiFour.setOnClickListener {
            gotoToStore(activity)
             dialog.dismiss()
        }
        emojiFive.setOnClickListener {
            gotoToStore(activity)
            dialog.dismiss()

        }


//        exit_yes.setOnClickListener {
//            activity.finish()
//            activity.finishAffinity()
//        }

//        exit_no.setOnClickListener {
//            dialog.dismiss()
//        }

        try {
            dialog.show()
        } catch (e: Exception) {
            Log.e("checkthedialog", "showDialog: " + e.message)
        }

          dialog.dismiss();

    }

    fun sendEmail(context: Context, rating: Int) {
        val selectorIntent = Intent(Intent.ACTION_SENDTO)
        selectorIntent.data = Uri.parse("mailto:")
        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("navigationappsstudio@gmail.com"))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Rating Live Earth Map")
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Rating: $rating")
        emailIntent.selector = selectorIntent
        context.startActivity(Intent.createChooser(emailIntent, "Send email..."))

    }
    fun gotoToStore(mContext: Context) {
        try {
            val intentRateApp =
                Intent(Intent.ACTION_VIEW, Uri.parse(mContext.getString(R.string.rate_app)))
            mContext.startActivity(intentRateApp)
        } catch (e: Exception) {
        }
    }

}