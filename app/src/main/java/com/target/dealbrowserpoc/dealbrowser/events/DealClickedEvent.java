package com.target.dealbrowserpoc.dealbrowser.events;

import com.target.dealbrowserpoc.dealbrowser.model.DealItem;

/**
 * Created by mcnaik on 9/24/17.
 */

public class DealClickedEvent {

    private DealItem mDealItem;

    public DealClickedEvent(DealItem mDealItem) {
        this.mDealItem =  mDealItem;
    }

    public DealItem getDealItem() {
        return mDealItem;
    }
}
