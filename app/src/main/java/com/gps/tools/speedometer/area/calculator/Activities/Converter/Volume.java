package com.gps.tools.speedometer.area.calculator.Activities.Converter;

public class Volume {

    public enum VolumeRatio{

        Decilitre(1),
        Millilitre(0.01),
        Litre(10),
        Cubecentimeter(0.01),
        Cubemeter(10000),
        Cubeinch(0.1638806949),
        Cubefoot(250),
        Cubeyard(1000),
        Gallon_UK(45.4545),
        Gallon_US(38.4615384615);

        private final double ratio;
        VolumeRatio(double ratio){
            this.ratio = ratio;
        }
        private double getRatio(){
            return ratio;
        }
    }

    public Volume(){

    }

    public double calculate(double value, String from, String to){

        double kat = getRatio(from, to);
        return value * kat;
    }

    public double getRatio(String from, String to){
        return VolumeRatio.valueOf(from).getRatio() / VolumeRatio.valueOf(to).getRatio();
    }


}
