package com.SkyzoneGroup.MadhusudhanCreation.Fragment;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.SkyzoneGroup.MadhusudhanCreation.Adapter.Search_Adapter;
import com.SkyzoneGroup.MadhusudhanCreation.BuildConfig;
import com.SkyzoneGroup.MadhusudhanCreation.R;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.ApiClient;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.ApiInterface;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.Datum;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.MultipleImage;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.Responce;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class SearchFragment extends Fragment implements Search_Adapter.ShareClickShare {

    ApiInterface apiService;
    String USER_ID;
    private RecyclerView recyclerView;
    SearchView search;
    String newText = "";
    ProgressDialog pDialog;
    String descrepstion;
    static String Descre;
    //Share
    Search_Adapter adapter;
    List<Datum> Sub_CatItem;
    public static RelativeLayout popupshare;
    public static RelativeLayout rlShareImage, rlShareText, rlShareOther;
    public static TextView txtShareImage, txtShareText;
    private static final int REQUEST_PERMISSION = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    private static long refid;
    ArrayList<Long> list = new ArrayList<>();
    static ArrayList<String> pathlist = new ArrayList<String>();

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);


        //show keyboard when any fragment of this class has been attached
        showSoftwareKeyboard(true);
    }
    protected void showSoftwareKeyboard(boolean showKeyboard){
        final Activity activity = getActivity();
        final InputMethodManager inputManager = (InputMethodManager)activity.getSystemService(getActivity().INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), showKeyboard ? InputMethodManager.SHOW_FORCED : InputMethodManager.HIDE_NOT_ALWAYS);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        SharedPreferences prefs = getActivity().getSharedPreferences("myPref", MODE_PRIVATE);
        USER_ID = prefs.getString("USER_ID", "0");
        LinearLayout myLayout =(LinearLayout)view.findViewById(R.id.myLayout);
        recyclerView = (RecyclerView)view.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        ///////share//////////////
        popupshare = view.findViewById(R.id.popupshare);
        rlShareImage = view.findViewById(R.id.rlShareImage);
        rlShareText = view.findViewById(R.id.rlShareText);
        txtShareImage = view.findViewById(R.id.txtShareImage);
        txtShareText = view.findViewById(R.id.txtShareText);
        ///////share over //////////////
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1, 1, false));
        search = (SearchView) view.findViewById(R.id.search);
        search.setIconified(false);
        InputMethodManager inputMethodManager =
                (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInputFromWindow(
                search.getApplicationWindowToken(),
                InputMethodManager.SHOW_FORCED, 0);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getSerachProduct(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });

        return view;
    }

    private void getSerachProduct(String query) {
        Toast.makeText(getActivity(), query, Toast.LENGTH_SHORT).show();
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
        try {
            Call<Responce> call = apiService.search(query);

            call.enqueue(new Callback<Responce>() {
                @Override
                public void onResponse(Call<Responce> call, Response<Responce> response) {
                    pDialog.dismiss();
                    if (response.body().getSuccess() > 0) {
                       Sub_CatItem = response.body().getData();

                        Collections.shuffle(Sub_CatItem);

                        //  imageView.setVisibility(View.GONE);

                        adapter = new Search_Adapter(Sub_CatItem, getActivity());
                        adapter.RegistershareClickShareClick(SearchFragment.this);
                        // set the adapter object to the Recyclerview
                        recyclerView.setAdapter(adapter);

                    } else {
                        //    dismiss();
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<Responce> call, Throwable t) {
                    // Log error here since request failed
                    Log.e("", t.toString());
                    pDialog.dismiss();
                    // imageView.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Try Again", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception ex) {
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        showSoftwareKeyboard(false);
    }

    @Override
    public void ShareClickShare(int position) {
        sharedata(Sub_CatItem.get(position).getProductId());
    }

    public void sharedata(String Product_Id) {
        Call<Responce> call = apiService.Getproductinfo(Product_Id);
        call.enqueue(new Callback<Responce>() {
            @Override
            public void onResponse(Call<Responce> call, retrofit2.Response<Responce> response) {
                List<Datum> items = response.body().getData();
                Log.d("", "Numbe " + items.size());
                if (response.body().getData().size() == 0) {
                    //   llmain.setVisibility(View.VISIBLE);
                } else {
                    popupshare.setVisibility(View.VISIBLE);
                    rlShareImage.setBackgroundResource(R.drawable.round_share);
                    txtShareImage.setTextColor(ContextCompat.getColor(getContext(), R.color.drak_grey));
                    rlShareText.setBackgroundResource(R.drawable.round_share);
                    txtShareText.setTextColor(ContextCompat.getColor(getContext(), R.color.drak_grey));
                    Descre = "Name :-" + items.get(0).getProductName().toString() + "\n" +
                            "SkuCode :-"  + items.get(0).getSku().toString() + "\n" +
                            "Price :-"  + items.get(0).getSpecialPrice().toString()+ "\n" +
                            "Description :-" + "\n" + Html.fromHtml(items.get(0).getDescription().toString());

                    ArrayList<String> dataurl = new ArrayList<>();
                    List<MultipleImage> multipleImages = items.get(0).getMultipleImages();
                    for (int i = 0; i < multipleImages.size(); i++) {
                        dataurl.add(multipleImages.get(i).getImage());
                    }
//                    SetMainImage(items.get(0).getProductImage());
              //      Log.d("DATAITEM", "DESCRIPTION" + description);
                    if (dataurl.size() > 0) {

                        DownloadManager downloadManager = (DownloadManager)getContext().getSystemService(Context.DOWNLOAD_SERVICE);
                        for (int i = 0; i < dataurl.size(); i++) {
                            Uri Download_Uri = Uri.parse(dataurl.get(i));
                            DownloadManager.Request request = new DownloadManager.Request(Download_Uri);
                            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                            request.setAllowedOverRoaming(false);
                            request.setTitle("Images Downloading " + "Sample_" + i + ".png");
                            request.setDescription("Downloading " + "Sample_" + i + ".png");
                            request.setVisibleInDownloadsUi(true);
                            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/Madhusudhan Creation/" + "/" + "Sample_" + i + ".png");
                            try {
                                refid = downloadManager.enqueue(request);
                                list.add(refid);
                                pathlist.add(Environment.getExternalStorageDirectory().toString() + "/Madhusudhan Creation/" + "/" + "Sample_" + i + ".png");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    } else {
                        Intent intent1 = new Intent();
                        intent1 = new Intent("android.intent.action.SEND");
                        intent1.setPackage("com.whatsapp");
                        intent1.setType("text/plain");
                        intent1.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        intent1.putExtra(Intent.EXTRA_TEXT, Descre);
                        try {
                            startActivityForResult(intent1, 2);
                        } catch (Exception e2s) {
                        }
                    }
                }

            }
            @Override
            public void onFailure(Call<Responce> call, Throwable t) {
                // Log error here since request failed
                Log.e(">>>>>>", t.toString());
                Toast.makeText(getActivity(), "Try..", Toast.LENGTH_SHORT).show();
            }
        });


    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getActivity().getIntent().removeExtra("android.intent.extra.STREAM");
        if (requestCode == 1) {
            rlShareImage.setBackgroundResource(R.drawable.round_share_completed);
            txtShareImage.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.whats_color));
            File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/Madhusudhan Creation");
            if (dir.isDirectory()) {
                String[] children = dir.list();
                for (int i = 0; i < children.length; i++) {
                    new File(dir, children[i]).delete();
                }
            }
            Toast.makeText(getActivity().getApplicationContext(), "Share Description", Toast.LENGTH_LONG).show();

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Do something after 100ms
                    Intent intent1 = new Intent();
                    intent1 = new Intent("android.intent.action.SEND");
                    intent1.setPackage("com.whatsapp");
                    intent1.setType("text/plain");
                    intent1.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent1.putExtra(Intent.EXTRA_TEXT, Descre);
                    try {
                        getActivity().startActivityForResult(intent1, 2);
                    } catch (Exception e2s) {
                    }

                }
            }, 2000);
            popupshare.setVisibility(View.GONE);
        } else if (requestCode == 2) {
            rlShareText.setBackgroundResource(R.drawable.round_share_completed);
            txtShareText.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.whats_color));
            popupshare.setVisibility(View.GONE);
        }
    }


    BroadcastReceiver onComplete = new BroadcastReceiver() {
        public void onReceive(Context ctxt, Intent intent) {
            long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            Log.e("IN", "" + referenceId);
            list.remove(referenceId);

            if (list.isEmpty()) {

                ArrayList<Uri> files_list = new ArrayList<Uri>();
                File files = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/Madhusudhan Creation");
                File[] F = files.listFiles();
                for (int i = 0; i < F.length; i++) {
                    String s = F[i].getName();
                    if (F[i].getName().contains("Sample_")) {
                        Uri uri1 = FileProvider.getUriForFile(getActivity().getApplicationContext(), BuildConfig.APPLICATION_ID + ".provider", F[i]);
                        files_list.add(uri1);
                    }

                }

                Intent intent1 = new Intent();
                intent1 = new Intent("android.intent.action.SEND");
                intent1.setPackage("com.whatsapp");
                intent1.setAction(Intent.ACTION_SEND_MULTIPLE);
                intent1.setType("image/jpeg");
                intent1.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent1.removeExtra("android.intent.extra.STREAM");
                intent1.putParcelableArrayListExtra("android.intent.extra.STREAM", files_list);
                try {
                    startActivityForResult(intent1, 1);
                } catch (Exception e2s) {
                }

            }

        }
    };

    @Override
    public void onResume() {
        getActivity().getIntent().removeExtra("android.intent.extra.STREAM");
        getActivity().registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        super.onResume();
    }

    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(onComplete);
        getActivity().unregisterReceiver(onComplete);
        super.onPause();
    }
}
