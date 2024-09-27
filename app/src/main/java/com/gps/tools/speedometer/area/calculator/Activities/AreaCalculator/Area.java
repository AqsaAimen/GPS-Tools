package com.gps.tools.speedometer.area.calculator.Activities.AreaCalculator;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.InterstitialAd;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.StreetViewPanoramaFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.gps.tools.speedometer.area.calculator.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;


public final class Area extends AppCompatActivity implements OnMapReadyCallback, OnStreetViewPanoramaReadyCallback, RecycleViewAdapter.newInterface {
    private HashMap _$_findViewCache;
    public RecycleViewAdapter adapter;
    public GoogleMap amap;
    private double area;
    private String areaKm = "N/A";
    private int count;
    private int currentMarker = R.drawable.b_pointer_new;
    public double distance;
    private ImageView downlaodBtn;
    private AdView fbAdView;
    private AdView fbAdViewTop;
    private LinearLayout fbContainer;
    private LinearLayout fbContainerTop;
    public InterstitialAd fbInterstitialAd;
    private double feets;
    @Nullable
    private SqliteOpenHelperClass helperClass;


    private int f3945i;
    @NotNull
    private ArrayList<Integer> iconList = new ArrayList<>();
    private boolean isMap2d = true;
    private double kms;
    @NotNull
    public LatLng latLngto;
    @NotNull
    public LatLng latlng;

    public final Stack<Polyline> lines = new Stack<>();
    private com.google.android.gms.ads.AdView mAdView;
    private com.google.android.gms.ads.AdView mAdViewTop;
    @NotNull
    public LatLng makerLatLng;
    @NotNull
    public SupportMapFragment mapFragment;
    public PopupWindow mapTypeMenu;
    public ArrayList<Integer> markersList = new ArrayList<>();
    @NotNull
    private String placeSearch = "";
    @NotNull
    private final Stack<Marker> points = new Stack<>();
    private Polygon polygon;
    private Polyline polyline;
    @Nullable
    private View popUpView;
    public StreetViewPanoramaFragment streetFragment;
    @Nullable
    private StreetViewPanorama streetViewarea;
    private double stringdoublelat = 48.858093d;
    private double stringdoublelong = 2.294694d;
    public double temp1;
    public double temp2;
    @NotNull
    private Stack<LatLng> trace = new Stack<>();

    public void _$_clearFindViewByIdCache() {
        if (this._$_findViewCache != null) {
            this._$_findViewCache.clear();
        }
    }

    public View _$_findCachedViewById(int i) {
        if (this._$_findViewCache == null) {
            this._$_findViewCache = new HashMap();
        }
        View view = (View) this._$_findViewCache.get(Integer.valueOf(i));
        if (view != null) {
            return view;
        }
        View findViewById = findViewById(i);
        this._$_findViewCache.put(Integer.valueOf(i), findViewById);
        return findViewById;
    }

    public void button2(int i) {
    }

    public void button3(int i) {
    }

    public void button4(int i) {
    }

 /*   @NotNull
    public static final *//* synthetic *//* RecycleViewAdapter access$getAdapter$p(AreaCalculatorActivity areaCalculatorActivity) {
      //  RecycleViewAdapter recycleViewAdapter = areaCalculatorActivity.adapter;
   //     return recycleViewAdapter;
    }*/

    @NotNull
 //   public static final /* synthetic */ GoogleMap access$getAmap$p(AreaCalculatorActivity areaCalculatorActivity) {
      //  GoogleMap googleMap = areaCalculatorActivity.amap;

     //   return googleMap;
  //  }


    public static final /* synthetic */ PopupWindow access$getMapTypeMenu$p(AreaCalculatorMapActivity areaCalculatorActivity) {
        PopupWindow popupWindow = areaCalculatorActivity.mapTypeMenu;
        return popupWindow;
    }

    public final double getStringdoublelat$app_release() {
        return this.stringdoublelat;
    }

    public final void setStringdoublelat$app_release(double d) {
        this.stringdoublelat = d;
    }

    public final double getStringdoublelong$app_release() {
        return this.stringdoublelong;
    }

    public final void setStringdoublelong$app_release(double d) {
        this.stringdoublelong = d;
    }

