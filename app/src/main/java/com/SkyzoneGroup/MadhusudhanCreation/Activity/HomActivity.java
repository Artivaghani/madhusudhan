package com.SkyzoneGroup.MadhusudhanCreation.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.Fragment;

import com.SkyzoneGroup.MadhusudhanCreation.Notification.Config;
import com.crashlytics.android.Crashlytics;
import com.SkyzoneGroup.MadhusudhanCreation.Fragment.Dailog_WishListFragment;
import com.SkyzoneGroup.MadhusudhanCreation.Fragment.OfferFragment;
import com.SkyzoneGroup.MadhusudhanCreation.Fragment.SearchFragment;
import com.SkyzoneGroup.MadhusudhanCreation.Fragment.SecondFragment;
import com.SkyzoneGroup.MadhusudhanCreation.Model.BottomNavigationViewHelper;
import com.SkyzoneGroup.MadhusudhanCreation.Model.CartList;
import com.SkyzoneGroup.MadhusudhanCreation.R;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.ApiClient;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.ApiInterface;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.Datum;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.Responce;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.ResponceCartItem;
import com.onesignal.OSNotificationAction;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;

import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.List;

import io.fabric.sdk.android.Fabric;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomActivity extends AppCompatActivity {

    private static final int REQUEST_WRITE_PERMISSION = 786;
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;

    FloatingActionButton floatingActionButton;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    Toolbar mytoolbar;
    Boolean doubleBackToExitPressedOnce = false;
    BottomNavigationView bottomNavigationViewEx;
    Fragment fragment;
    FrameLayout containerView, fl_nointernet;
    private Typeface typeace;
    TextView textCartItemCount;
    int mCartItemCount = 0;
    ApiInterface apiService;
    String USER_ID;
    String USER_NAME;
    String USER_EMAIL;
    LinearLayout ll_main;
    LinearLayout ll_loginbutton;
    Boolean Login;
    View badge;
    ImageButton count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());

        setContentView(R.layout.activity_hom);
//        typeace = Typeface.createFromAsset(getAssets(), "fonts/DINNextLTPro-Regular.otf");
        mytoolbar = (Toolbar) findViewById(R.id.mytoolbar);
        mytoolbar.setTitle("Madhusudhan Creation");
        // TODO: Move this to where you establish a user session
        logUser();

        SharedPreferences prefs = getSharedPreferences("myPref", MODE_PRIVATE);

        Login = prefs.getBoolean("LOGIN", false);
        USER_ID = prefs.getString("USER_ID", "0");
        USER_NAME = prefs.getString("USER_NAME", null);
        USER_EMAIL = prefs.getString("USER_EMAIL", null);
        apiService = ApiClient.getClient().create(ApiInterface.class);

        containerView = (FrameLayout) findViewById(R.id.containerView);
        fl_nointernet = (FrameLayout) findViewById(R.id.fl_nointernet);

        fatchCartItem(USER_ID);
        bindview();

        OneSignal.startInit(this).setNotificationOpenedHandler(new ExampleNotificationOpenedHandler()).init();
        OneSignal.enableSound(true);
        requestPermission();

