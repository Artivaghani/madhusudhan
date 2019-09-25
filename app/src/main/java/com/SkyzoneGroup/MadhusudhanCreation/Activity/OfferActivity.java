package com.SkyzoneGroup.MadhusudhanCreation.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.SkyzoneGroup.MadhusudhanCreation.Adapter.ImageOfferPagerAdapter;
import com.SkyzoneGroup.MadhusudhanCreation.Model.CartList;
import com.SkyzoneGroup.MadhusudhanCreation.R;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.ApiClient;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.ApiInterface;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.Datum;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.Responce;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.ResponceCartItem;
import com.SkyzoneGroup.MadhusudhanCreation.util.AutoScrollViewPager;

import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OfferActivity extends AppCompatActivity
{
    RecyclerView recyclerView;
    ApiInterface apiService;
    ImageView ivnotfound;
    TextView textCartItemCount;
    int mCartItemCount = 0;
    ProgressDialog pDialog;
    String USER_ID;
    CircleIndicator indicator;
    AutoScrollViewPager viewpager;
    int min;
    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Offer For You");

        apiService = ApiClient.getClient().create(ApiInterface.class);

        viewpager = (AutoScrollViewPager) findViewById(R.id.news_slider);
        indicator = (CircleIndicator) findViewById(R.id.indicator);
        viewpager.startAutoScroll();
        viewpager.setInterval(5000);
        viewpager.setCycle(true);
        viewpager.setStopScrollWhenTouch(true);

        SharedPreferences prefs = getSharedPreferences("myPref", MODE_PRIVATE);
        final Boolean Login = prefs.getBoolean("LOGIN", false);
        USER_ID = prefs.getString("USER_ID", "0");
        fatchCartItem(USER_ID);

        ivnotfound = (ImageView) findViewById(R.id.iv_notfound);
        recyclerView = (RecyclerView) findViewById(R.id.recycleview);

        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(OfferActivity.this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


            }
        });
        min = viewpager.getCurrentItem();
      //  catwisepro();
        fetchSlider();
    }

    private void fetchSlider() {

        try {
            Call<Responce> call = apiService.getOfferSlider();

            call.enqueue(new Callback<Responce>() {
                @Override
                public void onResponse(Call<Responce> call, Response<Responce> response) {
                    List<Datum> items = response.body().getData();
                    Log.d("", "Number of CAT By Image received: " + items.size());

                    if (response.body().getSuccess() == 1) {
                        ImageOfferPagerAdapter madapter = new ImageOfferPagerAdapter(OfferActivity.this, items);
                        viewpager.setAdapter(madapter);
                        indicator.setViewPager(viewpager);
                    } else {

                    }

                }

                @Override
                public void onFailure(Call<Responce> call, Throwable t) {
                    // Log error here since request failed
                    Log.e("", t.toString());
                    // Toast.makeText(getActivity(), "Try Again", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception ex) {
        }

    }

    private void fatchCartItem(String user_id)
    {

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

//    private void catwisepro() {
//        pDialog = new ProgressDialog(OfferActivity.this);
//        pDialog.setMessage("Please wait...");
//        pDialog.setIndeterminate(false);
//        pDialog.setCancelable(false);
//        pDialog.show();
//        Call<Responce> call = apiService.GetOfferPage();
//        call.enqueue(new Callback<Responce>() {
//            @Override
//            public void onResponse(Call<Responce> call, retrofit2.Response<Responce> response) {
//                List<Datum> items = response.body().getData();
//                Log.d("", "Numbe " + items.size());
//                if (response.body().getData().size() == 0) {
//                    pDialog.dismiss();
//                    recyclerView.setVisibility(View.GONE);
//                    ivnotfound.setVisibility(View.VISIBLE);
//                } else {
//                    pDialog.dismiss();
//                    ivnotfound.setVisibility(View.GONE);
//                    recyclerView.setAdapter(new SubCatWiseProadapter(OfferActivity.this, items));
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<Responce> call, Throwable t) {
//                // Log error here since request failed
//                Log.e(">>>>>>", t.toString());
//                //  imageView.setVisibility(View.GONE);
//                pDialog.dismiss();
//                Toast.makeText(OfferActivity.this, "Try..", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

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
                Intent i = new Intent(OfferActivity.this, CartActivity.class);
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
