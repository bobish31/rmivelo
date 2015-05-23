import bdd.bddClass.StationMetier;
import bdd.bddClass.UtilisateurMetier;

import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 * Created by David on 23/05/2015.
 */
public interface BorneTechnicien extends Remote {


    public void notifierVide(String st, String ut) throws java.rmi.RemoteException;
    public void notifierPlein(String st, String ut)  throws java.rmi.RemoteException;


}