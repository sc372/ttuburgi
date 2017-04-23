package com.TT.kitcoop.ttuburgi;

/**
 * Created by yunjeong on 2017-04-12.
 */

public class Select_Item {
    private int icon;
    private String title;
    private String txt;

    public Select_Item(int icon, String title, String txt) {
        this.icon = icon;
        this.title = title;
        this.txt = txt;
    }

    public Select_Item(String title, String txt){
        this.title = title;
        this.txt = txt;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }
}




