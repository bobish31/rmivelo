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


    private HashMap<Integer, UtilisateurMetier> listeUtilisateurs;
    private HashMap<Integer, VeloMetier> listeVelos;
    private HashMap<Integer, StationMetier> listeStations;

    private VeloDAO veldao;
    private DAO<StationMetier> stationdao;
    private UtilisateurDAO utilisateurdao;

    public ServeurGeneralImpl() throws RemoteException {

        // Déclaration des listes
        listeUtilisateurs = new HashMap<>();
        listeVelos = new HashMap<>();
        listeStations = new HashMap<>();

        // on lance la bdd
        Connection connect = BDDConnecteur.getInstance();

        if (connect != null) {
            System.out.println("Connection BDD OK");

            // Création des variables pour la bdd
            veldao=new VeloDAO();
            stationdao= new StationDAO();
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

        Set listKeys;
        Iterator it;

        // On créer une liste
        ArrayList <UtilisateurMetier> arrayUtilisateur = new ArrayList<>();
        ArrayList<VeloMetier> arrayVelo = new ArrayList<>();
        ArrayList<StationMetier> arrayStation = new ArrayList<>();

        // --- Utilisateur --- //
        arrayUtilisateur = utilisateurdao.getInstances();

        // On ajoute dans la map
        for (UtilisateurMetier u : arrayUtilisateur)
        {
            listeUtilisateurs.put(u.getNumero(),new UtilisateurMetier(u.getNumero(),u.getCode()));
        }

                System.out.println("Liste Utilisateurs : " + listeUtilisateurs.size() + "\n");


         listKeys = listeUtilisateurs.keySet();
         it = listKeys.iterator();
        while (it.hasNext()) {
            Object key = it.next();
            System.out.println(listeUtilisateurs.get(key).toString());
        }

        System.out.println();

         // --- Velo --- //
        arrayVelo = veldao.getInstances();

        // On ajoute dans la map
        for (VeloMetier v : arrayVelo)
        {
            listeVelos.put(v.getIdentifiantVelo(),new VeloMetier(v.getIdentifiantVelo(),v.isOperationnel()));
        }

        System.out.println("Liste Velo : " + listeVelos.size() + "\n");


        listKeys = listeVelos.keySet();
        it = listKeys.iterator();
        while (it.hasNext()) {
            Object key = it.next();
            System.out.println(listeVelos.get(key).toString());
        }

        System.out.println();

        // --- Station --- //
        arrayStation = stationdao.getInstances();

        // On ajoute dans la map
        for (StationMetier s : arrayStation)
        {
            listeStations.put(s.getIdentifiantStation(),new StationMetier(s.getIdentifiantStation(),s.getCapacite(),s.getNbRetraits(),s.getNbDepots(),s.getLatitude(),s.getLongitude()));
        }

        System.out.println("Liste Station : " + listeStations.size() + "\n");


        listKeys = listeStations.keySet();
         it = listKeys.iterator();
        while (it.hasNext()) {
            Object key = it.next();
            System.out.println(listeStations.get(key).toString());
        }

        System.out.println();

    }


    @Override
    public String genererUtilisateur() throws RemoteException {

        // On récupére le plus grand numero d'utilisateur de la map correspondante
        int numero = Collections.max(listeUtilisateurs.keySet());

        // On créé le numero + 1
        numero++;

        // On génére un code aléatoire de 4 chiffres pour le code [BUG CA FAIT QUE 3 CHIFFRES]
        Random rand = new Random();
        int code = rand.nextInt((9999 - 0) + 1) + 0;

        // On stocke le nouvel utilisateur selon la stratégie de stockage choisie
        UtilisateurMetier u =  new UtilisateurMetier(numero, code);

        // on ajoute dans la map locale
        listeUtilisateurs.put(numero, u);

        // on ajoute en bdd
        utilisateurdao.create(u);

        // Création de la string
        String resultat = u.getNumero() + "/" + u.getCode();

        // On retourne l'utilisateur
        return resultat;
    }

    @Override
    public void deposerVelo(int identifiantBorneUtilisateur, int numero, int identifiantVelo, Date heureDepot) throws RemoteException {

        // Changement du statut du vélo, affectation de la station dans la bdd

        VeloMetier vel=veldao.find(identifiantVelo);

        StationMetier st = stationdao.find(identifiantBorneUtilisateur);
        veldao.update2(vel, st);


        // Changement des capacités de la station concernée
        //TODO checker si ça vaut le coup de faire ça
        st.setCapacite(st.getCapacite()+1);

        // Gérer les nombres de dépôts de vélo dans la station
        st.incrementerNbDepots();

        //maj dans la base
        stationdao.update(st);

    }

    @Override
    public void retirerVelo(int identifiantBorneUtilisateur, int numero, int identifiantVelo, Date heureRetrait) throws RemoteException {

        // Changement du statut du vélo => on enleve la clé étrangère
        VeloMetier vel=veldao.find(identifiantVelo);

        veldao.update2(vel,null);

        // Changement des capacités de la station concernée
        StationMetier st=stationdao.find(identifiantBorneUtilisateur);
        st.setCapacite(st.getCapacite() - 1);


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
