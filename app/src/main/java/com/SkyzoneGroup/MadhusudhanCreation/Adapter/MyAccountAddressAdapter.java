package com.SkyzoneGroup.MadhusudhanCreation.Adapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.SkyzoneGroup.MadhusudhanCreation.Activity.MyAccountAddressActivity;
import com.SkyzoneGroup.MadhusudhanCreation.R;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.ApiClient;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.ApiInterface;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.Datum;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by kevalt on 4/20/2018.
 */

public class MyAccountAddressAdapter extends RecyclerView.Adapter<View_Holder>{

    List<Datum> dataSet;
    MyAccountAddressActivity activity;
    String name, address;
    ApiInterface apiService;
    AlertDialog.Builder alertDialogBuilder1;
    AlertDialog alertDialog1;
    Button btsave;
    String country = "IN";
    ImageView icon_cancel;
    TextInputEditText etFName, tv_phone_no, tv_add_one, tv_city, tv_add_two, tv_state, tv_pincode, etLName;
    String LName, FName, phone_no, add_one, city, add_two, state, pincode;
    String USER_ID;
    String USER_EMAIL;
    ProgressDialog pDialog;
    String addressid;
    private static CheckBox lastChecked = null;
    private static int lastCheckedPos = 0;
    private RadioButton selectedRadioButton;

    public MyAccountAddressAdapter(List<Datum> items, MyAccountAddressActivity myAccountAddressActivity) {

        this.dataSet = items;
        this.activity = myAccountAddressActivity;
        SharedPreferences prefs = activity.getSharedPreferences("myPref", MODE_PRIVATE);
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
        //   holder.radioButton

        RadioButton radioButton = holder.radioButton;
        radioButton.setChecked(dataSet.get(position).isChecked());
        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Set unchecked all other elements in the list, so to display only one selected radio button at a time
                for (Datum model : dataSet) {
                    model.setChecked(false);
                }

                // Set "checked" the model associated to the clicked radio button
                dataSet.get(position).setChecked(true);
                // dataSet.get(pos).setChecked(true);
                addressid = dataSet.get(position).getEntityId();
                activity.Addressid = Integer.parseInt(addressid);
                //    Toast.makeText(activity, dataSet.get(position).getEntityId() + " clicked!", Toast.LENGTH_SHORT).show();

                // If current view (RadioButton) differs from previous selected radio button, then uncheck selectedRadioButton
                if (null != selectedRadioButton && !view.equals(selectedRadioButton)) {
                    selectedRadioButton.setChecked(false);
                }

                // Replace the previous selected radio button with the current (clicked) one, and "check" it
                selectedRadioButton = (RadioButton) view;
                selectedRadioButton.setChecked(true);
            }
        });

        holder.ibDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.addressdelete(dataSet.get(position).getEntityId());
            }
        });
        holder.btVproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.addressupdate(dataSet.get(position).getEntityId());
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
