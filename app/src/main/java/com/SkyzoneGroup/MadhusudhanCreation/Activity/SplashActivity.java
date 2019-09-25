package com.SkyzoneGroup.MadhusudhanCreation.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.SkyzoneGroup.MadhusudhanCreation.R;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.ApiClient;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.ApiInterface;

public class SplashActivity extends AppCompatActivity
{
    private static int SPLASH_TIME_OUT = 3000;
    ApiInterface apiService;
  //  BroadcastReceiver mRegistrationBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        apiService = ApiClient.getClient().create(ApiInterface.class);

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run() {
                Intent ii = new Intent(getApplicationContext(), HomActivity.class);
                ii.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(ii);
                finish();
            }
        }, SPLASH_TIME_OUT);

//        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//
//                // checking for type intent filter
//                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
//                    // gcm successfully registered
//                    // now subscribe to `global` topic to receive app wide notifications
//                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);
//
//                    displayFirebaseRegId();
//
//                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
//                    // new push notification is received
//
//                }
//            }
//        };
//        displayFirebaseRegId();
    }

//    private void displayFirebaseRegId() {
//        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
//        String regId = pref.getString("regId", null);
//        Boolean Token = pref.getBoolean("TOKEN", false);
//
//        Log.e(">>>>>>>>>", "Firebase reg id: " + regId);
//
//        if (!TextUtils.isEmpty(regId)) {
//            Log.i(">>>>>>>>>", "displayFirebaseRegId: " + regId);
//
//            if (Token == false) {
//                if (InternetConnection.checkConnection(getApplicationContext())) {
//                    setNotificationId(regId);
//                }
//            }
//
//        } else {
//
//        }
//
//    }

//    private void setNotificationId(String token) {
//        Call<Responce> call = apiService.GetNotification(token);
//        call.enqueue(new Callback<Responce>() {
//            @Override
//            public void onResponse(Call<Responce> call, retrofit2.Response<Responce> response) {
////                if (response.body().getData().size() == 0) {
////
////                } else {
////                }
//
//            }
//
//            @Override
//            public void onFailure(Call<Responce> call, Throwable t) {
//                //   Toast.makeText(SlashActvity.this, "Try Again", Toast.LENGTH_SHORT).show();
//            }
//        });
//        // JSONParser.insertData(token);
//    }

}
