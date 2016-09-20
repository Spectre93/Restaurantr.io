package nl.verhoogenvansetten.restaurantrio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestaurantListContent {

    // An array of sample (dummy) items.
    public static final List<RestaurantItem> ITEMS = new ArrayList<RestaurantItem>();

    // A map of sample (dummy) items, by ID.
    public static final Map<String, RestaurantItem> ITEM_MAP = new HashMap<String, RestaurantItem>();

    static {
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
            addItem(new RestaurantItem(testData[i], location, description));
        }
    }

    private static void addItem(RestaurantItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.name, item);
    }

    // A dummy item representing a piece of content.
    public static class RestaurantItem {
        public final String name;
        public final String location;
        public final String description;

        public RestaurantItem(String name, String location, String description) {
            this.name = name;
            this.location = location;
            this.description = description;
        }

        @Override
        public String toString() {
            return name + " in " + location;
        }
    }
}
