package bdd.bddTestDAO;

import bdd.BDDConnecteur;
import bdd.bddClass.UtiliserMetier;
import bdd.bddDAO.UtiliserDAO;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Menu on 04/05/2015.
 */
public class TestDAOUtiliser {

    public static void main(String[] args) {

        // on lance la bdd
        Connection connect = BDDConnecteur.getInstance();

        if (connect != null)
        {
            System.out.println("Connection BDD OK");

            // ---- TEST CLASSE UTILISER ---- //
            UtiliserDAO utilD = new UtiliserDAO();

            // FIND -> CERTIFIER = ok
              UtiliserMetier u = utilD.find(10);
              // System.out.println(u.toString());

            // DELETE -> CERTIFICATION = ok
             // utilD.delete(u);
             // System.out.println("Suppression OK");

            // UPDATE -> CERTIFICATION = ok
            // Date date = new Date();
            // Timestamp timeStampDate = new Timestamp(date.getTime());

            // u.setDateArivee(timeStampDate);
            // utilD.update(u);
            // System.out.println("Maj ok");

            // CREATE -> CERTIFICATION = ok
            // Date date = new Date();
             // Timestamp timeStampDate = new Timestamp(date.getTime());

             // UtiliserMetier u = new UtiliserMetier (2,timeStampDate,timeStampDate);
             // utilD.create(u);

            // GETINSTANCES -> CERTIFICATION = OK
           /* ArrayList<UtiliserMetier> listePret = utilD.getInstances();

            for (UtiliserMetier u : listePret)
             {
               System.out.println(u.toString());
            }
            */

        }
        else
        {
            System.out.println("Erreur Connection BDD");
        }

    }
}
