package com.gps.tools.speedometer.area.calculator.DataValidity;

public class DataValidity {

        public static boolean isValidString(String string)
        {
            boolean result=true;
            if(string.equalsIgnoreCase("") || (string==null))
            {
                result=false;
            }
            return result;
        }
}
