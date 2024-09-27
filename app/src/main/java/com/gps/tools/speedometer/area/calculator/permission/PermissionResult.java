package com.gps.tools.speedometer.area.calculator.permission;

public interface PermissionResult {
    void permissionDenied();

    void permissionForeverDenied();

    void permissionGranted();
}
