package com.target.dealbrowserpoc.dealbrowser.events;

import com.target.dealbrowserpoc.dealbrowser.model.DealItem;
import com.target.dealbrowserpoc.dealbrowser.model.DealList;
import com.target.dealbrowserpoc.dealbrowser.model.Response;
import com.target.dealbrowserpoc.dealbrowser.http.NetworkEvent;

import java.util.List;

/**
 * Created by mcnaik on 9/24/17.
 */

public class DealReceivedEvent extends NetworkEvent<DealList> {

    public DealReceivedEvent(Response response) {
        super(response, DealList.class);
    }

    public List<DealItem> getDealList() {
        return getResponseObject().getDealList();
    }


}
