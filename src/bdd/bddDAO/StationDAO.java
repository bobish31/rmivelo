package bdd.bddDAO;

import bdd.DAO;
import bdd.bddClass.StationMetier;

/**
 * Created by Menu on 20/04/2015.
 */
public class StationDAO extends DAO<StationMetier> {

    private static final String TABLE_STATION = "station";

    private static final String COLONNE_STATION_IDENTIFIANTSTATION = "identifiantstation";
    private static final String COLONNE_STATION_CAPACITE = "capacite";
    private static final String COLONNE_STATION_NBRETRAITS = "nbretraits";
    private static final String COLONNE_STATION_NBDEPOTS = "nbdepots";
    private static final String COLONNE_STATION_LATITUDE = "latitude";
    private static final String COLONNE_STATION_LONGITUDE = "longitude";

    private static final String SEQ_STATION_IDENTIFIANTSTATION = "identifiantstation_seq";


    @Override
    public StationMetier create(StationMetier obj) {
        return null;
    }

    @Override
    public StationMetier delete(StationMetier obj) {
        return null;
    }

    @Override
    public StationMetier update(StationMetier obj) {
        return null;
    }

    @Override
    public StationMetier find(int id) {
        return null;
    }
}
