package com.gps.tools.speedometer.area.calculator.Activities.Converter;

public class Speed {

    public enum SpeedRatio{

        Metrepersecond(1),
        Kilometreperhour(0.27778),
        Mileperhour(0.4470272687),
        Knot(0.5144038922);


        private final double ratio;
        SpeedRatio(double ratio){
            this.ratio = ratio;
        }
        private double getRatio(){
            return ratio;
        }
    }

    public Speed(){

    }

    public double calculate(double value, String from, String to){

        double kat = getRatio(from, to);
        return value * kat;
    }

    public double getRatio(String from, String to){
        return SpeedRatio.valueOf(from).getRatio() / SpeedRatio.valueOf(to).getRatio();
    }


}
