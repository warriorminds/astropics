package com.warriorminds.astropics.retrofit;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rodrigo.guerrero on 6/24/2015.
 */
public class ImageOfTheDay {
    private String date;
    private String image;
    @SerializedName("resource_uri")
    private String resourceUri;
    @SerializedName("runnerup_1")
    private String firstRunnerup;
    @SerializedName("runnerup_2")
    private String secondRunnerup;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public String getResourceUri() {
        return resourceUri;
    }

    public void setResourceUri(String resourceUri) {
        this.resourceUri = resourceUri;
    }

    public String getFirstRunnerup() {
        return firstRunnerup;
    }

    public void setFirstRunnerup(String firstRunnerup) {
        this.firstRunnerup = firstRunnerup;
    }

    public String getSecondRunnerup() {
        return secondRunnerup;
    }

    public void setSecondRunnerup(String secondRunnerup) {
        this.secondRunnerup = secondRunnerup;
    }
}
