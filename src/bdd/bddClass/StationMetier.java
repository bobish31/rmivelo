package bdd.bddClass;

import java.util.ArrayList;

/**
 * Created by Robin on 03/04/2015.
 */
public class StationMetier {

    private int identifiantStation;
    private int capacite;
    private int nbVelosDispos;
    private double latitude;
    private double longitude;
    private int nbDepots;
    private int nbRetraits;

    private ArrayList<Integer> listeVeloDispo;

    public StationMetier(int identifiantStation, int capacite,int nbVelosDispos, double latitude, double longitude, int nbDepots, int nbRetraits) {
        this.identifiantStation = identifiantStation;
        this.capacite = capacite;
        this.nbVelosDispos = nbVelosDispos;
        this.latitude = latitude;
        this.longitude = longitude;
        this.nbDepots = nbDepots;
        this.nbRetraits = nbRetraits;
        listeVeloDispo = new ArrayList<>();
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

    public ArrayList<Integer> getListeVeloDispo() {
        return listeVeloDispo;
    }

    public void setListeVeloDispo(ArrayList<Integer> listeVeloDispo) {
        this.listeVeloDispo = listeVeloDispo;
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

    public void deposerVelo(int identifiantVelo) {
        listeVeloDispo.add(identifiantVelo);
    }

    public void retirerVelo(int identifiantVelo) {
        listeVeloDispo.remove(identifiantVelo);
    }

    public void incrementerNbVelosDispos() {
        nbVelosDispos++;
    }

    public void decrementerNbVelosDispos() {
        nbVelosDispos--;
    }

    public void incrementerNbDepots() {
        nbDepots++;
    }

    public void decrementerNbRetraits() {
        nbRetraits--;
    }
}
