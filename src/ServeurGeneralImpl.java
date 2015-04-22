import bdd.BDDConnecteur;
import bdd.bddClass.StationMetier;
import bdd.bddClass.UtilisateurMetier;
import bdd.bddClass.VeloMetier;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

/**
 * Created by Robin on 03/04/2015.
 */
public class ServeurGeneralImpl extends UnicastRemoteObject implements ServeurGeneral {


    private HashMap<Integer, UtilisateurMetier> listeUtilisateurs;
    private HashMap<Integer, VeloMetier> listeVelos;
    private HashMap<Integer, StationMetier> listeStations;

    public ServeurGeneralImpl() throws RemoteException {

        // Déclaration des listes
        listeUtilisateurs = new HashMap<>();
    }

    // Méthode qui lance le serveur
    public static void main(String[] args) {

        try {
            // Création du registre
            LocateRegistry.createRegistry(5588);

            // Création de l'objet
            ServeurGeneralImpl obj = new ServeurGeneralImpl();

            // Création de l'adresse URL
            String url = "rmi://127.0.0.1:5588/ServeurGeneral";

            // Enregistrement de l'adresse
            Naming.rebind(url, obj);

            // On confirme que tout est ok
            System.out.println ("Serveur Lancé");
        }
        catch (RemoteException | MalformedURLException e){
            e.printStackTrace();
        }
    }

    @Override
    public int[] genererUtilisateur() throws RemoteException {

        // On récupére le plus grand numero d'utilisateur de la map correspondante
        // int numero = Collections.max(listeUtilisateurs.keySet());
        int numero = 1;

        // On créé le numero + 1
        numero++;

        // On génére un code aléatoire de 4 chiffres pour le code
        Random rand = new Random();
        int code = rand.nextInt((9999 - 0) + 1) + 0;

        // On stocke le nouvel utilisateur selon la stratégie de stockage choisie
        // new Utilisateur(numero, code);

        // on ajoute dans la map locale
        listeUtilisateurs.put(numero, new UtilisateurMetier(numero,code));

        // On retourne le numero + code générés dans un tableau de Int
        int[] utilisateur = new int[2];
        utilisateur[0] = numero;
        utilisateur[1] = code;
        return utilisateur;
    }

    @Override
    public void deposerVelo(int identifiantBorneUtilisateur, int numero, int identifiantVelo, Date heureDepot) throws RemoteException {

        // Changement du statut du vélo
        listeVelos.get(identifiantVelo).setEnCirculation(false);

        // Changement des capacités de la station concernée
        listeStations.get(identifiantBorneUtilisateur).incrementerNbVelosDispos();

        // Gérer les nombres de dépôts de vélo dans la station
        listeStations.get(identifiantBorneUtilisateur).incrementerNbDepots();

    }

    @Override
    public void retirerVelo(int identifiantBorneUtilisateur, int numero, int identifiantVelo, Date heureRetrait) throws RemoteException {

        // Changement du statut du vélo
        listeVelos.get(identifiantVelo).setEnCirculation(true);

        // Changement des capacités de la station concernée
        listeStations.get(identifiantBorneUtilisateur).decrementerNbVelosDispos();

        // Gérer les nombres de retraits de vélo dans la station
        listeStations.get(identifiantBorneUtilisateur).decrementerNbRetraits();

    }

    @Override
    public String[] obtenirBornesVoisines(int identifiantBorneUtilisateur) throws RemoteException {


        return new String[0];
    }

    @Override
    public void creerVelo(int identifiantVelo, boolean operationnel, boolean enCirculation) throws RemoteException {

        // On récupére le plus grand numero de velo de la map correspondante
        int numero = Collections.max(listeVelos.keySet());

        // On créé le numero + 1
        numero++;

        // On stocke le nouvel velo selon la stratégie de stockage choisie
        // new Velo(numero)
    }

    @Override
    public boolean authentifierUtilisateur(int numero, int code) throws RemoteException {

        UtilisateurMetier user = listeUtilisateurs.get(numero);
        if (user != null) {
            if (user.getCode() == code) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String[] imprimerRecuUtilisateur() throws RemoteException {
        return new String[0];
    }

    @Override
    public void notifier(int idStation) throws RemoteException {

    }

    @Override
    public boolean connexionOkBDD() throws RemoteException {
         return (BDDConnecteur.getInstance() != null);
    }
}
