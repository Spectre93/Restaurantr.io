package nl.verhoogenvansetten.restaurantrio;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import nl.verhoogenvansetten.restaurantrio.model.Restaurant;

/**
 * A fragment representing a single Restaurant detail screen.
 * This fragment is either contained in a {@link RestaurantListActivity}
 * in two-pane mode (on tablets) or a {@link RestaurantDetailActivity}
 * on handsets.
 */
public class RestaurantDetailFragment extends Fragment {
    // The fragment argument representing the item name that this fragment represents.
    public static final String ARG_ITEM_ID = "item_id";

    // The restaurant this fragment is presenting.
    private Restaurant mItem;

    // Mandatory empty constructor for the fragment manager to instantiate the fragment
    public RestaurantDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the content specified by the fragment arguments.
            mItem = RestaurantListContent.ITEM_MAP.get(getArguments().getLong(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.getName());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.restaurant_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.restaurant_detail)).setText(mItem.getDescription());
            //((ImageView) rootView.findViewById(R.id.restaurant_image)).setImageDrawable(this.getResources().getDrawable(R.drawable.paris));
        }
        return rootView;
    }
}
