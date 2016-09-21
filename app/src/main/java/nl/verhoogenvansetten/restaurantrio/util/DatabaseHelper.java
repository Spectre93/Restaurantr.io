package nl.verhoogenvansetten.restaurantrio.util;

import java.sql.Blob;
import java.util.ArrayList;

import nl.verhoogenvansetten.restaurantrio.model.Restaurant;

/**
 * Created by Jori on 20-9-2016.
 */

public final class DatabaseHelper {

    // Private constructor so the class can't be instantiated
    private DatabaseHelper(){
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

    public static boolean addRestaurant(String name, String location, String description, Blob image){
        return false;
    }

    public static boolean editRestaurant(int id, String name, String location, String description, Blob image){
        return false;
    }

    public static boolean deleteRestaurant(int id){
        return false;
    }
}
