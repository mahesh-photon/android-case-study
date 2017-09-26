package com.target.dealbrowserpoc.dealbrowser.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DealItem implements Serializable{

    @SerializedName("index")
    public int index;

    @SerializedName("_id")
    public String id;

    @SerializedName("title")
    public String title;

    @SerializedName("description")
    public String description;

    @SerializedName("price")
    public String originalPrice;

    @SerializedName("salePrice")
    public String salePrice;

    @SerializedName("image")
    public String image;

    @SerializedName("aisle")
    public String aisle;


    public int getIndex() {
        return index;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getOriginalPrice() {
        return originalPrice;
    }

    public String getSalePrice() {
        return salePrice;
    }

    public String getImage() {
        return image;
    }

    public String getAisle() {
        return aisle;
    }

    @Override
    public String toString() {
        return title;
    }
}