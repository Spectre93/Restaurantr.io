package nl.verhoogenvansetten.restaurantrio.model;

import java.sql.Blob;

/**
 * Created by Jori on 20-9-2016.
 */

public class Restaurant {

    private int id;
    private String name;
    private String location;
    private String description;
    private Blob image;

    public Restaurant(int id, String name, String location, String description, Blob image){
        this.id = id;
        this.name = name;
        this.location = location;
        this.description = description;
        this.image = image;
    }
}
