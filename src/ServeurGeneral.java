import bdd.bddClass.StationMetier;
import bdd.bddClass.UtilisateurMetier;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by Robin on 03/04/2015.
 */


public interface ServeurGeneral extends Remote {

    // Toutes les m�thodes doivent --> throws java.rmi.RemoteException

    public String genererUtilisateur() throws java.rmi.RemoteException;

    public String deposerVelo(int identifiantBorneUtilisateur, int identifiantVelo, Timestamp heureDepot) throws java.rmi.RemoteException, VeloInconnuException;

    public void retirerVelo(int identifiantBorneUtilisateur, int numero, int identifiantVelo, Timestamp heureRetrait) throws java.rmi.RemoteException;

    public TreeMap<Double, Integer> obtenirBornesVoisines(int identifiantBorneUtilisateur) throws java.rmi.RemoteException;

    public  void creerVelo(int identifiantVelo, boolean operationnel) throws java.rmi.RemoteException;

    public boolean authentifierUtilisateur(int numero, int code) throws java.rmi.RemoteException;

    public String imprimerRecuUtilisateur(int numero, Timestamp retrait, Timestamp depot) throws java.rmi.RemoteException;

    public void notifierVide(BorneTechnicien bt, int idStation) throws java.rmi.RemoteException;

    public void notifierPlein(BorneTechnicien bt, int idStation) throws java.rmi.RemoteException;

    public boolean connexionOkBDD() throws RemoteException;

    public void chargementListeBdd () throws RemoteException;

    public void affectationVeloStation (int identifiantVelo, int identificationStation) throws RemoteException;

    public ArrayList<Integer> obtenirVelosRattachesAUneStation(int identificationStation) throws RemoteException;

    public int obtenirVeloCorrespondantAuPretEnCours(int numero) throws RemoteException;

    public boolean aUnPretEnCours(int numero) throws RemoteException;

    public boolean velosDisposDansLaStation(int identifiantBorneUtilisateur) throws RemoteException;

    public boolean stationPleineDeVelos(int identifiantBorneUtilisateur) throws RemoteException;

    public String connaitreSituationVelo(int identifiantVelo) throws RemoteException;

    public boolean initBorneTechnicien() throws RemoteException;

    public String obtenirInfosBorneVoisine(int identifiantStation) throws RemoteException;
}
