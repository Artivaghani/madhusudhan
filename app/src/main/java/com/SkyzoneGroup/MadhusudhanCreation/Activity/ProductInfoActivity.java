package com.SkyzoneGroup.MadhusudhanCreation.Activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.SkyzoneGroup.MadhusudhanCreation.Inquiry;
import com.balysv.materialripple.MaterialRippleLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.SkyzoneGroup.MadhusudhanCreation.Adapter.MultipleImage_Adapter;
import com.SkyzoneGroup.MadhusudhanCreation.BuildConfig;
import com.SkyzoneGroup.MadhusudhanCreation.Model.CartList;
import com.SkyzoneGroup.MadhusudhanCreation.Model.DialogActivity;
import com.SkyzoneGroup.MadhusudhanCreation.Model.TouchImageView;
import com.SkyzoneGroup.MadhusudhanCreation.R;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.ApiClient;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.ApiInterface;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.Datum;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.MultipleImage;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.Option;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.Responce;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.ResponceCartItem;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductInfoActivity extends AppCompatActivity {
    ApiInterface apiService;
    RelativeLayout llmain;
    TextView tvProDes, tvQuantity;
    TextView submitcheckondelivery;
    ImageView ivImage, ivImage2;
    TextView tvName, tvDiscount, tvspecial_price, tvprice, tvskuid, tvshow;
    Button btnAddToCart, btVproceed;
    TextView textCartItemCount;
    int mCartItemCount = 0;
    String Product_Id;
    ImageView ibAdd, ibSub;
    int Counter = 1;
    int ShippingCounter = 1;
    public static float MainShippingCounter;
    public static float Shippingweight;
    ProgressDialog pDialog;
    String USER_ID;
    String url;
    public Dialog dialog;
    String postcode;
    ImageView error;
    EditText etPincode;
    TextView tvdata;
    LinearLayout ll_locastion, btVshare;
    List<Datum> items;
    Spinner spcountry;
    List<Option> options;
    MaterialRippleLayout iv_copy;
    String descre;
    String optionsid = "Free Size";
    String suboptionid = "Free Size";
    RecyclerView recyclerview;
    List<MultipleImage> multipleImages;
    TextView txtSupplierInfo;
    LinearLayout ll_panle;
    TextView tvshipping;
    int datamain;

    //Share
    private long refid;
    ArrayList<Long> list = new ArrayList<>();
    ArrayList<String> pathlist = new ArrayList<String>();
    RelativeLayout popupshare;
    RelativeLayout rlShareImage, rlShareText,rlShareOther;
    TextView txtShareImage, txtShareText;

    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_info);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Product_Id = getIntent().getStringExtra("PRODUCTID");
        String PRODUCTNAME = getIntent().getStringExtra("PRODUCTNAME");

        //  Toast.makeText(this, Product_Id, Toast.LENGTH_LONG).show();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(PRODUCTNAME);
        llmain = (RelativeLayout) findViewById(R.id.llmain);
        llmain.setVisibility(View.GONE);
