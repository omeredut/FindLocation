package com.example.edut.qrscanner;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.model.LatLng;

import static com.example.edut.qrscanner.MainActivity.HIGHT_INDEX;
import static com.example.edut.qrscanner.MainActivity.editorLocations;
import static com.example.edut.qrscanner.MainActivity.sharedPreferencesLocations;

/**
 * Created by Edut on 25/12/2016.
 */
public class SaveLocationFragment extends DialogFragment {


    public static final String LOCATION = "LOCATION";
    /*SharedPreferences sharedPreferencesLocations = getPreferences(Context.MODE_PRIVATE);
    SharedPreferences.Editor editorLocations = sharedPreferencesLocations.edit();*/
    String locationToSave;
    int index;

    public static SaveLocationFragment newInstanceSaveFragment(LatLng myLocation){
        double lat = myLocation.latitude;
        double lng = myLocation.longitude;
        String location = String.valueOf(lat) + ", " + String.valueOf(lng);
        SaveLocationFragment saveLocationFragment = new SaveLocationFragment();

        Bundle args = new Bundle();
        args.putString(LOCATION, location);
        saveLocationFragment.setArguments(args);

        return saveLocationFragment;
    }




    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        locationToSave = getArguments().getString(LOCATION);
        index = sharedPreferencesLocations.getInt(HIGHT_INDEX, 0);
        //location = savedInstanceState.getString(LOCATION);
        AlertDialog.Builder builderDialog = new AlertDialog.Builder(getActivity());
        builderDialog.setMessage("Are you want to save this location?");
        builderDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });
        builderDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //TODO: save location in database
                editorLocations.putString(String.valueOf(index), locationToSave);
                index++;
                editorLocations.putInt(HIGHT_INDEX, index);
                editorLocations.commit();
                System.out.println("index: " + index + " the location: " + locationToSave);
            }
        });
        return builderDialog.create();
    }
}