    @NotNull
    public final ArrayList<Integer> getIconList() {
        return this.iconList;
    }

    public final void setIconList(@NotNull ArrayList<Integer> arrayList) {
        this.iconList = arrayList;
    }

    @Nullable
    public final View getPopUpView() {
        return this.popUpView;
    }

    public final void setPopUpView(@Nullable View view) {
        this.popUpView = view;
    }

    @NotNull
    public final LatLng getMakerLatLng() {
        LatLng latLng = this.makerLatLng;
        if (latLng == null) {
        }
        return latLng;
    }

    public final void setMakerLatLng(@NotNull LatLng latLng) {
        this.makerLatLng = latLng;
    }

    public final int getI() {
        return this.f3945i;
    }

    public final void setI(int i) {
        this.f3945i = i;
    }

    public final double getFeets() {
        return this.feets;
    }

    public final void setFeets(double d) {
        this.feets = d;
    }

    @NotNull
    public final String getAreaKm() {
        return this.areaKm;
    }

    public final void setAreaKm(@NotNull String str) {
        this.areaKm = str;
    }

    @NotNull
    public final LatLng getLatlng() {
        LatLng latLng = this.latlng;
        if (latLng == null) {
        }
        return latLng;
    }

    public final void setLatlng(@NotNull LatLng latLng) {
        this.latlng = latLng;
    }

    @NotNull
    public final SupportMapFragment getMapFragment() {
        SupportMapFragment supportMapFragment = this.mapFragment;
        if (supportMapFragment == null) {
        }
        return supportMapFragment;
    }

    public final void setMapFragment(@NotNull SupportMapFragment supportMapFragment) {
        this.mapFragment = supportMapFragment;
    }

    @Nullable
    public final StreetViewPanorama getStreetViewarea() {
        return this.streetViewarea;
    }

    public final void setStreetViewarea(@Nullable StreetViewPanorama streetViewPanorama) {
        this.streetViewarea = streetViewPanorama;
    }

    @NotNull
    public final LatLng getLatLngto() {
        LatLng latLng = this.latLngto;
        if (latLng == null) {
        }
        return latLng;
    }

    public final void setLatLngto(@NotNull LatLng latLng) {
        this.latLngto = latLng;
    }

    public final double getKms() {
        return this.kms;
    }

    public final void setKms(double d) {
        this.kms = d;
    }

    @NotNull
    public final String getPlaceSearch() {
        return this.placeSearch;
    }

    public final void setPlaceSearch(@NotNull String str) {
        this.placeSearch = str;
    }

    @Nullable
    public final SqliteOpenHelperClass getHelperClass() {
        return this.helperClass;
    }

    public final void setHelperClass(@Nullable SqliteOpenHelperClass sqliteOpenHelperClass) {
        this.helperClass = sqliteOpenHelperClass;
    }

    @NotNull
    public final Stack<Marker> getPoints() {
        return this.points;
    }

    @NotNull
    public final Stack<LatLng> getTrace() {
        return this.trace;
    }

    public final void setTrace(@NotNull Stack<LatLng> stack) {
        this.trace = stack;
    }

    public final boolean isMap2d() {
        return this.isMap2d;
    }

    public final void setMap2d(boolean z) {
        this.isMap2d = z;
    }

    public final int getCount() {
        return this.count;
    }

    public final void setCount(int i) {
        this.count = i;
    }

    public final int getCurrentMarker() {
        return this.currentMarker;
    }

    public final void setCurrentMarker(int i) {
        this.currentMarker = i;
    }

    public void onStreetViewPanoramaReady(@Nullable StreetViewPanorama streetViewPanorama) {
        this.streetViewarea = streetViewPanorama;
        if (streetViewPanorama == null) {
        }
        streetViewPanorama.setPosition(new LatLng(this.stringdoublelat, this.stringdoublelong));
   //     streetViewPanorama.setOnStreetViewPanoramaChangeListener(new AreaCalculatorActivity$onStreetViewPanoramaReady$1(this));
    }

    public void button1(int r6) {

        throw new UnsupportedOperationException("Method not decompiled: com.streetview.liveearth.mapsnavigation.newModules.p014ui.AreaCalculatorActivity.button1(int):void");
    }


