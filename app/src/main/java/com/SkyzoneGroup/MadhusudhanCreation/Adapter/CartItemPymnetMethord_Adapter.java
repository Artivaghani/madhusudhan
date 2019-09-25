package com.SkyzoneGroup.MadhusudhanCreation.Adapter;

import android.content.SharedPreferences;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.SkyzoneGroup.MadhusudhanCreation.Activity.PaymentMethodActivity;
import com.SkyzoneGroup.MadhusudhanCreation.R;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.ApiClient;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.ApiInterface;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.DatumPymentMethord;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class CartItemPymnetMethord_Adapter extends RecyclerView.Adapter<View_Holder> {
    List<DatumPymentMethord> dataSet;
    String USER_ID;
    PaymentMethodActivity activity;
    ApiInterface apiService;


    public CartItemPymnetMethord_Adapter(List<DatumPymentMethord> items, PaymentMethodActivity paymentMethodActivity) {
        this.dataSet = items;
        this.activity = paymentMethodActivity;
        apiService = ApiClient.getClient().create(ApiInterface.class);
        SharedPreferences prefs = activity.getSharedPreferences("myPref", MODE_PRIVATE);
        this.USER_ID = prefs.getString("USER_ID", null);
    }


    @Override
    public View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_paymentcart_item, parent, false);
        View_Holder holder = new View_Holder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(View_Holder holder, int position) {
//        Picasso.with(activity).load(dataSet.get(position).getImage()).fit().centerCrop()
//                .placeholder(R.drawable.icon)
//                .into(holder.ivImage);

        Glide.with(activity).load(dataSet.get(position).getImage()).placeholder(R.drawable.icon)
                .into(holder.ivImage);
//        if (!(((DatumPymentMethord) this.dataSet.get(position)).getImage() == null || ((DatumPymentMethord) this.dataSet.get(position)).getImage().isEmpty())) {
//            Picasso.with(this.activity).load(((DatumPymentMethord) this.dataSet.get(position)).getImage()).resize(450, 250).onlyScaleDown().into(holder.ivImage);
//        }
        final int quntity = dataSet.get(position).getQuantity();
        holder.tvQuantity.setText(String.valueOf(quntity) + " Items");
        holder.tvMRP.setPaintFlags(holder.tvMRP.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.tvMRP.setText(dataSet.get(position).getPrice(), TextView.BufferType.SPANNABLE);
        // holder.tvQuantity.setText(dataSet.get(position).getQuantity().toString());
        String size = dataSet.get(position).getOptions();
        if (size.isEmpty()) {
            holder.rlSize.setVisibility(View.GONE);
        }
        holder.tvSize.setText(dataSet.get(position).getOptions());
        holder.tvName.setText(dataSet.get(position).getName());
        holder.tvskucode.setText(dataSet.get(position).getSku());
        holder.tvprice.setText("₹ " + dataSet.get(position).getSubTotal());
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
