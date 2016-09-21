package nl.verhoogenvansetten.restaurantrio.util;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import nl.verhoogenvansetten.restaurantrio.AddEditDialogFragment;

/**
 * Created by Bas on 21-9-2016.
 */

public class DialogUtil {
    public static void openAddDialog(FragmentManager fragmentManager, String title) {
        AddEditDialogFragment addFragment = AddEditDialogFragment.newInstance(title, false);
        addFragment.show(fragmentManager, "add_edit_fragment");
        //FragmentTransaction transaction = fragmentManager.beginTransaction();
        //transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        //transaction.add(android.R.id.content, addFragment, "add_edit_fragment").addToBackStack(null).commit();
    }

    public static void openEditDialog(FragmentManager fragmentManager, String title, String name, String location, byte[] image) {
        AddEditDialogFragment addFragment = AddEditDialogFragment.newInstance(title, name, location, image, true);
        addFragment.show(fragmentManager, "add_edit_fragment");
        //FragmentTransaction transaction = fragmentManager.beginTransaction();
        //transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        //transaction.add(android.R.id.content, addFragment, "add_edit_fragment").addToBackStack(null).commit();
    }
}
