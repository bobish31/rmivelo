package bdd.bddClass;

/**
 * Created by Menu on 20/04/2015.
 */
public class VeloMetier {

    private int identifiantVelo;
    private boolean operationnel;
    private int identifiantStation;

    public VeloMetier() {}

    public VeloMetier(int identifiantVelo, boolean operationnel) {
        this.identifiantVelo = identifiantVelo;
        this.operationnel = operationnel;
    }

    public VeloMetier(int identifiantVelo, boolean operationnel, int identifiantStation) {
        this.identifiantVelo = identifiantVelo;
        this.operationnel = operationnel;
        this.identifiantStation = identifiantStation;
    }

    public int getIdentifiantStation() {
        return identifiantStation;
    }

    public void setIdentifiantStation(int identifiantStation) {
        this.identifiantStation = identifiantStation;
    }

    public int getIdentifiantVelo() {
        return identifiantVelo;
    }

    public boolean isOperationnel() {
        return operationnel;
    }

    @Override
    public String toString() {
        return "VeloMetier{" +
                "identifiantVelo=" + identifiantVelo +
                ", operationnel=" + operationnel +
                '}';
    }
}
