package com.kreativeco.sysbioscience.utils;

/**
 * Created by JuanC on 25/04/2016.
 */
public class ListIds {

    private static int idState = -1;
    private static int idLocality = -1;
    private static int idSellType = -1;
    private static int idVariety = -1;

    private static String stringState = "";
    private static String stringLocality = "";

    private static int idPurchase;
    private static int idPurchaseVariety;
    private static String namePurchase;
    private static String varietyPurchase;

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

    public static int getIdSellType() {
        return idSellType;
    }

    public static void setIdSellType(int idSellType) {
        ListIds.idSellType = idSellType;
    }

    public static int getIdVariety() {
        return idVariety;
    }

    public static void setIdVariety(int idVariety) {
        ListIds.idVariety = idVariety;
    }

    public static void clear(){
        idLocality = -1;
        idSellType = -1;
        idState = -1;
        idVariety = -1;
        stringState = "";
        stringLocality = "";
    }

    public static String getStringState() {
        return stringState;
    }

    public static void setStringState(String stringState) {
        ListIds.stringState = stringState;
    }

    public static String getStringLocality() {
        return stringLocality;
    }

    public static void setStringLocality(String stringLocality) {
        ListIds.stringLocality = stringLocality;
    }

    public static int getIdPurchase() {
        return idPurchase;
    }

    public static void setIdPurchase(int idPurchase) {
        ListIds.idPurchase = idPurchase;
    }

    public static int getIdPurchaseVariety() {
        return idPurchaseVariety;
    }

    public static void setIdPurchaseVariety(int idPurchaseVariety) {
        ListIds.idPurchaseVariety = idPurchaseVariety;
    }

    public static String getNamePurchase() {
        return namePurchase;
    }

    public static void setNamePurchase(String namePurchase) {
        ListIds.namePurchase = namePurchase;
    }

    public static String getVarietyPurchase() {
        return varietyPurchase;
    }

    public static void setVarietyPurchase(String varietyPurchase) {
        ListIds.varietyPurchase = varietyPurchase;
    }
}
