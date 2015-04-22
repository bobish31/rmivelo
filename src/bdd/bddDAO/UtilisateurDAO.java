package bdd.bddDAO;

import bdd.DAO;
import bdd.bddClass.UtilisateurMetier;

/**
 * Created by Menu on 20/04/2015.
 */
public class UtilisateurDAO extends DAO<UtilisateurMetier> {


    @Override
    public boolean create(UtilisateurMetier obj) {
        return false;
    }

    @Override
    public boolean delete(UtilisateurMetier obj) {
        return false;
    }

    @Override
    public boolean update(UtilisateurMetier obj) {
        return false;
    }

    @Override
    public UtilisateurMetier find(int id) {
        return null;
    }
}
