package nl.verhoogenvansetten.restaurantrio.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.ContextCompat;

import nl.verhoogenvansetten.restaurantrio.R;
import nl.verhoogenvansetten.restaurantrio.util.DatabaseHelper;
import nl.verhoogenvansetten.restaurantrio.util.DbBitmapUtility;

/**
 * Created by Jori on 20-9-2016.
 */

public class Restaurant{
    private long id;
    private String name;
    private String location;
    private String description;
    private Bitmap image;

    //Constructor which is used to create new restaurant objects and adds them to the db.
    public Restaurant(Context context, String name, String location, String description, Bitmap image) {
        this.name = name;
        this.location = location;
        this.description = description;
        //If there is a imaged added
        if(image != null){
            //Add the image to the Restaurant object
            this.image = image;
        }else{
            //Add the default image to the Restaurant object
            BitmapFactory.decodeResource(context.getResources(), R.drawable.image);
        }
        long result = DatabaseHelper.addRestaurant(this.getName(), this.getLocation(),
                this.getDescription(), this.getByteArrayFromImage());
        //If the insert is successful
        if (result != -1){
            //Set the id and return true
            this.id = result;
        }else{
            //Return false
        }
    }

    //Constructor which is used to load existing restaurant objects from the DB.
    public Restaurant(int id, String name, String location, String description, Bitmap image) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.description = description;
        this.image = image;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public void setImageFromByteArray(byte[] image) {
        this.image = DbBitmapUtility.getImage(image);
    }

    public byte[] getByteArrayFromImage(){
        return DbBitmapUtility.getBytes(this.getImage());
    }

}
