/**
 * Created by Robin on 03/06/2015.
 */
public class VeloInconnuException extends Exception {

    public int identifiantBorneUtilisateur;
    public int identifiantVelo;

    public VeloInconnuException(int identifiantBorneUtilisateur, int identifiantVelo) {
        this.identifiantBorneUtilisateur = identifiantBorneUtilisateur;
        this.identifiantVelo = identifiantVelo;
    }

    public String toString() {
        return "Le velo que vous souhaiter deposer n'est pas correct.\n" +
                "Informations complementaires : borne concernee = " + identifiantBorneUtilisateur + " / velo concerne = " + identifiantVelo;
    }
}
