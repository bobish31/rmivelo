package bdd;

import java.sql.Connection;

/**
 * Created by Menu on 20/04/2015.
 */
public interface DAO<T> {

        public Connection connect = ConnectionBDD.getInstance();

        /**
         * Méthode de création
         * @param obj
         * @return boolean
         */

        public abstract boolean create(T obj);

        /**
         * Méthode pour effacer
         * @param obj
         * @return boolean
         */
        public abstract boolean delete(T obj);

        /**
         * Méthode de mise à jour
         * @param obj
         * @return boolean
         */
        public abstract boolean update(T obj);

        /**
         * Méthode de recherche des informations
         * @param id
         * @return T
         */
        public abstract T find(int id);
}
