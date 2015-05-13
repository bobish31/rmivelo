import bdd.bddClass.StationMetier;
import bdd.bddClass.UtilisateurMetier;

import java.net.MalformedURLException;
import java.rmi.*;
import java.util.Date;

/**
 * Created by David on 10/04/2015.
 */
public class BorneUtilisateur {

    private StationMetier station;

    public BorneUtilisateur(StationMetier s) {
        //initialisation station locale
        station=s;
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
                    String u = serveurDistant.genererUtilisateur();

                    // On affiche ce qu'on récupère
                    System.out.println(u);

                    // Découpage en split
                    String tableau_utilisateur[]=u.split("/");

                    // On crée le nouvel utilisateur
                    UtilisateurMetier util = new UtilisateurMetier(Integer.parseInt(tableau_utilisateur[0]),Integer.parseInt(tableau_utilisateur[1]));

                    // On affiche l'objet metier
                    System.out.println(util.toString());





                    // ----- Test deposerVelo ------
                    Date date = new Date();
                    serveurDistant.deposerVelo(1, 2, 1, date);


                } else
                {
                    System.out.println("Erreur BDD sur le serveur");
                }


            }


            System.out.println ("Fin du client");

        }
        catch (RemoteException e){
            e.printStackTrace();
        }
        catch (MalformedURLException e){
            e.printStackTrace();
        }
        catch (NotBoundException e) {
            e.printStackTrace();
        }
    }


    public void deposerVelo(int numero, int idVelo, Date heureDepot) throws RemoteException {
        //Maj de la station locale
        station.deposerVelo(idVelo);

        //maj de la station distante via l'objet rmi serveur
      //  serv.deposerVelo(station.getIdentifiantStation(),numero,idVelo,heureDepot);
    }

    public void retirerVelo(int numero, int idVelo, Date heureDepot) throws RemoteException {
        //maj de la station locale
        station.retirerVelo(idVelo);


        //maj de la station distante via l'objet rmi serveur
       // serv.retirerVelo(station.getIdentifiantStation(),numero,idVelo,heureDepot);
    }
}
