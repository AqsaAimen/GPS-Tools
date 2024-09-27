package com.gps.tools.speedometer.area.calculator.Activities.Converter;

public class Area {

    public enum AreaRatio{

        Squarecentimeter(0.0001),
        Squaremetre(1),
        Squarekilometre(1000000),
        Hectare(10000),
        Squareinch(0.00064516127),
        Squarefoot(0.09291),
        Squareyard(0.836127);

        private final double ratio;
        AreaRatio(double ratio){
            this.ratio = ratio;
        }
        private double getRatio(){
            return ratio;
        }
    }

    public Area(){

    }

    public double calculate(double value, String from, String to){

        double kat = getRatio(from, to);
        return value * kat;
    }

    public double getRatio(String from, String to){
        return AreaRatio.valueOf(from).getRatio() / AreaRatio.valueOf(to).getRatio();
    }


}
