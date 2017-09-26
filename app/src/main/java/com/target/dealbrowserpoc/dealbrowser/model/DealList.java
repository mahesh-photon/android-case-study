package com.target.dealbrowserpoc.dealbrowser.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mcnaik on 9/24/17.
 */

public class DealList  implements Serializable {

    @SerializedName("_id")
    private String _id;

    @SerializedName("data")
    private List<DealItem> dealList;

    public List<DealItem> getDealList() {
        return dealList;
    }
}
