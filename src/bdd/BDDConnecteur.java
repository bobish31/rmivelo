package bdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Menu on 20/04/2015.
 */
public class BDDConnecteur {

    // URL acc�s bdd
    private static final String POSTGRESQL_URL = "jdbc:postgresql://localhost:5590/rmivelo";

    // Login user bdd
    private static final String POSTGRESQL_USER = "postgres";

    // Mot de passe user bdd
    private static final String POSTGERSQL_PASSWORD = "romain66";

    // Variable pour la connection
    private static Connection INSTANCE;

    public static Connection getInstance(){
        if(INSTANCE == null){
            try {
                INSTANCE = DriverManager.getConnection(POSTGRESQL_URL, POSTGRESQL_USER, POSTGERSQL_PASSWORD);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return INSTANCE;
    }

}
