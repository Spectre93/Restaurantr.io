package nl.verhoogenvansetten.restaurantrio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.verhoogenvansetten.restaurantrio.model.Restaurant;
import nl.verhoogenvansetten.restaurantrio.util.DatabaseHelper;

class RestaurantListContent {
    // The array of restaurants
    static final List<Restaurant> ITEMS = new ArrayList<>();
    // A map of sample (dummy) items, by ID.
    static final Map<Long, Restaurant> ITEM_MAP = new HashMap<Long, Restaurant>();
    private static final String TAG = "RestaurantListContent";

    static {
        List<Restaurant> itemList = DatabaseHelper.getRestaurantList();
        for (Restaurant r : itemList) {
            addItem(r);
        }
    }

    private static void addItem(Restaurant item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getId(), item);
    }
}