    @SuppressLint({"InflateParams"})
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_area_calculator);
        Context context = this;
   //     this.billingHelper = new BillingHelper(context);
  //      View findViewById = findViewById(R.id.adView);
   //     this.mAdView = (com.google.android.gms.ads.AdView) findViewById;
        this.fbAdView = new AdView(context, getString(R.string.fb_banner_ad_id), AdSize.BANNER_HEIGHT_50);
     //   View findViewById2 = findViewById(R.id.fbAdContainer);
    //    this.fbContainer = (LinearLayout) findViewById2;
    //    View findViewById3 = findViewById(R.id.adView);
    //    this.mAdView = (com.google.android.gms.ads.AdView) findViewById3;
        this.fbAdView = new AdView(context, getString(R.string.fb_banner_ad_id), AdSize.BANNER_HEIGHT_50);
    //    View findViewById4 = findViewById(R.id.fbAdContainer);
     //   this.fbContainer = (LinearLayout) findViewById4;
        View findViewById5 = findViewById(R.id.adViewTop);
        this.mAdViewTop = (com.google.android.gms.ads.AdView) findViewById5;
        this.fbAdViewTop = new AdView(context, getString(R.string.fb_banner_ad_id), AdSize.BANNER_HEIGHT_50);
        View findViewById6 = findViewById(R.id.fbAdContainerTop);
        this.fbContainerTop = (LinearLayout) findViewById6;
    //    BillingHelper billingHelper2 = this.billingHelper;
   /*     if (billingHelper2 == null) {

        }*/
