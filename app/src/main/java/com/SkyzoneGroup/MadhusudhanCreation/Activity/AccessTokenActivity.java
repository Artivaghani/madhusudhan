package com.SkyzoneGroup.MadhusudhanCreation.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.SkyzoneGroup.MadhusudhanCreation.R;
import com.SkyzoneGroup.MadhusudhanCreation.util.PrefUtil;

public class AccessTokenActivity extends AppCompatActivity {

    private TextView tokenTV;
    private PrefUtil prefUtil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access_token);
        init();
    }
    private void init() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        prefUtil = new PrefUtil(this);
        tokenTV = (TextView) findViewById(R.id.token_tv);
        tokenTV.setText(prefUtil.getToken());
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
