import java.util.Date;

/**
 * Created by Robin on 03/04/2015.
 */
public class Utiliser {

    private Date dateDepart;
    private Date dateArivee;

    public Utiliser(Date dateDepart, Date dateArivee) {
        this.dateDepart = dateDepart;
        this.dateArivee = dateArivee;
    }

    public Date getDateDepart() {
        return dateDepart;
    }

    public void setDateDepart(Date dateDepart) {
        this.dateDepart = dateDepart;
    }

    public Date getDateArivee() {
        return dateArivee;
    }

    public void setDateArivee(Date dateArivee) {
        this.dateArivee = dateArivee;
    }
}
