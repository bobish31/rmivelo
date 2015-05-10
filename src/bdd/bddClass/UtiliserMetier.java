package bdd.bddClass;


import java.sql.Timestamp;

/**
 * Created by David on 22/04/2015.
 */
public class UtiliserMetier {

    private Timestamp dateRetrait;
    private Timestamp dateDepot;
    private int idUtilisation;

    public UtiliserMetier () {}

    public UtiliserMetier(int id,Timestamp dateRetrait, Timestamp dateDepot) {
        this.dateRetrait = dateRetrait;
        this.dateDepot = dateDepot;
        this.idUtilisation = id;
    }

    public int getIdUtilisation () {return idUtilisation;}

    public Timestamp getDateRetrait() {
        return dateRetrait;
    }

    public void setDateDepart(Timestamp dateDepart) {
        this.dateRetrait = dateDepart;
    }

    public Timestamp getDateDepot() {
        return dateDepot;
    }

    public void setDateArivee(Timestamp dateArivee) {
        this.dateDepot = dateArivee;
    }

    @Override
    public String toString() {
        return "UtiliserMetier [PRET] {" +
                "idUtilisation = " + idUtilisation +
                ", Depot =" + dateDepot +
                ", Retrait =" + dateRetrait +
                '}';
    }
}