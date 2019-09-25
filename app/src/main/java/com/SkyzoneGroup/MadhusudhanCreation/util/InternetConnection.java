package com.SkyzoneGroup.MadhusudhanCreation.util;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by n9xCh on 23-Aug-16.
 */
public class
InternetConnection
{
    public static String cancelUrl="http://www.1stchoice.in/ccavenue/ccavResponseHandler.php";
    public static String redirectUrl="http://www.1stchoice.in/ccavenue/ccavResponseHandler.php";
    public static String rsaUrl="http://www.1stchoice.in/ccavenue/GetRSA.php";
    public static String currency="INR";
    public static String merchantId="172202";
    public static String accessCode="AVLO77FD26AH24OLHA";

    //SendBox
    public static final String PAYPAL_CLIENT_ID = "AazgAlIwr_OcwKa6egKXoSZy63f5pBugVlrmgQsB7chh490y1_-YxE-9bknA4_Qejxn5neXYvkHqav3g";

//Live
  //  public static final String PAYPAL_CLIENT_ID = "AdgZZmQ1xIYXVNGG5vOp79SFan9Rdf7gKRxCKD3lLyLQxcd32nl1fz0Hn8T_p4NN4fldy3SrFJ9z5OHN";


    /** CHECK WHETHER INTERNET CONNECTION IS AVAILABLE OR NOT */
    public static boolean checkConnection(Context context) {
        return  ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }
}
