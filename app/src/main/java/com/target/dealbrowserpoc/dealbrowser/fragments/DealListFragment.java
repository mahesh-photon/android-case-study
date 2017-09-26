package com.target.dealbrowserpoc.dealbrowser.fragments;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.target.dealbrowserpoc.dealbrowser.MainActivity;
import com.target.dealbrowserpoc.dealbrowser.adapters.DealListItemAdapter;
import com.target.dealbrowserpoc.dealbrowser.events.DealClickedEvent;
import com.target.dealbrowserpoc.dealbrowser.model.DealItem;
import com.target.dealbrowserpoc.dealbrowser.model.DealList;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.ButterKnife;

public class DealListFragment extends ListFragment {

    private static final String  DEAL_LIST = "DEAL_LIST";
    private DealListItemAdapter mDealListItemAdapter;
    private  List<DealItem> dealList;


    public static DealListFragment newInstance(DealList dealList) {
        final Bundle args = new Bundle();
        if (dealList != null) {
            args.putSerializable(DEAL_LIST, dealList);
        }

        final DealListFragment fragment = new DealListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Bundle args = getArguments();
        if (args != null) {
            final DealList dealListObject = (DealList) args.getSerializable(DEAL_LIST);
             dealList = dealListObject.getDealList();
            if (dealList != null) {
                mDealListItemAdapter = new DealListItemAdapter(getActivity(), dealList);
            }
        }
        setListAdapter(mDealListItemAdapter);
        if(((MainActivity)getActivity()).isTablet()) {
            setDetailDefault();
        }
    }

    private void setDetailDefault() {
        if(dealList != null) {
            EventBus.getDefault().post(new DealClickedEvent(dealList.get(0)));
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        EventBus.getDefault().post(new DealClickedEvent(dealList.get(position)));
    }
}