package nl.verhoogenvansetten.restaurantrio.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

import java.util.ArrayList;

import nl.verhoogenvansetten.restaurantrio.model.Restaurant;

/**
 * Created by Jori on 20-9-2016.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    //Database version
    private static final int DATABASE_VERSION = 1;

    //Database name
    private static final String DATABASE_NAME = "restaurantrio.db";

    //Database table names
    private static final String TABLE_RESTAURANT = "table_restaurant";
    //Database column names for the TABLE_RESTAURANT
    private static final String COLUMN_RESTAURANT_ID = "id";
    private static final String COLUMN_RESTAURANT_NAME = "name";
    private static final String COLUMN_RESTAURANT_LOCATION = "location";
    private static final String COLUMN_RESTAURANT_DESCRIPTION = "description";
    private static final String COLUMN_RESTAURANT_IMAGE = "image";

    //Table creation statement
    private static final String CREATE_TABLE_RESTAURANT = "CREATE TABLE " + TABLE_RESTAURANT + "(" +
            COLUMN_RESTAURANT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_RESTAURANT_NAME + " TEXT," +
            COLUMN_RESTAURANT_LOCATION + " TEXT," +
            COLUMN_RESTAURANT_DESCRIPTION + " TEXT," +
            COLUMN_RESTAURANT_IMAGE + " BLOB);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public static Restaurant getRestaurantById(int id){
        return null;
    }

    // Returns the entire list of restaurants
    public static ArrayList<Restaurant> getRestaurantList(){
        return null;
    }

    // Returns only the restaurants whose names match the query
    public static ArrayList<Restaurant> getRestaurantList(String query){
        return null;
    }


    public boolean editRestaurant(int id, String name, String location, String description, byte[] image){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_RESTAURANT_NAME, name);
        contentValues.put(COLUMN_RESTAURANT_LOCATION, location);
        contentValues.put(COLUMN_RESTAURANT_DESCRIPTION, description);
        contentValues.put(COLUMN_RESTAURANT_IMAGE, image);
        String whereClause = COLUMN_RESTAURANT_ID + " = ?";
        String[] whereArgs = new String[]{ Integer.toString(id) };
        int result = db.update(TABLE_RESTAURANT, contentValues, whereClause, whereArgs);
        //If the amount of rows updated is 1 return true
        if(result == 1){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean deleteRestaurant(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = COLUMN_RESTAURANT_ID + " = ?";
        String[] whereArgs = new String[]{ Integer.toString(id) };
        int result = db.delete(TABLE_RESTAURANT, whereClause, whereArgs);
        if(result == 1){
            return true;
        }
        else{
            return false;
        }
    }

    //Adds the restaurant to the DB, returns the id or -1 when it failed.
    public long addRestaurant(String name, String location, String description, byte[] image){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_RESTAURANT_NAME, name);
        contentValues.put(COLUMN_RESTAURANT_LOCATION, location);
        contentValues.put(COLUMN_RESTAURANT_DESCRIPTION, description);
        contentValues.put(COLUMN_RESTAURANT_IMAGE, image);
        long result = db.insert(TABLE_RESTAURANT, null, contentValues);
        return result;
    }

}
