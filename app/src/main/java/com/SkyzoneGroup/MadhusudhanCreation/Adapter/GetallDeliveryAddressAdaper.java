package com.SkyzoneGroup.MadhusudhanCreation.Adapter;

import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.SkyzoneGroup.MadhusudhanCreation.Activity.DeliveryAddressActivity;
import com.SkyzoneGroup.MadhusudhanCreation.R;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.ApiClient;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.ApiInterface;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.Datum;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by kevalt on 4/19/2018.
 */

public class GetallDeliveryAddressAdaper extends RecyclerView.Adapter<View_Holder> {

    List<Datum> dataSet;
    DeliveryAddressActivity dactivity;
    String name, address;
    ApiInterface apiService;
    String USER_ID;
    String USER_EMAIL;
    String addressid;
    private RadioButton selectedRadioButton;


    public GetallDeliveryAddressAdaper(List<Datum> items, DeliveryAddressActivity deliveryAddressActivity) {
        this.dataSet = items;
        this.dactivity = deliveryAddressActivity;
        SharedPreferences prefs = dactivity.getSharedPreferences("myPref", MODE_PRIVATE);
        USER_ID = prefs.getString("USER_ID", "0");
        USER_EMAIL = prefs.getString("USER_EMAIL", null);
    }
    @Override
    public View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        apiService = ApiClient.getClient().create(ApiInterface.class);
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_address_items, parent, false);
        View_Holder holder = new View_Holder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(final View_Holder holder, final int position) {

        name = dataSet.get(position).getFirstname().toString() + " " + dataSet.get(position).getLastname().toString();
        address = dataSet.get(position).getStreet().toString() + "," + dataSet.get(position).getCity().toString() +
                "," + dataSet.get(position).getRegion().toString() + "," + dataSet.get(position).getCountryId().toString();
        holder.tvName.setText(name);
        holder.tvaddressdisplay.setText(address);
        holder.tvmobile.setText(dataSet.get(position).getTelephone().toString());

        RadioButton radioButton = holder.radioButton;
        radioButton.setChecked(dataSet.get(position).isChecked());
        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Datum model : dataSet) {
                    model.setChecked(false);
                }
                dataSet.get(position).setChecked(true);
                addressid = dataSet.get(position).getEntityId();
                dactivity.DeliveryAddressid = Integer.parseInt(addressid);
                dactivity.Deliverypincode = dataSet.get(position).getPostcode();
                //Toast.makeText(dactivity, dataSet.get(position).getPostcode() + " clicked!", Toast.LENGTH_SHORT).show();
                if (null != selectedRadioButton && !view.equals(selectedRadioButton)) {
                    selectedRadioButton.setChecked(false);
                }
                selectedRadioButton = (RadioButton) view;
                selectedRadioButton.setChecked(true);
            }
        });

        holder.ibDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dactivity.addressdelete(dataSet.get(position).getEntityId());
            }
        });
        holder.btVproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dactivity.addressupdate(dataSet.get(position).getEntityId());
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
