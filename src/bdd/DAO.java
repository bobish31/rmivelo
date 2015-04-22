package bdd;

import java.sql.Connection;

/**
 * Created by Menu on 20/04/2015.
 */
public abstract class DAO<T> {

        Connection bddConnecteur = BDDConnecteur.getInstance();

        public abstract boolean create(T obj);

        public abstract boolean delete(T obj);

        public abstract boolean update(T obj);

        public abstract T find(int id);
}
