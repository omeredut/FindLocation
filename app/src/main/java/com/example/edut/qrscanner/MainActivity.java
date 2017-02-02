package com.example.edut.qrscanner;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    public static final int QR_SCANNER = 1;
    public static final int GPS_MAPS = 2;
    public static final int SAVED = 3;
    public static final String TYPE = "type";

    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    public static final String SHOW_LOCATION = "QR_RESULT";
    public static final String SCAN_MODE = "SCAN_MODE";
    public static final String QR_CODE_MODE = "QR_CODE_MODE";
    public static final String SCAN_RESULT = "SCAN_RESULT";
    public static final String HIGHT_INDEX = "HIGHT_INDEX";
    public static final int DEF_INDEX = 0;


    public static SharedPreferences sharedPreferencesLocations;
    public static SharedPreferences.Editor editorLocations;






    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init the sharedPreferences.
        sharedPreferencesLocations = getPreferences(Context.MODE_PRIVATE);
        editorLocations = sharedPreferencesLocations.edit();

    }


    //GPS part
    public void btnGPSLocation(View view) {
        Intent intentGPS = new Intent(this, MapsActivity.class);
        intentGPS.putExtra(TYPE, GPS_MAPS);
        startActivity(intentGPS);
    }


    //QR scanner part
    public void btnQr(View view) {
        openQrScanner();
        /*Intent intentQr = new Intent(this, QrScannerActivity.class);
        intentQr.putExtra(TYPE, QR_SCANNER);
        startActivity(intentQr);*/
    }


    private static AlertDialog showDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(message);
        downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    act.startActivity(intent);
                } catch (ActivityNotFoundException anfe) {

                }
            }
        });
        downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        return downloadDialog.show();
    }



    private void openQrScanner() {

        try {

            Intent intentQr = new Intent(ACTION_SCAN);
            intentQr.putExtra(SCAN_MODE, QR_CODE_MODE); // "PRODUCT_MODE for bar codes

            startActivityForResult(intentQr, 0);

        } catch (Exception e) {

            Uri marketUri = Uri.parse("market://details?id=com.google.zxing.client.android");
            Intent marketIntent = new Intent(Intent.ACTION_VIEW,marketUri);
            startActivity(marketIntent);

        }
    }







    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {

            if (resultCode == RESULT_OK) {
                //get string result of qr scanner
                final String contents = data.getStringExtra(SCAN_RESULT);

                //open maps activity with data location
                Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //resultLocation.setText("your location is: " + contents);
                        Intent intentMaps = new Intent(MainActivity.this, MapsActivity.class);
                        intentMaps.putExtra(TYPE, QR_SCANNER);
                        intentMaps.putExtra(SHOW_LOCATION, contents);
                        startActivity(intentMaps);
                    }
                });
            }
            if(resultCode == RESULT_CANCELED){
                //handle cancel
                return;
            }
        }

    }

    public void btnListLocations(View view) {
        Intent intentListLocations = new Intent(this, LocationListActivity.class);
        startActivity(intentListLocations);
    }
}
