package bdd.bddDAO;

import bdd.BDDConnecteur;
import bdd.DAO;
import bdd.bddClass.VeloMetier;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Menu on 20/04/2015.
 */
public class VeloDAO extends DAO<VeloMetier> {


    @Override
    public VeloMetier create(VeloMetier obj){
        try {

            //Vu que nous sommes sous postgres, nous allons chercher manuellement
            //la prochaine valeur de la séquence correspondant à l'id de notre table
            ResultSet result = bddConnecteur.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE
            ).executeQuery(
                            "SELECT NEXTVAL('velo_id_seq') as id"
                    );
            if(result.first()){
                int id = result.getInt("id");
                PreparedStatement prepare =bddConnecteur.prepareStatement(
                        "INSERT INTO velo (velo_id, velo_operationnel,velo_enCirculation) VALUES(?,?,?)"
                );
                prepare.setInt(1, id);
                prepare.setBoolean(2, obj.isOperationnel());
                prepare.setBoolean(3,obj.isEnCirculation());

                prepare.executeUpdate();
                obj = this.find(id);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }

    @Override
    public VeloMetier delete(VeloMetier obj) {
        return null;
    }

    @Override
    public VeloMetier update(VeloMetier obj) {
        return null;
    }

    @Override
    public VeloMetier find(int id){
        VeloMetier velo = new VeloMetier();
        try {
            ResultSet result = this.bddConnecteur
                    .createStatement(
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_UPDATABLE
                    ).executeQuery(
                            "SELECT * FROM velo WHERE velo_id = " + id
                    );
            if(result.first())
                velo = new VeloMetier(id,result.getBoolean("velo_operationnel"),result.getBoolean("velo_enCirculation"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return velo;

    }
}
