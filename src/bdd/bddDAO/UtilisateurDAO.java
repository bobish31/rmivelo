package bdd.bddDAO;

import bdd.DAO;
import bdd.bddClass.UtilisateurMetier;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Menu on 20/04/2015.
 */
public class UtilisateurDAO extends DAO<UtilisateurMetier> {

    private static final String TABLE_UTILISATEUR = "utilisateur";

    private static final String COLONNE_UTILISATEUR_NUMERO = "numero";
    private static final String COLONNE_UTILISATEUR_CODE = "code";
    private static final String COLONNE_UTILISATEUR_TECH = "tech";

    private static final String SEQ_UTILISATEUR_NUMERO= "numero_seq";

    @Override
    public UtilisateurMetier create(UtilisateurMetier obj) {

        try {

            String requete_sequence = "SELECT NEXTVAL('" + SEQ_UTILISATEUR_NUMERO + "') AS " + COLONNE_UTILISATEUR_NUMERO + ";";

            ResultSet result = bddConnecteur.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE
            ).executeQuery(requete_sequence);

            if(result.first()){

                int id = result.getInt(COLONNE_UTILISATEUR_NUMERO);
                String requete =
                        "INSERT INTO " + TABLE_UTILISATEUR + "("
                                + COLONNE_UTILISATEUR_NUMERO + ","
                                + COLONNE_UTILISATEUR_CODE + ","
                                + COLONNE_UTILISATEUR_TECH
                                + ")"
                                + "VALUES (?,?,?);";

                PreparedStatement prepare = bddConnecteur.prepareStatement(requete);
                prepare.setInt(1, id);
                prepare.setInt(2, obj.getCode());
                prepare.setBoolean(3, obj.estTechnicien());
                prepare.executeUpdate();
                obj = this.find(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;

    }

    @Override
    public void delete(UtilisateurMetier obj) {
        try {
            String requete = "DELETE FROM " + TABLE_UTILISATEUR +  " where numero = " + obj.getNumero();

            bddConnecteur.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE
            ).executeUpdate(requete);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public UtilisateurMetier update(UtilisateurMetier obj) {
        String requete = "UPDATE " + TABLE_UTILISATEUR + " SET "
                + COLONNE_UTILISATEUR_CODE+ "=" + obj.getCode()+","
                + COLONNE_UTILISATEUR_TECH+ "=" + obj.estTechnicien()
                + " WHERE " + COLONNE_UTILISATEUR_NUMERO + " = " + obj.getNumero();

        try
        {
            bddConnecteur.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE
            ).executeUpdate(requete);

            obj = this.find(obj.getNumero());

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }

    @Override
    public UtilisateurMetier find(int id) {

        UtilisateurMetier utilisateur = new UtilisateurMetier();

        try {

            String requete = "SELECT * FROM " + TABLE_UTILISATEUR + " WHERE " + COLONNE_UTILISATEUR_NUMERO + " = " + id + ";";

            // R�cup�ration de l'abonn�
            ResultSet result = this.bddConnecteur.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE
            ).executeQuery(requete);

            if(result.first()) {
                utilisateur = new UtilisateurMetier(id,result.getInt(2),result.getBoolean(3));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return utilisateur;

    }

    @Override
    public HashMap<Integer,UtilisateurMetier> getInstancesByMap() {
        HashMap<Integer,UtilisateurMetier> mapUtilisateur = new HashMap<>();

        try {
            String requete = "SELECT * from " + TABLE_UTILISATEUR;

            ResultSet result = bddConnecteur.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE
            ).executeQuery(requete);

            // pour chaque enregistrement de la bdd on le charge dans la liste
            while (result.next()) {
                UtilisateurMetier u = this.find(result.getInt(1));
                mapUtilisateur.put(u.getNumero(), u);

            }
        }    catch (SQLException e) {
            e.printStackTrace();
        }

        return mapUtilisateur;
    }

    public ArrayList<UtilisateurMetier> getInstancesByList() {
        ArrayList<UtilisateurMetier> listeUtilisateur = new ArrayList<>();

        try {
            String requete = "SELECT * from " + TABLE_UTILISATEUR;

            ResultSet result = bddConnecteur.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE
            ).executeQuery(requete);

            // pour chaque enregistrement de la bdd on le charge dans la liste
            while (result.next()) {
                UtilisateurMetier u = this.find(result.getInt(1));
                listeUtilisateur.add(u);

            }
        }    catch (SQLException e) {
            e.printStackTrace();
        }

        return listeUtilisateur;
    }

    
    public HashMap<Integer,UtilisateurMetier> getTechs() {
        HashMap<Integer,UtilisateurMetier> map = new HashMap<>();

        try {
            String requete = "SELECT * from " + TABLE_UTILISATEUR + " where "+COLONNE_UTILISATEUR_TECH+" = true"; 

            ResultSet result = bddConnecteur.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE
            ).executeQuery(requete);

            // pour chaque enregistrement de la bdd on le charge dans la liste
            while (result.next()) {
                UtilisateurMetier u = this.find(result.getInt(1));
                map.put(u.getNumero(), u);

            }
        }    catch (SQLException e) {
            e.printStackTrace();
        }

        return map;
    }
}
