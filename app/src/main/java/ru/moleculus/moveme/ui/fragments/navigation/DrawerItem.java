package ru.moleculus.moveme.ui.fragments.navigation;

/**
 * Created by Oleg on 02.03.2016.
 */
public class DrawerItem {

    private String title;
    private int resourceId;

    public DrawerItem(int resourceId, String title){
        this.title = title;
        this.resourceId = resourceId;
    }

    public String getTitle(){
        return title;
    }

    public int getResourceId(){
        return resourceId;
    }
}
