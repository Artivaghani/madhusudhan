package com.SkyzoneGroup.MadhusudhanCreation.Adapter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.SkyzoneGroup.MadhusudhanCreation.Activity.ProductInfoActivity;
import com.SkyzoneGroup.MadhusudhanCreation.Activity.SubCatWiseSubCatActivity;
import com.SkyzoneGroup.MadhusudhanCreation.R;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.ApiClient;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.ApiInterface;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.CATwishproduct;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.ResponceSub;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by kevalt on 4/26/2018.
 */

public class SubCatWiseSubCatapter extends RecyclerView.Adapter<View_Holder> {

    List<CATwishproduct> dataSet;
    SubCatWiseSubCatActivity activity;
    ApiInterface apiService;
    Boolean flag = false;
    String name;
    ProgressDialog pDialog;
    String USER_ID;

    public SubCatWiseSubCatapter(SubCatWiseSubCatActivity subCatWiseSubCatActivity, List<CATwishproduct> itemss) {
        dataSet = itemss;
        this.activity = subCatWiseSubCatActivity;
        apiService = ApiClient.getClient().create(ApiInterface.class);

    }

    @Override
    public View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_cat_subpro_item, parent, false);
        View_Holder holder = new View_Holder(v);
        SharedPreferences prefs = activity.getSharedPreferences("myPref", MODE_PRIVATE);
        this.USER_ID= prefs.getString("USER_ID",null);
        return holder;
    }

    @Override
    public void onBindViewHolder(final View_Holder holder, final int position) {
        holder.tvName.setText(dataSet.get(position).getProductName());
        holder.tvprice.setText("Starting at : ₹ " + String.valueOf(dataSet.get(position).getSpecialPrice()));
//        holder.tvStyle.setText(dataSet.get(position).getCatTitle());
        holder.tvStyle.setVisibility(View.GONE);
        holder.tvspecial_price.setPaintFlags( holder.tvspecial_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.tvspecial_price.setText(String.valueOf(dataSet.get(position).getPrice() + ""));
        holder.catalog_type.setText(String.valueOf(dataSet.get(position).getCatalogType()));
        int dis = dataSet.get(position).getDiscount();
        if (dis == 0) {
            holder.tvDiscount.setVisibility(View.GONE);
        } else {
            holder.tvDiscount.setText("(" + dataSet.get(position).getDiscount() + "%OFF)");
        }

//        Picasso.with(activity).load(dataSet.get(position).getCatImage()).fit().centerCrop()
//                .placeholder(R.drawable.icon)
//                .into(holder.ivImage);
//        Glide.with(activity).load(dataSet.get(position).getCatImage()).placeholder(R.drawable.icon)
//                .into(holder.ivImage);
//        if (!(((CATwishproduct) this.dataSet.get(position)).getProductImage() == null || ((CATwishproduct) this.dataSet.get(position)).getProductImage().isEmpty())) {
        Picasso.with(this.activity).load(((CATwishproduct) this.dataSet.get(position)).getProductImage())
                .placeholder(R.drawable.icon)
//                    .resize(800, 500)
//                    .onlyScaleDown()
                .into(holder.ivImage);
//        }

//        if (dataSet.get(position).getIsWishlist()==0)
//        {
//            holder.product_wish.setImageResource(R.drawable.ic_favorite_border_black_24dp);
//            holder.product_wish.setTag("UNFAV");
//        }
//        else
//        {
//            holder.product_wish.setImageResource(R.drawable.ic_favorite_black_24dp);
//            holder.product_wish.setTag("FAV");
//        }

        holder.btVproduct.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
//                int design = dataSet.get(position).getTotalDesign();
//                if (design == 0) {
//                    Toast.makeText(activity, "Product Design Not Available", Toast.LENGTH_LONG).show();
//                } else {
                String catid = dataSet.get(position).getProductId();
                String catname = dataSet.get(position).getProductName();
                Intent intent = new Intent(activity, ProductInfoActivity.class);
                intent.putExtra("PRODUCTID", catid);
                intent.putExtra("PRODUCTNAME", catname);
                activity.startActivity(intent);

                    /// Add step all post cat product api call
//                }
            }
        });
//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int design = dataSet.get(position).getTotalDesign();
//                if (design == 0) {
//                    Toast.makeText(activity, "Product Design Not Available", Toast.LENGTH_LONG).show();
//                } else {
//                    String catid = dataSet.get(position).getCatId();
//                    String catname = dataSet.get(position).getCatName();
//                    Intent intent = new Intent(activity, SubCategoryWiseProductActivity.class);
//                    intent.putExtra("CATID", catid);
//                    intent.putExtra("CATNAME", catname);
//                    activity.startActivity(intent);
//                }
//            }
//        });

        holder.btVproshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String catid = dataSet.get(position).getProductId();
                activity.sharesingleproduct(catid);

            }
        });


//        holder.product_wish.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if ( holder.product_wish.getTag()=="UNFAV")
//                {
//                    Animation rotation = AnimationUtils.loadAnimation(activity, R.anim.button_rotate);
//                    holder.product_wish.startAnimation(rotation);
//
//                    AddWishList(USER_ID,dataSet.get(position).getProductId());
//                    holder.product_wish.setImageResource(R.drawable.ic_favorite_black_24dp);
//                    holder.product_wish.setTag("FAV");
//                }
//                else
//                {
//                    Animation rotation = AnimationUtils.loadAnimation(activity, R.anim.button_rotate1);
//                    holder.product_wish.startAnimation(rotation);
//
//                    RemoveWish(USER_ID, dataSet.get(position).getProductId());
//                    holder.product_wish.setImageResource(R.drawable.ic_favorite_border_black_24dp);
//                    holder.product_wish.setTag("UNFAV");
//                }
//            }
//
//            private void RemoveWish(String user_id, String productId) {
//                apiService = ApiClient.getClient().create(ApiInterface.class);
//
//                Call<ResponceSub> call = apiService.removeWishList(user_id,productId);
//                call.enqueue(new Callback<ResponceSub>() {
//                    @Override
//                    public void onResponse(Call<ResponceSub> call, Response<ResponceSub> response) {
//
////                        int users = response.body().getSuccess();
////                        Log.d(">> ", "Number of WISH: " + users);
//                        // mAdapter.notifyDataSetChanged();
//                    }
//
//                    @Override
//                    public void onFailure(Call<ResponceSub>call, Throwable t) {
//                        // Log error here since request failed
//                        Log.e(">> ", t.toString());
//                    }
//                });
//            }
//
//            private void AddWishList(String user_id, String productId) {
//                apiService = ApiClient.getClient().create(ApiInterface.class);
//
//                Log.d(">> ", "AddWishList: "+user_id+" >> "+productId);
//                Call<ResponceSub> call = apiService.addWish(user_id,productId);
//                call.enqueue(new Callback<ResponceSub>() {
//                    @Override
//                    public void onResponse(Call<ResponceSub> call, Response<ResponceSub> response) {
////                        int users = response.body().getSuccess();
////                        Log.d(">> ", "Number of WISH: " + users);
//                    }
//
//                    @Override
//                    public void onFailure(Call<ResponceSub>call, Throwable t) {
//                        // Log error here since request failed
//                        Log.e(">> ", t.toString());
//                    }
//                });
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public File getDisc() {
        String t = getCurrentDateAndTime();
        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        return new File(file, "Madhusudhan Creation");
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private String getCurrentDateAndTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }
}
