package com.example.edut.qrscanner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import static com.example.edut.qrscanner.MainActivity.HIGHT_INDEX;
import static com.example.edut.qrscanner.MainActivity.SAVED;
import static com.example.edut.qrscanner.MainActivity.SHOW_LOCATION;
import static com.example.edut.qrscanner.MainActivity.TYPE;
import static com.example.edut.qrscanner.MainActivity.sharedPreferencesLocations;

public class LocationListActivity extends Activity {

    public static final String NULL = "NULL";
    private String[] locationsList;
    private String location;
    ListView listViewLocations;
    ArrayAdapter listAdapter;
    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_list);

        index = sharedPreferencesLocations.getInt(HIGHT_INDEX, 0);
        listViewLocations = (ListView) findViewById(R.id.list_view_locations);
        locationsList = new String[index];

        for (int i = 0; i < index; i++) {
            location = sharedPreferencesLocations.getString(String.valueOf(i), NULL);
            locationsList[i] = location;
        }
        listAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, locationsList);
        listViewLocations.setAdapter(listAdapter);
        listViewLocations.setOnItemClickListener(showLocation);
        listViewLocations.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(LocationListActivity.this, "long item click", Toast.LENGTH_SHORT).show();
                //TODO: option to remove location
                return false;
            }
        });
    }


    AdapterView.OnItemClickListener showLocation = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intentShowSaveLocation = new Intent(LocationListActivity.this, MapsActivity.class);
            String savedLocation = locationsList[position];
            intentShowSaveLocation.putExtra(TYPE, SAVED);
            intentShowSaveLocation.putExtra(SHOW_LOCATION, savedLocation);
            startActivity(intentShowSaveLocation);
        }
    };




}
