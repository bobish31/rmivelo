import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;

/**
 * Created by Robin on 03/04/2015.
 */


public interface ServeurGeneral extends Remote {

    // Toutes les m�thodes doivent --> throws java.rmi.RemoteException

    public int[] genererUtilisateur() throws java.rmi.RemoteException;

    public void deposerVelo(int identifiantBorneUtilisateur, int numero, int identifiantVelo, Date heureDepot) throws java.rmi.RemoteException;

    public void retirerVelo(int identifiantBorneUtilisateur, int numero, int identifiantVelo, Date heureRetrait) throws java.rmi.RemoteException;

    public String[] obtenirBornesVoisines(int identifiantBorneUtilisateur) throws java.rmi.RemoteException;

    public void creerVelo(int identifiantVelo, boolean operationnel, boolean enCirculation) throws java.rmi.RemoteException;

    public boolean authentifierUtilisateur(int numero, int code) throws java.rmi.RemoteException;

    public String[] imprimerRecuUtilisateur() throws java.rmi.RemoteException;

    public void notifier(int idStation) throws java.rmi.RemoteException;

    public boolean connexionOkBDD() throws RemoteException;
}
