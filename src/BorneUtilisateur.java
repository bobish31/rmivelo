import java.rmi.*;

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

    public void deposerVelo(String id){
        //Maj de la station locale
        station.deposerVelo(id);

        //maj de la station distante via l'objet rmi serveur
        serv.deposerVelo(id);
    }

    public void retirerVelo(String id){
        //maj de la station locale
        station.retirerVelo(id);


        //maj de la station distante via l'objet rmi serveur
        serv.retirerVelo(id);
    }
}
