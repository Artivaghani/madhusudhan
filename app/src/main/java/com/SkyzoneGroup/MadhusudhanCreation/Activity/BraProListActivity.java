package com.SkyzoneGroup.MadhusudhanCreation.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.SkyzoneGroup.MadhusudhanCreation.Adapter.BraProListadapter;
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

public class BraProListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ApiInterface apiService;
    CardView cardView;
    ImageView ivnotfound;
    String USER_ID, USER_EMAIL;
    TextView textCartItemCount;
    int mCartItemCount = 0;
    ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bra_pro_list);

        SharedPreferences prefs = getSharedPreferences("myPref", MODE_PRIVATE);
        USER_ID = prefs.getString("USER_ID", "0");
        String USER_NAME = prefs.getString("USER_NAME", null);
        USER_EMAIL = prefs.getString("USER_EMAIL", null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Collections");
        cardView = (CardView) findViewById(R.id.cardview);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        apiService = ApiClient.getClient().create(ApiInterface.class);
        catalogcategory();
        fatchCartItem(USER_ID);

    }

    private void catalogcategory() {
        pDialog = new ProgressDialog(BraProListActivity.this);
        pDialog.setMessage("Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
        Call<Responce> call = apiService.getall_categories();
        call.enqueue(new Callback<Responce>() {
            @Override
            public void onResponse(Call<Responce> call, Response<Responce> response) {
                List<Datum> items = response.body().getData();
                if (response.body().getData().size() == 0) {
                    pDialog.dismiss();
                    ivnotfound.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);

                } else {
                    pDialog.dismiss();
                    recyclerView.setAdapter( new BraProListadapter(items, BraProListActivity.this));

                }

            }

            @Override
            public void onFailure(Call<Responce> call, Throwable t) {
                // Log error here since request failed
                Log.e("", t.toString());
                pDialog.dismiss();
                Toast.makeText(BraProListActivity.this, "Try..", Toast.LENGTH_SHORT).show();
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
        getMenuInflater().inflate(R.menu.mycart, menu);
        final MenuItem menuItem = menu.findItem(R.id.action_cart);
        View actionView = MenuItemCompat.getActionView(menuItem);
        textCartItemCount = (TextView) actionView.findViewById(R.id.cart_count);

        setupBadge(mCartItemCount);
        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BraProListActivity.this, CartActivity.class);
                startActivity(i);
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
    protected void onStart() {
        super.onStart();
        fatchCartItem(USER_ID);
    }
}
