package com.gps.tools.speedometer.area.calculator.Model;


import android.os.Parcel;
import android.os.Parcelable;

public class SpeedoMeterHistory implements Parcelable {

    int id;
    String  startTime,startDate, endTime,endDate,distance, timeToDrive, maxSpeed, avgSpeed,startLat,startLng,endLat,endLng;

    public SpeedoMeterHistory()
    {

    }

    public SpeedoMeterHistory(int id, String startTime, String startDate, String endTime, String endDate, String distance, String timeToDrive, String maxSpeed, String avgSpeed, String startLat, String startLng, String endLat, String endLng) {
        this.id = id;
        this.startTime = startTime;
        this.startDate = startDate;
        this.endTime = endTime;
        this.endDate = endDate;
        this.distance = distance;
        this.timeToDrive = timeToDrive;
        this.maxSpeed = maxSpeed;
        this.avgSpeed = avgSpeed;
        this.startLat = startLat;
        this.startLng = startLng;
        this.endLat = endLat;
        this.endLng = endLng;
    }

    protected SpeedoMeterHistory(Parcel in) {
        id = in.readInt();
        startTime = in.readString();
        startDate = in.readString();
        endTime = in.readString();
        endDate = in.readString();
        distance = in.readString();
        timeToDrive = in.readString();
        maxSpeed = in.readString();
        avgSpeed = in.readString();
        startLat = in.readString();
        startLng = in.readString();
        endLat = in.readString();
        endLng = in.readString();
    }

    public static final Creator<SpeedoMeterHistory> CREATOR = new Creator<SpeedoMeterHistory>() {
        @Override
        public SpeedoMeterHistory createFromParcel(Parcel in) {
            return new SpeedoMeterHistory(in);
        }

        @Override
        public SpeedoMeterHistory[] newArray(int size) {
            return new SpeedoMeterHistory[size];
        }
    };

    public String getStartLat() {
        return startLat;
    }

    public void setStartLat(String startLat) {
        this.startLat = startLat;
    }

    public String getStartLng() {
        return startLng;
    }

    public void setStartLng(String startLng) {
        this.startLng = startLng;
    }

    public String getEndLat() {
        return endLat;
    }

    public void setEndLat(String endLat) {
        this.endLat = endLat;
    }

    public String getEndLng() {
        return endLng;
    }

    public void setEndLng(String endLng) {
        this.endLng = endLng;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTimeToDrive() {
        return timeToDrive;
    }

    public void setTimeToDrive(String timeToDrive) {
        this.timeToDrive = timeToDrive;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(String maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public String getAvgSpeed() {
        return avgSpeed;
    }

    public void setAvgSpeed(String avgSpeed) {
        this.avgSpeed = avgSpeed;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(startTime);
        parcel.writeString(startDate);
        parcel.writeString(endTime);
        parcel.writeString(endDate);
        parcel.writeString(distance);
        parcel.writeString(timeToDrive);
        parcel.writeString(maxSpeed);
        parcel.writeString(avgSpeed);
        parcel.writeString(startLat);
        parcel.writeString(startLng);
        parcel.writeString(endLat);
        parcel.writeString(endLng);
    }
}
