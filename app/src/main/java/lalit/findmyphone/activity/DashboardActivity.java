package lalit.findmyphone.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import lalit.findmyphone.DbHelper;
import lalit.findmyphone.R;
import lalit.findmyphone.model.Result;
import lalit.findmyphone.utilities.Contants;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
    }

    public void findNumberClick(View view) {
        startActivity(new Intent(DashboardActivity.this, FindPhoneActivity.class));
    }

    public void yourListClick(View view) {
        startActivity(new Intent(DashboardActivity.this, MainActivity.class));
    }

    @Override
    public void onResume() {
        registerReceiver(mReceiverLocation, new IntentFilter("action"));
        super.onResume();
    }

    @Override
    public void onPause() {
        unregisterReceiver(mReceiverLocation);
        super.onPause();
    }

    private BroadcastReceiver mReceiverLocation = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Boolean flag = intent.getBooleanExtra("flag", true);
            if (flag) {
                sendData();
            }
        }
    };

    private void sendData() {
        final DbHelper dbHelper = new DbHelper(this);
        final Result result = dbHelper.getUserData();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Contants.SERVICE_BASE_URL + Contants.UploadUserNamePhone,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dbHelper.deleteUserData();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("UserName", result.getUserName());
                params.put("PhoneNumber", result.getPhoneNumber());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }
}
