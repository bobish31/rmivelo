package bdd.bddClass;

import bdd.BDDConnecteur;
import bdd.bddDAO.VeloDAO;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by David on 22/04/2015.
 */
public class TestBDD {
    // On va tester dans cette classe si on peut vraiment se co � la bdd
    public static void main(String[] args) throws SQLException {

        // on lance la bdd
        Connection connect = BDDConnecteur.getInstance();

        if (connect != null) {
            System.out.println("Connection BDD OK");

            // On initialise nos usines à objets métiers
            VeloDAO veldao = new VeloDAO();

            veldao.create(new VeloMetier(1));

            System.out.println(veldao.find(1).toString());

        } else {

            System.out.println("Erreur Connection BDD");
        }
    }
}
