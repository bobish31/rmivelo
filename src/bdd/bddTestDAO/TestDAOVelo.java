package bdd.bddTestDAO;

import bdd.BDDConnecteur;
import bdd.bddClass.VeloMetier;
import bdd.bddDAO.VeloDAO;

import java.sql.Connection;
import java.util.ArrayList;

/**
 * Created by Menu on 04/05/2015.
 */
public class TestDAOVelo {

    public static void main(String[] args) {

        // on lance la bdd
        Connection connect = BDDConnecteur.getInstance();

        if (connect != null)
        {
            System.out.println("Connection BDD OK");

            // ---- TEST CLASSE VELO ---- //
            VeloDAO veloD = new VeloDAO();

            // FIND -> CERTIFIER = ok
             // VeloMetier v = veloD.find(6);
            // System.out.println(v.toString());

            // DELETE -> CERTIFICATION = ok
            // veloD.delete(v);
            // System.out.println("Suppression OK");

            // UPDATE -> CERTIFICATION = ok
            // v.setOperationnel(false);
            // veloD.update(v);
            // System.out.println(v.toString());

            // CREATE -> CERTIFICATION = ok
             // VeloMetier v = new VeloMetier(8,true);
             // veloD.create(v);
             // VeloMetier vcreer = veloD.find(8);
             // System.out.println(vcreer.toString());

            // GETINSTANCES -> CERTIFICATION = OK
            ArrayList<VeloMetier> listeVelo = veloD.getInstances();

            for (VeloMetier v : listeVelo)
            {
                System.out.println(v.toString());
            }





        }
        else
        {
        System.out.println("Erreur Connection BDD");
        }

    }

}
