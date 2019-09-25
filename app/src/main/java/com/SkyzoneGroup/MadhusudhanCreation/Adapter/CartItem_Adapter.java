package com.SkyzoneGroup.MadhusudhanCreation.Adapter;

import android.content.SharedPreferences;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.SkyzoneGroup.MadhusudhanCreation.Activity.CartActivity;
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
 * Created by kevalt on 3/28/2018.
 */

public class CartItem_Adapter extends RecyclerView.Adapter<View_Holder> {
    List<CartList> dataSet;
    String USER_ID;
    CartActivity activity;
    ApiInterface apiService;
    private static final StrikethroughSpan STRIKE_THROUGH_SPAN = new StrikethroughSpan();



    public CartItem_Adapter(List<CartList> items, CartActivity cartActivity) {
        this.dataSet = items;
        this.activity = cartActivity;
        apiService = ApiClient.getClient().create(ApiInterface.class);
        SharedPreferences prefs = activity.getSharedPreferences("myPref", MODE_PRIVATE);
        this.USER_ID = prefs.getString("USER_ID", null);
    }


    @Override
    public View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_cart_item, parent, false);
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
        final int quntity= dataSet.get(position).getQuantity();
        holder.tvQuantity.setText(String.valueOf(quntity));
        // holder.tvQuantity.setText(dataSet.get(position).getQuantity().toString());
        holder.tvMRP.setPaintFlags(holder.tvMRP.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.tvMRP.setText(dataSet.get(position).getPrice(),TextView.BufferType.SPANNABLE);
        holder.tvName.setText(dataSet.get(position).getName());
        String size = dataSet.get(position).getOptions();
//        if (size.isEmpty()){
//            holder.rlSize.setVisibility(View.GONE);
//        }
        holder.tvSize.setText(dataSet.get(position).getOptions());
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
                            Toast.makeText(activity, "Item Deleted Not From Cart..", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(activity, "Item Deleted From Cart..", Toast.LENGTH_SHORT).show();
                            activity.getcartitems(user_id);
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
        holder.ivRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dataSet.get(position).getQuantity() != 0) {

                    activity.UpdateCartQuntity(dataSet.get(position).getItemId(), dataSet.get(position).getQuantity() + 1);
                }
            }
        });

        holder.ivLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dataSet.get(position).getQuantity() != 0) {
                    activity.UpdateCartQuntity(dataSet.get(position).getItemId(), dataSet.get(position).getQuantity()-1);
                }
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
