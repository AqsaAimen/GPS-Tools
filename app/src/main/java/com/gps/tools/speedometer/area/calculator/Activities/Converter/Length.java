package com.gps.tools.speedometer.area.calculator.Activities.Converter;

public class Length {

    public enum LengthRatio{
        Millimeter(1),
        Centimeter(10),
        Inch(25.4),
        Feet(304.8),
        Yard(914.4),
        Meter(1000),
        Kilometer(1000000),
        Mile(1609000);

        private final double ratio;
        LengthRatio(double ratio){
            this.ratio = ratio;
        }
        private double getRatio(){
            return ratio;
        }
    }

    public Length(){

    }

    public double calculate(double value, String from, String to){

        double kat = getRatio(from, to);
        return value * kat;
    }

    public double getRatio(String from, String to){
        return (double) LengthRatio.valueOf(from).getRatio() / (double) LengthRatio.valueOf(to).getRatio();
    }


}
