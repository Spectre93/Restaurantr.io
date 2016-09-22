package nl.verhoogenvansetten.restaurantrio;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import nl.verhoogenvansetten.restaurantrio.model.Restaurant;
import nl.verhoogenvansetten.restaurantrio.util.DatabaseHelper;
import nl.verhoogenvansetten.restaurantrio.util.DbBitmapUtility;

import static android.app.Activity.RESULT_OK;


public class AddEditDialogFragment extends DialogFragment {
    private static final String TAG = "AddEditDialogFragment";
    private static final int CAMERA_REQUEST = 1;
    private static boolean isEdit;
    private static RestaurantListActivity.SimpleItemRecyclerViewAdapter adapter;
    ImageView imageView;
    private OnFragmentInteractionListener mListener;
    private Uri mOutputFileUri;

    public AddEditDialogFragment() {}

    public static AddEditDialogFragment newInstance(String title, boolean isEdit) {
        AddEditDialogFragment frag = new AddEditDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        AddEditDialogFragment.isEdit = isEdit;
        frag.setArguments(args);
        return frag;
    }

    public static AddEditDialogFragment newInstance(long id, String title, String name, String location, String description, byte[] image, boolean isEdit) {
        AddEditDialogFragment frag = new AddEditDialogFragment();
        Bundle args = new Bundle();
        args.putLong("id", id);
        args.putString("title", title);
        args.putString("name", name);
        args.putString("location", location);
        args.putString("description", description);
        args.putByteArray("image", image);
        AddEditDialogFragment.isEdit = isEdit;
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /*View rootView = inflater.inflate(R.layout.fragment_dialog_add_edit, container, false);

        Bundle args = getArguments();

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
        return inflater.inflate(R.layout.fragment_dialog_add_edit, container, false);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        @SuppressLint("InflateParams") View content = inflater.inflate(R.layout.fragment_dialog_add_edit, null);
        builder.setView(content);

        imageView = (ImageView) content.findViewById(R.id.imageView);

        Button mBtnUpload = (Button) content.findViewById(R.id.btnUpload);
        mBtnUpload.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageIntent();
            }
        });

        Bundle args = getArguments();

        String mName;
        TextInputEditText mETxtName = (TextInputEditText) content.findViewById(R.id.etxt_restaurant_name);
        if ((mName = args.getString("name")) != null)
            mETxtName.setText(mName);

        String mLocation;
        TextInputEditText mETxtLocation = (TextInputEditText) content.findViewById(R.id.etxt_restaurant_location);
        if ((mLocation = args.getString("location")) != null)
            mETxtLocation.setText(mLocation);

        String mDescription;
        TextInputEditText mETxtDescription = (TextInputEditText) content.findViewById(R.id.etxt_restaurant_description);
        if ((mDescription = args.getString("description")) != null)
            mETxtDescription.setText(mDescription);

        byte[] mImage;
        if ((mImage = args.getByteArray("image")) != null)
            imageView.setImageBitmap(DbBitmapUtility.getImage(mImage));

        final long mId = args.getLong("id");

        // Add action buttons
        builder.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                final String name = ((TextInputEditText) getDialog().findViewById(R.id.etxt_restaurant_name)).getText().toString();
                final String location = ((TextInputEditText) getDialog().findViewById(R.id.etxt_restaurant_location)).getText().toString();
                final String description = ((TextInputEditText) getDialog().findViewById(R.id.etxt_restaurant_description)).getText().toString();
                final Bitmap image = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

                if (isEdit) {
                    RestaurantListContent.updateItem(new Restaurant(mId, name, location, description, image));
                    DatabaseHelper.editRestaurant(mId, name, location, description, image);
                    updateUI(name, location, description, image);
                } else {
                    RestaurantListContent.addItem(new Restaurant(getContext(), name, location, description, image));
                }
                RestaurantListActivity.adapter.setFilter(DatabaseHelper.getRestaurantList());
            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                AddEditDialogFragment.this.getDialog().dismiss();
            }
        });
        return builder.create();
    }

    private void updateUI(String name, String location, String description, Bitmap image) {
        RestaurantDetailActivity activity = (RestaurantDetailActivity) getActivity();
        activity.updateUI(name, location, description, image);
    }

    private void openImageIntent() {
        File outputFile = null;
        try {
            outputFile = File.createTempFile("tmp", ".jpg", getActivity().getCacheDir());
        } catch (IOException pE) {
            pE.printStackTrace();
        }
        mOutputFileUri = Uri.fromFile(outputFile);

        // Camera.
        final List<Intent> cameraIntents = new ArrayList<>();
        final Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        final PackageManager packageManager = getActivity().getPackageManager();
        final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for(ResolveInfo res : listCam) {
            final String packageName = res.activityInfo.packageName;
            final Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(packageName);
            cameraIntents.add(intent);
        }

        // Filesystem.
        final Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_PICK);

        // Chooser of filesystem options.
        final Intent chooserIntent = Intent.createChooser(galleryIntent, "Select Source");

        // Add the camera options.
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[cameraIntents.size()]));

        startActivityForResult(chooserIntent, CAMERA_REQUEST);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_REQUEST) {
                Bitmap bmp;
                if (data.hasExtra("data")) {
                    Bundle extras = data.getExtras();
                    bmp = (Bitmap) extras.get("data");
                } else {
                    AssetFileDescriptor fd = null;
                    try {
                        fd = getDialog().getContext().getContentResolver().openAssetFileDescriptor(data.getData(), "r");
                    } catch (FileNotFoundException pE) {
                        pE.printStackTrace();
                    }
                    bmp = BitmapFactory.decodeFileDescriptor(fd != null ? fd.getFileDescriptor() : null);
                }
                try {
                    FileOutputStream out = new FileOutputStream(new File(mOutputFileUri.getPath()));
                    if (bmp != null) {
                        bmp.compress(Bitmap.CompressFormat.JPEG, 100, out);
                    }
                    out.flush();
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                imageView.setImageBitmap(bmp);
            }
        }
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
        void onFragmentInteraction(Uri uri);
    }
}