//        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
//        floatingActionButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                PackageManager packageManager = HomActivity.this.getPackageManager();
//                Intent i = new Intent(Intent.ACTION_VIEW);
//
//                try {
//                    String url = "https://api.whatsapp.com/send?phone=" + "917359332535" + "&text=" + URLEncoder.encode("HELLO,1ST-CHOICE", "UTF-8");
//                    //   String url =" https://api.whatsapp.com/send?phone=919824622268&text=HELLO,1ST-CHOICE";
//
//                    i.setPackage("com.whatsapp");
//                    i.setData(Uri.parse(url));
//                    if (i.resolveActivity(packageManager) != null) {
//                        HomActivity.this.startActivity(i);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        final BottomSheetDialog dialog = new BottomSheetDialog(HomActivity.this);
//        View view = getLayoutInflater().inflate(R.layout.log_in_dialog_box, null);
//        dialog.setContentView(view);
//        dialog.setCancelable(true);
//        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        dialog.getWindow().setGravity(Gravity.BOTTOM);
//        LinearLayout login = (LinearLayout) view.findViewById(R.id.ll_login);
//
//        TextView tvTital = (TextView) view.findViewById(R.id.tvtital);
//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(HomActivity.this, LoginActivity.class);
//                startActivity(intent);
//            }
//        });
//        ImageView Ivcancle = (ImageView) view.findViewById(R.id.Ivcancle);
//        Ivcancle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.cancel();
//            }
//        });
//        String first = "Welcome To ";
//        String next = "<font color='#DD0629'>Madhusudhan Creation</font>";
//        tvTital.setText(Html.fromHtml(first + next));
//
//        if (isNetworkStatusAvialable(getApplicationContext())) {
//            if (Login == true) {
//                dialog.cancel();
//            } else {
//                dialog.show();
//            }
//            //  Toast.makeText(getApplicationContext(), "internet avialable", Toast.LENGTH_SHORT).show();
//        }
    }

    private void blink() {
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                int timeToBlink = 400;    //in milissegunds
                try{Thread.sleep(timeToBlink);}catch (Exception e) {}
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        // whatsapp = (TextView) findViewById(R.id.whatsapp);
                        if(count.getVisibility() == View.VISIBLE){
                            count.setVisibility(View.INVISIBLE);
                        }else{
                            count.setVisibility(View.VISIBLE);
                        }
                        blink();
                    }
                });
            }
        }).start();
    }

    public void forceCrash(View view) {
        throw new RuntimeException("This is a crash");
    }

    protected void onStart() {
        super.onStart();
//        fatchCartItem(USER_ID);
//        mytoolbar.setTitle("Offers");
//        fragment = new OfferFragment();
//        loadFragment(fragment);

//        mFragmentManager = getSupportFragmentManager();
//        mFragmentTransaction = mFragmentManager.beginTransaction();
//        mFragmentTransaction.replace(R.id.containerView, new OfferFragment()).commit();
//        bottomNavigationViewEx.getMenu().findItem(R.id.nav_Offer).setChecked(true);
//
//         this.onCreate();

    }
    private void logUser() {
        // TODO: Use the current user's information
        // You can call any combination of these three methods
        Crashlytics.setUserIdentifier("12345");
        Crashlytics.setUserEmail("user@fabric.io");
        Crashlytics.setUserName("Test User");
    }

    private void onCreate() {
        fatchCartItem(USER_ID);
    }

    private void fatchCartItem(String user_id) {

        Call<ResponceCartItem> call = apiService.getCartDetail(user_id);
        call.enqueue(new Callback<ResponceCartItem>() {

            @Override
            public void onResponse(Call<ResponceCartItem> call, Response<ResponceCartItem> response) {
            try {
                List<CartList> items = response.body().getData();
                if (response.body().getSuccess() == 0) {

                    try {
                        mCartItemCount = 0;
                        setupBadge(mCartItemCount);
                    } catch (Exception ex) {
                    }
                } else {
                    try {
                        mCartItemCount = items.size();
                        setupBadge(mCartItemCount);
                    } catch (Exception ex) {
                    }
                }
//                try {
//                    Log.d(">>> ", "Number of Cart Item received: " + items.size());
//                    mCartItemCount = items.size();
//                    setupBadge(mCartItemCount);
//                } catch (Exception ex) {
//                }
            }catch (Exception e){}

            }

            @Override
            public void onFailure(Call<ResponceCartItem> call, Throwable t) {
                Log.e(">> ", t.toString());
            }
        });
    }

    private void requestPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
        } else {
            //  openFilePicker();
        }
    }

    private void setupBadge(int mCartItemCount) {

        if (textCartItemCount != null) {
            if (this.mCartItemCount == 0) {
                if (textCartItemCount.getVisibility() != View.GONE) {
                    textCartItemCount.setVisibility(View.GONE);
                    badge.setVisibility(View.GONE);
                }
            } else {
                textCartItemCount.setText(String.valueOf(Math.min(this.mCartItemCount, mCartItemCount)));
                if (textCartItemCount.getVisibility() != View.VISIBLE) {
                    textCartItemCount.setVisibility(View.VISIBLE);
                    badge.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    private void bindview() {

//        fatchCartItem(USER_ID);
        setSupportActionBar(mytoolbar);

        bottomNavigationViewEx = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationViewEx);
        BottomNavigationMenuView bottomNavigationMenuView = (BottomNavigationMenuView) bottomNavigationViewEx.getChildAt(0);
//        View v = bottomNavigationMenuView.getChildAt(3);
        //   bottomNavigationMenuView.setSelectedItemId(R.id.item_id);
//        BottomNavigationItemView itemView = (BottomNavigationItemView) v;
//        View badge = LayoutInflater.from(this).inflate(R.layout.cart_action_view, bottomNavigationMenuView, false);
//        textCartItemCount = badge.findViewById(R.id.cart_count);
//        itemView.addView(badge);
        // bottomNavigationViewEx.getMenu().getItem(0).setChecked(false);
        bottomNavigationViewEx.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_help:
                        showSoftwareKeyboard(false);
                        PackageManager packageManager = HomActivity.this.getPackageManager();
                        Intent i = new Intent(Intent.ACTION_VIEW);

                        try {
                            String url = "https://api.whatsapp.com/send?phone=" + "919106049495" + "&text=" + URLEncoder.encode("HELLO, MADHUSUDHAN CREATION...", "UTF-8");
                            //   String url =" https://api.whatsapp.com/send?phone=919824622268&text=HELLO,1ST-CHOICE";

                            i.setPackage("com.whatsapp");
                            i.setData(Uri.parse(url));
                            if (i.resolveActivity(packageManager) != null) {
                                HomActivity.this.startActivity(i);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
//                    case R.id.nav_home:
//                        if (isNetworkStatusAvialable(getApplicationContext())) {
//                            mytoolbar.setTitle("1st Choice");
//                            fragment = new MainFragment();
//                            loadFragment(fragment);
//                            doubleBackToExitPressedOnce = false;
//                            //  Toast.makeText(getApplicationContext(), "internet avialable", Toast.LENGTH_SHORT).show();
//                        } else {
//
//                            Toast.makeText(getApplicationContext(), "internet is not avialable", Toast.LENGTH_SHORT).show();
//
//                        }
//
//
//                        break;
                    case R.id.nav_Shop:
                        if (isNetworkStatusAvialable(getApplicationContext())) {
                            containerView.setVisibility(View.VISIBLE);
                            fl_nointernet.setVisibility(View.GONE);
                            showSoftwareKeyboard(false);
                            mytoolbar.setTitle("Collections");
                            fragment = new SecondFragment();
                            loadFragment(fragment);
                            doubleBackToExitPressedOnce = false;
                            //  Toast.makeText(getApplicationContext(), "internet avialable", Toast.LENGTH_SHORT).show();
                        } else {

                            Toast.makeText(getApplicationContext(), "internet is not avialable", Toast.LENGTH_SHORT).show();

                        }

//                        Intent intent = new Intent(HomActivity.this, BraProListActivity.class);
//                        startActivity(intent);
                        break;
                    case R.id.nav_search:
                        if (isNetworkStatusAvialable(getApplicationContext())) {
                            containerView.setVisibility(View.VISIBLE);
                            fl_nointernet.setVisibility(View.GONE);
                            mytoolbar.setTitle("Madhusudhan Creation");
                            fragment = new SearchFragment();
                            loadFragment(fragment);
                            doubleBackToExitPressedOnce = false;
                            // Toast.makeText(HomActivity.this, "search", Toast.LENGTH_SHORT).show();

                            //  Toast.makeText(getApplicationContext(), "internet avialable", Toast.LENGTH_SHORT).show();
                        } else {

                            Toast.makeText(getApplicationContext(), "internet is not avialable", Toast.LENGTH_SHORT).show();

                        }
                        break;
                    case R.id.nav_Offer:
                        if (isNetworkStatusAvialable(getApplicationContext())) {
                            containerView.setVisibility(View.VISIBLE);
                            fl_nointernet.setVisibility(View.GONE);
                            showSoftwareKeyboard(false);
                            mytoolbar.setTitle(R.string.app_name);
                            fragment = new OfferFragment();
                            loadFragment(fragment);
                            doubleBackToExitPressedOnce = false;
                        } else {

                            Toast.makeText(getApplicationContext(), "internet is not avialable", Toast.LENGTH_SHORT).show();

                        }

//                        Intent Offer = new Intent(HomActivity.this, OfferActivity.class);
//                        startActivity(Offer);
//                        Toast.makeText(HomActivity.this, "Offer", Toast.LENGTH_SHORT).show();
                        break;
//                    case R.id.action_cart:
//                        if (isNetworkStatusAvialable(getApplicationContext())) {
//                            Intent cartintet = new Intent(HomActivity.this, CartActivity.class);
//                            cartintet.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            startActivity(cartintet);
//                            // finish();
//                            doubleBackToExitPressedOnce = false;
//                            //  Toast.makeText(getApplicationContext(), "internet avialable", Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(getApplicationContext(), "internet is not avialable", Toast.LENGTH_SHORT).show();
//                        }
//
//                        break;

                    default:
                        return false;
                }


                return true;
            }
        });

//        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigationViewEx.getLayoutParams();
//        layoutParams.setBehavior(new BottomNavigationBehavior());


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.navigationview);

        if (isNetworkStatusAvialable(getApplicationContext())) {
            containerView.setVisibility(View.VISIBLE);
            fl_nointernet.setVisibility(View.GONE);
            mFragmentManager = getSupportFragmentManager();
            mFragmentTransaction = mFragmentManager.beginTransaction();
            mFragmentTransaction.replace(R.id.containerView, new OfferFragment()).commit();

            //  Toast.makeText(getApplicationContext(), "internet avialable", Toast.LENGTH_SHORT).show();
        } else {
            containerView.setVisibility(View.GONE);
            fl_nointernet.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(), "internet is not avialable", Toast.LENGTH_SHORT).show();

        }


        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        mDrawerLayout.closeDrawers();
                    }
                }, 200);

                if (menuItem.getItemId() == R.id.nav_Home) {
//                    fragment = new MainFragment();
//                    loadFragment(fragment);
                    if (isNetworkStatusAvialable(getApplicationContext())) {
                        bottomNavigationViewEx.getMenu().findItem(R.id.nav_Offer).setChecked(true);
                        mytoolbar.setTitle(R.string.app_name);
                        fragment = new OfferFragment();
                        loadFragment(fragment);
                    } else {

                        Toast.makeText(getApplicationContext(), "internet is not avialable", Toast.LENGTH_SHORT).show();

                    }

                }

                if (menuItem.getItemId() == R.id.nav_MyAccount) {

//                    if (Login.booleanValue() == false) {
//                        Toast.makeText(HomActivity.this, "User Is Not Login..", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Intent intent = new Intent(HomActivity.this, MyAccountActivity.class);
//                        startActivity(intent);
//                        finish();
//                    }

                }

                if (menuItem.getItemId() == R.id.nav_Mywishlist) {

//                    if (Login.booleanValue() == false) {
//                        Toast.makeText(HomActivity.this, "User Is Not Login..", Toast.LENGTH_SHORT).show();
//                    } else {
//                        BottomSheetDialogFragment bottomSheetDialogFragment = new Dailog_WishListFragment();
//                        bottomSheetDialogFragment.show(getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
//                    }

                }

                if (menuItem.getItemId() == R.id.nav_Shopbycat) {
                    if (isNetworkStatusAvialable(getApplicationContext())) {
                        bottomNavigationViewEx.getMenu().findItem(R.id.nav_Shop).setChecked(true);
                        mytoolbar.setTitle("Collections");
                        fragment = new SecondFragment();
                        loadFragment(fragment);

                        //  Toast.makeText(getApplicationContext(), "internet avialable", Toast.LENGTH_SHORT).show();
                    } else {

                        Toast.makeText(getApplicationContext(), "internet is not avialable", Toast.LENGTH_SHORT).show();

                    }


                }

                if (menuItem.getItemId() == R.id.nav_FAQs) {

                    Intent intent = new Intent(HomActivity.this, WebviewActivity.class);
                    intent.putExtra("TITLE", "FAQ");
                    intent.putExtra("ID", 1);
                    startActivity(intent);
                }

                if (menuItem.getItemId() == R.id.nav_About) {
                    Intent intent = new Intent(HomActivity.this, WebviewActivity.class);
                    intent.putExtra("TITLE", "About Us");
                    intent.putExtra("ID", 2);
                    startActivity(intent);
                }
                if (menuItem.getItemId() == R.id.nav_call) {
                    Intent intent = new Intent(HomActivity.this, WebviewActivity.class);
                    intent.putExtra("TITLE", "Call Us");
                    intent.putExtra("ID", 3);
                    startActivity(intent);
                }
                if (menuItem.getItemId() == R.id.nav_PrivacyPolicy) {
                    Intent intent = new Intent(HomActivity.this, WebviewActivity.class);
                    intent.putExtra("TITLE", "Privacy Policy");
                    intent.putExtra("ID", 4);
                    startActivity(intent);
                }
                if (menuItem.getItemId() == R.id.nav_ReturnsPolicy) {
                    Intent intent = new Intent(HomActivity.this, WebviewActivity.class);
                    intent.putExtra("TITLE", "Return Policy");
                    intent.putExtra("ID", 5);
                    startActivity(intent);
                }
                if (menuItem.getItemId() == R.id.nav_bank) {
                    Intent intent = new Intent(HomActivity.this, BankInfoActivity.class);
                    intent.putExtra("TITLE", "Bank Informastion");
                    startActivity(intent);
                }
                if (menuItem.getItemId() == R.id.nav_ShippingPolicy) {
                    Intent intent = new Intent(HomActivity.this, WebviewActivity.class);
                    intent.putExtra("TITLE", "Shipping Policy");
                    intent.putExtra("ID", 6);
                    startActivity(intent);
                }
                if (menuItem.getItemId() == R.id.nav_TermsConditions) {
                    Intent intent = new Intent(HomActivity.this, WebviewActivity.class);
                    intent.putExtra("TITLE", "Terms Conditions");
                    intent.putExtra("ID", 7);
                    startActivity(intent);
                }

                if (menuItem.getItemId() == R.id.nav_offer) {
//                    if (isNetworkStatusAvialable(getApplicationContext())) {
//                        bottomNavigationViewEx.getMenu().findItem(R.id.nav_Offer).setChecked(true);
//                        showSoftwareKeyboard(false);
//                        mytoolbar.setTitle("Offer For You");
//                        fragment = new OfferFragment();
//                        loadFragment(fragment);
//                        //  Toast.makeText(getApplicationContext(), "internet avialable", Toast.LENGTH_SHORT).show();
//                    } else {
//
//                        Toast.makeText(getApplicationContext(), "internet is not avialable", Toast.LENGTH_SHORT).show();
//
//                    }


                }


                return false;
            }

        });
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mytoolbar, R.string.app_name, R.string.app_name);

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();
        View headerLayout = mNavigationView.inflateHeaderView(R.layout.nav_header_main);

