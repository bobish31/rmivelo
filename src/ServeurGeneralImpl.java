import bdd.BDDConnecteur;
import bdd.DAO;
import bdd.bddClass.StationMetier;
import bdd.bddClass.UtilisateurMetier;
import bdd.bddClass.UtiliserMetier;
import bdd.bddClass.VeloMetier;
import bdd.bddDAO.StationDAO;
import bdd.bddDAO.UtilisateurDAO;
import bdd.bddDAO.UtiliserDAO;
import bdd.bddDAO.VeloDAO;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Robin on 03/04/2015.
 */
public class ServeurGeneralImpl extends UnicastRemoteObject implements ServeurGeneral {


    private HashMap<Integer, UtilisateurMetier> mapUtilisateurs;
    private HashMap<Integer, VeloMetier> mapVelos;
    private HashMap<Integer, StationMetier> mapStations;
    private HashMap<Integer,UtiliserMetier> mapPret;

    private VeloDAO veldao;
    private DAO<StationMetier> stationdao;
    private UtilisateurDAO utilisateurdao;
    private UtiliserDAO utiliserdao;
    private static BorneTechnicien  bornetech;

    public ServeurGeneralImpl() throws RemoteException {

        // Déclaration des listes
        mapUtilisateurs = new HashMap<>();
        mapVelos = new HashMap<>();
        mapStations = new HashMap<>();
        mapPret = new HashMap<>();

        // on lance la bdd
        Connection connect = BDDConnecteur.getInstance();

        if (connect != null) {
            System.out.println("Connection BDD OK");

            // Création des variables pour la bdd
            veldao = new VeloDAO();
            stationdao = new StationDAO();
            utilisateurdao = new UtilisateurDAO();
            utiliserdao = new UtiliserDAO();

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


            /*UtiliserDAO u = new UtiliserDAO();
            UtiliserMetier recu = u.find();
            obj.imprimerRecuUtilisateur(5,recu.getDateRetrait(),recu.getDateDepot());
            */


        }
        catch (RemoteException | MalformedURLException e){
            e.printStackTrace();
        }
    }

    // OK
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

        // On charge la liste des vélos présents dans chaque station
        for (StationMetier station : mapStations.values()) {
            // On récupère la liste des vélos pour chaque station
            station.setListeVelos(veldao.getVeloFromAStation(station.getIdentifiantStation()));
        }
        afficherContenuMapStations();

