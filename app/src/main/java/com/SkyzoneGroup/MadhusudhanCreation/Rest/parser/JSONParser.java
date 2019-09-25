package com.SkyzoneGroup.MadhusudhanCreation.Rest.parser;

/**
 * Created by n9xCh on 23-Aug-16.
 */

import android.support.annotation.NonNull;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class JSONParser {
    /********
     * URLS
     *******/
    private static final String MAIN_URL = "http://1stchoice.in/web_service.php?f=get_all_subcategory&cat_id=";
    public static final String TAG = "TAG";
    private static Response response;
    public static JSONObject getDataFromWeb(String CAT_ID) {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(MAIN_URL+CAT_ID)
                    .build();
            response = client.newCall(request).execute();
            return new JSONObject(response.body().string());
        } catch (@NonNull IOException | JSONException e) {
            Log.e(TAG, "CAT_ID" + e.getLocalizedMessage());
        }
        return null;
    }




}