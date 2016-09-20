package nl.verhoogenvansetten.restaurantrio;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A fragment representing a single Restaurant detail screen.
 * This fragment is either contained in a {@link RestaurantListActivity}
 * in two-pane mode (on tablets) or a {@link RestaurantDetailActivity}
 * on handsets.
 */
public class RestaurantDetailFragment extends Fragment {
    // The fragment argument representing the item name that this fragment represents.
    public static final String ARG_ITEM_NAME = "item_name";

    // The dummy content this fragment is presenting.
    private RestaurantListContent.RestaurantItem mItem;

    // Mandatory empty constructor for the fragment manager to instantiate the fragment
    public RestaurantDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_NAME)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = RestaurantListContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_NAME));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.name);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.restaurant_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.restaurant_detail)).setText(mItem.description);
            //((ImageView) rootView.findViewById(R.id.restaurant_image)).setImageDrawable(this.getResources().getDrawable(R.drawable.paris));
        }
        return rootView;
    }
}
