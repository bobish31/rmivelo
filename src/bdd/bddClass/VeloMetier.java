package bdd.bddClass;

/**
 * Created by Menu on 20/04/2015.
 */
public class VeloMetier {

    private int identifiantVelo;
    private boolean operationnel;
    private boolean enCirculation;

    public VeloMetier()
    {
        // Constructeur par defaut
    }

    public VeloMetier(int id){
        this.identifiantVelo = id;
        operationnel = true;
        enCirculation = false;
    }

    public int getIdentifiant () {
        return identifiantVelo;
    }

    public void setIdentifiantVelo(int identifiantVelo) {
        this.identifiantVelo = identifiantVelo;
    }

    public boolean isOperationnel() {
        return operationnel;
    }

    public void setOperationnel(boolean operationnel) {
        this.operationnel = operationnel;
    }

    public boolean isEnCirculation() {
        return enCirculation;
    }

    public void setEnCirculation(boolean enCirculation) {
        this.enCirculation = enCirculation;
    }

}