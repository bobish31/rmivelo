package bdd.bddClass;

import java.util.Date;

/**
 * Created by David on 22/04/2015.
 */
public class UtiliserMetier {

    private Date dateRetrait;
    private Date dateDepot;
    private int idUtilisation;

    public UtiliserMetier(Date dateRetrait, Date dateDepot) {
        this.dateRetrait = dateRetrait;
        this.dateDepot = dateDepot;
    }

    public Date getDateDepart() {
        return dateRetrait;
    }

    public void setDateDepart(Date dateDepart) {
        this.dateRetrait = dateDepart;
    }

    public Date getDateArivee() {
        return dateDepot;
    }

    public void setDateArivee(Date dateArivee) {
        this.dateDepot = dateArivee;
    }
}