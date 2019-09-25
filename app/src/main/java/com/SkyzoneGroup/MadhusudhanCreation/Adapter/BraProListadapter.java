package com.SkyzoneGroup.MadhusudhanCreation.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.SkyzoneGroup.MadhusudhanCreation.Activity.SubCatActivity;
import com.SkyzoneGroup.MadhusudhanCreation.R;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.Datum;

import java.util.List;

/**
 * Created by kevalt on 3/21/2018.
 */

public class BraProListadapter extends RecyclerView.Adapter<View_Holder> {

    LayoutInflater inflater;
    Context activity;
    List<Datum> dataSet;

    public BraProListadapter(List<Datum> items, FragmentActivity activity) {
        this.activity = activity;
        this.inflater = activity.getLayoutInflater();
        this.dataSet = items;
    }


    @Override
    public View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_bran_pro_list, parent, false);
        View_Holder holder = new View_Holder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final View_Holder holder, final int position) {
        //    if (position == 0) {
//        Picasso.with(activity).load(dataSet.get(position).getCatImage()).fit().centerCrop()
//                .placeholder(R.drawable.icon)
//                .into(holder.ivImage);
//        if (!(((Datum) this.dataSet.get(position)).getCatImage() == null || ((Datum) this.dataSet.get(position)).getCatImage().isEmpty())) {
//            Picasso.with(this.activity).load(((Datum) this.dataSet.get(position)).getCatImage()).resize(800, 450).onlyScaleDown().into(holder.ivImage);
//        }
//        Glide.with(activity).load(dataSet.get(position).getCatImage()).placeholder(R.drawable.icon)
//                .into(holder.ivImage);
        holder.tvcat_name.setText(dataSet.get(position).getCatName());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(activity, SubCatActivity.class);
                intent.putExtra("CATID", dataSet.get(position).getCatId());
                intent.putExtra("CATNAME", dataSet.get(position).getCatName());
                activity.startActivity(intent);
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
