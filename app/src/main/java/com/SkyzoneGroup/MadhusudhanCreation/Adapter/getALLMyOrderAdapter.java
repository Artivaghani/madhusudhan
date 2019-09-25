package com.SkyzoneGroup.MadhusudhanCreation.Adapter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.SkyzoneGroup.MadhusudhanCreation.Activity.MyOrderActivity;
import com.SkyzoneGroup.MadhusudhanCreation.Activity.MyOrederFullInfoActivity;
import com.SkyzoneGroup.MadhusudhanCreation.R;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.ApiClient;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.ApiInterface;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.Datum;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by kevalt on 3/31/2018.
 */

public class getALLMyOrderAdapter extends RecyclerView.Adapter<View_Holder> {
    List<Datum> dataSet;
    String USER_ID;
    MyOrderActivity activity;
    ApiInterface apiService;

    public getALLMyOrderAdapter(List<Datum> items, MyOrderActivity myOrderActivity) {
        this.dataSet = items;
        this.activity = myOrderActivity;
        apiService = ApiClient.getClient().create(ApiInterface.class);
        SharedPreferences prefs = activity.getSharedPreferences("myPref", MODE_PRIVATE);
        this.USER_ID = prefs.getString("USER_ID", null);
    }

    @Override
    public View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_myorder_item, parent, false);
        View_Holder holder = new View_Holder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(View_Holder holder, final int position) {
        holder.tvOrderId.setText(dataSet.get(position).getIncrementId().toString());

//        String strCurrentDate = dataSet.get(position).getOrderDate();
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date newDate = null;
//        try {
//            newDate = format.parse(strCurrentDate);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        format = new SimpleDateFormat("MMM dd,yyyy 'at' hh:mm a");
//        String date = format.format(newDate);

        holder.tvDate.setText(dataSet.get(position).getOrderDate());
        holder.tvStates.setText(dataSet.get(position).getStatus().toString());
        holder.tvItem.setText(dataSet.get(position).getOrderQty().toString());
        holder.tvprice.setText("₹ " + dataSet.get(position).getGrandTotal().toString());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ODid = dataSet.get(position).getOrderId().toString();
                Intent intent = new Intent(activity, MyOrederFullInfoActivity.class);
                intent.putExtra("ORDERID", ODid);
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
