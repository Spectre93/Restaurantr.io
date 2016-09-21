package nl.verhoogenvansetten.restaurantrio.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

import java.util.ArrayList;

import nl.verhoogenvansetten.restaurantrio.model.Restaurant;

/**
 * Created by Jori on 20-9-2016.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    //Singleton instance
    private static DatabaseHelper firstInstance = null;

    //Static SqliteDatabase object
    private static SQLiteDatabase db = null;

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

    //Table delete entries statement
    private static final String DELETE_TABLE_RESTAURANT = "DROP TABLE IF EXISTS "
            + TABLE_RESTAURANT;

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = this.getWritableDatabase();
    }

    public static void initDatabase(Context context){
        if(firstInstance == null){
            firstInstance = new DatabaseHelper(context);
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_RESTAURANT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_TABLE_RESTAURANT);
        onCreate(db);
        //Todo smart upgrade of database without dropping entries
    }

    public static Restaurant getRestaurantById(int restaurantId){
        Restaurant restaurant;
        String[] columns = {
                COLUMN_RESTAURANT_ID,
                COLUMN_RESTAURANT_NAME,
                COLUMN_RESTAURANT_LOCATION,
                COLUMN_RESTAURANT_DESCRIPTION,
                COLUMN_RESTAURANT_IMAGE
        };
        String whereClause = COLUMN_RESTAURANT_ID + " = ?";
        String[] whereArgs = new String[]{ Integer.toString(restaurantId) };
        Cursor cursor = db.query(
                TABLE_RESTAURANT,   //Table
                columns,            //Columns to be selected
                whereClause,        //Selection
                whereArgs,          //Values for selection
                null,               //Don't group the rows
                null,               //Don't filter by row groups
                null                //Don't sort
                );
        try{
            cursor.moveToFirst();
            restaurant = DatabaseHelper.createRestaurantFromCursor(cursor);
        } catch (Exception e){
            //todo error handling
            restaurant = null;
        }finally {
            cursor.close();
        }

        return restaurant;
    }

    // Returns the entire list of restaurants
    public static ArrayList<Restaurant> getRestaurantList(){
        ArrayList<Restaurant> restaurants = null;
        String[] columns = {
                COLUMN_RESTAURANT_ID,
                COLUMN_RESTAURANT_NAME,
                COLUMN_RESTAURANT_LOCATION,
                COLUMN_RESTAURANT_DESCRIPTION,
                COLUMN_RESTAURANT_IMAGE
        };
        Cursor cursor = db.query(
                TABLE_RESTAURANT,   //Table
                columns,            //Columns to be selected
                null,               //Selection
                null,               //Values for selection
                null,               //Don't group the rows
                null,               //Don't filter by row groups
                null                //Don't sort
        );
        try{
            while(cursor.moveToNext()){
                restaurants.add(DatabaseHelper.createRestaurantFromCursor(cursor));
            }
        } catch (Exception e){
            //todo error handling
        }finally {
            cursor.close();
        }
        return restaurants;
    }

    // Returns only the restaurants whose names match the query
    public static ArrayList<Restaurant> getRestaurantList(String name){
        //If name is null or the length is smaller then 1 set a default value.
        if (name != null){
            if(name.length() < 1){
                name = "default";
            }
        } else {
            name = "default";
        }

        ArrayList<Restaurant> restaurants = new ArrayList<>();
        String[] columns = {
                COLUMN_RESTAURANT_ID,
                COLUMN_RESTAURANT_NAME,
                COLUMN_RESTAURANT_LOCATION,
                COLUMN_RESTAURANT_DESCRIPTION,
                COLUMN_RESTAURANT_IMAGE
        };
        String whereClause = COLUMN_RESTAURANT_NAME + " LIKE ?";
        String[] whereArgs = new String[]{"%" + name + "%"};
        Cursor cursor = db.query(
                TABLE_RESTAURANT,   //Table
                columns,            //Columns to be selected
                whereClause,        //Selection
                whereArgs,          //Values for selection
                null,               //Don't group the rows
                null,               //Don't filter by row groups
                null                //Don't sort
        );
        try{
            while(cursor.moveToNext()){
                restaurants.add(DatabaseHelper.createRestaurantFromCursor(cursor));
            }
        } catch (Exception e){
            //todo error handling
        }finally {
            cursor.close();
        }
        return restaurants;
    }

    public static boolean editRestaurant(int id, String name, String location, String description, Bitmap image){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_RESTAURANT_NAME, name);
        contentValues.put(COLUMN_RESTAURANT_LOCATION, location);
        contentValues.put(COLUMN_RESTAURANT_DESCRIPTION, description);
        contentValues.put(COLUMN_RESTAURANT_IMAGE, DbBitmapUtility.getBytes(image));
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

    public static boolean deleteRestaurant(int id) {
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
    public static long addRestaurant(String name, String location, String description, byte[] image){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_RESTAURANT_NAME, name);
        contentValues.put(COLUMN_RESTAURANT_LOCATION, location);
        contentValues.put(COLUMN_RESTAURANT_DESCRIPTION, description);
        contentValues.put(COLUMN_RESTAURANT_IMAGE, image);
        long result = db.insert(TABLE_RESTAURANT, null, contentValues);
        return result;
    }

    private static Restaurant createRestaurantFromCursor(Cursor cursor){
        Restaurant restaurant = null;
        try{
            int id = Integer.parseInt(cursor.getString(
                    cursor.getColumnIndexOrThrow(COLUMN_RESTAURANT_ID)));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(
                    COLUMN_RESTAURANT_NAME));
            String location = cursor.getString(cursor.getColumnIndexOrThrow(
                    COLUMN_RESTAURANT_LOCATION));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(
                    COLUMN_RESTAURANT_DESCRIPTION));
            Bitmap image = DbBitmapUtility.getImage(cursor.getBlob(cursor.getColumnIndexOrThrow(
                    COLUMN_RESTAURANT_IMAGE)));
            restaurant = new Restaurant(id, name, location, description, image);
        } catch (Exception e){
            //todo Error handling
            return restaurant;
        }
        return restaurant;
    }

}
