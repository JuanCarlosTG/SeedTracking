package com.kreativeco.sysbioscience.home;

/**
 * Created by kreativeco on 27/03/16.
 */
public class ListModelMenu {

    private int nameList;
    private int icMenuList;

    public ListModelMenu(int nameList, int icMenuList){
        this.nameList = nameList;
        this.icMenuList = icMenuList;
    }

    public ListModelMenu(int nameList){
        this.nameList = nameList;
        this.icMenuList = -1;
    }

    public int getNameList() {
        return nameList;
    }

    public void setNameList(int nameList) {
        this.nameList = nameList;
    }

    public int getIcMenuList() {
        return icMenuList;
    }

    public void setIcMenuList(int icMenuList) {
        this.icMenuList = icMenuList;
    }
}
