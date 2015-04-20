package bdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Menu on 20/04/2015.
 */
public class ConnectionBDD {

    // URL accès bdd
    private static String url = "jdbc:postgresql://localhost:5590/rmivelo";

    // Login user bdd
    private static String user = "postgres";

    // Mot de passe user bdd
    private static String passwd = "romain66";

    // Variable pour la connection
    private static Connection connect;

    public static Connection getInstance(){
        if(connect == null){
            try {
                connect = DriverManager.getConnection(url, user, passwd);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connect;
    }

}
