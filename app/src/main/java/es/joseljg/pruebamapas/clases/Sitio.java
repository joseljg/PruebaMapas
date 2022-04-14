package es.joseljg.pruebamapas.clases;

import java.util.Objects;

public class Sitio {
    private String nombreSitio;
    private double latitud;
    private double longitud;

    public Sitio(String nombreSitio, double latitud, double longitud) {
        this.nombreSitio = nombreSitio;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public String getNombreSitio() {
        return nombreSitio;
    }

    public void setNombreSitio(String nombreSitio) {
        this.nombreSitio = nombreSitio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sitio)) return false;
        Sitio sitio = (Sitio) o;
        return nombreSitio.equals(sitio.nombreSitio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombreSitio);
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

}
