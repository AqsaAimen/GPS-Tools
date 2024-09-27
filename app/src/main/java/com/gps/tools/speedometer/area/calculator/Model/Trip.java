package com.gps.tools.speedometer.area.calculator.Model;

public class Trip {
    private int tripID;
    private String speedLimit,destination;

    public Trip()
    {

    }

    public Trip(int tripID, String speedLimit, String destination) {
        this.tripID = tripID;
        this.speedLimit = speedLimit;
        this.destination = destination;
    }

    public int getTripID() {
        return tripID;
    }

    public void setTripID(int tripID) {
        this.tripID = tripID;
    }

    public String getSpeedLimit() {
        return speedLimit;
    }

    public void setSpeedLimit(String speedLimit) {
        this.speedLimit = speedLimit;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
