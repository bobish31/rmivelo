package bdd;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Menu on 20/04/2015.
 */
public class TestBDD {
    // On va tester dans cette classe si on peut vraiment se co à la bdd
    public static void main (String [] args) throws SQLException {

        // on lance la bdd
         Connection connect = ConnectionBDD.getInstance();

        if (connect != null)
        {
            System.out.println("Connection BDD OK");
        }
        else
        {
            System.out.println("Erreur Connection BDD");
        }

    }

}
