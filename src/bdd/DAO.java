package bdd;

import java.sql.Connection;

/**
 * Created by Menu on 20/04/2015.
 */
public interface DAO<T> {

        public Connection connect = ConnectionBDD.getInstance();

        /**
         * M�thode de cr�ation
         * @param obj
         * @return boolean
         */

        public abstract boolean create(T obj);

        /**
         * M�thode pour effacer
         * @param obj
         * @return boolean
         */
        public abstract boolean delete(T obj);

        /**
         * M�thode de mise � jour
         * @param obj
         * @return boolean
         */
        public abstract boolean update(T obj);

        /**
         * M�thode de recherche des informations
         * @param id
         * @return T
         */
        public abstract T find(int id);
}
