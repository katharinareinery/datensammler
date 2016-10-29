package com.example.gps;

import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;


public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener
{
    //Variable, um die Position auszulesen
    GoogleApiClient mGoogleApiClient;
    //Variable für die Positionsdaten
    Location mLastLocation;
    //Variablen, zur Anzeige der Daten
    TextView mLatitudeText;
    TextView mLongitudeText;

    //Wird beim Start der App aufgerufen
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //ordnet View zu
        setContentView(R.layout.activity_main);

        //Definieren Textausgabefelder
        mLatitudeText = (TextView) findViewById(R.id.textBreitenGrad);
        mLongitudeText = (TextView) findViewById(R.id.textLaengenGrad);

        //GoogleAPICLient erstellen
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    //Verbindung GoogleAPIClient starten
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    //Verbindung GoogleAPIClient stoppen
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    //Bei erfolgreicher Verbindung zur Google API, wird Verbindung zum Client aufgebaut
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        //Berechtigungsabfrage
        ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.
                                            ACCESS_COARSE_LOCATION},1);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        //Daten verwerten und anzeigen
        if (mLastLocation != null)
        {
            mLatitudeText.setText("Breitengrad: "+String.valueOf(mLastLocation.getLatitude()));
            mLongitudeText.setText("Längengrad: "+String.valueOf(mLastLocation.getLongitude()));
        }
    }

    @Override
    public void onConnectionSuspended(int i) {}

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {}

}



