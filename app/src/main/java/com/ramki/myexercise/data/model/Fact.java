package com.ramki.myexercise.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Ramki on 2/11/2018.
 */
public class Fact {
    @SerializedName("title")
    private String title;
    @SerializedName("rows")
    private ArrayList<Information> informations;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Information> getInformations() {
        return informations;
    }

    public void setInformations(ArrayList<Information> informations) {
        this.informations = informations;
    }
}
