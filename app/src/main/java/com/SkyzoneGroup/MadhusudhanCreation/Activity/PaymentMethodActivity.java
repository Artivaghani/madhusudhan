package com.SkyzoneGroup.MadhusudhanCreation.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.SkyzoneGroup.MadhusudhanCreation.Adapter.CartItemPymnetMethord_Adapter;
import com.SkyzoneGroup.MadhusudhanCreation.R;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.Address;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.ApiClient;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.ApiInterface;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.DatumPymentMethord;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.Responce;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.ResponcePymentMethord;
import com.SkyzoneGroup.MadhusudhanCreation.ccAvenue.CCavenueWebviewActivity;
import com.SkyzoneGroup.MadhusudhanCreation.util.AvenuesParams;
import com.SkyzoneGroup.MadhusudhanCreation.util.InternetConnection;
import com.SkyzoneGroup.MadhusudhanCreation.util.ServiceUtility;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PaymentMethodActivity extends AppCompatActivity {

    TextView textCartItemCount;
    int mCartItemCount = 0;
    ApiInterface apiService;
    String USER_ID;
    TextView text_view_start;
    Button proceed;
    String orderId;
    String USER_EMAIL;
    public static float total;
    private RecyclerView recyclerView;
    ProgressDialog pDialog;
    TextView tvYouSave, tvTotalPrice, tvQty, tvPOtotal;
    public static String TotalPrice;
    public static float YouSave = 0;
    public static float Qty = 0;
    public static float sp = 0;
    public static float COD = 0;
    RadioGroup mySelection;
    String addressid;
    int index = 1;
    ImageView icon_cancel;
    String PTOTAL;
    TextView txt_shpping;
    RelativeLayout rl_main;
    RelativeLayout rl_COD;
    String TOADDRESSID;
    String postcode;
    LinearLayout ll_emptybox, ll_main, main;
    RelativeLayout rl_maintop, rl_maindata;
    FrameLayout fl_nointernet;
    CardView cc_paymentcharg;
    RadioButton online_option, cod_option;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);

        apiService = ApiClient.getClient().create(ApiInterface.class);

        addressid = String.valueOf(getIntent().getIntExtra("ADDRESSID", 0));
        SharedPreferences prefs = getSharedPreferences("myPref", MODE_PRIVATE);
        USER_ID = prefs.getString("USER_ID", "0");
        String USER_NAME = prefs.getString("USER_NAME", null);
        USER_EMAIL = prefs.getString("USER_EMAIL", null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Payment Method");

        TOADDRESSID = prefs.getString("ADDRESSID", null);
        postcode = prefs.getString("PINCODEID", null);
        bindview(USER_ID, addressid);

        final RadioButton radioButton = (RadioButton) findViewById(R.id.cod_option);

//        Call<Responce> call = apiService.checkondelivery(postcode);
//        call.enqueue(new Callback<Responce>() {
//            @Override
//            public void onResponse(Call<Responce> call, Response<Responce> response) {
//                pDialog.dismiss();
//                Log.d("DADADADA", "DATASUCCESS" + response.body().getSuccess());
//                if (response.body().getSuccess() == 0) {
//                    radioButton.setVisibility(View.GONE);
//                } else {
//                    radioButton.setVisibility(View.VISIBLE);
//
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<Responce> call, Throwable t) {
//                Log.e(">> ", t.toString());
//                pDialog.dismiss();
//                Toast.makeText(PaymentMethodActivity.this, "Try...", Toast.LENGTH_SHORT).show();
//            }
//        });

        radioButton.setVisibility(View.VISIBLE);
    }


    private void bindview(final String USER_ID, final String addressid) {

        proceed = (Button) findViewById(R.id.proceed);
        rl_maintop = (RelativeLayout) findViewById(R.id.rl_maintop);
        text_view_start = (TextView) findViewById(R.id.text_view_start);
        ll_emptybox = (LinearLayout) findViewById(R.id.ll_emptybox);
        ll_main = (LinearLayout) findViewById(R.id.ll_main);
        ll_main.setVisibility(View.GONE);
        main = (LinearLayout) findViewById(R.id.ll_main);
        main.setVisibility(View.GONE);
        online_option = (RadioButton) findViewById(R.id.online_option);
        cod_option = (RadioButton) findViewById(R.id.cod_option);
        mySelection = (RadioGroup) findViewById(R.id.myRadioGroup);
        txt_shpping = (TextView) findViewById(R.id.txt_shpping);
        rl_main = (RelativeLayout) findViewById(R.id.rl_main);
        rl_maindata = (RelativeLayout) findViewById(R.id.rl_maindata);
        fl_nointernet = (FrameLayout) findViewById(R.id.fl_nointernet);
        rl_COD = (RelativeLayout) findViewById(R.id.rl_COD);
        tvPOtotal = (TextView) findViewById(R.id.tvPOtotal);
        cc_paymentcharg = (CardView) findViewById(R.id.cc_paymentcharg);
        cc_paymentcharg.setVisibility(View.GONE);
        tvTotalPrice = (TextView) findViewById(R.id.tvTotalPrice);
        tvYouSave = (TextView) findViewById(R.id.tvYouSave);
        if (isNetworkStatusAvialable(getApplicationContext())) {
            rl_maindata.setVisibility(View.VISIBLE);
            fl_nointernet.setVisibility(View.GONE);
        } else {
            rl_maindata.setVisibility(View.GONE);
            fl_nointernet.setVisibility(View.VISIBLE);
        }

        text_view_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PaymentMethodActivity.this, HomActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();

            }
        });
        mySelection.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (isNetworkStatusAvialable(getApplicationContext())) {
                    View radioButton = radioGroup.findViewById(i);
                    index = radioGroup.indexOfChild(radioButton) + 1;
                } else {
                    Intent intent = getIntent();
                    overridePendingTransition(0, 0);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(intent);
                }
                if (isNetworkStatusAvialable(getApplicationContext())) {
                    if (index == 1) {
                        rl_COD.setVisibility(View.GONE);
                        rl_main.setVisibility(View.VISIBLE);
                        getOnlinecartitems(USER_ID);
                    } else if (index == 2) {
                        rl_COD.setVisibility(View.VISIBLE);
                        rl_main.setVisibility(View.VISIBLE);
//                        getCodecartitems(USER_ID);

                    } else {

                    }
                    switch(i) {
                        case R.id.online_option:
                            getOnlinecartitems(USER_ID);
                            break;
                        case R.id.cod_option:
//                            getCodecartitems(USER_ID);
                            break;
                    }
                }
            }
        });
        proceed.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (isNetworkStatusAvialable(getApplicationContext())) {
                    if (index == 0) {
                        Toast.makeText(PaymentMethodActivity.this, "Please Select Payment Method...", Toast.LENGTH_SHORT).show();
                    }
                    else if (index == 1) {

                        pDialog = new ProgressDialog(PaymentMethodActivity.this);
                        pDialog.setMessage("Please wait...");
                        pDialog.setIndeterminate(false);
                        pDialog.setCancelable(false);
                        pDialog.show();
                        Log.d("DATAUSER", "USERPLACE" + USER_ID + addressid + TOADDRESSID);
                        Call<Responce> call = apiService.getOrderccavenueResponse(USER_ID, addressid, TOADDRESSID);

                        call.enqueue(new Callback<Responce>() {
                            @Override
                            public void onResponse(Call<Responce> call, Response<Responce> response) {
                                pDialog.dismiss();
//                                //   Toast.makeText(PaymentMethodActivity.this, response.body().getOrderId() , Toast.LENGTH_SHORT).show();
//                                if (response.body().getOrderId() == " ") {
//                                    try {
                                       orderId = response.body().getOrderId();
                                        Log.d("DATAUSER", "USEROrder" + orderId);
//                                    } catch (Exception e) {
//                                        Log.wtf("DO THIS", " WHEN SAVE() FAILS");
//                                    }
                             //   } else {


                                    List<Address> getbilling = response.body().getAddress();
                                    if (response.body().getSuccess() == 0) {
                                        Toast.makeText(PaymentMethodActivity.this, "Unsuccessful Place order", Toast.LENGTH_SHORT).show();
                                        pDialog.dismiss();
                                    } else {
                                        String billingname = getbilling.get(0).getFirstname() + getbilling.get(0).getLastname();
                                        String billingaddress = getbilling.get(0).getStreet();
                                        String billingcity = getbilling.get(0).getCity();
                                        String billingstate = getbilling.get(0).getRegion();
                                        String billingzip = getbilling.get(0).getPostcode();
                                        String billingContry = "india";
                                        String billingtel = getbilling.get(0).getTelephone();
                                        String billingEmail = USER_EMAIL;
                                        String vAccessCode = ServiceUtility.chkNull(InternetConnection.accessCode).toString().trim();
                                        String vMerchantId = ServiceUtility.chkNull(InternetConnection.merchantId).toString().trim();
                                        String vCurrency = ServiceUtility.chkNull(InternetConnection.currency).toString().trim();
                                        String vAmount = ServiceUtility.chkNull(String.valueOf(TotalPrice)).toString().trim();
                                        Log.d("DATATATATATA", "MAININFO" + vAmount);
                                        if (!vAccessCode.equals("") && !vMerchantId.equals("") && !vCurrency.equals("") && !vAmount.equals("")) {
                                            Intent intent = new Intent(PaymentMethodActivity.this, CCavenueWebviewActivity.class);
                                            intent.putExtra(AvenuesParams.ACCESS_CODE, ServiceUtility.chkNull(InternetConnection.accessCode).toString().trim());
                                            intent.putExtra(AvenuesParams.MERCHANT_ID, ServiceUtility.chkNull(InternetConnection.merchantId).toString().trim());
                                            intent.putExtra(AvenuesParams.ORDER_ID, ServiceUtility.chkNull(String.valueOf(orderId)).toString().trim());
                                            intent.putExtra(AvenuesParams.CURRENCY, ServiceUtility.chkNull(InternetConnection.currency).toString().trim());
                                            intent.putExtra(AvenuesParams.AMOUNT, ServiceUtility.chkNull(String.valueOf(TotalPrice)).toString().trim());
                                            //     intent.putExtra(AvenuesParams.AMOUNT, ServiceUtility.chkNull(String.valueOf("1")).toString().trim());
                                            intent.putExtra(AvenuesParams.REDIRECT_URL, ServiceUtility.chkNull(InternetConnection.redirectUrl).toString().trim());
                                            intent.putExtra(AvenuesParams.CANCEL_URL, ServiceUtility.chkNull(InternetConnection.cancelUrl).toString().trim());
                                            intent.putExtra(AvenuesParams.RSA_KEY_URL, ServiceUtility.chkNull(InternetConnection.rsaUrl).toString().trim());
                                            intent.putExtra(AvenuesParams.BILLING_NAME, ServiceUtility.chkNull(billingname).toString().trim());
                                            intent.putExtra(AvenuesParams.BILLING_ADDRESS, ServiceUtility.chkNull(billingaddress).toString().trim());
                                            intent.putExtra(AvenuesParams.BILLING_CITY, ServiceUtility.chkNull(billingcity).toString().trim());
                                            intent.putExtra(AvenuesParams.BILLING_STATE, ServiceUtility.chkNull(billingstate).toString().trim());
                                            intent.putExtra(AvenuesParams.BILLING_ZIP, ServiceUtility.chkNull(billingzip).toString().trim());
                                            intent.putExtra(AvenuesParams.BILLING_COUNTRY, ServiceUtility.chkNull("India").toString().trim());
                                            intent.putExtra(AvenuesParams.BILLING_TEL, ServiceUtility.chkNull(billingtel).toString().trim());
                                            intent.putExtra(AvenuesParams.BILLING_EMAIL, ServiceUtility.chkNull(billingEmail).toString().trim());
                                            startActivity(intent);
                                        }
                                    }
                                }
                          //  }

                            @Override
                            public void onFailure(Call<Responce> call, Throwable t) {
                                Log.e(" MY order >>>> ", t.toString());
                                pDialog.dismiss();
                                Toast.makeText(PaymentMethodActivity.this, "Try again", Toast.LENGTH_SHORT).show();
                            }

                        });

                    } else if (index == 2) {

                        if (isNetworkStatusAvialable(getApplicationContext())) {
//                            if (Double.parseDouble(TotalPrice) < 2501) {
                                PlacedOrder(USER_ID, addressid, TOADDRESSID);
//                            } else {
//                                Toast.makeText(PaymentMethodActivity.this, "COD Accepts Only Below ₹2500/-", Toast.LENGTH_SHORT).show();
//                            }
                        } else {
                            Toast.makeText(PaymentMethodActivity.this, "Please Internet connection check", Toast.LENGTH_SHORT).show();
                        }
//                    } else if (index == 3) {
//                    LayoutInflater li = (LayoutInflater) PaymentMethodActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                    ;
//                    View promptsView1 = li.inflate(R.layout.layout_bank_account_details, null);
//
//                    AlertDialog.Builder alertDialogBuilder1 = new AlertDialog.Builder(PaymentMethodActivity.this);
//                    icon_cancel = (ImageView) promptsView1.findViewById(R.id.icon_cancel);
//                    Button proceed = (Button) promptsView1.findViewById(R.id.proceed);
//                    // set prompts.xml to alertdialog builder
//                    alertDialogBuilder1.setView(promptsView1);
//                    alertDialogBuilder1.setCancelable(true);
//                    final AlertDialog alertDialog1 = alertDialogBuilder1.create();
//                    proceed.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            alertDialog1.dismiss();
//                            PlacedOrder(USER_ID, addressid);
//                        }
//                    });
//
//                    icon_cancel.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            alertDialog1.dismiss();
//                        }
//                    });
//
//                    // show it
//                    alertDialog1.show();
//
//                    ((ViewGroup) alertDialog1.getWindow().getDecorView()).getChildAt(0).startAnimation(AnimationUtils.loadAnimation(PaymentMethodActivity.this, R.anim.shake));


                        // Toast.makeText(PaymentMethodActivity.this, "Please Select Payment Method... :- Imps", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "internet is not avialable", Toast.LENGTH_SHORT).show();
                }

            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(PaymentMethodActivity.this, 1));
        getOnlinecartitems(this.USER_ID);

    }

    private boolean isNetworkStatusAvialable(Context applicationContext) {

        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfos = connectivityManager.getActiveNetworkInfo();
            if (netInfos != null)
                if (netInfos.isConnected())
                    return true;
        }
        return false;
    }

    public void PlacedOrder(String USER_ID, String addressid, String TOADDRESSID) {
        pDialog = new ProgressDialog(PaymentMethodActivity.this);
        pDialog.setMessage("Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
        // Log.d("DATA", "DATAUSERPLACEORDER" + this.USER_ID + this.addressid + TOADDRESSID);
        Call<Responce> call = apiService.getPlacedOrder(USER_ID, addressid, TOADDRESSID);
        call.enqueue(new Callback<Responce>() {
            @Override
            public void onResponse(Call<Responce> call, Response<Responce> response) {
                if (response.body().getSuccess() == 0) {
                    Toast.makeText(PaymentMethodActivity.this, "Unsuccessful Place order", Toast.LENGTH_SHORT).show();
                    pDialog.dismiss();
                } else {
                    pDialog.dismiss();

                    LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                    View promptsView1 = li.inflate(R.layout.dilog_ordersucces, null);

                    AlertDialog.Builder alertDialogBuilder1 = new AlertDialog.Builder(PaymentMethodActivity.this);

                    // set prompts.xml to alertdialog builder
                    alertDialogBuilder1.setView(promptsView1);
                    alertDialogBuilder1.setCancelable(false);

                    final AlertDialog alertDialog1 = alertDialogBuilder1.create();
                    TextView tvbtnOk = (TextView) promptsView1.findViewById(R.id.tvbtnOk);
                    icon_cancel = (ImageView) promptsView1.findViewById(R.id.icon_cancel);
                    icon_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog1.dismiss();
                        }
                    });
                    tvbtnOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Intent intent = new Intent(PaymentMethodActivity.this, HomActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                    Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                    Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();

                        }
                    });


                    // show it
                    alertDialog1.show();

                    ((ViewGroup) alertDialog1.getWindow().getDecorView()).getChildAt(0).startAnimation(AnimationUtils.loadAnimation(PaymentMethodActivity.this, R.anim.shake));
                    //    Toast.makeText(PaymentMethodActivity.this, "Successfully Completed Place Order..", Toast.LENGTH_LONG).show();


                }

            }

            @Override
            public void onFailure(Call<Responce> call, Throwable t) {
                Log.e("", t.toString());
                pDialog.dismiss();
                Toast.makeText(PaymentMethodActivity.this, "Try..", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getOnlinecartitems(String user_id) {
        pDialog = new ProgressDialog(PaymentMethodActivity.this);
        pDialog.setMessage("Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
        Call<ResponcePymentMethord> call = apiService.getCartPymentOnlineDetail(user_id);
        call.enqueue(new Callback<ResponcePymentMethord>() {
            @Override
            public void onResponse(Call<ResponcePymentMethord> call, Response<ResponcePymentMethord> response) {
                List<DatumPymentMethord> items = response.body().getData();
                if (response.body().getSuccess() == 0) {
                    ll_emptybox.setVisibility(View.VISIBLE);
                    rl_maintop.setVisibility(View.GONE);
                    ll_main.setVisibility(View.GONE);
                    main.setVisibility(View.GONE);
                    fl_nointernet.setVisibility(View.VISIBLE);
                    tvTotalPrice.setText("₹ " + (TotalPrice));
                    tvPOtotal.setText("₹ " + (TotalPrice));
                    recyclerView.setVisibility(View.GONE);
                    pDialog.dismiss();
                } else {
                    pDialog.dismiss();
                    cc_paymentcharg.setVisibility(View.VISIBLE);
                    ll_emptybox.setVisibility(View.GONE);
                    ll_main.setVisibility(View.VISIBLE);
                    main.setVisibility(View.VISIBLE);
                    fl_nointernet.setVisibility(View.GONE);
                    rl_maintop.setVisibility(View.VISIBLE);
                    tvTotalPrice.setText("₹ " + response.body().getProductCharge().toString());
                    txt_shpping.setVisibility(View.GONE);
//                    txt_shpping.setText("₹ " + response.body().getShippingCharge().toString());
                    tvPOtotal.setText("₹ " + response.body().getGrandTotal().toString());
                    TotalPrice = response.body().getGrandTotal().toString();
                    List<DatumPymentMethord> users = response.body().getData();
                    recyclerView.setAdapter(new CartItemPymnetMethord_Adapter(users, PaymentMethodActivity.this));
                }

            }

            @Override
            public void onFailure(Call<ResponcePymentMethord> call, Throwable t) {
                Log.e("", t.toString());
                pDialog.dismiss();
                Toast.makeText(PaymentMethodActivity.this, "Try..", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void getCodecartitems(String user_id) {

        pDialog = new ProgressDialog(PaymentMethodActivity.this);
        pDialog.setMessage("Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

        Call<ResponcePymentMethord> call = apiService.getCartPymentCODDetail(user_id);
        call.enqueue(new Callback<ResponcePymentMethord>() {
            @Override
            public void onResponse(Call<ResponcePymentMethord> call, Response<ResponcePymentMethord> response) {

                List<DatumPymentMethord> items = response.body().getData();

                if (response.body().getSuccess() == 0) {

                    ll_emptybox.setVisibility(View.VISIBLE);
                    rl_maintop.setVisibility(View.GONE);
                    ll_main.setVisibility(View.GONE);
                    main.setVisibility(View.GONE);
                    fl_nointernet.setVisibility(View.VISIBLE);
                    tvTotalPrice.setText("₹ " + (TotalPrice));
                    tvPOtotal.setText("₹ " + (TotalPrice));
                    recyclerView.setVisibility(View.GONE);
                    pDialog.dismiss();

                } else {

                    pDialog.dismiss();
                    cc_paymentcharg.setVisibility(View.VISIBLE);
                    ll_emptybox.setVisibility(View.GONE);
                    ll_main.setVisibility(View.VISIBLE);
                    main.setVisibility(View.VISIBLE);
                    fl_nointernet.setVisibility(View.GONE);
                    rl_maintop.setVisibility(View.VISIBLE);

                    tvTotalPrice.setText("₹ " + response.body().getProductCharge().toString());
//                    txt_shpping.setText("₹ " + response.body().getShippingCharge().toString());
                    tvPOtotal.setText("₹ " + response.body().getGrandTotal().toString());
                    tvYouSave.setText("₹" + response.body().getCodCharge().toString());
                    List<DatumPymentMethord> users = response.body().getData();
                    recyclerView.setAdapter(new CartItemPymnetMethord_Adapter(users, PaymentMethodActivity.this));

                }

            }

            @Override
            public void onFailure(Call<ResponcePymentMethord> call, Throwable t) {
                Log.e("", t.toString());
                pDialog.dismiss();
                Toast.makeText(PaymentMethodActivity.this, "Try..", Toast.LENGTH_SHORT).show();
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


    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("lifecycle", "onPause invoked");
    }

    @Override
    protected void onRestart() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
        super.onRestart();
        Log.d("lifecycle", "onRestart invoked");
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
