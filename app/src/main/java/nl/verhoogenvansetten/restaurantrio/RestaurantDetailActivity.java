package nl.verhoogenvansetten.restaurantrio;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.widget.EditText;

/**
 * An activity representing a single Restaurant detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item description are presented side-by-side with a list of items
 * in a {@link RestaurantListActivity}.
 */
public class RestaurantDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
                editDialog();
            }
        });

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // If savedInstanceState is non-null it will be automatically re-added
        // If it is null, make a new fragment and add it using a transaction
        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putString(RestaurantDetailFragment.ARG_ITEM_NAME,
                    getIntent().getStringExtra(RestaurantDetailFragment.ARG_ITEM_NAME));
            RestaurantDetailFragment fragment = new RestaurantDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.restaurant_detail_container, fragment)
                    .commit();
        }
    }

    public void editDialog() {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Edit your favorite restaurant");
        alertDialogBuilder.setMessage("Are you going to edit this?");
        final EditText input = new EditText(this);
        alertDialogBuilder.setView(input);
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Intent i = new Intent(getContext(), ItemSelection.class);
                // startActivity(i);
            }
        });
        alertDialogBuilder.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void deleteDialog() {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Delete your favorite restaurant");
        alertDialogBuilder.setMessage("Are you sure?");
        alertDialogBuilder.setPositiveButton("Cancel",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });
        alertDialogBuilder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //this.deleteDatabase("databasename.db");
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        // This ID represents the Home or Up button.
        if (id == android.R.id.home) {
            NavUtils.navigateUpTo(this, new Intent(this, RestaurantListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}