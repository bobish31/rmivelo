import bdd.bddClass.StationMetier;

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
        int [] utilisateur;

        try {

            System.out.println ("Lancement de la borne utilisateur");

            //R�cup�ration d'un proxy sur l'objet
            Remote serv = Naming.lookup("rmi://127.0.0.1:5588/ServeurGeneral");

            if (serv instanceof ServeurGeneral) {

                // On fera les tous appels sur serveurDistant
                ServeurGeneral serveurDistant = (ServeurGeneral) serv;

                // On teste la connexion à la BDD
                if (serveurDistant.connexionOkBDD()) {
                    System.out.println("Connexion à la base de données réussie");
                }

                // On demande la generation d'un client par exemple
                utilisateur = serveurDistant.genererUtilisateur();

                // On affiche le code pour voir si tout fonctionne
                System.out.println ("Numero : " + utilisateur[0]);
                System.out.println ("Code : " + utilisateur[1]);

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
