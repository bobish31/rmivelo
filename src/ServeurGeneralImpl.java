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


    private HashMap<Integer, Utilisateur> listeUtilisateurs;
    private HashMap<Integer, Velo> listeVelos;
    private HashMap<Integer, Station> listeStations;

    public ServeurGeneralImpl() throws RemoteException {

        // D�claration des listes
        listeUtilisateurs = new HashMap<>();
    }

    // M�thode qui lance le serveur
    public  static void main(String[] args) {

        try
        {
            // Cr�ation du registre
            LocateRegistry.createRegistry(5588);

            // Cr�ation de l'objet
            ServeurGeneralImpl obj = new ServeurGeneralImpl();

            // Cr�ation de l'adresse URL
            String url = "rmi://127.0.0.1:5588/ServeurGeneral";

            // Enregistrement de l'adresse
            Naming.rebind(url, obj);

            // On confirme que tout est ok
            System.out.println ("Serveur Lanc�");
        }
        catch (RemoteException e){
            e.printStackTrace();
        }
        catch (MalformedURLException e){
            e.printStackTrace();
        }
    }

    @Override
    public int[] genererUtilisateur() throws RemoteException {

        // On r�cup�re le plus grand numero d'utilisateur de la map correspondante
        // int numero = Collections.max(listeUtilisateurs.keySet());
        int numero = 1;

        // On cr�� le numero + 1
        numero++;

        // On g�n�re un code al�atoire de 4 chiffres pour le code
        Random rand = new Random();
        int code = rand.nextInt((9999 - 0) + 1) + 0;

        // On stocke le nouvel utilisateur selon la strat�gie de stockage choisie
        // new Utilisateur(numero, code);

        // on ajoute dans la map locale
        listeUtilisateurs.put(numero, new Utilisateur(numero,code));

        // On retourne le numero + code g�n�r�s dans un tableau de Int
        int[] utilisateur = new int[2];
        utilisateur[0] = numero;
        utilisateur[1] = code;
        return utilisateur;
    }

    @Override
    public void deposerVelo(int identifiantBorneUtilisateur, int numero, int identifiantVelo, Date heureDepot) throws RemoteException {

        // Changement du statut du v�lo
        listeVelos.get(identifiantVelo).setEnCirculation(false);

        // Changement des capacit�s de la station concern�e
        listeStations.get(identifiantBorneUtilisateur).incrementerNbVelosDispos();

        // G�rer les nombres de d�p�ts de v�lo dans la station
        listeStations.get(identifiantBorneUtilisateur).incrementerNbDepots();

    }

    @Override
    public void retirerVelo(int identifiantBorneUtilisateur, int numero, int identifiantVelo, Date heureRetrait) throws RemoteException {

        // Changement du statut du v�lo
        listeVelos.get(identifiantVelo).setEnCirculation(true);

        // Changement des capacit�s de la station concern�e
        listeStations.get(identifiantBorneUtilisateur).decrementerNbVelosDispos();

        // G�rer les nombres de retraits de v�lo dans la station
        listeStations.get(identifiantBorneUtilisateur).decrementerNbRetraits();

    }

    @Override
    public String[] obtenirBornesVoisines(int identifiantBorneUtilisateur) throws RemoteException {


        return new String[0];
    }

    @Override
    public void creerVelo(int identifiantVelo, boolean operationnel, boolean enCirculation) throws RemoteException {

        // On r�cup�re le plus grand numero de velo de la map correspondante
        int numero = Collections.max(listeVelos.keySet());

        // On cr�� le numero + 1
        numero++;

        // On stocke le nouvel velo selon la strat�gie de stockage choisie
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
