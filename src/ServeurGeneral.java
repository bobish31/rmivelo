import bdd.bddClass.StationMetier;
import bdd.bddClass.UtilisateurMetier;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Robin on 03/04/2015.
 */


public interface ServeurGeneral extends Remote {

    // Toutes les m�thodes doivent --> throws java.rmi.RemoteException

    public String genererUtilisateur() throws java.rmi.RemoteException;

    public String deposerVelo(int identifiantBorneUtilisateur, int identifiantVelo, Timestamp heureDepot) throws java.rmi.RemoteException;

    public void retirerVelo(int identifiantBorneUtilisateur, int numero, int identifiantVelo, Timestamp heureRetrait) throws java.rmi.RemoteException;

    public HashMap<Integer, Double> obtenirBornesVoisines(int identifiantBorneUtilisateur) throws java.rmi.RemoteException;

    public  void creerVelo(int identifiantVelo, boolean operationnel) throws java.rmi.RemoteException;

    public boolean authentifierUtilisateur(int numero, int code) throws java.rmi.RemoteException;

    public String imprimerRecuUtilisateur(int numero, Timestamp retrait, Timestamp depot) throws java.rmi.RemoteException;

    public void notifier(int idStation) throws java.rmi.RemoteException;

    public boolean connexionOkBDD() throws RemoteException;

    public void chargementListeBdd () throws RemoteException;

    public void affectationVeloStation (int identifiantVelo, int identificationStation) throws RemoteException;
}
