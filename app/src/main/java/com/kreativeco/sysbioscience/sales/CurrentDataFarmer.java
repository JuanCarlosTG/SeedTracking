package com.kreativeco.sysbioscience.sales;

/**
 * Created by JuanC on 23/04/2016.
 */
public class CurrentDataFarmer {

    private static String farmerName                = "";
    private static String farmerLastNameA           = "";
    private static String farmerLastNameB           = "";
    private static String farmerFullName            = "";
    private static String farmerCompany             = "";
    private static String farmerRFC                 = "";
    private static String farmerPhone               = "";
    private static String farmerNameMunicipality    = "";
    private static String farmerAddress             = "";
    private static String farmerNameState           = "";
    private static String farmerZip                 = "";
    private static String farmerIdCard              = "";
    private static String farmerAssigns             = "";
    private static String farmerApplications        = "";
    private static String farmerUserType            = "";
    private static String farmerMail                = "";
    private static String farmerPass                = "";
    private static String farmerToken               = "";
    private static String farmerPhotoFile           = "";
    private static String farmerContract            = "";
    private static int farmerIdState                = -1;
    private static int farmerIdMunicipality         = -1;
    private static int farmerId                     = -1;
    private static int farmerIdUserType             = -1;
    private static boolean farmerNotify             = false;
    private static boolean farmerAssignTypeSeeds    = false;
    private static boolean farmerCanLogin           = false;
    private static boolean farmerActive             = false;

    private static String strFileFarmer             = "";
    private static String strFileContract           = "";


    public static String getFarmerName() {
        return farmerName;
    }

    public static void setFarmerName(String farmerName) {
        CurrentDataFarmer.farmerName = farmerName;
    }

    public static String getFarmerLastNameA() {
        return farmerLastNameA;
    }

    public static void setFarmerLastNameA(String farmerLastNameA) {
        CurrentDataFarmer.farmerLastNameA = farmerLastNameA;
    }

    public static String getFarmerLastNameB() {
        return farmerLastNameB;
    }

    public static void setFarmerLastNameB(String farmerLastNameB) {
        CurrentDataFarmer.farmerLastNameB = farmerLastNameB;
    }

    public static String getFarmerFullName() {
        return farmerFullName;
    }

    public static void setFarmerFullName(String farmerFullName) {
        CurrentDataFarmer.farmerFullName = farmerFullName;
    }

    public static String getFarmerCompany() {
        return farmerCompany;
    }

    public static void setFarmerCompany(String farmerCompany) {
        CurrentDataFarmer.farmerCompany = farmerCompany;
    }

    public static String getFarmerRFC() {
        return farmerRFC;
    }

    public static void setFarmerRFC(String farmerRFC) {
        CurrentDataFarmer.farmerRFC = farmerRFC;
    }

    public static String getFarmerPhone() {
        return farmerPhone;
    }

    public static void setFarmerPhone(String farmerPhone) {
        CurrentDataFarmer.farmerPhone = farmerPhone;
    }

    public static String getFarmerNameMunicipality() {
        return farmerNameMunicipality;
    }

    public static void setFarmerNameMunicipality(String farmerNameMunicipality) {
        CurrentDataFarmer.farmerNameMunicipality = farmerNameMunicipality;
    }

    public static String getFarmerAddress() {
        return farmerAddress;
    }

    public static void setFarmerAddress(String farmerAddress) {
        CurrentDataFarmer.farmerAddress = farmerAddress;
    }

    public static String getFarmerNameState() {
        return farmerNameState;
    }

    public static void setFarmerNameState(String farmerNameState) {
        CurrentDataFarmer.farmerNameState = farmerNameState;
    }

    public static String getFarmerZip() {
        return farmerZip;
    }

    public static void setFarmerZip(String farmerZip) {
        CurrentDataFarmer.farmerZip = farmerZip;
    }

    public static String getFarmerIdCard() {
        return farmerIdCard;
    }

