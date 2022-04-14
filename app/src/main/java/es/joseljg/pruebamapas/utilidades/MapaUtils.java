package es.joseljg.pruebamapas.utilidades;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import es.joseljg.pruebamapas.clases.Sitio;

public class MapaUtils {

    public static void addMarkes(GoogleMap googleMap, ArrayList<Sitio> sitios)
    {
        for(Sitio s: sitios)
        {
            addMarker(googleMap,s);
        }
    }

    public static void addMarker(GoogleMap googleMap, Sitio sitio)
    {
        LatLng sitio1 = new LatLng(sitio.getLatitud(),sitio.getLongitud());
        googleMap.addMarker(new MarkerOptions().position(sitio1).title(sitio.getNombreSitio()));
      //  googleMap.moveCamera(CameraUpdateFactory.newLatLng(sitio1));
    }
}
