import bdd.BDDConnecteur;
import bdd.DAO;
import bdd.bddClass.StationMetier;
import bdd.bddClass.UtilisateurMetier;
import bdd.bddClass.VeloMetier;
import bdd.bddDAO.StationDAO;
import bdd.bddDAO.UtilisateurDAO;
import bdd.bddDAO.VeloDAO;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.util.*;

/**
 * Created by Robin on 03/04/2015.
 */
public class ServeurGeneralImpl extends UnicastRemoteObject implements ServeurGeneral {


    private HashMap<Integer, UtilisateurMetier> mapUtilisateurs;
    private HashMap<Integer, VeloMetier> mapVelos;
    private HashMap<Integer, StationMetier> mapStations;

    private VeloDAO veldao;
    private DAO<StationMetier> stationdao;
    private UtilisateurDAO utilisateurdao;

    public ServeurGeneralImpl() throws RemoteException {

        // Déclaration des listes
        mapUtilisateurs = new HashMap<>();
        mapVelos = new HashMap<>();
        mapStations = new HashMap<>();

        // on lance la bdd
        Connection connect = BDDConnecteur.getInstance();

        if (connect != null) {
            System.out.println("Connection BDD OK");

            // Création des variables pour la bdd
            veldao = new VeloDAO();
            stationdao = new StationDAO();
            utilisateurdao = new UtilisateurDAO();

        } else {
            System.out.println("Erreur Connection BDD");
        }
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
            System.out.println("Serveur Lancé");

            // CHARGEMENT BDD
            System.out.println("Chargement bdd en cours ... \n");
            obj.chargementListeBdd();
            System.out.println("Chargement bdd ok\n");

            System.out.println("En attente d'opération ... ");


        }
        catch (RemoteException | MalformedURLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void  chargementListeBdd () throws RemoteException {

        // --- Utilisateur --- //
        mapUtilisateurs = utilisateurdao.getInstancesByMap();
        afficherContenuMapUtilisateurs();

         // --- Velo --- //
        mapVelos = veldao.getInstancesByMap();
        afficherContenuMapVelos();

        // --- Station --- //
        mapStations = stationdao.getInstancesByMap();
        afficherContenuMapStations();

    }

    private void afficherContenuMapUtilisateurs() {
        System.out.println("Liste Utilisateurs : " + mapUtilisateurs.size() + "\n");

        for (UtilisateurMetier utilisateur : mapUtilisateurs.values()) {
            System.out.println(utilisateur.toString());
        }
    }

    private void afficherContenuMapVelos() {
        System.out.println("Liste Velos : " + mapVelos.size() + "\n");

        for (VeloMetier velo : mapVelos.values()) {
            System.out.println(velo.toString());
        }
    }

    private void afficherContenuMapStations() {
        System.out.println("Liste Stations : " + mapStations.size() + "\n");

        for (StationMetier station : mapStations.values()) {
            System.out.println(station.toString());
        }
    }

    @Override
    public String genererUtilisateur() throws RemoteException {

        // On récupére le plus grand numero d'utilisateur de la map correspondante
        int numero = Collections.max(mapUtilisateurs.keySet());

        // On créé le numero + 1
        numero++;

        // On génére un code aléatoire de 4 chiffres pour le code [BUG CA FAIT QUE 3 CHIFFRES]
        Random rand = new Random();
        int code = rand.nextInt((9999 - 0) + 1) + 0;

        // On stocke le nouvel utilisateur selon la stratégie de stockage choisie
        UtilisateurMetier u =  new UtilisateurMetier(numero, code);

        // on ajoute dans la map locale
        mapUtilisateurs.put(numero, u);

        // on ajoute en bdd
        utilisateurdao.create(u);

        // Création de la string
        String resultat = u.getNumero() + "/" + u.getCode();

        // On retourne l'utilisateur
        return resultat;
    }

    @Override
    public void deposerVelo(int identifiantBorneUtilisateur, int numero, int identifiantVelo, Date heureDepot) throws RemoteException {

        // TODO : ne pas avoir le numéro de l'utilisateur dans le dépôt, on doit pouvoir le retrouver dans la relation Utiliser !
        // TODO : Ajouter l'heure de dépôt pour la BD en créant la relation Utiliser correspondante --> modifier la structure de la relation Utiliser pour avoir heure de depot à null

        // Changement du statut du vélo, affectation de la station dans la bdd
        VeloMetier vel = veldao.find(identifiantVelo);
        StationMetier st = stationdao.find(identifiantBorneUtilisateur);
        veldao.update2(vel, st);


        // Changement des capacités de la station concernée
        st.setCapacite(st.getCapacite() - 1);

        // Gérer les nombres de dépôts de vélo dans la station
        st.incrementerNbDepots();

        //maj dans la base
        stationdao.update(st);

    }

    @Override
    public void retirerVelo(int identifiantBorneUtilisateur, int numero, int identifiantVelo, Date heureRetrait) throws RemoteException {

        // TODO : créer la relation Utiliser correspondante avec l'heure de retrait (l'heure de depot reste à null, elle sera mise dans deposer)

        // Changement du statut du vélo => on enleve la clé étrangère
        VeloMetier vel=veldao.find(identifiantVelo);
        StationMetier st=stationdao.find(identifiantBorneUtilisateur);
        veldao.update2(vel,null);

        // Changement des capacités de la station concernée
        st.setCapacite(st.getCapacite() + 1);

        // Gérer les nombres de retraits de vélo dans la station
        st.incrementerNbRetraits();

        //Maj dans la bdd
        stationdao.update(st);

    }

    @Override
    public String[] obtenirBornesVoisines(int identifiantBorneUtilisateur) throws RemoteException {


        return new String[0];
    }

    @Override
    public void creerVelo(int identifiantVelo, boolean operationnel) throws RemoteException {

        veldao.create(new VeloMetier(identifiantVelo,operationnel));
    }

    @Override
    public boolean authentifierUtilisateur(int numero, int code) throws RemoteException {

        UtilisateurMetier user = mapUtilisateurs.get(numero);
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
