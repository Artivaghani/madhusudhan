package com.SkyzoneGroup.MadhusudhanCreation.Notification;

import android.app.Application;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.SkyzoneGroup.MadhusudhanCreation.Activity.HomActivity;
import com.SkyzoneGroup.MadhusudhanCreation.Activity.SubCategoryWiseProductActivity;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.ApiClient;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.ApiInterface;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.ResponceCartItem;
import com.onesignal.OSNotification;
import com.onesignal.OSNotificationAction;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;


import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExampleApplication extends Application {
    ApiInterface apiService;

    @Override
    public void onCreate() {
        super.onCreate();
        apiService = ApiClient.getClient().create(ApiInterface.class);
        OneSignal.startInit(this)
                .setNotificationReceivedHandler(new ExampleNotificationReceivedHandler())
                .setNotificationOpenedHandler(new ExampleNotificationOpenedHandler())
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
    }

    private class ExampleNotificationReceivedHandler implements OneSignal.NotificationReceivedHandler {
        @Override
        public void notificationReceived(OSNotification notification) {
            JSONObject data = notification.payload.additionalData;
            String notificationID = notification.payload.notificationID;
            String title = notification.payload.title;
            String body = notification.payload.body;
            String smallIcon = notification.payload.smallIcon;
            String largeIcon = notification.payload.largeIcon;
            String bigPicture = notification.payload.bigPicture;
            String smallIconAccentColor = notification.payload.smallIconAccentColor;
            String sound = notification.payload.sound;
            String ledColor = notification.payload.ledColor;
            int lockScreenVisibility = notification.payload.lockScreenVisibility;
            String groupKey = notification.payload.groupKey;
            String groupMessage = notification.payload.groupMessage;
            String fromProjectNumber = notification.payload.fromProjectNumber;
            String rawPayload = notification.payload.rawPayload;

            Toast.makeText(ExampleApplication.this, "" + title + notificationID, Toast.LENGTH_SHORT).show();

            String customKey;

            Log.i("OneSignalExample", "NotificationID received: " + notificationID);

            if (data != null) {
                customKey = data.optString("customkey", null);
                if (customKey != null)
                    Log.i("OneSignalExample", "customkey set with value: " + customKey);
            }
        }
    }


    private class ExampleNotificationOpenedHandler implements OneSignal.NotificationOpenedHandler {
        @Override
        public void notificationOpened(OSNotificationOpenResult result) {
            try {
                OSNotificationAction.ActionType actionType = result.action.type;
                JSONObject data = result.notification.payload.additionalData;
                String customKey;
                String catid = null;
                String catname = null;
                Object activityToLaunch = HomActivity.class;

                if (data != null) {
                    // customKey = data.optString("customkey", null);
                    catid = data.optString("cat_id", null);
                    catname = data.optString("catalog_name", null);
                    activityToLaunch = SubCategoryWiseProductActivity.class;




                }
                Intent intent = new Intent(getApplicationContext(), (Class<?>) activityToLaunch);
                intent.putExtra("CATID", catid);
                intent.putExtra("CATNAME", catname);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                getcartitems();
            } catch (Exception e) {
            }

        }
    }

    public void getcartitems() {
        try {
            Call<ResponceCartItem> call = apiService.getimagedelete();
            call.enqueue(new Callback<ResponceCartItem>() {
                @Override
                public void onResponse(Call<ResponceCartItem> call, Response<ResponceCartItem> response) {

                }

                @Override
                public void onFailure(Call<ResponceCartItem> call, Throwable t) {
                }
            });
        } catch (Exception e) {
        }
    }
}
