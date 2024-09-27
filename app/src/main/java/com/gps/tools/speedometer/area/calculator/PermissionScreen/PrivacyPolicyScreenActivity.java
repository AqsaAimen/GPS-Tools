package com.gps.tools.speedometer.area.calculator.PermissionScreen;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.gps.tools.speedometer.area.calculator.Activities.MainActivity;
import com.gps.tools.speedometer.area.calculator.R;


public class PrivacyPolicyScreenActivity extends AppCompatActivity {
    Button next;
    CheckBox confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_privacy_policy_screen);
        next=findViewById(R.id.next_btn);
        confirm=findViewById(R.id.check);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(confirm.isChecked())
                {
                    //...... move to permissions screen
                    setDataToSP();
                    Intent homeIntent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(homeIntent);
                    finish();
                }
                else
                {
                    //...... toast for accept privacy policy
                    Toast.makeText(PrivacyPolicyScreenActivity.this, "Please Check Agree Button", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void setDataToSP()
    {
        PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean("my_pref",true).apply();
    }

}
