package com.SkyzoneGroup.MadhusudhanCreation.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.SkyzoneGroup.MadhusudhanCreation.Adapter.OrderDetail_Adapter;
import com.SkyzoneGroup.MadhusudhanCreation.R;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.ApiClient;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.ApiInterface;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.MyOrderDetail;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.MyOrderDetailProduct;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.MyOrderDetailResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyOrederFullInfoActivity extends AppCompatActivity {
    TextView tvTotalPrice, tvYouSave, tvPOtotal, tvDATE, tvStatus, tv_address, tv_phoneno, tv_name;
    ApiInterface apiService;
    String USER_ID, USER_EMAIL;
    ProgressDialog pDialog;
    TextView textCartItemCount;
    int mCartItemCount = 0;
    LinearLayout ll_contactus, ll_viewpolicy,ll_INVOICE;
    RecyclerView recyclerView;
    TextView txtmethord, cod_carge;
    TextView txtgratotal;
    ScrollView scr_view;

    TextView tv_toname, tv_tophoneno, tv_toaddress;
    TextView tv_fromname, tv_fromphoneno, tv_fromaddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_oreder_full_info);
        String odid = getIntent().getStringExtra("ORDERID");

        apiService = ApiClient.getClient().create(ApiInterface.class);

        SharedPreferences prefs = getSharedPreferences("myPref", MODE_PRIVATE);
        USER_ID = prefs.getString("USER_ID", "0");
        String USER_NAME = prefs.getString("USER_NAME", null);
        USER_EMAIL = prefs.getString("USER_EMAIL", null);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("MyOrder List");

        bindview(odid);
        //   fatchCartItem(USER_ID);
    }

//    private void fatchCartItem(String user_id) {
//        Call<ResponceCartItem> call = apiService.getCartDetail(user_id);
//        call.enqueue(new Callback<ResponceCartItem>() {
//            @Override
//            public void onResponse(Call<ResponceCartItem> call, Response<ResponceCartItem> response) {
//                List<CartList> items = response.body().getData();
//                try {
//                    Log.d(">>> ", "Number of Cart Item received001:" + items.size());
//                    mCartItemCount = items.size();
//                    setupBadge(mCartItemCount);
//                } catch (Exception ex) {
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<ResponceCartItem> call, Throwable t) {
//                Log.e(">> ", t.toString());
//            }
//        });
//    }

    private void bindview(String odid) {
        scr_view = (ScrollView) findViewById(R.id.scr_view);
        scr_view.setVisibility(View.GONE);
        txtgratotal = (TextView) findViewById(R.id.txtgratotal);
        ll_INVOICE= (LinearLayout) findViewById(R.id.ll_INVOICE);
        ll_contactus = (LinearLayout) findViewById(R.id.ll_contactus);
        ll_viewpolicy = (LinearLayout) findViewById(R.id.ll_viewpolicy);
        tvTotalPrice = (TextView) findViewById(R.id.tvTotalPrice);
        tvYouSave = (TextView) findViewById(R.id.tvYouSave);
        tv_toname = (TextView) findViewById(R.id.tv_toname);
        tvPOtotal = (TextView) findViewById(R.id.tvPOtotal);
        tv_tophoneno = (TextView) findViewById(R.id.tv_tophoneno);
        tv_toaddress = (TextView) findViewById(R.id.tv_toaddress);

        tv_fromname = (TextView) findViewById(R.id.tv_fromname);
        tv_fromphoneno = (TextView) findViewById(R.id.tv_fromphoneno);
        tv_fromaddress = (TextView) findViewById(R.id.tv_fromaddress);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        tvDATE = (TextView) findViewById(R.id.tvDATE);
        tvStatus = (TextView) findViewById(R.id.tvStatus);
        txtmethord = (TextView) findViewById(R.id.txtmethord);
        cod_carge = (TextView) findViewById(R.id.cod_carge);
        recyclerView.setLayoutManager(new LinearLayoutManager(MyOrederFullInfoActivity.this, LinearLayoutManager.VERTICAL, false));
        ll_INVOICE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MyOrederFullInfoActivity.this, "Coming Soon", Toast.LENGTH_SHORT).show();
            }
        });
        ll_contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyOrederFullInfoActivity.this, WebviewActivity.class);
                intent.putExtra("TITLE", "Call Us");
                intent.putExtra("ID", 3);
                startActivity(intent);
            }
        });
        ll_viewpolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                View promptsView1 = li.inflate(R.layout.dilog_user_returnspolicy, null);

                AlertDialog.Builder alertDialogBuilder1 = new AlertDialog.Builder(MyOrederFullInfoActivity.this);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder1.setView(promptsView1);
                alertDialogBuilder1.setCancelable(true);

                final AlertDialog alertDialog1 = alertDialogBuilder1.create();
                ImageView icon_cancel = (ImageView) promptsView1.findViewById(R.id.icon_cancel);
                icon_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog1.dismiss();
                    }
                });
                TextView cancel = (TextView) promptsView1.findViewById(R.id.tvbtnOk);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog1.dismiss();

                    }
                });


                // show it
                alertDialog1.show();

                ((ViewGroup) alertDialog1.getWindow().getDecorView()).getChildAt(0).startAnimation(AnimationUtils.loadAnimation(MyOrederFullInfoActivity.this, R.anim.shake));

            }
        });
        PlacedOrder(odid);
