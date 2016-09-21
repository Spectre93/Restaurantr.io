package nl.verhoogenvansetten.restaurantrio;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import nl.verhoogenvansetten.restaurantrio.model.Restaurant;
import nl.verhoogenvansetten.restaurantrio.util.DialogUtil;

/**
 * An activity representing a single Restaurant detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item description are presented side-by-side with a list of items
 * in a {@link RestaurantListActivity}.
 */
public class RestaurantDetailActivity extends AppCompatActivity implements AddEditDialogFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        Restaurant rest = RestaurantListContent.ITEM_MAP.get(getIntent().getLongExtra(RestaurantDetailFragment.ARG_ITEM_ID, -1));
        final String name = rest.getName();
        final String location = rest.getLocation();
        final String description = rest.getDescription();
        final byte[] image = "lol".getBytes(); //rest.getByteArrayFromImage();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogUtil.openEditDialog(getSupportFragmentManager(), getString(R.string.new_restaurant), name, location, description, image);
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
            arguments.putLong(RestaurantDetailFragment.ARG_ITEM_ID,
                    getIntent().getLongExtra(RestaurantDetailFragment.ARG_ITEM_ID, -1));
            RestaurantDetailFragment fragment = new RestaurantDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.restaurant_detail_container, fragment)
                    .commit();
        }
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

    public void onFragmentInteraction(Uri uri) {}
}
