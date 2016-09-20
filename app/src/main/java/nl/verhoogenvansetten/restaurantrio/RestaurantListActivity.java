package nl.verhoogenvansetten.restaurantrio;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;

/**
 * An activity representing a list of Restaurants. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link RestaurantDetailActivity} representing
 * item description. On tablets, the activity presents the list of items and
 * item description side-by-side using two vertical panes.
 */
public class RestaurantListActivity extends AppCompatActivity {

    // Whether or not the activity is in two-pane mode, i.e. running on a tablet device.
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
                addDialog();
            }
        });

        View recyclerView = findViewById(R.id.restaurant_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        // Detail container view will only be present on large layouts
        if (findViewById(R.id.restaurant_detail_container) != null) {
            mTwoPane = true;
        }
    }

    public void addDialog() {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        Context context = getApplicationContext();
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.add_dialog, (ViewGroup)findViewById(R.id.addDialogLayout));
        alertDialogBuilder.setView(view);

        alertDialogBuilder.setTitle("Add your favorite restaurant");
        alertDialogBuilder.setMessage("Enter the name of restaurant");
        final EditText title = (EditText) view.findViewById(R.id.addInput);

        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if(title.getText().toString().length()==0){
                    Toast.makeText(RestaurantListActivity.this, "Pleas enter the name", Toast.LENGTH_SHORT).show();
                    alertDialogBuilder.setCancelable(false);
                }
                else {
                    String str =title.getText().toString();
                    Intent intent = new Intent(getApplicationContext(), AddData.class);
                    intent.putExtra("input", str);
                    startActivity(intent);
                }
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

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(RestaurantListContent.ITEMS));
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<RestaurantListContent.RestaurantItem> mValues;

        public SimpleItemRecyclerViewAdapter(List<RestaurantListContent.RestaurantItem> items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.restaurant_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            holder.mNameView.setText(mValues.get(position).name);
            holder.mLocationView.setText(mValues.get(position).location);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putString(RestaurantDetailFragment.ARG_ITEM_NAME, holder.mItem.name);
                        RestaurantDetailFragment fragment = new RestaurantDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.restaurant_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, RestaurantDetailActivity.class);
                        intent.putExtra(RestaurantDetailFragment.ARG_ITEM_NAME, holder.mItem.name);

                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mNameView;
            public final TextView mLocationView;
            public RestaurantListContent.RestaurantItem mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mNameView = (TextView) view.findViewById(R.id.restaurant_name);
                mLocationView = (TextView) view.findViewById(R.id.restaurant_location);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mLocationView.getText() + "'";
            }
        }
    }
}