//        ll_main = (LinearLayout) headerLayout.findViewById(R.id.ll_main);
//        ll_loginbutton = (LinearLayout) headerLayout.findViewById(R.id.ll_loginbutton);
//        final TextView tvName = (TextView) headerLayout.findViewById(R.id.tvName);
//        final TextView tvEmail = (TextView) headerLayout.findViewById(R.id.tvEmail);
//        final TextView tvSymbol = (TextView) headerLayout.findViewById(R.id.tvSymbol);
//        Button btnMyAccount = (Button) headerLayout.findViewById(R.id.btnMyAccount);

//        btnMyAccount.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (isNetworkStatusAvialable(getApplicationContext())) {
//                    Intent intent = new Intent(HomActivity.this, LoginActivity.class);
//                    startActivity(intent);
//
//                    //  Toast.makeText(getApplicationContext(), "internet avialable", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(getApplicationContext(), "internet is not avialable", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });

//        if (Login.booleanValue() == false) {
//            Toast.makeText(HomActivity.this, "User Is Not Login..", Toast.LENGTH_SHORT).show();
//        } else {
//            Call<Responce> call = apiService.get_customer_detail(USER_ID);
//            call.enqueue(new Callback<Responce>() {
//                @Override
//                public void onResponse(Call<Responce> call, Response<Responce> response) {
//                    List<Datum> items = response.body().getData();
//                    if (response.body().getSuccess() == 0) {
//                        //   Toast.makeText(HomActivity.this, "User Not Login", Toast.LENGTH_SHORT).show();
//                    } else {
//                        tvName.setText(items.get(0).getFirstname() + " " + items.get(0).getLastname());
//                        tvEmail.setText(items.get(0).getEmail());
//
//                        try {
//                            char first = items.get(0).getFirstname().charAt(0);
//                            char last = items.get(0).getLastname().charAt(0);
//                            tvSymbol.setText("" + first + last);
//                        } catch (Exception ex) {
//                            ex.printStackTrace();
//                        }
//
//                        //   tvSymbol.setText("" + first + last);
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<Responce> call, Throwable t) {
//                    Log.e(">> ", t.toString());
//                }
//            });
//        }
    }

    private boolean isNetworkStatusAvialable(Context applicationContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfos = connectivityManager.getActiveNetworkInfo();
            if (netInfos != null)
                if (netInfos.isConnected())
                    return true;
        }
        return false;
    }


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();

            System.exit(0);
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        final MenuItem item = menu.findItem((R.id.noti));
        count = (ImageButton) menu.findItem(R.id.noti).getActionView();
        count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:9106049495"));
                startActivity(intent);
            }
        });
