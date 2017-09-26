package com.target.dealbrowserpoc.dealbrowser.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.target.dealbrowserpoc.dealbrowser.R;
import com.target.dealbrowserpoc.dealbrowser.Utility;
import com.target.dealbrowserpoc.dealbrowser.model.DealItem;

import java.util.List;

public class DealListItemAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<DealItem> dealItems;
    private Context context;


    public static DealListItemAdapter newInstance(Context context, List<DealItem> items) {
        return new DealListItemAdapter(context, items);
    }

    public DealListItemAdapter(Context ctx, List<DealItem> items) {
        super();
        context = ctx;
        inflater = LayoutInflater.from(context);
        dealItems = items;
    }

    @Override
    public int getCount() {
        return dealItems.size();
    }

    @Override
    public Object getItem(int position){
        try {
            return dealItems.get(position);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder holder;
        final DealItem dealItem = dealItems.get(position);
        if(convertView == null) {
            view = inflater.inflate(R.layout.deal_list_item, parent, false);
            holder = new ViewHolder();
            holder.productImage = (ImageView)view.findViewById(R.id.deal_list_item_image_view);
            holder.title = (TextView)view.findViewById(R.id.deal_list_item_title);
            holder.price = (TextView)view.findViewById(R.id.deal_list_item_price);
            holder.aisle = (TextView)view.findViewById(R.id.aisle);
            holder.itemView = view.findViewById(R.id.deal_list_item);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder)view.getTag();
        }
        Picasso.with(context).load(dealItem.getImage()).error(R.drawable.place_holder).into(holder.productImage);
        holder.title.setText(dealItem.title);
        holder.price.setText(dealItem.salePrice);
        holder.price.setTextColor(context.getResources().getColor(R.color.red));
        if(Utility.isStringEmpty(dealItem.salePrice)) {
            holder.price.setText(dealItem.getOriginalPrice());
            holder.price.setTextColor(context.getResources().getColor(R.color.black));
        }
        holder.aisle.setText(dealItem.aisle);

        return view;
    }

    private class ViewHolder {
        public ImageView productImage;
        public TextView title, price, aisle;
        public View itemView;
    }
}
