package com.SkyzoneGroup.MadhusudhanCreation.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.SkyzoneGroup.MadhusudhanCreation.Adapter.ViewPagerAdapter;
import com.SkyzoneGroup.MadhusudhanCreation.R;
import com.SkyzoneGroup.MadhusudhanCreation.Tab.LoginFragment;
import com.SkyzoneGroup.MadhusudhanCreation.Tab.SignupFragment;
import com.SkyzoneGroup.MadhusudhanCreation.util.IntentUtil;
import com.SkyzoneGroup.MadhusudhanCreation.util.PrefUtil;

public class LoginActivity extends AppCompatActivity {
    Context c;
    EditText mEtMobile, eEtEmail;
    LinearLayout lldone, ll_eEtEmail;
    Button btLogin;
    String Phone_No;
    String Email;
    TextView text_view_start;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    RelativeLayout  tab_view;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    LoginFragment loginFragment;
    SignupFragment signupFragment;
    private TextView info;
    private ImageView icon_cancel;
    private LoginButton loginButton;
    LinearLayout ll_emptybox;
    private PrefUtil prefUtil;
    private IntentUtil intentUtil;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SharedPreferences prefs = getSharedPreferences("myPref", MODE_PRIVATE);
        final Boolean Login = prefs.getBoolean("LOGIN", false);
        tab_view =(RelativeLayout)findViewById(R.id.tab_view);
        if (Login.booleanValue() == false) {
            bindnew();
        } else {

            text_view_start = (TextView) findViewById(R.id.text_view_start);
            text_view_start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    SharedPreferences settings = getSharedPreferences("myPref", MODE_PRIVATE);
                    settings.edit().clear().commit();

                    Intent intent = new Intent(LoginActivity.this, HomActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK |
                            Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                }
            });
            tab_view.setVisibility(View.GONE);
            ll_emptybox = (LinearLayout) findViewById(R.id.ll_emptybox);
            ll_emptybox.setVisibility(View.VISIBLE);
        }

//        FacebookSdk.sdkInitialize(getApplicationContext());
//        callbackManager = CallbackManager.Factory.create();
//
//
//        prefUtil = new PrefUtil(this);
//        intentUtil = new IntentUtil(this);
//
//        info = (TextView) findViewById(R.id.info);
//        profileImgView = (ImageView) findViewById(R.id.profile_img);
//        loginButton = (LoginButton) findViewById(R.id.login_button);
//        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                Profile profile = Profile.getCurrentProfile();
//              //  info.setText(message(profile));
//
//                String userId = loginResult.getAccessToken().getUserId();
//                String accessToken = loginResult.getAccessToken().getToken();
//
//                // save accessToken to SharedPreference
//                prefUtil.saveAccessToken(accessToken);
//
//                String profileImgUrl = "https://graph.facebook.com/" + userId + "/picture?type=large";
//
//
//                Glide.with(LoginActivity.this)
//                        .load(profileImgUrl)
//                        .into(profileImgView);
//            }
//
//            @Override
//            public void onCancel() {
//                info.setText("Login attempt cancelled.");
//            }
//
//            @Override
//            public void onError(FacebookException e) {
//                e.printStackTrace();
//                info.setText("Login attempt failed.");
//            }
//        });
    }

    private void bindnew() {
        icon_cancel = (ImageView) findViewById(R.id.icon_cancel);
        icon_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(2);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                viewPager.setCurrentItem(position, false);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        loginFragment = new LoginFragment();
        signupFragment = new SignupFragment();
//        signupFragment = new SignupFragment();
        adapter.addFragment(loginFragment, "Log In");
        adapter.addFragment(signupFragment, "Sign Up");
        viewPager.setAdapter(adapter);
    }


}
