package lalit.findmyphone;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import lalit.findmyphone.model.Result;
import lalit.findmyphone.utilities.Contants;


public class DbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = Contants.DATABASE_NAME;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS userData");
        onCreate(db);

    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void onCreate(SQLiteDatabase db) {
        String CREATE_user_TABLE = "CREATE TABLE userData(UserName TEXT,PhoneNumber TEXT)";
        db.execSQL(CREATE_user_TABLE);

    }


    //insert userData data.............
    public boolean insertUserData(Result ob) {
        ContentValues values = new ContentValues();
        values.put("UserName", ob.getUserName());
        values.put("PhoneNumber", ob.getPhoneNumber());

        SQLiteDatabase db = this.getWritableDatabase();

        long i = db.insert("userData", null, values);
        db.close();
        return i > 0;
    }

    //userData data
    public Result getUserData() {

        String query = "Select * FROM userData";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Result data = new Result();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            populateUserData(cursor, data);
            cursor.close();
        } else {
            data = null;
        }
        db.close();
        return data;
    }

    //userData data
    public Result getUserDataByLoginId(String PhoneNumber) {

        String query = "Select * FROM userData WHERE PhoneNumber =' " + PhoneNumber + " '";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Result data = new Result();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            populateUserData(cursor, data);

            cursor.close();
        } else {
            data = null;
        }
        db.close();
        return data;
    }

    // for user data..........
    private void populateUserData(Cursor cursor, Result ob) {
        ob.setUserName(cursor.getString(0));
        ob.setPhoneNumber(cursor.getString(1));
    }

    // delete Address Data from addressId
    public boolean deleteUserData() {
        boolean result = false;
        SQLiteDatabase db = this.getWritableDatabase();
        // String query = "LoginId = " + LoginId + " ";
        db.delete("userData", null, null);
        db.close();
        return result;
    }

    //get all city data
    public List<Result> getAllData() {
        String query = "Select * FROM userData ";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        List<Result> list = new ArrayList<Result>();

        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                Result ob = new Result();
                populateUserData(cursor, ob);
                list.add(ob);
                cursor.moveToNext();
            }
        }
        db.close();
        return list;
    }

}