package com.SkyzoneGroup.MadhusudhanCreation.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.SkyzoneGroup.MadhusudhanCreation.Adapter.getallAddress;
import com.SkyzoneGroup.MadhusudhanCreation.Model.CartList;
import com.SkyzoneGroup.MadhusudhanCreation.R;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.ApiClient;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.ApiInterface;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.Datum;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.Responce;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.ResponceCartItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressActivity extends AppCompatActivity {
    Button btVproduct, btsave, iVProceed;
    ImageView icon_cancel;
    TextInputLayout inputLayoutPincode, inputLayoutState, inputLayoutAddtwo, inputLayoutCity;
    TextInputLayout inputLayoutAddress, inputLayoutPhoneNo, inputLayoutLastName, inputLayoutFirstName;
    TextInputEditText etFName, tv_phone_no, tv_add_one, tv_city, tv_add_two, tv_state, tv_pincode, etLName;
    String LName, FName, phone_no, add_one, city, add_two, state, pincode;
    ApiInterface apiService;
    String country = "IN";
    ImageView notfound;
    RecyclerView recyclerView;
    AlertDialog OptionDialog;
    AlertDialog alertDialog1;
    AlertDialog.Builder alertDialogBuilder1;
    String USER_ID;
    String USER_EMAIL;
    ProgressDialog pDialog;
    FrameLayout recycler_view_wrapper, fl_nointernet;
    TextView textCartItemCount;
    int mCartItemCount = 0;
    public static int Addressid = 0;
    List<CartList> itemss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        apiService = ApiClient.getClient().create(ApiInterface.class);
        SharedPreferences prefs = getSharedPreferences("myPref", MODE_PRIVATE);
        USER_ID = prefs.getString("USER_ID", "0");
        String USER_NAME = prefs.getString("USER_NAME", null);
        USER_EMAIL = prefs.getString("USER_EMAIL", null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("To Address");
        iVProceed = (Button) findViewById(R.id.iVProceed);

        recycler_view_wrapper = (FrameLayout) findViewById(R.id.recycler_view_wrapper);
        fl_nointernet = (FrameLayout) findViewById(R.id.fl_nointernet);

       // getallAddress()

        bindview();


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

    private void fatchCartItem(String user_id) {

        Call<ResponceCartItem> call = apiService.getCartDetail(user_id);
        call.enqueue(new Callback<ResponceCartItem>() {
            @Override
            public void onResponse(Call<ResponceCartItem> call, Response<ResponceCartItem> response) {
                itemss = response.body().getData();
                iVProceed.setVisibility(View.VISIBLE);
                try {
                    Log.d(">>> ", "Number of Cart Item received001:" + itemss.size());
                    mCartItemCount = itemss.size();
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

    private void bindview() {

        notfound = (ImageView) findViewById(R.id.iv_notfound);
        recyclerView = (RecyclerView) findViewById(R.id.my_List_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        btVproduct = (Button) findViewById(R.id.btVproduct);
        iVProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNetworkStatusAvialable(getApplicationContext())) {
                if (Addressid == 0 || mCartItemCount == 0) {
                    if (Addressid == 0) {
                        Toast.makeText(AddressActivity.this, " Please Select Address...", Toast.LENGTH_SHORT).show();
                    }
                    if (mCartItemCount == 0) {

                        Toast.makeText(AddressActivity.this, "Your Cart Is Empty....", Toast.LENGTH_SHORT).show();

                    }

                } else {
                    Intent intent = new Intent(AddressActivity.this, DeliveryAddressActivity.class);
                    intent.putExtra("ADDRESSID", Addressid);
                    startActivity(intent);
                }
                } else {
                    Toast.makeText(getApplicationContext(), "internet is not avialable", Toast.LENGTH_SHORT).show();
                }

            }
        });
        btVproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNetworkStatusAvialable(getApplicationContext())) {
                    LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View promptsView1 = li.inflate(R.layout.user_add_address, null);
                    alertDialogBuilder1 = new AlertDialog.Builder(AddressActivity.this);
                    // set prompts.xml to alertdialog builder
                    alertDialogBuilder1.setView(promptsView1);
                    alertDialogBuilder1.setCancelable(true);

                    alertDialog1 = alertDialogBuilder1.create();
                    alertDialog1.show();
                    SharedPreferences prefs = getSharedPreferences("myPref", MODE_PRIVATE);
                    final String USER_ID = prefs.getString("USER_ID", "0");
                    String USER_NAME = prefs.getString("USER_NAME", null);
                    final String USER_EMAIL = prefs.getString("USER_EMAIL", null);


                    inputLayoutFirstName = (TextInputLayout) promptsView1.findViewById(R.id.inputLayoutFirstName);
                    inputLayoutLastName = (TextInputLayout) promptsView1.findViewById(R.id.inputLayoutLastName);
                    inputLayoutPincode = (TextInputLayout) promptsView1.findViewById(R.id.inputLayoutPincode);
                    inputLayoutPhoneNo = (TextInputLayout) promptsView1.findViewById(R.id.inputLayoutPhoneNo);
                    inputLayoutState = (TextInputLayout) promptsView1.findViewById(R.id.inputLayoutState);
                    inputLayoutAddtwo = (TextInputLayout) promptsView1.findViewById(R.id.inputLayoutAddtwo);
                    inputLayoutCity = (TextInputLayout) promptsView1.findViewById(R.id.inputLayoutCity);
                    inputLayoutAddress = (TextInputLayout) promptsView1.findViewById(R.id.inputLayoutAddress);


                    btsave = (Button) promptsView1.findViewById(R.id.save);
                    icon_cancel = (ImageView) promptsView1.findViewById(R.id.icon_cancel);
                    etFName = (TextInputEditText) promptsView1.findViewById(R.id.etFName);
                    etLName = (TextInputEditText) promptsView1.findViewById(R.id.etLName);
                    tv_phone_no = (TextInputEditText) promptsView1.findViewById(R.id.tv_phone_no);
                    tv_add_one = (TextInputEditText) promptsView1.findViewById(R.id.tv_add_one);
                    tv_add_two = (TextInputEditText) promptsView1.findViewById(R.id.tv_add_two);
                    tv_city = (TextInputEditText) promptsView1.findViewById(R.id.tv_city);
                    tv_state = (TextInputEditText) promptsView1.findViewById(R.id.tv_state);
                    tv_pincode = (TextInputEditText) promptsView1.findViewById(R.id.tv_pincode);

                    btsave.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            FName = etFName.getText().toString();
                            LName = etLName.getText().toString();
                            phone_no = tv_phone_no.getText().toString();
                            add_one = tv_add_one.getText().toString();
                            add_two = tv_add_two.getText().toString();
                            city = tv_city.getText().toString();
                            state = tv_state.getText().toString();
                            pincode = tv_pincode.getText().toString();

                            if (FName == null || FName.isEmpty() || LName == null || LName.isEmpty() || phone_no == null || phone_no.isEmpty() || phone_no.length() != 10 || add_one == null || add_one.isEmpty() || add_two == null || add_two.isEmpty() || city == null || city.isEmpty() || state == null || state.isEmpty() || pincode == null || pincode.isEmpty() || pincode.length() != 6) {
                                if (FName == null || FName.isEmpty()) {
                                    inputLayoutFirstName.setError("Please Enter FirstName.");
                                } else {
                                    inputLayoutFirstName.setErrorEnabled(false);
                                }
                                if (LName == null || LName.isEmpty()) {
                                    inputLayoutLastName.setError("Please Enter LastName.");
                                } else {
                                    inputLayoutLastName.setErrorEnabled(false);
                                }
                                if (add_one == null || add_one.isEmpty()) {
                                    inputLayoutAddress.setError("Please Enter Address.");
                                } else {
                                    inputLayoutAddress.setErrorEnabled(false);
                                }
                                if (add_two == null || add_two.isEmpty()) {
                                    inputLayoutAddtwo.setError("Please Enter Landmark.");
                                } else {
                                    inputLayoutAddtwo.setErrorEnabled(false);
                                }


                                if (city == null || city.isEmpty()) {
                                    inputLayoutCity.setError("Please Enter City.");
                                } else {
                                    inputLayoutCity.setErrorEnabled(false);
                                }

                                if (state == null || state.isEmpty()) {
                                    inputLayoutState.setError("Please Enter State.");
                                } else {
                                    inputLayoutState.setErrorEnabled(false);
                                }
//                            if (phone_no == null || phone_no.isEmpty()) {
//                                inputLayoutPhoneNo.setError("Please Enter 10 Digit No.");
//                            } else {
//                                inputLayoutPhoneNo.setErrorEnabled(false);
//                            }

                                if (pincode.length() == 6) {
                                    inputLayoutPincode.setErrorEnabled(false);
                                } else {
                                    inputLayoutPincode.setError("Please Enter 6 Digit Pincode.");

                                }

                                if (phone_no.length() != 10) {
                                    inputLayoutPhoneNo.setError("Please Enter 10 Digit No.");


                                } else {
                                    inputLayoutPhoneNo.setErrorEnabled(false);
                                }


                                //
                            } else {
                                Addadress(USER_ID, FName, LName, phone_no, add_one, add_two, city, state, pincode, country, USER_EMAIL);
                                //Toast.makeText(AddressActivity.this, "Please All Field done", Toast.LENGTH_SHORT).show();
                            }


                        }
                    });
                    icon_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog1.dismiss();
                        }
                    });


                    ((ViewGroup) alertDialog1.getWindow().getDecorView()).getChildAt(0).startAnimation(AnimationUtils.loadAnimation(AddressActivity.this, R.anim.shake));
                } else {
                    Toast.makeText(getApplicationContext(), "internet is not avialable", Toast.LENGTH_SHORT).show();
                }


            }


        });

    }

    public void Addadress(String user_id, String fName, String lName, String phone_no, String add_one, String add_two, String city, String state, String pincode, String country, String user_email) {
        pDialog = new ProgressDialog(AddressActivity.this);
        pDialog.setMessage("Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
        Call<Responce> call = apiService.Addadress(user_id, fName, lName, phone_no, add_one, add_two, city, state, pincode, country, user_email);
        call.enqueue(new Callback<Responce>() {
            @Override
            public void onResponse(Call<Responce> call, Response<Responce> response) {
                pDialog.dismiss();
                if (response.body().getSuccess() == 0) {
                    pDialog.dismiss();
                    Toast.makeText(AddressActivity.this, "Address Not Save", Toast.LENGTH_SHORT).show();
                } else {
                    pDialog.dismiss();
                    alertDialog1.dismiss();
                    getallAddress(USER_ID);
                    Toast.makeText(AddressActivity.this, "Address Save", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<Responce> call, Throwable t) {
                // Log error here since request failed
                Log.e("", t.toString());
                pDialog.dismiss();
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

    public void getallAddress(String USER_ID) {
        pDialog = new ProgressDialog(AddressActivity.this);
        pDialog.setMessage("Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
        Log.d("DATA", "MAINMINA" + USER_ID);
        Call<Responce> call = apiService.getalladdress(USER_ID);
        call.enqueue(new Callback<Responce>() {
            @Override
            public void onResponse(Call<Responce> call, Response<Responce> response) {
                List<Datum> items = response.body().getData();
                pDialog.dismiss();
                if (response.body().getSuccess() == 0) {
                    Log.d("DATA", "MAINMAINSIZE" + response.body().getSuccess());
                    notfound.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);

                } else {
                    Log.d("DATA", "MAINMAINSIZE" + response.body().getSuccess());
                    notfound.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    // set the adapter object to the Recyclerview
                    recyclerView.setAdapter(new getallAddress(items, AddressActivity.this));

                }

            }

            @Override
            public void onFailure(Call<Responce> call, Throwable t) {
                // Log error here since request failed
                Log.e("", t.toString());
                pDialog.dismiss();
                Toast.makeText(AddressActivity.this, "Try..", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mycart, menu);
        final MenuItem menuItem = menu.findItem(R.id.action_cart);
        View actionView = MenuItemCompat.getActionView(menuItem);
        textCartItemCount = (TextView) actionView.findViewById(R.id.cart_count);

        setupBadge(mCartItemCount);
        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AddressActivity.this, CartActivity.class);
                startActivity(i);
                finish();
            }
        });

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

    public void addressdelete(String entityId) {

        pDialog = new ProgressDialog(AddressActivity.this);
        pDialog.setMessage("Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
        Call<Responce> call = apiService.deleteaddress(entityId);
        call.enqueue(new Callback<Responce>() {
            @Override
            public void onResponse(Call<Responce> call, Response<Responce> response) {
                pDialog.dismiss();
                if (response.body().getSuccess() == 0) {
                    pDialog.dismiss();
                    Toast.makeText(AddressActivity.this, "Shipping Address Not Delete", Toast.LENGTH_SHORT).show();
                } else {
                    pDialog.dismiss();
                    getallAddress(USER_ID);
                    Toast.makeText(AddressActivity.this, "Shipping Address Delete", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<Responce> call, Throwable t) {
                // Log error here since request failed
                Log.e("", t.toString());
                Toast.makeText(AddressActivity.this, "Try..", Toast.LENGTH_SHORT).show();
                pDialog.dismiss();
            }
        });
    }

    public void addressupdate(final String entityId) {
        //   Toast.makeText(this, entityId, Toast.LENGTH_SHORT).show();

        LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View promptsView1 = li.inflate(R.layout.user_add_address, null);
        alertDialogBuilder1 = new AlertDialog.Builder(AddressActivity.this);
        alertDialogBuilder1.setView(promptsView1);
        alertDialogBuilder1.setCancelable(true);

        alertDialog1 = alertDialogBuilder1.create();
        alertDialog1.show();
        btsave = (Button) promptsView1.findViewById(R.id.save);
        icon_cancel = (ImageView) promptsView1.findViewById(R.id.icon_cancel);
        etFName = (TextInputEditText) promptsView1.findViewById(R.id.etFName);
        etLName = (TextInputEditText) promptsView1.findViewById(R.id.etLName);
        tv_phone_no = (TextInputEditText) promptsView1.findViewById(R.id.tv_phone_no);
        tv_add_one = (TextInputEditText) promptsView1.findViewById(R.id.tv_add_one);
        tv_add_two = (TextInputEditText) promptsView1.findViewById(R.id.tv_add_two);
        tv_city = (TextInputEditText) promptsView1.findViewById(R.id.tv_city);
        tv_state = (TextInputEditText) promptsView1.findViewById(R.id.tv_state);
        tv_pincode = (TextInputEditText) promptsView1.findViewById(R.id.tv_pincode);


        pDialog = new ProgressDialog(AddressActivity.this);
        pDialog.setMessage("Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
        Call<Responce> call = apiService.GetAutoFeldData(entityId);
        call.enqueue(new Callback<Responce>() {
            @Override
            public void onResponse(Call<Responce> call, Response<Responce> response) {
                List<Datum> DATAIETEMS = response.body().getData();
                pDialog.dismiss();
                if (response.body().getSuccess() == 0) {
                    // pDialog.dismiss();
                    Toast.makeText(AddressActivity.this, "Shipping Address Data Not Available.", Toast.LENGTH_LONG).show();
                } else {
                    pDialog.dismiss();
                    getallAddress(USER_ID);
                    etFName.setText(DATAIETEMS.get(0).getFirstname().toString());
                    etLName.setText(DATAIETEMS.get(0).getLastname().toString());
                    tv_phone_no.setText(DATAIETEMS.get(0).getTelephone().toString());
                    tv_add_one.setText(DATAIETEMS.get(0).getStreet1().toString());
                    tv_add_two.setText(DATAIETEMS.get(0).getStreet2().toString());
                    tv_city.setText(DATAIETEMS.get(0).getCity().toString());
                    tv_state.setText(DATAIETEMS.get(0).getRegion().toString());
                    tv_pincode.setText(DATAIETEMS.get(0).getPostcode().toString());
                    //  Toast.makeText(AddressActivity.this, "Shipping Address Delete", Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<Responce> call, Throwable t) {
                // Log error here since request failed
                Log.e("", t.toString());
                Toast.makeText(AddressActivity.this, "Try..", Toast.LENGTH_SHORT).show();
                pDialog.dismiss();
            }
        });


        btsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FName = etFName.getText().toString();
                LName = etLName.getText().toString();
                phone_no = tv_phone_no.getText().toString();
                add_one = tv_add_one.getText().toString();
                add_two = tv_add_two.getText().toString();
                city = tv_city.getText().toString();
                state = tv_state.getText().toString();
                pincode = tv_pincode.getText().toString();
                if (FName != null && !FName.isEmpty() || LName != null && !LName.isEmpty() || phone_no != null && !phone_no.isEmpty() || phone_no.length() == 10 || pincode.length() == 6 || add_one != null && !add_one.isEmpty() || add_two != null && !add_two.isEmpty() || city != null && !city.isEmpty() || state != null && !state.isEmpty() || pincode != null && !pincode.isEmpty()) {
                    updateaddress(entityId, FName, LName, phone_no, add_one, add_two, city, state, pincode, country, USER_EMAIL);
                } else {
                    Toast.makeText(AddressActivity.this, "Please All Field Required", Toast.LENGTH_LONG).show();
                }
            }
        });
        icon_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog1.dismiss();
            }
        });


        ((ViewGroup) alertDialog1.getWindow().getDecorView()).getChildAt(0).startAnimation(AnimationUtils.loadAnimation(AddressActivity.this, R.anim.shake));
    }

    private void updateaddress(String entityId, String fName, String lName, String phone_no, String add_one, String add_two, String city, String state, String pincode, String country, String user_email) {

        pDialog = new ProgressDialog(AddressActivity.this);
        pDialog.setMessage("Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
        //   Toast.makeText(activity, addressid, Toast.LENGTH_SHORT).show();
        Call<Responce> call = apiService.updateddadress(entityId, fName, lName, phone_no, add_one, add_two, city, state, pincode, country);
        call.enqueue(new Callback<Responce>() {
            @Override
            public void onResponse(Call<Responce> call, Response<Responce> response) {
                pDialog.dismiss();
                if (response.body().getSuccess() == 0) {
                    pDialog.dismiss();
                    Toast.makeText(AddressActivity.this, "Address Not Save", Toast.LENGTH_SHORT).show();
                } else {
                    pDialog.dismiss();
                    AddressActivity.this.getallAddress(USER_ID);
                    alertDialog1.dismiss();
                    Toast.makeText(AddressActivity.this, "Address Save", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Responce> call, Throwable t) {
                // Log error here since request failed
                Log.e("", t.toString());
                pDialog.dismiss();
            }
        });

    }

    @Override
    public void onRestart() {
        super.onRestart();
        if (isNetworkStatusAvialable(getApplicationContext())) {
         //   getallAddress(USER_ID);
//            fatchCartItem(USER_ID);

            //  Toast.makeText(getApplicationContext(), "internet avialable", Toast.LENGTH_SHORT).show();
        } else {
            recycler_view_wrapper.setVisibility(View.GONE);
            fl_nointernet.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(), "internet is not avialable", Toast.LENGTH_SHORT).show();

        }
        //getallAddress(USER_ID);
    }
    protected void onStart() {
        super.onStart();
        if (isNetworkStatusAvialable(getApplicationContext())) {
            getallAddress(USER_ID);
            fatchCartItem(USER_ID);
            Addressid = 0;
            // fatchCartItem(USER_ID);

            //  Toast.makeText(getApplicationContext(), "internet avialable", Toast.LENGTH_SHORT).show();
        } else {
            recycler_view_wrapper.setVisibility(View.GONE);
            fl_nointernet.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(), "internet is not avialable", Toast.LENGTH_SHORT).show();

        }

    }

}
