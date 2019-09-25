package com.SkyzoneGroup.MadhusudhanCreation.Adapter;

import android.content.SharedPreferences;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.SkyzoneGroup.MadhusudhanCreation.Activity.MyOrederFullInfoActivity;
import com.SkyzoneGroup.MadhusudhanCreation.R;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.ApiClient;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.ApiInterface;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.MyOrderDetailProduct;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by kevalt on 3/31/2018.
 */

public class OrderDetail_Adapter extends RecyclerView.Adapter<View_Holder> {

    List<MyOrderDetailProduct> dataSet;
    String USER_ID;
    MyOrederFullInfoActivity activity;
    ApiInterface apiService;

    public OrderDetail_Adapter(List<MyOrderDetailProduct> users, MyOrederFullInfoActivity myOrederFullInfoActivity) {
        this.dataSet = users;
        this.activity = myOrederFullInfoActivity;
        apiService = ApiClient.getClient().create(ApiInterface.class);
        SharedPreferences prefs = activity.getSharedPreferences("myPref", MODE_PRIVATE);
        this.USER_ID = prefs.getString("USER_ID", null);

    }

    @Override
    public View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_myorderpro_item, parent, false);
        View_Holder holder = new View_Holder(v);
        return holder;

    }

    @Override
    public void onBindViewHolder(View_Holder holder, int position) {
//        Picasso.with(activity).load(dataSet.get(position).getImage()).fit().centerCrop()
//                .placeholder(R.drawable.icon)
//                .into(holder.ivImage);
        if (!(((MyOrderDetailProduct) this.dataSet.get(position)).getImage() == null || ((MyOrderDetailProduct) this.dataSet.get(position)).getImage().isEmpty())) {
            Picasso.with(this.activity).load(((MyOrderDetailProduct) this.dataSet.get(position)).getImage()).resize(800, 450).onlyScaleDown().into(holder.ivImage);
        }
//        Glide.with(activity).load(dataSet.get(position).getImage()).placeholder(R.drawable.icon)
//                .into(holder.ivImage);
        final String quntity = dataSet.get(position).getProductQty();
        holder.tvQuantity.setText(quntity + "Item");
        String size = dataSet.get(position).getSize();
        if (size == null) {
            holder.rlSize.setVisibility(View.GONE);
        }
        holder.tvSize.setText(dataSet.get(position).getSize());
        //  holder.tvQuantity.setText(dataSet.get(position).getSize().toString());
        holder.tvMRP.setPaintFlags(holder.tvMRP.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.tvMRP.setText(dataSet.get(position).getOriginalPrice(), TextView.BufferType.SPANNABLE);
        holder.tvName.setText(dataSet.get(position).getProductName());
        holder.tvskucode.setText(dataSet.get(position).getSku());
//        int a = Integer.parseInt(dataSet.get(position).getPrice().toString());
//        int b = Integer.parseInt(dataSet.get(position).getProductQty().toString());
//        int result = a * b ;
        holder.tvprice.setText("₹ " + dataSet.get(position).getSubTotal().toString());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

    }
}