/*        if (billingHelper2.shouldShowAds()) {
       //     UtilHelper.Companion companion = MyAdUtils.Companion;
            AdView adView = this.fbAdView;
            if (adView == null) {
            }
            LinearLayout linearLayout = this.fbContainer;
            if (linearLayout == null) {
            }
            com.google.android.gms.ads.AdView adView2 = this.mAdView;
            if (adView2 == null) {
            }
      //      companion.loadFacebookBannerAd(context, adView, linearLayout, adView2);
      //      UtilHelper.Companion companion2 = MyAdUtils.Companion;
            AdView adView3 = this.fbAdViewTop;
            if (adView3 == null) {
            }
            LinearLayout linearLayout2 = this.fbContainerTop;
            if (linearLayout2 == null) {

            }
            com.google.android.gms.ads.AdView adView4 = this.mAdViewTop;
            if (adView4 == null) {
            }
    //        companion2.loadFacebookBannerAd(context, adView3, linearLayout2, adView4);
        }*/
    //    BillingHelper billingHelper3 = this.billingHelper;
     /*   if (billingHelper3 == null) {
        }*/
  /*      if (billingHelper3.shouldShowAds()) {
        //    UtilHelper.Companion companion3 = MyAdUtils.Companion;
            Context applicationContext = getApplicationContext();
        //    this.mInterstitialAd = companion3.loadAdMobInterstitialAd(applicationContext);
            com.google.android.gms.ads.InterstitialAd interstitialAd = this.mInterstitialAd;
            if (interstitialAd == null) {

            }
     //       interstitialAd.setAdListener(new AreaCalculatorActivity$onCreate$1(this));
        }*/
      //  this.popUpView = getLayoutInflater().inflate(R.layout.map_type_window_layout, null);
        this.mapTypeMenu = new PopupWindow(this.popUpView, -2, -2, true);
        PopupWindow popupWindow = this.mapTypeMenu;
        if (popupWindow == null) {
        }
        popupWindow.setOutsideTouchable(true);
        PopupWindow popupWindow2 = this.mapTypeMenu;
        if (popupWindow2 == null) {
        }
        popupWindow2.setFocusable(true);
        PopupWindow popupWindow3 = this.mapTypeMenu;
        if (popupWindow3 == null) {
        }
        popupWindow3.setTouchable(true);
        PopupWindow popupWindow4 = this.mapTypeMenu;
        if (popupWindow4 == null) {
        }
        popupWindow4.setBackgroundDrawable(new ColorDrawable(0));
        PopupWindow popupWindow5 = this.mapTypeMenu;
        if (popupWindow5 == null) {
        }
        popupWindow5.update();
        Fragment findFragmentById = getSupportFragmentManager().findFragmentById(R.id.mapArea);
        if (findFragmentById != null) {
            this.mapFragment = (SupportMapFragment) findFragmentById;
            SupportMapFragment supportMapFragment = this.mapFragment;
            if (supportMapFragment == null) {
            }
            supportMapFragment.getMapAsync(this);
            android.app.Fragment findFragmentById2 = getFragmentManager().findFragmentById(R.id.map_street_Area);
            if (!(findFragmentById2 instanceof StreetViewPanoramaFragment)) {
                findFragmentById2 = null;
            }
            this.streetFragment = (StreetViewPanoramaFragment) findFragmentById2;
            StreetViewPanoramaFragment streetViewPanoramaFragment = this.streetFragment;
            if (streetViewPanoramaFragment != null) {
                streetViewPanoramaFragment.getStreetViewPanoramaAsync(this);
            }
            View findViewById7 = findViewById(R.id.pdf_download);
            this.downlaodBtn = (ImageView) findViewById7;
            ImageView imageView = this.downlaodBtn;
            if (imageView == null) {
            }
            setAnimationZoom(imageView);
            addMarkersToList();
            this.iconList.add(Integer.valueOf(R.drawable.latitude_longitutde));
            this.iconList.add(Integer.valueOf(R.drawable.unit_convertor));
            this.iconList.add(Integer.valueOf(R.drawable.twod));
            this.adapter = new RecycleViewAdapter(context, this, this.iconList);
            RecyclerView recyclerView = (RecyclerView) _$_findCachedViewById(R.id.rec_view_area);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            RecyclerView recyclerView2 = (RecyclerView) _$_findCachedViewById(R.id.rec_view_area);
            RecycleViewAdapter recycleViewAdapter = this.adapter;
            if (recycleViewAdapter == null) {
            }
            recyclerView2.setAdapter(recycleViewAdapter);
/*            ((ImageView) _$_findCachedViewById(R.id.myLocationBtnArea)).setOnClickListener(new AreaCalculatorActivity$onCreate$2(this));
            ((ImageView) _$_findCachedViewById(R.id.cancel_latlng)).setOnClickListener(new AreaCalculatorActivity$onCreate$3(this));
            ((ImageView) _$_findCachedViewById(R.id.pdf_download)).setOnClickListener(new AreaCalculatorActivity$onCreate$4(this));
            ((ImageView) _$_findCachedViewById(R.id.mapTypeBtnArea)).setOnClickListener(new AreaCalculatorActivity$onCreate$5(this));
            ((ImageView) _$_findCachedViewById(R.id.search_text)).setOnClickListener(new AreaCalculatorActivity$onCreate$6(this));
            ((ImageView) _$_findCachedViewById(R.id.routfinder_Area)).setOnClickListener(new AreaCalculatorActivity$onCreate$7(this));
            ((ImageView) _$_findCachedViewById(R.id.streetView_Area)).setOnClickListener(new AreaCalculatorActivity$onCreate$8(this));
            ((ImageView) _$_findCachedViewById(R.id.history_Area)).setOnClickListener(new AreaCalculatorActivity$onCreate$9(this));*/
            return;
        }
    }

    public void onBackPressed() {
      //  MyAdUtils.Companion.adOnBackPressed(this, this, this.mInterstitialAd, this.fbInterstitialAd, AnalyticsConstants.AREA_MEASUREMENT_ACTIVITY_BACK_PRESSED);
    }

    public final void addMarkersToList() {
        this.markersList.add(Integer.valueOf(R.drawable.marker_a));
        this.markersList.add(Integer.valueOf(R.drawable.marker_b));
        this.markersList.add(Integer.valueOf(R.drawable.marker_c));
        this.markersList.add(Integer.valueOf(R.drawable.marker_d));
/*        this.markersList.add(Integer.valueOf(C1987R.C1988drawable.marker_e));
        this.markersList.add(Integer.valueOf(C1987R.C1988drawable.marker_f));
        this.markersList.add(Integer.valueOf(C1987R.C1988drawable.marker_g));
        this.markersList.add(Integer.valueOf(C1987R.C1988drawable.marker_h));
        this.markersList.add(Integer.valueOf(C1987R.C1988drawable.marker_i));
        this.markersList.add(Integer.valueOf(C1987R.C1988drawable.marker_j));
        this.markersList.add(Integer.valueOf(C1987R.C1988drawable.marker_k));
        this.markersList.add(Integer.valueOf(C1987R.C1988drawable.marker_l));
        this.markersList.add(Integer.valueOf(C1987R.C1988drawable.marker_m));
        this.markersList.add(Integer.valueOf(C1987R.C1988drawable.marker_n));
        this.markersList.add(Integer.valueOf(C1987R.C1988drawable.marker_o));
        this.markersList.add(Integer.valueOf(C1987R.C1988drawable.marker_p));
        this.markersList.add(Integer.valueOf(C1987R.C1988drawable.marker_q));
        this.markersList.add(Integer.valueOf(C1987R.C1988drawable.marker_r));
        this.markersList.add(Integer.valueOf(C1987R.C1988drawable.marker_s));
        this.markersList.add(Integer.valueOf(C1987R.C1988drawable.marker_t));
        this.markersList.add(Integer.valueOf(C1987R.C1988drawable.marker_u));
        this.markersList.add(Integer.valueOf(C1987R.C1988drawable.marker_v));
        this.markersList.add(Integer.valueOf(C1987R.C1988drawable.marker_w));
        this.markersList.add(Integer.valueOf(C1987R.C1988drawable.marker_x));
        this.markersList.add(Integer.valueOf(C1987R.C1988drawable.marker_y));
        this.markersList.add(Integer.valueOf(C1987R.C1988drawable.marker_z));*/
    }

    public final void setAnimationZoom(@NotNull View view) {
        Animation loadAnimation = AnimationUtils.loadAnimation(this, R.anim.zoom);
        loadAnimation.setDuration(500);
        view.setAnimation(loadAnimation);
        view.animate();
        loadAnimation.start();
    }

    public final String getStringFromList(Stack<LatLng> stack) {
        String str = "";
        int size = stack.size();
        for (int i = 0; i < size; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(((LatLng) stack.get(i)).latitude);
            sb.append(',');
            sb.append(((LatLng) stack.get(i)).longitude);
            sb.append('|');
            str = sb.toString();
        }
        return str;
    }

    public final void dismissMapTypeMenu() {
        try {
            PopupWindow popupWindow = this.mapTypeMenu;
            if (popupWindow == null) {
            }
            popupWindow.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onMapReady(@NotNull GoogleMap googleMap) {
        amap = googleMap;
        makerLatLng = new LatLng(48.858093d, 2.294694d);
        try {
            GoogleMap googleMap2 = amap;
            if (googleMap2 == null) {

            }
            LatLng latLng = makerLatLng;
            if (latLng == null) {

            }
            googleMap2.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12.0f));
        } catch (Exception unused) {
        }
        GoogleMap googleMap3 = amap;
        if (googleMap3 == null) {

        }
      //  googleMap3.setOnMapClickListener(new AreaCalculatorActivityonMapReady(this));
    }

    public final void drawPolyline() {
        int i = 0;
        PolylineOptions addAll = new PolylineOptions().addAll(this.trace.subList(0, this.trace.size()));
        if (this.polyline != null) {
            Polyline polyline2 = this.polyline;
            if (polyline2 == null) {

            }
            polyline2.remove();
        }
        try {
            GoogleMap googleMap = this.amap;
            if (googleMap == null) {

            }
            this.polyline = googleMap.addPolyline(addAll.width(5.0f).color(-16776961));
        } catch (Exception unused) {
        }
        List subList = this.trace.subList(0, this.trace.size());
        this.distance = 0.0d;
        int size = subList.size() - 1;
        while (i < size) {
            i++;
            this.distance += SphericalUtil.computeDistanceBetween((LatLng) subList.get(i), (LatLng) subList.get(i));
            //MathKt.roundToLong(this.distance);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(decimalFormat().format(this.distance));
        sb.append(" m");
        String sb2 = sb.toString();
        StringBuilder sb3 = new StringBuilder();
        sb3.append("distance /n ");
        sb3.append(sb2);
        Log.e("Area", sb3.toString());
    }

    @SuppressLint({"SetTextI18n"})
    public final void drawPolygone() {
        PolygonOptions fillColor = new PolygonOptions().addAll(this.trace.subList(0, this.trace.size())).strokeColor(-16776961).fillColor(Color.parseColor("#800000FF"));
        if (this.polygon != null) {
            Polygon polygon2 = this.polygon;
            if (polygon2 == null) {

            }
            polygon2.remove();
        }
        try {
            GoogleMap googleMap = this.amap;
            if (googleMap == null) {

            }
            this.polygon = googleMap.addPolygon(fillColor);
        } catch (Exception unused) {
        }
        this.area = SphericalUtil.computeArea(this.trace.subList(0, this.trace.size()));
        TextView textView = (TextView) _$_findCachedViewById(R.id.textView_results);
        StringBuilder sb = new StringBuilder();
        sb.append(decimalFormat().format(this.area));
        sb.append(" m²");
        textView.setText(sb.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append("m sq ");
        sb2.append(decimalFormat().format(this.area));
        Log.d("areavalu", sb2.toString());
        feets();
    }

    public final void feets() {
        try {
            String format = decimalFormat().format(this.area);
            this.feets = Double.parseDouble(format) * 10.764d;
        } catch (Exception unused) {
        }
    }

    private final DecimalFormat decimalFormat() {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        decimalFormat.setRoundingMode(RoundingMode.CEILING);
        return decimalFormat;
    }


    public void onActivityResult(int r9, int r10, @Nullable android.content.Intent r11) {

        super.onActivityResult(r9, r10, r11);
    }

    private final void showDialog() {
        Dialog dialog = new Dialog(this);
        int i = 1;
        dialog.requestWindowFeature(1);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.latlong_items_view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        View findViewById = dialog.findViewById(R.id.text_latlong);
        if (findViewById != null) {
            TextView textView = (TextView) findViewById;
            StringBuilder sb = new StringBuilder();
            int size = this.trace.size();
            if (1 <= size) {
                while (true) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("Marker ");
                    sb2.append(i);
                    sb2.append(" is at ");
                    sb2.append((LatLng) this.trace.get(i - 1));
                    sb2.append(" \n\n");
                    sb.append(sb2.toString());
                    if (i == size) {
                        break;
                    }
                    i++;
                }
            }
            textView.setText(sb.toString());
            dialog.show();
            return;
        }
    }

    private final void showAreaDialog() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(1);
        dialog.setCancelable(true);
      //  dialog.setContentView(R.layout.meter_foot_view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        double d = this.distance;
        double d2 = (double) 1000;
        Double.isNaN(d2);
        double d3 = d * d2;
        View findViewById = dialog.findViewById(R.id.distanceM);

        TextView textView = (TextView) findViewById;
        StringBuilder sb = new StringBuilder();
        sb.append(decimalFormat().format(d3));
        sb.append(" m ");
        textView.setText(sb.toString());
        View findViewById2 = dialog.findViewById(R.id.distanceKM);
        TextView textView2 = (TextView) findViewById2;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(decimalFormat().format(this.distance));
        sb2.append(" km");
        textView2.setText(sb2.toString());
        double d4 = this.distance;
        double d5 = (double) 0.3048f;
        Double.isNaN(d5);
        double d6 = d4 / d5;
        View findViewById3 = dialog.findViewById(R.id.distanceft);

        TextView textView3 = (TextView) findViewById3;
        StringBuilder sb3 = new StringBuilder();
        sb3.append(decimalFormat().format(d6));
        sb3.append(" ft");
        textView3.setText(sb3.toString());
        double d7 = this.distance / 0.9144d;
        View findViewById4 = dialog.findViewById(R.id.distanceyd);

        TextView textView4 = (TextView) findViewById4;
        StringBuilder sb4 = new StringBuilder();
        sb4.append(decimalFormat().format(d7));
        sb4.append(" yd");
        textView4.setText(sb4.toString());
        double d8 = this.distance;
        double d9 = (double) 1609.344f;
        Double.isNaN(d9);
        double d10 = d8 / d9;
        View findViewById5 = dialog.findViewById(R.id.distancemi);

        TextView textView5 = (TextView) findViewById5;
        StringBuilder sb5 = new StringBuilder();
        sb5.append(decimalFormat().format(d10));
        sb5.append(" mi");
        textView5.setText(sb5.toString());
        double d11 = this.distance;
        double d12 = (double) 1852.0f;
        Double.isNaN(d12);
        double d13 = d11 / d12;
        View findViewById6 = dialog.findViewById(R.id.distancenm);


        TextView textView6 = (TextView) findViewById6;
        StringBuilder sb6 = new StringBuilder();
        sb6.append(decimalFormat().format(d13));
        sb6.append(" nautical miles");
        textView6.setText(sb6.toString());
        View findViewById7 = dialog.findViewById(R.id.areaM);

        TextView textView7 = (TextView) findViewById7;
        StringBuilder sb7 = new StringBuilder();
        sb7.append(decimalFormat().format(this.area));
        sb7.append(" m²");
        textView7.setText(sb7.toString());
        double d14 = this.area;
        Double.isNaN(d2);
        this.kms = d14 / d2;
        View findViewById8 = dialog.findViewById(R.id.areaKM);

        TextView textView8 = (TextView) findViewById8;
        StringBuilder sb8 = new StringBuilder();
        sb8.append(decimalFormat().format(this.kms));
        sb8.append(" km²");
        textView8.setText(sb8.toString());
        StringBuilder sb9 = new StringBuilder();
        sb9.append(decimalFormat().format(this.kms));
        sb9.append(" km²");
        this.areaKm = sb9.toString();
        double d15 = this.area / 0.09290304d;
        View findViewById9 = dialog.findViewById(R.id.areaft);
        TextView textView9 = (TextView) findViewById9;
        StringBuilder sb10 = new StringBuilder();
        sb10.append(decimalFormat().format(d15));
        sb10.append(" ft²");
        textView9.setText(sb10.toString());
        double d16 = this.area;
        double d17 = (double) 100;
        Double.isNaN(d17);
        double d18 = d16 * d17;
        View findViewById10 = dialog.findViewById(R.id.areaha);
        TextView textView10 = (TextView) findViewById10;
        StringBuilder sb11 = new StringBuilder();
        sb11.append(decimalFormat().format(d18));
        sb11.append(" ha");
        textView10.setText(sb11.toString());
        double d19 = this.area / 25899.880336d;
        View findViewById11 = dialog.findViewById(R.id.areami);
        TextView textView11 = (TextView) findViewById11;
        StringBuilder sb12 = new StringBuilder();
        sb12.append(decimalFormat().format(d19));
        sb12.append(" mi²");
        textView11.setText(sb12.toString());
        double d20 = this.area / 4046.8726099d;
        View findViewById12 = dialog.findViewById(R.id.areaac);
        TextView textView12 = (TextView) findViewById12;
        StringBuilder sb13 = new StringBuilder();
        sb13.append(decimalFormat().format(d20));
        sb13.append(" ac(U.S Survey)");
        textView12.setText(sb13.toString());
        dialog.show();
    }

    private final ArrayList<LatLng> getListFromString(String r12) {
        /*
            r11 = this;
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            java.lang.CharSequence r12 = (java.lang.CharSequence) r12
            java.lang.String r1 = "\\|"
            kotlin.text.Regex r2 = new kotlin.text.Regex
            r2.<init>(r1)
            r1 = 0
            java.util.List r12 = r2.split(r12, r1)
            boolean r2 = r12.isEmpty()
            r3 = 1
            if (r2 != 0) goto L_0x0047
            int r2 = r12.size()
            java.util.ListIterator r2 = r12.listIterator(r2)
        L_0x0022:
            boolean r4 = r2.hasPrevious()
            if (r4 == 0) goto L_0x0047
            java.lang.Object r4 = r2.previous()
            java.lang.String r4 = (java.lang.String) r4
            java.lang.CharSequence r4 = (java.lang.CharSequence) r4
            int r4 = r4.length()
            if (r4 != 0) goto L_0x0038
            r4 = 1
            goto L_0x0039
        L_0x0038:
            r4 = 0
        L_0x0039:
            if (r4 != 0) goto L_0x0022
            java.lang.Iterable r12 = (java.lang.Iterable) r12
            int r2 = r2.nextIndex()
            int r2 = r2 + r3
            java.util.List r12 = kotlin.collections.CollectionsKt.take(r12, r2)
            goto L_0x004b
        L_0x0047:
            java.util.List r12 = kotlin.collections.CollectionsKt.emptyList()
        L_0x004b:
            java.util.Collection r12 = (java.util.Collection) r12
            if (r12 == 0) goto L_0x00e1
            java.lang.String[] r2 = new java.lang.String[r1]
            java.lang.Object[] r12 = r12.toArray(r2)
            if (r12 == 0) goto L_0x00d9
            java.lang.String[] r12 = (java.lang.String[]) r12
            int r2 = r12.length
            r4 = 0
        L_0x005b:
            if (r4 >= r2) goto L_0x00d8
            r5 = r12[r4]
            java.lang.CharSequence r5 = (java.lang.CharSequence) r5
            java.lang.String r6 = ","
            kotlin.text.Regex r7 = new kotlin.text.Regex
            r7.<init>(r6)
            java.util.List r5 = r7.split(r5, r1)
            boolean r6 = r5.isEmpty()
            if (r6 != 0) goto L_0x009f
            int r6 = r5.size()
            java.util.ListIterator r6 = r5.listIterator(r6)
        L_0x007a:
            boolean r7 = r6.hasPrevious()
            if (r7 == 0) goto L_0x009f
            java.lang.Object r7 = r6.previous()
            java.lang.String r7 = (java.lang.String) r7
            java.lang.CharSequence r7 = (java.lang.CharSequence) r7
            int r7 = r7.length()
            if (r7 != 0) goto L_0x0090
            r7 = 1
            goto L_0x0091
        L_0x0090:
            r7 = 0
        L_0x0091:
            if (r7 != 0) goto L_0x007a
            java.lang.Iterable r5 = (java.lang.Iterable) r5
            int r6 = r6.nextIndex()
            int r6 = r6 + r3
            java.util.List r5 = kotlin.collections.CollectionsKt.take(r5, r6)
            goto L_0x00a3
        L_0x009f:
            java.util.List r5 = kotlin.collections.CollectionsKt.emptyList()
        L_0x00a3:
            java.util.Collection r5 = (java.util.Collection) r5
            if (r5 == 0) goto L_0x00d0
            java.lang.String[] r6 = new java.lang.String[r1]
            java.lang.Object[] r5 = r5.toArray(r6)
            if (r5 == 0) goto L_0x00c8
            java.lang.String[] r5 = (java.lang.String[]) r5
            com.google.android.gms.maps.model.LatLng r6 = new com.google.android.gms.maps.model.LatLng
            r7 = r5[r1]
            double r7 = java.lang.Double.parseDouble(r7)
            r5 = r5[r3]
            double r9 = java.lang.Double.parseDouble(r5)
            r6.<init>(r7, r9)
            r0.add(r6)
            int r4 = r4 + 1
            goto L_0x005b
        L_0x00c8:
            kotlin.TypeCastException r12 = new kotlin.TypeCastException
            java.lang.String r0 = "null cannot be cast to non-null type kotlin.Array<T>"
            r12.<init>(r0)
            throw r12
        L_0x00d0:
            kotlin.TypeCastException r12 = new kotlin.TypeCastException
            java.lang.String r0 = "null cannot be cast to non-null type java.util.Collection<T>"
            r12.<init>(r0)
            throw r12
        L_0x00d8:
            return r0
        L_0x00d9:
            kotlin.TypeCastException r12 = new kotlin.TypeCastException
            java.lang.String r0 = "null cannot be cast to non-null type kotlin.Array<T>"
            r12.<init>(r0)
            throw r12
        L_0x00e1:
            kotlin.TypeCastException r12 = new kotlin.TypeCastException
            java.lang.String r0 = "null cannot be cast to non-null type java.util.Collection<T>"
            r12.<init>(r0)
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.streetview.liveearth.mapsnavigation.newModules.p014ui.AreaCalculatorActivity.getListFromString(java.lang.String):java.util.ArrayList");
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
