package nl.verhoogenvansetten.restaurantrio;

public class ListItem {
    private int image;
    private String name;

    public ListItem(int i, String n) {

        image = i;
        name = n;

    }

    String getName() {
        return name;
    }

    int getImage() {
        return image;
    }

}