//        ll_panle = (LinearLayout)findViewById(R.id.ll_panle);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        SharedPreferences prefs = getSharedPreferences("myPref", MODE_PRIVATE);
        USER_ID = prefs.getString("USER_ID", "0");
        String USER_NAME = prefs.getString("USER_NAME", null);
        String USER_EMAIL = prefs.getString("USER_EMAIL", null);
        ///////share//////////////
        popupshare=findViewById(R.id.popupshare);
        rlShareImage=findViewById(R.id.rlShareImage);
        rlShareText=findViewById(R.id.rlShareText);
        txtShareImage=findViewById(R.id.txtShareImage);
        txtShareText=findViewById(R.id.txtShareText);
        ///////share over //////////////

        fatchCartItem(USER_ID);
        bindview();
        ProductInfo(Product_Id);
        recyclerview = (RecyclerView) findViewById(R.id.my_recycler_view);


        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(ProductInfoActivity.this, LinearLayoutManager.HORIZONTAL, false));

        ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (multipleImages.size() > 0) {
                    onOpenDialog(view);
                } else {
                    dialog = new Dialog(ProductInfoActivity.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    Window window = dialog.getWindow();
                    window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    dialog.setCancelable(true);
                    dialog.setContentView(R.layout.full_screen_image);
                    ivImage2 = (TouchImageView) dialog.findViewById(R.id.ivImage2);
                    Glide.with(ProductInfoActivity.this).load(url)
                            .placeholder(R.drawable.icon)
                            .crossFade()
                            .thumbnail(0.5f)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .listener(new RequestListener<String, GlideDrawable>() {
                                @Override
                                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                    ivImage2.setImageDrawable(resource);
                                    return false;
                                }
                            })
                            .into(ivImage2);

                    dialog.show();
                }

            }
        });
        //
    }

    public void onOpenDialog(View v) {
        FragmentManager fm = getSupportFragmentManager();
        DialogActivity overlay = new DialogActivity(multipleImages);
        overlay.show(fm, "FragmentDialog");
    }


    private void ProductInfo(String Product_Id) {
        pDialog = new ProgressDialog(ProductInfoActivity.this);
        pDialog.setMessage("Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
        Call<Responce> call = apiService.Getproductinfo(Product_Id);
        call.enqueue(new Callback<Responce>() {
            @Override
            public void onResponse(Call<Responce> call, retrofit2.Response<Responce> response) {
                items = response.body().getData();
                Log.d("", "Numbe " + items.size());
                if (response.body().getData().size() == 0) {
                    llmain.setVisibility(View.VISIBLE);
                } else {
                    pDialog.dismiss();
                    llmain.setVisibility(View.VISIBLE);
//                    url = items.get(0).getProductImage();
//                    Glide.with(ProductInfoActivity.this).load(items.get(0).getProductImage()).placeholder(R.drawable.icon)
//                            .into(ivImage);
                    int dis = items.get(0).getDiscount();
                    if (dis == 0) {
                        tvDiscount.setVisibility(View.GONE);
                    } else {
                        tvDiscount.setText("(" + items.get(0).getDiscount() + "%OFF)");
                    }
                    datamain = Integer.parseInt(items.get(0).getAvailability().toString());

                    tvProDes.setText((Html.fromHtml(items.get(0).getDescription().toString())));
                    tvName.setText(items.get(0).getProductName().toString());
                    //    tvDiscount.setText(items.get(0).getDiscount().toString());
                    tvspecial_price.setText(items.get(0).getSpecialPrice().toString());
                    tvprice.setText(items.get(0).getPrice().toString());
                    tvskuid.setText(items.get(0).getSku().toString());
                    try {
                        txtSupplierInfo.setText(Html.fromHtml(items.get(0).getSupplier().toString()));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        // System.out.println("rest of the code...");
                    }
                    //Shippingweight = Integer.parseInt(items.get(0).getWeight());
                    SetMainImage(items.get(0).getProductImage());
                    multipleImages = items.get(0).getMultipleImages();
                    Log.e("DATAIMAGE", "MULTIPLEIMAGESIZE" + multipleImages.size());
                    MultipleImage_Adapter Adapter = new MultipleImage_Adapter(ProductInfoActivity.this, items.get(0).getMultipleImages());
                    recyclerview.setAdapter(Adapter);


                }

            }

            @Override
            public void onFailure(Call<Responce> call, Throwable t) {
                // Log error here since request failed
                Log.e(">>>>>>", t.toString());
                pDialog.dismiss();
                Toast.makeText(ProductInfoActivity.this, "Try..", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void bindview() {
        this.txtSupplierInfo = (TextView) findViewById(R.id.txtSupplierInfo);
        this.iv_copy = (MaterialRippleLayout) findViewById(R.id.iv_copy);
        this.etPincode = (EditText) findViewById(R.id.etPincode);
        this.ll_locastion = (LinearLayout) findViewById(R.id.ll_locastion);
        this.error = (ImageView) findViewById(R.id.img);
        this.tvdata = (TextView) findViewById(R.id.txt);
        this.submitcheckondelivery = (TextView) findViewById(R.id.submitcheckondelivery);
        this.btVshare = (LinearLayout) findViewById(R.id.btVshare);
        this.btVproceed = (Button) findViewById(R.id.btVproceed);
        this.tvName = (TextView) findViewById(R.id.name);
        this.ivImage = (ImageView) findViewById(R.id.ivImage);
        this.tvDiscount = (TextView) findViewById(R.id.tvdiscount);
        this.tvspecial_price = (TextView) findViewById(R.id.tvspecial_price);
        this.tvprice = (TextView) findViewById(R.id.tvprice);
        tvprice.setPaintFlags(tvprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        this.tvProDes = (TextView) findViewById(R.id.tvProDes);
        this.tvskuid = (TextView) findViewById(R.id.tvskuid);
        tvshipping = (TextView) findViewById(R.id.tvshipping);
        //   getAllcountry();
        iv_copy.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                clipboard.setText(tvProDes.getText());
                Toast toast = Toast.makeText(ProductInfoActivity.this, "copied", Toast.LENGTH_LONG);
                toast.show();
            }
        });
        this.submitcheckondelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                postcode = etPincode.getText().toString();
//                pDialog = new ProgressDialog(ProductInfoActivity.this);
//                pDialog.setMessage("Please wait...");
//                pDialog.setIndeterminate(false);
//                pDialog.setCancelable(false);
//                pDialog.show();
//                Log.d("DADADADA", "DATAPINCODE" + postcode);
//                Call<Responce> call = apiService.checkondelivery(postcode);
//                call.enqueue(new Callback<Responce>() {
//                    @Override
//                    public void onResponse(Call<Responce> call, Response<Responce> response) {
//                        pDialog.dismiss();
//                        Log.d("DADADADA", "DATASUCCESS" + response.body().getSuccess());
//                        if (response.body().getSuccess() == 0) {
//                            ll_locastion.setVisibility(View.VISIBLE);
//                            error.setImageResource(R.drawable.error);
//                            tvdata.setText(R.string.errorlocation);
//                        } else {
//                            ll_locastion.setVisibility(View.VISIBLE);
//                            error.setImageResource(R.drawable.noterror);
//                            tvdata.setText(R.string.location);
//                        }
//
//
//                    }
//
//                    @Override
//                    public void onFailure(Call<Responce> call, Throwable t) {
//                        Log.e(">> ", t.toString());
//                        pDialog.dismiss();
//                        Toast.makeText(ProductInfoActivity.this, "Try...", Toast.LENGTH_SHORT).show();
//
//                    }
//
//                });
            }
        });
        tvshow = (TextView) findViewById(R.id.tvshow);
//        tvshow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//                View promptsView1 = li.inflate(R.layout.dilog_user_returnspolicy, null);
//
//                AlertDialog.Builder alertDialogBuilder1 = new AlertDialog.Builder(ProductInfoActivity.this);
//
//                // set prompts.xml to alertdialog builder
//                alertDialogBuilder1.setView(promptsView1);
//                alertDialogBuilder1.setCancelable(true);
//
//                final AlertDialog alertDialog1 = alertDialogBuilder1.create();
//                ImageView icon_cancel = (ImageView) promptsView1.findViewById(R.id.icon_cancel);
//                icon_cancel.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        alertDialog1.dismiss();
//                    }
//                });
//                TextView cancel = (TextView) promptsView1.findViewById(R.id.tvbtnOk);
//                cancel.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        alertDialog1.dismiss();
//
//                    }
//                });
//
//
//                // show it
//                alertDialog1.show();
//
//                ((ViewGroup) alertDialog1.getWindow().getDecorView()).getChildAt(0).startAnimation(AnimationUtils.loadAnimation(ProductInfoActivity.this, R.anim.shake));
//
//
//            }
//        });

        //  this.ibSub = (ImageButton) findViewById(R.id.ibSub);

        this.btnAddToCart = (Button) findViewById(R.id.btVproduct);
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                SharedPreferences prefs = getSharedPreferences("myPref", MODE_PRIVATE);
//                final Boolean Login = prefs.getBoolean("LOGIN", false);
//                if (Login.booleanValue() == false) {
//
//                    LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//                    View promptsView1 = li.inflate(R.layout.dilog_userfirst_login, null);
//
//                    AlertDialog.Builder alertDialogBuilder1 = new AlertDialog.Builder(ProductInfoActivity.this);
//
//                    // set prompts.xml to alertdialog builder
//                    alertDialogBuilder1.setView(promptsView1);
//                    alertDialogBuilder1.setCancelable(true);
//
//                    final AlertDialog alertDialog1 = alertDialogBuilder1.create();
//                    ImageView icon_cancel = (ImageView) promptsView1.findViewById(R.id.icon_cancel);
//                    icon_cancel.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            alertDialog1.dismiss();
//                        }
//                    });
//                    TextView cancel = (TextView) promptsView1.findViewById(R.id.tvbtnOk);
//                    cancel.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            alertDialog1.dismiss();
//                            Intent intent = new Intent(ProductInfoActivity.this, LoginActivity.class);
//                            startActivity(intent);
//                        }
//                    });
//
//
//                    // show it
//                    alertDialog1.show();
//
//                    ((ViewGroup) alertDialog1.getWindow().getDecorView()).getChildAt(0).startAnimation(AnimationUtils.loadAnimation(ProductInfoActivity.this, R.anim.shake));
//
//
//                } else {
//                    if (datamain == 0) {
//                        Toast.makeText(ProductInfoActivity.this, "out of stock", Toast.LENGTH_SHORT).show();
//                    } else {
//
//                        LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//                        View promptsView1 = li.inflate(R.layout.dialog_sizeqty_box, null);
//
//                        AlertDialog.Builder alertDialogBuilder1 = new AlertDialog.Builder(ProductInfoActivity.this);
//
//                        // set prompts.xml to alertdialog builder
//                        alertDialogBuilder1.setView(promptsView1);
//                        alertDialogBuilder1.setCancelable(true);
//
//                        final AlertDialog alertDialog1 = alertDialogBuilder1.create();
//
//                        spcountry = (Spinner) promptsView1.findViewById(R.id.spcountry);
////                    spcountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
////                        @Override
////                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
////                            if (position == 0) {
////                                Toast.makeText(ProductInfoActivity.this, "Please Select Size 0", Toast.LENGTH_SHORT).show();
////                            }else {
////                                Toast.makeText(ProductInfoActivity.this, "Please Select Size 1", Toast.LENGTH_SHORT).show();
////                            }
////                        }
////
////                        @Override
////                        public void onNothingSelected(AdapterView<?> adapterView) {
////
////                        }
////                    });
//                        tvQuantity = (TextView) promptsView1.findViewById(R.id.tvQuantity);
//                        tvQuantity.setText(String.valueOf(Counter));
//                        ibAdd = (ImageView) promptsView1.findViewById(R.id.ivRight);
//                        ibSub = (ImageView) promptsView1.findViewById(R.id.ivLeft);
//                        ibAdd.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                Counter++;
//                                tvQuantity.setText(String.valueOf(Counter));
//                            }
//                        });
//                        ibSub.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                if (Counter > 1) {
//                                    Counter--;
//                                    tvQuantity.setText(String.valueOf(Counter));
//
//                                }
//                            }
//                        });
//
//                        final List<Option> users = items.get(0).getOptions();
//                        if (users.size() == 0) {
//
//                        } else {
//                            Log.d("DATAF", "DATAADEPTERLIST" + users.size());
//                            SpinnerCustomAdapter adapter = new SpinnerCustomAdapter(ProductInfoActivity.this, users);
//                            spcountry.setAdapter(adapter);
//                        }
//                        spcountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                            @Override
//                            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
//                                if (position == 0) {
//                                    suboptionid = "";
//                                    optionsid = "";
//                                    Toast.makeText(ProductInfoActivity.this, "Please Select Size", Toast.LENGTH_SHORT).show();
//                                } else {
//                                    optionsid = users.get(position).getOptionId().toString();
//                                    suboptionid = users.get(position).getSubOptionId().toString();
//                                }
//
//
//                            }
//
//                            @Override
//                            public void onNothingSelected(AdapterView<?> adapterView) {
//
//                            }
//                        });
//
//                        TextView cancel = (TextView) promptsView1.findViewById(R.id.tvbtnOk);
//
//                        cancel.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                final List<Option> users = items.get(0).getOptions();
//
//                                if (users.size() == 0) {
//
//                                    AddToCart(USER_ID, Product_Id, String.valueOf(Counter), optionsid, suboptionid);
//                                    alertDialog1.dismiss();
//
//                                } else {
//                                    if (optionsid.isEmpty() && suboptionid.isEmpty()) {
//
//                                        Toast.makeText(ProductInfoActivity.this, "Please Select Size", Toast.LENGTH_SHORT).show();
//
//                                    } else {
//                                        AddToCart(USER_ID, Product_Id, String.valueOf(Counter), optionsid, suboptionid);
//                                        alertDialog1.dismiss();
//                                    }
//                                    // Toast.makeText(ProductInfoActivity.this, optionsid + "\n" + suboptionid , Toast.LENGTH_LONG).show();show
//                                    // AddToCart(USER_ID, Product_Id, String.valueOf(Counter), optionsid, suboptionid);
//                                    //   alertDialog1.dismiss();
//                                }
//
//                            }
//                        });
//
//
//                        // show it
//                        alertDialog1.show();
//
//                        ((ViewGroup) alertDialog1.getWindow().getDecorView()).getChildAt(0).startAnimation(AnimationUtils.loadAnimation(ProductInfoActivity.this, R.anim.shake));
//                    }
//
//
//                }
//
                Intent intent = new Intent(ProductInfoActivity.this, Inquiry.class);
                intent.putExtra("PRO_URL",items.get(0).getProductUrl());
                startActivity(intent);
            }

            private void AddToCart(final String USER_ID, String product_Id, String quantity, String optionsid, String suboptionid) {
                Log.d("DATAITEMSS", "DATAADDTOCART" + USER_ID + product_Id + quantity + optionsid + suboptionid);
                pDialog = new ProgressDialog(ProductInfoActivity.this);
                pDialog.setMessage("Please wait...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(false);
                pDialog.show();
                Call<Responce> call = apiService.addToCart(USER_ID, product_Id, quantity, optionsid, suboptionid);
                call.enqueue(new Callback<Responce>() {
                    @Override
                    public void onResponse(Call<Responce> call, Response<Responce> response) {
                        pDialog.dismiss();
                        if (response.body().getSuccess() == 1) {

                            btVproceed.setVisibility(View.VISIBLE);
                            btVshare.setVisibility(View.GONE);
                            Toast.makeText(ProductInfoActivity.this, "Product Added To Cart", Toast.LENGTH_SHORT).show();
                            fatchCartItem(USER_ID);
                        } else {
                            btVproceed.setVisibility(View.GONE);
                            btVshare.setVisibility(View.VISIBLE);
                        }

                    }

                    @Override
                    public void onFailure(Call<Responce> call, Throwable t) {

                        pDialog.dismiss();
                        Toast.makeText(ProductInfoActivity.this, "Try....", Toast.LENGTH_SHORT).show();
                        Log.e(">> ", t.toString());

                    }
                });
//
            }
        });

        this.btVproceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductInfoActivity.this, CartActivity.class);
                startActivity(intent);

            }
        });
        this.btVshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupshare.setVisibility(View.VISIBLE);
                rlShareImage.setBackgroundResource(R.drawable.round_share);
                txtShareImage.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.drak_grey));
                rlShareText.setBackgroundResource(R.drawable.round_share);
                txtShareText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.drak_grey));
                ArrayList<String> dataurl = new ArrayList<>();
                for (int i = 0; i < multipleImages.size(); i++) {

                    dataurl.add(multipleImages.get(i).getImage());
                }


                if (dataurl.size() > 0) {

                    DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
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
                    intent1.putExtra(Intent.EXTRA_TEXT, tvName.getText().toString()+ "\n" + tvProDes.getText().toString());
                    try {
                        startActivityForResult(intent1, 2);
                    } catch (Exception e2s) {
                    }
                }
            }
        });
        tvshipping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                View promptsView1 = li.inflate(R.layout.delog_shippingcharg, null);

                AlertDialog.Builder alertDialogBuilder1 = new AlertDialog.Builder(ProductInfoActivity.this);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder1.setView(promptsView1);
                alertDialogBuilder1.setCancelable(false);

                final AlertDialog alertDialog1 = alertDialogBuilder1.create();
                final TextView txtweight = (TextView) promptsView1.findViewById(R.id.txtweight);
                final DecimalFormat precision = new DecimalFormat("0.00");
                MainShippingCounter = Float.parseFloat(items.get(0).getWeight());
                Shippingweight = Float.parseFloat(items.get(0).getWeight());
                txtweight.setText("(" + precision.format(Shippingweight) + "KG)");
                //  String date = String.valueOf(dateEditText.getText());

                ImageView icon_cancel = (ImageView) promptsView1.findViewById(R.id.icon_cancel);
                icon_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ShippingCounter = 1;
                        Shippingweight = MainShippingCounter;
                        alertDialog1.dismiss();
                    }
                });
                TextView tvbtnOk = (TextView) promptsView1.findViewById(R.id.tvbtnOk);
                final TextInputLayout inputLayoutPincode = (TextInputLayout) promptsView1.findViewById(R.id.inputLayoutPincode);
                final TextInputEditText tv_pincode = (TextInputEditText) promptsView1.findViewById(R.id.tv_pincode);
                final LinearLayout locastion = (LinearLayout) promptsView1.findViewById(R.id.locastion);
                final ImageView imgright = (ImageView) promptsView1.findViewById(R.id.imgright);
                final TextView txt_text = (TextView) promptsView1.findViewById(R.id.txt_text);

                tvbtnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        String pincode = tv_pincode.getText().toString();
                        Log.d("DATA", "DATAPINCODE" + pincode);
                        if (pincode == null || pincode.isEmpty() || pincode.length() != 6) {
                            if (pincode.length() == 6) {
                                inputLayoutPincode.setErrorEnabled(false);
                            } else {
                                inputLayoutPincode.setError("Please Enter 6 Digit Pincode.");
                            }
                            Toast.makeText(ProductInfoActivity.this, "condistiondata", Toast.LENGTH_SHORT).show();
                        } else {
                            inputLayoutPincode.setErrorEnabled(false);
                            String chargedata = String.valueOf(Shippingweight);
//                            Toast.makeText(ProductInfoActivity.this, chargedata, Toast.LENGTH_SHORT).show();
                            Call<Responce> call = apiService.getshippingcharge(chargedata);
                            call.enqueue(new Callback<Responce>() {
                                @Override
                                public void onResponse(Call<Responce> call, Response<Responce> response) {
                                    //  pDialog.dismiss();
                                    Log.d("DADADADA", "DATASUCCESS" + response.body().getMessage());
                                    if (response.body().getSuccess() == 0) {
                                        //             Toast.makeText(ProductInfoActivity.this, "Shipping Charges Not Available", Toast.LENGTH_SHORT).show();
//                                    ll_locastion.setVisibility(View.VISIBLE);
//                                    error.setImageResource(R.drawable.error);
//                                    tvdata.setText(R.string.errorlocation);
                                    } else {
                                        locastion.setVisibility(View.VISIBLE);
                                        imgright.setImageResource(R.drawable.noterror);
                                        txt_text.setText(response.body().getMessage() + " ₹ " + response.body().getShippingCharge());
                                    }
                                }

                                @Override
                                public void onFailure(Call<Responce> call, Throwable t) {
                                    Log.e(">> ", t.toString());
                                    // pDialog.dismiss();
                                    Toast.makeText(ProductInfoActivity.this, "Try...", Toast.LENGTH_SHORT).show();

                                }

                            });
                        }
                    }
                });


                tvQuantity = (TextView) promptsView1.findViewById(R.id.tvQuantity);
                tvQuantity.setText(String.valueOf(ShippingCounter));
                ibAdd = (ImageView) promptsView1.findViewById(R.id.ivRight);
                ibSub = (ImageView) promptsView1.findViewById(R.id.ivLeft);
                ibAdd.setOnClickListener(new View.OnClickListener()

                {
                    @Override
                    public void onClick(View view) {
                        ShippingCounter++;
                        Shippingweight = Shippingweight + MainShippingCounter;

                        tvQuantity.setText(String.valueOf(ShippingCounter));
                        txtweight.setText("(" + precision.format(Shippingweight) + "KG)");
                        //   txtweight.setText("(" + Shippingweight + "KG)");
                        // Toast.makeText(ProductInfoActivity.this,  String.valueOf(Shippingweight) + "/n" + String.valueOf(MainShippingCounter) + "/n" +  String.valueOf(Shippingweight), Toast.LENGTH_SHORT).show();

                    }
                });
                ibSub.setOnClickListener(new View.OnClickListener()

                {
                    @Override
                    public void onClick(View view) {
                        if (ShippingCounter > 1) {
                            ShippingCounter--;
                            Shippingweight = Shippingweight - MainShippingCounter;
                            tvQuantity.setText(String.valueOf(ShippingCounter));
                            txtweight.setText("(" + precision.format(Shippingweight) + "KG)");
                            //  txtweight.setText("(" + Shippingweight + "KG)");
                            //Toast.makeText(ProductInfoActivity.this, String.valueOf(Shippingweight) , Toast.LENGTH_LONG).show();
                        }
                    }
                });

                txtweight.setText("(" + Shippingweight + "KG)");
                ImageView cancel = (ImageView) promptsView1.findViewById(R.id.icon_cancel);
                cancel.setOnClickListener(new View.OnClickListener()

                {
                    @Override
                    public void onClick(View view) {
                        ShippingCounter = 1;
                        Shippingweight = 0;
                        alertDialog1.dismiss();

                    }
                });


                // show it
                alertDialog1.show();

                ((ViewGroup) alertDialog1.getWindow().

                        getDecorView()).

                        getChildAt(0).

                        startAnimation(AnimationUtils.loadAnimation(ProductInfoActivity.this, R.anim.shake));

            }
        });
    }

    private void galleryAddPic(File new_file, String name) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri imageUri = Uri.fromFile(new_file);
        mediaScanIntent.setData(imageUri);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND_MULTIPLE);
        intent.setPackage("com.whatsapp");
        intent.putExtra(Intent.EXTRA_TEXT, name);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_STREAM, imageUri);
        intent.setType("image/jpeg");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        try {
            ProductInfoActivity.this.startActivity(intent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(ProductInfoActivity.this, "Whatsapp have not been installed.", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.mycart, menu);
//
//        final MenuItem menuItem = menu.findItem(R.id.action_cart);
//        final View actionView = MenuItemCompat.getActionView(menuItem);
//        textCartItemCount = (TextView) actionView.findViewById(R.id.cart_count);
//        setupBadge(mCartItemCount);
//        actionView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(ProductInfoActivity.this, CartActivity.class);
//                startActivity(intent);
//
//            }
//        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }

    private void fatchCartItem(String user_id) {
        Call<ResponceCartItem> call = apiService.getCartDetail(user_id);
        call.enqueue(new Callback<ResponceCartItem>() {
            @Override
            public void onResponse(Call<ResponceCartItem> call, Response<ResponceCartItem> response) {
                List<CartList> items = response.body().getData();
                try {
                    Log.d(">>> ", "Number of Cart Item received:" + items.size());
                    mCartItemCount = items.size();
                    setupBadge(mCartItemCount);
                    //     llmain.setVisibility(View.VISIBLE);
                } catch (Exception ex) {
                }

            }

            @Override
            public void onFailure(Call<ResponceCartItem> call, Throwable t) {
                Log.e(">> ", t.toString());
            }
        });

    }

    private void setupBadge(int itemcount) {
        if (textCartItemCount != null) {
            if (this.mCartItemCount == 0) {
                if (textCartItemCount.getVisibility() != View.GONE) {
                    textCartItemCount.setVisibility(View.GONE);
                }
            } else {
                textCartItemCount.setText(String.valueOf(Math.min(this.mCartItemCount, itemcount)));
                if (textCartItemCount.getVisibility() != View.VISIBLE) {
                    textCartItemCount.setVisibility(View.VISIBLE);
                }
            }

        }
    }

    protected void onStart() {
        super.onStart();
        fatchCartItem(USER_ID);
        Counter = 1;

    }

    @Override
    protected void onStop() {
        super.onStop();
        Counter = 1;
        ShippingCounter = 1;


    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getIntent().removeExtra("android.intent.extra.STREAM");
        if (requestCode == 1) {
            rlShareImage.setBackgroundResource(R.drawable.round_share_completed);
            txtShareImage.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.whats_color));
            File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/1st Choice");
            if (dir.isDirectory()) {
                String[] children = dir.list();
                for (int i = 0; i < children.length; i++) {
                    new File(dir, children[i]).delete();
                }
            }
            Toast.makeText(getApplicationContext(),"Share Description",Toast.LENGTH_LONG).show();

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
                    intent1.putExtra(Intent.EXTRA_TEXT, tvProDes.getText().toString() );
                    try {
                        startActivityForResult(intent1, 2);
                    } catch (Exception e2s) {
                    }

                }
            }, 2000);

        }
        else if(requestCode==2)
        {
            rlShareText.setBackgroundResource(R.drawable.round_share_completed);
            txtShareText.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.whats_color));
            popupshare.setVisibility(View.GONE);
        }
    }


    BroadcastReceiver onComplete = new BroadcastReceiver() {
        public void onReceive(Context ctxt, Intent intent) {
            long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            Log.e("IN", "" + referenceId);
            list.remove(referenceId);

            if (list.isEmpty()) {

                ArrayList<Uri> files_list = new ArrayList<Uri>();
                File  files= new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/Madhusudhan Creation");
                File[] F = files.listFiles();
                for (int i = 0; i < F.length; i++) {
                    String s=F[i].getName();
                    if(F[i].getName().contains("Sample_")) {
                        Uri uri1 = FileProvider.getUriForFile(getApplicationContext(), BuildConfig.APPLICATION_ID + ".provider", F[i]);
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
                    startActivityForResult(intent1,1);
                } catch (Exception e2s) {
                }

            }

        }
    };

    @Override
    protected void onPause() {
        unregisterReceiver(onComplete);
        super.onPause();
        Counter = 1;
        ShippingCounter = 1;
    }


    @Override
    protected void onResume() {
        getIntent().removeExtra("android.intent.extra.STREAM");
        registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        super.onResume();
        Counter = 1;
        ShippingCounter = 1;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Counter = 1;
        ShippingCounter = 1;

    }



    public void SetMainImage(String image) {
        url = image;
        Log.d("DATAIMAGE", "IMAGEURL" + image);

        Glide.with(ProductInfoActivity.this).load(image)
                .crossFade()
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        ivImage.setImageDrawable(resource);
                        return false;
                    }
                })
                .into(ivImage);
    }
}