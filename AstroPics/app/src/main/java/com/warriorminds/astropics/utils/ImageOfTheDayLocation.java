package com.warriorminds.astropics.utils;

import com.warriorminds.astropics.retrofit.Image;
import com.warriorminds.astropics.retrofit.ImageOfTheDay;
import com.warriorminds.astropics.retrofit.Location;

/**
 * Created by rodrigo.guerrero on 7/21/2015.
 */
public class ImageOfTheDayLocation {
    private Image image;
    private Location location;
    private ImageOfTheDay imageOfTheDay;


    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public ImageOfTheDay getImageOfTheDay() {
        return imageOfTheDay;
    }

    public void setImageOfTheDay(ImageOfTheDay imageOfTheDay) {
        this.imageOfTheDay = imageOfTheDay;
    }
}
