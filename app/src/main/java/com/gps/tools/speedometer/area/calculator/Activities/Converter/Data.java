package com.gps.tools.speedometer.area.calculator.Activities.Converter;

public class Data {

    public enum DataRatio{

        bit(0.125),
        Byte(1),
        KiloByte(1000),
        MegaByte(1000000),
        GigaByte(1*Math.pow(10,9)),
        TeraByte(1*Math.pow(10,12));

        private final double ratio;
        DataRatio(double ratio){
            this.ratio = ratio;
        }
        private double getRatio(){
            return ratio;
        }
    }

    public Data(){

    }

    public double calculate(double value, String from, String to){

        double kat = getRatio(from, to);
        return value * kat;
    }

    public double getRatio(String from, String to){
        return DataRatio.valueOf(from).getRatio() / DataRatio.valueOf(to).getRatio();
    }


}
