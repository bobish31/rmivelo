import java.util.ArrayList;

/**
 * Created by Robin on 03/04/2015.
 */
public class Station {

    private String identifiantStation;
    private int capacite;
    private double latitude;
    private double longitude;
    private int nbDepots;
    private int nbRetraits;

    private ArrayList<String> listeVeloDispo;

    public Station(String identifiantStation, int capacite, double latitude, double longitude, int nbDepots, int nbRetraits) {
        this.identifiantStation = identifiantStation;
        this.capacite = capacite;
        this.latitude = latitude;
        this.longitude = longitude;
        this.nbDepots = nbDepots;
        this.nbRetraits = nbRetraits;
        listeVeloDispo = new ArrayList<>();
    }

    public String getIdentifiantStation() {
        return identifiantStation;
    }

    public void setIdentifiantStation(String identifiantStation) {
        this.identifiantStation = identifiantStation;
    }

    public int getCapacite() {
        return capacite;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
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

    public void deposerVelo(String identifiantVelo) {
        nbDepots++;
        listeVeloDispo.add(identifiantVelo);
    }

    public void retirerVelo(String identifiantVelo) {
        nbRetraits++;
        listeVeloDispo.remove(identifiantVelo);
    }
}
