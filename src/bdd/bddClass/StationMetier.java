package bdd.bddClass;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Robin on 03/04/2015.
 */
public class StationMetier {

    private int identifiantStation;
    private int capacite;
    private int nbRetraits;
    private int nbDepots;
    private double latitude;
    private double longitude;

    public static final int IDENTIFIANT_STATION_NULL = -1;

    private ArrayList<Integer> listeVelos;

    public StationMetier () {}

    public StationMetier(int identifiantStation, int capacite, int nbRetraits, int nbDepots, double latitude, double longitude) {
        this.identifiantStation = identifiantStation;
        this.capacite = capacite;
        this.nbRetraits = nbRetraits;
        this.nbDepots = nbDepots;
        this.latitude = latitude;
        this.longitude = longitude;

        listeVelos = new ArrayList<>();
    }

    public void deposerVelo(int identifiantVelo) {
        listeVelos.add(identifiantVelo);
    }

    public void retirerVelo(int identifiantVelo) {
        listeVelos.remove(identifiantVelo);
    }

    public void incrementerNbDepots() {
        nbDepots++;
    }

    public void incrementerNbRetraits() {
        nbRetraits++;
    }

    public int getIdentifiantStation() {
        return identifiantStation;
    }

    public void setIdentifiantStation(int identifiantStation) {
        this.identifiantStation = identifiantStation;
    }

    public int getCapacite() {
        return capacite;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    public int getNbDepots() {
        return nbDepots;
    }

    public void setNbDepots(int nbDepots) {
        this.nbDepots = nbDepots;
    }

    public int getNbRetraits() {
        return nbRetraits;
    }

    public void setNbRetraits(int nbRetraits) {
        this.nbRetraits = nbRetraits;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public ArrayList<Integer> getListeVelos() {
        return listeVelos;
    }

    public void setListeVelos(ArrayList<Integer> listeVelos) {
        this.listeVelos = listeVelos;
    }

    @Override
    public String toString() {
        return "StationMetier{" +
                "identifiantStation=" + identifiantStation +
                ", capacite=" + capacite +
                ", nbDepots=" + nbDepots +
                ", nbRetraits=" + nbRetraits +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", listeVelos=" + listeVelos +
                '}';
    }

    public static double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        // Convertir en Kilometres
        dist = dist * 1.609344;
        return dist;
    }

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

}
