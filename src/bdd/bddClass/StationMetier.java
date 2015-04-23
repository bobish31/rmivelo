package bdd.bddClass;

import java.util.ArrayList;

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

    private ArrayList<Integer> listeVelos;

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

    public void decrementerNbRetraits() {
        nbRetraits--;
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

}
