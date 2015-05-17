import bdd.bddClass.StationMetier;
import bdd.bddClass.UtilisateurMetier;

import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.rmi.*;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by David on 10/04/2015.
 */
public class BorneUtilisateur {

    private StationMetier station;
    private ServeurGeneralImpl serv;


    public BorneUtilisateur(StationMetier s) {
        //initialisation station locale
        station=s;
        try {
            serv= new ServeurGeneralImpl();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void main (String [] args) {

        // Utilisateur temporaire
        UtilisateurMetier utilisateur;

        try {

            //Récupération d'un proxy sur l'objet
            Remote serv = Naming.lookup("rmi://127.0.0.1:5588/ServeurGeneral");

            System.out.println ("Lancement de la borne utilisateur");

            if (serv instanceof ServeurGeneral) {

                // On fera les tous appels sur serveurDistant
                ServeurGeneral serveurDistant = (ServeurGeneral) serv;

                // On teste la connexion à la BDD
                if (serveurDistant.connexionOkBDD()) {
                    System.out.println("Connexion à la base de données réussie");

                    // --- Appel des fonctionnalités --- //

                    // ---- TEST genererUtilisateur ---- //
                    //inscrireUtilisateur(serveurDistant);

                    // ---- TEST authentifierUtilisateur ---- //
                    //authentifierUtilisateur(serveurDistant);

                    // ---- TEST créerVelo ---- //
                    // serveurDistant.creerVelo(10,true);
                    // System.out.println("Velo créé");

                    // ---- TEST affectationVeloStation  ----
                   /* serveurDistant.affectationVeloStation(8,3);
                    System.out.println("Station affecte");
                    */

                    // ---- TEST retirerVelo  ----
                    //retirerUnVelo(serveurDistant);

                    // ---- TEST deposerVelo  ----
                    //deposerUnVelo(serveurDistant);

                    // ---- TEST obtenirBornesVoisines ----
                    //consulterStationsAlentours(serveurDistant);

                    // MENU D'ACCUEIL
                    lancerMenuAccueil(serveurDistant);

                } else {
                    System.out.println("Erreur BDD sur le serveur\n");
                }

            }

            System.out.println("\n >----- Fin du client -----<");

        }
        catch (RemoteException | NotBoundException | MalformedURLException e){
            e.printStackTrace();
        }
    }

    private static void consulterStationsAlentours(ServeurGeneral serveurDistant) throws RemoteException {
        System.out.println("\nListe des stations voisines disponibles : \n");
        HashMap<Integer, Double> stationsVoisines = serveurDistant.obtenirBornesVoisines(1);

        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.HALF_UP);

        for (Map.Entry<Integer, Double> e : stationsVoisines.entrySet()){
            System.out.println("Station " + e.getKey() + " ---> Distance = " + df.format(e.getValue()) + "km");
        }
    }

    private static void deposerUnVelo(ServeurGeneral serveurDistant) throws RemoteException {
        Date dateDepot = new Date();
        Timestamp heureDepot = new Timestamp(dateDepot.getTime());

        String ticket = serveurDistant.deposerVelo(3, 3, heureDepot);
        System.out.println(ticket);
    }

    private static void retirerUnVelo(ServeurGeneral serveurDistant) throws RemoteException {
        Date dateRetrait = new Date();
        Timestamp heureRetrait = new Timestamp(dateRetrait.getTime());

        serveurDistant.retirerVelo(2, 5, 3, heureRetrait);
    }

    private static void lancerMenuAccueil(ServeurGeneral serveurDistant) throws RemoteException {
        System.out.println("" +
                "************ BIENVENUE SUR LE SERVICE VELO'TOULOUSE ************\n" +
                "\n" +
                "Faites votre choix à l'aide du menu ci-dessous :\n" +
                "\n" +
                "1 - S'inscrire au service Velo'Toulouse\n" +
                "2 - S'authentifier à l'aide de son numéro d'adhérent et code secret\n");

        System.out.println("Votre choix : ");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        switch (input) {
            case "1":
                inscrireUtilisateur(serveurDistant);
                break;
            case "2":
                authentifierUtilisateur(serveurDistant);
                break;
            default:
                System.out.println("Votre saisie est incorrecte, veuillez reprendre la procédure.\n");
                lancerMenuAccueil(serveurDistant);
                break;
        }
    }

    private static void lancerMenuUtilisateurAuthentifie(ServeurGeneral serveurDistant) throws RemoteException {
        System.out.println("" +
                "************ BIENVENUE SUR LE SERVICE VELO'TOULOUSE ************\n" +
                "\n" +
                "Faites votre choix à l'aide du menu ci-dessous :\n" +
                "\n" +
                "1 - Retirer un velo\n" +
                "2 - Déposer un vélo\n" +
                "3 - Consulter les stations aux alentours\n" +
                "");


        System.out.println("Votre choix :\n");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        switch (input) {
            case "1":
                retirerUnVelo(serveurDistant);
                lancerMenuAccueil(serveurDistant);
                break;
            case "2":
                deposerUnVelo(serveurDistant);
                lancerMenuAccueil(serveurDistant);
                break;
            case "3":
                consulterStationsAlentours(serveurDistant);
                break;
            default:
                System.out.println("Votre saisie est incorrecte, veuillez reprendre la procédure.\n");
                lancerMenuUtilisateurAuthentifie(serveurDistant);
                break;
        }
    }

    private static void authentifierUtilisateur(ServeurGeneral serveurDistant) throws RemoteException {
        String input;
        System.out.println("Veuillez saisir votre numéro d'abonné ainsi que votre code secret sous le format suivant : xx/xxxx\n");
        input = new Scanner(System.in).nextLine();
        int numero = Integer.parseInt(input.split("/")[0]);
        int code = Integer.parseInt(input.split("/")[1]);
        boolean connexion = serveurDistant.authentifierUtilisateur(numero, code);
        System.out.println(connexion);
        lancerMenuUtilisateurAuthentifie(serveurDistant);
    }

    private static void inscrireUtilisateur(ServeurGeneral serveurDistant) throws RemoteException {
        String u = serveurDistant.genererUtilisateur();
        // On affiche ce qu'on récupère
        System.out.println(
                "\nFélicitations, votre compte est maintenant actif.\n" +
                "Vos identifiants sont les suivants (numero/code) : " + u + "\n" +
                "Vous pouvez maintenant vous identifier pour profiter du service Velo'Toulouse\n");

        // Découpage en split
        String tableau_utilisateur[]=u.split("/");

        // On crée le nouvel utilisateur
        UtilisateurMetier util = new UtilisateurMetier(Integer.parseInt(tableau_utilisateur[0]),Integer.parseInt(tableau_utilisateur[1]), false);

        // On affiche l'objet metier
        System.out.println(util.toString());
        lancerMenuAccueil(serveurDistant);
    }


    // public void deposerVelo(int numero, int idVelo, Date heureDepot) throws RemoteException {
        //Maj de la station locale
      //  station.deposerVelo(idVelo);

        //maj de la station distante via l'objet rmi serveur
        //serv.deposerVelo(station.getIdentifiantStation(), numero, idVelo, heureDepot); //todo à corriger
   // }

 /*  public void retirerVelo(int numero, int idVelo, Date heureDepot) throws RemoteException {
        //maj de la station locale
        station.retirerVelo(idVelo);




        //maj de la station distante via l'objet rmi serveur
        //serv.retirerVelo(station.getIdentifiantStation(),numero,idVelo,heureDepot); // todo à corriger
    }

    */



}

