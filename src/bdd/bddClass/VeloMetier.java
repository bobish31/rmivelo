package bdd.bddClass;

/**
 * Created by Menu on 20/04/2015.
 */
public class VeloMetier {

    private int identifiantVelo;
    private boolean operationnel;

    public VeloMetier() {}

    public VeloMetier(int identifiantVelo){
        this.identifiantVelo = identifiantVelo;
        this.operationnel = true;
    }

    public VeloMetier(int identifiantVelo, boolean operationnel) {
        this.identifiantVelo = identifiantVelo;
        this.operationnel = operationnel;
    }

    public int getIdentifiantVelo() {
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

    @Override
    public String toString() {
        return "VeloMetier{" +
                "identifiantVelo=" + identifiantVelo +
                ", operationnel=" + operationnel +
                '}';
    }
}
