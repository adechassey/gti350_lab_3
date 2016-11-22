package com.company.meetsports.Entities;

import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;

public class Event {

    @SerializedName("id_event")
    private Integer id_event;
    @SerializedName("category")
    private String category;
    @SerializedName("type")
    private String type;
    @SerializedName("date_time")
    private String date_time;
    @SerializedName("minDuration")
    private Double minDuration;
    @SerializedName("maxDuration")
    private Double maxDuration;
    @SerializedName("minParticipants")
    private Integer minParticipants;
    @SerializedName("maxParticipants")
    private Integer maxParticipants;
    @SerializedName("minAge")
    private Integer minAge;
    @SerializedName("maxAge")
    private Integer maxAge;
    @SerializedName("minContribution")
    private Integer minContribution;
    @SerializedName("maxContribution")
    private Integer maxContribution;
    @SerializedName("level")
    private String level;
    @SerializedName("latitude")
    private Double latitude;
    @SerializedName("longitude")
    private Double longitude;

    public Event(Integer id_event, String category, String type, String date_time, Double minDuration, Double maxDuration,
                 Integer minParticipants, Integer maxParticipants, Integer minAge, Integer maxAge, Integer minContribution,
                 Integer maxContribution, String level, Double latitude, Double longitude) {
        super();
        this.id_event = id_event;
        this.category = category;
        this.type = type;
        this.date_time = date_time;
        this.minDuration = minDuration;
        this.maxDuration = maxDuration;
        this.minParticipants = minParticipants;
        this.maxParticipants = maxParticipants;
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.minContribution = minContribution;
        this.maxContribution = maxContribution;
        this.level = level;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Integer getId_event() {
        return id_event;
    }

    public void setId_event(Integer id_event) {
        this.id_event = id_event;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public Double getMinDuration() {
        return minDuration;
    }

    public void setMinDuration(Double minDuration) {
        this.minDuration = minDuration;
    }

    public Double getMaxDuration() {
        return maxDuration;
    }

    public void setMaxDuration(Double maxDuration) {
        this.maxDuration = maxDuration;
    }

    public Integer getMinParticipants() {
        return minParticipants;
    }

    public void setMinParticipants(Integer minParticipants) {
        this.minParticipants = minParticipants;
    }

    public Integer getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(Integer maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public Integer getMinAge() {
        return minAge;
    }

    public void setMinAge(Integer minAge) {
        this.minAge = minAge;
    }

    public Integer getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
    }

    public Integer getMinContribution() {
        return minContribution;
    }

    public void setMinContribution(Integer minContribution) {
        this.minContribution = minContribution;
    }

    public Integer getMaxContribution() {
        return maxContribution;
    }

    public void setMaxContribution(Integer maxContribution) {
        this.maxContribution = maxContribution;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}