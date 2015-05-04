package bdd.bddDAO;

import bdd.DAO;
import bdd.bddClass.UtilisateurMetier;
import bdd.bddClass.UtiliserMetier;
import bdd.bddClass.VeloMetier;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

/**
 * Created by lintingre on 23/04/15.
 */
public class UtiliserDAO extends DAO<UtiliserMetier> {

    private static final String TABLE_UTILISER = "utiliser";

    private static final String COLONNE_UTILISER_UTILISER_ID = "utiliser_id";
    private static final String COLONNE_UTILISER_FK_IDENTIFIANTVELO = "fk_identifiantvelo";
    private static final String COLONNE_UTILISER_FK_NUMERO = "fk_numero";
    private static final String COLONNE_UTILISER_DATERETRAIT = "dateretrait";
    private static final String COLONNE_UTILISER_DATEDEPOT = "datedepot";

    private static final String SEQ_UTILISER_UTILISER_ID = "utiliser_id_seq";


    @Override
    public UtiliserMetier create(UtiliserMetier obj) {
        try {

            // Vu que nous sommes sous postgres, nous allons chercher manuellement
            // la prochaine valeur de la s�quence correspondant � l'id de notre table

            String requete_sequence = "SELECT NEXTVAL('" + SEQ_UTILISER_UTILISER_ID + "') AS " + COLONNE_UTILISER_UTILISER_ID + ";";

            ResultSet result = bddConnecteur.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE
            ).executeQuery(requete_sequence);

            if(result.first()){

                int id = result.getInt(COLONNE_UTILISER_UTILISER_ID);
                String requete =
                        "INSERT INTO " + TABLE_UTILISER + "("
                                + COLONNE_UTILISER_UTILISER_ID + ","
                                + COLONNE_UTILISER_DATERETRAIT + ","
                                + COLONNE_UTILISER_DATEDEPOT + ","
                                + COLONNE_UTILISER_FK_IDENTIFIANTVELO + ","
                                + COLONNE_UTILISER_FK_NUMERO
                                + ")"
                                + "VALUES (?,?,?,?,?);";

                PreparedStatement prepare = bddConnecteur.prepareStatement(requete);
                prepare.setInt(1, id);
                prepare.setDate(2, (Date) obj.getDateDepart());
                prepare.setDate(3, (Date) obj.getDateArivee());
                prepare.setNull(4, Types.INTEGER);
                prepare.setNull(5, Types.INTEGER);
                prepare.executeUpdate();
                obj = this.find(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }



    public UtiliserMetier create2 (UtiliserMetier obj , VeloMetier vel, UtilisateurMetier util) {
        try {

            // Vu que nous sommes sous postgres, nous allons chercher manuellement
            // la prochaine valeur de la s�quence correspondant � l'id de notre table

            String requete_sequence = "SELECT NEXTVAL('" + SEQ_UTILISER_UTILISER_ID + "') AS " + COLONNE_UTILISER_UTILISER_ID + ";";

            ResultSet result = bddConnecteur.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE
            ).executeQuery(requete_sequence);

            if(result.first()){

                int id = result.getInt(COLONNE_UTILISER_UTILISER_ID);
                String requete =
                        "INSERT INTO " + TABLE_UTILISER + "("
                                + COLONNE_UTILISER_UTILISER_ID + ","
                                + COLONNE_UTILISER_DATERETRAIT + ","
                                + COLONNE_UTILISER_DATEDEPOT + ","
                                + COLONNE_UTILISER_FK_IDENTIFIANTVELO + ","
                                + COLONNE_UTILISER_FK_NUMERO
                                + ")"
                                + "VALUES (?,?,?,?,?);";

                PreparedStatement prepare = bddConnecteur.prepareStatement(requete);
                prepare.setInt(1, id);
                prepare.setDate(2, (Date) obj.getDateDepart());
                prepare.setDate(3, (Date) obj.getDateArivee());
                prepare.setInt(4, vel.getIdentifiantVelo());
                prepare.setInt(5, util.getNumero());
                prepare.executeUpdate();
                obj = this.find(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }

    @Override
    public void delete(UtiliserMetier obj) {
        try {
            String requete = "DELETE FROM " + TABLE_UTILISER + "where utiliser_id ="+obj.getIdUtilisation();

            bddConnecteur.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE
            ).executeUpdate(requete);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public UtiliserMetier update(UtiliserMetier obj) {

        String requete = "UPDATE" + TABLE_UTILISER + "SET"
                + COLONNE_UTILISER_DATERETRAIT + "=" + obj.getDateDepart() + ","
                + COLONNE_UTILISER_DATEDEPOT + "=" + obj.getDateArivee()
                + "WHERE" + COLONNE_UTILISER_UTILISER_ID + "=" + obj.getIdUtilisation();

        try
        {
            bddConnecteur.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE
            ).executeUpdate(requete);

            obj = this.find(obj.getIdUtilisation());

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;


    }

    // UPDATE : Permet de mettre � jour toute les cl�s �trang�res
    public UtiliserMetier update2 (UtiliserMetier obj, VeloMetier velo, UtilisateurMetier util) {

        String requete = "UPDATE" + TABLE_UTILISER + "SET"
                + COLONNE_UTILISER_FK_IDENTIFIANTVELO + "=" + velo.getIdentifiantVelo() + ","
                + COLONNE_UTILISER_FK_NUMERO + "=" + util.getNumero() + ","
                + COLONNE_UTILISER_DATERETRAIT + "=" + obj.getDateDepart() + ","
                + COLONNE_UTILISER_DATEDEPOT + "=" + obj.getDateArivee()
                + "WHERE" + COLONNE_UTILISER_UTILISER_ID + "=" + obj.getIdUtilisation();

        try
        {
            bddConnecteur.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE
            ).executeUpdate(requete);

            obj = this.find(obj.getIdUtilisation());

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;


    }

    @Override
    public UtiliserMetier find(int id) {

        UtiliserMetier utiliser = new UtiliserMetier();

        try {

            String requete = "SELECT * FROM " + TABLE_UTILISER + " WHERE " + COLONNE_UTILISER_UTILISER_ID + " = " + id + ";";

            ResultSet result = this.bddConnecteur.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE
            ).executeQuery(requete);

            if(result.first()) {
                utiliser = new UtiliserMetier(id,result.getDate(COLONNE_UTILISER_DATERETRAIT), result.getDate(COLONNE_UTILISER_DATEDEPOT));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return utiliser;
    }

    // Permet de charger toutes les instances dans la liste velo utiliser pour ServeurGeneralImpl
    public ArrayList<UtiliserMetier> getInstances () {
        ArrayList<UtiliserMetier> listePret = new ArrayList<>();

        try {
            String requete = "SELECT * from " + TABLE_UTILISER;

            ResultSet result = bddConnecteur.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE
            ).executeQuery(requete);

            // pour chaque enregistrement de la bdd on le charge dans la liste
            while (result.next()) {
                UtiliserMetier u = this.find(result.getInt(1));
                listePret.add(u);

            }
        }    catch (SQLException e) {
            e.printStackTrace();
        }

        return listePret;


    }
}
