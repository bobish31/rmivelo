import bdd.bddClass.StationMetier;
import bdd.bddClass.UtilisateurMetier;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by David on 23/05/2015.
 */
public class BorneTechnicienImpl extends UnicastRemoteObject implements BorneTechnicien {

    ServeurGeneralImpl serv;

    protected BorneTechnicienImpl() throws RemoteException {
        serv = new ServeurGeneralImpl();
    }

    // Méthode qui lance le serveur
    public static void main(String[] args) {

        try {
            // Création du registre
            LocateRegistry.createRegistry(5589);

            // Création de l'objet
            BorneTechnicienImpl obj = new BorneTechnicienImpl();

            // Création de l'adresse URL
            String url = "rmi://127.0.0.1:5589/BorneTechnicien";

            // Enregistrement de l'adresse
            Naming.rebind(url, obj);

            // On confirme que tout est ok
            System.out.println("Borne Tech lancée ");


        } catch (RemoteException | MalformedURLException e) {
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

}
