package com.kreativeco.sysbioscience.utils;

/**
 * Created by kreativeco on 11/03/16.
 */
public class Constants {

    private static String spotDescription = "";
    private static String spotFullImage = "";
    private static String spotTitle = "";

    private static String assignCalendarDate   = "";
    private static String assignCalendarDateFormatted = "";

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

    public static String getAssignCalendarDate() {
        return assignCalendarDate;
    }

    public static void setAssignCalendarDate(String assignCalendarDate) {
        Constants.assignCalendarDate = assignCalendarDate;
    }

    public static String getAssignCalendarDateFormatted() {
        return assignCalendarDateFormatted;
    }

    public static void setAssignCalendarDateFormatted(String assignCalendarDateFormatted) {
        Constants.assignCalendarDateFormatted = assignCalendarDateFormatted;
    }
}
