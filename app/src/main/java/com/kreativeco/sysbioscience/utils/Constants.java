package com.kreativeco.sysbioscience.utils;

/**
 * Created by kreativeco on 11/03/16.
 */
public class Constants {

    private static String spotDescription = "";
    private static String spotFullImage = "";
    private static String spotTitle = "";

    public static String getSpotDescription() {
        return spotDescription;
    }

    public static void setSpotDescription(String spotDescription) {
        Constants.spotDescription = spotDescription;
    }

    public static String getSpotFullImage() {
        return spotFullImage;
    }

    public static void setSpotFullImage(String spotFullImage) {
        Constants.spotFullImage = spotFullImage;
    }

    public static void clear(){

    }

    public static String getSpotTitle() {
        return spotTitle;
    }

    public static void setSpotTitle(String spotTitle) {
        Constants.spotTitle = spotTitle;
    }
}
