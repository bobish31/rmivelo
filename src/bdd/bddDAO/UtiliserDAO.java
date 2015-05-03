package bdd.bddDAO;

import bdd.DAO;
import bdd.bddClass.UtiliserMetier;
import bdd.bddClass.VeloMetier;

import java.sql.ResultSet;
import java.sql.SQLException;

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
        return null;
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
        return null;
    }

    @Override
    public UtiliserMetier find(int id) {
        return null;
    }
}
