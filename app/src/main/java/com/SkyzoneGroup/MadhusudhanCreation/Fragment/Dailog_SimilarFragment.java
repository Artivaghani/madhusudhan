package com.SkyzoneGroup.MadhusudhanCreation.Fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.SkyzoneGroup.MadhusudhanCreation.Adapter.Dailog_SimilarProduct_Adapter;
import com.SkyzoneGroup.MadhusudhanCreation.Model.Subcatdatamodel;
import com.SkyzoneGroup.MadhusudhanCreation.R;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.ApiClient;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.ApiInterface;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.ResponceSub;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

@SuppressLint("ValidFragment")
public class Dailog_SimilarFragment extends BottomSheetDialogFragment {


    String ProductId;
    ApiInterface apiService;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter Adapter;
    String USER_ID;

    TextView tvPageName;
    ImageView imageView;

    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
            }

        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    };

    @SuppressLint("ValidFragment")
    public Dailog_SimilarFragment(String product_id)
    {
        this.ProductId=product_id;
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View contentView = View.inflate(getContext(), R.layout.fragment_dailog__wish_list, null);
        dialog.setContentView(contentView);

        SharedPreferences prefs = getActivity().getSharedPreferences("myPref", MODE_PRIVATE);
        USER_ID= prefs.getString("USER_ID",null);


        apiService = ApiClient.getClient().create(ApiInterface.class);
        recyclerView = (RecyclerView)contentView.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        //   recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),1,1,false));

        tvPageName = (TextView)contentView.findViewById(R.id.tvPageName);
        tvPageName.setText("Similar Products");

        imageView=(ImageView)contentView.findViewById(R.id.image);
        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(imageView);
        Glide.with(this).load(R.raw.special).into(imageViewTarget);

        getSimilarProduct(ProductId);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        if( behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }
    }

    public void AddWishList(String user_id, String productId)
    {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Log.d(">> ", "AddWishList: "+user_id+" >> "+productId);
        Call<ResponceSub> call = apiService.addWish(user_id,productId);
        call.enqueue(new Callback<ResponceSub>() {
            @Override
            public void onResponse(Call<ResponceSub> call, Response<ResponceSub> response) {
//                        int users = response.body().getSuccess();
//                        Log.d(">> ", "Number of WISH: " + users);
            }

            @Override
            public void onFailure(Call<ResponceSub>call, Throwable t) {
                // Log error here since request failed
                Log.e(">> ", t.toString());
            }
        });
    }

    public void RemoveWish(String User_id,String product_Id)
    {
        ApiInterface apiService1 = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponceSub> call = apiService.removeWishList(User_id,product_Id);
        call.enqueue(new Callback<ResponceSub>() {
            @Override
            public void onResponse(Call<ResponceSub> call, Response<ResponceSub> response) {

//                        int users = response.body().getSuccess();
//                        Log.d(">> ", "Number of WISH: " + users);
                // mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponceSub>call, Throwable t) {
                // Log error here since request failed
                Log.e(">> ", t.toString());
            }
        });
    }


    private void getSimilarProduct(String productId)
    {
        try {
            Call<ResponceSub> call = apiService.getSimilarProduct(productId,USER_ID);

            call.enqueue(new Callback<ResponceSub>() {
                @Override
                public void onResponse(Call<ResponceSub> call, Response<ResponceSub> response) {

                    if (response.body().getSuccess()==1)
                    {
                        List<Subcatdatamodel> Sub_CatItem = response.body().getData();

                        imageView.setVisibility(View.GONE);
                        Collections.shuffle(Sub_CatItem);

//                        SharedPreferences prefs = getActivity().getSharedPreferences("myPref", MODE_PRIVATE);
//                        String USER_ID= prefs.getString("USER_ID",null);
                        Adapter = new Dailog_SimilarProduct_Adapter(Sub_CatItem,Dailog_SimilarFragment.this,USER_ID);
                        // set the adapter object to the Recyclerview
//                        Toast.makeText(getActivity(), response.body().getMessage()+"---"+response.body().getSuccess(), Toast.LENGTH_SHORT).show();
                        recyclerView.setAdapter(Adapter);
                    }
                    else
                    {
                        dismiss();
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<ResponceSub> call, Throwable t) {
                    Log.e("", t.toString());
                    imageView.setVisibility(View.GONE);
                }

            });
        }catch (Exception ex)
        {}
    }

}