package com.SkyzoneGroup.MadhusudhanCreation.Fragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.SkyzoneGroup.MadhusudhanCreation.Adapter.Catalog_CatAdapter;

import com.SkyzoneGroup.MadhusudhanCreation.Adapter.ExpandableListAdapter;
import com.SkyzoneGroup.MadhusudhanCreation.Adapter.ImageOfferPagerAdapter;
import com.SkyzoneGroup.MadhusudhanCreation.BuildConfig;
import com.SkyzoneGroup.MadhusudhanCreation.Model.SectionDataModel;
import com.SkyzoneGroup.MadhusudhanCreation.R;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.ApiClient;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.ApiInterface;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.Datum;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.Responce;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.parser.JSONParser;
import com.SkyzoneGroup.MadhusudhanCreation.util.AutoScrollViewPager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.facebook.GraphRequest.TAG;

public class MainFragment extends Fragment implements ExpandableListAdapter.Subcatlist, Catalog_CatAdapter.ClickShare {

    private static final String url = "https://madhusudhancreation.com/web_service.php?f=get_all_subcategory&cat_id=";
    public static int SUBCat;
    String descrepstion;
    static ApiInterface apiService;
    private static RecyclerView recyclerView, recyclerview;
    private Catalog_CatAdapter Adapter;
    List<Datum> movies;
    static ImageView imageView;
    static ImageView ivnotfound;
    List<Datum> users = new ArrayList<Datum>();
    List<Datum> items;
    NestedScrollView nestedScrollView;
    CircleIndicator indicator, Iofferzone, Isindicator;
    public static LinearLayout selectCat, ll_mainview;
    static ArrayList<Datum> listDataHeader;
    static ArrayList<SectionDataModel> allSampleData;
    AutoScrollViewPager viewpager;
    int min;
    static ProgressDialog pDialog;
    public static AlertDialog alertDialog1;
    public static ExpandableListView expListView;
    public static BottomSheetDialog dialog;
    Handler mHandler = new Handler();
    public static int[] banner = {
            R.drawable.img1,
            R.drawable.img2,
            R.drawable.img3

    };
    boolean onclikeFirst = false;
    static ExpandableListAdapter listAdapter;
    static ArrayList<Datum> list;
    TextView tvSubCategory;
    Activity activity;
    private List<Datum> studentList = new ArrayList<>();
    View view;
    //Share
    public static RelativeLayout popupshare;
    public static RelativeLayout rlShareImage, rlShareText, rlShareOther;
    public static TextView txtShareImage, txtShareText;
    private static final int REQUEST_PERMISSION = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    private static long refid;
    public static ArrayList<Long> listt = new ArrayList<>();
    static ArrayList<String> pathlist = new ArrayList<String>();
    private int mCurrentPage = 1;
    private int mItemPerRow = 20;
    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 5;
    int firstVisibleItem, visibleItemCount, totalItemCount;
    // on scroll
    private static int firstVisibleInListview;

    private static int current_page = 1;

    private int ival = 1;
    private int loadLimit = 5;
    LinearLayoutManager mLayoutManager;
    public MainFragment() {

    }


    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_main, container, false);
        ivnotfound = (ImageView) view.findViewById(R.id.iv_notfound);
        imageView = (ImageView) view.findViewById(R.id.image);
        recyclerview = (RecyclerView) view.findViewById(R.id.recyclerview);

        selectCat = (LinearLayout) view.findViewById(R.id.selectCat);
        expListView = (ExpandableListView) view.findViewById(R.id.lvExp);
        tvSubCategory = (TextView) view.findViewById(R.id.tvSubCategory);
        allSampleData = new ArrayList<SectionDataModel>();
        ll_mainview = (LinearLayout) view.findViewById(R.id.ll_mainview);


        ///////share//////////////
        popupshare = view.findViewById(R.id.popupshare);
        rlShareImage = view.findViewById(R.id.rlShareImage);
        rlShareText = view.findViewById(R.id.rlShareText);
        txtShareImage = view.findViewById(R.id.txtShareImage);
        txtShareText = view.findViewById(R.id.txtShareText);
        ///////share over //////////////
        if (Build.VERSION.SDK_INT >= 23) {
            // Check if we have write permission
            int permission2 = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int permission3 = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);

            if (permission2 != PackageManager.PERMISSION_GRANTED ||
                    permission3 != PackageManager.PERMISSION_GRANTED) {
                // We don't have permission so prompt the user
                ActivityCompat.requestPermissions(
                        getActivity(),
                        PERMISSIONS_STORAGE,
                        REQUEST_PERMISSION
                );
            }
        }
        nestedScrollView = (NestedScrollView) view.findViewById(R.id.nestedScrollView);
        // setting list adapter
        listAdapter = new ExpandableListAdapter(getActivity(), allSampleData);
        listAdapter.Regter(MainFragment.this);
        expListView.setAdapter(listAdapter);
        listDataHeader = new ArrayList<Datum>();
        selectCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                LayoutInflater li = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//                View promptsView1 = li.inflate(R.layout.filter_dialog_box, null);
