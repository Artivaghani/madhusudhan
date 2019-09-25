package com.SkyzoneGroup.MadhusudhanCreation.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.SkyzoneGroup.MadhusudhanCreation.R;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.ApiClient;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.ApiInterface;

import static android.content.Context.MODE_PRIVATE;


public class TherdFragment extends Fragment {
    LinearLayout ll_emptybox;
    TextView text_view_start;
    ApiInterface apiService;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter Adapter;
    ImageView imageView;
    ImageView ivnotfound;
    ViewPager viewpager;
    int min;

    public TherdFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_therd, container, false);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        bindview(view);


        return view;
    }

    private void bindview(View view) {
        SharedPreferences prefs = getActivity().getSharedPreferences("myPref", MODE_PRIVATE);
        final String USER_ID = prefs.getString("USER_ID", "0");
        String USER_NAME = prefs.getString("USER_NAME", null);
        String USER_EMAIL = prefs.getString("USER_EMAIL", null);
        ll_emptybox = (LinearLayout) view.findViewById(R.id.ll_emptybox);
        text_view_start = (TextView) view.findViewById(R.id.text_view_start);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        Log.d("datatat","maiandata" + USER_ID);
      //  getcartitems(USER_ID);
        text_view_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }




//    public  void getcartitems(String USER_ID) {
//        Call<Responce> call = apiService.getCartDetail(USER_ID);
//        call.enqueue(new Callback<Responce>() {
//            @Override
//            public void onResponse(Call<Responce> call, Response<Responce> response) {
//                List<Datum> items = response.body().getData();
//                if (response.body().getData().size() == 0) {
//                    ll_emptybox.setVisibility(View.VISIBLE);
//                    recyclerView.setVisibility(View.GONE);
//                    imageView.setVisibility(View.GONE);
//                } else {
//                    ll_emptybox.setVisibility(View.GONE);
//                    Adapter = new CartItem_Adapter(items, getActivity());
//                    // set the adapter object to the Recyclerview
//                    recyclerView.setAdapter(Adapter);
//
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<Responce> call, Throwable t) {
//                Log.e("", t.toString());
//                Toast.makeText(getActivity(), "Try..", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
    @Override
    public void onDetach() {
        super.onDetach();

    }


}
