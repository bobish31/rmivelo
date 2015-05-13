package bdd.bddTestDAO;

import bdd.BDDConnecteur;
import bdd.bddClass.StationMetier;
import bdd.bddDAO.StationDAO;

import java.sql.Connection;
import java.util.ArrayList;

/**
 * Created by Menu on 04/05/2015.
 */
public class TestDAOStation {

    public static void main(String[] args) {

        // on lance la bdd
        Connection connect = BDDConnecteur.getInstance();

        if (connect != null) {
            System.out.println("Connection BDD OK");

            // ---- TEST CLASSE STATION ---- //
            StationDAO stationD = new StationDAO();

            // FIND -> CERTIFIER = OK
            // StationMetier s = stationD.find(1);
           // System.out.println(s.toString());

            // DELETE -> CERTIFICATION = OK
            //stationD.delete(s);
            //System.out.println("Suppression OK");

            // UPDATE -> CERTIFICATION = OK
            // s.setCapacite(50);
            // s.setNbRetraits(10);
            // stationD.update(s);
            // System.out.println(s.toString());

            // CREATE -> CERTIFICATION = OK
            // StationMetier s = new StationMetier(5,20,10,10,43.5555,1.48888);
            // stationD.create(s);
            // StationMetier screer = stationD.find(5);
            // System.out.println(s.toString());

            // GETINSTANCES -> CERTIFICATION = OK
            ArrayList<StationMetier> listeStation = stationD.getInstancesByList();

            for (StationMetier s : listeStation)
            {
                System.out.println(s.toString());
            }



        }else {
            System.out.println("Erreur Connection BDD");
        }

    }
}
