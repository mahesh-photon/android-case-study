package com.target.dealbrowserpoc.dealbrowser.fragments;

import android.app.Fragment;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.target.dealbrowserpoc.dealbrowser.R;
import com.target.dealbrowserpoc.dealbrowser.Utility;
import com.target.dealbrowserpoc.dealbrowser.model.DealItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mcnaik on 9/23/17.
 */

public class DealDetailsFragment extends Fragment {

    private static final String  DEAL_ITEM = "DEAL_ITEM";

    @BindView(R.id.deal_deatil_image)
    ImageView dealImage;
    @BindView(R.id.deal_deatil_discription)
    TextView dealDiscription;
    @BindView(R.id.deal_deatil_title)
    TextView dealTitle;
    @BindView(R.id.deal_deatil_sale_price)
    TextView dealSalePrice;
    @BindView(R.id.deal_deatil_orginal_price)
    TextView dealOriginalPrice;

    public static DealDetailsFragment newInstance(DealItem mDealItem) {
        final Bundle args = new Bundle();
        if (mDealItem != null) {
            args.putSerializable(DEAL_ITEM, mDealItem);
        }

        final DealDetailsFragment fragment = new DealDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.deal_details, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        final Bundle args = getArguments();
        if (args != null) {
            final DealItem mDealItem = (DealItem) args.getSerializable(DEAL_ITEM);
            if (mDealItem != null) {
                Picasso.with(getActivity()).load(mDealItem.getImage()).placeholder(R.drawable.place_holder).error(R.drawable.place_holder).into(dealImage);
                dealDiscription.setText(mDealItem.description);
                dealTitle.setText(mDealItem.title);
                dealSalePrice.setText(mDealItem.salePrice);

                dealOriginalPrice.setText(mDealItem.originalPrice);
                if (!Utility.isStringEmpty(mDealItem.salePrice)) {
                    dealOriginalPrice.setPaintFlags(dealOriginalPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }
            }

        }

    }

    @OnClick(R.id.add_to_cart)
    public void addToCart() {
        Toast.makeText(getActivity(),"TODO: Add to cart Implementation", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.add_to_list)
    public void addToList() {
        Toast.makeText(getActivity(),"TODO: Add to list Implementation", Toast.LENGTH_SHORT).show();
    }
}


