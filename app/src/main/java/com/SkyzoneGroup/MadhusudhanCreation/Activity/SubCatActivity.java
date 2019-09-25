package com.SkyzoneGroup.MadhusudhanCreation.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import com.SkyzoneGroup.MadhusudhanCreation.Adapter.CatWiseProadapter;
import com.SkyzoneGroup.MadhusudhanCreation.Model.CartList;
import com.SkyzoneGroup.MadhusudhanCreation.Model.Subcatdatamodel;
import com.SkyzoneGroup.MadhusudhanCreation.R;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.ApiClient;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.ApiInterface;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.ResponceCartItem;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.ResponceSub;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubCatActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ApiInterface apiService;
    ImageView ivnotfound;
    String USER_ID, USER_EMAIL;
    TextView textCartItemCount;
    int mCartItemCount = 0;
    ProgressDialog pDialog;
    String namwe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_cat);

        apiService = ApiClient.getClient().create(ApiInterface.class);
        String catid = getIntent().getStringExtra("CATID");

        if( getIntent().getExtras() != null)
        {
            namwe = getIntent().getStringExtra("CATNAME");
        }else {
            namwe =  "Offer Name";
        }


        SharedPreferences prefs = getSharedPreferences("myPref", MODE_PRIVATE);
        USER_ID = prefs.getString("USER_ID", "0");
        String USER_NAME = prefs.getString("USER_NAME", null);
        USER_EMAIL = prefs.getString("USER_EMAIL", null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(namwe);


        ivnotfound = (ImageView) findViewById(R.id.iv_notfound);

        recyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        catalogcategory(catid);
        fatchCartItem(USER_ID);
    }

    private void catalogcategory(String catid) {

        Log.d("MAINDATA", "DATATAMAINA" + catid);
        pDialog = new ProgressDialog(SubCatActivity.this);
        pDialog.setMessage("Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
//        Toast.makeText(this, catid, Toast.LENGTH_SHORT).show();
        Call<ResponceSub> call = apiService.Getcatwisesubcat(catid);
        call.enqueue(new Callback<ResponceSub>() {
            @Override
            public void onResponse(Call<ResponceSub> call, retrofit2.Response<ResponceSub> response) {
              try {
                  List<Subcatdatamodel> itemss = response.body().getData();
                  if (response.body().getSuccess() == 0) {
                      pDialog.dismiss();
                      recyclerView.setVisibility(View.GONE);
                      ivnotfound.setVisibility(View.VISIBLE);
                  } else {
                      pDialog.dismiss();
                      ivnotfound.setVisibility(View.GONE);
                      recyclerView.setVisibility(View.VISIBLE);
                      recyclerView.setAdapter(new CatWiseProadapter(SubCatActivity.this, itemss));
                  }
              }catch (Exception e){}

            }

            @Override
            public void onFailure(Call<ResponceSub> call, Throwable t) {
                // Log error here since request failed
                Log.e(">>>>>>", t.toString());
                //  imageView.setVisibility(View.GONE);
                pDialog.dismiss();
                Toast.makeText(SubCatActivity.this, "Try..", Toast.LENGTH_SHORT).show();
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
//                Intent i = new Intent(SubCatActivity.this, CartActivity.class);
//                startActivity(i);
//            }
//        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {

        switch (menuItem.getItemId()) {

            case android.R.id.home:
                   super.onBackPressed();
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
