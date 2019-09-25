package com.SkyzoneGroup.MadhusudhanCreation.Adapter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.SkyzoneGroup.MadhusudhanCreation.Activity.PaymentMethodActivity;
import com.SkyzoneGroup.MadhusudhanCreation.Model.CartList;
import com.SkyzoneGroup.MadhusudhanCreation.R;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.ApiClient;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.ApiInterface;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.Responce;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by kevalt on 3/30/2018.
 */

public class CartItemPymnet_Adapter extends RecyclerView.Adapter<View_Holder> {
    List<CartList> dataSet;
    String USER_ID;
    PaymentMethodActivity activity;
    ApiInterface apiService;

    public CartItemPymnet_Adapter(List<CartList> items, PaymentMethodActivity paymentMethodActivity) {
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
    public void onBindViewHolder(final View_Holder holder, final int position) {

//        Picasso.with(activity).load(dataSet.get(position).getImage()).fit().centerCrop()
//                .placeholder(R.drawable.icon)
//                .into(holder.ivImage);
        Glide.with(activity).load(dataSet.get(position).getImage()).placeholder(R.drawable.icon)
                .into(holder.ivImage);
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
        holder.ibDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RemoveToCart(USER_ID, dataSet.get(position).getItemId());
            }

            private void RemoveToCart(final String user_id, String itemId) {
                Call<Responce> call = apiService.RemoveToCart(user_id, itemId);
                call.enqueue(new Callback<Responce>() {
                    @Override
                    public void onResponse(Call<Responce> call, Response<Responce> response) {

//                        int  users = response.body().getSuccess();
                        if (response.body().getSuccess() == 0) {
                           // activity.TotalPrice = 0;
                            Toast.makeText(activity, "Item Deleted Not From Cart..", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(activity, "Item Deleted From Cart..", Toast.LENGTH_SHORT).show();
                            Intent intent = activity.getIntent();
                            activity.overridePendingTransition(0, 0);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            activity.finish();
                            activity.overridePendingTransition(0, 0);
                            activity.startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<Responce> call, Throwable t) {
                        // Log error here since request failed
                        Toast.makeText(activity, "Try...", Toast.LENGTH_SHORT).show();
                        Log.e("", t.toString());
                    }
                });
            }
        });

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
