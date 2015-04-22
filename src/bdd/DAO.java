package bdd;

import java.sql.Connection;

/**
 * Created by Menu on 20/04/2015.
 */
public interface DAO<T> {

        Connection bddConnecter = BDDConnecteur.getInstance();

        boolean create(T obj);

        boolean delete(T obj);

        boolean update(T obj);

        T find(int id);
}
