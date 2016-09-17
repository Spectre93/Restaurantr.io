package nl.verhoogenvansetten.restaurantrio;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import layout.DetailFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final FragmentManager fragmentManager = getSupportFragmentManager();

        //ListFragment lf = new ListFragment();

        //fragmentTransaction.replace(android.R.id.content, lf);

        //fragmentTransaction.commit();

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

        ListAdapter theAdapter = new MyAdapter(this, testData);

        ListView theListView = (ListView) findViewById(R.id.restList);

        theListView.setAdapter(theAdapter);

        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String testDataPicked = "You selected " +
                        String.valueOf(((ListItem) parent.getItemAtPosition(position)).getName());

                Toast.makeText(MainActivity.this, testDataPicked, Toast.LENGTH_LONG).show();

                DetailFragment detailFragment = new DetailFragment();

                FragmentTransaction ft = fragmentManager.beginTransaction();

                ft.replace(R.id.ListFrag, detailFragment);

                ft.addToBackStack(null);

                ft.commit();


            }
        });





        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog();
               // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void dialog(){
        AlertDialog.Builder alertDialogBuilder = new  AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Add your favorite restaurant");
        alertDialogBuilder.setPositiveButton("Check", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
