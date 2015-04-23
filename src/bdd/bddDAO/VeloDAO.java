package bdd.bddDAO;

import bdd.DAO;
import bdd.bddClass.VeloMetier;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * Created by Menu on 20/04/2015.
 */
public class VeloDAO extends DAO<VeloMetier> {

    private static final String TABLE_VELO = "velo";
    private static final String TABLE_UTILISATEUR = "utilisateur";
    private static final String TABLE_STATION = "station";
    private static final String TABLE_UTILISER = "utiliser";

    private static final String COLONNE_VELO_IDENTIFIANTVELO = "identifiantvelo";
    private static final String COLONNE_VELO_OPERATIONNEL = "operationnel";
    private static final String COLONNE_VELO_FK_IDENTIFIANTSTATION = "fk_identifiantstation";

    private static final String COLONNE_UTILISATEUR_NUMERO = "numero";
    private static final String COLONNE_UTILISATEUR_CODE = "code";

    private static final String COLONNE_STATION_IDENTIFIANTSTATION = "identifiantstation";
    private static final String COLONNE_STATION_CAPACITE = "capacite";
    private static final String COLONNE_STATION_NBRETRAITS = "nbretraits";
    private static final String COLONNE_STATION_NBDEPOTS = "nbdepots";
    private static final String COLONNE_STATION_LATITUDE = "latitude";
    private static final String COLONNE_STATION_LONGITUDE = "longitude";

    private static final String COLONNE_UTILISER_UTILISER_ID = "utiliser_id";
    private static final String COLONNE_UTILISER_FK_IDENTIFIANTVELO = "fk_identifiantvelo";
    private static final String COLONNE_UTILISER_FK_NUMERO = "fk_numero";
    private static final String COLONNE_UTILISER_DATERETRAIT = "dateretrait";
    private static final String COLONNE_UTILISER_DATEDEPOT = "datedepot";

    private static final String SEQ_VELO_IDENTIFIANTVELO = "identifiantvelo_seq";
    private static final String SEQ_UTILISATEUR_NUMERO= "numero_seq";
    private static final String SEQ_STATION_IDENTIFIANTSTATION = "identifiantstation_seq";
    private static final String SEQ_UTILISER_UTILISER_ID = "utiliser_id_seq";


    @Override
    public VeloMetier create(VeloMetier velo){
        try {

            // Vu que nous sommes sous postgres, nous allons chercher manuellement
            // la prochaine valeur de la séquence correspondant à l'id de notre table

            String requete_sequence = "SELECT NEXTVAL('" + SEQ_VELO_IDENTIFIANTVELO + "') AS " + COLONNE_VELO_IDENTIFIANTVELO + ";";

            ResultSet result = bddConnecteur.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE
            ).executeQuery(requete_sequence);

            if(result.first()){

                int id = result.getInt(COLONNE_VELO_IDENTIFIANTVELO);
                String requete =
                        "INSERT INTO " + TABLE_VELO + "("
                            + COLONNE_VELO_IDENTIFIANTVELO + ","
                            + COLONNE_VELO_FK_IDENTIFIANTSTATION
                        + ")"
                        + "VALUES (?,?);";

                PreparedStatement prepare = bddConnecteur.prepareStatement(requete);
                prepare.setInt(1, id);
                prepare.setNull(2, Types.INTEGER);


                // TODO : operationnel est censé être à TRUE automatiquement à la création ... Doit on garder cette ligne ?
                // prepare.setBoolean(2, velo.isOperationnel());
                // TODO : enCirculation a disparu du modèle, on l'enlève du coup ?
                //prepare.setBoolean(3, velo.isEnCirculation());

                prepare.executeUpdate();
                velo = this.find(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return velo;
    }

    @Override
    public VeloMetier delete(VeloMetier obj) {
        return null;
    }

    @Override
    public VeloMetier update(VeloMetier obj) {
        return null;
    }

    @Override
    public VeloMetier find(int id){

        VeloMetier velo = null;

        try {

            String requete = "SELECT * FROM " + TABLE_VELO + " WHERE " + COLONNE_VELO_IDENTIFIANTVELO + " = " + id + ";";

            ResultSet result = this.bddConnecteur.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE
            ).executeQuery(requete);

            if(result.first()) {
                velo = new VeloMetier(id,result.getBoolean(COLONNE_VELO_OPERATIONNEL));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return velo;

    }
}
