package com.SkyzoneGroup.MadhusudhanCreation.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.SkyzoneGroup.MadhusudhanCreation.R;

/**
 * Created by kevalt on 3/27/2018.
 */

public class subListadapter extends RecyclerView.Adapter<View_Holder> {

    LayoutInflater inflater;
    Context context;
    int[] banner;

    public subListadapter(Activity activity, int[] adbanner) {
        this.context = activity;
        this.inflater = activity.getLayoutInflater();
        this.banner = adbanner;
    }
    @Override
    public View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_bran_pro_first_list, parent, false);
        View_Holder holder = new View_Holder(v);
        return holder;

    }
    @Override
    public void onBindViewHolder(final View_Holder holder, final int position) {
            holder.ivImage2.setImageResource(banner[position]);

    }


    @Override
    public int getItemCount() {
        return banner.length;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

    }

}
