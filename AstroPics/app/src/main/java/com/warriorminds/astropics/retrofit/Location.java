package com.warriorminds.astropics.retrofit;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rodrigo.guerrero on 6/24/2015.
 */
public class Location {
    private int altitude;
    private String city;
    private String country;
    private String name;
    @SerializedName("resource_uri")
    private String resourceUri;
    private String state;

    public int getAltitude() {
        return altitude;
    }

    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getResourceUri() {
        return resourceUri;
    }

    public void setResourceUri(String resourceUri) {
        this.resourceUri = resourceUri;
    }
}
