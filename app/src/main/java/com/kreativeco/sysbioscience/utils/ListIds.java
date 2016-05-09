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

    private static int idPurchase = -1;
    private static int idPurchaseVariety = -1;
    private static String namePurchase = "";
    private static String varietyPurchase = "";

    private static int idProperty = -1;
    private static String nameProperty = "";

    private static int idPeriod = -1;
    private static String namePeriod = "";

    private static int idSeedType = -1;
    private static String nameSeedType = "";

    private static int idPossession = -1;
    private static String namePossession = "";

    private static String nameSellType = "";

    private static String stringPoints   = "";
    private static String stringCoordinatesCounter = "";

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

    public static int getIdProperty() {
        return idProperty;
    }

    public static void setIdProperty(int idProperty) {
        ListIds.idProperty = idProperty;
    }

    public static String getNameProperty() {
        return nameProperty;
    }

    public static void setNameProperty(String nameProperty) {
        ListIds.nameProperty = nameProperty;
    }

    public static String getNameSellType() {
        return nameSellType;
    }

    public static void setNameSellType(String nameSellType) {
        ListIds.nameSellType = nameSellType;
    }

    public static int getIdPeriod() {
        return idPeriod;
    }

    public static void setIdPeriod(int idPeriod) {
        ListIds.idPeriod = idPeriod;
    }

    public static String getNamePeriod() {
        return namePeriod;
    }

    public static void setNamePeriod(String namePeriod) {
        ListIds.namePeriod = namePeriod;
    }

    public static int getIdSeedType() {
        return idSeedType;
    }

    public static void setIdSeedType(int idSeedType) {
        ListIds.idSeedType = idSeedType;
    }

    public static String getNameSeedType() {
        return nameSeedType;
    }

    public static void setNameSeedType(String nameSeedType) {
        ListIds.nameSeedType = nameSeedType;
    }

    public static int getIdPossession() {
        return idPossession;
    }

    public static void setIdPossession(int idPossession) {
        ListIds.idPossession = idPossession;
    }

    public static String getNamePossession() {
        return namePossession;
    }

    public static void setNamePossession(String namePossession) {
        ListIds.namePossession = namePossession;
    }

    public static String getStringPoints() {
        return stringPoints;
    }

    public static void setStringPoints(String stringPoints) {
        ListIds.stringPoints = stringPoints;
    }

    public static String getStringCoordinatesCounter() {
        return stringCoordinatesCounter;
    }

    public static void setStringCoordinatesCounter(String stringCoordinatesCounter) {
        ListIds.stringCoordinatesCounter = stringCoordinatesCounter;
    }
}
