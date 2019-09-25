package com.SkyzoneGroup.MadhusudhanCreation.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.SkyzoneGroup.MadhusudhanCreation.Adapter.SubCatWiseSubCatapter;
import com.SkyzoneGroup.MadhusudhanCreation.BuildConfig;
import com.SkyzoneGroup.MadhusudhanCreation.Model.CartList;
import com.SkyzoneGroup.MadhusudhanCreation.R;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.ApiClient;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.ApiInterface;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.CATResponce;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.CATwishproduct;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.Datum;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.MultipleImage;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.Responce;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.ResponceCartItem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubCatWiseSubCatActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ApiInterface apiService;
    ImageView ivnotfound;
    String USER_ID, USER_EMAIL;
    TextView textCartItemCount;
    int mCartItemCount = 0;
    ProgressDialog pDialog;
    //Shareshre
    static String Descre;
    private long refid;
    ArrayList<Long> list = new ArrayList<>();
    ArrayList<String> pathlist = new ArrayList<String>();
    RelativeLayout popupshare;
    RelativeLayout rlShareImage, rlShareText, rlShareOther;
    TextView txtShareImage, txtShareText;
    public static ArrayList<Long> listt = new ArrayList<>();
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    Activity activity;
    private static final int REQUEST_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_cat_wise_sub_cat);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        String catid = getIntent().getStringExtra("CATID");
        String namwe = getIntent().getStringExtra("CATNAME");
        SharedPreferences prefs = getSharedPreferences("myPref", MODE_PRIVATE);
        USER_ID = prefs.getString("USER_ID", "0");
        String USER_NAME = prefs.getString("USER_NAME", null);
        USER_EMAIL = prefs.getString("USER_EMAIL", null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(namwe == null)
        {
            getSupportActionBar().setTitle("Offer Zone");
        }else {
            getSupportActionBar().setTitle(namwe);
        }

        ///////share//////////////
        popupshare = findViewById(R.id.popupshare);
        rlShareImage = findViewById(R.id.rlShareImage);
        rlShareText = findViewById(R.id.rlShareText);
        txtShareImage = findViewById(R.id.txtShareImage);
        txtShareText = findViewById(R.id.txtShareText);
        ///////share over //////////////

        if (Build.VERSION.SDK_INT >= 23) {
            // Check if we have write permission
            int permission2 = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int permission3 = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

            if (permission2 != PackageManager.PERMISSION_GRANTED ||
                    permission3 != PackageManager.PERMISSION_GRANTED) {
                // We don't have permission so prompt the user
                ActivityCompat.requestPermissions(
                        this,
                        PERMISSIONS_STORAGE,
                        REQUEST_PERMISSION
                );
            }
        }
        ivnotfound = (ImageView) findViewById(R.id.iv_notfound);

        recyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        catalogcategory(catid,USER_ID);
        fatchCartItem(USER_ID);
    }

    private void catalogcategory(String catid,String USER_ID) {

        Log.d("MAINDATA", "DATATAMAINA" + catid);
        pDialog = new ProgressDialog(SubCatWiseSubCatActivity.this);
        pDialog.setMessage("Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
//        Toast.makeText(this, catid, Toast.LENGTH_SHORT).show();
        Call<CATResponce> call = apiService.GetSubCategoryWiseProduct(catid,USER_ID);
        call.enqueue(new Callback<CATResponce>() {
            @Override
            public void onResponse(Call<CATResponce> call, retrofit2.Response<CATResponce> response) {

                List<CATwishproduct> itemss = response.body().getData();
                if (response.body().getSuccess() == 0) {
                    pDialog.dismiss();
                    recyclerView.setVisibility(View.GONE);
                    ivnotfound.setVisibility(View.VISIBLE);
                } else {
                    pDialog.dismiss();
                    ivnotfound.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    recyclerView.setAdapter(new SubCatWiseSubCatapter(SubCatWiseSubCatActivity.this, itemss));
                }

            }

            @Override
            public void onFailure(Call<CATResponce> call, Throwable t) {
                // Log error here since request failed
                Log.e(">>>>>>", t.toString());
                //  imageView.setVisibility(View.GONE);
                pDialog.dismiss();
                Toast.makeText(SubCatWiseSubCatActivity.this, "Try..", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void fatchCartItem(String user_id) {
        Call<ResponceCartItem> call = apiService.getCartDetail(user_id);
        call.enqueue(new Callback<ResponceCartItem>() {
            @Override
            public void onResponse(Call<ResponceCartItem> call, Response<ResponceCartItem> response) {
                List<CartList> items = response.body().getData();
                try {
                    Log.d(">>> ", "Number of Cart Item received001:" + items.size());
                    mCartItemCount = items.size();
                    setupBadge(mCartItemCount);
                } catch (Exception ex) {
                }

            }

            @Override
            public void onFailure(Call<ResponceCartItem> call, Throwable t) {
                Log.e(">> ", t.toString());
            }
        });
    }

    public void setupBadge(int upBadge) {
        if (textCartItemCount != null) {
            if (this.mCartItemCount == 0) {
                if (textCartItemCount.getVisibility() != View.GONE) {
                    textCartItemCount.setVisibility(View.GONE);
                }
            } else {
                textCartItemCount.setText(String.valueOf(Math.min(this.mCartItemCount, upBadge)));
                if (textCartItemCount.getVisibility() != View.VISIBLE) {
                    textCartItemCount.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.mycart, menu);
//        final MenuItem menuItem = menu.findItem(R.id.action_cart);
//        View actionView = MenuItemCompat.getActionView(menuItem);
//        textCartItemCount = (TextView) actionView.findViewById(R.id.cart_count);
//
//        setupBadge(mCartItemCount);
//        actionView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(SubCatWiseSubCatActivity.this, CartActivity.class);
//                startActivity(i);
//            }
//        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {

        switch (menuItem.getItemId()) {

            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    protected void onStart() {
        super.onStart();
        fatchCartItem(USER_ID);

    }

    public void sharesingleproduct(String Product_Id) {

//        Call<Responce> call = apiService.GetcatShareProduct(SUB_Cat);
//        call.enqueue(new Callback<Responce>() {
//            @Override
//            public void onResponse(Call<Responce> call, retrofit2.Response<Responce> response) {
//                List<Datum> items = response.body().getData();
//                List<String> imagurl = response.body().getImages();
//                if (response.body().getSuccess() == 0) {
//                    //   llmain.setVisibility(View.VISIBLE);
//                } else {
//                    popupshare.setVisibility(View.VISIBLE);
//                    rlShareImage.setBackgroundResource(R.drawable.round_share);
//                    txtShareImage.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.drak_grey));
//                    rlShareText.setBackgroundResource(R.drawable.round_share);
//                    txtShareText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.drak_grey));
//                    Spanned spanned = Html.fromHtml(response.body().getDescription());
//                    Descre = spanned.toString();
////                    ArrayList<String> dataurl = new ArrayList<>();
////                    List<MultipleImage> multipleImages = items.get(0).getMultipleImages();
////                    for (int i = 0; i < multipleImages.size(); i++) {
////                        dataurl.add(multipleImages.get(i).getImage());
////                    }
////                    SetMainImage(items.get(0).getProductImage());
//                    if (imagurl.size() > 0) {
//
//                        DownloadManager downloadManager = (DownloadManager) getApplicationContext().getSystemService(Context.DOWNLOAD_SERVICE);
//                        for (int i = 0; i < imagurl.size(); i++) {
//                            Uri Download_Uri = Uri.parse(imagurl.get(i));
//                            DownloadManager.Request request = new DownloadManager.Request(Download_Uri);
//                            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
//                            request.setAllowedOverRoaming(false);
//                            request.setTitle("Images Downloading " + "Sample_" + i + ".png");
//                            request.setDescription("Downloading " + "Sample_" + i + ".png");
//                            request.setVisibleInDownloadsUi(true);
//                            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/happy mart/" + "/" + "Sample_" + i + ".png");
//                            try {
//                                refid = downloadManager.enqueue(request);
//                                list.add(refid);
//                                pathlist.add(Environment.getExternalStorageDirectory().toString() + "/happy mart/" + "/" + "Sample_" + i + ".png");
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//
//                        }
//                    } else {
//                        Intent intent1 = new Intent();
//                        intent1 = new Intent("android.intent.action.SEND");
//                        intent1.setPackage("com.whatsapp");
//                        intent1.setType("text/plain");
//                        intent1.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                        intent1.putExtra(Intent.EXTRA_TEXT, Descre);
//                        try {
//                            startActivityForResult(intent1, 2);
//                        } catch (Exception e2s) {
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Responce> call, Throwable t) {
//                // Log error here since request failed
//                Log.e(">>>>>>", t.toString());
//                Toast.makeText(SubCatWiseSubCatActivity.this, "Try..", Toast.LENGTH_SHORT).show();
//            }
//        });
        Call<Responce> call = apiService.Getproductinfo(Product_Id);
        call.enqueue(new Callback<Responce>() {
            @Override
            public void onResponse(Call<Responce> call, retrofit2.Response<Responce> response) {
                List<Datum> items = response.body().getData();
                Log.d("", "Numbe " + items.size());
                if (response.body().getData().size() == 0) {
                    //   llmain.setVisibility(View.VISIBLE);
                } else {
                    popupshare.setVisibility(View.VISIBLE);
                    rlShareImage.setBackgroundResource(R.drawable.round_share);
                    txtShareImage.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.drak_grey));
                    rlShareText.setBackgroundResource(R.drawable.round_share);
                    txtShareText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.drak_grey));
                    Descre = "Name :-" + items.get(0).getProductName().toString() + "\n" +
                            "SkuCode :-"  + items.get(0).getSku().toString() + "\n" +
                            "Price :-"  + items.get(0).getSpecialPrice().toString()+ "\n" +
                            "Description :-" + "\n" + Html.fromHtml(items.get(0).getDescription().toString());

                    ArrayList<String> dataurl = new ArrayList<>();
                    List<MultipleImage> multipleImages = items.get(0).getMultipleImages();
                    for (int i = 0; i < multipleImages.size(); i++) {
                        dataurl.add(multipleImages.get(i).getImage());
                    }
//                    SetMainImage(items.get(0).getProductImage());
                    //      Log.d("DATAITEM", "DESCRIPTION" + description);
                    if (dataurl.size() > 0) {

                        DownloadManager downloadManager = (DownloadManager)getApplicationContext().getSystemService(Context.DOWNLOAD_SERVICE);
                        for (int i = 0; i < dataurl.size(); i++) {
                            Uri Download_Uri = Uri.parse(dataurl.get(i));
                            DownloadManager.Request request = new DownloadManager.Request(Download_Uri);
                            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                            request.setAllowedOverRoaming(false);
                            request.setTitle("Images Downloading " + "Sample_" + i + ".png");
                            request.setDescription("Downloading " + "Sample_" + i + ".png");
                            request.setVisibleInDownloadsUi(true);
                            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/Madhusudhan Creation/" + "/" + "Sample_" + i + ".png");
                            try {
                                refid = downloadManager.enqueue(request);
                                list.add(refid);
                                pathlist.add(Environment.getExternalStorageDirectory().toString() + "/Madhusudhan Creation/" + "/" + "Sample_" + i + ".png");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    } else {
                        Intent intent1 = new Intent();
                        intent1 = new Intent("android.intent.action.SEND");
                        intent1.setPackage("com.whatsapp");
                        intent1.setType("text/plain");
                        intent1.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        intent1.putExtra(Intent.EXTRA_TEXT, Descre);
                        try {
                            startActivityForResult(intent1, 2);
                        } catch (Exception e2s) {
                        }
                    }
                }

            }
            @Override
            public void onFailure(Call<Responce> call, Throwable t) {
                // Log error here since request failed
                Log.e(">>>>>>", t.toString());
                Toast.makeText(getApplicationContext(), "Try..", Toast.LENGTH_SHORT).show();
            }
        });
    }
    //////////////////share reci..///////////////////////////




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getIntent().removeExtra("android.intent.extra.STREAM");

//        if (requestCode == 1) {
//            rlShareImage.setBackgroundResource(R.drawable.round_share_completed);
//            txtShareImage.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.whats_color));
//            File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/Madhusudhan Creation");
//            if (dir.isDirectory()) {
//                String[] children = dir.list();
//                for (int i = 0; i < children.length; i++) {
//                    new File(dir, children[i]).delete();
//                }
//            }
//            Toast.makeText(getApplicationContext(), "Share Description", Toast.LENGTH_LONG).show();
//
//            final Handler handler = new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    //Do something after 100ms
//                    Intent intent1 = new Intent();
//                    intent1 = new Intent("android.intent.action.SEND");
//                    intent1.setPackage("com.whatsapp");
//                    intent1.setType("text/plain");
//                    intent1.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                    intent1.putExtra(Intent.EXTRA_TEXT, Descre);
//                    try {
//                        startActivityForResult(intent1, 2);
//                    } catch (Exception e2s) {
//                    }
//
//                }
//            }, 2000);
//
//        } else if (requestCode == 2) {
//            rlShareText.setBackgroundResource(R.drawable.round_share_completed);
//            txtShareText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.whats_color));
//            popupshare.setVisibility(View.GONE);
//        }

        if (requestCode == 1) {
            rlShareImage.setBackgroundResource(R.drawable.round_share_completed);
            txtShareImage.setTextColor(ContextCompat.getColor(getApplicationContext().getApplicationContext(), R.color.whats_color));
            File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/Madhusudhan Creation");
            if (dir.isDirectory()) {
                String[] children = dir.list();
                for (int i = 0; i < children.length; i++) {
                    new File(dir, children[i]).delete();
                }
            }
            Toast.makeText(getApplicationContext().getApplicationContext(), "Share Description", Toast.LENGTH_LONG).show();

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Do something after 100ms
                    Intent intent1 = new Intent();
                    intent1 = new Intent("android.intent.action.SEND");
                    intent1.setPackage("com.whatsapp");
                    intent1.setType("text/plain");
                    intent1.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent1.putExtra(Intent.EXTRA_TEXT, Descre);
                    try {
                     startActivityForResult(intent1, 2);
                    } catch (Exception e2s) {
                    }

                }
            }, 2000);
            popupshare.setVisibility(View.GONE);

        } else if (requestCode == 2) {

            rlShareText.setBackgroundResource(R.drawable.round_share_completed);
            txtShareText.setTextColor(ContextCompat.getColor(getApplicationContext().getApplicationContext(), R.color.whats_color));
            popupshare.setVisibility(View.GONE);
        }
    }


    BroadcastReceiver onComplete = new BroadcastReceiver() {
        public void onReceive(Context ctxt, Intent intent) {

//            long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
//            Log.e("IN", "" + referenceId);
//            list.remove(referenceId);
//
//            if (list.isEmpty()) {
//
//                ArrayList<Uri> files_list = new ArrayList<Uri>();
//                File files = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/Madhusudhan Creation");
//                File[] F = files.listFiles();
//                for (int i = 0; i < F.length; i++) {
//                    String s = F[i].getName();
//                    if (F[i].getName().contains("Sample_")) {
//                        Uri uri1 = FileProvider.getUriForFile(getApplicationContext(), BuildConfig.APPLICATION_ID + ".provider", F[i]);
//                        files_list.add(uri1);
//                    }
//
//                }
//
//                Intent intent1 = new Intent();
//                intent1 = new Intent("android.intent.action.SEND");
//                intent1.setPackage("com.whatsapp");
//                intent1.setAction(Intent.ACTION_SEND_MULTIPLE);
//                intent1.setType("image/jpeg");
//                intent1.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                intent1.removeExtra("android.intent.extra.STREAM");
//                intent1.putParcelableArrayListExtra("android.intent.extra.STREAM", files_list);
//                try {
//                    startActivityForResult(intent1, 1);
//                } catch (Exception e2s) {
//                }
//
//            }
            long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            Log.e("IN", "" + referenceId);
            listt.remove(referenceId);

            if (listt.isEmpty()) {

                ArrayList<Uri> files_list = new ArrayList<Uri>();
                File files = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/Madhusudhan Creation");
                File[] F = files.listFiles();
                for (int i = 0; i < F.length; i++) {
                    String s = F[i].getName();
                    if (F[i].getName().contains("Sample_")) {
                        Uri uri1 = FileProvider.getUriForFile(getApplicationContext() .getApplicationContext(), BuildConfig.APPLICATION_ID + ".provider", F[i]);
                        files_list.add(uri1);
                    }

                }

                Intent intent1 = new Intent();
                intent1 = new Intent("android.intent.action.SEND");
                intent1.setPackage("com.whatsapp");
                intent1.setAction(Intent.ACTION_SEND_MULTIPLE);
                intent1.setType("image/jpeg");
                intent1.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent1.removeExtra("android.intent.extra.STREAM");
                intent1.putParcelableArrayListExtra("android.intent.extra.STREAM", files_list);
                try {
                    startActivityForResult(intent1, 1);
                } catch (Exception e2s) {
                }

            }
        }
    };

    @Override
    protected void onPause() {
        unregisterReceiver(onComplete);
        super.onPause();
    }


    @Override
    protected void onResume() {
        getIntent().removeExtra("android.intent.extra.STREAM");
        registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        super.onResume();
    }

}
