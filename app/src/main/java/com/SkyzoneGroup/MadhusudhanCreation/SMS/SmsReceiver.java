package com.SkyzoneGroup.MadhusudhanCreation.SMS;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

/**
 * Created by kevalt on 19-07-2017.
 */

public class SmsReceiver extends BroadcastReceiver {
    private static SmsListner mListener;
    Boolean b;
    String abcd, xyz;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle data = intent.getExtras();
        Object[] pdus = (Object[]) data.get("pdus");
        for (int i = 0; i < pdus.length; i++) {
            SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdus[i]);
            String sender = smsMessage.getDisplayOriginatingAddress();
           //  b=sender.endsWith("VM-MYGOAL");  //Just to fetch otp sent from WNRCRP
            b=sender.contains("SMRPAN");
            String messageBody = smsMessage.getMessageBody();
            abcd = messageBody.replaceAll("[^0-9]", "");   // here abcd contains otp
         //   which is in number format
            //Pass on the text to our listener.

            try {
                if (b == true) {
                    mListener.messageReceived(abcd);  // attach value to interface object
                } else {
                }
            }catch (Exception ex){}

        }
    }

    public static void bindListener(SmsListner listener) {
        mListener = listener;
    }
}
