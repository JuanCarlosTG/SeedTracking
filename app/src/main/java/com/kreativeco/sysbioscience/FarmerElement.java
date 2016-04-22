package com.kreativeco.sysbioscience;

/**
 * Created by kreativeco on 22/02/16.
 */
public class FarmerElement {

    private String farmerImage;
    private String farmerName;
    private String farmerSite;

    public FarmerElement (String farmerImage, String farmerName, String farmerSite){

        this.farmerImage    = farmerImage;
        this.farmerName     = farmerName;
        this.farmerSite     = farmerSite;

    }

    public String getFarmerName() {
        return farmerName;
    }

    public void setFarmerName(String farmerName) {
        this.farmerName = farmerName;
    }

    public String getFarmerSite() {
        return farmerSite;
    }

    public void setFarmerSite(String farmerSite) {
        this.farmerSite = farmerSite;
    }

    public String getFarmerImage() {
        return farmerImage;
    }

    public void setFarmerImage(String farmerImage) {
        this.farmerImage = farmerImage;
    }
}
