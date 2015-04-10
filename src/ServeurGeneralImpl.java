import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.HashMap;

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
            // On associe le nom du serveur à l'objet du serveur créé, et on le publie sur le serveur de noms
            Naming.rebind(obj.name, obj);
            System.out.println(obj.name + " déclaré auprès du serveur de noms");
        } catch (Exception e) {
            System.out.println("ServeurGeneral erreur : " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public String[] genererUtilisateur() throws RemoteException {

        // On

        return new String[0];
    }

    @Override
    public boolean deposerVelo(int identifiantBorneUtilisateur, int numero, int identifiantVelo, Date heureDepot) throws RemoteException {
        return false;
    }

    @Override
    public boolean retirerVelo(int identifiantBorneUtilisateur, int numero, int identifiantVelo, Date heureRetrait) throws RemoteException {
        return false;
    }

    @Override
    public String[] obtenirBornesVoisines(int identifiantBorneUtilisateur) throws RemoteException {
        return new String[0];
    }

    @Override
    public boolean creerVelo(int identifiantVelo, boolean operationnel, boolean enCirculation) throws RemoteException {
        return false;
    }

    @Override
    public boolean authentifierUtilisateur(int numero, int code) throws RemoteException {
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
