package bdd.bddClass;


import java.sql.Timestamp;

/**
 * Created by David on 22/04/2015.
 */
public class UtiliserMetier {

    private Timestamp dateRetrait;
    private Timestamp dateDepot;
    private int idUtilisation;
    private int numero;
    private int identifiantVelo;

    public UtiliserMetier () {}

    public UtiliserMetier(int id, int numero, int identifiantVelo, Timestamp dateRetrait, Timestamp dateDepot) {
        this.dateRetrait = dateRetrait;
        this.dateDepot = dateDepot;
        this.idUtilisation = id;
        this.numero = numero;
        this.identifiantVelo = identifiantVelo;
    }

    public int getIdUtilisation () {return idUtilisation;}

    public Timestamp getDateRetrait() {
        return dateRetrait;
    }

    public int getNumero() {
        return numero;
    }

    public int getIdentifiantVelo() {
        return identifiantVelo;
    }

    public Timestamp getDateDepot() {
        return dateDepot;
    }

    public void setDateArivee(Timestamp dateArivee) {
        this.dateDepot = dateArivee;
    }

    @Override
    public String toString() {
        return "UtiliserMetier{" +
                "dateRetrait=" + dateRetrait +
                ", dateDepot=" + dateDepot +
                ", idUtilisation=" + idUtilisation +
                ", numero=" + numero +
                ", identifiantVelo=" + identifiantVelo +
                '}';
    }
}