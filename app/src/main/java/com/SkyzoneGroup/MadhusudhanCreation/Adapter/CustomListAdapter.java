package com.SkyzoneGroup.MadhusudhanCreation.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.SkyzoneGroup.MadhusudhanCreation.Activity.SubCategoryWiseProductActivity;
import com.SkyzoneGroup.MadhusudhanCreation.R;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.Datum;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by kevalt on 4/3/2018.
 */

public class CustomListAdapter extends RecyclerView.Adapter<View_Holder> {
    List<Datum> dataSet;
    Context dactivity;
    ProgressDialog pDialog;
    public CustomListAdapter(FragmentActivity activity, List<Datum> users) {
        this.dactivity = activity;
        this.dataSet = users;
    }

    @Override
    public View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_cat_subpro_item, parent, false);
        View_Holder holder = new View_Holder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(View_Holder holder, final int position) {
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

//        Picasso.with(dactivity).load(dataSet.get(position).getCatImage()).fit().centerCrop()
//                .placeholder(R.drawable.icon)
//                .into(holder.ivImage);
//        Glide.with(dactivity).load(dataSet.get(position).getCatImage()).placeholder(R.drawable.icon)
//                .into(holder.ivImage);
        if (!(((Datum) this.dataSet.get(position)).getCatImage() == null || ((Datum) this.dataSet.get(position)).getCatImage().isEmpty())) {
            Picasso.with(this.dactivity).load(((Datum) this.dataSet.get(position)).getCatImage()).resize(512, 288).onlyScaleDown().into(holder.ivImage);
        }



//        holder.tvName.setText(dataSet.get(position).getCatName());
//        holder.tvprice.setText("₹ " + String.valueOf(dataSet.get(position).getSpecialPrice()));
//        Glide.with(dactivity).load(dataSet.get(position).getProductImage()).placeholder(R.drawable.icon)
//                .into(holder.ivImage);
        holder.btVproduct.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                int design = dataSet.get(position).getTotalDesign();
                if (design == 0) {
                    Toast.makeText(dactivity, "Product Design Not Available", Toast.LENGTH_LONG).show();
                } else {
                    String catid = dataSet.get(position).getCatId();
                    String catname = dataSet.get(position).getCatName();
                    Intent intent = new Intent(dactivity, SubCategoryWiseProductActivity.class);
                    intent.putExtra("CATID", catid);
                    intent.putExtra("CATNAME", catname);
                    dactivity.startActivity(intent);
                }
            }
        });
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int design = dataSet.get(position).getTotalDesign();
                if (design == 0) {
                    Toast.makeText(dactivity, "Product Design Not Available", Toast.LENGTH_LONG).show();
                } else {
                    String catid = dataSet.get(position).getCatId();
                    String catname = dataSet.get(position).getCatName();
                    Intent intent = new Intent(dactivity, SubCategoryWiseProductActivity.class);
                    intent.putExtra("CATID", catid);
                    intent.putExtra("CATNAME", catname);
                    dactivity.startActivity(intent);
                }
            }
        });
        holder.btVproshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pDialog = new ProgressDialog(dactivity);
                pDialog.setMessage("Please wait...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(false);
                pDialog.show();
                Glide.with(dactivity)
                        .load(dataSet.get(position).getCatImage())
                        .asBitmap()
                        .into(new SimpleTarget<Bitmap>() {
                            @RequiresApi(api = Build.VERSION_CODES.N)
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                                saveImage(resource, dataSet.get(position).getCatName());
                            }
                        });
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
    @RequiresApi(api = Build.VERSION_CODES.N)
    private String saveImage(Bitmap resource, String productName) {
        String savedImagePath = null;
        FileOutputStream fos = null;
        File file = getDisc();
        if (!file.exists() && !file.mkdirs()) {
            Toast.makeText(dactivity, "Can't create directory to store image", Toast.LENGTH_LONG).show();
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyymmsshhmmss");
        String date = simpleDateFormat.format(new Date());
        String name = "FileName" + date + ".jpg";
        String file_name = file.getAbsolutePath() + "/" + name;
        File new_file = new File(file_name);
        try {

            fos = new FileOutputStream(file_name);
            resource.compress(Bitmap.CompressFormat.PNG, 100, fos);
            pDialog.dismiss();
            Toast.makeText(dactivity, "Save success", Toast.LENGTH_LONG).show();
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        galleryAddPic(new_file,productName);

        Uri imageUri = Uri.fromFile(new_file);
        savedImagePath = imageUri.getPath().toString();
        return savedImagePath;
    }

    private void galleryAddPic(File savedImagePath, String productName) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri imageUri = Uri.fromFile(savedImagePath);
        mediaScanIntent.setData(imageUri);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND_MULTIPLE);
        intent.setPackage("com.whatsapp");
        intent.putExtra(Intent.EXTRA_TEXT, productName);
        //intent.putExtra(Intent.EXTRA_HTML_TEXT, "<html>Text caption message!!");
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_STREAM, imageUri);
//        intent.putExtra(Intent.EXTRA_STREAM, mediaScanIntent);
        intent.setType("image/jpeg");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        try {
            dactivity.startActivity(intent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(dactivity, "Whatsapp have not been installed.", Toast.LENGTH_SHORT).show();
        }

    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public File getDisc() {
        String t = getCurrentDateAndTime();
        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        return new File(file, "First_Choise");
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private String getCurrentDateAndTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }
}
