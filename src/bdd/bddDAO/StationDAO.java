package bdd.bddDAO;

import bdd.DAO;
import bdd.bddClass.StationMetier;

/**
 * Created by Menu on 20/04/2015.
 */
public class StationDAO extends DAO<StationMetier> {


    @Override
    public boolean create(StationMetier obj) {
        return false;
    }

    @Override
    public boolean delete(StationMetier obj) {
        return false;
    }

    @Override
    public boolean update(StationMetier obj) {
        return false;
    }

    @Override
    public StationMetier find(int id) {
        return null;
    }
}
