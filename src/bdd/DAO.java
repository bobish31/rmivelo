package bdd;

import java.sql.Connection;

/**
 * Created by Menu on 20/04/2015.
 */
public abstract class DAO<T> {

        public Connection bddConnecteur = BDDConnecteur.getInstance();

        public abstract T create(T obj);

        public abstract T delete(T obj);

        public abstract T update(T obj);

        public abstract T find(int id);
}
