package lalit.findmyphone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import lalit.findmyphone.model.Result;
import lalit.findmyphone.utilities.Contants;

/**
 * Created by Lalit on 1/20/2018.
 */

public class NetworkChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        isNetworkAvailable(context);
    }

    public void isNetworkAvailable(Context context) {
        if (isOnline(context)) {
            Intent intent = new Intent("action");
            intent.putExtra("flag", true);
            context.sendBroadcast(intent);
        }
    }

    //check online
    public static boolean isOnline(Context context) {
        ConnectivityManager connectivityManager;
        boolean connected = false;
        try {
            connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            connected = networkInfo != null && networkInfo.isAvailable() &&
                    networkInfo.isConnected();
            return connected;


        } catch (Exception e) {
            //System.out.println("CheckConnectivity Exception: " + e.getMessage());
            //Log.v("connectivity", e.toString());
        }
        return connected;
    }
}