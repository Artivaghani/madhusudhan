package com.SkyzoneGroup.MadhusudhanCreation.Fragment;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.SkyzoneGroup.MadhusudhanCreation.Adapter.BraProListadapter;
import com.SkyzoneGroup.MadhusudhanCreation.R;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.ApiClient;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.ApiInterface;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.Datum;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.Responce;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class SecondFragment extends Fragment {
    RecyclerView recyclerView;
    ApiInterface apiService;
    CardView cardView;
    ImageView ivnotfound;
    String USER_ID, USER_EMAIL;
    TextView textCartItemCount;
    int mCartItemCount = 0;
    ProgressDialog pDialog;
    private OnFragmentInteractionListener mListener;

    public SecondFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        SharedPreferences prefs =getActivity().getSharedPreferences("myPref", MODE_PRIVATE);
        USER_ID = prefs.getString("USER_ID", "0");
        String USER_NAME = prefs.getString("USER_NAME", null);
        USER_EMAIL = prefs.getString("USER_EMAIL", null);
        cardView = (CardView) view.findViewById(R.id.cardview);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        apiService = ApiClient.getClient().create(ApiInterface.class);
        catalogcategory();
    //    fatchCartItem(USER_ID);

        return view;
    }

    private void catalogcategory() {
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

        Call<Responce> call = apiService.getall_categories();
        call.enqueue(new Callback<Responce>() {
            @Override
            public void onResponse(Call<Responce> call, Response<Responce> response) {
             try {
                 List<Datum> items = response.body().getData();
                 if (response.body().getData().size() == 0) {
                     pDialog.dismiss();
                     ivnotfound.setVisibility(View.VISIBLE);
                     recyclerView.setVisibility(View.GONE);

                 } else {
                     pDialog.dismiss();
                     recyclerView.setAdapter(new BraProListadapter(items, getActivity()));

                 }
             }catch(Exception e){}

            }

            @Override
            public void onFailure(Call<Responce> call, Throwable t) {
                // Log error here since request failed
                Log.e("", t.toString());
                pDialog.dismiss();
                Toast.makeText(getActivity(), "Try..", Toast.LENGTH_SHORT).show();
            }
        });
    }



    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
