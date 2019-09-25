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
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.SkyzoneGroup.MadhusudhanCreation.Activity.CatWiseProActivity;
import com.SkyzoneGroup.MadhusudhanCreation.Activity.SubCategoryWiseProductActivity;
import com.SkyzoneGroup.MadhusudhanCreation.Model.Subcatdatamodel;
import com.SkyzoneGroup.MadhusudhanCreation.R;
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

public class SubCatWiseProadapter extends RecyclerView.Adapter<View_Holder> {

    List<Subcatdatamodel> dataSet;
    Context activity;
    ProgressDialog pDialog;
    String sizeshow;

//    public SubCatWiseProadapter(FragmentActivity activity, List<Datum> items) {
//        this.dataSet = items;
//       this.activity = activity;
//    }

    public SubCatWiseProadapter(CatWiseProActivity catWiseProActivity, List<Subcatdatamodel> items) {
        this.dataSet = items;
        this.activity = catWiseProActivity;
//        this.sizeshow = citiesCommaSeparated;
    }

    @Override
    public View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_cat_subpro_item, parent, false);
        View_Holder holder = new View_Holder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final View_Holder holder, final int position) {

        holder.tvName.setText(dataSet.get(position).getCatName());
        holder.tvprice.setText("Starting at : ₹ " + String.valueOf(dataSet.get(position).getStartingPrice()));
        holder.tvStyle.setText(dataSet.get(position).getCatTitle());
        holder.tvspecial_price.setText(String.valueOf(dataSet.get(position).getTotalDesign() + " Desings"));
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
        if (!(((Subcatdatamodel) this.dataSet.get(position)).getCatImage() == null || ((Subcatdatamodel) this.dataSet.get(position)).getCatImage().isEmpty())) {
            Picasso.with(this.activity).load(((Subcatdatamodel) this.dataSet.get(position)).getCatImage()).resize(800, 450).onlyScaleDown().into(holder.ivImage);
        }

//        holder.tvName.setText(dataSet.get(position).getProductName());
//        if (sizeshow.isEmpty()) {
//            holder.tvSize.setText("Available in free Size");
//        } else {
//            holder.tvSize.setText("Available in " + sizeshow);
//        }
//
//
//        holder.tvprice.setText("₹ " + String.valueOf(dataSet.get(position).getSpecialPrice()));
//        Glide.with(activity).load(dataSet.get(position).getProductImage())
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
//        Glide.with(activity).load(dataSet.get(position).getProductImage()).placeholder(R.drawable.icon)
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
//                String catid = dataSet.get(position).getProductId();
//                String catname = dataSet.get(position).getProductName();
//                Intent intent = new Intent(activity, ProductInfoActivity.class);
//                intent.putExtra("PRODUCTID", catid);
//                intent.putExtra("PRODUCTNAME", catname);
//                activity.startActivity(intent);
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
//                String catid = dataSet.get(position).getProductId();
//                String catname = dataSet.get(position).getProductName();
//                Intent intent = new Intent(activity, ProductInfoActivity.class);
//                intent.putExtra("PRODUCTID", catid);
//                intent.putExtra("PRODUCTNAME", catname);
//                activity.startActivity(intent);
            }
        });
        holder.btVproshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                pDialog = new ProgressDialog(activity);
//                pDialog.setMessage("Please wait...");
//                pDialog.setIndeterminate(false);
//                pDialog.setCancelable(false);
//                pDialog.show();
//                Glide.with(activity)
//                        .load(dataSet.get(position).getProductImage())
//                        .asBitmap()
//                        .into(new SimpleTarget<Bitmap>(100, 100) {
//                            @RequiresApi(api = Build.VERSION_CODES.N)
//                            @Override
//                            public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
//                                saveImage(resource, dataSet.get(position).getProductName());
//                            }
//                        });


            }
        });

    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private String saveImage(Bitmap resource, String catName) {
        String savedImagePath = null;
        FileOutputStream fos = null;
        File file = getDisc();
        if (!file.exists() && !file.mkdirs()) {
            Toast.makeText(activity, "Can't create directory to store image", Toast.LENGTH_LONG).show();
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyymmsshhmmss");
        String date = simpleDateFormat.format(new Date());
        String name = "FileName" + date + ".jpg";
        String file_name = file.getAbsolutePath() + "/" + name;
        File new_file = new File(file_name);
        try {

            fos = new FileOutputStream(file_name);
            resource.compress(Bitmap.CompressFormat.JPEG, 90, fos);
            pDialog.dismiss();
            Toast.makeText(activity, "Save success", Toast.LENGTH_LONG).show();
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        galleryAddPic(new_file, catName);

        Uri imageUri = Uri.fromFile(new_file);
        savedImagePath = imageUri.getPath().toString();
        return savedImagePath;
    }

    private void galleryAddPic(File savedImagePath, String catName) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri imageUri = Uri.fromFile(savedImagePath);
        mediaScanIntent.setData(imageUri);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND_MULTIPLE);
        intent.setPackage("com.whatsapp");
        intent.putExtra(Intent.EXTRA_TEXT, catName);
        //intent.putExtra(Intent.EXTRA_HTML_TEXT, "<html>Text caption message!!");
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_STREAM, imageUri);
//        intent.putExtra(Intent.EXTRA_STREAM, mediaScanIntent);
        intent.setType("image/jpeg");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        try {
            activity.startActivity(intent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(activity, "Whatsapp have not been installed.", Toast.LENGTH_SHORT).show();
        }

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
