package bdd.bddTestDAO;

import bdd.BDDConnecteur;
import bdd.bddClass.UtilisateurMetier;
import bdd.bddClass.UtiliserMetier;
import bdd.bddDAO.UtilisateurDAO;

import java.sql.Connection;
import java.util.ArrayList;

/**
 * Created by Menu on 04/05/2015.
 */
public class TestDAOUtilisateur {

        public static void main(String[] args) {

            // on lance la bdd
            Connection connect = BDDConnecteur.getInstance();

            if (connect != null) {
                System.out.println("Connection BDD OK");

                // ---- TEST CLASSE STATION ---- //
                UtilisateurDAO utilD = new UtilisateurDAO();

                // FIND -> CERTIFIER = OK
                // UtilisateurMetier u = utilD.find(1);
                 //  System.out.println(u.toString());

                // DELETE -> CERTIFICATION = OK
                // utilD.delete(u);
                // System.out.println("Suppression OK");

                // UPDATE -> CERTIFICATION = OK
                // u.setCode(1500);
                // utilD.update(u);
                // System.out.println(u.toString());

                // CREATE -> CERTIFICATION = OK
                 // UtilisateurMetier u = new UtilisateurMetier(6,8888);
                 // utilD.create(u);
                // UtilisateurMetier screer = utilD.find(6);
                // System.out.println(u.toString());

                // GETINSTANCES -> CERTIFICATION = OK
                ArrayList<UtilisateurMetier> listeUtilisateur = utilD.getInstances();

                for (UtilisateurMetier u : listeUtilisateur)
                {
                    System.out.println(u.toString());
                }



            }else {
                System.out.println("Erreur Connection BDD");
            }

        }
}


