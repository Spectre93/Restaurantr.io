package nl.verhoogenvansetten.restaurantrio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.verhoogenvansetten.restaurantrio.model.Restaurant;
import nl.verhoogenvansetten.restaurantrio.util.DatabaseHelper;

public class RestaurantListContent {
    private static final String TAG = "RestaurantListContent";

    // The array of restaurants
    public static final List<Restaurant> ITEMS = new ArrayList<>();

    // A map of sample (dummy) items, by ID.
    public static final Map<Long, Restaurant> ITEM_MAP = new HashMap<Long, Restaurant>();

    static {
        boolean debug = false;
        if(debug){
            String[] testData = {"Pushing Daisies", "Better Off Ted",
                    "Twin Peaks", "Freaks and Geeks", "Orphan Black", "Walking Dead",
                    "Breaking Bad", "The 400", "Alphas", "Life on Mars", "Oz",
                    "Narcos", "South Park", "Rick and Morty", "Silicon Valley",
                    "Suits", "Halt and Catch Fire", "Elementary"};

            String location = "Delft";
            String description = "This is a placeholder description for the list view. This " +
                    "description will also be passed to the Detail fragment to list below the image. " +
                    "This placeholder text will have to be replaced by a database in the future.";

            for (int i = 0; i < testData.length; i++) {
                addItem(new Restaurant(i, testData[i], location, description, null));
            }
        }else{
            List<Restaurant> itemList = DatabaseHelper.getRestaurantList();
            for (Restaurant r : itemList) {
                addItem(r);
            }
        }
    }

    private static void addItem(Restaurant item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getId(), item);
    }

    public static void replaceAll(List<Restaurant> itemList) {
        ITEMS.clear();
        ITEM_MAP.clear();

        for (Restaurant r : itemList) {
            addItem(r);
        }
    }
}
