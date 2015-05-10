package bdd.bddDAO;

import bdd.DAO;
import bdd.bddClass.StationMetier;
import bdd.bddClass.VeloMetier;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

/**
 * Created by Menu on 20/04/2015.
 *
 * Pour faire un select on utilise un executeQuery avec un ResultSet
 * Sinon pour les autres opérations en BDD on utilise un executeUpdate
 */
public class VeloDAO extends DAO<VeloMetier> {

    private static final String TABLE_VELO = "velo";

    private static final String COLONNE_VELO_IDENTIFIANTVELO = "identifiantvelo";
    private static final String COLONNE_VELO_OPERATIONNEL = "operationnel";
    private static final String COLONNE_VELO_FK_IDENTIFIANTSTATION = "fk_identifiantstation";

    private static final String SEQ_VELO_IDENTIFIANTVELO = "identifiantvelo_seq";

    @Override
    public VeloMetier create(VeloMetier obj){
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
                            + COLONNE_VELO_OPERATIONNEL + ","
                            + COLONNE_VELO_FK_IDENTIFIANTSTATION
                        + ")"
                        + "VALUES (?,?,?);";

                PreparedStatement prepare = bddConnecteur.prepareStatement(requete);
                prepare.setInt(1, id);
                prepare.setBoolean(2, obj.isOperationnel());
                prepare.setNull(3, Types.INTEGER);
                prepare.executeUpdate();
                obj = this.find(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }

    @Override
    public void delete(VeloMetier obj) {
        try {
            String requete = "DELETE FROM " + TABLE_VELO + " where identifiantvelo = "+obj.getIdentifiantVelo();

            bddConnecteur.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE
            ).executeUpdate(requete);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public VeloMetier update(VeloMetier obj) {

        String requete = "UPDATE " + TABLE_VELO + " SET "
                + COLONNE_VELO_OPERATIONNEL + " = " + obj.isOperationnel()
                + " WHERE " + COLONNE_VELO_IDENTIFIANTVELO + " = " + obj.getIdentifiantVelo();

            try
            {
            bddConnecteur.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE
            ).executeUpdate(requete);

                obj = this.find(obj.getIdentifiantVelo());

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public VeloMetier update2(VeloMetier obj, StationMetier sta) {

        String requete;
        if(sta!=null){
            requete = "UPDATE " + TABLE_VELO + " SET "
                    + COLONNE_VELO_OPERATIONNEL + " = " + obj.isOperationnel() +","
                    + COLONNE_VELO_FK_IDENTIFIANTSTATION + " = " + sta.getIdentifiantStation()
                    + " WHERE " + COLONNE_VELO_IDENTIFIANTVELO + " = " + obj.getIdentifiantVelo();
        }else{
            requete = "UPDATE " + TABLE_VELO + " SET "
                    + COLONNE_VELO_OPERATIONNEL + " = " + obj.isOperationnel()+","
                    + COLONNE_VELO_FK_IDENTIFIANTSTATION + " = 0"
                    + " WHERE " + COLONNE_VELO_IDENTIFIANTVELO + " = " + obj.getIdentifiantVelo();
        }

        try
        {
            bddConnecteur.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE
            ).executeUpdate(requete);

            obj = this.find(obj.getIdentifiantVelo());

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }

    @Override
    public VeloMetier find(int id){

        VeloMetier velo = new VeloMetier();

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

    // Permet de charger toutes les instances dans la liste velo utiliser pour ServeurGeneralImpl
    public ArrayList<VeloMetier> getInstances () {
        ArrayList<VeloMetier> listeVelos = new ArrayList<>();

        try {
            String requete = "SELECT * from " + TABLE_VELO;

            ResultSet result = bddConnecteur.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE
            ).executeQuery(requete);

            // pour chaque enregistrement de la bdd on le charge dans la liste
            while (result.next()) {
                VeloMetier v = this.find(result.getInt(1));
                listeVelos.add(v);

            }
        }    catch (SQLException e) {
            e.printStackTrace();
        }

        return listeVelos;


        }


}
