package com.gps.tools.speedometer.area.calculator.Activities.Converter;

import static com.gps.tools.speedometer.area.calculator.AddsManager.AdmobInterstitialAd.isFirstCLickEnabled;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.gps.tools.speedometer.area.calculator.AddsManager.AdmobInterstitialAd;
import com.gps.tools.speedometer.area.calculator.R;

import java.text.DecimalFormat;
import java.util.Objects;

public class ConverterDetailActivity extends AppCompatActivity implements TextWatcher{

    EditText enter;

    TextView sample;
    TextView get;
    TextView end;
    Button calculate;
    Button clear;
    AdView ddetailadview;

    float settings_decimal;

    ImageView back_button;
    Intent i;

    ArrayAdapter<CharSequence> adapterfromlength;
    ArrayAdapter<CharSequence> adaptertolength;

    ArrayAdapter<CharSequence> adapterfromarea;
    ArrayAdapter<CharSequence> adaptertoarea;

    ArrayAdapter<CharSequence> adapterfromvolume;
    ArrayAdapter<CharSequence> adaptertovolume;

    ArrayAdapter<CharSequence> adapterfromtemp;
    ArrayAdapter<CharSequence> adaptertotemp;

    ArrayAdapter<CharSequence> adapterfromspeed;
    ArrayAdapter<CharSequence> adaptertospeed;

    ArrayAdapter<CharSequence> adapterfrompower;
    ArrayAdapter<CharSequence> adaptertopower;

    ArrayAdapter<CharSequence> adapterfromenergy;
    ArrayAdapter<CharSequence> adaptertoenergy;

    ArrayAdapter<CharSequence> adapterfromweight;
    ArrayAdapter<CharSequence> adaptertoweight;

    ArrayAdapter<CharSequence> adapterfrompressure;
    ArrayAdapter<CharSequence> adaptertopressure;

    ArrayAdapter<CharSequence> adapterfromwork;
    ArrayAdapter<CharSequence> adaptertowork;

    ArrayAdapter<CharSequence> adapterfromdata;
    ArrayAdapter<CharSequence> adaptertodata;

    ArrayAdapter<CharSequence> adapterfromforce;
    ArrayAdapter<CharSequence> adaptertoforce;

    String eChangerTo;
    String eChangerFrom;

    String textTo;
    String textFrom;

    String enterValue;
    String enterValueWarning;

    DecimalFormat df;
    DecimalFormat ds;

    // Temperature Start

    String Fahrenheit;
    String Celsius;
    String Kelvin;

    // Temperature-Kısaltma


    String Fahrenheit2;
    String Celcius2;
    String Kelvin2;
    ConstraintLayout adviewConstraint;
    RelativeLayout frame;

    Spinner to;
    Spinner from;


    double val;
    double res;
    char c;

    int a;
    private Context activity;
AdmobInterstitialAd interstitialAd;
    InterstitialAd admob;
    com.facebook.ads.InterstitialAd fb;
    
