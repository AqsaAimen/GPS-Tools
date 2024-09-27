package com.gps.tools.speedometer.area.calculator.Activities.Converter;

public class Work {

    public enum WorkRatio{

        Joule(1),
        KiloJoule(1000),
        Kilowattperhour(3600000),
        Calorie(4.1868),
        Kilocalorie(4186.8);

        private final double ratio;
        WorkRatio(double ratio){
            this.ratio = ratio;
        }
        private double getRatio(){
            return ratio;
        }
    }

    public Work(){

    }

    public double calculate(double value, String from, String to){

        double kat = getRatio(from, to);
        return value * kat;
    }

    public double getRatio(String from, String to){
        return WorkRatio.valueOf(from).getRatio() / WorkRatio.valueOf(to).getRatio();
    }


}
