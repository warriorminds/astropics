package com.warriorminds.astropics.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by rodrigo.guerrero on 6/24/2015.
 */
public class ImageOfTheDayResult {
    @SerializedName("meta")
    private Meta resultInfo;
    @SerializedName("objects")
    private ArrayList<ImageOfTheDay> imagesList;

    public Meta getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(Meta resultInfo) {
        this.resultInfo = resultInfo;
    }

    public ArrayList<ImageOfTheDay> getImagesList() {
        return imagesList;
    }

    public void setImagesList(ArrayList<ImageOfTheDay> imagesList) {
        this.imagesList = imagesList;
    }
}
