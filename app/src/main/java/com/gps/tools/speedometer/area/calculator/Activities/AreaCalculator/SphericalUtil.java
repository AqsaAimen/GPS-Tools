package com.gps.tools.speedometer.area.calculator.Activities.AreaCalculator;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class SphericalUtil {
    private static final double EARTH_RADIUS = 6371000.0d;

    private SphericalUtil() {
    }

    private static double distanceRadians(double d, double d2, double d3, double d4) {
        return MathUtil.arcHav(MathUtil.havDistance(d, d3, d2 - d4));
    }

    private static double computeAngleBetween(LatLng latLng, LatLng latLng2) {
        return distanceRadians(Math.toRadians(latLng.latitude), Math.toRadians(latLng.longitude), Math.toRadians(latLng2.latitude), Math.toRadians(latLng2.longitude));
    }

    public static double computeDistanceBetween(LatLng latLng, LatLng latLng2) {
        return computeAngleBetween(latLng, latLng2) * EARTH_RADIUS;

    }

    public static double computeDistance(LatLng p1, LatLng p2) {
        return Math.sqrt(Math.pow(p1.latitude - p2.longitude, 2) + Math.pow(p1.latitude - p2.longitude, 2));
    }


    public static double computeArea(List<LatLng> list) {
        return Math.abs(computeSignedArea(list));
    }

    private static double computeSignedArea(List<LatLng> list) {
        int size = list.size();
        double d = 0.0d;
        if (size < 3) {
            return 0.0d;
        }
        LatLng latLng = (LatLng) list.get(size - 1);
        double tan = Math.tan((1.5707963267948966d - Math.toRadians(latLng.latitude)) / 2.0d);
        double radians = Math.toRadians(latLng.longitude);
        double d2 = tan;
        double d3 = radians;
        for (LatLng latLng2 : list) {
            double tan2 = Math.tan((1.5707963267948966d - Math.toRadians(latLng2.latitude)) / 2.0d);
            double radians2 = Math.toRadians(latLng2.longitude);
            d += polarTriangleArea(tan2, radians2, d2, d3);
            d2 = tan2;
            d3 = radians2;
        }
        return d * 4.0589641E13d;
    }

    private static double polarTriangleArea(double d, double d2, double d3, double d4) {
        double d5 = d2 - d4;
        double d6 = d * d3;
        return Math.atan2(Math.sin(d5) * d6, (d6 * Math.cos(d5)) + 1.0d) * 2.0d;
    }

}
