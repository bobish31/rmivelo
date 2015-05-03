package bdd;

import java.sql.Connection;
import java.util.ArrayList;

/**
 * Created by Menu on 20/04/2015.
 */
public abstract class DAO<T> {

        public Connection bddConnecteur = BDDConnecteur.getInstance();

        public abstract T create(T obj);

        public abstract void delete(T obj);

        public abstract T update(T obj);

        public abstract T find(int id);

        public abstract ArrayList <T> getInstances ();
}
