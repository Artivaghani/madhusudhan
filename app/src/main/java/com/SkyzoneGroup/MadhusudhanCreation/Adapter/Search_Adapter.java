package com.SkyzoneGroup.MadhusudhanCreation.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import com.SkyzoneGroup.MadhusudhanCreation.Activity.ProductInfoActivity;
import com.SkyzoneGroup.MadhusudhanCreation.R;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.Datum;

import java.util.List;

/**
 * Created by kevalt on 4/13/2018.
 */

public class Search_Adapter extends RecyclerView.Adapter<View_Holder> {

    List<Datum> dataSet;
    Context dactivity;
    ShareClickShare shareClickShare;


    public interface ShareClickShare
    {
        void ShareClickShare(int position);
    }
    public void RegistershareClickShareClick(ShareClickShare shareClickShare)
    {
        this.shareClickShare=shareClickShare;
    }

    public Search_Adapter(List<Datum> sub_catItem, FragmentActivity activity) {
        this.dactivity = activity;
        this.dataSet =sub_catItem;

    }
    @Override
    public View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_cat_subproduct_item, parent, false);
        View_Holder holder = new View_Holder(v);
        return holder;
    }
    @Override
    public void onBindViewHolder(final View_Holder holder, final int position) {

        holder.tvName.setText(dataSet.get(position).getProductName());

//        if (sizeshow.isEmpty()) {
//            holder.tvSize.setText("Available in free Size");
//        } else {
//            holder.tvSize.setText("Available in " + sizeshow);
//        }
        holder.tvMRP.setPaintFlags( holder.tvMRP.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.tvMRP.setText("₹ " + String.valueOf(dataSet.get(position).getPrice()));
        holder.tvprice.setText("₹ " + String.valueOf(dataSet.get(position).getSpecialPrice()));
//        Glide.with(dactivity).load(dataSet.get(position).getProductImage())
//                .crossFade()
//                .thumbnail(0.5f)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .placeholder(R.drawable.icon)
//                .listener(new RequestListener<String, GlideDrawable>() {
//                    @Override
//                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                        holder.ivImage.setImageDrawable(resource);
//                        return false;
//                    }
//                })
//                .into(holder.ivImage);
//        Picasso.with(dactivity).load(dataSet.get(position).getProductImage()).fit().centerCrop()
//                .placeholder(R.drawable.icon)
//                .into(holder.ivImage);
        Glide.with(dactivity).load(dataSet.get(position).getProductImage()).placeholder(R.drawable.icon)
                .into(holder.ivImage);
        holder.btVproduct.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                String catid = dataSet.get(position).getProductId();
                String catname = dataSet.get(position).getProductName();
                Intent intent = new Intent(dactivity, ProductInfoActivity.class);
                intent.putExtra("PRODUCTID", catid);
                intent.putExtra("PRODUCTNAME", catname);
                dactivity.startActivity(intent);
            }
        });
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String catid = dataSet.get(position).getProductId();
                String catname = dataSet.get(position).getProductName();
                Intent intent = new Intent(dactivity, ProductInfoActivity.class);
                intent.putExtra("PRODUCTID", catid);
                intent.putExtra("PRODUCTNAME", catname);
                dactivity.startActivity(intent);
            }
        });
        holder.btVproshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(shareClickShare!=null)
                {
                    shareClickShare.ShareClickShare(position);
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