//        tvTotalPrice =(TextView)findViewById(R.id.tvTotalPrice);
//        tvTotalPrice =(TextView)findViewById(R.id.tvTotalPrice);

    }

    public void PlacedOrder(String odid) {
        pDialog = new ProgressDialog(MyOrederFullInfoActivity.this);
        pDialog.setMessage("Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
        Call<MyOrderDetailResponse> call = apiService.getMyOrderDetail(odid);
        call.enqueue(new Callback<MyOrderDetailResponse>() {
            @Override
            public void onResponse(Call<MyOrderDetailResponse> call, Response<MyOrderDetailResponse> response) {

                pDialog.dismiss();
                scr_view.setVisibility(View.VISIBLE);
                try {

                    MyOrderDetail data = response.body().getData();
                    tvStatus.setText(data.getOrderStatus());
                    tvPOtotal.setText("₹ " + data.getGrandTotal());
                    //    tvAmount.setText("Total Amount : Rs. "+data.getGrandTotal());

                    // getShippingAddress

                    tv_fromname.setText(data.getShippingAddress().getFirsname() + " " + data.getShippingAddress().getLastname());
                    tv_fromaddress.setText(data.getShippingAddress().getStreet());
                    tv_fromphoneno.setText(data.getShippingAddress().getCity() + "-" + data.getShippingAddress().getPostcode() + "," + data.getShippingAddress().getRegion() + "\n" + "Mobile" + " : " + data.getShippingAddress().getTelephone());

                    // BillingAddress

                    tv_toname.setText(data.getBillingAddress().getFirsname() + " " + data.getBillingAddress().getLastname());
                    tv_toaddress.setText(data.getBillingAddress().getStreet());
                    tv_tophoneno.setText(data.getBillingAddress().getCity() + " - " + data.getBillingAddress().getPostcode() + "," + data.getBillingAddress().getRegion() + " \n " + "Mobile" + " : " + data.getBillingAddress().getTelephone());


                    txtmethord.setText(data.getPaymentMethod().toString());
                    tvYouSave.setText("+ ₹ " + data.getCodCharge().toString());
                    cod_carge.setText("+ ₹ " + data.getShippingCharges().toString());
                    txtgratotal.setText("+ ₹ " + data.getRowTotal().toString());
                    // Toast.makeText(MyOrederFullInfoActivity.this, data.getAddress().getFirsname(), Toast.LENGTH_SHORT).show();
//                    String strCurrentDate = data.getOrderDate();
//                    SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
//                    Date newDate = format.parse(strCurrentDate);
//
//                    format = new SimpleDateFormat("MMM dd,yyyy 'at' hh:mm a");
//                    String date = format.format(newDate);

                    tvDATE.setText(data.getOrderDate());

                    if (data.getOrderStatus().equals("pending")) {
                        tvStatus.setTextColor(ContextCompat.getColor(MyOrederFullInfoActivity.this, R.color.meesho_green));
                    } else {
                        tvStatus.setTextColor(ContextCompat.getColor(MyOrederFullInfoActivity.this, R.color.meesho));
                    }

                    List<MyOrderDetailProduct> users = data.getProducts();

                    Log.d(">>>>>>>>>>>>>>", "Number of orderDetail Item received: " + users.size());
                    recyclerView.setAdapter(new OrderDetail_Adapter(users, MyOrederFullInfoActivity.this));
                } catch (Exception ex) {
                }

            }

            @Override
            public void onFailure(Call<MyOrderDetailResponse> call, Throwable t) {
                Toast.makeText(MyOrederFullInfoActivity.this, "Try again", Toast.LENGTH_SHORT).show();
            }

        });
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

    //    public void setupBadge(int upBadge) {
//        if (textCartItemCount != null) {
//            if (this.mCartItemCount == 0) {
//                if (textCartItemCount.getVisibility() != View.GONE) {
//                    textCartItemCount.setVisibility(View.GONE);
//                }
//            } else {
//                textCartItemCount.setText(String.valueOf(Math.min(this.mCartItemCount, upBadge)));
//                if (textCartItemCount.getVisibility() != View.VISIBLE) {
//                    textCartItemCount.setVisibility(View.VISIBLE);
//                }
//            }
//        }
//    }
    protected void onStart() {
        super.onStart();
        //  fatchCartItem(USER_ID);
    }
}
