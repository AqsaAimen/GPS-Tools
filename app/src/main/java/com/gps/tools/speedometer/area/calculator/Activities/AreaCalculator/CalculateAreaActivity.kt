package com.gps.tools.speedometer.area.calculator.Activities.AreaCalculator;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.*;
import androidx.appcompat.app.AppCompatActivity;
import com.gps.tools.speedometer.area.calculator.BillingMethod.BillingHelper;
import com.gps.tools.speedometer.area.calculator.R;


@Suppress("DEPRECATION")
class CalculateAreaActivity : AppCompatActivity(), TextView.OnEditorActionListener, OnMapReadyCallback,
    View.OnClickListener, GoogleMap.OnMapClickListener {
    override fun onClick(v: View?) {

    }

    private var latitude = 0.0
    private var longitude = 0.0
    private var address: String? = null
    private var polygon: Polygon? = null
    private var polygonOption = PolygonOptions()

    private var valueInMeterSquare = 0.0
    private var valueInKiloMeterSquare = 0.0
    private var valueInMilesquare = 0.0
    private var valueInFeetSquare = 0.0
    private var myUnit = "m²"
    private var fbInterstitialAdBack: com.facebook.ads.InterstitialAd? = null

    private fun initInterstitialAds2() {
        val billingHelper = BillingHelper(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //  setContentView(R.layout.activity_calculate_area)
        initViews(savedInstanceState)
        Handler().postDelayed({ initInterstitialAds2() }, 6000)
    }


    @SuppressLint("SetTextI18n")
    private fun initViews(savedInstanceState: Bundle?) {
        MapsInitializer.initialize(this)

    }





    private var googleMap: GoogleMap? = null
    override fun onMapReady(p0: GoogleMap) {
        this.googleMap = p0
        googleMap!!.setOnMapClickListener(this)
        googleMap!!.setPadding(0, 0, 0, 275)
        googleMap!!.uiSettings.isCompassEnabled = false
        if (latitude != 0.0 && longitude != 0.0) {
            animateCameraThreeD()
        }
    }

    override fun onMapClick(latLng: LatLng) {
        googleMap!!.clear()
        polygonOption.add(latLng)
        polygonOption.strokeWidth(14F)
        polygonOption.strokeColor(resources.getColor(R.color.colorPrimaryDark))
      //  polygonOption.fillColor(resources.getColor(R.color.polygonColor))
        polygonOption.strokeJointType(PolygonOptions.CONTENTS_FILE_DESCRIPTOR)
        polygon = googleMap!!.addPolygon(polygonOption)
        Log.d("NEWSIZE", "" + polygonOption.points.size)
        if (polygonOption.points.size < 1) {
           // tvInstructions.visibility = View.VISIBLE
        } else {
            // tvInstructions.visibility = View.GONE
        }
        showValues()
    }

    @SuppressLint("SetTextI18n")
    private fun showValues() {
        try {
            valueInMeterSquare = SphericalUtil.computeArea(polygonOption.points)
            valueInKiloMeterSquare = 1000000 * valueInMeterSquare
            valueInMilesquare = valueInMeterSquare * 2589988.1102853
            valueInFeetSquare = valueInMeterSquare * 0.092903
        } catch (e: Exception) {
        }
        when (myUnit) {
            "m²" -> {
               // tvArea.text = "Area " + String.format("%.2f", valueInMeterSquare) + " m²"
            }
            "km²" -> {

            //    tvArea.text = "Area " + String.format("%.2f", valueInKiloMeterSquare) + " km²"
            }
            "mi²" -> {

          //      tvArea.text = "Area " + String.format("%.2f", valueInMilesquare) + " mi²"
            }
            "feet²" -> {

            //    tvArea.text = "Area " + String.format("%.2f", valueInFeetSquare) + " feet²"
            }
        }
        //googleMap!!.clear()
        for (latLngModel in polygonOption.points) {
            addPolygonMarker(latLngModel.latitude, latLngModel.longitude)
        }
    }

    private fun addPolygonMarker(lat: Double, lng: Double) {
        try {
            googleMap?.addMarker(
                MarkerOptions()
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                    .title("$lat, $lng")
                    .position(LatLng(lat, lng))
            )
        } catch (e: Exception) {
        }
    }


    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
       // performSearchAction()
        return true
    }

    private fun animateCameraThreeD() {
        val cameraPosition =
            CameraPosition.Builder().bearing(-10f).target(LatLng(latitude, longitude)).tilt(65f).zoom(16f).build()
        try {
            googleMap?.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setThisType(position: Int) {
        when (position) {
            0 -> {
                googleMap?.mapType = GoogleMap.MAP_TYPE_NORMAL
            }
            1 -> {
                googleMap?.mapType = GoogleMap.MAP_TYPE_SATELLITE
            }
            2 -> {
                googleMap?.mapType = GoogleMap.MAP_TYPE_TERRAIN
            }
        }
    }



}
