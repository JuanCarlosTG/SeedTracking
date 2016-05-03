package com.kreativeco.sysbioscience.home;

import com.kreativeco.sysbioscience.R;

import java.util.ArrayList;

/**
 * Created by JuanC on 25/04/2016.
 */
public class ItemsMenu {

    public static ArrayList<ListModelMenu> customListView = new ArrayList<ListModelMenu>(){{
        add(new ListModelMenu(R.string.txt_menu_news, R.drawable.bt_menu_news));
        add(new ListModelMenu(R.string.txt_menu_farmers, R.drawable.bt_menu_farmer));
        add(new ListModelMenu(R.string.txt_menu_profile, R.drawable.bt_menu_profile));
        add(new ListModelMenu(R.string.txt_menu_about, R.drawable.bt_menu_logout));
    }};


    public static ArrayList<ListModelMenu> getCustomListView(){
        return customListView;
    }

}
