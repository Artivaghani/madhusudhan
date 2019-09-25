package com.SkyzoneGroup.MadhusudhanCreation.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.SkyzoneGroup.MadhusudhanCreation.Activity.SubCategoryWiseProductActivity;
import com.SkyzoneGroup.MadhusudhanCreation.R;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.ApiClient;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.ApiInterface;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.Datum;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevalt on 3/15/2018.
 */

public class Catalog_CatAdapter extends RecyclerView.Adapter<View_Holder> {

    private long refid;
    ArrayList<Long> list = new ArrayList<>();
    ArrayList<String> pathlist = new ArrayList<String>();
    List<Datum> dataSet;
    Context activity;
    int height;
    Boolean flag = false;
    ApiInterface apiService;
   public static String descrepstion;
    ProgressDialog pDialog;
    private String[] urlArray = null;
    ClickShare clickShare;
    public ProgressBar progressBar;
    public interface ClickShare
    {
        public void ClickOnShare(int position);
    }

    public void RegisterClickShareClick(ClickShare clickShare)
    {
        this.clickShare=clickShare;
    }

    public Catalog_CatAdapter(List<Datum> items, FragmentActivity activity) {
        dataSet = items;
        this.activity = activity;
    }

    @Override
    public View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_cat_subpro_item, parent, false);
        View_Holder holder = new View_Holder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final View_Holder holder, final int position) {
        apiService = ApiClient.getClient().create(ApiInterface.class);
        holder.tvName.setText(dataSet.get(position).getCatName());
        holder.tvStyle.setText(dataSet.get(position).getCatTitle());
        int dis = dataSet.get(position).getDiscount();
        if (dis == 0) {
            holder.tvDiscount.setVisibility(View.GONE);
        } else {
            holder.tvDiscount.setText("(" + dataSet.get(position).getDiscount() + "%OFF)");
        }
        holder.tvspecial_price.setText(String.valueOf(dataSet.get(position).getTotalDesign() + " Desings"));
        holder.tvprice.setText("Staring Price : ₹ " + String.valueOf(dataSet.get(position).getStartingPrice()));

//        Picasso.with(activity).load(dataSet.get(position).getCatImage()).fit().centerCrop()
//                .placeholder(R.drawable.icon)
//                .into(holder.ivImage);
        if (!(((Datum) this.dataSet.get(position)).getImage() == null || ((Datum) this.dataSet.get(position)).getImage().isEmpty())) {
            Picasso.with(this.activity).load(((Datum) this.dataSet.get(position)).getImage()).resize(800, 450).onlyScaleDown().into(holder.ivImage);
        }
//        Glide.with(activity).load(dataSet.get(position).getCatImage()).placeholder(R.drawable.icon)
//                .into(holder.ivImage);
        holder.btVproduct.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                int design = dataSet.get(position).getTotalDesign();
                if (design == 0) {
                    Toast.makeText(activity, "Product Design Not Available", Toast.LENGTH_LONG).show();
                } else {
                    String catid = dataSet.get(position).getCatId();
                    String catname = dataSet.get(position).getCatName();
                    Intent intent = new Intent(activity, SubCategoryWiseProductActivity.class);
                    intent.putExtra("CATID", catid);
                    intent.putExtra("CATNAME", catname);
                    activity.startActivity(intent);
                }

            }
        });
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int design = dataSet.get(position).getTotalDesign();
                if (design == 0) {
                    Toast.makeText(activity, "Product Design Not Available", Toast.LENGTH_LONG).show();
                } else {
                    String catid = dataSet.get(position).getCatId();
                    String catname = dataSet.get(position).getCatName();
                    Intent intent = new Intent(activity, SubCategoryWiseProductActivity.class);
                    intent.putExtra("CATID", catid);
                    intent.putExtra("CATNAME", catname);
                    activity.startActivity(intent);
                }

            }
        });
        holder.btVproshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  String SUB_Cat = dataSet.get(position).getCatId();
//                String SUB_Cat = dataSet.get(position).getCatId();
                MainFragment fragment = new MainFragment();
                ((MainFragment) fragment).sharedata(SUB_Cat);
             //   activity.sharedata(SUB_Cat);
              //  fragment.myMethod();

                String catimgeurl =  dataSet.get(position).getCatImage();*/


                if(clickShare!=null)
                {
                    clickShare.ClickOnShare(position);
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return dataSet.size();
       // return 20;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

    }

}
