package com.SkyzoneGroup.MadhusudhanCreation.Adapter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.SkyzoneGroup.MadhusudhanCreation.Activity.ProductInfoActivity;
import com.SkyzoneGroup.MadhusudhanCreation.Activity.SubCategoryWiseProductActivity;
import com.SkyzoneGroup.MadhusudhanCreation.R;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.CATwishproduct;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SubCategoryWiseProductapter extends RecyclerView.Adapter<View_Holder> {

    List<CATwishproduct> dataSet;
    SubCategoryWiseProductActivity activity;
    ProgressDialog pDialog;
    String sizeshow;
    String citiesCommaSeparated;
    String finalsize;

    public SubCategoryWiseProductapter(SubCategoryWiseProductActivity subCategoryWiseProductActivity, List<CATwishproduct> dataitems) {
        this.dataSet = dataitems;
        this.activity = subCategoryWiseProductActivity;
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
        List<String> size = dataSet.get(position).getSize();

        citiesCommaSeparated = "";
        finalsize = "";
        for (String s : size)
        {
            citiesCommaSeparated += s + "\t";
        }

        if (citiesCommaSeparated.isEmpty()) {
            finalsize = "Available in free Size";
            holder.tvSize.setText(finalsize);
        } else {
            finalsize = "Available in " + citiesCommaSeparated;
            holder.tvSize.setText(finalsize);
        }
        holder.tvMRP.setPaintFlags( holder.tvMRP.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.tvMRP.setText("₹ " + String.valueOf(dataSet.get(position).getPrice()));
        holder.tvprice.setText("₹ " + String.valueOf(dataSet.get(position).getSpecialPrice()));

//        Glide.with(activity).load(dataSet.get(position).getProductImage())
//                .crossFade()
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

        Picasso.with(this.activity).load(((CATwishproduct) this.dataSet.get(position)).getProductImage())
                .placeholder(R.drawable.icon)
//                    .resize(800, 500)
//                    .onlyScaleDown()
                .into(holder.ivImage);

        holder.btVproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String catid = dataSet.get(position).getProductId();
                String catname = dataSet.get(position).getProductName();
                Intent intent = new Intent(activity, ProductInfoActivity.class);
                intent.putExtra("PRODUCTID", catid);
                intent.putExtra("PRODUCTNAME", catname);
                activity.startActivity(intent);
            }
        });
//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String catid = dataSet.get(position).getProductId();
//                String catname = dataSet.get(position).getProductName();
//                Intent intent = new Intent(activity, ProductInfoActivity.class);
//                intent.putExtra("PRODUCTID", catid);
//                intent.putExtra("PRODUCTNAME", catname);
//                activity.startActivity(intent);
//            }
//        });
        holder.btVproshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Description = dataSet.get(position).getProductName() + "\n" +  finalsize + "\n" + dataSet.get(position).getSpecialPrice();
                String Imageurl = dataSet.get(position).getProductImage();
                String catid = dataSet.get(position).getProductId();
                activity.sharesingleproduct(Description,Imageurl,catid);


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
