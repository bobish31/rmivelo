import java.rmi.*;
import java.util.Date;

/**
 * Created by David on 10/04/2015.
 */
public class BorneUtilisateur {
    private ServeurGeneral serv;
    private Station station;

    public BorneUtilisateur(Station s){
        try{
            //Récupération d'un proxy sur l'objet
            serv=(ServeurGeneral) Naming.lookup("//localhost/Serv");

            //initialisation station locale
            station=s;
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void deposerVelo(int numero, int idVelo, Date heureDepot) throws RemoteException {
        //Maj de la station locale
        station.deposerVelo(idVelo);

        //maj de la station distante via l'objet rmi serveur
        serv.deposerVelo(station.getIdentifiantStation(),numero,idVelo,heureDepot);
    }

    public void retirerVelo(int numero, int idVelo, Date heureDepot) throws RemoteException {
        //maj de la station locale
        station.retirerVelo(idVelo);


        //maj de la station distante via l'objet rmi serveur
        serv.retirerVelo(station.getIdentifiantStation(),numero,idVelo,heureDepot);
    }
}
