package com.target.dealbrowserpoc.dealbrowser.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.target.dealbrowserpoc.dealbrowser.R;
import com.target.dealbrowserpoc.dealbrowser.Utility;
import com.target.dealbrowserpoc.dealbrowser.events.DealClickedEvent;
import com.target.dealbrowserpoc.dealbrowser.model.DealItem;

import org.greenrobot.eventbus.EventBus;

import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DealCardListItemAdapter extends RecyclerView.Adapter<DealCardListItemAdapter.DealCardListViewHolder> {
    private List<DealItem> dealItems;
    private WeakReference<Context> mContext = new WeakReference<>(null);


    public DealCardListItemAdapter(Context ctx, List<DealItem> items) {
        super();
        dealItems = items;
        mContext =  new WeakReference<Context>(ctx);
    }

    @Override
    public DealCardListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.deals_card_items, parent, false);

        return new DealCardListItemAdapter.DealCardListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DealCardListViewHolder holder, int position) {
        Context context = mContext.get();
        final DealItem dealItem = dealItems.get(position);
        Picasso.with(context).load(dealItem.getImage()).placeholder(R.drawable.place_holder).error(R.drawable.place_holder).into(holder.productImage);
        holder.title.setText(dealItem.title);
        holder.price.setText(dealItem.salePrice);
        holder.price.setTextColor(holder.redColor);
        if(Utility.isStringEmpty(dealItem.salePrice)) {
            holder.price.setText(dealItem.getOriginalPrice());
            holder.price.setTextColor(holder.blackColor);
        }
        holder.aisle.setText(dealItem.aisle);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new DealClickedEvent(dealItem));
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return dealItems.size();
    }


    public class DealCardListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.deal_list_item_image_view)
        public ImageView productImage;

        @BindView(R.id.deal_list_item_title)
        public TextView title;

        @BindView(R.id.deal_list_item_price)
        public TextView price;

        @BindView(R.id.aisle)
        public TextView aisle;

        @BindColor(R.color.red)
        public int redColor;

        @BindColor(R.color.black)
        public int blackColor;


        public DealCardListViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
