package nl.verhoogenvansetten.restaurantrio;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import nl.verhoogenvansetten.restaurantrio.ListItem;
import nl.verhoogenvansetten.restaurantrio.MyAdapter;
import nl.verhoogenvansetten.restaurantrio.R;

public class MyListFragment extends Fragment {

    ListView list;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list, container, false);
        list = (ListView) view.findViewById(R.id.restList);

        String[] testData1 = {"Pushing Daisies", "Better Off Ted",
                "Twin Peaks", "Freaks and Geeks", "Orphan Black", "Walking Dead",
                "Breaking Bad", "The 400", "Alphas", "Life on Mars", "Oz",
                "Narcos", "South Park", "Rick and Morty", "Silicon Valley",
                "Suits", "Halt and Catch Fire", "Elementary"};

        String placeholderDescription = "This is a placeholder description for the list view. This " +
                "description will also be passed to the Detail fragment to list below the image. " +
                "This placeholder text will have to be replaced by a database in the future.";

        ListItem[] testData = new ListItem[testData1.length];

        for (int i = 0; i < testData1.length; i++) {
            testData[i] = new ListItem(R.drawable.image, testData1[i], placeholderDescription);
        }

        ListAdapter theAdapter = new MyAdapter(this.getActivity(), testData);

        list.setAdapter(theAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String testDataPicked = "You selected " +
                        String.valueOf(((ListItem) parent.getItemAtPosition(position)).getName());

                Toast.makeText(parent.getContext(), testDataPicked, Toast.LENGTH_LONG).show();

                FragmentManager fm = getFragmentManager();

                DetailFragment detailFragment = new DetailFragment();

                FragmentTransaction ft = fm.beginTransaction();

                ft.replace(R.id.contentMain, detailFragment);

                ft.addToBackStack(null);

                ft.commit();

            }
        });
        return view;
    }

    public ListView getList() {
        return list;
    }
}