    com.facebook.ads.AdView adViewFb;
    AdView mAdViewMob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter_detail);



      //  showFaceBookBannerAdd();
        ddetailadview= findViewById(R.id.adView_converter_detail);
        adviewConstraint = findViewById(R.id.cons_Ad_view);
         interstitialAd.gpsToolsBannerAdMob(adviewConstraint ,this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        back_button = (ImageView) findViewById(R.id.drawer_icon_convert);


        Fahrenheit = "Fahrenheit";
        Celsius = "Celsius";
        Kelvin = "Kelvin";

        // Temperature-Kısaltma

        Fahrenheit2 = "F";
        Celcius2 = "C";
        Kelvin2 = "K";

        //definiton fnish

        c = '0';

        calculate = (Button)findViewById(R.id.calculate);
        clear=(Button)findViewById(R.id.restore);
        enter = (EditText)findViewById(R.id.enter);

        enter.setTextSize(20);
        sample=(TextView)findViewById(R.id.sample);

        get=(TextView)findViewById(R.id.get);
        end=(TextView)findViewById(R.id.end);



        end.setTextSize(20);
        get.setTextSize(20);
        sample.setTextSize(18);


            calculate.setText("Convert");
            clear.setText("Clear");

            get.setText("Your Value");
            end.setText("Result");


            enterValue = "";
            enterValueWarning = "Please enter a valid number !";



        a = getIntent().getIntExtra("a",a);

        to = (Spinner)findViewById(R.id.chooseTo);
        from = (Spinner)findViewById(R.id.chooseFrom);


        adaptertolength = ArrayAdapter.createFromResource(this,R.array.tolength, R.layout.spinner_item);
        adapterfromlength = ArrayAdapter.createFromResource(this,R.array.fromlength, R.layout.spinner_item);

        adaptertoarea = ArrayAdapter.createFromResource(this,R.array.toarea, R.layout.spinner_item);
        adapterfromarea = ArrayAdapter.createFromResource(this,R.array.fromarea, R.layout.spinner_item);

        adaptertovolume = ArrayAdapter.createFromResource(this,R.array.tovolume, R.layout.spinner_item);
        adapterfromvolume = ArrayAdapter.createFromResource(this,R.array.fromvolume, R.layout.spinner_item);

        adapterfromweight = ArrayAdapter.createFromResource(this,R.array.fromweight, R.layout.spinner_item);
        adaptertoweight = ArrayAdapter.createFromResource(this,R.array.toweight, R.layout.spinner_item);

        adapterfromtemp = ArrayAdapter.createFromResource(this,R.array.fromtemp,R.layout.spinner_item);
        adaptertotemp = ArrayAdapter.createFromResource(this,R.array.totemp, R.layout.spinner_item);

        adapterfromspeed = ArrayAdapter.createFromResource(this,R.array.fromspeed, R.layout.spinner_item);
        adaptertospeed = ArrayAdapter.createFromResource(this,R.array.tospeed, R.layout.spinner_item);

        adapterfrompressure = ArrayAdapter.createFromResource(this,R.array.frompressure, R.layout.spinner_item);
        adaptertopressure = ArrayAdapter.createFromResource(this,R.array.topressure, R.layout.spinner_item);

        adapterfrompower = ArrayAdapter.createFromResource(this,R.array.frompower, R.layout.spinner_item);
        adaptertopower = ArrayAdapter.createFromResource(this,R.array.topower, R.layout.spinner_item);

        adapterfromwork = ArrayAdapter.createFromResource(this,R.array.fromwork, R.layout.spinner_item);
        adaptertowork = ArrayAdapter.createFromResource(this,R.array.towork, R.layout.spinner_item);

        adapterfromdata = ArrayAdapter.createFromResource(this,R.array.fromdata, R.layout.spinner_item);
        adaptertodata = ArrayAdapter.createFromResource(this,R.array.todata, R.layout.spinner_item);

        adapterfromenergy = ArrayAdapter.createFromResource(this,R.array.fromenergy, R.layout.spinner_item);
        adaptertoenergy = ArrayAdapter.createFromResource(this,R.array.toenergy, R.layout.spinner_item);

        adapterfromforce = ArrayAdapter.createFromResource(this,R.array.fromforce, R.layout.spinner_item);
        adaptertoforce = ArrayAdapter.createFromResource(this,R.array.toforce, R.layout.spinner_item);



        //edit text "enter" click
        enter.setText(enterValue);

        enter.setOnClickListener(
                new EditText.OnClickListener() {
                    public void onClick(View v) {

                        if(enter.getText().toString().matches(enterValue)){
                            enter.setText("");
                            enter.setSelection(enter.getText().length());

                        }else{

                        }
                        return;
                    }
                }
        );
        clear.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {

                        enter.setText("");
                        get.setText("");
                        end.setText("");
                        sample.setText("");
                    }
                });
        description();
        convert();

        back_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               onBackPressed();
            }
        });
    }

    public void convert(){
        calculate.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        if (enter.getText().toString().trim().isEmpty()) {
                            Toast.makeText(getApplicationContext(), enterValueWarning, Toast.LENGTH_SHORT).show();
                        } else {
                            if (settings_decimal == 0) {
                                df = new DecimalFormat("#");
                                ds = new DecimalFormat("#");
                            } else if (settings_decimal == 1) {
                                df = new DecimalFormat("#.#E0");
                                ds = new DecimalFormat("#.#");
                            } else if (settings_decimal == 2) {
                                df = new DecimalFormat("#.##E0");
                                ds = new DecimalFormat("#.##");
                            } else if (settings_decimal == 3) {
                                df = new DecimalFormat("#.###E0");
                                ds = new DecimalFormat("#.###");
                            } else if (settings_decimal == 4) {
                                df = new DecimalFormat("#.####E0");
                                ds = new DecimalFormat("#.####");
                            } else if (settings_decimal == 5) {
                                df = new DecimalFormat("#.#####E0");
                                ds = new DecimalFormat("#.#####");
                            } else if (settings_decimal == 6) {
                                df = new DecimalFormat("#.######E0");
                                df = new DecimalFormat("#.######");
                            } else if (settings_decimal == 7) {
                                df = new DecimalFormat("#.#######E0");
                                ds = new DecimalFormat("#.#######");
                            } else if (settings_decimal == 8) {
                                df = new DecimalFormat("#.########E0");
                                ds = new DecimalFormat("#.########");
                            } else if (settings_decimal == 9) {
                                df = new DecimalFormat("#.#########E0");
                                ds = new DecimalFormat("#.#########");
                            } else if (settings_decimal == 10) {
                                df = new DecimalFormat("#.##########E0");
                                ds = new DecimalFormat("#.##########");
                            } else if (settings_decimal == 11) {
                                df = new DecimalFormat("#.###########E0");
                                ds = new DecimalFormat("#.###########");
                            } else if (settings_decimal == 12) {
                                df = new DecimalFormat("#.############E0");
                                ds = new DecimalFormat("#.############");
                            }
                            if (enter.getText().toString().matches(enterValue)) {
                                enter.setText("");

                                enter.setSelection(enter.getText().length());

                            } else {
                                if (enter.getText().toString() == null || enter.getText().toString().trim().equals("")) {
                                    Toast.makeText(getApplicationContext(), enterValueWarning,
                                            Toast.LENGTH_SHORT).show();
                                } else if (enter.getText().toString().length() >= 13) {

                                } else if (enter.getText().toString().length() >= 2 && enter.getText().toString().charAt(1) == c && enter.getText().toString().charAt(0) == c) {

                                } else if (new String(Fahrenheit).equals(textFrom) && new String(Fahrenheit).equals(textTo)) {
                                    val = Double.parseDouble(enter.getText().toString());
                                    res = val * 1.0;
                                    sample.setText("");

                                } else if (new String(Fahrenheit).equals(textFrom) && new String(Celsius).equals(textTo)) {
                                    val = Double.parseDouble(enter.getText().toString());
                                    res = (val - 32) / 1.8;
                                    sample.setText("0" + " " + textFrom + " = " + "-17.77" + " " + textTo);

                                } else if (new String(Fahrenheit).equals(textFrom) && new String(Kelvin).equals(textTo)) {
                                    val = Double.parseDouble(enter.getText().toString());
                                    res = (val + 459.67) / 1.8;
                                    sample.setText("0" + " " + textFrom + "=" + " 255.3722222 " + " " + textTo);
                                } else if (new String(Celsius).equals(textFrom) && new String(Celsius).equals(textTo)) {
                                    val = Double.parseDouble(enter.getText().toString());
                                    res = val * 1.0;
                                    sample.setText("");

                                } else if (new String(Celsius).equals(textFrom) && new String(Kelvin).equals(textTo)) {
                                    val = Double.parseDouble(enter.getText().toString());
                                    res = val + 273.15;
                                    sample.setText("0" + " " + textFrom + " = " + "273.15" + " " + textTo);

                                } else if (new String(Celsius).equals(textFrom) && new String(Fahrenheit).equals(textTo)) {
                                    val = Double.parseDouble(enter.getText().toString());
                                    res = ((1.8) * val) + 32;
                                    sample.setText("0" + " " + textFrom + " = " + "32" + " " + textTo);

                                } else if (new String(Kelvin).equals(textFrom) && new String(Kelvin).equals(textTo)) {
                                    val = Double.parseDouble(enter.getText().toString());
                                    res = val * 1.0;
                                    sample.setText("");

                                } else if (new String(Kelvin).equals(textFrom) && new String(Fahrenheit).equals(textTo)) {
                                    val = Double.parseDouble(enter.getText().toString());
                                    res = ((val - 273.12) * 1.8) + 32;
                                    sample.setText("1" + " " + textFrom + " = " + " -459.616 " + " " + textTo);

                                } else if (new String(Kelvin).equals(textFrom) && new String(Celsius).equals(textTo)) {
                                    val = Double.parseDouble(enter.getText().toString());
                                    res = val - 273.15;
                                    sample.setText("0" + " " + textFrom + " = " + "-273.15" + " " + textTo);
                                    return;
                                }

                                ShortCuts shortcut = new ShortCuts();
                                textFrom = textFrom.replace('[', '-');
                                textFrom = textFrom.replace('/', '-');
                                textFrom = textFrom.replace(']', '-');
                                textFrom = textFrom.replace(' ', '-');
                                textFrom = textFrom.replace('.', '-');
                                textTo = textTo.replace('[', '-');
                                textTo = textTo.replace('/', '-');
                                textTo = textTo.replace(']', '-');
                                textTo = textTo.replace(' ', '-');
                                textTo = textTo.replace('.', '-');

                                textFrom = textFrom.replaceAll("-", "");
                                textTo = textTo.replaceAll("-", "");

                                switch (a) {
                                    case ConverterActivity.LENGTH:
                                        Length length = new Length();
                                        if (enter.getText().toString().matches(" ") || enter.getText().toString() == null || enter.getText().toString().trim().equals("")) {
                                            Toast.makeText(getApplicationContext(), enterValueWarning, Toast.LENGTH_SHORT).show();
                                        } else
                                            val = Double.parseDouble(enter.getText().toString());
                                        res = length.calculate(val, textFrom, textTo);
                                        if (!textFrom.equals(textTo))
                                            sample.setText("1" + " " + textFrom + " = " + df.format(length.getRatio(textFrom, textTo)) + " " + textTo);
                                        break;
                                    case ConverterActivity.AREA:
                                        Area area = new Area();
                                        if (enter.getText().toString().matches(" ") || enter.getText().toString() == null || enter.getText().toString().trim().equals("")) {
                                            Toast.makeText(getApplicationContext(), enterValueWarning, Toast.LENGTH_SHORT).show();
                                        } else
                                            val = Double.parseDouble(enter.getText().toString());

                                        res = area.calculate(val, textFrom, textTo);
                                        if (!textFrom.equals(textTo))
                                            sample.setText("1" + " " + textFrom + " = " + (area.getRatio(textFrom, textTo)) + " " + textTo);
                                        break;

                                    case ConverterActivity.VOLUME:
                                        Volume volume = new Volume();
                                        if (enter.getText().toString().matches(" ") || enter.getText().toString() == null || enter.getText().toString().trim().equals("")) {
                                            Toast.makeText(getApplicationContext(), enterValueWarning, Toast.LENGTH_SHORT).show();
                                        } else
                                            val = Double.parseDouble(enter.getText().toString());
                                        res = volume.calculate(val, textFrom, textTo);
                                        if (!textFrom.equals(textTo))
                                            sample.setText("1" + " " + textFrom + " = " + (volume.getRatio(textFrom, textTo)) + " " + textTo);
                                        break;

                                    case ConverterActivity.WEIGHT:
                                        Weight weight = new Weight();
                                        if (enter.getText().toString().matches(" ") || enter.getText().toString() == null || enter.getText().toString().trim().equals("")) {
                                            Toast.makeText(getApplicationContext(), enterValueWarning, Toast.LENGTH_SHORT).show();
                                        } else
                                            val = Double.parseDouble(enter.getText().toString());
                                        res = weight.calculate(val, textFrom, textTo);
                                        if (!textFrom.equals(textTo))
                                            sample.setText("1" + " " + textFrom + " = " + df.format(weight.getRatio(textFrom, textTo)) + " " + textTo);
                                        break;

                                    case ConverterActivity.SPEED:
                                        Speed speed = new Speed();
                                        if (enter.getText().toString().matches(" ") || enter.getText().toString() == null || enter.getText().toString().trim().equals("")) {
                                            Toast.makeText(getApplicationContext(), enterValueWarning, Toast.LENGTH_SHORT).show();
                                        } else
                                            val = Double.parseDouble(enter.getText().toString());
                                        res = speed.calculate(val, textFrom, textTo);
                                        if (!textFrom.equals(textTo))
                                            sample.setText("1" + " " + textFrom + " = " + (speed.getRatio(textFrom, textTo)) + " " + textTo);
                                        break;
                                    case ConverterActivity.PRESSURE:
                                        Pressure pressure = new Pressure();
                                        if (enter.getText().toString().matches(" ") || enter.getText().toString() == null || enter.getText().toString().trim().equals("")) {
                                            Toast.makeText(getApplicationContext(), enterValueWarning, Toast.LENGTH_SHORT).show();
                                        } else
                                            val = Double.parseDouble(enter.getText().toString());
                                        res = pressure.calculate(val, textFrom, textTo);
                                        if (!textFrom.equals(textTo))
                                            sample.setText("1" + " " + textFrom + " = " + df.format(pressure.getRatio(textFrom, textTo)) + " " + textTo);
                                        break;
                                    case ConverterActivity.POWER:
                                        Power power = new Power();
                                        if (enter.getText().toString().matches(" ") || enter.getText().toString() == null || enter.getText().toString().trim().equals("")) {
                                            Toast.makeText(getApplicationContext(), enterValueWarning, Toast.LENGTH_SHORT).show();
                                        } else
                                            val = Double.parseDouble(enter.getText().toString());
                                        res = power.calculate(val, textFrom, textTo);
                                        if (!textFrom.equals(textTo))
                                            sample.setText("1" + " " + textFrom + " = " + df.format(power.getRatio(textFrom, textTo)) + " " + textTo);
                                        break;

                                    case ConverterActivity.WORK:
                                        Work work = new Work();
                                        if (enter.getText().toString().matches(" ") || enter.getText().toString() == null || enter.getText().toString().trim().equals("")) {
                                            Toast.makeText(getApplicationContext(), enterValueWarning, Toast.LENGTH_SHORT).show();
                                        } else
                                            val = Double.parseDouble(enter.getText().toString());
                                        res = work.calculate(val, textFrom, textTo);
                                        if (!textFrom.equals(textTo))
                                            sample.setText("1" + " " + textFrom + " = " + (work.getRatio(textFrom, textTo)) + " " + textTo);
                                        break;
                                    case ConverterActivity.DATA:
                                        Data data = new Data();
                                        if (enter.getText().toString().matches(" ") || enter.getText().toString() == null || enter.getText().toString().trim().equals("")) {
                                            Toast.makeText(getApplicationContext(), enterValueWarning, Toast.LENGTH_SHORT).show();
                                        } else
                                            val = Double.parseDouble(enter.getText().toString());
                                        res = data.calculate(val, textFrom, textTo);
                                        if (!textFrom.equals(textTo))
                                            sample.setText("1" + " " + textFrom + " = " + (data.getRatio(textFrom, textTo)) + " " + textTo);
                                        break;
                                    case ConverterActivity.ENERGY:
                                        Energy energy = new Energy();
                                        if (enter.getText().toString().matches(" ") || enter.getText().toString() == null || enter.getText().toString().trim().equals("")) {
                                            Toast.makeText(getApplicationContext(), enterValueWarning, Toast.LENGTH_SHORT).show();
                                        } else
                                            val = Double.parseDouble(enter.getText().toString());
                                        res = energy.calculate(val, textFrom, textTo);
                                        if (!textFrom.equals(textTo))
                                            sample.setText("1" + " " + textFrom + " = " + df.format(energy.getRatio(textFrom, textTo)) + " " + textTo);
                                        break;
                                    case ConverterActivity.Force:
                                        Force force = new Force();
                                        if (enter.getText().toString().matches(" ") || enter.getText().toString() == null || enter.getText().toString().trim().equals("")) {
                                            Toast.makeText(getApplicationContext(), enterValueWarning, Toast.LENGTH_SHORT).show();
                                        } else
                                            val = Double.parseDouble(enter.getText().toString());
                                        res = force.calculate(val, textFrom, textTo);
                                        if (!textFrom.equals(textTo))
                                            sample.setText("1" + " " + textFrom + " = " + df.format(force.getRatio(textFrom, textTo)) + " " + textTo);
                                        break;

                                    // diger caseleri buraya siralarsiniz

                                    default:
                                        break;
                                }
                                if (textTo.equals(textFrom))
                                    sample.setText("");

                                if (val > 1000 || val < 0.001) {

                                    eChangerFrom = df.format(val);

                                    try {
                                        eChangerFrom = eChangerFrom.replaceAll("E", " x 10#");

                                        String[] parts = eChangerFrom.split("#");
                                        String from1 = parts[0];
                                        String from2 = parts[1];

                                        get.setText(from1 + "^" + from2 + " " + shortcut.getShortCutMap().get(textFrom));

                                    } catch (Exception e) {

                                        get.setText(df.format(val) + " " + shortcut.getShortCutMap().get(textFrom));

                                    }


                                } else if (val <= 1000 || val >= 0.001 || val == 0) {


                                    get.setText(ds.format(val) + " " + shortcut.getShortCutMap().get(textFrom));

                                }

                                if (res > 1000 || res < 0.001) {

                                    eChangerTo = df.format(res);

                                    try {


                                        eChangerTo = eChangerTo.replaceAll("E", " x 10#");

                                        String[] parts = eChangerTo.split("#");
                                        String to1 = parts[0];
                                        String to2 = parts[1];

                                        end.setText(to1 + "^" + to2 + " " + shortcut.getShortCutMap().get(textTo));

                                    } catch (Exception e) {


                                        end.setText((res) + " " + shortcut.getShortCutMap().get(textTo));

                                    }

                                } else if (res <= 1000 || res >= 0.001 || res == 0) {


                                    end.setText((res) + " " + shortcut.getShortCutMap().get(textTo));


                                }
                            }


                            InputMethodManager inputManager = (InputMethodManager)
                                    getSystemService(Context.INPUT_METHOD_SERVICE);

                            inputManager.hideSoftInputFromWindow(Objects.requireNonNull(getCurrentFocus()).getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                        }
                    }

                });
    }

    // ifler ve adapter choose

    public void description(){

        if(a==ConverterActivity.LENGTH){to.setAdapter(adaptertolength);
            to.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                    textTo = to.getSelectedItem().toString();
                }


                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            from.setAdapter(adapterfromlength);
            from.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                    textFrom = from.getSelectedItem().toString();
                }


                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });} else if(a==ConverterActivity.AREA){to.setAdapter(adaptertoarea);
            to.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                    textTo = to.getSelectedItem().toString();
                }


                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            from.setAdapter(adapterfromarea);
            from.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                    textFrom = from.getSelectedItem().toString();
                }


                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });} else if(a==ConverterActivity.VOLUME){to.setAdapter(adaptertovolume);
            to.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                    textTo = to.getSelectedItem().toString();
                }


                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            from.setAdapter(adapterfromvolume);
            from.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                    textFrom = from.getSelectedItem().toString();
                }


                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });} else if(a==ConverterActivity.WEIGHT){to.setAdapter(adaptertoweight);
            to.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                    textTo = to.getSelectedItem().toString();
                }


                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            from.setAdapter(adapterfromweight);
            from.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                    textFrom = from.getSelectedItem().toString();
                }


                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });}
        else if(a==ConverterActivity.TEMPERATURE){to.setAdapter(adaptertotemp);
            to.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                    textTo = to.getSelectedItem().toString();
                }


                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            from.setAdapter(adapterfromtemp);
            from.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                    textFrom = from.getSelectedItem().toString();
                }


                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });} else if(a==ConverterActivity.SPEED){to.setAdapter(adaptertospeed);
            to.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                    textTo = to.getSelectedItem().toString();
                }


                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            from.setAdapter(adapterfromspeed);
            from.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                    textFrom = from.getSelectedItem().toString();
                }


                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });} else if(a==ConverterActivity.PRESSURE){to.setAdapter(adaptertopressure);
            to.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                    textTo = to.getSelectedItem().toString();
                }


                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            from.setAdapter(adapterfrompressure);
            from.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                    textFrom = from.getSelectedItem().toString();
                }


                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });} else if(a==ConverterActivity.POWER){to.setAdapter(adaptertopower);
            to.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                    textTo = to.getSelectedItem().toString();
                }


                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            from.setAdapter(adapterfrompower);
            from.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    textFrom = from.getSelectedItem().toString();
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });} else if(a==ConverterActivity.WORK){to.setAdapter(adaptertowork);
            to.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                    textTo = to.getSelectedItem().toString();
                }


                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            from.setAdapter(adapterfromwork);
            from.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                    textFrom = from.getSelectedItem().toString();
                }


                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });} else if(a==ConverterActivity.DATA){to.setAdapter(adaptertodata);
            to.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                    textTo = to.getSelectedItem().toString();
                }


                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            from.setAdapter(adapterfromdata);
            from.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                    textFrom = from.getSelectedItem().toString();
                }


                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });}


        else if(a==ConverterActivity.ENERGY){to.setAdapter(adaptertoenergy);
            to.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                    textTo = to.getSelectedItem().toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            from.setAdapter(adapterfromenergy);
            from.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                    textFrom = from.getSelectedItem().toString();
                }


                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });} else if(a==ConverterActivity.Force){to.setAdapter(adaptertoforce);
            to.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                    textTo = to.getSelectedItem().toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            from.setAdapter(adapterfromforce);
            from.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                    textFrom = from.getSelectedItem().toString();
                }


                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });}

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {


    }

    @Override
    public void afterTextChanged(Editable s) {


    }

    public Context getActivity() {
        return activity;
    }


    public void onBackPressed() {
        super.onBackPressed();

    }
}
