package nl.verhoogenvansetten.restaurantrio;

public class ListItem {
    private int image;
    private String name;
    private String description;

    public ListItem(int i, String n, String d) {

        image = i;
        name = n;
        description = d;

    }

    String getName() {
        return name;
    }

    int getImage() {
        return image;
    }

    String getDescription() {
        return description;
    }

}