    public static void setFarmerIdCard(String farmerIdCard) {
        CurrentDataFarmer.farmerIdCard = farmerIdCard;
    }

    public static String getFarmerAssigns() {
        return farmerAssigns;
    }

    public static void setFarmerAssigns(String farmerAssigns) {
        CurrentDataFarmer.farmerAssigns = farmerAssigns;
    }

    public static String getFarmerApplications() {
        return farmerApplications;
    }

    public static void setFarmerApplications(String farmerApplications) {
        CurrentDataFarmer.farmerApplications = farmerApplications;
    }

    public static String getFarmerUserType() {
        return farmerUserType;
    }

    public static void setFarmerUserType(String farmerUserType) {
        CurrentDataFarmer.farmerUserType = farmerUserType;
    }

    public static String getFarmerMail() {
        return farmerMail;
    }

    public static void setFarmerMail(String farmerMail) {
        CurrentDataFarmer.farmerMail = farmerMail;
    }

    public static String getFarmerPass() {
        return farmerPass;
    }

    public static void setFarmerPass(String farmerPass) {
        CurrentDataFarmer.farmerPass = farmerPass;
    }

    public static String getFarmerToken() {
        return farmerToken;
    }

    public static void setFarmerToken(String farmerToken) {
        CurrentDataFarmer.farmerToken = farmerToken;
    }

    public static String getFarmerPhotoFile() {
        return farmerPhotoFile;
    }

    public static void setFarmerPhotoFile(String farmerPhotoFile) {
        CurrentDataFarmer.farmerPhotoFile = farmerPhotoFile;
    }

    public static String getFarmerContract() {
        return farmerContract;
    }

    public static void setFarmerContract(String farmerContract) {
        CurrentDataFarmer.farmerContract = farmerContract;
    }

    public static int getFarmerIdState() {
        return farmerIdState;
    }

    public static void setFarmerIdState(int farmerIdState) {
        CurrentDataFarmer.farmerIdState = farmerIdState;
    }

    public static int getFarmerIdMunicipality() {
        return farmerIdMunicipality;
    }

    public static void setFarmerIdMunicipality(int farmerIdMunicipality) {
        CurrentDataFarmer.farmerIdMunicipality = farmerIdMunicipality;
    }

    public static int getFarmerId() {
        return farmerId;
    }

    public static void setFarmerId(int farmerId) {
        CurrentDataFarmer.farmerId = farmerId;
    }

    public static int getFarmerIdUserType() {
        return farmerIdUserType;
    }

    public static void setFarmerIdUserType(int farmerIdUserType) {
        CurrentDataFarmer.farmerIdUserType = farmerIdUserType;
    }

    public static boolean isFarmerNotify() {
        return farmerNotify;
    }

    public static void setFarmerNotify(boolean farmerNotify) {
        CurrentDataFarmer.farmerNotify = farmerNotify;
    }

    public static boolean isFarmerAssignTypeSeeds() {
        return farmerAssignTypeSeeds;
    }

    public static void setFarmerAssignTypeSeeds(boolean farmerAssignTypeSeeds) {
        CurrentDataFarmer.farmerAssignTypeSeeds = farmerAssignTypeSeeds;
    }

    public static boolean isFarmerCanLogin() {
        return farmerCanLogin;
    }

    public static void setFarmerCanLogin(boolean farmerCanLogin) {
        CurrentDataFarmer.farmerCanLogin = farmerCanLogin;
    }

    public static boolean isFarmerActive() {
        return farmerActive;
    }

    public static void setFarmerActive(boolean farmerActive) {
        CurrentDataFarmer.farmerActive = farmerActive;
    }

    public static String getStrFileFarmer() {
        return strFileFarmer;
    }

    public static void setStrFileFarmer(String strFileFarmer) {
        CurrentDataFarmer.strFileFarmer = strFileFarmer;
    }

    public static String getStrFileContract() {
        return strFileContract;
    }

    public static void setStrFileContract(String strFileContract) {
        CurrentDataFarmer.strFileContract = strFileContract;
    }
}