//
//                AlertDialog.Builder alertDialogBuilder1 = new AlertDialog.Builder(getActivity());
//
//                // set prompts.xml to alertdialog builder
//                alertDialogBuilder1.setView(promptsView1);
//                alertDialogBuilder1.setCancelable(true);
//
//                alertDialog1 = alertDialogBuilder1.create();
//
//
//                TextView tvTital = (TextView) promptsView1.findViewById(R.id.tvtital);
//                expListView = (ExpandableListView) promptsView1.findViewById(R.id.lvExp);
//                listAdapter = new ExpandableListAdapter(getActivity(), allSampleData);
//                listAdapter.Regter(MainFragment.this);
//                expListView.setAdapter(listAdapter);
//                ImageView Ivcancle = (ImageView) promptsView1.findViewById(R.id.icon_cancel);
//                Ivcancle.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        alertDialog1.cancel();
//                    }
//                });
//                alertDialog1.show();
////   ((ViewGroup) alertDialog1.getWindow().getDecorView()).getChildAt(0).startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.shake));

                if (onclikeFirst) {
                    expListView.setVisibility(View.GONE);
                    onclikeFirst = false;
                } else {
                    expListView.setVisibility(View.VISIBLE);
                    onclikeFirst = true;
                }
            }
        });

        list = new ArrayList<>();
        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(imageView);
        Glide.with(this).load(R.raw.special).into(imageViewTarget);
        viewpager = (AutoScrollViewPager) view.findViewById(R.id.news_slider);
        indicator = (CircleIndicator) view.findViewById(R.id.indicator);

        viewpager.startAutoScroll();
        viewpager.setInterval(5000);
        viewpager.setCycle(true);
        viewpager.setStopScrollWhenTouch(true);

        apiService = ApiClient.getClient().create(ApiInterface.class);
        recyclerView = (RecyclerView) view.findViewById(R.id.my_List_view);
        movies = new ArrayList<>();
        Adapter = new Catalog_CatAdapter(movies, getActivity());

        // recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        items = new ArrayList<Datum>();
        recyclerView.setNestedScrollingEnabled(false);

        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerview.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        firstVisibleInListview = mLayoutManager.findFirstVisibleItemPosition();
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {



            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int currentFirstVisible = mLayoutManager.findFirstVisibleItemPosition();

                if(currentFirstVisible > firstVisibleInListview)
                   // loadMoreData(current_page);
                   Log.i("RecyclerView scrolled: ", "scroll up!");
                else
                    loadMoreData(current_page);
                   // Log.i("RecyclerView scrolled: ", "scroll down!");

                firstVisibleInListview = currentFirstVisible;
//                if (dy > 0) //check for scroll down
//                {
//                    visibleItemCount = mLayoutManager.getChildCount();
//                    totalItemCount = mLayoutManager.getItemCount();
//                    firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();
//
//                    if ((visibleItemCount + firstVisibleItem) >= totalItemCount) {
//                        loading = false;

//                        Log.v("...", " Reached Last Item");
////                          //  loadMoreVideos(searchVideos);
//                    }


               // }

            }
        });


        getAllCategory();

        if (SUBCat == 0) {
            loadData(current_page);
            //  catalogcategory();
        } else {

        }

        min = viewpager.getCurrentItem();

        fetchSlider();


        return view;
    }

    private void loadMoreData(final int current_page) {

        // I have not used current page for showing demo, if u use a webservice
        // then it is useful for every call request
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();

        Call<Responce> call = apiService.getallsubproduct();
        //   imageView.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<Responce>() {
            @Override
            public void onResponse(Call<Responce> call, Response<Responce> response) {
                items = response.body().getData();
               // pDialog.dismiss();
                if (response.body().getData().size() == 0) {
                    ivnotfound.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.GONE);
                    imageView.setVisibility(View.GONE);
                } else {
                    loadLimit = ival + 10;
                    for (int i = ival; i <= loadLimit; i++) {
                        studentList.add(response.body().getData().get(i));
                        Log.d("DATAsize", "SecondDATASIZE" + studentList.size());
                        ival++;
                    }
//                    for (int i = ival; i <= loadLimit; i++) {
//
//                        items.add();
//                        ival++;
//
//                    }
                    loadData(current_page);
                    imageView.setVisibility(View.GONE);
//                    recyclerview.setVisibility(View.GONE);
                    //    recyclerView.setVisibility(View.VISIBLE);
                    Adapter = new Catalog_CatAdapter(studentList, getActivity());
                    Adapter.RegisterClickShareClick(MainFragment.this);
                    Adapter.notifyDataSetChanged();
                    // set the adapter object to the Recyclerview
                    recyclerView.setAdapter(Adapter);


                }

            }

            @Override
            public void onFailure(Call<Responce> call, Throwable t) {
                // Log error here since request failed
                Log.e("", t.toString());
            //    pDialog.dismiss();
                imageView.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Try..", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void loadData(int current_page) {

        // I have not used current page for showing demo, if u use a webservice
        // then it is useful for every call request
        // I have not used current page for showing demo, if u use a webservice
        // then it is useful for every call request
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();

        Call<Responce> call = apiService.getallsubproduct();
        //   imageView.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<Responce>() {
            @Override
            public void onResponse(Call<Responce> call, Response<Responce> response) {
                items = response.body().getData();
                Log.d("DATAsize" ,"DATARESPONCESIZE" + items.size());
                pDialog.dismiss();
                if (response.body().getData().size() == 0) {
                    ivnotfound.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.GONE);
                    imageView.setVisibility(View.GONE);
                } else {
                    for (int i = ival; i <= loadLimit; i++) {


                        studentList.add(response.body().getData().get(i));
                        Log.d("DATAsize", "FirstDATASIZE" + studentList.size());
                        ival++;

                    }
//                    for (int i = ival; i <= loadLimit; i++) {
//
//                        items.add();
//                        ival++;
//
//                    }
//                    loadData(current_page);
                    imageView.setVisibility(View.GONE);
//                    recyclerview.setVisibility(View.GONE);
                    //    recyclerView.setVisibility(View.VISIBLE);
                    Adapter = new Catalog_CatAdapter(studentList, getActivity());
                    Adapter.RegisterClickShareClick(MainFragment.this);
                    Adapter.notifyDataSetChanged();
                    // set the adapter object to the Recyclerview
                    recyclerView.setAdapter(Adapter);


                }

            }

            @Override
            public void onFailure(Call<Responce> call, Throwable t) {
                // Log error here since request failed
                Log.e("", t.toString());
                pDialog.dismiss();
                imageView.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Try..", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void fetchSlider() {
        try {
            Call<Responce> call = apiService.getMianSlider();

            call.enqueue(new Callback<Responce>() {
                @Override
                public void onResponse(Call<Responce> call, Response<Responce> response) {
                    List<Datum> items = response.body().getData();
                    Log.d("", "Number of CAT By Image received: " + items.size());

                    if (response.body().getSuccess() == 1) {
                        ImageOfferPagerAdapter madapter = new ImageOfferPagerAdapter(getActivity(), items);
                        viewpager.setAdapter(madapter);
                        indicator.setViewPager(viewpager);
                    } else {

                    }

                }

                @Override
                public void onFailure(Call<Responce> call, Throwable t) {
                    // Log error here since request failed
                    Log.e("", t.toString());
                    Toast.makeText(getActivity(), "Try Again", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception ex) {
        }

    }

//    private void catalogcategory() {
//        pDialog = new ProgressDialog(getActivity());
//        pDialog.setMessage("Please wait...");
//        pDialog.setIndeterminate(false);
//        pDialog.setCancelable(true);
//        pDialog.show();
//
//        Call<Responce> call = apiService.getallsubproduct();
//        //   imageView.setVisibility(View.VISIBLE);
//        call.enqueue(new Callback<Responce>() {
//            @Override
//            public void onResponse(Call<Responce> call, Response<Responce> response) {
//                items = response.body().getData();
//                pDialog.dismiss();
//                if (response.body().getData().size() == 0) {
//                    ivnotfound.setVisibility(View.VISIBLE);
//                    recyclerView.setVisibility(View.GONE);
//                    recyclerView.setVisibility(View.GONE);
//                    imageView.setVisibility(View.GONE);
//                } else {
////                    for (int i = ival; i <= loadLimit; i++) {
////
////                        items.add();
////                        ival++;
////
////                    }
////                    loadData(current_page);
//                    imageView.setVisibility(View.GONE);
////                    recyclerview.setVisibility(View.GONE);
//                    //    recyclerView.setVisibility(View.VISIBLE);
//                    Adapter = new Catalog_CatAdapter(items, getActivity());
//                    Adapter.RegisterClickShareClick(MainFragment.this);
//
//                    // set the adapter object to the Recyclerview
//                    recyclerView.setAdapter(Adapter);
//
//
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<Responce> call, Throwable t) {
//                // Log error here since request failed
//                Log.e("", t.toString());
//                pDialog.dismiss();
//                imageView.setVisibility(View.GONE);
//                Toast.makeText(getActivity(), "Try..", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //  Collections.shuffle(os_versions);
            try {
                if (min < banner.length) {
                    min++;
                    viewpager.setCurrentItem(min, true);
                } else {
                    min = 0;
                    viewpager.setCurrentItem(min, true);
                }
                mHandler.postDelayed(this, 3000);
            } catch (Exception ex) {
            }

        }
    };


    private void getAllCategory() {
        Call<Responce> call = apiService.getAllCategory();
        call.enqueue(new Callback<Responce>() {
            @Override
            public void onResponse(Call<Responce> call, Response<Responce> response) {

                try {
                    listDataHeader = (ArrayList<Datum>) response.body().getData();
                    Log.d(TAG, " >> Number of Category received: " + listDataHeader.size());


                    for (int i = 0; i < listDataHeader.size(); i++) {
                        new GetDataTask(listDataHeader.get(i).getCatId(), listDataHeader.get(i).getCatName()).execute();
                    }


                } catch (Exception ex) {
                }

            }

            @Override
            public void onFailure(Call<Responce> call, Throwable t) {

                Log.e(TAG, t.toString());
            }
        });
    }


    @Override
    public void passcatid(String SUB_Cat, String catid) {
        tvSubCategory.setText(catid);
        //    Toast.makeText(getActivity(), SUB_Cat, Toast.LENGTH_SHORT).show();
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
        Call<Responce> call = apiService.GetcatProduct(SUB_Cat);
        call.enqueue(new Callback<Responce>() {
            @Override
            public void onResponse(Call<Responce> call, Response<Responce> response) {
                items = response.body().getData();
                pDialog.dismiss();
                if (response.body().getSuccess() == 0) {
                    ivnotfound.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    //    Catalog_CatAdapter = new Catalog_CatAdapter(items,getActivity());
                    Adapter = new Catalog_CatAdapter(items, getActivity());
                    Adapter.RegisterClickShareClick(MainFragment.this);
                    recyclerView.setAdapter(Adapter);
                    Adapter.notifyDataSetChanged();

                    nestedScrollView.post(new Runnable() {
                        @Override
                        public void run() {
                            nestedScrollView.fullScroll(NestedScrollView.FOCUS_UP);
                            recyclerView.scrollToPosition(items.size() - 1);
                            nestedScrollView.scrollTo(0, 0);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Responce> call, Throwable t) {
                pDialog.dismiss();
                Log.e(TAG, t.toString());
            }
        });


    }

    public void sharedata(String SUB_Cat) {
        Call<Responce> call = apiService.GetcatShareProduct(SUB_Cat);
        call.enqueue(new Callback<Responce>() {
            @Override
            public void onResponse(Call<Responce> call, Response<Responce> response) {
                List<Datum> items = response.body().getData();
                List<String> imagurl = response.body().getImages();
                if (response.body().getSuccess() == 0) {

                } else {
                    popupshare.setVisibility(View.VISIBLE);
                    rlShareImage.setBackgroundResource(R.drawable.round_share);
                    txtShareImage.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.drak_grey));
                    rlShareText.setBackgroundResource(R.drawable.round_share);
                    txtShareText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.drak_grey));
                    Spanned spanned = Html.fromHtml(response.body().getDescription());
                    descrepstion = spanned.toString();
//                            ArrayList<String> dataurl = new ArrayList<>();
//
//                            for (int i = 0; i < imagurl.size(); i++) {
//                                dataurl.add(imagurl.get(i).get());
//                            }

                    if (imagurl.size() > 0) {

                        DownloadManager downloadManager = (DownloadManager) getApplicationContext().getSystemService(Context.DOWNLOAD_SERVICE);
                        for (int i = 0; i < imagurl.size(); i++) {
                            Uri Download_Uri = Uri.parse(imagurl.get(i));
                            DownloadManager.Request request = new DownloadManager.Request(Download_Uri);
                            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                            request.setAllowedOverRoaming(false);
                            request.setTitle("Images Downloading " + "Sample_" + i + ".png");
                            request.setDescription("Downloading " + "Sample_" + i + ".png");
                            request.setVisibleInDownloadsUi(true);
                            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/Madhusudhan Creation/" + "/" + "Sample_" + i + ".png");
                            try {
                                refid = downloadManager.enqueue(request);
                                listt.add(refid);
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
                        intent1.putExtra(Intent.EXTRA_TEXT, descrepstion);
                        try {
                            getActivity().startActivityForResult(intent1, 2);
                        } catch (Exception e2s) {
                        }
                    }
                }


            }

            @Override
            public void onFailure(Call<Responce> call, Throwable t) {
                pDialog.dismiss();
                Log.e(TAG, t.toString());
            }
        });

    }

    @Override
    public void ClickOnShare(int position) {
        sharedata(items.get(position).getCatId());
    }


    static class GetDataTask extends AsyncTask<Void, Void, Void> {

        //    ProgressDialog pDialog;
        String CAT_ID, NAME;
        ArrayList<Datum> singleItem = new ArrayList<Datum>();

        public GetDataTask(String cat_id, String catName) {
            this.CAT_ID = cat_id;
            this.NAME = catName;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Nullable
        @Override
        protected Void doInBackground(Void... params) {

            /**
             * Getting JSON Object from Web Using okHttp
             */
            JSONObject jsonObject = JSONParser.getDataFromWeb(CAT_ID);

            try {
                /**
                 * Check Whether Its NULL???
                 */
                if (jsonObject != null) {
                    /**
                     * Check Length...
                     */
                    if (jsonObject.length() > 0) {
                        /**
                         * Getting Array named "contacts" From MAIN Json Object
                         */
                        JSONArray array = jsonObject.getJSONArray("data");

                        /**
                         * Check Length of Array...
                         */
                        int lenArray = array.length();
                        if (lenArray > 0) {
                            SectionDataModel dm = new SectionDataModel();

                            dm.setHeaderTitle(NAME);
                            dm.setHeaderId(CAT_ID);

                            for (int jIndex = 0; jIndex < lenArray; jIndex++) {

                                /**
                                 * Creating Every time New Object
                                 * and
                                 * Adding into List
                                 */
                                Datum model = new Datum();
                                /**
                                 * Getting Inner Object from Info array...
                                 * and
                                 * From that We will get Name of that Contact
                                 *
                                 */
                                JSONObject innerObject = array.getJSONObject(jIndex);

                                String cat_id = innerObject.getString("cat_id");
                                String cat_name = innerObject.getString("cat_name");


                                model.setCatId(cat_id);
                                model.setCatName(cat_name);

                                /**
                                 * Adding name and phone concatenation in List...
                                 */
                                singleItem.add(new Datum(cat_name, cat_id));
                                list.add(model);
                                Collections.shuffle(singleItem);

                                dm.setAllItemsInSection(singleItem);

                            }


                            allSampleData.add(dm);


                        }
                    }
                } else {

                }
            } catch (JSONException je) {
                Log.i(JSONParser.TAG, "" + je.getLocalizedMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }


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
                    intent1.putExtra(Intent.EXTRA_TEXT, descrepstion);
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
            listt.remove(referenceId);

            if (listt.isEmpty()) {

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

    public void onStart() {

        super.onStart();
        //    Log.d("lifecycle","onStart invoked");
    }
}
