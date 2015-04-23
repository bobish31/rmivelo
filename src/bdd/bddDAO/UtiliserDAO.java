package bdd.bddDAO;

import bdd.DAO;
import bdd.bddClass.VeloMetier;

/**
 * Created by lintingre on 23/04/15.
 */
public class UtiliserDAO extends DAO<VeloMetier> {

    private static final String TABLE_UTILISER = "utiliser";

    private static final String COLONNE_UTILISER_UTILISER_ID = "utiliser_id";
    private static final String COLONNE_UTILISER_FK_IDENTIFIANTVELO = "fk_identifiantvelo";
    private static final String COLONNE_UTILISER_FK_NUMERO = "fk_numero";
    private static final String COLONNE_UTILISER_DATERETRAIT = "dateretrait";
    private static final String COLONNE_UTILISER_DATEDEPOT = "datedepot";

    private static final String SEQ_UTILISER_UTILISER_ID = "utiliser_id_seq";


    @Override
    public VeloMetier create(VeloMetier obj) {
        return null;
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
    public VeloMetier find(int id) {
        return null;
    }
}
