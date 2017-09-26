package com.target.dealbrowserpoc.dealbrowser.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.target.dealbrowserpoc.dealbrowser.R;
import com.target.dealbrowserpoc.dealbrowser.adapters.DealCardListItemAdapter;
import com.target.dealbrowserpoc.dealbrowser.model.DealItem;
import com.target.dealbrowserpoc.dealbrowser.model.DealList;

import java.util.List;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mcnaik on 9/25/17.
 */

public class DealCardListFragment  extends Fragment {
    private static final String  DEAL_LIST = "DEAL_LIST";
    private List<DealItem> dealList;
    private DealCardListItemAdapter mDealCardListItemAdapter;
    @BindView(R.id.recycler_view)
    protected RecyclerView recyclerView;
    @BindInt(R.integer.no_of_rows)
    protected int noOfRowsGrid;

    public static DealCardListFragment newInstance(DealList dealList) {
        final Bundle args = new Bundle();
        if (dealList != null) {
            args.putSerializable(DEAL_LIST, dealList);
        }

        final DealCardListFragment fragment = new DealCardListFragment();
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
                mDealCardListItemAdapter = new DealCardListItemAdapter(getActivity(), dealList);
            }
        }

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), noOfRowsGrid);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mDealCardListItemAdapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_deal_list_card, container, false);
    }
}
