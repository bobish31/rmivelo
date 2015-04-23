package bdd.bddDAO;

import bdd.DAO;
import bdd.bddClass.UtilisateurMetier;

/**
 * Created by Menu on 20/04/2015.
 */
public class UtilisateurDAO extends DAO<UtilisateurMetier> {

    private static final String TABLE_UTILISATEUR = "utilisateur";

    private static final String COLONNE_UTILISATEUR_NUMERO = "numero";
    private static final String COLONNE_UTILISATEUR_CODE = "code";

    private static final String SEQ_UTILISATEUR_NUMERO= "numero_seq";

    @Override
    public UtilisateurMetier create(UtilisateurMetier obj) {
        return null;
    }

    @Override
    public UtilisateurMetier delete(UtilisateurMetier obj) {
        return null;
    }

    @Override
    public UtilisateurMetier update(UtilisateurMetier obj) {
        return null;
    }

    @Override
    public UtilisateurMetier find(int id) {
        return null;
    }
}
