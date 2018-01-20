package lalit.findmyphone.framework;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import lalit.findmyphone.DbHelper;
import lalit.findmyphone.model.ContentData;
import lalit.findmyphone.model.Result;
import lalit.findmyphone.utilities.Contants;


public class ServiceCaller {
    Context context;

    public ServiceCaller(Context context) {
        this.context = context;
    }

    //call user data
    public void callUserService(String phone, final IAsyncWorkCompletedCallback workCompletedCallback) {

        final String url = Contants.SERVICE_BASE_URL + Contants.FetchfindMyPhoneData;
        JSONObject obj = new JSONObject();
        try {
            obj.put("PhoneNumber", phone);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(Contants.LOG_TAG, "Payload*****" + obj);
        new ServiceHelper().callService(url, obj, new IServiceSuccessCallback() {
            @Override
            public void onDone(String doneWhatCode, String result, String error) {
                if (result != null) {
                    parseAndSaveLoginData(result, workCompletedCallback);
                } else {
                    workCompletedCallback.onDone("callUserService done", false);
                }
            }
        });
    }

    //parse and save user data
    public void parseAndSaveLoginData(final String result, final IAsyncWorkCompletedCallback workCompletedCallback) {
        new AsyncTask<Void, Void, Boolean>() {


            @Override
            protected Boolean doInBackground(Void... voids) {
                Boolean flag = false;
                if (result != null) {
                    ContentData data = new Gson().fromJson(result, ContentData.class);
                    if (data != null) {
                        if (data.getResult() != null) {
                            DbHelper dbHelper = new DbHelper(context);
                            dbHelper.deleteUserData();
                            for (Result result1 : data.getResult()) {
                                if (result1 != null) {
                                    dbHelper.insertUserData(result1);
                                }
                            }
                            flag = true;
                        }
                    }
                }
                return flag;

            }


            @Override
            protected void onPostExecute(Boolean flag) {
                super.onPostExecute(flag);
                if (flag) {
                    workCompletedCallback.onDone("callUserService done", true);
                } else {
                    workCompletedCallback.onDone("callUserService done", false);
                }
            }
        }.execute();
    }

}