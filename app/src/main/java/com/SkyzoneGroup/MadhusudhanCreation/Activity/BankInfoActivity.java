package com.SkyzoneGroup.MadhusudhanCreation.Activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.SkyzoneGroup.MadhusudhanCreation.R;

public class BankInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_info);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Bank Details");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case android.R.id.home:
                finish();
                return true;

//            case R.id.share:
//                Bitmap imgBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon);
//                String imgBitmapPath = MediaStore.Images.Media.insertImage(BankInfoActivity.this.getContentResolver(), imgBitmap, "title", null);
//                Uri imgBitmapUri = Uri.parse(imgBitmapPath);
//                Intent shareIntent = new Intent(Intent.ACTION_SEND);
//                shareIntent.setType("image/*");
//                shareIntent.putExtra(Intent.EXTRA_TEXT, app_name + " Created By : " + acc_link);
//                shareIntent.putExtra(Intent.EXTRA_STREAM, imgBitmapUri);
//                startActivity(shareIntent);
//                return true;
//
//            case R.id.rate:
//                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
//                return true;

            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

}
