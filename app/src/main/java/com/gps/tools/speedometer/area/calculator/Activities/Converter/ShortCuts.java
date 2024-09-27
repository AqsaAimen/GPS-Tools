package com.gps.tools.speedometer.area.calculator.Activities.Converter;

import java.util.HashMap;


public class ShortCuts {

    private HashMap<String, String> shortCutMap;
    public ShortCuts(){
        shortCutMap = new HashMap<String, String>();
        shortCutMap.put("Millimeter", "mm");
        shortCutMap.put("Centimeter", "cm");
        shortCutMap.put("Meter", "m");
        shortCutMap.put("Yard", "yr");
        shortCutMap.put("Inch", "in");
        shortCutMap.put("Kilometer", "km");
        shortCutMap.put("Feet", "ft");
        shortCutMap.put("Mile", "ml");

        //Force
        shortCutMap.put("Dyne", "dyn");
        shortCutMap.put("Meganewton", "MN");
        shortCutMap.put("Kip", "kip");
        shortCutMap.put("Newton", "N");
        shortCutMap.put("TonForceMetric", "tf");
        shortCutMap.put("PoundForce", "lbf");
        shortCutMap.put("Poundal", "pdl");
        shortCutMap.put("Sthene", "sn");
        shortCutMap.put("KilogramForce", "kgf");
        shortCutMap.put("TonForce", "tf");

        //Time
        shortCutMap.put("Nanosecond", "ns");
        shortCutMap.put("Microsecond", "us");
        shortCutMap.put("Millisecond", "ms");
        shortCutMap.put("Second", "s");
        shortCutMap.put("Minute", "min");
        shortCutMap.put("Quadrans", "q");
        shortCutMap.put("Hour", "h");
        shortCutMap.put("Day", "D");
        shortCutMap.put("Week", "W");
        shortCutMap.put("Month30Days", "M");
        shortCutMap.put("Year", "Y");
        shortCutMap.put("Century", "C");
        shortCutMap.put("Millennium", "M");
        //Pressure
        shortCutMap.put("Pascal", "Pa");
        shortCutMap.put("Hectopascal", "hPa");
        shortCutMap.put("Kilopascal", "kPa");
        shortCutMap.put("Megapascal", "MPa");
        shortCutMap.put("Atmosphere", "bar");
        shortCutMap.put("Torr", "Tr");
        shortCutMap.put("Millimetersofmercury", "mmHg");
        shortCutMap.put("Psi", "lbf/in^2");
        shortCutMap.put("Newtonpersquaremillimeter", "N/mm^2");
        shortCutMap.put("Kilogrampersquaremeter", "K/m^2");
        shortCutMap.put("Bar", "bar");
        //Energy
        shortCutMap.put("Joule", "J");
        shortCutMap.put("Kilojoule", "kJ");
        shortCutMap.put("Kilogrammeter", "kGm");
        shortCutMap.put("Watthour", "Wh");
        shortCutMap.put("Kilowatthour", "kWh");
        shortCutMap.put("Erg", "erg");
        shortCutMap.put("Calorie", "cal");
        shortCutMap.put("Kilocalorie", "kcal");
        shortCutMap.put("FootPoundal", "ft pdl");
        shortCutMap.put("InchPoundForce", "in lbf");
        shortCutMap.put("FootPoundForce", "ft lbf");
        shortCutMap.put("HorsepowerHour", "hph");
        shortCutMap.put("BTU", "BTU");
        shortCutMap.put("Electronvolt", "eV");
        shortCutMap.put("Hartree", "Eh");
        shortCutMap.put("Rydberg", "Ry");
        //Temperature
        shortCutMap.put("Fahrenheit", "F");
        shortCutMap.put("Celsius", "C");
        shortCutMap.put("Kelvin", "K");
        //weight
        shortCutMap.put("Milligram", "mg");
        shortCutMap.put("Gram", "g");
        shortCutMap.put("Decagram", "dag");
        shortCutMap.put("Kilogram", "kg");
        shortCutMap.put("Metric tonne", "T");
        shortCutMap.put("Carat", "ct");
        shortCutMap.put("Quintal", "q");
        shortCutMap.put("AtomicMassUnit", "amu");
        shortCutMap.put("PlanckMass", "Pm");
        shortCutMap.put("Ounce", "oz");
        shortCutMap.put("Pound", "lb");
        shortCutMap.put("Stone", "s");
        shortCutMap.put("USHundredweight", "cwt");
        shortCutMap.put("ImpHundredweight", "cwt");
        shortCutMap.put("ShortTon", "S/T");
        shortCutMap.put("LongTon", "L/T");
        shortCutMap.put("Grain", "gr");
        //power
        shortCutMap.put("Attowatt", "aW");
        shortCutMap.put("CalorieHour", "cal/h");
        shortCutMap.put("Centiwatt", "cW");
        shortCutMap.put("ErgHour", "erg/h");
        shortCutMap.put("Gigawatt", "GW");
        shortCutMap.put("Hectowatt", "hW");
        shortCutMap.put("Horsepower", "hp");
        shortCutMap.put("Kilowatt", "kW");
        shortCutMap.put("Megawatt", "MW");
        shortCutMap.put("Watt", "W");


        //area
        shortCutMap.put("Squarecentimeter", "cm^2");
        shortCutMap.put("Squaremetre", "m^2");
        shortCutMap.put("Squarekilometre", "km^2");
        shortCutMap.put("Hectare", "Ha");
        shortCutMap.put("Squareinch", "In^2");
        shortCutMap.put("Squarefoot", "ft^2");
        shortCutMap.put("Squareyard", "yd^2");


        //volume
        shortCutMap.put("Decilitre", "dl");
        shortCutMap.put("Millilitre", "ml");
        shortCutMap.put("Litre", "l");
        shortCutMap.put("Cubecentimeter", "cm^3");
        shortCutMap.put("Cubemeter", "m^3");
        shortCutMap.put("Cubeinch", "i^3");
        shortCutMap.put("Cubefoot", "ft^3");
        shortCutMap.put("Cubeyard", "yd^3");
        shortCutMap.put("Gallon_UK", "gal(UK)");
        shortCutMap.put("Gallon_US", "gal(US)");

        //speed
        shortCutMap.put("Metrepersecond", "m/s");
        shortCutMap.put("Kilometreperhour", "km/h");
        shortCutMap.put("Mileperhour", "mp/h");
        shortCutMap.put("Knot", "knot");


        //work
        shortCutMap.put("Joule", "J");
        shortCutMap.put("KiloJoule", "KJ");
        shortCutMap.put("Kilowattperhour", "Kwh");
        shortCutMap.put("Calorie", "cal");
        shortCutMap.put("Kilocalorie", "kcal");


        //data
        shortCutMap.put("bit", "bit(b)");
        shortCutMap.put("Byte", "Byte(B)");
        shortCutMap.put("KiloByte", "KB");
        shortCutMap.put("MegaByte", "MB");
        shortCutMap.put("GigaByte", "GB");
        shortCutMap.put("TeraByte", "TB");


    }

    public HashMap<String, String> getShortCutMap(){
        return shortCutMap;
    }

}
