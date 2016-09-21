package nl.verhoogenvansetten.restaurantrio;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AddEditDialogFragment extends DialogFragment {
    private static final String TAG = "AddEditDialogFragment";
    private OnFragmentInteractionListener mListener;

    public AddEditDialogFragment() {}

    public static AddEditDialogFragment newInstance(String title) {
        AddEditDialogFragment frag = new AddEditDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    public static AddEditDialogFragment newInstance(String title, String name, String location, byte[] image) {
        AddEditDialogFragment frag = new AddEditDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("name", name);
        args.putString("location", location);
        args.putByteArray("image", image);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dialog_add_edit, container, false);

        /*Bundle args = getArguments();

        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        String mTitle = getArguments().getString("title");
        toolbar.setTitle(mTitle);

        String mName;
        TextInputEditText mETxtName = (TextInputEditText) rootView.findViewById(R.id.etxt_restaurant_name);
        if ((mName = args.getString("name")) != null)
            mETxtName.setText(mName);

        String mLocation;
        TextInputEditText mETxtLocation = (TextInputEditText) rootView.findViewById(R.id.etxt_restaurant_location);
        if ((mLocation = args.getString("location")) != null)
            mETxtLocation.setText(mLocation);

        /*AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
        }

        setHasOptionsMenu(true);*/
        return rootView;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View content = inflater.inflate(R.layout.fragment_dialog_add_edit, null);
        builder.setView(content);

        Bundle args = getArguments();

        String mName;
        TextInputEditText mETxtName = (TextInputEditText) content.findViewById(R.id.etxt_restaurant_name);
        if ((mName = args.getString("name")) != null)
            mETxtName.setText(mName);

        String mLocation;
        TextInputEditText mETxtLocation = (TextInputEditText) content.findViewById(R.id.etxt_restaurant_location);
        if ((mLocation = args.getString("location")) != null)
            mETxtLocation.setText(mLocation);

        // Add action buttons
        builder.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

            }
        })
        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                AddEditDialogFragment.this.getDialog().dismiss();
            }
        });
        return builder.create();
    }

    /*
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        getActivity().getMenuInflater().inflate(R.menu.menu_add_edit_dialog, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save) {
            // Save data to DB
            return true;
        } else if (id == android.R.id.home) {
            dismiss();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
