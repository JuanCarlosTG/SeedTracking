package com.kreativeco.sysbioscience.utils;

/**
 * Created by JuanC on 25/04/2016.
 */
public class ListIds {

    private static int idState = 0;
    private static int idLocality = 0;

    public static int getIdState() {
        return idState;
    }

    public static void setIdState(int idState) {
        ListIds.idState = idState;
    }

    public static int getIdLocality() {
        return idLocality;
    }

    public static void setIdLocality(int idLocality) {
        ListIds.idLocality = idLocality;
    }
}
