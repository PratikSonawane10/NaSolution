package com.nasolution.com.nasolution.Model;

import java.util.ArrayList;

public class ExpandableParentItems {

    private String title;
    private int image;
    ArrayList<ExpandableChildItems> Items;

    public ExpandableParentItems(String title, int imageUrl){

        this.title = title;
        this.image = imageUrl;
    }

    public ExpandableParentItems() {

    }

    public String getTittle() {
        return title;
    }

    public void setTittle(String tittle) {
        this.title = tittle;
    }

    public int getIcon() {
        return image;
    }

    public void setIcons(int icon) {
        this.image = icon;
    }
    public ArrayList<ExpandableChildItems> getItems() {
        return Items;
    }

    public void setItems(ArrayList<ExpandableChildItems> Items) {
        this.Items = Items;
    }
}
