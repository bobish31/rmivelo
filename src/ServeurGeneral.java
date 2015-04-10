import java.rmi.Remote;
import java.util.Date;

/**
 * Created by Robin on 03/04/2015.
 */


public interface ServeurGeneral extends Remote {

    // Toutes les méthodes doivent --> throws java.rmi.RemoteException

    public String[] genererUtilisateur() throws java.rmi.RemoteException;

    public boolean deposerVelo(int identifiantBorneUtilisateur, int numero, int identifiantVelo, Date heureDepot) throws java.rmi.RemoteException;

    public boolean retirerVelo(int identifiantBorneUtilisateur, int numero, int identifiantVelo, Date heureRetrait) throws java.rmi.RemoteException;

    public String[] obtenirBornesVoisines(int identifiantBorneUtilisateur) throws java.rmi.RemoteException;

    public boolean creerVelo(int identifiantVelo, boolean operationnel, boolean enCirculation) throws java.rmi.RemoteException;

    public boolean authentifierUtilisateur(int numero, int code) throws java.rmi.RemoteException;

    public String[] imprimerRecuUtilisateur() throws java.rmi.RemoteException;

    public void notifier(int idStation) throws java.rmi.RemoteException;
}
