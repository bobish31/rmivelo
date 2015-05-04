package bdd.bddDAO;

import bdd.DAO;
import bdd.bddClass.StationMetier;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

/**
 * Created by Menu on 20/04/2015.
 */
public class StationDAO extends DAO<StationMetier> {

    private static final String TABLE_STATION = "station";

    private static final String COLONNE_STATION_IDENTIFIANTSTATION = "identifiantstation";
    private static final String COLONNE_STATION_CAPACITE = "capacite";
    private static final String COLONNE_STATION_NBRETRAITS = "nbretraits";
    private static final String COLONNE_STATION_NBDEPOTS = "nbdepots";
    private static final String COLONNE_STATION_LATITUDE = "latitude";
    private static final String COLONNE_STATION_LONGITUDE = "longitude";

    private static final String SEQ_STATION_IDENTIFIANTSTATION = "identifiantstation_seq";


    @Override
    public StationMetier create(StationMetier obj) {
        try {

            String requete_sequence = "SELECT NEXTVAL('" + SEQ_STATION_IDENTIFIANTSTATION + "') AS " + COLONNE_STATION_IDENTIFIANTSTATION + ";";

            ResultSet result = bddConnecteur.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE
            ).executeQuery(requete_sequence);

            if(result.first()){

                int id = result.getInt(COLONNE_STATION_IDENTIFIANTSTATION);
                String requete =
                        "INSERT INTO " + TABLE_STATION + "("
                                + COLONNE_STATION_IDENTIFIANTSTATION + ","
                                + COLONNE_STATION_CAPACITE + ","
                                + COLONNE_STATION_NBRETRAITS + ","
                                + COLONNE_STATION_NBDEPOTS + ","
                                + COLONNE_STATION_LATITUDE + ","
                                + COLONNE_STATION_LONGITUDE
                                + ")"
                                + "VALUES (?,?,?,?,?,?);";

                PreparedStatement prepare = bddConnecteur.prepareStatement(requete);
                prepare.setInt(1, id);
                prepare.setInt(2, obj.getCapacite());
                prepare.setInt(3, obj.getNbRetraits());
                prepare.setInt(4, obj.getNbDepots());
                prepare.setDouble (5, obj.getLatitude());
                prepare.setDouble (6, obj.getLongitude());
                prepare.executeUpdate();
                obj = this.find(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }

    @Override
    public void delete(StationMetier obj) {
        try {
            String requete = "DELETE FROM " + TABLE_STATION + " where identifiantstation = " + obj.getIdentifiantStation();

            bddConnecteur.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE
            ).executeUpdate(requete);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public StationMetier update(StationMetier obj) {

        String requete = "UPDATE " + TABLE_STATION + " SET "
                + COLONNE_STATION_CAPACITE + " = " + obj.getCapacite() + ","
                + COLONNE_STATION_NBRETRAITS + " = " + obj.getNbRetraits() + ","
                + COLONNE_STATION_NBDEPOTS + " = " + obj.getNbDepots() + ","
                + COLONNE_STATION_LATITUDE + "=  " + obj.getLatitude() + ","
                + COLONNE_STATION_LONGITUDE + " = " + obj.getLongitude()
                + " WHERE " + COLONNE_STATION_IDENTIFIANTSTATION + "=" + obj.getIdentifiantStation();

        try
        {
            bddConnecteur.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE
            ).executeUpdate(requete);

            obj = this.find(obj.getIdentifiantStation());

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }

    @Override
    public StationMetier find(int id) {

        StationMetier station = new StationMetier();

        try {

            String requete = "SELECT * FROM " + TABLE_STATION + " WHERE " + COLONNE_STATION_IDENTIFIANTSTATION + " = " + id + ";";

            // Création de la station
            ResultSet result = this.bddConnecteur.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE
            ).executeQuery(requete);

            if(result.first()) {
                station = new StationMetier(id,result.getInt(COLONNE_STATION_CAPACITE),result.getInt(COLONNE_STATION_NBRETRAITS),result.getInt(COLONNE_STATION_NBDEPOTS), result.getDouble(COLONNE_STATION_LATITUDE), result.getDouble(COLONNE_STATION_LONGITUDE));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return station;

    }

    public ArrayList<StationMetier> getInstances () {
        ArrayList<StationMetier> listeStation = new ArrayList<>();

        try {
            String requete = "SELECT * from " + TABLE_STATION;

            ResultSet result = bddConnecteur.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE
            ).executeQuery(requete);

            // pour chaque enregistrement de la bdd on le charge dans la liste
            while (result.next()) {
                StationMetier s = this.find(result.getInt(1));
                listeStation.add(s);

            }
        }    catch (SQLException e) {
            e.printStackTrace();
        }

        return listeStation;
    }
}
