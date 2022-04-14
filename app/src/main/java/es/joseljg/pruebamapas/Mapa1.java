package es.joseljg.pruebamapas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import es.joseljg.pruebamapas.clases.Sitio;
import es.joseljg.pruebamapas.utilidades.MapaUtils;
import es.joseljg.pruebamapas.utilidades.PermissionUtils;


public class Mapa1 extends AppCompatActivity  implements GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private boolean permissionDenied = false;
    private GoogleMap mMap;
    ArrayList<Sitio> sitiosMapa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa1);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        /*
        SupportMapFragment mapFragment = SupportMapFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.my_container, R.id.map)
                .commit();
                */
      // obtengo los sitio del mapa
        sitiosMapa = new ArrayList<Sitio>();
        sitiosMapa.add(new Sitio("colegio Juan Alonso Rivas", 37.373286299317584, -3.7117379557998453));
        sitiosMapa.add(new Sitio("ayuntamiento colomera", 37.36910317654226, -3.712712709694243));
    }

    // METODOS PARA LOS MAPAS
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
          //  googleMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));

        mMap = googleMap;

        /*
        // Ejemplo para posicionar mapa en seseña
        LatLng sydney = new LatLng(40.1044, -3.69742);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Seseña"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
         */

        // Ejemplo para posicionar mapa en mi ubicación actual
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        enableMyLocation();
        
        //zoom hasta mi posición

        LatLng destino = new LatLng(37.36910317654226, -3.712712709694243);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom( destino, 15));
        MapaUtils.addMarkes(mMap,sitiosMapa);

    }

    // METODOS PARA HABILITAR LA LOCALIZACION
    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (mMap != null) {
                mMap.setMyLocationEnabled(true);
            }
        } else {
            // Permission to access the location is missing. Show rationale and request permission
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        // al darle a mi ubicación actual qué hago
        Toast.makeText(this, "Mi localización actual:\n" + location, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults, Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Permission was denied. Display an error message
            // Display the missing permission error dialog when the fragments resume.
            permissionDenied = true;
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (permissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            permissionDenied = false;
        }
    }

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    // METODOS DEL MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mapa1_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.ir_mapa1_atras:
                finish();
                return true;
            case R.id.ir_mapa1_home:
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.ir_mapa1_localizacion:
                // codigo para guiar a la localizacion
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}