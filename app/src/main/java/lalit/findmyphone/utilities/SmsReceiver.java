package lalit.findmyphone.utilities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;


/**
 * Created by Neeraj on 4/12/2017.
 */
public class SmsReceiver extends BroadcastReceiver {
    Context mContext;
    public static String verificationCode="true";

    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context;
                    Intent myIntent = new Intent("otp");
                    myIntent.putExtra("message", verificationCode);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(myIntent);
    }
}