//        blink();
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.noti:
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:9924799905"));
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(menuItem);
        }

    }


    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.containerView, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }



    public void onRestart() {
//        bottomNavigationViewEx.getMenu().findItem(R.id.nav_Shop).setChecked(true);
//        mytoolbar.setTitle("Collections");
//        fragment = new SecondFragment();
//        loadFragment(fragment);
        super.onRestart();

    }
    protected void showSoftwareKeyboard(boolean showKeyboard){
        final Activity activity = HomActivity.this;
        final InputMethodManager inputManager = (InputMethodManager)activity.getSystemService( HomActivity.this.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), showKeyboard ? InputMethodManager.SHOW_FORCED : InputMethodManager.HIDE_NOT_ALWAYS);
    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK) {
////            fatchCartItem(USER_ID);
//         //   bindview();
//            Intent refresh = new Intent(this, HomActivity.class); //inboxlist is activity which list the read and unread messages
//            startActivity(refresh);
//            this.finish();
//        }
//    }
public class ExampleNotificationOpenedHandler implements OneSignal.NotificationOpenedHandler {

    public void notificationOpened(String message, JSONObject additionalData, boolean isActive) {
        try {
            if (additionalData != null) {
                if (additionalData.has("actionSelected"))
                    Log.d("OneSignalExample", "OneSignal notification button with id " + additionalData.getString("actionSelected") + " pressed");

                Log.d("OneSignalExample", "Full additionalData:\n" + additionalData.toString());
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    @Override
    public void notificationOpened(OSNotificationOpenResult result) {
        OSNotificationAction.ActionType actionType = result.action.type;
        JSONObject data = result.notification.payload.additionalData;
        String customKey;

        if (data != null) {
            customKey = data.optString("customkey", null);
            if (customKey != null)
                Log.i("OneSignalExample", "customkey set with value: " + customKey);
        }

        if (actionType == OSNotificationAction.ActionType.ActionTaken)
            Log.i("OneSignalExample", "Button pressed with id: " + result.action.actionID);


    }
}

}
