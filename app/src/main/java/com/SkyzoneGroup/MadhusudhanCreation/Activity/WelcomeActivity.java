package com.SkyzoneGroup.MadhusudhanCreation.Activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.SkyzoneGroup.MadhusudhanCreation.Adapter.ImagePagerAdapter;
import com.SkyzoneGroup.MadhusudhanCreation.R;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    private static int[] VPimage = new int[]{R.drawable.onboarding_1, R.drawable.onboarding_2, R.drawable.onboarding_3, R.drawable.onboarding_4, R.drawable.onboarding_5};
    Button Btsignup;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        bindview();

    }

    private void bindview() {
        Btsignup = (Button) findViewById(R.id.sign_up);
        Btsignup.setOnClickListener(this);
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        CircleIndicator circleIndicator = (CircleIndicator) findViewById(R.id.indicator);
        viewPager.setAdapter(new ImagePagerAdapter(this, VPimage));
        circleIndicator.setViewPager(viewPager);
        if (checkAndRequestPermissions()) {
            Intent intent = new Intent(this, LoginActivity.class);
            //    intent.putExtra(AccountKitActivity.f4517a, new C1672a(ad.PHONE, C1667a.TOKEN).m6891a());
            startActivity(intent);
            // carry on the normal flow, as the case of  permissions  granted.
        }

    }

    private boolean checkAndRequestPermissions() {
        int permissionSendMessage = ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS);

        int receiveSMS = ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECEIVE_SMS);

        int readSMS = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_SMS);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (receiveSMS != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.RECEIVE_MMS);
        }
        if (readSMS != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_SMS);
        }
        if (permissionSendMessage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.SEND_SMS);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                    REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    public void onClick(View v) {
        switch (v.getId() /*to get clicked view id**/) {
            case R.id.sign_up:
                AlertDialog.Builder dialog = new AlertDialog.Builder(WelcomeActivity.this);
                LayoutInflater inflater = WelcomeActivity.this.getLayoutInflater();
                View sortView = inflater.inflate(R.layout.log_in_dialog_box, null);
                dialog.setCancelable(true);
//                final Button btsingup = (Button) sortView.findViewById(R.id.BTsingup);
//                final EditText mEtPwd = (EditText) sortView.findViewById(R.id.etPassword);
//                final CheckBox mCbShowPwd = (CheckBox) sortView.findViewById(R.id.cbShowPwd);
//                btsingup.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
//                        startActivity(intent);
//                    }
//                });
//                mCbShowPwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
//                        if (!isChecked) {
//                            // show password
//                            mEtPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
//                        } else {
//                            // hide password
//                            mEtPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//                        }
//                    }
//                });
                dialog.setView(sortView);
                dialog.show();


                break;

            default:
                break;
        }

    }


}
