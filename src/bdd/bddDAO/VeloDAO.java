package bdd.bddDAO;

import bdd.DAO;
import bdd.bddClass.VeloMetier;

/**
 * Created by Menu on 20/04/2015.
 */
public class VeloDAO extends DAO<VeloMetier> {


    @Override
    public boolean create(VeloMetier obj) {
        return false;
    }

    @Override
    public boolean delete(VeloMetier obj) {
        return false;
    }

    @Override
    public boolean update(VeloMetier obj) {
        return false;
    }

    @Override
    public VeloMetier find(int id) {
        return null;
    }
}
