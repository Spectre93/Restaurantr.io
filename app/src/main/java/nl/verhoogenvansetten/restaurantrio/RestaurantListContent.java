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
    static final Map<Long, Restaurant> ITEM_MAP = new HashMap<>();
    private static final String TAG = "RestaurantListContent";

    static {
        List<Restaurant> itemList = DatabaseHelper.getRestaurantList();
        for (Restaurant r : itemList) {
            addItem(r);
        }
    }

    static void addItem(Restaurant item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getId(), item);
    }

    static void updateItem(Restaurant item) {
        ITEM_MAP.put(item.getId(), item);
        for (int i = 0; i < ITEMS.size(); i++) {
            if (ITEMS.get(i).getId() == item.getId())
                ITEMS.set(i, item);
        }
    }
}