        // --- Pret --- //
        mapPret = utiliserdao.getInstancesByMap();
        afficherContenuMapPret();

    }

   private void afficherContenuMapUtilisateurs() {
        System.out.println("Liste Utilisateurs : " + mapUtilisateurs.size() + "\n");

        for (UtilisateurMetier utilisateur : mapUtilisateurs.values()) {
            System.out.println(utilisateur.toString());
        }
        System.out.println("\n");
    }

    private void afficherContenuMapVelos() {
        System.out.println("Liste Velos : " + mapVelos.size() + "\n");

        for (VeloMetier velo : mapVelos.values()) {
            System.out.println(velo.toString());
        }
        System.out.println("\n");
    }

    private void afficherContenuMapStations() {
        System.out.println("Liste Stations : " + mapStations.size() + "\n");

        for (StationMetier station : mapStations.values()) {
            System.out.println(station.toString());
        }
        System.out.println("\n");
    }

    private void afficherContenuMapPret() {
        System.out.println("Liste Pret : " + mapPret.size() + "\n");

        for (UtiliserMetier utiliser : mapPret.values()) {
            System.out.println(utiliser.toString());
        }
        System.out.println("\n");
    }

    // OK
    @Override
    public String genererUtilisateur() throws RemoteException {
        //par défault pas technicien
        // On récupére le plus grand numero d'utilisateur de la map correspondante
        int numero = Collections.max(mapUtilisateurs.keySet());

        // On créé le numero + 1
        numero++;

        // On génére un code aléatoire de 4 chiffres pour le code
        Random rand = new Random();
        int code = rand.nextInt(9999 - 1000)+1000;

        // On stocke le nouvel utilisateur selon la stratégie de stockage choisie
        UtilisateurMetier u =  new UtilisateurMetier(numero, code,false);

        // on ajoute dans la map locale
        mapUtilisateurs.put(numero, u);

        // on ajoute en bdd
        utilisateurdao.create(u);

        // Création de la string
        String resultat = u.getNumero() + "/" + u.getCode();

        // On retourne l'utilisateur
        return resultat;
    }

    // EN TEST
    @Override
    public String deposerVelo(int identifiantBorneUtilisateur, int identifiantVelo, Timestamp heureDepot) throws RemoteException, VeloInconnuException {

        // Changement du statut du vélo, affectation de la station dans la bdd
        UtiliserMetier util = utiliserdao.find(utiliserdao.obtenirIDUtiliser(identifiantVelo));

        // Si le prêt retourné par le vélo concerné n'existe pas ou bien que le vélo a déjà été déposé
        if (util.getIdUtilisation() == 0) {
            throw new VeloInconnuException(identifiantBorneUtilisateur, identifiantVelo);

        } else {
            StationMetier st = stationdao.find(identifiantBorneUtilisateur);
            VeloMetier vel = veldao.find(identifiantVelo);
            vel.setIdentifiantStation(st.getIdentifiantStation());
            veldao.update(vel);

            // Changement des capacités de la station concernée
            st.setNbVelosDispos(st.getNbVelosDispos() + 1);

            // Gérer les nombres de dépôts de vélo dans la station
            st.incrementerNbDepots();
            // st.deposerVelo(identifiantVelo);

            //maj dans la base
            stationdao.update(st);

            System.out.println("ID PRET :" + util.getIdUtilisation());


            util.setDateArivee(heureDepot);

            // On créé la relation utiliser
            utiliserdao.update(util);

            // On imprime le recu
            String resultat = imprimerRecuUtilisateur(util.getIdUtilisation(), util.getDateRetrait(), util.getDateDepot());

            System.out.println("DEPOT - " + util.getDateDepot());
            System.out.println("RETRAIT - " + util.getDateRetrait());

            // Gestion de la notification en cas de saturation
            if(st.getCapacite() - st.getNbVelosDispos() < 3){
                notifierPlein(bornetech, st.getIdentifiantStation());
                st.setNbPlein(st.getNbPlein() + 1);
                //maj dans la base
                stationdao.update(st);
            }

            return  resultat;
        }
    }

    // EN TEST
    @Override
    public void retirerVelo(int identifiantBorneUtilisateur, int numero, int identifiantVelo, Timestamp heureRetrait) throws RemoteException {

        // TODO : Il faut rajouter une map comme pour le reste, récupérer la dernire transaction et faire un +1 pour l'id

        VeloMetier vel = veldao.find(identifiantVelo);

        // Vérifier si le vélo est bien dans la station désignée
        if (vel.getIdentifiantStation() != identifiantBorneUtilisateur) {
            System.out.println("---- ERREUR : Le vélo n'est pas présent sur la borne choisie");

        } else {

            // Changement du statut du vélo => on enleve la clé étrangère
            StationMetier st=stationdao.find(identifiantBorneUtilisateur);
            vel.setIdentifiantStation(StationMetier.IDENTIFIANT_STATION_NULL);
            veldao.update(vel);

            // Changement des capacités de la station concernée
            st.setNbVelosDispos(st.getNbVelosDispos() - 1);

            // Gérer les nombres de retraits de vélo dans la station
            st.incrementerNbRetraits();
            // st.retirerVelo(identifiantVelo);

            //Maj dans la bdd de la station
            stationdao.update(st);

            // On recupere l'utilisateur dans la bdd
            UtilisateurMetier u = utilisateurdao.find(numero);

            // On récupère l'id du dernier pret
            int idpret;

            Set<Integer> keyset = mapPret.keySet();
            if (keyset.isEmpty()) {
                idpret = 1;
            } else {
                idpret = Collections.max(mapPret.keySet()) + 1;
            }

            // On créer l'objet utiliser
            UtiliserMetier util = new UtiliserMetier(idpret, u.getNumero(), vel.getIdentifiantVelo(), heureRetrait, null);

            // On créé la relation utiliser
            utiliserdao.create(util);

            // Gestion de la notification en cas de pénurie
            if(st.getNbVelosDispos() < 3){
                //si on a une capcité inférieure à 3 vélo on notifie un technicien
                notifierVide(bornetech, st.getIdentifiantStation());
                st.setNbPenurie(st.getNbPenurie() + 1);
                //maj dans la base
                stationdao.update(st);
            }

            System.out.println("Velo retire");
        }
    }

    // A FAIRE
    @Override
    public TreeMap<Double, Integer> obtenirBornesVoisines(int identifiantBorneUtilisateur) throws RemoteException {
        // obtenir les coordonnées de la station consultée
        StationMetier stationSource = stationdao.find(identifiantBorneUtilisateur);

        // obtenir les coordonnées de toutes les stations
        ArrayList<StationMetier> toutesLesStations = stationdao.getInstancesByList();

        HashMap<Double, Integer> stationsParCoordonnes = new HashMap<>();

        // Je mets toutes les distances dans la map
        for (StationMetier station : toutesLesStations) {
            if (station.getIdentifiantStation() != identifiantBorneUtilisateur) {
                stationsParCoordonnes.put(StationMetier.distance(stationSource.getLatitude(), stationSource.getLongitude(), station.getLatitude(), station.getLongitude()), station.getIdentifiantStation());
            }
        }

        TreeMap<Double, Integer> stationsOrdonneesParCoordonnes = new TreeMap<Double, Integer>(stationsParCoordonnes);

        // retourner la liste des 5 stations les plus proches
        return stationsOrdonneesParCoordonnes;
    }

    // OK
    @Override
    public void creerVelo(int identifiantVelo, boolean operationnel) throws RemoteException {

        veldao.create(new VeloMetier(identifiantVelo, operationnel));
    }

    // OK
    @Override
    public void affectationVeloStation (int identifiantVelo, int identificationStation){

        // On récupère dans la bdd les objets
        VeloMetier v = veldao.find(identifiantVelo);
        StationMetier s = stationdao.find(identificationStation);

        v.setIdentifiantStation(s.getIdentifiantStation());

        // On applique update avec la station
        veldao.update(v);
    }

    @Override
    public ArrayList<Integer> obtenirVelosRattachesAUneStation(int identificationStation) throws RemoteException {
        StationMetier station = stationdao.find(identificationStation);
        station.setListeVelos(veldao.getVeloFromAStation(station.getIdentifiantStation()));
        return station.getListeVelos();
    }

    @Override
    public int obtenirVeloCorrespondantAuPretEnCours(int numero) throws RemoteException {
        UtiliserMetier pret = utiliserdao.find(utiliserdao.obtenirIDUtiliserParNumeroClient(numero));
        return pret.getIdentifiantVelo();
    }

    @Override
    public boolean aUnPretEnCours(int numero) throws RemoteException {
        // SI != 0 --> pret en cours, sinon non
         return utiliserdao.obtenirIDUtiliserParNumeroClient(numero) != 0;
    }

    @Override
    public boolean velosDisposDansLaStation(int identifiantBorneUtilisateur) {
        StationMetier st = stationdao.find(identifiantBorneUtilisateur);
        st.setListeVelos(veldao.getVeloFromAStation(identifiantBorneUtilisateur));
        return !st.getListeVelos().isEmpty();
    }

    @Override
    public boolean stationPleineDeVelos(int identifiantBorneUtilisateur) throws RemoteException {
        StationMetier st = stationdao.find(identifiantBorneUtilisateur);
        st.setListeVelos(veldao.getVeloFromAStation(identifiantBorneUtilisateur));
        return st.getListeVelos().size() == st.getCapacite();
    }

    @Override
    public String connaitreSituationVelo(int identifiantVelo) throws RemoteException {

        String response = "";

        VeloMetier vel = veldao.find(identifiantVelo);
        if (vel.getIdentifiantVelo() == 0) {
            response = "Velo inconnu !";
        } else {
            if (vel.isOperationnel()) {

                // Vérifier le statut du vélo si il est emprunté ou pas
                if (vel.getIdentifiantStation() == 0) {
                    response = "operationnel --> en circulation";
                } else {
                    response = "operationnel --> gare a la station " + vel.getIdentifiantStation();
                }

            } else {
                response = "non operationnel";
            }
        }

        return response;
    }

    @Override
    public boolean initBorneTechnicien() throws RemoteException {

        // Connexion à la borne du tech
        //Récupération d'un proxy sur l'objet
        Remote tech = null;
        try {
            tech = Naming.lookup("rmi://127.0.0.1:5589/BorneTechnicien");
        } catch (NotBoundException | MalformedURLException e) {
            e.printStackTrace();
            return false;
        }

        System.out.println ("Lancement de la borne technicien");

        if (tech instanceof BorneTechnicien) {
            bornetech = (BorneTechnicien) tech;
        }

        return true;
    }

    @Override
    public String obtenirInfosBorneVoisine(int identifiantStation) {
        StationMetier st = stationdao.find(identifiantStation);
        return "Nombre de velos disponibles : " +  st.getNbVelosDispos() + " || Nombre de places restantes = " + (st.getCapacite() - st.getNbVelosDispos());
    }

    @Override
    public String obtenirStatistiqueBorne(int identifiantStation) {
            StationMetier st = stationdao.find(identifiantStation);
            return "ID Borne : " +  st.getIdentifiantStation() + "\nCapacité = " + st.getCapacite() + "\nNombres de vélo disponible : " + st.getNbVelosDispos() + "\nNombre de Retrait : " + st.getNbRetraits() + "\nNombre de Dépôt : " + st.getNbDepots() + "\nNombre de Pénurie : " + st.getNbPenurie() + "\nNombre de Saturation : " + st.getNbPlein() + "\nNombre de Consultation Bornes Voisines : " + st.getNbConsultation() + "\n\n";
        }

    @Override
    public void statisqueBorneConsultation(int identifiantStation) throws RemoteException {
        StationMetier st = stationdao.find(identifiantStation);
        st.setNbConsultation(st.getNbConsultation() + 1);
        //maj dans la base
        stationdao.update(st);
    }


    // OK
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

    // OK
    @Deprecated
    @Override
    public String imprimerRecuUtilisateur(int idPret, Timestamp retrait, Timestamp depot) throws RemoteException {

        // Variable Prix
        double prix_seconde = 0.005;

        // Calcul temps location
        long timeStampFin = depot.getTime();
        long timeStampDebut = retrait.getTime();

        // Calcul de la durée de pret en ms
        long diff = timeStampFin - timeStampDebut;

        // On passe la période en seconde
        long diff_s = Math.abs(diff / 1000) ;

        // calcul du prix de la location
        double prix_location = prix_seconde * diff_s;

        // jour
        int jour = ((int) diff_s) / 86400;
        diff_s = diff_s%86400;

        // heure
        int heure = ((int) diff_s) / 3600;
        diff_s = diff_s%3600;

        // minute

        int minutes = ((int) diff_s) / 60;
        diff_s = diff_s%60;


        // On passe en date humaine pour le ticket
        Date date_humaine_retrait = new Date(retrait.getTime());
        Date date_humaine_depot = new Date(depot.getTime());
        Date date_humaine_emprunt = new Date(diff_s);
        SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy kk:mm:ss" );

        // Affichage du ticket
        String resultat = " // ---------- TICKET UTILISATEUR ---------- // \n" + "N° Prêt : " + idPret + "\n" + "Temps Location : " + "Du " +  sdf.format( date_humaine_retrait ) + " au " + sdf.format( date_humaine_depot ) + " = " + jour + " jour(s) " + heure + " heure(s) " + minutes + " minute(s) " + diff_s + " seconde(s) \n" + "Prix Location : " + prix_location +  " € " +  "\n\n" + "\n Merci de votre visite ! A bientôt sur notre réseau.";
        return resultat;
    }

    // OK
    @Override
    public void notifierVide(BorneTechnicien bt,int idStation) throws RemoteException {
        // en fonction de la station on contacte le bon tech

        //On récupère la station

        StationMetier st=stationdao.find(idStation);
        st.setListeVelos(veldao.getVeloFromAStation(st.getIdentifiantStation()));


        //on recupère la map des techs :
        HashMap<Integer,UtilisateurMetier> techs=utilisateurdao.getTechs();

        if (idStation>= 0 && idStation <5){
            //On récupère le tech
            UtilisateurMetier ut = utilisateurdao.find(6);

            bt.notifierVide(st.toString(), ut.toString());
        }
        if (idStation>= 5 && idStation <10){
            //On récupère le tech
            UtilisateurMetier ut = utilisateurdao.find(7);

            bt.notifierVide(st.toString(), ut.toString());
        }
        if (idStation>= 10 && idStation <15){
            //On récupère le tech
            UtilisateurMetier ut = utilisateurdao.find(8);

            bt.notifierVide(st.toString(), ut.toString());
        }
        if (idStation>= 15 && idStation <20){
            //On récupère le tech
            UtilisateurMetier ut = utilisateurdao.find(9);

            bt.notifierVide(st.toString(), ut.toString());
        }
    }

    @Override
    public void notifierPlein(BorneTechnicien bt,int idStation) throws RemoteException {

        // en fonction de la station on contacte le bon tech
        //On récupère la station

        StationMetier st=stationdao.find(idStation);
        st.setListeVelos(veldao.getVeloFromAStation(st.getIdentifiantStation()));


        //on recupère la map des techs :
        HashMap<Integer,UtilisateurMetier> techs=utilisateurdao.getTechs();

        if (idStation>= 0 && idStation <5){
            //On récupère le tech
            UtilisateurMetier ut = utilisateurdao.find(6);

            bt.notifierPlein(st.toString(), ut.toString());
        }
        if (idStation>= 5 && idStation <10){
            //On récupère le tech
            UtilisateurMetier ut = utilisateurdao.find(7);

            bt.notifierPlein(st.toString(), ut.toString());
        }
        if (idStation>= 10 && idStation <15){
            //On récupère le tech
            UtilisateurMetier ut = utilisateurdao.find(8);

            bt.notifierPlein(st.toString(), ut.toString());
        }
        if (idStation>= 15 && idStation <20){
            //On récupère le tech
            UtilisateurMetier ut = utilisateurdao.find(9);

            bt.notifierPlein(st.toString(), ut.toString());
        }
    }



    // OK
    @Override
    public boolean connexionOkBDD() throws RemoteException {
         return (BDDConnecteur.getInstance() != null);
    }
}
