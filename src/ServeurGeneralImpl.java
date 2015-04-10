import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

/**
 * Created by Robin on 03/04/2015.
 */
public class ServeurGeneralImpl extends UnicastRemoteObject implements ServeurGeneral {

    // Le nom du serveur sert à nommer le service sur le serveur de noms
    private String name;
    //
    private HashMap<Integer, Utilisateur> listeUtilisateurs;
    private HashMap<Integer, Velo> listeVelos;

    public ServeurGeneralImpl() throws RemoteException {
        // On fixe le nom du serveur pour éviter les erreurs de frappe plus tard
        name = "ServeurGeneral";
    }

    public static void main(String args[]) {
        try {
            ServeurGeneralImpl obj = new ServeurGeneralImpl();
            // On s'enregistre dans le registre
            LocateRegistry.createRegistry(5588);
            // On associe le nom du serveur à l'objet du serveur créé, et on le publie sur le serveur de noms
            Naming.rebind(obj.name, obj);
            System.out.println(obj.name + " déclaré auprès du serveur de noms");
        } catch (Exception e) {
            System.out.println("ServeurGeneral erreur : " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public int[] genererUtilisateur() throws RemoteException {

        // On récupère le plus grand numero d'utilisateur de la map correspondante
        int numero = Collections.max(listeUtilisateurs.keySet());

        // On créé le numero + 1
        numero++;

        // On génére un code aléatoire de 4 chiffres pour le code
        Random rand = new Random();
        int code = rand.nextInt((9999 - 0) + 1) + 0;

        // On stocke le nouvel utilisateur selon la stratégie de stockage choisie
        // new Utilisateur(numero, code);

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

        //


    }

    @Override
    public void retirerVelo(int identifiantBorneUtilisateur, int numero, int identifiantVelo, Date heureRetrait) throws RemoteException {

        // Changement du statut du vélo
        listeVelos.get(identifiantVelo).setEnCirculation(true);

    }

    @Override
    public String[] obtenirBornesVoisines(int identifiantBorneUtilisateur) throws RemoteException {


        return new String[0];
    }

    @Override
    public void creerVelo(int identifiantVelo, boolean operationnel, boolean enCirculation) throws RemoteException {

        // On récupère le plus grand numero de velo de la map correspondante
        int numero = Collections.max(listeVelos.keySet());

        // On créé le numero + 1
        numero++;

        // On stocke le nouvel velo selon la stratégie de stockage choisie
        // new Velo(numero)
    }

    @Override
    public boolean authentifierUtilisateur(int numero, int code) throws RemoteException {

        Utilisateur user = listeUtilisateurs.get(numero);
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
}
