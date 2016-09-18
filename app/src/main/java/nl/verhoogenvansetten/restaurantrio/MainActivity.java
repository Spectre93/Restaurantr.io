package nl.verhoogenvansetten.restaurantrio;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String[] testData1 = {"Pushing Daisies", "Better Off Ted",
                "Twin Peaks", "Freaks and Geeks", "Orphan Black", "Walking Dead",
                "Breaking Bad", "The 400", "Alphas", "Life on Mars", "Oz",
                "Narcos", "South Park", "Rick and Morty", "Silicon Valley",
                "Suits", "Halt and Catch Fire", "Elementary"};

        ListItem[] testData = new ListItem[testData1.length];

        for (int i = 0; i < testData1.length; i++) {
            testData[i] = new ListItem(R.drawable.image, testData1[i]);
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
