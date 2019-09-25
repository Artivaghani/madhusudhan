package com.SkyzoneGroup.MadhusudhanCreation.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.SkyzoneGroup.MadhusudhanCreation.Adapter.CartItem_Adapter;
import com.SkyzoneGroup.MadhusudhanCreation.Model.CartList;
import com.SkyzoneGroup.MadhusudhanCreation.R;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.ApiClient;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.ApiInterface;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.Responce;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.ResponceCartItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {
    LinearLayout ll_emptybox,ll_total;
    TextView text_view_start;
    ApiInterface apiService;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter Adapter;
    int min;
    Button proceed;
    TextView textCartItemCount;
    int mCartItemCount = 0;
    int cartitem = 0;
    String USER_ID;
    ProgressDialog pDialog;
    List<CartList> items;
    TextView txt_item, txt_total;
    float TotalPrice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("My Cart");
        apiService = ApiClient.getClient().create(ApiInterface.class);
        SharedPreferences prefs = getSharedPreferences("myPref", MODE_PRIVATE);
        USER_ID = prefs.getString("USER_ID", "0");
        String USER_NAME = prefs.getString("USER_NAME", null);
        String USER_EMAIL = prefs.getString("USER_EMAIL", null);
        bindview(USER_ID);
    }

    private void bindview(String USER_ID) {
        proceed = (Button) findViewById(R.id.proceed);
        ll_emptybox = (LinearLayout) findViewById(R.id.ll_emptybox);
        ll_total= (LinearLayout) findViewById(R.id.ll_total);
        ll_total.setVisibility(View.GONE);
        text_view_start = (TextView) findViewById(R.id.text_view_start);

        txt_item = (TextView) findViewById(R.id.txt_item);
        txt_total = (TextView) findViewById(R.id.txt_total);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(CartActivity.this, 1));
        getcartitems(USER_ID);
        //   fatchCartItem(USER_ID);
        text_view_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartActivity.this, HomActivity.class);
                startActivity(intent);
                finish();
            }
        });
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartActivity.this, AddressActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void getcartitems(String user_id) {
        pDialog = new ProgressDialog(CartActivity.this);
        pDialog.setMessage("Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
        Call<ResponceCartItem> call = apiService.getCartDetail(user_id);
        call.enqueue(new Callback<ResponceCartItem>() {
            @Override
            public void onResponse(Call<ResponceCartItem> call, Response<ResponceCartItem> response) {
                List<CartList> items = response.body().getData();

                if (response.body().getSuccess() == 0) {
                    ll_emptybox.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                    ll_total.setVisibility(View.GONE);
                    proceed.setVisibility(View.GONE);
                    pDialog.dismiss();
                } else {
                    pDialog.dismiss();
                    ll_total.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);
                    ll_emptybox.setVisibility(View.GONE);
                    proceed.setVisibility(View.VISIBLE);

                    txt_item.setText(items.size() + " Items");

                    for (int i = 0; i < items.size(); i++) {

                        Float xyz = Float.valueOf(items.get(i).getSubTotal());
                        TotalPrice = TotalPrice + xyz;
                        txt_total.setText("₹ " + (TotalPrice) +"/-");
                    }


                    Adapter = new CartItem_Adapter(items, CartActivity.this);
                    recyclerView.setAdapter(Adapter);
                    TotalPrice = 0 ;
                    //    fatchCartItem(USER_ID);

                }

            }

            @Override
            public void onFailure(Call<ResponceCartItem> call, Throwable t) {
                Log.e("", t.toString());
                pDialog.dismiss();
                Toast.makeText(CartActivity.this, "Try..", Toast.LENGTH_SHORT).show();
            }
        });
    }

//    private void fatchCartItem(String user_id) {
//        Call<ResponceCartItem> call = apiService.getCartDetail(user_id);
//        call.enqueue(new Callback<ResponceCartItem>() {
//            @Override
//            public void onResponse(Call<ResponceCartItem> call, Response<ResponceCartItem> response) {
//                items = response.body().getData();
//                try {
//                    Log.d(">>> ", "Number of Cart Item received: " + items.size());
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
//
//    }

//    private void setupBadge(int itemcount) {
//        if (textCartItemCount != null) {
//            if (this.mCartItemCount == 0) {
//                if (textCartItemCount.getVisibility() != View.GONE) {
//                    textCartItemCount.setVisibility(View.GONE);
//                }
//            } else {
//                textCartItemCount.setText(String.valueOf(Math.min(this.mCartItemCount, itemcount)));
//                if (textCartItemCount.getVisibility() != View.VISIBLE) {
//                    textCartItemCount.setVisibility(View.VISIBLE);
//                }
//            }
//
//        }
//    }

    public void UpdateCartQuntity(String itemId, int i) {
        pDialog = new ProgressDialog(CartActivity.this);
        pDialog.setMessage("Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
        Call<Responce> call = apiService.updateToCart(USER_ID, itemId, i);
        //       Toast.makeText(this, ">> " + USER_ID + " >> " + itemId + " >> " + i, Toast.LENGTH_SHORT).show();
        call.enqueue(new Callback<Responce>() {
            @Override
            public void onResponse(Call<Responce> call, Response<Responce> response) {
                if (response.body().getSuccess() == 0) {
                    Toast.makeText(CartActivity.this, "Product Updated Not Quantity", Toast.LENGTH_SHORT).show();
                } else {

                    pDialog.dismiss();
                    getcartitems(USER_ID);
                    Toast.makeText(CartActivity.this, "Product Updated Quantity", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Responce> call, Throwable t) {
                Log.e(">> ", t.toString());
                Toast.makeText(CartActivity.this, "Try..", Toast.LENGTH_SHORT).show();
                pDialog.dismiss();
            }
        });
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.mycart, menu);
//
//        final MenuItem menuItem = menu.findItem(R.id.action_cart);
//        final View actionView = MenuItemCompat.getActionView(menuItem);
//        textCartItemCount = (TextView) actionView.findViewById(R.id.cart_count);
//        setupBadge(mCartItemCount);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    protected void onStart() {
        super.onStart();
        //  fatchCartItem(USER_ID);
    }

    @Override
    protected void onStop() {
        super.onStop();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }

    @Override
    protected void onPause() {
        super.onPause();


    }

    @Override
    protected void onResume() {
        super.onResume();


    }

}
