package nl.verhoogenvansetten.restaurantrio;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Array;

import nl.verhoogenvansetten.restaurantrio.R;

public class MyAdapter extends ArrayAdapter<ListItem> {


    public MyAdapter(Context context, ListItem[] values) {
        super(context, R.layout.row_layout_2, values);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater theInflater = LayoutInflater.from(getContext());

        View theView = theInflater.inflate(R.layout.row_layout_2, parent, false);

        String Data = getItem(position).getName();

        TextView theTextView = (TextView) theView.findViewById(R.id.textView1);

        theTextView.setText(Data);

        ImageView theImageView = (ImageView) theView.findViewById(R.id.imageView1);

        theImageView.setImageResource(getItem(position).getImage());

        return theView;

    }

}