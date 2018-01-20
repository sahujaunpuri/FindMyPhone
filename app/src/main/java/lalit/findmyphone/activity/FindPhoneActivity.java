package lalit.findmyphone.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import lalit.findmyphone.R;
import lalit.findmyphone.model.ContentData;
import lalit.findmyphone.model.Result;
import lalit.findmyphone.utilities.Contants;

public class FindPhoneActivity extends AppCompatActivity {
    TextView username, phoneNumber;
    EditText search_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_phone);
        init();
    }

    private void init() {
        username = (TextView) findViewById(R.id.username);
        phoneNumber = (TextView) findViewById(R.id.phone);
        search_bar = (EditText) findViewById(R.id.search_bar);

    }

    public void findNumberClick(View view) {
        final String number = search_bar.getText().toString();
        if (number != null) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Contants.SERVICE_BASE_URL + Contants.FetchfindMyPhoneData,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            ContentData contentData = new Gson().fromJson(response, ContentData.class);
                            if (contentData != null) {
                                for (Result result : contentData.getResult()) {
                                    if (result != null) {
                                        username.setText(result.getUserName());
                                        phoneNumber.setText(result.getPhoneNumber());
                                    } else {
                                        username.setText("Sorry User Name Not Available");
                                        phoneNumber.setText("Sorry Phone Number Not Available");
                                    }
                                }
                            }
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
                    params.put("PhoneNumber", number);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
    }
}
