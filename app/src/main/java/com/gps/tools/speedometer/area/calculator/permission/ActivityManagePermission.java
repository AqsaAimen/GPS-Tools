package com.gps.tools.speedometer.area.calculator.permission;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.LinkedList;

public class ActivityManagePermission extends AppCompatActivity {
    private final int KEY_PERMISSION = 200;
    private PermissionResult permissionResult;
    private String[] permissionsAsk;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    public boolean isPermissionGranted(Context context, String str) {
        return VERSION.SDK_INT < 23 || ContextCompat.checkSelfPermission(context, str) == 0;
    }

    public boolean isPermissionsGranted(Context context, String[] strArr) {
        if (VERSION.SDK_INT < 23) {
            return true;
        }
        boolean z = true;
        for (String checkSelfPermission : strArr) {
            if (ActivityCompat.checkSelfPermission(context, checkSelfPermission) != 0) {
                z = false;
            }
        }
        return z;
    }

    private void internalRequestPermission(String[] strArr) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < strArr.length; i++) {
            if (!isPermissionGranted(this, strArr[i])) {
                arrayList.add(strArr[i]);
            }
        }
        if (!arrayList.isEmpty()) {
            ActivityCompat.requestPermissions(this, (String[]) arrayList.toArray(new String[arrayList.size()]), 200);
        } else if (this.permissionResult != null) {
            this.permissionResult.permissionGranted();
        }
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        if (i == 200) {
            LinkedList<String> linkedList = new LinkedList<>();
            boolean z = true;
            for (int i2 = 0; i2 < iArr.length; i2++) {
                if (iArr[i2] != 0) {
                    linkedList.add(strArr[i2]);
                    z = false;
                }
            }
            if (this.permissionResult != null) {
                if (z) {
                    this.permissionResult.permissionGranted();
                } else {
                    for (String shouldShowRequestPermissionRationale : linkedList) {
                        if (!ActivityCompat.shouldShowRequestPermissionRationale(this, shouldShowRequestPermissionRationale)) {
                            this.permissionResult.permissionForeverDenied();
                            return;
                        }
                    }
                    this.permissionResult.permissionDenied();
                }
            }
        }
    }

    public void askCompactPermission(String str, PermissionResult permissionResult2) {
        this.permissionsAsk = new String[]{str};
        this.permissionResult = permissionResult2;
        internalRequestPermission(this.permissionsAsk);
    }

    public void askCompactPermissions(String[] strArr, PermissionResult permissionResult2) {
        this.permissionsAsk = strArr;
        this.permissionResult = permissionResult2;
        internalRequestPermission(this.permissionsAsk);
    }

    public void openSettingsApp(Context context) {
        if (VERSION.SDK_INT >= 9) {
            Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
            StringBuilder sb = new StringBuilder();
            sb.append("package:");
            sb.append(context.getPackageName());
            intent.setData(Uri.parse(sb.toString()));
            startActivity(intent);
        }
    }
}