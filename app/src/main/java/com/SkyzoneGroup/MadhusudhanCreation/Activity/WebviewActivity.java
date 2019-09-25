package com.SkyzoneGroup.MadhusudhanCreation.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.SkyzoneGroup.MadhusudhanCreation.R;

public class WebviewActivity extends AppCompatActivity {
    WebView webView;
    RelativeLayout rl_main;
    int REQUEST_CALL_PHONE = 15 ;
    LinearLayout ll_call,ll_gmail;
    public static String app_name = "Madhusudhan Creation";
    public static String acc_link = "https://play.google.com/store/apps/details?id=com.SkyzoneGroup.MadhusudhanCreation";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        String name = getIntent().getStringExtra("TITLE");
        int id = getIntent().getIntExtra("ID", 0);
        actionBar.setTitle(name);
        int checkPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
        if (checkPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    REQUEST_CALL_PHONE);
        } else {
         //   customDialog(CallChoosyActivity.this);
        }
        rl_main = (RelativeLayout) findViewById(R.id.rl_main);
        ll_gmail =(LinearLayout) findViewById(R.id.ll_gmail);
        ll_gmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(
                        Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("plain/text");
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                        new String[] { "madhusudhancreation1015@gmail.com" });
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                        "Any Query For Shopping In Madhusudhan Creation");

                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,
                        "Thank You");
                startActivity(Intent.createChooser(emailIntent, "Send Email"));
            }
        });
        ll_call = (LinearLayout) findViewById(R.id.ll_call);
        ll_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:9106049495"));
                if (ContextCompat.checkSelfPermission(WebviewActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    int REQUEST_PHONE_CALL = 5;
                    ActivityCompat.requestPermissions(WebviewActivity.this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_PHONE_CALL);
                }
                else
                {
                    startActivity(intent);
                }
            }
        });
        webView = (WebView) findViewById(R.id.mwebview);
        if (id == 1) {

            webView.loadUrl("file:///android_asset/faq.html");
            webView.setBackgroundColor(Color.TRANSPARENT);
        }
        if (id == 2) {
            webView.loadUrl("file:///android_asset/about_us.html");
            webView.setBackgroundColor(Color.TRANSPARENT);
        }
        if (id == 3) {
            rl_main.setVisibility(View.VISIBLE);
            webView.setVisibility(View.GONE);
        }
        if (id == 4) {
            webView.loadUrl("file:///android_asset/privacypolicy.html");
            webView.setBackgroundColor(Color.TRANSPARENT);
        }
        if (id == 5) {
            webView.loadUrl("file:///android_asset/returnspolicy.html");
            webView.setBackgroundColor(Color.TRANSPARENT);
        }
        if (id == 6) {
            webView.loadUrl("file:///android_asset/shipping_policy.html");
            webView.setBackgroundColor(Color.TRANSPARENT);
        }
        if (id == 7) {
            webView.loadUrl("file:///android_asset/terms_conditions.html");
            webView.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menuinv, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case android.R.id.home:
                finish();
                return true;

            case R.id.share:
                Bitmap imgBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon);
                String imgBitmapPath = MediaStore.Images.Media.insertImage(WebviewActivity.this.getContentResolver(), imgBitmap, "title", null);
                Uri imgBitmapUri = Uri.parse(imgBitmapPath);
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("image/*");
                shareIntent.putExtra(Intent.EXTRA_TEXT, app_name + " Created By : " + acc_link);
                shareIntent.putExtra(Intent.EXTRA_STREAM, imgBitmapUri);
                startActivity(shareIntent);
                return true;

            case R.id.rate:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
                return true;

            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }


}
