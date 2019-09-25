package com.SkyzoneGroup.MadhusudhanCreation.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.balysv.materialripple.MaterialRippleLayout;
import com.SkyzoneGroup.MadhusudhanCreation.Adapter.SubCatWiseProadapter;
import com.SkyzoneGroup.MadhusudhanCreation.Model.CartList;
import com.SkyzoneGroup.MadhusudhanCreation.Model.Subcatdatamodel;
import com.SkyzoneGroup.MadhusudhanCreation.R;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.ApiClient;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.ApiInterface;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.ResponceCartItem;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.ResponceSub;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CatWiseProActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ApiInterface apiService;
    ImageView ivnotfound;
    TextView textCartItemCount, tvDescr;
    int mCartItemCount = 0;
    ProgressDialog pDialog;
    String USER_ID;
    MaterialRippleLayout iv_copy;
    String descre;
    LinearLayout ll_whatsapp;
    List<Subcatdatamodel> dataitems;
    ArrayList<Uri> uris = new ArrayList<>();
    public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_wise_pro);
        String Catid = getIntent().getStringExtra("CATID");
        String CatName = getIntent().getStringExtra("CATNAME");


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(CatName);
        apiService = ApiClient.getClient().create(ApiInterface.class);

        SharedPreferences prefs = getSharedPreferences("myPref", MODE_PRIVATE);
        final Boolean Login = prefs.getBoolean("LOGIN", false);
        USER_ID = prefs.getString("USER_ID", "0");
        fatchCartItem(USER_ID);

        ivnotfound = (ImageView) findViewById(R.id.iv_notfound);
        recyclerView = (RecyclerView) findViewById(R.id.recycleview);
        recyclerView.setNestedScrollingEnabled(false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


            }
        });

        catwisepro(Catid);
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

    public void catwisepro(String catid) {
        pDialog = new ProgressDialog(CatWiseProActivity.this);
        pDialog.setMessage("Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
        Toast.makeText(this, catid, Toast.LENGTH_SHORT).show();
        Call<ResponceSub> call = apiService.Getsubcat(catid);
        call.enqueue(new Callback<ResponceSub>() {
            @Override
            public void onResponse(Call<ResponceSub> call, retrofit2.Response<ResponceSub> response) {
                dataitems = response.body().getData();
                pDialog.dismiss();
                Log.d("DATASIZE","MAINDATASuccess" + response.body().getSuccess() );
                Log.d("", "Numbe" + dataitems.size());
                if (response.body().getData().size() == 0) {
                    recyclerView.setVisibility(View.GONE);
                    ivnotfound.setVisibility(View.VISIBLE);
                } else {
                    ivnotfound.setVisibility(View.GONE);
                    recyclerView.setAdapter(new SubCatWiseProadapter(CatWiseProActivity.this, dataitems));
                }

            }

            @Override
            public void onFailure(Call<ResponceSub> call, Throwable t) {
                Log.e(">>>>>>", t.toString());
                pDialog.dismiss();
                Toast.makeText(CatWiseProActivity.this, "Try..", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void shareImages() {


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
                Intent i = new Intent(CatWiseProActivity.this, CartActivity.class);
                startActivity(i);
            }
        });

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

    protected void onStart() {
        super.onStart();
        fatchCartItem(USER_ID);
    }
}
