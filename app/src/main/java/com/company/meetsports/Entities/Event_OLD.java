package com.company.meetsports.Entities;

/**
 * Created by VMabille on 05/11/2016.
 */

public class Event_OLD {
    private Integer id;
    private String category;
    private String type;
    private String date;
    private String duration;
    private String distance;
    private String place;
    private String address;


    public Event_OLD(Integer id, String category, String type, String date, String duration, String distance, String place, String address) {
        this.id = id;
        this.category = category;
        this.type = type;
        this.date = date;
        this.duration = duration;
        this.distance = distance;
        this.place = place;
        this.address = address;
    }

    public Integer getId() { return id; }

    public String getCategory() {
        return category;
    }

    public String getType() { return type; }

    public String getDate() { return date; }

    public String getDuration() {return duration; }

    public String getDistance() {
        return distance;
    }

    public String getPlace() { return place; }

    public String getAddress() { return address; }

    public void setCategory(String Category) { category = Category; }

    public void setType(String Type) { type = Type; }

    public void setDate(String Date) { date = Date; }

    public void setDuration(String Duration) { duration = Duration; }

    public void setDistance(String Distance) { distance = distance; }

    public void setPlace(String Place) { place = Place; }

    public void setAddress(String Address) { address = Address; }




}
