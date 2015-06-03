import bdd.bddClass.VeloMetier;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

/**
 * Created by David on 23/05/2015.
 */
public class BorneTechnicienImpl extends UnicastRemoteObject implements BorneTechnicien {

    ServeurGeneralImpl serv;

    protected BorneTechnicienImpl() throws RemoteException {
        serv = new ServeurGeneralImpl();
    }

    // M�thode qui lance le serveur
    public static void main(String[] args) {

        try {
            // Cr�ation du registre
            LocateRegistry.createRegistry(5589);

            // Cr�ation de l'objet
            BorneTechnicienImpl obj = new BorneTechnicienImpl();

            // Cr�ation de l'adresse URL
            String url = "rmi://127.0.0.1:5589/BorneTechnicien";

            // Enregistrement de l'adresse
            Naming.rebind(url, obj);

            // On confirme que tout est ok
            System.out.println("Borne Tech lanc�e ");


            // On va chercher la r�f�rence du serveur g�n�ral
            Remote serv = Naming.lookup("rmi://127.0.0.1:5588/ServeurGeneral");

            if (serv instanceof ServeurGeneral) {

                // On fera les tous appels sur serveurDistant
                ServeurGeneral serveurDistant = (ServeurGeneral) serv;
                serveurDistant.initBorneTechnicien();

                lancerMenuAccueil(serveurDistant);
            }

        } catch (RemoteException | MalformedURLException | NotBoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void notifierVide(String st, String ut) {

        System.out.println("L\'utilisateur : " + ut + " Doit se rendre à la station " + st+" Car elle est presque en pénurie.  " );

    }

    @Override
    public void notifierPlein(String st, String ut) {

        System.out.println("L\'utilisateur : " + ut + " Doit se rendre à la station " + st+" Car elle est presque en saturation.");

    }

    private static void lancerMenuAccueil(ServeurGeneral serveurDistant) throws RemoteException {
        System.out.println("" +
                "************ BIENVENUE SUR LE GESTIONNAIRE DE SERVICE VELO'TOULOUSE ************\n" +
                "\n" +
                "Faites votre choix à l'aide du menu ci-dessous :\n" +
                "\n" +
                "1 - Consulter la situation d'un vélo\n" +
                "2 - Consulter les statistiques du réseau\n");

        System.out.println("Votre choix : ");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        switch (input) {
            case "1":
                consulterSituationVelo(serveurDistant);
                break;
            case "2":
                consulterStatistiqueStation(serveurDistant);
                break;
            default:
                System.out.println("Votre saisie est incorrecte, veuillez reprendre la procédure.\n");
                lancerMenuAccueil(serveurDistant);
                break;
        }
    }

    private static void consulterSituationVelo(ServeurGeneral serveurDistant) throws RemoteException{
        String input;
        System.out.println("Veuillez saisir le numéro du vélo concerné : ");
        input = new Scanner(System.in).nextLine();
        int numeroVelo = Integer.parseInt(input);

        System.out.println(serveurDistant.connaitreSituationVelo(numeroVelo));

        lancerMenuAccueil(serveurDistant);
    }

    private static void consulterStatistiqueStation(ServeurGeneral serveurDistant) throws RemoteException{
        String input;
        System.out.println("Veuillez saisir l'identifiant de la station recherchée : ");
        input = new Scanner(System.in).nextLine();
        int idStation = Integer.parseInt(input);

        System.out.println(serveurDistant.obtenirStatistiqueBorne(idStation));

        lancerMenuAccueil(serveurDistant);
    }
}
